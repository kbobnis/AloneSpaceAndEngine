package com.kprojekt.alonespace.data;

/**
 * 
 */
public class Player
{

	private String name;

	public Player( String name )
	{
		this.name = name;
	}

	public static Player loadPlayer( int playerId )
	{
		return new Player( "nowyGracz" );
	}

	public String getName()
	{
		return this.name;
	}

}
