package com.kprojekt.alonespace.activities.android;

import java.util.List;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.kprojekt.alonespace.R;
import com.kprojekt.alonespace.activities.andengine.MinigameActivity;
import com.kprojekt.alonespace.data.Core;

/**
 * @author Krzysztof Bobnis 

 */
public class MainMenuActivity extends ListActivity
{

	@Override
	protected void onCreate( Bundle savedInstanceState )
	{
		super.onCreate( savedInstanceState );
		setContentView( R.layout.main_menu_list );

		ListView lv = getListView();
		LayoutInflater inflater = getLayoutInflater();

		View header = inflater.inflate( R.layout.main_menu_headerlist,
				(ViewGroup)findViewById( R.id.header_layout_root ) );
		TextView titleTV = (TextView)header.findViewById( R.id.listLinearAdText );
		titleTV.setText( Core.locale.get( "str.title" ) );
		lv.addHeaderView( header, null, false );

		View footer = inflater.inflate( R.layout.main_menu_footerlist,
				(ViewGroup)findViewById( R.id.footer_layout_root ) );
		lv.addFooterView( footer );
		Button highscores = (Button)footer.findViewById( R.id.main_menu_footerlist_highscores );
		highscores.setText( Core.locale.get( "str.highscores.label" ) );
		highscores.setOnClickListener( new OnClickListener()
		{
			@Override
			public void onClick( View v )
			{
				Intent minigame = new Intent( MainMenuActivity.this, HighscoresActivity.class );
				MainMenuActivity.this.startActivityForResult( minigame, RESULT_OK );
			}
		} );
		Button newGame = (Button)footer.findViewById( R.id.main_menu_footerlist_newgame );
		newGame.setText( Core.locale.get( "str.newgame.label" ) );
		newGame.setOnClickListener( new OnClickListener()
		{
			@Override
			public void onClick( View arg0 )
			{
				Core.loggedProfile = Core.db.createProfile( System.currentTimeMillis() );
				Intent cityCamerasIntent = new Intent( MainMenuActivity.this, MinigameActivity.class );
				MainMenuActivity.this.startActivityForResult( cityCamerasIntent, RESULT_CANCELED );
			}
		} );

		List<PlayerProfile> profiles = Core.db.loadProfiles();
		ProfilesAdapter cityArrayAdapter = new ProfilesAdapter( this, R.layout.main_menu_row2, profiles, this );
		setListAdapter( cityArrayAdapter );
	}

	@Override
	public boolean onKeyDown( int keyCode, KeyEvent event )
	{
		if( keyCode == KeyEvent.KEYCODE_BACK )
		{
			setResult( 0, new Intent() );
			finish();
		}
		return true;
	}

	@Override
	protected void onListItemClick( ListView l, View v, int position, long id )
	{
		Intent cityCamerasIntent = new Intent( this, MinigameActivity.class );
		this.startActivityForResult( cityCamerasIntent, RESULT_CANCELED );
	}
}

class ProfilesAdapter extends ArrayAdapter<PlayerProfile>
{

	private final List<PlayerProfile> cities;
	private TextView profileName;

	public ProfilesAdapter( Context context, int textViewResourceId, List<PlayerProfile> cities,
			ListActivity oneCityActivity )
	{
		super( context, textViewResourceId, cities );
		this.cities = cities;
	}

	public int getCount()
	{
		return this.cities.size();
	}

	public PlayerProfile getItem( int index )
	{
		return this.cities.get( index );
	}

	public View getView( int position, View convertView, ViewGroup parent )
	{
		View row = convertView;
		if( row == null )
		{
			LayoutInflater inflater = (LayoutInflater)this.getContext().getSystemService(
					Context.LAYOUT_INFLATER_SERVICE );
			row = inflater.inflate( R.layout.main_menu_row2, null );
		}
		final PlayerProfile el = getItem( position );

		profileName = (TextView)row.findViewById( R.id.main_menu_row2_textView );
		profileName.setText( Core.locale.get( "str.created" ) + ": " + el.getCreated() );

		return row;
	}
}
