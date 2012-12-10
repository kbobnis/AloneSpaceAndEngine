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
import com.kprojekt.alonespace.data.chooseSector.Star;
import com.kprojekt.alonespace.data.minigame.icons.Icon;

/**
 * 
 */
public class AsteroidsManager extends Entity
{

	private ZoomCamera camera;

	public AsteroidsManager( SmartList<TextureRegion> starRegions, VertexBufferObjectManager manager,
			ZoomCamera camera, int count, PhysicsWorld mPhysicsWorld, SmartList<TextureRegion> iconRegions )
	{
		this.camera = camera;
		for( int i = 0; i < count; i++ )
		{
			int nextInt = Core.random.nextInt( starRegions.size() );
			TextureRegion textureRegion = starRegions.get( nextInt );
			Star star2 = new Star( textureRegion, manager );
			float x = camera.getXMin() + Core.random.nextInt( (int)(camera.getXMax() * 2 - camera.getXMin()) );
			float y = camera.getYMin() + Core.random.nextInt( (int)(camera.getYMax() * 2 - camera.getYMin()) );
			star2.setLocation( x, y );
			this.attachChild( star2 );

			nextInt = Core.random.nextInt( iconRegions.size() );
			TextureRegion iconRegion = iconRegions.get( nextInt );
			Icon icon = new Icon( star2.getWidth() / 2 - iconRegion.getWidth() / 2, star2.getHeight() / 2
					- iconRegion.getHeight() / 2, iconRegion, manager );
			star2.attachChild( icon );

			if( mPhysicsWorld != null )
			{
				Body createBoxBody = PhysicsFactory.createBoxBody( mPhysicsWorld, star2, BodyType.DynamicBody,
						PhysicsFactory.createFixtureDef( 150f, 0.01f, 0.9f ) );
				mPhysicsWorld.registerPhysicsConnector( new PhysicsConnector( star2, createBoxBody, true, true ) );
				createBoxBody.setBullet( true );
			}
		}

	}
}
