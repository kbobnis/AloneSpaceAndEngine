package com.kprojekt.alonespace.data.minigame;

import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.adt.list.SmartList;

import com.badlogic.gdx.physics.box2d.Body;
import com.kprojekt.alonespace.data.Core;
import com.kprojekt.alonespace.data.minigame.icons.Icon;

/**
 * @author Krzysiek Bobnis
 */
public class Star extends Sprite
{
	private static float chance = 0.1f;
	private SmartList<Icon> icons = new SmartList<Icon>();

	public Star( TextureRegion textureRegion, VertexBufferObjectManager manager )
	{
		super( 0, 0, textureRegion, manager );
	}

	public static boolean willBeBorn()
	{
		return Core.random.nextFloat() < Star.chance;
	}

	public float getRotationSpeed()
	{
		return 0; //Core.random.nextFloat() * 100;
	}

	public float getBlinkSpeed()
	{
		return Core.random.nextFloat() * 0.5f;
	}

	public void setLocation( float x, float y )
	{
		this.setX( x );
		this.setY( y );
	}

	public void addIcon( VertexBufferObjectManager manager, SmartList<TextureRegion> iconRegions )
	{
		int nextInt = Core.random.nextInt( iconRegions.size() );
		TextureRegion iconRegion = iconRegions.get( nextInt );
		Icon icon = new Icon( getWidth() / 2 - iconRegion.getWidth() / 2, getHeight() / 2 - iconRegion.getHeight() / 2,
				iconRegion, manager );
		this.attachChild( icon );
		this.icons.add( icon );
	}

}
