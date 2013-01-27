package com.kprojekt.alonespace.data.model;

import java.util.List;

import org.andengine.util.adt.list.SmartList;

import com.kprojekt.alonespace.data.FillBlanksException;

import android.graphics.drawable.Drawable;

/**
 * @author Krzysiek Bobnis
 * @since 16:13:13 06-01-2013
 */
public class Ship
{
	private final String id;
	private transient String name;
	private transient String desc;
	private transient Drawable img;
	private List<ShipPart> parts;

	public Ship( String id, String name, String desc, Drawable img, List<ShipPart> collection )
	{
		this.id = id;
		this.name = name;
		this.desc = desc;
		this.img = img;
		this.parts = collection;
	}

	public Ship( Ship copy )
	{
		this.id = copy.id;
		this.name = copy.name;
		this.desc = copy.desc;
		this.img = copy.img;
		this.parts = copy.parts;
	}

	public void fillBlanks( Ship ship )
	{
		this.name = ship.name;
		this.desc = ship.desc;
		this.img = ship.img;
		for( ShipPart shipPart : this.parts )
		{
			try
			{
				String shipPartId = shipPart.getId();
				ShipPart part = ship.getPart( shipPartId, shipPart.getCategory().getId() );
				shipPart.fillBlanks( part );
			}
			catch( FillBlanksException e )
			{
				throw new RuntimeException( e + ": " + shipPart + ", " + this );
			}
		}
	}

	private ShipPart getPart( String partId, String catId )
	{
		for( ShipPart shipPart : this.parts )
		{
			if( shipPart.getId().equals( id ) && shipPart.getCategory().getId().equals( catId ) )
			{
				return shipPart;
			}
		}
		throw new RuntimeException( "There is no ship part " + catId + "/" + id + " in ship " + this );
	}

	public List<ShipPartCategory> getCategories()
	{
		List<ShipPartCategory> categories = new SmartList<ShipPartCategory>();
		for( ShipPart part : this.parts )
		{
			categories.add( part.getCategory() );
		}
		return categories;
	}

	public List<ShipPart> getPartsOfCategory( String catId )
	{
		List<ShipPart> tmp = new SmartList<ShipPart>();
		for( ShipPart part : this.parts )
		{
			if( part.getCategory().getId() == catId )
			{
				tmp.add( part );
			}
		}
		return tmp;
	}

	public String getId()
	{
		return this.id;
	}

	@Override
	public String toString()
	{
		String parts = "";
		for( ShipPart shipPart : this.parts )
		{
			parts += shipPart + ", ";
		}
		return this.id + "(ship), " + parts;
	}
}
