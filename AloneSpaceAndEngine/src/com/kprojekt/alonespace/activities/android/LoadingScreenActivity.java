package com.kprojekt.alonespace.activities.android;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.util.adt.list.SmartList;
import org.w3c.dom.Node;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Display;
import android.view.KeyEvent;
import android.widget.ProgressBar;

import com.kprojekt.alonespace.R;
import com.kprojekt.alonespace.data.Core;
import com.kprojekt.alonespace.data.DataBase;
import com.kprojekt.alonespace.data.model.ActionTemplate;
import com.kprojekt.alonespace.data.model.AloneSpaceModel;
import com.kprojekt.alonespace.data.model.AloneSpaceParser;
import com.kprojekt.alonespace.data.model.Ship;
import com.kprojekt.alonespace.data.model.ShipPart;
import com.kprojekt.alonespace.data.model.ShipPartCategory;
import com.kprojekt.alonespace.utils.XMLHelper;
import com.kprojekt.locale.Locale;

/**
 * @author Krzysiek Bobnis
 */
public class LoadingScreenActivity extends Activity
{

	private XmlLoader xmlLoader;

	@Override
	protected void onCreate( Bundle savedInstanceState )
	{
		Display display = getWindowManager().getDefaultDisplay();
		float prop = display.getHeight() / (float)display.getWidth();
		int height = (int)(prop * Core.widthInMeters);
		Core.heightInMeters = height;
		Core.ratioResPolicy = new RatioResolutionPolicy( Core.widthInMeters, Core.heightInMeters );
		Core.db = new DataBase( this );

		super.onCreate( savedInstanceState );
		setContentView( R.layout.loader );

		ProgressBar progressBar = (ProgressBar)findViewById( R.id.loadProgressBar );

		this.xmlLoader = new XmlLoader( "xml/model.xml", progressBar, this, "xml/locale.xml" );
		this.xmlLoader.execute();
	}

	@Override
	public boolean onKeyDown( int keyCode, KeyEvent event )
	{
		if( keyCode == KeyEvent.KEYCODE_BACK )
		{
			setResult( RESULT_CANCELED, new Intent() );
			if( this.xmlLoader != null )
			{
				this.xmlLoader.cancel( true );
			}
			finish();
		}
		return true;
	}

	@Override
	protected void onActivityResult( int requestCode, int resultCode, Intent data )
	{
		finish();
	}

}

class XmlLoader extends AsyncTask<Void, String, Void>
{

	private final String modelPath;
	private final ProgressBar progressBar;
	private final Activity context;
	private static final int steps = 3;
	private int step = 0;
	private final String localePath;

	public XmlLoader( String modelPath, ProgressBar progressBar, Activity context, String localePath )
	{
		this.modelPath = modelPath;
		this.progressBar = progressBar;
		this.localePath = localePath;
		this.progressBar.setMax( XmlLoader.steps );
		this.progressBar.setProgress( 0 );
		this.context = context;
	}

	@Override
	protected Void doInBackground( Void... arg0 )
	{
		long startTime = System.currentTimeMillis();

		try
		{
			AssetManager assetManager = this.context.getAssets();

			Core.locale = new Locale( assetManager.open( this.localePath ) );
			publishProgress( "loading model. parsing actions" );

			InputStream open = assetManager.open( this.modelPath );

			Node model = AloneSpaceParser.getNodeFromInputStream( open, "alonespace-model" );

			Node tagActions = XMLHelper.getChildrenOfName( model, "action-templates" ).get( 0 );
			HashMap<String, ActionTemplate> actions = AloneSpaceParser.parseActionTemplates( tagActions, assetManager );

			publishProgress( "parsing ship categories" );

			Node tagShipPartCategories = XMLHelper.getChildrenOfName( model, "ship-part-categories" ).get( 0 );
			HashMap<String, ShipPartCategory> shipPartCat = AloneSpaceParser.parseShipCategories(
					tagShipPartCategories, assetManager, actions );

			publishProgress( "parsing ship parts" );

			HashMap<String, HashMap<String, ShipPart>> shipParts = AloneSpaceParser.parseShipPart2(
					tagShipPartCategories, assetManager, actions, shipPartCat );

			publishProgress( "parsing ships" );

			Node tagShips = XMLHelper.getChildrenOfName( model, "ships" ).get( 0 );
			HashMap<String, Ship> parseShips = AloneSpaceParser.parseShips( tagShips, shipParts, assetManager,
					shipPartCat );

			publishProgress( "parsing default ship" );
			Ship startingShip = AloneSpaceParser.parseDefaultShip( tagShips, parseShips );

			ArrayList<ShipPart> shipPartsFinal = new SmartList<ShipPart>();
			for( HashMap<String, ShipPart> tmp : shipParts.values() )
			{
				for( ShipPart tmp2 : tmp.values() )
				{
					shipPartsFinal.add( tmp2 );
				}
			}

			Core.model = new AloneSpaceModel( new ArrayList<ShipPartCategory>( shipPartCat.values() ), shipPartsFinal,
					new ArrayList<Ship>( parseShips.values() ), startingShip, new ArrayList<ActionTemplate>(
							actions.values() ) );
		}
		catch( Exception e )
		{
			throw new RuntimeException( e );
		}

		long endTime = System.currentTimeMillis();
		publishProgress( "" + (endTime - startTime) / 1000f );

		if( !this.isCancelled() )
		{
			Intent intent = new Intent( this.context, MainMenuActivity.class );
			this.context.startActivityForResult( intent, 1 );
		}

		return null;
	}

	@Override
	protected void onProgressUpdate( String... values )
	{
		this.progressBar.setProgress( ++this.step );
	}

}
