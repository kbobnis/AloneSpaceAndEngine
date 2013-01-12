package com.kprojekt.alonespace.data.model;

import java.util.Map;


import android.graphics.drawable.Drawable;

/**
 * @author Krzysiek Bobnis
 * @since 15:31:55 06-01-2013
 */
public class ShipPartCategoryTemplate
{
	private final String id;
	private final String name;
	private final String desc;
	private final Drawable img;
	private final Map<String, ShipPartTemplate> shipParts;
	private final boolean obligatory;

	public ShipPartCategoryTemplate( String id, String name, String desc, Drawable img, Map<String, ShipPartTemplate> shipParts,
			boolean obligatory )
	{
		this.id = id;
		this.name = name;
		this.desc = desc;
		this.img = img;
		this.shipParts = shipParts;
		this.obligatory = obligatory;
	}

	public Map<String, ShipPartTemplate> getShipParts()
	{
		return this.shipParts;
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
