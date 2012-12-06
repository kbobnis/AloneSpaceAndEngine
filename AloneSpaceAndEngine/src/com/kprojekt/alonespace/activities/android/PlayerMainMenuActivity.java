package com.kprojekt.alonespace.activities.android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.kprojekt.alonespace.R;
import com.kprojekt.alonespace.activities.andengine.ChooseSectorActivity;
import com.kprojekt.alonespace.activities.andengine.MinigameActivity;
import com.kprojekt.alonespace.activities.andengine.ShowShipActivity;
import com.kprojekt.alonespace.data.Player;

/**
 * 
 */
public class PlayerMainMenuActivity extends Activity
{
	protected static final int CHOOSE_SECTOR = 0;
	protected static final int SHOW_SHIP = 1;
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

		Button showSpaceButton = (Button)this.findViewById( R.id.hangar_button_showspace );
		showSpaceButton.setOnClickListener( new OnClickListener()
		{
			@Override
			public void onClick( View v )
			{
				Intent intent = new Intent( PlayerMainMenuActivity.this, ChooseSectorActivity.class );
				intent.putExtra( "sectorX", PlayerMainMenuActivity.this.sectorX );
				intent.putExtra( "sectorY", PlayerMainMenuActivity.this.sectorY );
				PlayerMainMenuActivity.this.startActivityForResult( intent, CHOOSE_SECTOR );
			}
		} );

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

		Button showShipButton = (Button)this.findViewById( R.id.hangar_button_ship );
		showShipButton.setOnClickListener( new OnClickListener()
		{
			@Override
			public void onClick( View v )
			{
				Intent intent = new Intent( PlayerMainMenuActivity.this, ShowShipActivity.class );
				PlayerMainMenuActivity.this.startActivityForResult( intent, SHOW_SHIP );
			}
		} );
	}

	@Override
	protected void onActivityResult( int requestCode, int resultCode, Intent data )
	{
		super.onActivityResult( requestCode, resultCode, data );
		switch( requestCode )
		{
			case CHOOSE_SECTOR:
			{
				break;
			}
			case SHOW_SHIP:
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
