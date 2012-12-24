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

	public StarsManager( SmartList<TextureRegion> starRegions, VertexBufferObjectManager vertexBufferObjectManager,
			ZoomCamera camera, float scrollFactor, float zoom, int count, Color blue )
	{
		this.camera = camera;
		this.scrollFactor = scrollFactor;

		for( int i = 0; i < 3; i += 2 )
		{
			StarsSector declareSector = declareSector( starRegions, vertexBufferObjectManager, camera, zoom, count,
					blue, i, 0 );
			starSectors.add( declareSector );
			this.attachChild( declareSector.getEntity() );
		}
	}

	private StarsSector declareSector( SmartList<TextureRegion> starRegions, VertexBufferObjectManager mgr,
			ZoomCamera camera, float zoom, int count, Color blue, int x, int y )
	{
		Entity sector = new Entity( x * camera.getWidth(), y * camera.getHeight() );
		for( int i = 0; i < count; i++ )
		{
			int nextInt = Core.random.nextInt( starRegions.size() );
			TextureRegion textureRegion = starRegions.get( nextInt );
			Star star2 = new Star( textureRegion, mgr );
			float _x = camera.getXMin() + Core.random.nextInt( (int)(camera.getXMax() - camera.getXMin()) );
			float _y = camera.getYMin() + Core.random.nextInt( (int)(camera.getYMax() - camera.getYMin()) );
			star2.setLocation( _x, _y );
			star2.setScale( zoom );
			star2.setColor( blue );
			sector.attachChild( star2 );
		}
		Line top = new Line( 0, 0, camera.getWidth(), 0, mgr );
		Line bottom = new Line( 0, camera.getHeight(), camera.getWidth(), camera.getHeight(), mgr );
		Line left = new Line( 0, 0, 0, camera.getHeight(), mgr );
		Line right = new Line( camera.getWidth(), 0, camera.getWidth(), camera.getHeight(), mgr );
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
		float sectorX = this.getSectorX( this.camera.getXMin() + this.camera.getWidth() / 2 );
		float sectorY = this.getSectorY( this.camera.getYMin() + this.camera.getWidth() / 2 );

		for( StarsSector sector : this.starSectors )
		{
			sector.getEntity().setX( sector.getPos().x * this.camera.getWidth() + this.camera.getXMin() * scrollFactor );
			sector.getEntity().setY( sector.getPos().y * this.camera.getHeight() + this.camera.getYMin() * scrollFactor );
		}

		this.camera.onUpdate( pSecondsElapsed );
	}

	private float getSectorY( float posY )
	{
		return (posY - camera.getYMin() * scrollFactor) / camera.getHeight();
	}

	private float getSectorX( float posX )
	{
		return (posX - camera.getXMin() * scrollFactor) / camera.getWidth();
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
