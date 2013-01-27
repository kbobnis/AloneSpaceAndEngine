package com.kprojekt.alonespace.data.model;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import java.util.TreeSet;

import org.andengine.util.adt.list.SmartList;

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

	public void fillBlanks( AloneSpaceModel model )
	{
		Ship ship = model.getShip( this.id );
		this.name = ship.name;
		this.desc = ship.desc;
		this.img = ship.img;
		for( ShipPart shipPart : this.parts )
		{
			shipPart.fillBlanks( model );
		}
	}

	public List<ShipPartCategory> getCategories()
	{
		TreeMap<String, ShipPartCategory> categories = new TreeMap<String, ShipPartCategory>();
		for( ShipPart part : this.parts )
		{
			categories.put( part.getCategory().getId(), part.getCategory() );
		}
		return new ArrayList<ShipPartCategory>( categories.values() );
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
