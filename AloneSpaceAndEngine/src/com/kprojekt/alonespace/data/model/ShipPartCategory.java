package com.kprojekt.alonespace.data.model;

import android.graphics.drawable.Drawable;

/**
 * @author Krzysiek Bobnis
 * @since 15:31:55 06-01-2013
 */
public class ShipPartCategory
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

	public void fillBlanks( ShipPartCategory category )
	{
		this.name = category.name;
		this.desc = category.desc;
		this.img = category.img;
		this.obligatory = category.obligatory;
	}

}
