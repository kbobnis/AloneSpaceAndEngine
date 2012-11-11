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
public class HangarActivity extends Activity
{
	@Override
	protected void onCreate( Bundle savedInstanceState )
	{
		super.onCreate( savedInstanceState );

		String playerName = this.getIntent().getExtras().getString( "cityPos" );

		setContentView( R.layout.hangar );

		Button gameButton = (Button)this.findViewById( R.id.hangar_button_gotospace );
		gameButton.setOnClickListener( new OnClickListener()
		{
			@Override
			public void onClick( View v )
			{
				Intent minigame = new Intent( HangarActivity.this, MinigameActivity.class );
				HangarActivity.this.startActivityForResult( minigame, RESULT_OK );
			}
		} );
	}

}
