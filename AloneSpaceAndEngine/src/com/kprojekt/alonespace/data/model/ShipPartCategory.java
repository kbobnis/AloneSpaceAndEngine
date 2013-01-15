package com.kprojekt.alonespace.data.model;

import java.util.List;
import java.util.Map;

import android.graphics.drawable.Drawable;

/**
 * @author Krzysiek Bobnis
 * @since 15:31:55 06-01-2013
 */
public class ShipPartCategory
{
	private final String id;
	private final transient String name;
	private final transient String desc;
	private final transient Drawable img;
	private final transient boolean obligatory;

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

}
