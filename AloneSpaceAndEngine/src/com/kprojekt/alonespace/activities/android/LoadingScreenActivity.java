package com.kprojekt.alonespace.activities.android;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.ProgressBar;

import com.kprojekt.alonespace.R;
import com.kprojekt.alonespace.data.Core;
import com.kprojekt.alonespace.data.model.AloneSpaceModel;
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
			publishProgress( "" );

			Core.model = new AloneSpaceModel( assetManager.open( this.modelPath ), assetManager );
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
