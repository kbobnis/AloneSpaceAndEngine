package com.kprojekt.alonespace.data.chooseSector;

import org.andengine.entity.primitive.Line;
import org.andengine.entity.scene.Scene;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.kprojekt.alonespace.data.Core;

/**
 * 
 */
public class Sectors extends Scene
{

	private final int offsetX;
	private final int offsetY;

	public Sectors( int offsetX, int offsetY, VertexBufferObjectManager manager )
	{
		float sectorWidth = Core.width / 3.5f;
		float sectorHeight = sectorWidth;

		this.setBackgroundEnabled( false );

		Line line = new Line( 0, 0, sectorWidth, sectorHeight, manager );
		this.attachChild( line );

		this.offsetX = offsetX;
		this.offsetY = offsetY;
	}

}
