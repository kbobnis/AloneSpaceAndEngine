package com.kprojekt.alonespace.data.model;

import android.graphics.drawable.Drawable;

/**
 * @author Krzysiek Bobnis
 * @since 03:39:55 06-01-2013
 */
public class ActionTemplate
{
	protected final String id;
	protected final String nameNeg;
	protected final String descNeg;
	protected final Drawable imgPos;
	protected final Drawable imgNeg;
	protected final String namePos;
	protected final String descPos;

	public ActionTemplate( String id, String nameNeg, String descNeg, Drawable imgPos, Drawable imgNeg,
			String namePos, String descPos )
	{
		this.id = id;
		this.nameNeg = nameNeg;
		this.descNeg = descNeg;
		this.imgPos = imgPos;
		this.imgNeg = imgNeg;
		this.namePos = namePos;
		this.descPos = descPos;
	}
}
