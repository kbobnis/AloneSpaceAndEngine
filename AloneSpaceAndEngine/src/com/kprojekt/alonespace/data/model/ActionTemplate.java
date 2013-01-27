package com.kprojekt.alonespace.data.model;

import android.graphics.drawable.Drawable;

/**
 * @author Krzysiek Bobnis
 * @since 03:39:55 06-01-2013
 */
public class ActionTemplate
{
	protected final String id;
	protected transient Drawable imgPos;
	protected transient String namePos;
	protected transient String descPos;

	public ActionTemplate( String id, Drawable imgPos, String namePos, String descPos )
	{
		if( id.equals( "" ) || id.equals( " " ) )
		{
			throw new RuntimeException( "You can not ad action template with id empty " );
		}
		this.id = id;
		this.imgPos = imgPos;
		this.namePos = namePos;
		this.descPos = descPos;
	}

	public void fillBlanks( ActionTemplate action )
	{
		this.imgPos = action.imgPos;
		this.namePos = action.namePos;
		this.descPos = action.descPos;
	}
}
