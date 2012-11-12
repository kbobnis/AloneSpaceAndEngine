package com.kprojekt.alonespace.activities.andengine;

import org.andengine.opengl.texture.region.TextureRegion;

/**
 * 
 */
public class ShipOnSector
{

	private final TextureRegion shipTextureRegion;
	private final int sectorX;
	private final int sectorY;

	public ShipOnSector( TextureRegion shipTextureRegion, int sectorX, int sectorY )
	{
		this.shipTextureRegion = shipTextureRegion;
		this.sectorX = sectorX;
		this.sectorY = sectorY;

	}

}
