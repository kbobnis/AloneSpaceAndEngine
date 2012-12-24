package com.kprojekt.alonespace.data.chooseSector;

import java.util.ArrayList;
import java.util.List;

import org.andengine.entity.primitive.Line;
import org.andengine.entity.scene.Scene;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.color.Color;

import com.kprojekt.alonespace.data.Core;

/**
 * 
 */
public class Sector extends Scene
{

	private List<Line> lines = new ArrayList<Line>();
	private final int offsetX;
	private final int offsetY;
	private final int sectorW;
	private final int sectorH;

	public Sector( int offsetX, int offsetY, int sectorW, int sectorH, VertexBufferObjectManager manager,
			List<Star> stars )
	{

		this.offsetX = offsetX;
		this.offsetY = offsetY;
		this.sectorW = sectorW;
		this.sectorH = sectorH;
		//drawing lines
		this.setBackgroundEnabled( false );

		this.setLines( offsetX, offsetY, sectorW, sectorH, manager );
		//vertical

		for( Line tmp : this.lines )
		{
			this.attachChild( tmp );
		}

		for( Star star : stars )
		{
			if( Star.willBeBorn() )
			{
			}
		}
	}

	private void setLines( float offsetX, float offsetY, float sectorW, float sectorH, VertexBufferObjectManager manager )
	{
		Color randomColor = new Color( Core.random.nextFloat(), Core.random.nextFloat(), Core.random.nextFloat() );
		int offset = 10;

		// left
		Line line = new Line( offsetX + offset, offsetY, offsetX + offset, offsetY + sectorH, manager );
		line.setColor( randomColor );
		this.lines.add( line );

		// right
		line = new Line( offsetX + sectorW - offset, offsetY, offsetX + sectorW - offset, offsetY + sectorH, manager );
		line.setColor( randomColor );
		this.lines.add( line );

		//top
		line = new Line( offsetX, offsetY + offset, offsetX + sectorW, offsetY + offset, manager );
		line.setColor( randomColor );
		this.lines.add( line );

		//bottom
		line = new Line( offsetX, offsetY + sectorH - offset, offsetX + sectorW, offsetY + sectorH - offset, manager );
		line.setColor( randomColor );
		this.lines.add( line );
	}

	public void onUpdateHandle( float pSecondsElapsed )
	{
	}

	public float getHeight()
	{
		return this.sectorH;
	}

	public float getOffsetX()
	{
		return this.offsetX;
	}

	public float getOffsetY()
	{
		return this.offsetY;
	}

	public int getWidth()
	{
		return this.sectorW;
	}

}
