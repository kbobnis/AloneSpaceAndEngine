package com.kprojekt.alonespace.data.chooseSector;

import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

/**
 * 
 */
public class ShipWhenChoosingSector extends Sprite
{

	public ShipWhenChoosingSector( int i, int j, TextureRegion shipTextureRegion, VertexBufferObjectManager manager,
			float scale )
	{
		super( i, j, shipTextureRegion, manager );
		setScale( scale );
		setRotation( 90f );

	}

	protected static final int STARTING = 0;
	public int sectorX = 0;
	public int sectorY = 0;

	public void placeInMiddleOf( Sector sector )
	{
		setX( sector.getOffsetX() - getWidth() / 2 + sector.getWidth() / 2 );
		setY( sector.getOffsetY() - getHeight() / 2 + sector.getHeight() / 2 );
		setScale( sector.getHeight() / this.getWidth() );
	}

}
