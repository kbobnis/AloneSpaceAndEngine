package com.kprojekt.alonespace.data.model;

import java.util.Collection;

import org.andengine.opengl.texture.region.TextureRegion;

import com.kprojekt.alonespace.data.model.ActionTemplate.Type;

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
	private transient TextureRegion imgRegion;
	private final Collection<Action> actions;
	private final ShipPartCategory category;
	private transient String imgPath;

	public ShipPart( String id, String name, String desc, Drawable img, Collection<Action> actions,
			ShipPartCategory category, String imgPath )
	{
		this.id = id;
		this.name = name;
		this.desc = desc;
		this.img = img;
		this.actions = actions;
		this.category = category;
		this.imgPath = imgPath;
	}

	public void fillBlanks( AloneSpaceModel model )
	{
		ShipPart part = model.getPart( this.getId(), this.getCategory().getId() );
		this.name = part.name;
		this.desc = part.desc;
		this.img = part.img;
		this.imgPath = part.imgPath;
		for( Action action : this.actions )
		{
			action.fillBlanks( model );
		}
		this.category.fillBlanks( model );
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

	public TextureRegion getTextureRegion()
	{
		return this.imgRegion;
	}

	public String getImgPath()
	{
		return this.imgPath;
	}

	public void setTextureImg( TextureRegion textureRegion )
	{
		this.imgRegion = textureRegion;
	}

	public float getActionValue( ActionTemplate.Type type )
	{
		float val = 0;
		for( Action action : this.actions )
		{
			if( action.getType() == type )
			{
				val += action.getValue();
			}
		}
		return val;
	}
}
