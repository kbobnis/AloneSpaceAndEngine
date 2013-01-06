package com.kprojekt.alonespace.data.model;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

import android.content.res.AssetManager;

import com.kprojekt.alonespace.utils.AssetHelper;
import com.kprojekt.alonespace.utils.Utils;
import com.kprojekt.alonespace.utils.XMLHelper;

/**
 * @author Krzysiek Bobnis
 * @since 03:00:11 06-01-2013
 */
public class AloneSpaceModel
{
	private Map<String, ShipPartCategory> shipParts;
	private Map<String, ShipTemplate> ships;
	private ShipTemplate startingShip;

	public AloneSpaceModel( InputStream open, AssetManager assetManager )
	{
		Node model = AloneSpaceModelParser.getNodeFromInputStream( open, "alonespace-model" );
		this.shipParts = AloneSpaceModelParser.parseShipCategories( model, assetManager );

		Node ships = XMLHelper.getChildrenOfName( model, "ships" ).get( 0 );
		this.ships = AloneSpaceModelParser.parseShips( ships, this.shipParts, assetManager );
		this.startingShip = this.ships.get( XMLHelper.getAttrOfName( ships, "starting" ) );
	}

	private static class AloneSpaceModelParser
	{

		private static Node getNodeFromInputStream( InputStream is, String nodeName )
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

			return doc.getElementsByTagName( nodeName ).item( 0 );
		}

		public static HashMap<String, ShipTemplate> parseShips( Node tagShips, Map<String, ShipPartCategory> shipParts, AssetManager assetManager )
		{
			Node dShip = XMLHelper.getChildrenOfName( tagShips, "default-ship" ).get( 0 );
			String dName = XMLHelper.getAttrOfName( dShip, "name" );
			String dDesc = XMLHelper.getAttrOfName( dShip, "desc" );
			String dImg = XMLHelper.getAttrOfName( dShip, "img" );

			HashMap<String, ShipTemplate> ships = new HashMap<String, ShipTemplate>();
			for( Node tmp : XMLHelper.getChildrenOfName( tagShips, "ship" ) )
			{
				String id = XMLHelper.getAttrOfName( tmp, "id" );
				String name = Utils.safeGet( XMLHelper.getAttrOfName( tmp, "name" ), dName );
				String desc = Utils.safeGet( XMLHelper.getAttrOfName( tmp, "desc" ), dDesc );
				String img = Utils.safeGet( XMLHelper.getAttrOfName( tmp, "img" ), dImg );

				HashMap<String, ShipPart> parts = new HashMap<String, ShipPart>();
				for( Node tagPart : XMLHelper.getChildrenOfName( tmp, "part" ) )
				{
					String partId = XMLHelper.getAttrOfName( tagPart, "id" );
					String partCategoryId = XMLHelper.getAttrOfName( tagPart, "category-id" );
					parts.put( partCategoryId, shipParts.get( partCategoryId ).getShipParts().get( partId ) );
				}
				ships.put( id, new ShipTemplate( id, name.replace( "{id}", id ), desc.replace( "{id}", id ),
						AssetHelper.loadImage( img, assetManager ), parts ) );
			}

			return ships;
		}

		public static Map<String, ShipPartCategory> parseShipCategories( Node model, AssetManager assetManager )
		{

			Node tagActions = XMLHelper.getChildrenOfName( model, "action-templates" ).get( 0 );
			HashMap<String, ActionTemplate> actions = AloneSpaceModelParser.parseActionTemplates( tagActions,
					assetManager );

			Node tagShipPart = XMLHelper.getChildrenOfName( model, "ship-part-categories" ).get( 0 );
			Map<String, ShipPartCategory> shipPartCategories = AloneSpaceModelParser.parseShipPartCategories(
					tagShipPart, assetManager, actions );

			return shipPartCategories;

		}

		private static Map<String, ShipPartCategory> parseShipPartCategories( Node tagShipPartCategory, AssetManager assetManager, HashMap<String, ActionTemplate> actions )
		{
			Node dShipPartCat = XMLHelper.getChildrenOfName( tagShipPartCategory, "default-ship-part-category" ).get( 0 );
			String dCatName = XMLHelper.getAttrOfName( dShipPartCat, "name" );
			String dCatDesc = XMLHelper.getAttrOfName( dShipPartCat, "desc" );
			String dCatImg = XMLHelper.getAttrOfName( dShipPartCat, "img" );

			Node dShipPart = XMLHelper.getChildrenOfName( dShipPartCat, "default-ship-part" ).get( 0 );
			String dName = XMLHelper.getAttrOfName( dShipPart, "name" );
			String dDesc = XMLHelper.getAttrOfName( dShipPart, "desc" );
			String dImg = XMLHelper.getAttrOfName( dShipPart, "img" );

			HashMap<String, Action> dActionList = new HashMap<String, Action>();
			dActionList.putAll( AloneSpaceModelParser.parseActions( XMLHelper.getChildrenOfName( dShipPart, "action" ),
					actions ) );

			Map<String, ShipPartCategory> shipPartCategories = new HashMap<String, ShipPartCategory>();
			for( Node shipPartCategoryTmp : XMLHelper.getChildrenOfName( tagShipPartCategory, "ship-part-category" ) )
			{
				String id = XMLHelper.getAttrOfName( shipPartCategoryTmp, "id" );
				String name = Utils.safeGet( XMLHelper.getAttrOfName( shipPartCategoryTmp, "name" ), dCatName );
				String desc = Utils.safeGet( XMLHelper.getAttrOfName( shipPartCategoryTmp, "desc" ), dCatDesc );
				String img = Utils.safeGet( XMLHelper.getAttrOfName( shipPartCategoryTmp, "img" ), dCatImg );

				Map<String, ShipPart> shipParts = AloneSpaceModelParser.parseShipParts( XMLHelper.getChildrenOfName(
						shipPartCategoryTmp, "ship-part" ), dName, dDesc, dImg, dActionList, actions, assetManager );

				shipPartCategories.put( id, new ShipPartCategory( id, name.replace( "{id}", id ), desc.replace( "{id}",
						id ), AssetHelper.loadImage( img, assetManager ), shipParts ) );
			}

			return shipPartCategories;
		}

		private static Map<String, ShipPart> parseShipParts( List<Node> tagShipParts, String dName, String dDesc, String dImg, HashMap<String, Action> dActionList, HashMap<String, ActionTemplate> actions, AssetManager assetManager )
		{
			Map<String, ShipPart> shipParts = new HashMap<String, ShipPart>();
			for( Node tmp : tagShipParts )
			{
				String id = XMLHelper.getAttrOfName( tmp, "id" );

				HashMap<String, Action> shipPartActions = new HashMap<String, Action>();
				shipPartActions.putAll( AloneSpaceModelParser.parseActions(
						XMLHelper.getChildrenOfName( tmp, "action" ), actions ) );

				Set<String> keySet = dActionList.keySet();
				for( String tmpActionId : keySet )
				{
					if( !shipPartActions.containsKey( tmpActionId ) )
					{
						shipPartActions.put( tmpActionId, dActionList.get( tmpActionId ) );
					}
				}

				String name = XMLHelper.getAttrOfName( tmp, "name" );
				if( name == null )
				{
					name = dName;
				}
				String desc = XMLHelper.getAttrOfName( tmp, "desc" );
				if( desc == null )
				{
					desc = dDesc;
				}
				String img = XMLHelper.getAttrOfName( tmp, "img" );
				if( img == null )
				{
					img = dImg;
				}

				shipParts.put( id, new ShipPart( id, name.replace( "{id}", id ), desc.replace( "{id}", id ),
						AssetHelper.loadImage( img.replace( "{id}", id ), assetManager ), shipPartActions.values() ) );

			}
			return shipParts;
		}

		private static Map<String, Action> parseActions( List<Node> tagActions, HashMap<String, ActionTemplate> actionTemplates )
		{
			Map<String, Action> res = new HashMap<String, Action>();
			for( Node dAction : tagActions )
			{
				String id = XMLHelper.getAttrOfName( dAction, "id" );
				if( actionTemplates.get( id ) == null )
				{
					throw new RuntimeException( "Action of id " + id
							+ " has wrong id. Please add this id to actions tag" );
				}
				int value = Integer.parseInt( XMLHelper.getAttrOfName( dAction, "value" ) );
				res.put( id, new Action( actionTemplates.get( id ), value ) );
			}
			return res;
		}

		private static HashMap<String, ActionTemplate> parseActionTemplates( Node tagActions, AssetManager assetManager )
		{
			Node defaultAction = XMLHelper.getChildrenOfName( tagActions, "default-action-template" ).get( 0 );
			String dImgPositive = XMLHelper.getAttrOfName( defaultAction, "img-positive" );
			String dImgNegative = XMLHelper.getAttrOfName( defaultAction, "img-negative" );
			String dNamePositive = XMLHelper.getAttrOfName( defaultAction, "name-positive" );
			String dNameNegative = XMLHelper.getAttrOfName( defaultAction, "name-negative" );
			String dDescNegative = XMLHelper.getAttrOfName( defaultAction, "desc-negative" );
			String dDescPositive = XMLHelper.getAttrOfName( defaultAction, "desc-positive" );

			List<Node> actions = XMLHelper.getChildrenOfName( tagActions, "action-template" );

			HashMap<String, ActionTemplate> actionList = new HashMap<String, ActionTemplate>();
			for( Node action : actions )
			{
				String id = XMLHelper.getAttrOfName( action, "id" );
				String imgPos = Utils.safeGet( XMLHelper.getAttrOfName( action, "img-positive" ), dImgPositive ).replace(
						"{id}", id );
				String imgNeg = Utils.safeGet( XMLHelper.getAttrOfName( action, "negative-img" ), dImgNegative ).replace(
						"{id}", id );
				String namePos = Utils.safeGet( XMLHelper.getAttrOfName( action, "name-positive" ), dNamePositive ).replace(
						"{id}", id );
				String nameNeg = Utils.safeGet( XMLHelper.getAttrOfName( action, "name-negative" ), dNameNegative ).replace(
						"{id}", id );
				String descNeg = Utils.safeGet( XMLHelper.getAttrOfName( action, "desc-negative" ), dDescNegative ).replace(
						"{id}", id );
				String descPos = Utils.safeGet( XMLHelper.getAttrOfName( action, "desc-positive" ), dDescPositive ).replace(
						"{id}", id );
				actionList.put( id, new ActionTemplate( id, nameNeg, descNeg, AssetHelper.loadImage( imgPos,
						assetManager ), AssetHelper.loadImage( imgNeg, assetManager ), namePos, descPos ) );
			}
			return actionList;
		}
	}
}
