package com.kprojekt.alonespace.data.model;

import android.graphics.drawable.Drawable;

/**
 * @author Krzysiek Bobnis
 * @since 03:39:55 06-01-2013
 */
public class ActionTemplate
{
	protected final String id;
	protected final String name;
	protected final String desc;
	protected final Drawable positiveImage;
	protected final Drawable negativeImage;

	public ActionTemplate( String id, String name, String desc, Drawable positiveImage, Drawable negativeImage )
	{
		this.id = id;
		this.name = name;
		this.desc = desc;
		this.positiveImage = positiveImage;
		this.negativeImage = negativeImage;
	}
}
