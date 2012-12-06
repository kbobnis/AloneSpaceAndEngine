package com.kprojekt.alonespace.data.minigame.icons;

import org.andengine.entity.sprite.Sprite;
import org.andengine.extension.physics.box2d.util.Vector2Pool;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

/**
 * 
 */
public class Icon extends Sprite
{

	private Body shipBody;

	public Icon( float pX, float pY, ITextureRegion pTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager, Body shipBody )
	{
		super( pX, pY, pTextureRegion, pVertexBufferObjectManager );
		this.shipBody = shipBody;
	}

	@Override
	public boolean onAreaTouched( final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX,
			final float pTouchAreaLocalY )
	{
		switch( pSceneTouchEvent.getAction() )
		{
			case TouchEvent.ACTION_DOWN:
			case TouchEvent.ACTION_MOVE:
				Vector2 impulse = Vector2Pool.obtain( 500, -500 );
				Vector2 point = Vector2Pool.obtain( 0, 50 );

				if( this.shipBody != null )
				{
					this.shipBody.applyLinearImpulse( impulse, point );
				}

				Vector2Pool.recycle( impulse );
				Vector2Pool.recycle( point );
				return true;
		}
		return false;
	}

	public void setBody( Body shipBody2 )
	{
		shipBody = shipBody2;
	}

}
