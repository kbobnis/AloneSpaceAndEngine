package com.kprojekt.alonespace.data.minigame;

import org.andengine.entity.scene.IOnAreaTouchListener;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.ITouchArea;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.shape.IAreaShape;
import org.andengine.entity.sprite.Sprite;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.extension.physics.box2d.util.Vector2Pool;
import org.andengine.extension.physics.box2d.util.constants.PhysicsConstants;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.JointDef.JointType;
import com.badlogic.gdx.physics.box2d.joints.GearJointDef;
import com.badlogic.gdx.physics.box2d.joints.MouseJoint;
import com.badlogic.gdx.physics.box2d.joints.MouseJointDef;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;

/**
 * 
 */
public class Ship extends Sprite implements IOnAreaTouchListener, IOnSceneTouchListener
{
	private Body shipBody;
	private Body groundBody;
	private PhysicsWorld physicsWorld;
	private MouseJoint activeMouseJoint;

	public Ship( int posX, int posY, TextureRegion shipTextureRegion,
			VertexBufferObjectManager vertexBufferObjectManager )
	{
		super( posX, posY, shipTextureRegion, vertexBufferObjectManager );
		//this.setScale( 0.3f );
	}

	@Override
	public boolean onAreaTouched( TouchEvent pSceneTouchEvent, ITouchArea pTouchArea, float pTouchAreaLocalX,
			float pTouchAreaLocalY )
	{
		switch( pSceneTouchEvent.getAction() )
		{
			case TouchEvent.ACTION_DOWN:

				if( this.activeMouseJoint == null )
				{
					float x = (pTouchAreaLocalX - this.getWidth() / 2) / PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT;
					float y = (pTouchAreaLocalY - this.getHeight() / 2) / PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT;
					final Vector2 localPoint = Vector2Pool.obtain( x, y );
					this.activeMouseJoint = this.createMouseJoint( (IAreaShape)pTouchArea, localPoint );
					Vector2Pool.recycle( localPoint );
				}
				return true;
		}

		return false;
	}

	@Override
	public boolean onSceneTouchEvent( Scene pScene, TouchEvent pSceneTouchEvent )
	{
		switch( pSceneTouchEvent.getAction() )
		{
			case TouchEvent.ACTION_MOVE:
				if( this.activeMouseJoint != null )
				{
					float sceneX = pSceneTouchEvent.getX() / PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT;
					float sceneY = pSceneTouchEvent.getY() / PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT;
					final Vector2 vec = Vector2Pool.obtain( sceneX, sceneY );
					this.activeMouseJoint.setTarget( vec );
					Vector2Pool.recycle( vec );
				}
				return true;
			case TouchEvent.ACTION_UP:
				if( this.activeMouseJoint != null )
				{
					this.physicsWorld.destroyJoint( this.activeMouseJoint );
					this.activeMouseJoint = null;
				}
				return true;
		}
		return false;
	}

	public void setPhysicWorld( PhysicsWorld physicsWorld )
	{
		this.physicsWorld = physicsWorld;
		this.groundBody = this.physicsWorld.createBody( new BodyDef() );
	}

	public void setBody( Body shipBody )
	{
		this.shipBody = shipBody;
	}

	private MouseJoint createMouseJoint( final IAreaShape pFace, Vector2 localPoint )
	{

		final Body body = this.shipBody;
		final MouseJointDef mouseJointDef = new MouseJointDef();

		this.groundBody.setTransform( localPoint, 0 );

		mouseJointDef.bodyA = this.groundBody;
		mouseJointDef.bodyB = body;
		mouseJointDef.dampingRatio = 1f;
		mouseJointDef.frequencyHz = 100;
		mouseJointDef.maxForce = (100f * body.getMass());
		mouseJointDef.collideConnected = true;

		mouseJointDef.target.set( body.getWorldPoint( localPoint ) );

		return (MouseJoint)this.physicsWorld.createJoint( mouseJointDef );
	}
}
