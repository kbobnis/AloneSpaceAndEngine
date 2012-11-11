package com.kprojekt.alonespace;

import android.app.Activity;
import android.content.Intent;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

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

		Button gameButton = (Button)this.findViewById( R.id.main_button_game );
		gameButton.setOnClickListener( new OnClickListener()
		{
			@Override
			public void onClick( View v )
			{
				Intent minigame = new Intent( MainMenuActivity.this, AloneSpaceAndEngineActivity.class );
				MainMenuActivity.this.startActivityForResult( minigame, RESULT_OK );
			}
		} );
	}
}
