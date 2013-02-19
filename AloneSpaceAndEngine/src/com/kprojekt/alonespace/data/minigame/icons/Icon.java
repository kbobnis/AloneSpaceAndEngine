package com.kprojekt.alonespace.data.minigame.icons;

import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.kprojekt.alonespace.data.Core;
import com.kprojekt.alonespace.data.minigame.Star;
import com.kprojekt.alonespace.data.model.ShipPart;

/**
 * @author Krzysiek Bobnis
 */
public class Icon extends Sprite
{

	private final Scene scene;
	private final Star star;

	private IconState state = IconState.nothing;
	private boolean alphaGrowing;
	private final ShipPart part;

	enum IconState
	{
		nothing, clicked
	}

	public Icon( float x, float y, VertexBufferObjectManager manager, Scene scene2, Star star2, ShipPart part2 )
	{
		super( x - part2.getTextureRegion().getWidth() / 2, y - part2.getTextureRegion().getHeight() / 2,
				part2.getTextureRegion(), manager );
		this.scene = scene2;
		this.star = star2;
		this.part = part2;
		scene.registerTouchArea( this );
		star.attachChild( this );
	}

	@Override
	public boolean onAreaTouched( final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY )
	{
		switch( pSceneTouchEvent.getAction() )
		{
			case TouchEvent.ACTION_DOWN:
			{
				System.out.println( "Icon touched!" );
				scene.unregisterTouchArea( this );
				this.state = IconState.clicked;
				Core.loggedProfile.getShip().addPart( this.part );
				this.setAlpha( 1 );
				return true;
			}
		}
		return false;
	}

	@Override
	protected void onManagedUpdate( float pSecondsElapsed )
	{
		switch( this.state )
		{
			case nothing:
				glow( pSecondsElapsed );
				break;
			case clicked:
				die( pSecondsElapsed );
				break;
			default:
				break;
		}

		super.onManagedUpdate( pSecondsElapsed );
	}

	private void die( float pSecondsElapsed )
	{
		float scale = this.getScaleX();
		scale += 0.1f;
		if( scale > 4 )
		{
			this.disposeMe();
		}
		this.setScale( scale );
	}

	private void disposeMe()
	{
		this.scene.unregisterTouchArea( this );
		this.star.detachChild( this );
	}

	private void glow( float pSecondsElapsed )
	{
		float alpha = this.getAlpha();
		if( this.alphaGrowing )
		{
			alpha += 0.1f;
			if( alpha > 1 )
			{
				this.alphaGrowing = false;
				alpha = 1;
			}
		}
		else
		{
			alpha -= 0.1f;
			if( alpha < 0 )
			{
				this.alphaGrowing = true;
				alpha = 0;
			}
		}
		this.setAlpha( alpha );
	}

}
