package com.kprojekt.alonespace.activities.android;

import android.app.Activity;
import android.content.res.AssetManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ProgressBar;

import com.kprojekt.alonespace.R;
import com.kprojekt.alonespace.data.Core;
import com.kprojekt.alonespace.data.model.AloneSpaceModel;
import com.kprojekt.locale.Locale;

/**
 * @author Krzysztof Bobnis 
 * 
 */
public class LoadingScreenActivity extends Activity
{

	@Override
	protected void onCreate( Bundle savedInstanceState )
	{
		super.onCreate( savedInstanceState );
		setContentView( R.layout.loader );

		ProgressBar progressBar = (ProgressBar)findViewById( R.id.loadProgressBar );

		XmlLoader xmlLoader = new XmlLoader( "xml/model.xml", progressBar, this, "xml/locale.xml" );
		xmlLoader.execute();
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
			publishProgress( "" );
		}
		catch( Exception e )
		{
			throw new RuntimeException( e );
		}

		publishProgress( "" );

		long endTime = System.currentTimeMillis();
		publishProgress( "" + (endTime - startTime) / 1000f );
		return null;
	}

	@Override
	protected void onProgressUpdate( String... values )
	{
		this.progressBar.setProgress( ++this.step );
	}

}
