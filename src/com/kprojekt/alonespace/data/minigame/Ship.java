package com.kprojekt.alonespace.data.minigame;

import java.util.ArrayList;
import java.util.List;

import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

/**
 * 
 */
public class Ship extends Sprite
{

	private final double posX;
	private final double posY;
	private List<ShipChangeListener> shipChangeListeners = new ArrayList<ShipChangeListener>();

	public Ship( double posX, double posY, TextureRegion shipTextureRegion,
			VertexBufferObjectManager vertexBufferObjectManager )
	{
		super( 0, 0, shipTextureRegion, vertexBufferObjectManager );
		this.posX = posX;
		this.posY = posY;
	}

	public void addShipChangeListener( ShipChangeListener listener )
	{
		this.shipChangeListeners.add( listener );
	}

}
