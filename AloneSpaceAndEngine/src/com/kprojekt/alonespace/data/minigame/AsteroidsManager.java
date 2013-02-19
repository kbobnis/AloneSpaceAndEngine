package com.kprojekt.alonespace.data.minigame;

import java.util.List;

import org.andengine.engine.camera.ZoomCamera;
import org.andengine.entity.Entity;
import org.andengine.entity.scene.Scene;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.adt.list.SmartList;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.kprojekt.alonespace.data.Core;
import com.kprojekt.alonespace.data.model.ShipPart;

/**
 * @author Krzysiek Bobnis
 */
public class AsteroidsManager extends Entity
{

	private ZoomCamera camera;
	private SmartList<Star> stars = new SmartList<Star>();
	private final Scene scene;

	public AsteroidsManager( ZoomCamera camera, int count, PhysicsWorld physicsWorld,
			VertexBufferObjectManager manager, SmartList<TextureRegion> starRegions,
			SmartList<TextureRegion> iconRegion, Scene scene )
	{
		this.camera = camera;
		this.scene = scene;
		List<ShipPart> parts = Core.model.getAllParts();

		for( int i = 0; i < count; i++ )
		{
			ShipPart shipPart = parts.get( Core.random.nextInt( parts.size() ) );
			Star createStar = this.createStar( physicsWorld, manager, starRegions, shipPart );
			stars.add( createStar );
			this.attachChild( createStar );
		}
	}

	private Star createStar( PhysicsWorld mPhysicsWorld, VertexBufferObjectManager manager, SmartList<TextureRegion> starRegions, ShipPart part )
	{
		Star star2 = new Star( starRegions.get( Core.random.nextInt( starRegions.size() ) ), manager );
		float x = camera.getXMin() + Core.random.nextInt( (int)(camera.getXMax() * 2 - camera.getXMin()) );
		float y = camera.getYMin() + Core.random.nextInt( (int)(camera.getYMax() * 2 - camera.getYMin()) );
		star2.setLocation( x, y );
		star2.addIcon( manager, scene, part );

		if( mPhysicsWorld != null )
		{
			Body createBoxBody = PhysicsFactory.createBoxBody( mPhysicsWorld, star2, BodyType.DynamicBody,
					PhysicsFactory.createFixtureDef( 150f, 0.01f, 0.9f ) );
			mPhysicsWorld.registerPhysicsConnector( new PhysicsConnector( star2, createBoxBody, true, true ) );
		}
		return star2;
	}
}
