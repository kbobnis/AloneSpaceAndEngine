package com.kprojekt.alonespace.data.minigame;

import org.andengine.entity.Entity;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

/**
 * 
 */
public class ForegroundStarsManager extends Entity
{
	private final TextureRegion asterTextureRegion;
	private final VertexBufferObjectManager vertexBufferObjectManager;

	public ForegroundStarsManager( TextureRegion asterTextureRegion, VertexBufferObjectManager vertexBufferObjectManager )
	{
		this.asterTextureRegion = asterTextureRegion;
		this.vertexBufferObjectManager = vertexBufferObjectManager;
	}

}
