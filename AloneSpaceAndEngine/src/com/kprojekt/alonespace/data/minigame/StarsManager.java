package com.kprojekt.alonespace.data.minigame;

import org.andengine.engine.camera.ZoomCamera;
import org.andengine.entity.Entity;
import org.andengine.entity.IEntity;
import org.andengine.entity.primitive.Line;
import org.andengine.entity.text.Text;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.adt.list.SmartList;
import org.andengine.util.color.Color;

import android.graphics.Point;
import android.util.Log;

import com.kprojekt.alonespace.data.Core;
import com.kprojekt.alonespace.data.chooseSector.Star;

/**
 * 
 */
public class StarsManager extends Entity
{
	private final ZoomCamera camera;
	private final float scrollFactor;
	private SmartList<StarsSector> starSectors = new SmartList<StarsSector>();
	private final int sectorW;
	private final int sectorH;

	public StarsManager( SmartList<TextureRegion> starRegions, VertexBufferObjectManager vertexBufferObjectManager,
			ZoomCamera camera, float scrollFactor, float zoom, int count, Color blue, int width, int height )
	{
		this.camera = camera;
		this.scrollFactor = scrollFactor;
		this.sectorW = width;
		this.sectorH = height;

		for( int i = -1; i < 2; i++ )
		{
			for( int j = -1; j < 2; j++ )
			{
				StarsSector declareSector = declareSector( starRegions, vertexBufferObjectManager, camera, zoom, count,
						blue, i, j, width, height );
				starSectors.add( declareSector );
				this.attachChild( declareSector.getEntity() );
			}
		}
	}

	private StarsSector declareSector( SmartList<TextureRegion> starRegions, VertexBufferObjectManager mgr,
			ZoomCamera camera, float zoom, int count, Color blue, int x, int y, int width, int height )
	{
		Entity sector = new Entity( x * width, y * height );
		for( int i = 0; i < count; i++ )
		{
			int nextInt = Core.random.nextInt( starRegions.size() );
			TextureRegion textureRegion = starRegions.get( nextInt );
			Star star2 = new Star( textureRegion, mgr );
			float _x = camera.getXMin() + Core.random.nextInt( width );
			float _y = camera.getYMin() + Core.random.nextInt( height );
			star2.setLocation( _x, _y );
			star2.setScale( zoom );
			star2.setColor( blue );
			sector.attachChild( star2 );
		}
		Line top = new Line( 0, 0, width, 0, mgr );
		Line bottom = new Line( 0, height, width, height, mgr );
		Line left = new Line( 0, 0, 0, height, mgr );
		Line right = new Line( width, 0, width, height, mgr );
		sector.attachChild( top );
		sector.attachChild( bottom );
		sector.attachChild( left );
		sector.attachChild( right );
		Text text = new Text( 0, 0, Core.font, "x: " + x + ", y: " + y, mgr );
		sector.attachChild( text );
		return new StarsSector( sector, x, y );
	}

	@Override
	protected void onManagedUpdate( float pSecondsElapsed )
	{
		int sectorX = (int)Math.floor( this.getSectorX( this.camera.getXMin() + this.camera.getWidth() / 2 ) );
		int sectorY = (int)Math.floor( this.getSectorY( this.camera.getYMin() + this.camera.getHeight() / 2 ) );

		//manage sectors
		Log.d( "sector", "sector x: " + sectorX + ", sector y: " + sectorY );

		for( StarsSector sector : this.starSectors )
		{

		}

		for( StarsSector sector : this.starSectors )
		{
			sector.getEntity().setX( sector.getPos().x * sectorW + this.camera.getXMin() * scrollFactor );
			sector.getEntity().setY( sector.getPos().y * sectorH + this.camera.getYMin() * scrollFactor );
		}

		this.camera.onUpdate( pSecondsElapsed );
	}

	private float getSectorY( float posY )
	{
		return (posY - camera.getYMin() * scrollFactor) / sectorH;
	}

	private float getSectorX( float posX )
	{
		return (posX - camera.getXMin() * scrollFactor) / sectorW;
	}

}

class StarsSector
{

	private final Entity sector;
	private Point pos;

	public StarsSector( Entity sector, int x, int y )
	{
		this.sector = sector;
		this.pos = new Point( x, y );
	}

	public IEntity getEntity()
	{
		return sector;
	}

	public Point getPos()
	{
		return this.pos;
	}

}
