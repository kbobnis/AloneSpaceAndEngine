package com.kprojekt.alonespace.activities.andengine;

import java.io.IOException;
import java.io.InputStream;

import org.andengine.engine.camera.ZoomCamera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.TextMenuItem;
import org.andengine.entity.scene.menu.item.decorator.ScaleMenuItemDecorator;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.PhysicsWorld;
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
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.Toast;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.kprojekt.alonespace.data.Core;
import com.kprojekt.alonespace.data.minigame.AsteroidsManager;
import com.kprojekt.alonespace.data.minigame.Ship;
import com.kprojekt.alonespace.data.minigame.StarsLayer;

/**
 * @author Krzysiek Bobnis
 */
public class MinigameActivity extends SimpleBaseGameActivity
{
	public static ScreenOrientation orientation = ScreenOrientation.PORTRAIT_SENSOR;
	private ZoomCamera camera;
	private TextureRegion shipTextureRegion;
	private Body shipBody;
	private Ship ship;
	private PhysicsWorld mPhysicsWorld;
	private Scene scene;

	@Override
	protected void onCreate( final Bundle pSavedInstanceState )
	{
		super.onCreate( pSavedInstanceState );

	}

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
			SmartList<TextureRegion> starRegions = new SmartList<TextureRegion>();
			starRegions.add( TextureRegionFactory.extractFromTexture( starTexture ) );

			starTexture = new BitmapTexture( this.getTextureManager(), new IInputStreamOpener()
			{
				@Override
				public InputStream open() throws IOException
				{
					return getAssets().open( "gfx/stars/star.png" );
				}
			} );
			starTexture.load();
			starRegions.add( TextureRegionFactory.extractFromTexture( starTexture ) );

			starTexture = new BitmapTexture( this.getTextureManager(), new IInputStreamOpener()
			{
				@Override
				public InputStream open() throws IOException
				{
					return getAssets().open( "gfx/stars/star3.png" );
				}
			} );
			starTexture.load();
			starRegions.add( TextureRegionFactory.extractFromTexture( starTexture ) );

			BitmapTexture asterTexture = new BitmapTexture( this.getTextureManager(), new IInputStreamOpener()
			{
				@Override
				public InputStream open() throws IOException
				{
					return getAssets().open( "gfx/stars/asteroid.png" );
				}
			} );
			asterTexture.load();
			SmartList<TextureRegion> asterRegions = new SmartList<TextureRegion>();
			asterRegions.add( TextureRegionFactory.extractFromTexture( asterTexture ) );
			asterTexture = new BitmapTexture( this.getTextureManager(), new IInputStreamOpener()
			{
				@Override
				public InputStream open() throws IOException
				{
					return getAssets().open( "gfx/stars/asteroid2.png" );
				}
			} );
			asterTexture.load();
			asterRegions.add( TextureRegionFactory.extractFromTexture( asterTexture ) );
			asterTexture = new BitmapTexture( this.getTextureManager(), new IInputStreamOpener()
			{
				@Override
				public InputStream open() throws IOException
				{
					return getAssets().open( "gfx/stars/asteroid3.png" );
				}
			} );
			asterTexture.load();
			asterRegions.add( TextureRegionFactory.extractFromTexture( asterTexture ) );
			BitmapTexture iconTexture = new BitmapTexture( this.getTextureManager(), new IInputStreamOpener()
			{
				@Override
				public InputStream open() throws IOException
				{
					return getAssets().open( "gfx/icons/oil.png" );
				}
			} );
			iconTexture.load();
			SmartList<TextureRegion> iconRegions = new SmartList<TextureRegion>();
			iconRegions.add( TextureRegionFactory.extractFromTexture( iconTexture ) );

			final ITexture droidFontTexture = new BitmapTextureAtlas( this.getTextureManager(), 256, 256,
					TextureOptions.BILINEAR );
			FontFactory.setAssetBasePath( "font/" );
			Core.font = FontFactory.createFromAsset( this.getFontManager(), droidFontTexture, this.getAssets(),
					"courier.ttf", 46f, true, Color.WHITE );
			Core.font.load();
			Core.regions.starRegions.addAll( starRegions );
			Core.regions.iconRegions.addAll( iconRegions );
			Core.regions.asterRegions.addAll( asterRegions );

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
		Core.manager = manager;

		int sectorW = (int)(this.camera.getWidth() * 2 / 3f);
		int sectorH = (int)(this.camera.getHeight() * 2 / 3f);
		scene.attachChild( new StarsLayer( camera, 0.5f, 15, org.andengine.util.color.Color.WHITE, sectorW, sectorH ) );
		scene.attachChild( new StarsLayer( camera, 0.65f, 20, org.andengine.util.color.Color.WHITE, sectorW, sectorH ) );
		scene.attachChild( new StarsLayer( camera, 0.7f, 50, org.andengine.util.color.Color.WHITE, sectorW, sectorH ) );

		this.mPhysicsWorld = new PhysicsWorld( new Vector2( 0, 0 ), false );

		ship = new Ship( 0, 0, this.shipTextureRegion, this.getVertexBufferObjectManager(), this.camera );
		shipBody = PhysicsFactory.createBoxBody( mPhysicsWorld, ship, BodyType.DynamicBody,
				PhysicsFactory.createFixtureDef( 15f, 0.1f, 0.9f ) );
		ship.addBody( shipBody );

		scene.attachChild( new AsteroidsManager( camera, 10, this.mPhysicsWorld ) );

		this.camera.setChaseEntity( ship );
		scene.attachChild( ship );
		mPhysicsWorld.registerPhysicsConnector( new PhysicsConnector( ship, shipBody, true, true ) );

		scene.registerUpdateHandler( mPhysicsWorld );
		scene.registerUpdateHandler( ship );
		//scene.registerUpdateHandler( back );

		scene.registerTouchArea( ship );
		scene.setOnSceneTouchListener( ship );

		this.scene = scene;
		return scene;
	}

	@Override
	public boolean onKeyDown( int keyCode, KeyEvent event )
	{
		System.out.println( keyCode );
		switch( keyCode )
		{
			case KeyEvent.KEYCODE_MENU:
			{
				Toast.makeText( getBaseContext(), "menu button pressed", Toast.LENGTH_SHORT ).show();
				//this.scene.setIgnoreUpdate( true );
				MenuScene menuScene = new MenuScene( this.camera );
				menuScene.setOnMenuItemClickListener( new IOnMenuItemClickListener()
				{
					@Override
					public boolean onMenuItemClicked( MenuScene pMenuScene, IMenuItem pMenuItem, float pMenuItemLocalX, float pMenuItemLocalY )
					{
						return true;
					}
				} );
				IMenuItem textMenuItem = new TextMenuItem( 0, Core.font, "play", this.getVertexBufferObjectManager() );
				textMenuItem.setX( this.camera.getWidth() / 2 );
				final IMenuItem playMenuItem = new ScaleMenuItemDecorator( textMenuItem, 2, 1 );
				IMenuItem textMenuItem2 = new TextMenuItem( 1, Core.font, "options",
						this.getVertexBufferObjectManager() );
				textMenuItem2.setX( this.camera.getWidth() / 2 );
				textMenuItem2.setY( textMenuItem.getHeight() );
				final IMenuItem playMenuItem2 = new ScaleMenuItemDecorator( textMenuItem2, 2, 1 );

				menuScene.addMenuItem( playMenuItem );
				menuScene.addMenuItem( playMenuItem2 );
				menuScene.setBackgroundEnabled( false );
				//this.scene.attachChild( menuScene );
				//this.scene.setOnSceneTouchListener( menuScene );
				//this.scene.setOnAreaTouchListener( menuScene );
				//this.scene.registerUpdateHandler( menuScene );
				this.scene.setChildScene( menuScene );
				break;
			}
			case KeyEvent.KEYCODE_BACK:
			{
				finish();
				break;
			}
		}
		return true;
	}
}
