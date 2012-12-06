package com.kprojekt.alonespace.data.minigame;

import org.andengine.entity.sprite.Sprite;
import org.andengine.extension.physics.box2d.util.Vector2Pool;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.kprojekt.alonespace.data.minigame.icons.Icon;

/**
 * 
 */
public class Ship extends Sprite
{
	private Body shipBody;
	private final Icon lights;

	public Ship( int posX, int posY, TextureRegion shipTextureRegion,
			VertexBufferObjectManager vertexBufferObjectManager, Icon lights )
	{
		super( posX, posY, shipTextureRegion, vertexBufferObjectManager );
		this.lights = lights;
		lights.setX( this.getWidth() / 2 - lights.getWidth() / 2 );
		lights.setY( this.getHeight() - lights.getHeight() / 2 );
		lights.setBody( shipBody );
		this.attachChild( lights );
	}

	public void setBody( Body shipBody )
	{
		this.shipBody = shipBody;
		if( this.lights != null )
		{
			this.lights.setBody( shipBody );
		}
	}

}
