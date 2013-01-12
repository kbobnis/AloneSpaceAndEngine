package com.kprojekt.alonespace.data.model;

import java.util.Collection;


import android.graphics.drawable.Drawable;

/**
 * @author Krzysiek Bobnis
 * @since 03:55:16 06-01-2013
 */
public class ShipPartTemplate
{

	private final String id;
	private final String name;
	private final String desc;
	private final Drawable img;
	private final Collection<Action> actions;

	public ShipPartTemplate( String id, String name, String desc, Drawable img, Collection<Action> actions )
	{
		this.id = id;
		this.name = name;
		this.desc = desc;
		this.img = img;
		this.actions = actions;
	}

}
