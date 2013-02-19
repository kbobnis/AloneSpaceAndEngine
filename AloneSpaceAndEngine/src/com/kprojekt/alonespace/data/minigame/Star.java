package com.kprojekt.alonespace.data.minigame;

import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.adt.list.SmartList;

import com.kprojekt.alonespace.data.Core;
import com.kprojekt.alonespace.data.minigame.icons.Icon;
import com.kprojekt.alonespace.data.model.ShipPart;

/**
 * @author Krzysiek Bobnis
 */
public class Star extends Sprite
{
	private SmartList<Icon> icons = new SmartList<Icon>();

	public Star( TextureRegion textureRegion, VertexBufferObjectManager manager )
	{
		super( 0, 0, textureRegion, manager );
	}

	public void setLocation( float x, float y )
	{
		this.setX( x );
		this.setY( y );
	}

	public void addIcon( VertexBufferObjectManager manager, Scene scene, ShipPart part )
	{
		Icon icon = new Icon( getWidth() / 2, getHeight() / 2, manager, scene, this, part );
		this.icons.add( icon );
	}

}
