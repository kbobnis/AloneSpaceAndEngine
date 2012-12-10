package com.kprojekt.alonespace.data.minigame.icons;

import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

/**
 * 
 */
public class Icon extends Sprite
{

	public Icon( float pX, float pY, ITextureRegion pTextureRegion, VertexBufferObjectManager pVertexBufferObjectManager )
	{
		super( pX, pY, pTextureRegion, pVertexBufferObjectManager );
	}

	@Override
	public boolean onAreaTouched( final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX,
			final float pTouchAreaLocalY )
	{
		switch( pSceneTouchEvent.getAction() )
		{
			case TouchEvent.ACTION_DOWN:
				return true;
		}
		return false;
	}

}
