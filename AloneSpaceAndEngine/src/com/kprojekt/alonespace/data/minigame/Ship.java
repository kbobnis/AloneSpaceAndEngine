package com.kprojekt.alonespace.data.minigame;

import org.andengine.entity.scene.IOnAreaTouchListener;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.ITouchArea;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import android.graphics.PointF;
import android.text.method.Touch;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.joints.MouseJoint;

/**
 * 
 */
public class Ship extends Sprite implements IOnAreaTouchListener, IOnSceneTouchListener
{
	private float acclerationPsec = 1;
	private float decelerationPsec = 1;
	private float anglePsec = 10; //degrees
	private PointF acceleration = new PointF();
	private float touchedDownX;
	private float touchedDownY;
	private long touchedDownTime;
	private boolean shipSelected;
	private MouseJoint shipJoint;
	private Body shipBody;
	private float lastTouchedX;
	private float lastTouchedY;

	public Ship( int posX, int posY, TextureRegion shipTextureRegion,
			VertexBufferObjectManager vertexBufferObjectManager )
	{
		super( posX, posY, shipTextureRegion, vertexBufferObjectManager );
	}

	@Override
	public boolean onAreaTouched( TouchEvent pSceneTouchEvent, ITouchArea pTouchArea, float pTouchAreaLocalX,
			float pTouchAreaLocalY )
	{
		switch( pSceneTouchEvent.getAction() )
		{
			case TouchEvent.ACTION_DOWN:
				this.lastTouchedX = pSceneTouchEvent.getX();
				this.lastTouchedY = pSceneTouchEvent.getY();
				this.shipSelected = true;
				return true;
		}

		return false;
	}

	public void setBody( Body shipBody )
	{
		this.shipBody = shipBody;
	}

	public void onUpdates( float pSecondsElapsed )
	{
		double atan2 = Math.PI - Math.atan2( this.shipBody.getLinearVelocity().x, this.shipBody.getLinearVelocity().y );
		this.shipBody.setTransform( this.shipBody.getPosition().x, this.shipBody.getPosition().y, (float)atan2 );
		this.setRotation( (float)atan2 );
	}

	@Override
	public boolean onSceneTouchEvent( Scene pScene, TouchEvent pSceneTouchEvent )
	{
		switch( pSceneTouchEvent.getAction() )
		{
			case TouchEvent.ACTION_MOVE:
				TouchEvent e = pSceneTouchEvent;
				float movedX = e.getX() - this.lastTouchedX;
				float movedY = e.getY() - this.lastTouchedY;
				this.shipBody.setLinearVelocity( this.shipBody.getLinearVelocity().add( movedX, movedY ) );
				this.lastTouchedX = e.getX();
				this.lastTouchedY = e.getY();
				break;
			case TouchEvent.ACTION_UP:
				this.shipSelected = false;
				break;
		}
		return false;
	}
}
