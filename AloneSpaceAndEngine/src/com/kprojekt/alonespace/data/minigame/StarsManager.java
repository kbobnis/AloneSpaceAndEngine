package com.kprojekt.alonespace.data.minigame;

import org.andengine.engine.camera.ZoomCamera;
import org.andengine.entity.Entity;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.adt.list.SmartList;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.kprojekt.alonespace.data.Core;
import com.kprojekt.alonespace.data.chooseSector.Star;

/**
 * 
 */
public class StarsManager extends Entity
{
	private final ZoomCamera camera;
	private final float scrollFactor;

	public StarsManager( SmartList<TextureRegion> starRegions, VertexBufferObjectManager vertexBufferObjectManager,
			ZoomCamera camera, float scrollFactor, float zoom, int count )
	{
		this.camera = camera;
		this.scrollFactor = scrollFactor;
		for( int i = 0; i < count; i++ )
		{
			int nextInt = Core.random.nextInt( starRegions.size() );
			TextureRegion textureRegion = starRegions.get( nextInt );
			Star star2 = new Star( textureRegion, vertexBufferObjectManager );
			float x = camera.getXMin() + Core.random.nextInt( (int)(camera.getXMax() * 2 - camera.getXMin()) );
			float y = camera.getYMin() + Core.random.nextInt( (int)(camera.getYMax() * 2 - camera.getYMin()) );
			star2.setLocation( x, y );
			star2.setScale( zoom );
			this.attachChild( star2 );
		}
	}

	@Override
	protected void onManagedUpdate( float pSecondsElapsed )
	{
		this.setX( this.camera.getXMin() * (1 - scrollFactor) );
		this.setY( this.camera.getYMin() * (1 - scrollFactor) );
		this.camera.onUpdate( pSecondsElapsed );
	}
}
