package com.kprojekt.alonespace.data.shipParts;

import java.util.ArrayList;
import java.util.List;

import org.andengine.util.adt.list.SmartList;

/**
 * 
 */
public class ShipPartItem extends Item
{
	private ArrayList<Modifier> modifiers = new SmartList<Modifier>();

	public ShipPartItem( String name )
	{
		super( name );
	}

	public void addModifier( Modifier modifier )
	{
		modifiers.add( modifier );
	}

	public List<Modifier> getModifiers()
	{
		return this.modifiers;
	}

}