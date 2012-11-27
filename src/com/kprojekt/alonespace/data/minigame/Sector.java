package com.kprojekt.alonespace.data.minigame;

import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.util.color.Color;

/**
 * 
 */
public class Sector extends Scene
{
	public Sector( SectorData sectorData )
	{
		this.setBackground( new Background( Color.BLACK ) );
	}

}
