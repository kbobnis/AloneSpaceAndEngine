package com.kprojekt.alonespace.data.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Krzysiek Bobnis
 * @since 03:00:11 06-01-2013
 */
public class AloneSpaceModel
{
	private final ArrayList<ShipPartCategory> allShipPartCategories;
	private final ArrayList<ShipPart> allShipParts;
	private Ship startingShip;
	private ArrayList<Ship> ships;

	public AloneSpaceModel( ArrayList<ShipPartCategory> shipPartCategories, ArrayList<ShipPart> shipParts,
			ArrayList<Ship> ships, Ship startingShip )
	{
		this.allShipPartCategories = shipPartCategories;
		this.allShipParts = shipParts;
		this.startingShip = startingShip;
		this.ships = ships;
	}

	public Ship getStartingShip()
	{
		return this.startingShip;
	}

	public List<ShipPartCategory> getCategories()
	{
		return this.allShipPartCategories;
	}

	public Ship getShip( String id )
	{
		for( Ship ship : this.ships )
		{
			if( ship.getId().equals( id ) )
			{
				return ship;
			}
		}
		throw new RuntimeException( "There is no ship with id " + id );
	}
}
