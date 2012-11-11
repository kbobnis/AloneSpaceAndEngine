package com.kprojekt.alonespace.activities.android;

import com.kprojekt.alonespace.R;
import com.kprojekt.alonespace.R.id;
import com.kprojekt.alonespace.R.layout;
import com.kprojekt.alonespace.activities.andengine.ChooseSectorActivity;
import com.kprojekt.alonespace.activities.andengine.ShowShipActivity;
import com.kprojekt.alonespace.data.Player;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

/**
 * 
 */
public class PlayerMainMenuActivity extends Activity
{
	@Override
	protected void onCreate( Bundle savedInstanceState )
	{
		super.onCreate( savedInstanceState );

		String playerId = this.getIntent().getExtras().getString( "playerId" );
		Player player = Player.loadPlayer( playerId );

		setContentView( R.layout.playermain );

		TextView textViewPlayerName = (TextView)this.findViewById( R.id.hangar_textView_playerName );
		textViewPlayerName.setText( player.getName() );

		Button gameButton = (Button)this.findViewById( R.id.hangar_button_gotospace );
		gameButton.setOnClickListener( new OnClickListener()
		{
			@Override
			public void onClick( View v )
			{
				Intent intent = new Intent( PlayerMainMenuActivity.this, ChooseSectorActivity.class );
				PlayerMainMenuActivity.this.startActivityForResult( intent, RESULT_OK );
			}
		} );

		Button showShipButton = (Button)this.findViewById( R.id.hangar_button_ship );
		showShipButton.setOnClickListener( new OnClickListener()
		{
			@Override
			public void onClick( View v )
			{
				Intent intent = new Intent( PlayerMainMenuActivity.this, ShowShipActivity.class );
				PlayerMainMenuActivity.this.startActivityForResult( intent, RESULT_OK );
			}
		} );
	}
}
