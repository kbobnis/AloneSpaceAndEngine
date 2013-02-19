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
import org.andengine.util.math.MathUtils;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.kprojekt.alonespace.data.model.ActionTemplate;
import com.kprojekt.alonespace.data.model.Ship;

/**
 * @author Krzysiek Bobnis
 */
public class ShipSprite extends Sprite implements IOnSceneTouchListener, IUpdateHandler
{
	private Body shipBody;
	private boolean touchedDownOnFreeSpace;
	private float touchedDownX;
	private float touchedDownY;
	private Vector2 moveVector = new Vector2();
	private ZoomCamera camera;
	private Ship shipModel;

	public ShipSprite( int posX, int posY, TextureRegion shipTextureRegion,
			VertexBufferObjectManager vertexBufferObjectManager, ZoomCamera camera, Ship shipModel )
	{
		super( posX, posY, shipTextureRegion, vertexBufferObjectManager );
		this.camera = camera;
		this.shipModel = shipModel;
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
		this.useRotationEngine();
		this.usePrimaryEngine();

		this.camera.onUpdate( pSecondsElapsed );
	}

	private void usePrimaryEngine()
	{
		Vector2 linearVelocity = this.shipBody.getLinearVelocity();
		if( this.moveVector.len() > 1 )
		{
			this.shipBody.setLinearVelocity( this.moveVector.add( linearVelocity ) );
			this.moveVector.set( 0, 0 );
		}
		System.out.println( "LInear velocity " + this.shipBody.getLinearVelocity().len() );
	}

	private void useRotationEngine()
	{
		Vector2 vel = this.shipBody.getLinearVelocity();
		float moveVectorAngle = Math.round( 180 - MathUtils.radToDeg( (float)Math.atan2( vel.x, vel.y ) ) );
		float shipAngle = Math.round( MathUtils.radToDeg( this.shipBody.getAngle() ) % 360 );
		if( shipAngle < 0 )
		{
			shipAngle = 360 + shipAngle;
		}
		float delta = moveVectorAngle - shipAngle;
		float rotate_max = this.shipModel.getActionValue( ActionTemplate.Type.rotate );
		float angularVelocity = MathUtils.radToDeg( this.shipBody.getAngularVelocity() );
		angularVelocity -= Math.signum( angularVelocity ) * 1;
		if( Math.abs( angularVelocity ) > 0 )
		{
			//this.shipBody.setAngularVelocity( MathUtils.degToRad( moveVectorAngle ) ); //MathUtils.degToRad( angularVelocity ) );
		}
	}
}
