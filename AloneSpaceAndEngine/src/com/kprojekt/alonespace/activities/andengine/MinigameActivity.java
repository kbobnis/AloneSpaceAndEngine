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
import org.andengine.util.adt.list.SmartList;
import org.andengine.util.color.Color;
import org.andengine.util.debug.Debug;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.kprojekt.alonespace.data.Core;
import com.kprojekt.alonespace.data.minigame.AsteroidsManager;
import com.kprojekt.alonespace.data.minigame.Ship;
import com.kprojekt.alonespace.data.minigame.StarsManager;

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

	private SmartList<TextureRegion> asterRegions = new SmartList<TextureRegion>();

	private SmartList<TextureRegion> starRegions = new SmartList<TextureRegion>();

	private SmartList<TextureRegion> iconRegions = new SmartList<TextureRegion>();

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
			this.starRegions.add( TextureRegionFactory.extractFromTexture( starTexture ) );

			starTexture = new BitmapTexture( this.getTextureManager(), new IInputStreamOpener()
			{
				@Override
				public InputStream open() throws IOException
				{
					return getAssets().open( "gfx/stars/star.png" );
				}
			} );
			starTexture.load();
			this.starRegions.add( TextureRegionFactory.extractFromTexture( starTexture ) );

			starTexture = new BitmapTexture( this.getTextureManager(), new IInputStreamOpener()
			{
				@Override
				public InputStream open() throws IOException
				{
					return getAssets().open( "gfx/stars/star3.png" );
				}
			} );
			starTexture.load();
			this.starRegions.add( TextureRegionFactory.extractFromTexture( starTexture ) );

			BitmapTexture asterTexture = new BitmapTexture( this.getTextureManager(), new IInputStreamOpener()
			{
				@Override
				public InputStream open() throws IOException
				{
					return getAssets().open( "gfx/stars/asteroid.png" );
				}
			} );
			asterTexture.load();
			this.asterRegions.add( TextureRegionFactory.extractFromTexture( asterTexture ) );
			asterTexture = new BitmapTexture( this.getTextureManager(), new IInputStreamOpener()
			{
				@Override
				public InputStream open() throws IOException
				{
					return getAssets().open( "gfx/stars/asteroid2.png" );
				}
			} );
			asterTexture.load();
			this.asterRegions.add( TextureRegionFactory.extractFromTexture( asterTexture ) );
			asterTexture = new BitmapTexture( this.getTextureManager(), new IInputStreamOpener()
			{
				@Override
				public InputStream open() throws IOException
				{
					return getAssets().open( "gfx/stars/asteroid3.png" );
				}
			} );
			asterTexture.load();
			this.asterRegions.add( TextureRegionFactory.extractFromTexture( asterTexture ) );
			BitmapTexture iconTexture = new BitmapTexture( this.getTextureManager(), new IInputStreamOpener()
			{
				@Override
				public InputStream open() throws IOException
				{
					return getAssets().open( "gfx/icons/oil.png" );
				}
			} );
			iconTexture.load();
			this.iconRegions.add( TextureRegionFactory.extractFromTexture( iconTexture ) );
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
		final VertexBufferObjectManager manager = this.getVertexBufferObjectManager();

		StarsManager back = null;
		back = new StarsManager( this.starRegions, manager, camera, 0.5f, 1f, 10 );
		scene.attachChild( back );
		back = new StarsManager( this.starRegions, manager, camera, 0.3f, 0.5f, 50 );
		scene.attachChild( back );
		back = new StarsManager( this.starRegions, manager, camera, 0.1f, 0.3f, 100 );
		scene.attachChild( back );
		back = new StarsManager( this.starRegions, manager, camera, 0.01f, 0.1f, 500 );
		scene.attachChild( back );

		this.physxWorld = new PhysicsWorld( new Vector2( 0, 0 ), false ); //SensorManager.GRAVITY_EARTH 
		PhysicsWorld mPhysicsWorld = this.physxWorld;

		ship = new Ship( 0, 0, this.shipTextureRegion, this.getVertexBufferObjectManager(), this.camera );
		shipBody = PhysicsFactory.createBoxBody( mPhysicsWorld, ship, BodyType.DynamicBody,
				PhysicsFactory.createFixtureDef( 15f, 0.1f, 0.9f ) );
		ship.addBody( shipBody );

		AsteroidsManager asteroids = new AsteroidsManager( this.asterRegions, manager, camera, 10, mPhysicsWorld,
				this.iconRegions );
		scene.attachChild( asteroids );

		this.camera.setChaseEntity( ship );
		scene.attachChild( ship );
		mPhysicsWorld.registerPhysicsConnector( new PhysicsConnector( ship, shipBody, true, true ) );

		final VertexBufferObjectManager vertexBufferObjectManager = this.getVertexBufferObjectManager();

		float border = Core.pixelsToMeters( 10 );
		int sectorW = (int)Core.metersToPixels( Core.widthInMeters ) * 2;
		int sectorH = (int)Core.metersToPixels( Core.heightInMeters ) * 2;
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
		scene.registerUpdateHandler( ship );
		scene.registerUpdateHandler( back );

		scene.registerTouchArea( ship );
		scene.setOnSceneTouchListener( ship );

		return scene;
	}
}
