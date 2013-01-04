package com.kprojekt.alonespace.data.minigame;

import org.andengine.engine.camera.ZoomCamera;
import org.andengine.entity.Entity;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.util.adt.list.SmartList;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.kprojekt.alonespace.data.Core;

/**
 * 
 */
public class AsteroidsManager extends Entity
{

	private ZoomCamera camera;
	private SmartList<Star> stars = new SmartList<Star>();

	public AsteroidsManager( ZoomCamera camera, int count )
	{
		this.camera = camera;
		for( int i = 0; i < count; i++ )
		{
			Star createStar = this.createStar();
			stars.add( createStar );
			this.attachChild( createStar );
		}
	}

	private Star createStar()
	{
		SmartList<TextureRegion> starRegions = Core.regions.asterRegions;
		Star star2 = new Star( starRegions.get( Core.random.nextInt( starRegions.size() ) ) );
		float x = camera.getXMin() + Core.random.nextInt( (int)(camera.getXMax() * 2 - camera.getXMin()) );
		float y = camera.getYMin() + Core.random.nextInt( (int)(camera.getYMax() * 2 - camera.getYMin()) );
		star2.setLocation( x, y );
		star2.addIcon();

		PhysicsWorld mPhysicsWorld = Core.mPhysicsWorld;
		if( mPhysicsWorld != null )
		{
			Body createBoxBody = PhysicsFactory.createBoxBody( mPhysicsWorld, star2, BodyType.DynamicBody,
					PhysicsFactory.createFixtureDef( 150f, 0.01f, 0.9f ) );
			mPhysicsWorld.registerPhysicsConnector( new PhysicsConnector( star2, createBoxBody, true, true ) );
		}
		return star2;
	}
}
