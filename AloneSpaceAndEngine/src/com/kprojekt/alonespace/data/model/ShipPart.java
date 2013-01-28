package com.kprojekt.alonespace.data.model;

import java.util.Collection;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

/**
 * @author Krzysiek Bobnis
 * @since 03:55:16 06-01-2013
 */
public class ShipPart
{
	private final String id;
	private transient String name;
	private transient String desc;
	private transient Drawable img;
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

	public void fillBlanks( AloneSpaceModel model )
	{
		ShipPart part = model.getPart( this.getId(), this.getCategory().getId() );
		this.name = part.name;
		this.desc = part.desc;
		this.img = part.img;
		for( Action action : this.actions )
		{
			action.fillBlanks( model );
		}
		this.category.fillBlanks( model );
	}

	private Action getAction( String id2 )
	{
		for( Action action : this.actions )
		{
			if( action.getId().equals( id2 ) )
			{
				return action;
			}
		}
		throw new RuntimeException( "There is no action " + id2 + " in " + this.category.getId() + "/" + this.id );
	}

	public String getId()
	{
		return this.id;
	}

	public ShipPartCategory getCategory()
	{
		return this.category;
	}

	public String getName()
	{
		return this.name;
	}

	@Override
	public String toString()
	{
		String actions = "";
		for( Action action : this.actions )
		{
			actions += action + ", ";
		}
		return this.category.getId() + "/" + this.getId() + "(" + actions + ")";
	}

	public Drawable getBitmap()
	{
		return this.img;
	}

}
