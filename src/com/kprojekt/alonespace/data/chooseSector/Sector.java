package com.kprojekt.alonespace.data.chooseSector;

import java.util.List;

import org.andengine.entity.primitive.Line;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.kprojekt.alonespace.data.Core;

/**
 * 
 */
public class Sector extends Scene
{

	public Sector( int offsetX, int offsetY, int sectorW, int sectorH, VertexBufferObjectManager manager,
			List<TextureRegion> starsTextureRegions )
	{
		//drawing lines
		this.setBackgroundEnabled( false );
		//vertical
		Line line = new Line( offsetX, offsetY, offsetX, offsetY + sectorH, manager );
		this.attachChild( line );

		line = new Line( offsetX + sectorW, offsetY, offsetX + sectorW, offsetY, manager );
		this.attachChild( line );

		//horizontal
		line = new Line( offsetX, offsetY, offsetX + sectorW, offsetY, manager );
		this.attachChild( line );

		line = new Line( offsetX, offsetY + sectorW, offsetX + sectorW, offsetY + sectorW, manager );
		this.attachChild( line );

		for( TextureRegion textureRegion : starsTextureRegions )
		{
			Sprite face = new Sprite( offsetX + Core.random.nextFloat() * sectorW, offsetY + Core.random.nextFloat()
					* sectorH, textureRegion, manager );
			face.setScale( 0.3f );
			this.attachChild( face );
		}
	}
}
