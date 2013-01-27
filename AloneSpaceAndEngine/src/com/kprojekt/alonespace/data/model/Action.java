package com.kprojekt.alonespace.data.model;

/**
 * @author Krzysiek Bobnis
 * @since 04:01:14 06-01-2013
 */
public class Action extends ActionTemplate
{
	private final int value;

	public Action( ActionTemplate template, int value )
	{
		super( template.id, template.imgPos, template.namePos, template.descPos );
		this.value = value;
	}

	public String getId()
	{
		return this.id;
	}

	@Override
	public String toString()
	{
		return this.id + ": " + this.value;
	}

}
