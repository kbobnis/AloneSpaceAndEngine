package com.kprojekt.alonespace.activities.andengine;

import java.io.IOException;
import java.io.InputStream;

import org.andengine.engine.camera.ZoomCamera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.PhysicsWorld;
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
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.kprojekt.alonespace.data.Core;
import com.kprojekt.alonespace.data.minigame.Ship;
import com.kprojekt.alonespace.data.minigame.StarsManager;
import com.kprojekt.alonespace.data.minigame.icons.Icon;

public class MinigameActivity extends SimpleBaseGameActivity

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

	private TextureRegion engineIconTextureRegion;

	@Override
	public EngineOptions onCreateEngineOptions()
	{
		this.camera = new ZoomCamera( 0, 0, Core.metersToPixels( Core.widthInMeters ),
				Core.metersToPixels( Core.heightInMeters ) );
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
			BitmapTexture engineT = new BitmapTexture( this.getTextureManager(), new IInputStreamOpener()
			{
				@Override
				public InputStream open() throws IOException
				{
					return getAssets().open( "gfx/icons/engines.png" );
				}
			} );
			engineT.load();
			this.engineIconTextureRegion = TextureRegionFactory.extractFromTexture( engineT );
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

		starsManager = new StarsManager( Core.getSectorData( this.sectorX, this.sectorY ),
				this.getVertexBufferObjectManager(), this.starTextureRegion, this.starTextureRegion );
		scene.attachChild( starsManager );

		this.physxWorld = new PhysicsWorld( new Vector2( 0, 0 ), false ); //SensorManager.GRAVITY_EARTH 
		PhysicsWorld mPhysicsWorld = this.physxWorld;

		Icon lights = new Icon( 0, 0, this.engineIconTextureRegion, this.getVertexBufferObjectManager(), null );
		ship = new Ship( 0, 0, this.shipTextureRegion, this.getVertexBufferObjectManager(), lights );
		shipBody = PhysicsFactory.createBoxBody( mPhysicsWorld, ship, BodyType.DynamicBody,
				PhysicsFactory.createFixtureDef( 15f, 0.5f, 0.9f ) );
		ship.setBody( shipBody );
		//this.camera.setChaseEntity( ship );
		scene.attachChild( ship );
		mPhysicsWorld.registerPhysicsConnector( new PhysicsConnector( ship, shipBody, true, true ) );

		Icon lights2 = new Icon( 0, 0, this.engineIconTextureRegion, this.getVertexBufferObjectManager(), null );
		Ship ship2 = new Ship( 10, 0, this.shipTextureRegion, this.getVertexBufferObjectManager(), lights2 );
		Body shipBody2 = PhysicsFactory.createBoxBody( mPhysicsWorld, ship2, BodyType.DynamicBody,
				PhysicsFactory.createFixtureDef( 30f, 0.01f, 0.9f ) );
		ship2.setBody( shipBody2 );
		scene.attachChild( ship2 );
		mPhysicsWorld.registerPhysicsConnector( new PhysicsConnector( ship2, shipBody2, true, true ) );

		final VertexBufferObjectManager vertexBufferObjectManager = this.getVertexBufferObjectManager();

		float border = Core.pixelsToMeters( 2 );
		int sectorW = (int)Core.metersToPixels( Core.widthInMeters );
		int sectorH = (int)Core.metersToPixels( Core.heightInMeters );
		final Rectangle ground = new Rectangle( 0, sectorH - border, sectorW, border, vertexBufferObjectManager );
		final Rectangle roof = new Rectangle( 0, 0, sectorW, border, vertexBufferObjectManager );
		final Rectangle left = new Rectangle( 0, 0, border, sectorH, vertexBufferObjectManager );
		final Rectangle right = new Rectangle( sectorW - border, 0, border, sectorH, vertexBufferObjectManager );

		final FixtureDef wallFixtureDef = PhysicsFactory.createFixtureDef( 1f, 0.1f, 0.5f );
		PhysicsFactory.createBoxBody( mPhysicsWorld, ground, BodyType.StaticBody, wallFixtureDef );
		PhysicsFactory.createBoxBody( mPhysicsWorld, roof, BodyType.StaticBody, wallFixtureDef );
		PhysicsFactory.createBoxBody( mPhysicsWorld, left, BodyType.StaticBody, wallFixtureDef );
		PhysicsFactory.createBoxBody( mPhysicsWorld, right, BodyType.StaticBody, wallFixtureDef );
		scene.attachChild( ground );
		scene.attachChild( roof );
		scene.attachChild( left );
		scene.attachChild( right );

		scene.registerUpdateHandler( mPhysicsWorld );
		scene.registerUpdateHandler( this.camera );

		scene.registerTouchArea( ship );
		scene.registerTouchArea( ship2 );
		scene.registerTouchArea( lights );
		scene.registerTouchArea( lights2 );
		return scene;
	}
}
