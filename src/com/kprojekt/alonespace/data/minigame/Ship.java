package com.kprojekt.alonespace.data.minigame;

import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import android.graphics.PointF;

/**
 * 
 */
public class Ship extends Sprite implements IOnSceneTouchListener
{
	private enum ACTION
	{
		NOTHING, MOVE_SHIP
	};

	private float acclerationPsec = 1;
	private float decelerationPsec = 1;
	private float anglePsec = 10; //degrees
	private PointF acceleration = new PointF();
	private float touchedDownX;
	private float touchedDownY;
	private ACTION actualAction;
	private long touchedDownTime;

	public Ship( int posX, int posY, TextureRegion shipTextureRegion,
			VertexBufferObjectManager vertexBufferObjectManager )
	{
		super( posX, posY, shipTextureRegion, vertexBufferObjectManager );
	}

	@Override
	public boolean onSceneTouchEvent( Scene pScene, TouchEvent event )
	{
		float touchX = event.getX();
		float touchY = event.getY();
		float[] sceneXY = this.convertLocalToSceneCoordinates( new float[] { touchX, touchY } );
		switch( event.getAction() )
		{
			case TouchEvent.ACTION_DOWN:
			{
				this.doActionOnDownXY( sceneXY[0], sceneXY[1] );
				break;
			}
			case TouchEvent.ACTION_MOVE:
			{
				this.doActionOnMoveByXY( sceneXY[0] - this.touchedDownX, sceneXY[1] - this.touchedDownY );
				//this.moveBy( sceneXY[0], sceneXY[1] );
				break;
			}
			case TouchEvent.ACTION_UP:
			{
				this.actualAction = ACTION.NOTHING;
				break;
			}
		}
		return false;
	}

	private void doActionOnMoveByXY( float x, float y )
	{
		switch( this.actualAction )
		{
			case MOVE_SHIP:
			{
				this.moveBy( -x, -y );
				break;
			}
			case NOTHING:
			default:
				//just nothing
		}
	}

	private void doActionOnDownXY( float x, float y )
	{
		this.touchedDownX = x;
		this.touchedDownY = y;
		this.touchedDownTime = System.currentTimeMillis();
		this.actualAction = ACTION.MOVE_SHIP;
	}

	private void moveBy( float x, float y )
	{
		long now = System.currentTimeMillis();

		this.setX( this.getX() + x );
		this.setY( this.getY() + y );
		//lets check if this is accelerate, decelerate, rotate
	}

}
