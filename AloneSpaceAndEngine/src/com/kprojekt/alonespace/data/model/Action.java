package com.kprojekt.alonespace.data.model;

/**
 * @author Krzysiek Bobnis
 * @since 04:01:14 06-01-2013
 */
public class Action extends ActionTemplate
{
	private final float value;

	public Action( ActionTemplate template, float value )
	{
		super( template.type, template.imgPos, template.namePos, template.descPos );
		this.value = value;
	}

	@Override
	public String toString()
	{
		return this.type + ": " + this.value;
	}

	public float getValue()
	{
		return this.value;
	}

}
