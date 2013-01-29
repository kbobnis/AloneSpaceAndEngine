package com.kprojekt.alonespace.data.model;

import android.graphics.drawable.Drawable;

/**
 * @author Krzysiek Bobnis
 * @since 15:31:55 06-01-2013
 */
public class ShipPartCategory implements Comparable<ShipPartCategory>
{
	private final String id;
	private transient String name;
	private transient String desc;
	private transient Drawable img;
	private transient boolean obligatory;

	public ShipPartCategory( String id, String name, String desc, Drawable img, boolean obligatory )
	{
		this.id = id;
		this.name = name;
		this.desc = desc;
		this.img = img;
		this.obligatory = obligatory;
	}

	public boolean isObligatory()
	{
		return this.obligatory;
	}

	public String getId()
	{
		return this.id;
	}

	public String getName()
	{
		return this.name;
	}

	public void fillBlanks( AloneSpaceModel model )
	{
		ShipPartCategory category = model.getCategory( this.id );
		this.name = category.name;
		this.desc = category.desc;
		this.img = category.img;
		this.obligatory = category.obligatory;
	}

	@Override
	public int compareTo( ShipPartCategory two )
	{
		return id.equals( two.id ) ? 0 : 1;
	}

	@Override
	public boolean equals( Object o )
	{
		if( o instanceof ShipPartCategory )
		{
			return this.compareTo( (ShipPartCategory)o ) == 0;
		}
		return super.equals( o );
	}

	@Override
	public String toString()
	{
		return this.id;
	}

}
