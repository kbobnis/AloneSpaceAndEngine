package com.kprojekt.alonespace.activities.android;

/**
 * @author Krzysiek Bobnis
 * @since 04:01:14 06-01-2013
 */
public class Action extends ActionTemplate
{
	private final int value;

	public Action( ActionTemplate template, int value )
	{
		super( template.id, template.name, template.desc, template.positiveImage, template.negativeImage );
		this.value = value;
	}
}
