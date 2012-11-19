package com.kprojekt.alonespace.data.chooseSector;

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
	private Sectors sectors;

	public ShipOnSector( TextureRegion shipTextureRegion, int sectorX, int sectorY, VertexBufferObjectManager manager,
			Camera camera )
	{
		this.setBackground( new Background( Color.YELLOW ) );
		this.shipTextureRegion = shipTextureRegion;

		sectors = new Sectors( sectorX, sectorY, manager );
		this.attachChild( sectors );

		face = new Sprite( 100, 100, this.shipTextureRegion, manager );
		face.setRotation( 90f );
		this.attachChild( face );
	}

	public void onUpdateHandle( float pSecondsElapsed )
	{
		this.face.setRotation( this.face.getRotation() + 1 );
	}
}
