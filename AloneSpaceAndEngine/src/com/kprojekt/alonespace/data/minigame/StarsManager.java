package com.kprojekt.alonespace.data.minigame;

import org.andengine.entity.scene.Scene;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

/**
 * 
 */
public class StarsManager extends Scene
{

	private BackgroundStarsManager backgroundStarsManager;
	private ForegroundStarsManager foregroundStarsManager;

	public StarsManager( SectorData sectorData, final VertexBufferObjectManager vertexBufferObjectManager,
			TextureRegion starTextureRegion, TextureRegion asterTextureRegion )
	{
		this.backgroundStarsManager = new BackgroundStarsManager( starTextureRegion, vertexBufferObjectManager );
		this.attachChild( this.backgroundStarsManager );
		this.foregroundStarsManager = new ForegroundStarsManager( asterTextureRegion, vertexBufferObjectManager );
		this.attachChild( this.foregroundStarsManager );

	}

	public void cameraBounds( float centerX, float centerY, float width, float height )
	{
		this.backgroundStarsManager.cameraBounds( centerX, centerY, width, height );
		this.foregroundStarsManager.cameraBounds( centerX, centerY, width, height );

	}
}
