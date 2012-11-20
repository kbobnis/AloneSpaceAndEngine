package com.kprojekt.alonespace.data.chooseSector;

import java.util.ArrayList;
import java.util.List;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.color.Color;

import com.kprojekt.alonespace.data.Core;

/**
 * @author Philon
 */
public class ShipOnSector extends Scene
{

	private final TextureRegion shipTextureRegion;
	private Sprite face;
	private List<Sector> sectors = new ArrayList<Sector>();

	public ShipOnSector( TextureRegion shipTextureRegion, int sectorX, int sectorY, VertexBufferObjectManager manager,
			Camera camera, List<Star> starsTextureRegions )
	{
		this.setBackground( new Background( Color.BLACK ) );
		this.shipTextureRegion = shipTextureRegion;

		int sectorWCount = 3;
		int sectorHCount = 3;
		int sectorW = Core.width / sectorWCount;
		int sectorH = Core.height / sectorHCount;

		for( int i = 0; i < sectorWCount; i++ )
		{
			for( int j = 0; j < sectorHCount; j++ )
			{
				Sector sector = new Sector( i * sectorW, j * sectorH, sectorW, sectorH, manager, starsTextureRegions );
				this.attachChild( sector );
				this.sectors.add( sector );
			}
		}

		Sector sector = this.sectors.get( 4 );
		face = new Sprite( 0, 0, this.shipTextureRegion, manager );

		face.setScale( sector.getHeight() / face.getWidth() );
		face.setRotation( 90f );
		sector.addShip( face );
	}

	public void onUpdateHandle( float pSecondsElapsed )
	{
		for( Sector sector : this.sectors )
		{
			sector.onUpdateHandle( pSecondsElapsed );
		}
		//this.face.setRotation( this.face.getRotation() + 1 );
	}
}
