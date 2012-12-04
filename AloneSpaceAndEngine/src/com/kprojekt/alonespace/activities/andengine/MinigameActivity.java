package com.kprojekt.alonespace.activities.andengine;

import java.io.IOException;
import java.io.InputStream;

import org.andengine.engine.camera.ZoomCamera;
import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.extension.physics.box2d.util.constants.PhysicsConstants;
import org.andengine.opengl.texture.bitmap.BitmapTexture;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.texture.region.TextureRegionFactory;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.ui.activity.SimpleBaseGameActivity;
import org.andengine.util.adt.io.in.IInputStreamOpener;
import org.andengine.util.color.Color;
import org.andengine.util.debug.Debug;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.MassData;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.kprojekt.alonespace.data.Core;
import com.kprojekt.alonespace.data.minigame.Ship;
import com.kprojekt.alonespace.data.minigame.StarsManager;

public class MinigameActivity extends SimpleBaseGameActivity implements IUpdateHandler

{
	public static ScreenOrientation orientation = ScreenOrientation.PORTRAIT_SENSOR;

	private ZoomCamera camera;
	private TextureRegion shipTextureRegion;

	private int shipXInSector;
	private int shipYInSector;
	private int sectorX;
	private int sectorY;

	private PhysicsWorld physxWorld;

	private Body shipBody;

	private Ship ship;

	private StarsManager starsManager;

	private TextureRegion starTextureRegion;

	private TextureRegion asterTextureRegion;

	@Override
	public EngineOptions onCreateEngineOptions()
	{
		this.camera = new ZoomCamera( 0, 0, Core.width, Core.height );
		this.shipXInSector = this.getIntent().getExtras().getInt( "shipXInSector" );
		this.shipYInSector = this.getIntent().getExtras().getInt( "shipYInSector" );
		this.sectorX = this.getIntent().getExtras().getInt( "sectorX" );
		this.sectorY = this.getIntent().getExtras().getInt( "sectorY" );

		return new EngineOptions( Core.fullScreen, Core.orientation, Core.ratioResPolicy, camera );
	}

	@Override
	public void onCreateResources()
	{
		try
		{
			BitmapTexture shipTexture = new BitmapTexture( this.getTextureManager(), new IInputStreamOpener()
			{
				@Override
				public InputStream open() throws IOException
				{
					return getAssets().open( "gfx/ship.png" );
				}
			} );
			shipTexture.load();
			this.shipTextureRegion = TextureRegionFactory.extractFromTexture( shipTexture );

			BitmapTexture starTexture = new BitmapTexture( this.getTextureManager(), new IInputStreamOpener()
			{
				@Override
				public InputStream open() throws IOException
				{
					return getAssets().open( "gfx/stars/star2.png" );
				}
			} );
			starTexture.load();
			this.starTextureRegion = TextureRegionFactory.extractFromTexture( starTexture );

			BitmapTexture asterTexture = new BitmapTexture( this.getTextureManager(), new IInputStreamOpener()
			{
				@Override
				public InputStream open() throws IOException
				{
					return getAssets().open( "gfx/stars/asteroid.png" );
				}
			} );
			asterTexture.load();
			this.asterTextureRegion = TextureRegionFactory.extractFromTexture( asterTexture );
		}
		catch( IOException e )
		{
			Debug.e( e );
		}
	}

	@Override
	public Scene onCreateScene()
	{
		Scene scene = new Scene();
		scene.setBackground( new Background( Color.BLACK ) );

		int sectorW = Core.width;
		int sectorH = Core.height;

		starsManager = new StarsManager( Core.getSectorData( this.sectorX, this.sectorY ),
				this.getVertexBufferObjectManager(), this.starTextureRegion, this.asterTextureRegion );

		ship = new Ship( this.shipXInSector, this.shipYInSector, this.shipTextureRegion,
				this.getVertexBufferObjectManager() );

		scene.attachChild( starsManager );
		scene.attachChild( ship );

		this.physxWorld = new PhysicsWorld( new Vector2( 0, 0 ), false );
		PhysicsWorld mPhysicsWorld = this.physxWorld;

		shipBody = PhysicsFactory.createBoxBody( mPhysicsWorld, ship, BodyType.DynamicBody,
				PhysicsFactory.createFixtureDef( 1f, -0.1f, 0.9f ), PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT );
		mPhysicsWorld.registerPhysicsConnector( new PhysicsConnector( ship, shipBody, true, true ) );
		ship.setBody( shipBody );
		ship.setPhysicWorld( mPhysicsWorld );

		final VertexBufferObjectManager vertexBufferObjectManager = this.getVertexBufferObjectManager();

		final Rectangle ground = new Rectangle( 0, sectorH - 2, sectorW, 2, vertexBufferObjectManager );
		final Rectangle roof = new Rectangle( 0, 0, sectorW, 2, vertexBufferObjectManager );
		final Rectangle left = new Rectangle( 0, 0, 2, sectorH, vertexBufferObjectManager );
		final Rectangle right = new Rectangle( sectorW - 2, 0, 2, sectorH, vertexBufferObjectManager );

		final FixtureDef wallFixtureDef = PhysicsFactory.createFixtureDef( 1, 0.5f, 0.5f );
		PhysicsFactory.createBoxBody( mPhysicsWorld, ground, BodyType.StaticBody, wallFixtureDef );
		PhysicsFactory.createBoxBody( mPhysicsWorld, roof, BodyType.StaticBody, wallFixtureDef );
		PhysicsFactory.createBoxBody( mPhysicsWorld, left, BodyType.StaticBody, wallFixtureDef );
		PhysicsFactory.createBoxBody( mPhysicsWorld, right, BodyType.StaticBody, wallFixtureDef );
		scene.attachChild( ground );
		scene.attachChild( roof );
		scene.attachChild( left );
		scene.attachChild( right );

		scene.registerUpdateHandler( mPhysicsWorld );
		scene.registerUpdateHandler( this );
		scene.registerUpdateHandler( ship );

		scene.registerTouchArea( ship );
		scene.setOnSceneTouchListenerBindingOnActionDownEnabled( true );
		scene.setTouchAreaBindingOnActionMoveEnabled( true );
		scene.setOnAreaTouchListener( ship );
		scene.setOnSceneTouchListener( ship );
		return scene;
	}

	@Override
	public void onUpdate( float pSecondsElapsed )
	{
		//		this.camera.set( this.ship.getX() - Core.width / 2 + this.ship.getWidth() / 2, this.ship.getY() - Core.height
		//				/ 2 + this.ship.getHeight() / 2, this.ship.getX() + Core.width / 2 + this.ship.getWidth() / 2,
		//				this.ship.getY() + Core.height / 2 + this.ship.getHeight() / 2 );
		this.starsManager.cameraBounds( this.camera.getCenterX(), this.camera.getCenterY(), this.camera.getWidth(),
				this.camera.getHeight() );
	}

	@Override
	public void reset()
	{
		// TODO @Krzysiek Auto-generated method stub

	}

}
