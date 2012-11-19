package com.kprojekt.alonespace.data.chooseSector;

import java.util.List;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.color.Color;

/**
 * @author Philon
 */
public class ShipOnSector extends Scene
{

	private final TextureRegion shipTextureRegion;
	private Sprite face;

	public ShipOnSector( TextureRegion shipTextureRegion, int sectorX, int sectorY, VertexBufferObjectManager manager,
			Camera camera, List<TextureRegion> starsTextureRegions )
	{
		this.setBackground( new Background( Color.BLACK ) );
		this.shipTextureRegion = shipTextureRegion;

		for( int i = 0; i < 3; i++ )
		{
			for( int j = 0; j < 3; j++ )
			{
				int sectorW = 100;
				int sectorH = 100;
				Sector sector = new Sector( i * sectorW, j * sectorH, sectorW, sectorH, manager, starsTextureRegions );
				this.attachChild( sector );
			}
		}

		face = new Sprite( 100, 100, this.shipTextureRegion, manager );
		face.setRotation( 90f );
		this.attachChild( face );
	}

	public void onUpdateHandle( float pSecondsElapsed )
	{
		this.face.setRotation( this.face.getRotation() + 1 );
	}
}
