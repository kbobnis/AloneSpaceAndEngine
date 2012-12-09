package com.kprojekt.alonespace.data.chooseSector;

import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.kprojekt.alonespace.data.Core;

/**
 * 
 */
public class Star extends Sprite
{
	private static float chance = 0.1f;

	public Star( TextureRegion textureRegion, VertexBufferObjectManager manager )
	{
		super( 0, 0, textureRegion, manager );
	}

	public static boolean willBeBorn()
	{
		return Core.random.nextFloat() < Star.chance;
	}

	public float getRotationSpeed()
	{
		return 0; //Core.random.nextFloat() * 100;
	}

	public float getBlinkSpeed()
	{
		return Core.random.nextFloat() * 0.5f;
	}

	public void setLocation( float x, float y )
	{
		this.setX( x );
		this.setY( y );
	}

}
