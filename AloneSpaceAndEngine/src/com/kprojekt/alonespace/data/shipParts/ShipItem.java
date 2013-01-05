package com.kprojekt.alonespace.data.shipParts;

import java.util.ArrayList;

import org.andengine.util.adt.list.SmartList;

/**
 * 
 */
public class ShipItem
{

	public ArrayList<ShipPartItem> parts = new SmartList<ShipPartItem>();

	public ShipItem()
	{
		ArrayList<Modifier> arrayChildren = new ArrayList<Modifier>();

		//here we set the parents and the children
		for( int i = 0; i < 10; i++ )
		{
			ShipPartItem parent = new ShipPartItem( "Parent " + i );
			arrayChildren.add( new Modifier( "Child " + i ) );
			for( Modifier modifier : arrayChildren )
			{
				parent.addModifier( modifier );
			}

			this.parts.add( parent );
		}
	}

}
