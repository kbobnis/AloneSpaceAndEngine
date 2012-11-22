package com.kprojekt.alonespace.activities.andengine;

import java.io.IOException;
import java.io.InputStream;

import org.andengine.engine.camera.ZoomCamera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.input.touch.detector.PinchZoomDetector;
import org.andengine.input.touch.detector.PinchZoomDetector.IPinchZoomDetectorListener;
import org.andengine.input.touch.detector.ScrollDetector;
import org.andengine.input.touch.detector.ScrollDetector.IScrollDetectorListener;
import org.andengine.input.touch.detector.SurfaceScrollDetector;
import org.andengine.opengl.texture.bitmap.BitmapTexture;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.texture.region.TextureRegionFactory;
import org.andengine.ui.activity.SimpleBaseGameActivity;
import org.andengine.util.adt.io.in.IInputStreamOpener;
import org.andengine.util.debug.Debug;

import android.view.Display;

import com.kprojekt.alonespace.data.Core;
import com.kprojekt.alonespace.data.minigame.Sector;
import com.kprojekt.alonespace.data.minigame.Ship;
import com.kprojekt.alonespace.data.minigame.ShipChangeListener;

public class MinigameActivity extends SimpleBaseGameActivity implements IScrollDetectorListener,
		IPinchZoomDetectorListener, IOnSceneTouchListener, ShipChangeListener

{
	public static ScreenOrientation orientation = ScreenOrientation.PORTRAIT_SENSOR;

	private SurfaceScrollDetector surfaceScrollDetector;
	private ZoomCamera camera;
	private PinchZoomDetector pinchZoomDetector;
	private float startingZoom;
	private TextureRegion shipTextureRegion;
	private TextureRegion bonusOilTR;

	private double shipXInSector;
	private double shipYInSector;
	private int sectorX;
	private int sectorY;

	@Override
	public EngineOptions onCreateEngineOptions()
	{
		this.camera = new ZoomCamera( 0, 0, Core.width, Core.height );
		this.shipXInSector = this.getIntent().getExtras().getDouble( "shipXInSector" );
		this.shipYInSector = this.getIntent().getExtras().getDouble( "shipYInSector" );
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

			BitmapTexture bonusOil = new BitmapTexture( this.getTextureManager(), new IInputStreamOpener()
			{

				@Override
				public InputStream open() throws IOException
				{
					return getAssets().open( "gdx/bonuses/oil.png" );
				}
			} );
			bonusOil.load();
			bonusOilTR = TextureRegionFactory.extractFromTexture( bonusOil );

		}
		catch( IOException e )
		{
			Debug.e( e );
		}
	}

	@Override
	public Scene onCreateScene()
	{
		final Scene scene = new Scene();
		scene.setBackground( new Background( 0, 0, 0 ) );

		Sector sector = new Sector( Core.getSectorData( this.sectorX, this.sectorY ) );
		Ship ship = new Ship( this.shipXInSector, this.shipYInSector, this.shipTextureRegion,
				this.getVertexBufferObjectManager() );
		ship.addShipChangeListener( this );

		scene.attachChild( sector );
		scene.attachChild( ship );

		return scene;
	}

	@Override
	public void onScrollStarted( ScrollDetector pScollDetector, int pPointerID, float pDistanceX, float pDistanceY )
	{
	}

	@Override
	public void onScroll( ScrollDetector pScollDetector, int pPointerID, float pDistanceX, float pDistanceY )
	{
		Display defaultDisplay = getWindowManager().getDefaultDisplay();
		int w = defaultDisplay.getWidth();
		int h = defaultDisplay.getHeight();
		this.camera.setCenter( this.camera.getCenterX() - pDistanceX * Core.width / w / this.camera.getZoomFactor(),
				this.camera.getCenterY() - pDistanceY * Core.height / h / this.camera.getZoomFactor() );
	}

	@Override
	public void onScrollFinished( ScrollDetector pScollDetector, int pPointerID, float pDistanceX, float pDistanceY )
	{
	}

	@Override
	public boolean onSceneTouchEvent( Scene pScene, TouchEvent pSceneTouchEvent )
	{
		pinchZoomDetector.onTouchEvent( pSceneTouchEvent );
		if( !this.pinchZoomDetector.isZooming() )
		{
			surfaceScrollDetector.onTouchEvent( pSceneTouchEvent );
		}
		return true;
	}

	@Override
	public void onPinchZoomStarted( PinchZoomDetector pPinchZoomDetector, TouchEvent pSceneTouchEvent )
	{
		this.startingZoom = this.camera.getZoomFactor();
	}

	@Override
	public void onPinchZoom( PinchZoomDetector pPinchZoomDetector, TouchEvent pTouchEvent, float pZoomFactor )
	{
		System.out.println( "pointer id: " + pTouchEvent.getPointerID() + ", x: " + pTouchEvent.getX() + ", y: "
				+ pTouchEvent.getY() );
		//moveCenter of camera to the center of pinch
		float centerX = this.camera.getCenterX();
		float centerY = this.camera.getCenterY();
		this.camera.setCenter( centerX - pTouchEvent.getX(), centerY - pTouchEvent.getY() );
		this.camera.setZoomFactor( pZoomFactor * this.startingZoom );
		this.camera.setCenter( centerX, centerY );
	}

	@Override
	public void onPinchZoomFinished( PinchZoomDetector pPinchZoomDetector, TouchEvent pTouchEvent, float pZoomFactor )
	{
	}

	@Override
	public void positionChanged( double posX, double posY )
	{

	}
}
