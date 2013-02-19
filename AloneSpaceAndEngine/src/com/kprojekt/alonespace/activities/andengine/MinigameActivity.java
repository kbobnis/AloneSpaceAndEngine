package com.kprojekt.alonespace.activities.andengine;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

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

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.kprojekt.alonespace.activities.android.ShipPartsActivity;
import com.kprojekt.alonespace.data.Core;
import com.kprojekt.alonespace.data.minigame.AsteroidsManager;
import com.kprojekt.alonespace.data.minigame.ShipSprite;
import com.kprojekt.alonespace.data.minigame.StarsLayer;
import com.kprojekt.alonespace.data.model.ShipPart;

/**
 * @author Krzysiek Bobnis
 */
public class MinigameActivity extends SimpleBaseGameActivity
{
	public static ScreenOrientation orientation = ScreenOrientation.PORTRAIT_SENSOR;
	private ZoomCamera camera;
	private TextureRegion shipTextureRegion;
	private Body shipBody;
	private ShipSprite ship;
	private PhysicsWorld mPhysicsWorld;
	private Scene scene;
	private boolean menuOn;
	private ArrayList<TextureRegion> starRegions;
	private SmartList<TextureRegion> asteroidRegions;
	private SmartList<TextureRegion> iconsRegion;

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

			for( final ShipPart part : Core.model.getAllParts() )
			{
				BitmapTexture shipPartRegion = new BitmapTexture( this.getTextureManager(), new IInputStreamOpener()
				{
					@Override
					public InputStream open() throws IOException
					{
						return getAssets().open( part.getImgPath() ); //"gfx/icons/oil.png" );
					}
				} );
				shipPartRegion.load();
				part.setTextureImg( TextureRegionFactory.extractFromTexture( shipPartRegion ) );

			}

			final ITexture droidFontTexture = new BitmapTextureAtlas( this.getTextureManager(), 512, 512,
					TextureOptions.BILINEAR );
			FontFactory.setAssetBasePath( "font/" );
			Core.font = FontFactory.createFromAsset( this.getFontManager(), droidFontTexture, this.getAssets(),
					"courier.ttf", 80f, true, Color.WHITE );
			Core.font.load();
			this.starRegions = starRegions;
			this.iconsRegion = iconRegions;
			this.asteroidRegions = asterRegions;

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
		scene.attachChild( new StarsLayer( camera, 0.5f, 15, org.andengine.util.color.Color.WHITE, sectorW, sectorH,
				manager, this.starRegions ) );
		scene.attachChild( new StarsLayer( camera, 0.65f, 20, org.andengine.util.color.Color.WHITE, sectorW, sectorH,
				manager, this.starRegions ) );
		scene.attachChild( new StarsLayer( camera, 0.7f, 50, org.andengine.util.color.Color.WHITE, sectorW, sectorH,
				manager, this.starRegions ) );

		this.mPhysicsWorld = new PhysicsWorld( new Vector2( 0, 0 ), false );

		ship = new ShipSprite( 0, 0, this.shipTextureRegion, this.getVertexBufferObjectManager(), this.camera,
				Core.loggedProfile.getShip() );
		shipBody = PhysicsFactory.createBoxBody( mPhysicsWorld, ship, BodyType.DynamicBody,
				PhysicsFactory.createFixtureDef( 15f, 0.1f, 0.9f ) );
		ship.addBody( shipBody );

		AsteroidsManager asteroidsManager = new AsteroidsManager( camera, 10, this.mPhysicsWorld, manager,
				this.asteroidRegions, this.iconsRegion, scene );
		scene.attachChild( asteroidsManager );

		this.camera.setChaseEntity( ship );
		scene.attachChild( ship );
		mPhysicsWorld.registerPhysicsConnector( new PhysicsConnector( ship, shipBody, true, true ) );

		scene.registerUpdateHandler( mPhysicsWorld );
		scene.registerUpdateHandler( ship );

		scene.registerTouchArea( ship );
		scene.setOnSceneTouchListener( ship );

		this.scene = scene;
		return scene;
	}

	@Override
	public boolean onKeyDown( int keyCode, KeyEvent event )
	{
		switch( keyCode )
		{
			case KeyEvent.KEYCODE_MENU:
			{
				this.toggleMenu();
				break;
			}
			case KeyEvent.KEYCODE_BACK:
			{
				if( this.menuOn )
				{
					Core.db.saveProfiles();
					finish();
				}
				else
				{
					this.toggleMenu();
				}
				break;
			}
		}
		return true;
	}

	private void toggleMenu()
	{
		this.menuOn = !this.menuOn;
		this.scene.setIgnoreUpdate( this.menuOn );
		if( this.menuOn )
		{
			this.scene.setChildScene( buildMenu() );
		}
		else
			this.scene.clearChildScene();
	}

	private MenuScene buildMenu()
	{
		final MenuScene menuScene = new MenuScene( this.camera );
		int offset = 150;

		IMenuItem textMenuItem = new TextMenuItem( 0, Core.font, Core.locale.get( "str.game.resume" ),
				this.getVertexBufferObjectManager() );
		int xOffset = 400;
		textMenuItem.setX( xOffset );
		textMenuItem.setY( offset );
		final IMenuItem resumeMenuItem = new ScaleMenuItemDecorator( textMenuItem, 1.5f, 1 );
		menuScene.addMenuItem( resumeMenuItem );

		IMenuItem textMenuItem2 = new TextMenuItem( 1, Core.font, Core.locale.get( "str.game.shipParts" ),
				this.getVertexBufferObjectManager() );
		textMenuItem2.setX( xOffset );
		textMenuItem2.setY( textMenuItem.getHeight() + offset );
		final IMenuItem showShipMenuItem = new ScaleMenuItemDecorator( textMenuItem2, 1.5f, 1 );
		menuScene.addMenuItem( showShipMenuItem );

		IMenuItem playerMenuItem = new TextMenuItem( 2, Core.font, Core.locale.get( "str.game.returnToMenu" ),
				this.getVertexBufferObjectManager() );
		playerMenuItem.setX( xOffset );
		playerMenuItem.setY( textMenuItem.getHeight() * 2 + offset );
		final IMenuItem player2MenuItem = new ScaleMenuItemDecorator( playerMenuItem, 1.5f, 1 );
		menuScene.addMenuItem( player2MenuItem );

		menuScene.setBackgroundEnabled( false );

		menuScene.setOnMenuItemClickListener( new IOnMenuItemClickListener()
		{
			@Override
			public boolean onMenuItemClicked( MenuScene pMenuScene, IMenuItem pMenuItem, float pMenuItemLocalX, float pMenuItemLocalY )
			{
				if( pMenuItem == resumeMenuItem )
				{
					MinigameActivity.this.toggleMenu();
				}
				if( pMenuItem == showShipMenuItem )
				{
					Intent shipPartsActivity = new Intent( MinigameActivity.this, ShipPartsActivity.class );
					MinigameActivity.this.startActivityForResult( shipPartsActivity, RESULT_CANCELED );
				}
				if( pMenuItem == player2MenuItem )
				{
					MinigameActivity.this.finish();
				}
				return false;
			}
		} );
		return menuScene;
	}
}
