package com.kprojekt.alonespace.data.model;

import java.util.HashMap;

import android.graphics.drawable.Drawable;

/**
 * @author Krzysiek Bobnis
 * @since 16:13:13 06-01-2013
 */
public class ShipTemplate
{

	private final String id;
	private final String name;
	private final String desc;
	private final Drawable img;
	private final HashMap<String, ShipPart> parts;

	public ShipTemplate( String id, String name, String desc, Drawable img, HashMap<String, ShipPart> parts )
	{
		this.id = id;
		this.name = name;
		this.desc = desc;
		this.img = img;
		this.parts = parts;
	}

}
