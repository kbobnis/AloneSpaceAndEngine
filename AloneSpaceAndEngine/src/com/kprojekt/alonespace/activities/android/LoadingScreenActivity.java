package com.kprojekt.alonespace.activities.android;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import android.app.Activity;
import android.content.res.AssetManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ProgressBar;

import com.kprojekt.alonespace.R;

/**
 * @author Krzysztof Bobnis 
 */
public class LoadingScreenActivity extends Activity
{

	@Override
	protected void onCreate( Bundle savedInstanceState )
	{
		super.onCreate( savedInstanceState );
		setContentView( R.layout.loader );

		ProgressBar progressBar = (ProgressBar)findViewById( R.id.loadProgressBar );

		XmlLoader xmlLoader = new XmlLoader( "model.xml", progressBar, this );
		xmlLoader.execute();
	}

}

class XmlLoader extends AsyncTask<Void, String, Void>
{

	private final String modelPath;
	private final ProgressBar progressBar;
	private final Activity context;
	private final int steps = 8;
	private int step = 0;

	public XmlLoader( String modelPath, ProgressBar progressBar, Activity context )
	{
		this.modelPath = modelPath;
		this.progressBar = progressBar;
		this.context = context;
	}

	@Override
	protected Void doInBackground( Void... arg0 )
	{
		long startTime = System.currentTimeMillis();

		AssetManager assetManager = this.context.getAssets();
		InputStream inStr;
		try
		{
			inStr = assetManager.open( this.modelPath );
			DocumentBuilder docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document doc = docBuilder.parse( inStr, null );
			NodeList model = doc.getElementsByTagName( "alonespace-model" );
			int k = 65;
		}
		catch( IOException e )
		{
			// TODO @Krzysiek logger
			e.printStackTrace();
		}
		catch( ParserConfigurationException e )
		{
			// TODO @Krzysiek logger
			e.printStackTrace();
		}
		catch( SAXException e )
		{
			// TODO @Krzysiek logger
			e.printStackTrace();
		}

		publishProgress( "" );
		//Core.locale.load( this.modelPath );
		//Core.templates.shipParts.load( this.modelPath );

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
