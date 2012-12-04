package com.kprojekt.alonespace.data.minigame;

import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

/**
 * 
 */
public class BackgroundStarsManager extends Scene
{
	private final TextureRegion starTextureRegion;
	private final VertexBufferObjectManager vertexBufferObjectManager;
	private Sprite star;

	public BackgroundStarsManager( TextureRegion starTextureRegion, VertexBufferObjectManager vertexBufferObjectManager )
	{
		this.starTextureRegion = starTextureRegion;
		this.vertexBufferObjectManager = vertexBufferObjectManager;
		this.setBackgroundEnabled( false );

		this.star = new Sprite( 0, 0, starTextureRegion, vertexBufferObjectManager );
		this.attachChild( star );
	}

	public void cameraBounds( float centerX, float centerY, float width, float height )
	{
	}

}
