package com.kprojekt.alonespace.activities.android;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Krzysiek Bobnis
 * @since 00:26:11 09-01-2013
 */
public class PlayerProfile
{
	private final long created;

	public PlayerProfile( long created )
	{
		this.created = created;
	}

	public long getCreated()
	{
		return this.created;
	}

	public String toFile()
	{
		try
		{
			return new JSONObject( String.valueOf( this.created ) ).toString();
		}
		catch( JSONException e )
		{
			throw new RuntimeException( e );
		}
	}

}
