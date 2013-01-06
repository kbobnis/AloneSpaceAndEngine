package com.kprojekt.alonespace.activities.android;

import android.app.Activity;
import android.os.Bundle;

import com.kprojekt.alonespace.R;

/**
 * @author Krzysztof Bobnis 
 */
public class HighscoresActivity extends Activity
{

	@Override
	protected void onCreate( Bundle savedInstanceState )
	{
		super.onCreate( savedInstanceState );
		setContentView( R.layout.highscores );
	}

}
