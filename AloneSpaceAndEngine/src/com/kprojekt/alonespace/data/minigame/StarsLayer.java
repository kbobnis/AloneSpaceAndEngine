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

import com.kprojekt.alonespace.data.Core;
import com.kprojekt.alonespace.data.chooseSector.Star;
import com.kprojekt.alonespace.utils.Maths;

/**
 * 
 */
public class StarsLayer extends Entity
{
	private final ZoomCamera camera;
	private final float scrollFactor;
	private SmartList<StarsSector> starSectors = new SmartList<StarsSector>();
	private final int sectorW;
	private final int sectorH;
	private int lastChangedSectorX;
	private int lastChangedSectorY;

	public StarsLayer( SmartList<TextureRegion> starRegions, VertexBufferObjectManager vertexBufferObjectManager,
			ZoomCamera camera, float scrollFactor, int count, Color blue, int width, int height )
	{
		this.camera = camera;
		this.scrollFactor = scrollFactor;
		this.sectorW = width;
		this.sectorH = height;

		for( int i = -1; i < 2; i++ )
		{
			for( int j = -1; j < 2; j++ )
			{
				StarsSector declareSector = declareSector( starRegions, vertexBufferObjectManager, camera, count, blue,
						i, j, width, height );
				starSectors.add( declareSector );
				this.attachChild( declareSector.getEntity() );
			}
		}
	}

	private StarsSector declareSector( SmartList<TextureRegion> starRegions, VertexBufferObjectManager mgr,
			ZoomCamera camera, int count, Color blue, int x, int y, int width, int height )
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
			star2.setScale( 1 - this.scrollFactor );
			star2.setColor( blue );
			sector.attachChild( star2 );
		}
		if( Core.settings.starsLayerBorder )
		{
			Line top = new Line( 0, 0, width, 0, mgr );
			Line bottom = new Line( 0, height, width, height, mgr );
			Line left = new Line( 0, 0, 0, height, mgr );
			Line right = new Line( width, 0, width, height, mgr );
			sector.attachChild( top );
			sector.attachChild( bottom );
			sector.attachChild( left );
			sector.attachChild( right );
		}
		return new StarsSector( sector, x, y, mgr );
	}

	@Override
	protected void onManagedUpdate( float pSecondsElapsed )
	{
		int sectorX = (int)Math.floor( this.getSectorX( this.camera.getXMin() + this.camera.getWidth() / 2 ) );
		int sectorY = (int)Math.floor( this.getSectorY( this.camera.getYMin() + this.camera.getHeight() / 2 ) );

		if( sectorX != this.lastChangedSectorX || sectorY != this.lastChangedSectorY )
			for( int i = -1; i < 2; i++ )
			{
				for( int j = -1; j < 2; j++ )
				{
					StarsSector tmp = this.getSector( sectorX + i, sectorY + j );
					if( tmp == null )
					{
						StarsSector tmp2 = this.getOutsideSector( sectorX, sectorY );
						if( tmp2 != null )
						{
							tmp2.setPosX( sectorX + i );
							tmp2.setPosY( sectorY + j );
						}
					}
				}
			}
		this.lastChangedSectorX = sectorX;
		this.lastChangedSectorY = sectorY;

		for( StarsSector sector : this.starSectors )
		{
			sector.getEntity().setX( sector.getPos().x * sectorW + this.camera.getXMin() * scrollFactor );
			sector.getEntity().setY( sector.getPos().y * sectorH + this.camera.getYMin() * scrollFactor );
		}

		this.camera.onUpdate( pSecondsElapsed );
	}

	private StarsSector getOutsideSector( int x, int y )
	{
		for( StarsSector tmp : this.starSectors )
		{
			float distX = Maths.dist( tmp.getPos().x, x );
			float distY = Maths.dist( tmp.getPos().y, y );
			if( distX > 1 || distY > 1 )
			{
				return tmp;
			}
		}
		return null;
	}

	private StarsSector getSector( int x, int y )
	{
		for( StarsSector tmp : this.starSectors )
		{
			if( tmp.getPos().x == x && tmp.getPos().y == y )
			{
				return tmp;
			}
		}
		return null;
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
	private Text text;

	public StarsSector( Entity sector, int x, int y, VertexBufferObjectManager mgr )
	{
		this.sector = sector;
		this.pos = new Point( x, y );
		text = new Text( 0, 0, Core.font, "x: " + x + ", y: " + y, 500, mgr );
		if( Core.settings.sectorText )
		{
			sector.attachChild( text );
		}
	}

	public void setPosY( int j )
	{
		this.pos.y = j;
		this.text.setText( "x: " + this.pos.x + ", y: " + this.pos.y );
	}

	public void setPosX( int i )
	{
		this.pos.x = i;
		this.text.setText( "x: " + this.pos.x + ", y: " + this.pos.y );
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
