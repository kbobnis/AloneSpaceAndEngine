package com.kprojekt.alonespace.data.chooseSector;

import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

/**
 * 
 */
public class StarInSector extends Sprite
{

	public StarInSector( float x, float y, TextureRegion textureRegion, VertexBufferObjectManager manager,
			float rotationSpeed, float blinkSpeed )
	{
		super( x, y, textureRegion, manager );
	}

	public void animate( float pSecondsElapsed )
	{
		// TODO @Krzysiek Auto-generated method stub

	}

}
