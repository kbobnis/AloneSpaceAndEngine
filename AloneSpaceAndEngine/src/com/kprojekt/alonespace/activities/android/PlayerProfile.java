package com.kprojekt.alonespace.activities.android;

import com.kprojekt.alonespace.data.model.Ship;

/**
 * @author Krzysiek Bobnis
 * @since 00:26:11 09-01-2013
 */
public class PlayerProfile
{
	private final long created;
	private Ship ship;

	public PlayerProfile( long created, Ship startingShip )
	{
		this.created = created;
		this.ship = startingShip;
	}

	public long getCreated()
	{
		return this.created;
	}

}
