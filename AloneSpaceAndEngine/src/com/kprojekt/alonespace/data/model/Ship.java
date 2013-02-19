package com.kprojekt.alonespace.data.model;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

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
	private List<ShipPart> allParts;
	private List<ShipPart> equippedParts = new ArrayList<ShipPart>();

	public Ship( String id, String name, String desc, Drawable img, List<ShipPart> collection )
	{
		this.id = id;
		this.name = name;
		this.desc = desc;
		this.img = img;
		this.allParts = collection;
		for( ShipPartCategory shipPartCategory : this.getCategories() )
		{
			ShipPart shipPart = this.getPartsOfCategory( shipPartCategory ).get( 0 );
			this.equipPart( shipPart );
		}
	}

	public Ship( Ship copy )
	{
		this.id = copy.id;
		this.name = copy.name;
		this.desc = copy.desc;
		this.img = copy.img;
		this.allParts = copy.allParts;
		this.equippedParts = copy.equippedParts;
	}

	public void fillBlanks( AloneSpaceModel model )
	{
		Ship ship = model.getShip( this.id );
		this.name = ship.name;
		this.desc = ship.desc;
		this.img = ship.img;
		for( ShipPart shipPart : this.allParts )
		{
			shipPart.fillBlanks( model );
		}
		for( ShipPart shipPart : this.equippedParts )
		{
			shipPart.fillBlanks( model );
		}
	}

	public List<ShipPartCategory> getCategories()
	{
		TreeMap<String, ShipPartCategory> categories = new TreeMap<String, ShipPartCategory>();
		for( ShipPart part : this.allParts )
		{
			categories.put( part.getCategory().getId(), part.getCategory() );
		}
		return new ArrayList<ShipPartCategory>( categories.values() );
	}

	public List<ShipPart> getPartsOfCategory( ShipPartCategory cat )
	{
		List<ShipPart> tmp = new SmartList<ShipPart>();
		for( ShipPart part : this.allParts )
		{
			if( part.getCategory() == cat )
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
		for( ShipPart shipPart : this.allParts )
		{
			parts += shipPart + ", ";
		}
		return this.id + "(ship), " + parts;
	}

	public ShipPart getEquippedPartOfCategory( ShipPartCategory cat )
	{
		if( this.equippedParts == null )
		{
			this.equippedParts = new ArrayList<ShipPart>();
		}
		for( ShipPart part : this.equippedParts )
		{
			if( part.getCategory().compareTo( cat ) == 0 )
			{
				return part;
			}
		}
		return null;
	}

	public void equipPart( ShipPart shipPart )
	{
		ShipPart equippedPartOfCategory = this.getEquippedPartOfCategory( shipPart.getCategory() );
		if( equippedPartOfCategory != null )
		{
			this.equippedParts.remove( equippedPartOfCategory );
		}

		this.equippedParts.add( shipPart );
	}

	public void addPart( ShipPart part )
	{
		this.allParts.add( part );
	}

	public float getActionValue( ActionTemplate.Type type )
	{
		float val = 0;
		for( ShipPart part : this.allParts )
		{
			val += part.getActionValue( type );
		}
		return val;
	}

}
