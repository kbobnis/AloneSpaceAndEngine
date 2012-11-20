package com.kprojekt.alonespace.data.chooseSector;

import org.andengine.opengl.texture.region.TextureRegion;

import com.kprojekt.alonespace.data.Core;

/**
 * 
 */
public class Star
{
	private static float chance = 0.1f;
	private TextureRegion textureRegion;

	public Star( TextureRegion textureRegion )
	{
		this.textureRegion = textureRegion;
	}

	public TextureRegion getTextureRegion()
	{
		return this.textureRegion;
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

}
