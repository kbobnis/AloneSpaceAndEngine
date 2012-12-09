package com.kprojekt.alonespace.data.minigame;

import org.andengine.engine.camera.ZoomCamera;
import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.entity.Entity;
import org.andengine.input.touch.detector.ScrollDetector;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.color.Color;

import com.kprojekt.alonespace.data.Core;
import com.kprojekt.alonespace.data.chooseSector.Star;

/**
 * 
 */
public class StarsManager extends Entity
{
	private final ZoomCamera camera;
	private final float scrollFactor;

	public StarsManager( TextureRegion starTextureRegion,
			VertexBufferObjectManager vertexBufferObjectManager, ZoomCamera camera, Color color, float scrollFactor,
			float zoom )
	{
		this.camera = camera;
		this.scrollFactor = 1 - scrollFactor;
		for( int i = 0; i < 102; i++ )
		{
			Star star2 = new Star( starTextureRegion, vertexBufferObjectManager );
			float x = camera.getXMin() + Core.random.nextInt( (int)(camera.getXMax() * 2 - camera.getXMin()) );
			float y = camera.getYMin() + Core.random.nextInt( (int)(camera.getYMax() * 2 - camera.getYMin()) );
			star2.setLocation( x, y );
			star2.setScale( zoom );
			star2.setColor( color );
			this.attachChild( star2 );
		}
	}

	@Override
	protected void onManagedUpdate( float pSecondsElapsed )
	{
		this.setX( this.camera.getXMin() * scrollFactor );
		this.setY( this.camera.getYMin() * scrollFactor );
		super.onManagedUpdate( pSecondsElapsed );
	}
}
