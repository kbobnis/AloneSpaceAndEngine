package com.kprojekt.alonespace.activities.android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.kprojekt.alonespace.R;
import com.kprojekt.alonespace.data.Core;

/**
 * 
 */
public class MainMenuActivity extends Activity
{

	@Override
	protected void onCreate( Bundle savedInstanceState )
	{
		super.onCreate( savedInstanceState );
		setContentView( R.layout.main );

		Display display = getWindowManager().getDefaultDisplay();
		float prop = display.getHeight() / (float)display.getWidth();
		int height = (int)(prop * Core.width);
		Core.height = height;

		Button gameButton = (Button)this.findViewById( R.id.main_button_game );
		gameButton.setOnClickListener( new OnClickListener()
		{
			@Override
			public void onClick( View v )
			{
				Intent minigame = new Intent( MainMenuActivity.this, PlayerMainMenuActivity.class );
				minigame.putExtra( "playerId", 0 );
				MainMenuActivity.this.startActivityForResult( minigame, RESULT_OK );
			}
		} );
		Button highScores = (Button)this.findViewById( R.id.hangar_button_highscores );
		highScores.setOnClickListener( new OnClickListener()
		{
			@Override
			public void onClick( View v )
			{
				Intent minigame = new Intent( MainMenuActivity.this, HighscoresActivity.class );
				MainMenuActivity.this.startActivityForResult( minigame, RESULT_OK );
			}
		} );
	}
}
