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
		super( template.id, template.nameNeg, template.descNeg, template.imgPos, template.imgNeg,
				template.namePos, template.descPos );
		this.value = value;
	}
}
