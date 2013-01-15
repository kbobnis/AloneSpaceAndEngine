package com.kprojekt.alonespace.data.model;

import java.util.Collection;

import android.graphics.drawable.Drawable;

/**
 * @author Krzysiek Bobnis
 * @since 03:55:16 06-01-2013
 */
public class ShipPart
{
	private final String id;
	private final transient String name;
	private final transient String desc;
	private final transient Drawable img;
	private final Collection<Action> actions;
	private final ShipPartCategory category;

	public ShipPart( String id, String name, String desc, Drawable img, Collection<Action> actions,
			ShipPartCategory category )
	{
		this.id = id;
		this.name = name;
		this.desc = desc;
		this.img = img;
		this.actions = actions;
		this.category = category;
	}

	public String getId()
	{
		return this.id;
	}

	public ShipPartCategory getCategory()
	{
		return this.category;
	}

}
