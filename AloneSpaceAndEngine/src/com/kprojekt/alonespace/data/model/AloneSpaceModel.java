package com.kprojekt.alonespace.data.model;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.andengine.util.adt.list.SmartList;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import android.content.res.AssetManager;

import com.kprojekt.alonespace.utils.AssetHelper;
import com.kprojekt.alonespace.utils.XMLHelper;

/**
 * @author Krzysiek Bobnis
 * @since 03:00:11 06-01-2013
 */
public class AloneSpaceModel
{
	private List<ShipPart> shipParts;

	public AloneSpaceModel( InputStream open, AssetManager assetManager )
	{
		this( AloneSpaceModelParser.parse( open, assetManager ) );
	}

	public AloneSpaceModel( List<ShipPart> shipParts )
	{
		this.shipParts = shipParts;
	}

	private static class AloneSpaceModelParser
	{

		public static List<ShipPart> parse( InputStream is, AssetManager assetManager )
		{
			DocumentBuilder docBuilder;
			Document doc;
			try
			{
				docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
				doc = docBuilder.parse( is, null );
			}
			catch( Exception e )
			{
				throw new RuntimeException( e );
			}

			Node model = doc.getElementsByTagName( "alonespace-model" ).item( 0 );

			Node tagActions = XMLHelper.getChildrenOfName( model, "actions" ).get( 0 );
			HashMap<String, ActionTemplate> actions = AloneSpaceModelParser.parseActions( tagActions, assetManager );

			Node tagShipPart = XMLHelper.getChildrenOfName( model, "ship-parts" ).get( 0 );
			return AloneSpaceModelParser.parseShipParts( tagShipPart, assetManager, actions );
		}

		private static List<ShipPart> parseShipParts( Node tagShipPart, AssetManager assetManager,
				HashMap<String, ActionTemplate> actions )
		{
			Node dShipPart = XMLHelper.getChildrenOfName( tagShipPart, "default-ship-part" ).get( 0 );
			String dName = XMLHelper.getAttributeOfName( dShipPart, "name" );
			String dDesc = XMLHelper.getAttributeOfName( dShipPart, "desc" );
			String dImg = XMLHelper.getAttributeOfName( dShipPart, "img" );

			HashMap<String, Action> dActionList = new HashMap<String, Action>();
			for( Node dAction : XMLHelper.getChildrenOfName( dShipPart, "action" ) )
			{
				String id = XMLHelper.getAttributeOfName( dAction, "id" );
				int value = Integer.parseInt( XMLHelper.getAttributeOfName( dAction, "value" ) );
				dActionList.put( id, new Action( actions.get( id ), value ) );
			}

			List<ShipPart> shipParts = new SmartList<ShipPart>();
			for( Node tmp : XMLHelper.getChildrenOfName( tagShipPart, "ship-part" ) )
			{
				String id = XMLHelper.getAttributeOfName( tmp, "id" );
				HashMap<String, Action> shipPartActions = new HashMap<String, Action>();
				shipPartActions.putAll( dActionList );
				for( Node tmpAction : XMLHelper.getChildrenOfName( tmp, "action" ) )
				{
					String tmpActionId = XMLHelper.getAttributeOfName( tmpAction, "id" );
					ActionTemplate actionTemplate = actions.get( tmpActionId );
					if( actionTemplate == null )
					{
						throw new RuntimeException( "Action of id " + tmpActionId + " declared in ship-part " + id
								+ " has wrong id. Please add this id to actions tag" );
					}
					int value = Integer.parseInt( XMLHelper.getAttributeOfName( tmpAction, "value" ) );
					if( shipPartActions.containsKey( tmpActionId ) )
					{
						shipPartActions.remove( tmpActionId );
					}
					shipPartActions.put( tmpActionId, new Action( actionTemplate, value ) );
				}

				String name = XMLHelper.getAttributeOfName( tmp, "name" );
				if( name == null )
				{
					name = dName;
				}
				String desc = XMLHelper.getAttributeOfName( tmp, "desc" );
				if( desc == null )
				{
					desc = dDesc;
				}
				String img = XMLHelper.getAttributeOfName( tmp, "img" );
				if( img == null )
				{
					img = dImg;
				}

				shipParts.add( new ShipPart( id, name.replace( "{ship-part-id}", id ), desc.replace( "{ship-part-id}",
						id ), AssetHelper.loadImage( img.replace( "{ship-part-id}", id ), assetManager ),
						shipPartActions.values() ) );
			}
			return shipParts;
		}

		private static HashMap<String, ActionTemplate> parseActions( Node tagActions, AssetManager assetManager )
		{
			Node defaultAction = XMLHelper.getChildrenOfName( tagActions, "default-action" ).get( 0 );
			String dPositiveImg = XMLHelper.getAttributeOfName( defaultAction, "positive-img" );
			String dNegativeImg = XMLHelper.getAttributeOfName( defaultAction, "negative-img" );
			String dName = XMLHelper.getAttributeOfName( defaultAction, "name" );
			String dDesc = XMLHelper.getAttributeOfName( defaultAction, "desc" );

			List<Node> actions = XMLHelper.getChildrenOfName( tagActions, "action" );

			HashMap<String, ActionTemplate> actionList = new HashMap<String, ActionTemplate>();
			for( Node action : actions )
			{
				String id = XMLHelper.getAttributeOfName( action, "id" );
				String positiveImg = XMLHelper.getAttributeOfName( action, "positive-img" );
				if( positiveImg == null )
				{
					positiveImg = dPositiveImg;
				}
				String negativeImg = XMLHelper.getAttributeOfName( action, "negative-img" );
				if( negativeImg == null )
				{
					negativeImg = dNegativeImg;
				}
				String name = XMLHelper.getAttributeOfName( action, "name" );
				if( name == null )
				{
					name = dName;
				}
				String desc = XMLHelper.getAttributeOfName( action, "desc" );
				if( desc == null )
				{
					desc = dDesc;
				}
				actionList.put( id,
						new ActionTemplate( id, name.replace( "{action-id}", id ), desc.replace( "{action-id}", id ),
								AssetHelper.loadImage( positiveImg.replace( "{action-id}", id ), assetManager ),
								AssetHelper.loadImage( negativeImg.replace( "{action-id}", id ), assetManager ) ) );
			}
			return actionList;
		}
	}
}
