package com.kprojekt.alonespace.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kprojekt.alonespace.data.model.Ship;
import com.kprojekt.alonespace.data.model.ShipPart;

/**
 * @author Krzysiek Bobnis
 * @since 00:52:58 08-01-2013
 */
public class DataBase
{
	private static final String FILE_NAME = "db";
	private static final String PROFILES_KEY = "profiles";
	private final Context context;
	private List<PlayerProfile> profiles;

	public DataBase( Context context )
	{
		this.context = context;
	}

	public List<PlayerProfile> loadProfiles()
	{
		if( this.profiles == null )
		{
			SharedPreferences prefs = context.getSharedPreferences( DataBase.FILE_NAME, Context.MODE_PRIVATE );
			String tmpStrProfiles = prefs.getString( DataBase.PROFILES_KEY, null );
			Gson gson = new Gson();
			this.profiles = new ArrayList<PlayerProfile>();

			PlayerProfile[] profiles = gson.fromJson( tmpStrProfiles, PlayerProfile[].class );

			if( profiles != null )
			{
				for( PlayerProfile playerProfile : profiles )
				{
					Ship ship = playerProfile.getShip();
					ship.fillBlanks( Core.model );
				}
				this.profiles.addAll( Arrays.asList( profiles ) );
			}
		}

		return this.profiles;
	}

	public PlayerProfile createProfile( long currentTimeMillis )
	{
		List<PlayerProfile> loadProfiles = this.loadProfiles();
		PlayerProfile playerProfile = new PlayerProfile( currentTimeMillis, new Ship( Core.model.getStartingShip() ) );
		loadProfiles.add( playerProfile );
		this.saveProfiles( loadProfiles );
		return playerProfile;
	}

	private void saveProfiles( List<PlayerProfile> profiles )
	{
		SharedPreferences prefs = context.getSharedPreferences( DataBase.FILE_NAME, Context.MODE_PRIVATE );
		Editor edit = prefs.edit();

		Gson gson = new Gson();

		String json = gson.toJson( profiles );
		edit.putString( DataBase.PROFILES_KEY, json );
		edit.commit();

		this.profiles = profiles;
	}

	public void deleteProfile( PlayerProfile item )
	{
		this.profiles.remove( item );
		this.saveProfiles( this.profiles );
	}

	public void saveProfiles()
	{
		this.saveProfiles( this.profiles );
	}
}
