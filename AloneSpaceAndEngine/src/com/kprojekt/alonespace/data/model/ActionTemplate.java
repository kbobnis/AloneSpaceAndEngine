package com.kprojekt.alonespace.data.model;

import android.graphics.drawable.Drawable;

/**
 * @author Krzysiek Bobnis
 * @since 03:39:55 06-01-2013
 */
public class ActionTemplate
{
	protected transient Drawable imgPos;
	protected transient String namePos;
	protected transient String descPos;
	protected Type type;

	public enum Type
	{
		rotate, deal_dmg, decelerate, accelerate, resist_dmg, scan_radius, more_space, repair, hit_points, oil_tank
	}

	public ActionTemplate( ActionTemplate.Type type, Drawable imgPos, String namePos, String descPos )
	{
		this.type = type;
		this.imgPos = imgPos;
		this.namePos = namePos;
		this.descPos = descPos;
	}

	public Type getType()
	{
		return this.type;
	}

	public void fillBlanks( AloneSpaceModel model )
	{
		ActionTemplate actionTemplate = model.getActionTemplate( this.type );
		this.imgPos = actionTemplate.imgPos;
		this.namePos = actionTemplate.namePos;
		this.descPos = actionTemplate.descPos;
	}

}
