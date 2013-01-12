package com.kprojekt.alonespace.data.minigame;

import org.andengine.engine.camera.ZoomCamera;
import org.andengine.entity.Entity;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.adt.list.SmartList;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.kprojekt.alonespace.data.Core;

/**
 * @author Krzysiek Bobnis
 */
public class AsteroidsManager extends Entity
{

	private ZoomCamera camera;
	private SmartList<Star> stars = new SmartList<Star>();

	public AsteroidsManager( ZoomCamera camera, int count, PhysicsWorld physicsWorld,
			VertexBufferObjectManager manager, SmartList<TextureRegion> starRegions, SmartList<TextureRegion> iconRegion )
	{
		this.camera = camera;
		for( int i = 0; i < count; i++ )
		{
			Star createStar = this.createStar( physicsWorld, manager, starRegions, iconRegion );
			stars.add( createStar );
			this.attachChild( createStar );
		}
	}

	private Star createStar( PhysicsWorld mPhysicsWorld, VertexBufferObjectManager manager, SmartList<TextureRegion> starRegions, SmartList<TextureRegion> iconsRegion )
	{
		Star star2 = new Star( starRegions.get( Core.random.nextInt( starRegions.size() ) ), manager );
		float x = camera.getXMin() + Core.random.nextInt( (int)(camera.getXMax() * 2 - camera.getXMin()) );
		float y = camera.getYMin() + Core.random.nextInt( (int)(camera.getYMax() * 2 - camera.getYMin()) );
		star2.setLocation( x, y );
		star2.addIcon( manager, iconsRegion );

		if( mPhysicsWorld != null )
		{
			Body createBoxBody = PhysicsFactory.createBoxBody( mPhysicsWorld, star2, BodyType.DynamicBody,
					PhysicsFactory.createFixtureDef( 150f, 0.01f, 0.9f ) );
			mPhysicsWorld.registerPhysicsConnector( new PhysicsConnector( star2, createBoxBody, true, true ) );
		}
		return star2;
	}
}
