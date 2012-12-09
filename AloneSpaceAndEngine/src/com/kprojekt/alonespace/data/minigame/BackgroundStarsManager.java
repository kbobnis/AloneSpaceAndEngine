package com.kprojekt.alonespace.data.minigame;

import org.andengine.engine.camera.ZoomCamera;
import org.andengine.entity.Entity;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.kprojekt.alonespace.data.Core;
import com.kprojekt.alonespace.data.chooseSector.Star;

/**
 * 
 */
public class BackgroundStarsManager extends Entity
{
	private final VertexBufferObjectManager vertexBufferObjectManager;
	private Sprite star;
	private final ZoomCamera camera;

	public BackgroundStarsManager( TextureRegion starTextureRegion,
			VertexBufferObjectManager vertexBufferObjectManager, ZoomCamera camera )
	{
		this.vertexBufferObjectManager = vertexBufferObjectManager;
		this.camera = camera;

		for( int i = 0; i < 100; i++ )
		{
			Star star2 = new Star( starTextureRegion, vertexBufferObjectManager );
			float x = camera.getXMin() + Core.random.nextInt( (int)(camera.getXMax() * 2 - camera.getXMin()) );
			float y = camera.getYMin() + Core.random.nextInt( (int)(camera.getYMax() * 2 - camera.getYMin()) );
			star2.setLocation( x, y );
			star2.setScale( Core.random.nextFloat() );
			this.attachChild( star2 );
		}

	}
}
