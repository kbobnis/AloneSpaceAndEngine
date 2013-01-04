package com.kprojekt.alonespace.activities.android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.kprojekt.alonespace.R;
import com.kprojekt.alonespace.activities.andengine.MinigameActivity;
import com.kprojekt.alonespace.activities.andengine.ShipPartsActivity;
import com.kprojekt.alonespace.data.Player;

/**
 * 
 */
public class PlayerMainMenuActivity extends Activity
{
	protected static final int SHIP_PARTS = 1;
	protected static final int MINIGAME = 2;

	protected int sectorX = 0;
	protected int sectorY = 0;
	protected int shipXInSector = 0;
	protected int shipYInSector = 0;

	@Override
	protected void onCreate( Bundle savedInstanceState )
	{
		super.onCreate( savedInstanceState );

		int playerId = this.getIntent().getExtras().getInt( "playerId" );
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
				Intent intent = new Intent( PlayerMainMenuActivity.this, MinigameActivity.class );
				intent.putExtra( "shipXInSector", PlayerMainMenuActivity.this.shipXInSector );
				intent.putExtra( "shipYInSector", PlayerMainMenuActivity.this.shipYInSector );
				intent.putExtra( "sectorX", PlayerMainMenuActivity.this.sectorX );
				intent.putExtra( "sectorY", PlayerMainMenuActivity.this.sectorY );
				PlayerMainMenuActivity.this.startActivityForResult( intent, MINIGAME );
			}
		} );

		Button shipParts = (Button)this.findViewById( R.id.hangar_button_ship );
		shipParts.setOnClickListener( new OnClickListener()
		{
			@Override
			public void onClick( View v )
			{
				Intent intent = new Intent( PlayerMainMenuActivity.this, ShipPartsActivity.class );
				PlayerMainMenuActivity.this.startActivityForResult( intent, SHIP_PARTS );
			}
		} );

	}

	@Override
	protected void onActivityResult( int requestCode, int resultCode, Intent data )
	{
		super.onActivityResult( requestCode, resultCode, data );
		switch( requestCode )
		{
			case SHIP_PARTS:
			{
				break;
			}
			case MINIGAME:
			{
				break;
			}
			default:
				throw new RuntimeException( "There is no result with request code: " + requestCode );

		}
	}
}
