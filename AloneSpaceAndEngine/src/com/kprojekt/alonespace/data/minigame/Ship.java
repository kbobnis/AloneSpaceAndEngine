package com.kprojekt.alonespace.data.minigame;

import org.andengine.engine.camera.ZoomCamera;
import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.extension.physics.box2d.util.Vector2Pool;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

public class Ship extends Sprite implements IOnSceneTouchListener, IUpdateHandler
{
	private Body shipBody;
	private boolean touchedDownOnFreeSpace;
	private float touchedDownX;
	private float touchedDownY;
	private Vector2 moveVector = new Vector2();
	private ZoomCamera camera;

	public Ship( int posX, int posY, TextureRegion shipTextureRegion,
			VertexBufferObjectManager vertexBufferObjectManager, ZoomCamera camera )
	{
		super( posX, posY, shipTextureRegion, vertexBufferObjectManager );
		this.camera = camera;
	}

	@Override
	public boolean onSceneTouchEvent( Scene pScene, TouchEvent pSceneTouchEvent )
	{
		TouchEvent te = pSceneTouchEvent;
		float x = te.getX() - this.camera.getXMin();
		float y = te.getY() - this.camera.getYMin();

		switch( pSceneTouchEvent.getAction() )
		{
			case TouchEvent.ACTION_DOWN:
			{
				this.touchedDownOnFreeSpace = true;
				this.touchedDownX = x;
				this.touchedDownY = y;
				break;
			}
			case TouchEvent.ACTION_MOVE:
				if( this.touchedDownOnFreeSpace )
				{
					float movedX = x - this.touchedDownX;
					float movedY = y - this.touchedDownY;
					Vector2 obtain = Vector2Pool.obtain( movedX, movedY );
					obtain.mul( 0.05f );
					this.moveVector.add( obtain );
					Vector2Pool.recycle( obtain );
					this.touchedDownX = x;
					this.touchedDownY = y;
				}
				break;
			case TouchEvent.ACTION_UP:
				break;
		}
		return false;
	}

	public void addBody( Body shipBody2 )
	{
		shipBody = shipBody2;
	}

	@Override
	protected void onManagedUpdate( float pSecondsElapsed )
	{
		this.shipBody.setLinearVelocity( this.moveVector.add( this.shipBody.getLinearVelocity() ) );
		//		Vector2 linearVelocity = this.shipBody.getLinearVelocity();
		//		this.shipBody.setLinearVelocity( moveVector.add( linearVelocity ) );
		//		Log.d( "ship ", "ship x: " + this.shipBody.getPosition().x + ", y: " + this.shipBody.getPosition().y
		//				+ ", vel x: " + this.shipBody.getLinearVelocity().x + ", vel y: " + this.shipBody.getLinearVelocity().y
		//				+ ", camera.x " + this.camera.getXMin() );
		//
		//		Vector2 vel = this.shipBody.getLinearVelocity();
		//		float moveVectorAngle = (float)(180 - MathUtils.radToDeg( (float)Math.atan2( vel.x, vel.y ) ));

		//		this.shipBody.setTransform( this.shipBody.getPosition().x, this.shipBody.getPosition().y,
		//				MathUtils.degToRad( moveVectorAngle ) );

		this.moveVector.set( 0, 0 );
		this.camera.onUpdate( pSecondsElapsed );
	}
}
