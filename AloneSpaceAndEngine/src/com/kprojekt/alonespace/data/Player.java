package com.kprojekt.alonespace.data;

import com.kprojekt.alonespace.data.shipParts.ShipItem;

/**
 * 
 */
public class Player
{

	private String name;
	public ShipItem ship;

	public Player( String name )
	{
		this.name = name;
		this.ship = new ShipItem();
	}

	public static Player loadPlayer( int playerId )
	{
		return new Player( "new player" );
	}

	public String getName()
	{
		return this.name;
	}

}
