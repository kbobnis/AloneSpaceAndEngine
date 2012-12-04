package com.kprojekt.alonespace.data.minigame;

import org.andengine.entity.scene.Scene;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

/**
 * 
 */
public class ForegroundStarsManager extends Scene
{
	private final TextureRegion asterTextureRegion;
	private final VertexBufferObjectManager vertexBufferObjectManager;

	public ForegroundStarsManager( TextureRegion asterTextureRegion, VertexBufferObjectManager vertexBufferObjectManager )
	{
		this.asterTextureRegion = asterTextureRegion;
		this.vertexBufferObjectManager = vertexBufferObjectManager;
		this.setBackgroundEnabled( false );

	}

	public void cameraBounds( float centerX, float centerY, float width, float height )
	{
		// TODO @Krzysiek Auto-generated method stub

	}
}
