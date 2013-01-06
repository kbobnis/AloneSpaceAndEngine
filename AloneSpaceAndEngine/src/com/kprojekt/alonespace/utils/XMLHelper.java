package com.kprojekt.alonespace.utils;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * @author Krzysiek Bobnis
 * since 03:19:23 06-01-2013
 */
public class XMLHelper
{
	public static String getAttributeOfName( Node node, String string )
	{
		NamedNodeMap attributes = node.getAttributes();
		Node namedItem = attributes.getNamedItem( string );
		if( namedItem == null )
		{
			return null;
		}
		return namedItem.getNodeValue();
	}

	public static List<Node> getChildrenOfName( Node node, String name )
	{
		List<Node> res = new ArrayList<Node>();
		NodeList childNodes = node.getChildNodes();
		for( int i = 0; i < childNodes.getLength(); i++ )
		{
			Node item = childNodes.item( i );

			String localName = item.getNodeName();
			if( name.equals( localName ) )
			{
				res.add( item );
			}
		}
		return res;
	}
}
