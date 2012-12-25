package com.kprojekt.alonespace.activities.andengine;

import java.io.IOException;
import java.io.InputStream;

import org.andengine.engine.camera.ZoomCamera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.bitmap.BitmapTexture;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.texture.region.TextureRegionFactory;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.ui.activity.SimpleBaseGameActivity;
import org.andengine.util.adt.io.in.IInputStreamOpener;
import org.andengine.util.adt.list.SmartList;
import org.andengine.util.debug.Debug;

import android.graphics.Color;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.kprojekt.alonespace.data.Core;
import com.kprojekt.alonespace.data.minigame.AsteroidsManager;
import com.kprojekt.alonespace.data.minigame.Ship;
import com.kprojekt.alonespace.data.minigame.StarsManager;

public class MinigameActivity extends SimpleBaseGameActivity
{
	public static ScreenOrientation orientation = ScreenOrientation.PORTRAIT_SENSOR;

	private ZoomCamera camera;
	private TextureRegion shipTextureRegion;

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

			final ITexture droidFontTexture = new BitmapTextureAtlas( this.getTextureManager(), 256, 256,
					TextureOptions.BILINEAR );
			FontFactory.setAssetBasePath( "font/" );
			Core.font = FontFactory.createFromAsset( this.getFontManager(), droidFontTexture, this.getAssets(),
					"courier.ttf", 46f, true, Color.WHITE );
			Core.font.load();
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
		scene.setBackground( new Background( org.andengine.util.color.Color.BLACK ) );
		VertexBufferObjectManager manager = this.getVertexBufferObjectManager();

		int sectorW = (int)(this.camera.getWidth() * 2 / 3f);
		int sectorH = (int)(this.camera.getHeight() * 2 / 3f);
		StarsManager back = null;
		back = new StarsManager( this.starRegions, manager, camera, 0.5f, 1f, 10, org.andengine.util.color.Color.RED,
				sectorW, sectorH );
		scene.attachChild( back );
		//back = new StarsManager( this.starRegions, manager, camera, 0.7f, 0.5f, 20,
		//org.andengine.util.color.Color.GREEN, sectorW, sectorH );
		//scene.attachChild( back );
		//back = new StarsManager( this.starRegions, manager, camera, 0.9f, 0.3f, 50, org.andengine.util.color.Color.YELLOW );
		//scene.attachChild( back );
		//back = new StarsManager( this.starRegions, manager, camera, 0.99f, 0.1f, 100,org.andengine.util.color.Color.BLUE );
		//scene.attachChild( back );

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

		scene.registerUpdateHandler( mPhysicsWorld );
		scene.registerUpdateHandler( ship );
		scene.registerUpdateHandler( back );

		scene.registerTouchArea( ship );
		scene.setOnSceneTouchListener( ship );

		return scene;
	}
}
