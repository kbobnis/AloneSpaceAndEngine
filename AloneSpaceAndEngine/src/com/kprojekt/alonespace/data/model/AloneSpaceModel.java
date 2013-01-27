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
	private final ArrayList<ActionTemplate> actionTemplates;

	public AloneSpaceModel( ArrayList<ShipPartCategory> shipPartCategories, ArrayList<ShipPart> shipParts,
			ArrayList<Ship> ships, Ship startingShip, ArrayList<ActionTemplate> actionTemplates )
	{
		this.allShipPartCategories = shipPartCategories;
		this.allShipParts = shipParts;
		this.startingShip = startingShip;
		this.ships = ships;
		this.actionTemplates = actionTemplates;
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

	public ShipPart getPart( String partId, String catId )
	{
		for( ShipPart part : this.allShipParts )
		{
			if( part.getId().equals( partId ) && part.getCategory().getId().equals( catId ) )
			{
				return part;
			}
		}
		return null;
	}

	public ActionTemplate getActionTemplate( String id )
	{
		for( ActionTemplate actionTemplate : this.actionTemplates )
		{
			if( actionTemplate.getId().equals( id ) )
			{
				return actionTemplate;
			}
		}
		return null;
	}

	public ShipPartCategory getCategory( String id )
	{
		for( ShipPartCategory category : this.allShipPartCategories )
		{
			if( category.getId().equals( id ) )
			{
				return category;
			}
		}
		return null;
	}
}
