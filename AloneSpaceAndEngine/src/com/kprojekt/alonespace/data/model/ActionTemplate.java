package com.kprojekt.alonespace.data.model;

import android.graphics.drawable.Drawable;

/**
 * @author Krzysiek Bobnis
 * @since 03:39:55 06-01-2013
 */
public class ActionTemplate
{
	protected final String id;
	protected transient final Drawable imgPos;
	protected transient final String namePos;
	protected transient final String descPos;

	public ActionTemplate( String id, Drawable imgPos, String namePos, String descPos )
	{
		this.id = id;
		this.imgPos = imgPos;
		this.namePos = namePos;
		this.descPos = descPos;
	}
}
