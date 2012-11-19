package com.kprojekt.alonespace.activities.andengine;

import java.io.IOException;
import java.io.InputStream;

import org.andengine.engine.camera.ZoomCamera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
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

public class MinigameActivity extends SimpleBaseGameActivity implements IScrollDetectorListener,
		IPinchZoomDetectorListener, IOnSceneTouchListener

{
	public static ScreenOrientation orientation = ScreenOrientation.PORTRAIT_SENSOR;
	public static RatioResolutionPolicy resPolicy = new RatioResolutionPolicy( Core.width, Core.height );

	private SurfaceScrollDetector surfaceScrollDetector;
	private ZoomCamera camera;
	private PinchZoomDetector pinchZoomDetector;
	private float startingZoom;
	private TextureRegion shipTextureRegion;
	private TextureRegion star1TR;
	private TextureRegion star2TR;
	private TextureRegion star3TR;
	private TextureRegion bonusOilTR;

	@Override
	public EngineOptions onCreateEngineOptions()
	{
		this.camera = new ZoomCamera( 0, 0, Core.width, Core.height );

		return new EngineOptions( false, MinigameActivity.orientation, resPolicy, camera );
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

			BitmapTexture star1Text = new BitmapTexture( this.getTextureManager(), new IInputStreamOpener()
			{

				@Override
				public InputStream open() throws IOException
				{
					return getAssets().open( "gdx/stars/star.png" );
				}
			} );
			star1Text.load();
			star1TR = TextureRegionFactory.extractFromTexture( star1Text );

			BitmapTexture star2Text = new BitmapTexture( this.getTextureManager(), new IInputStreamOpener()
			{

				@Override
				public InputStream open() throws IOException
				{
					return getAssets().open( "gdx/stars/star2.png" );
				}
			} );
			star2Text.load();
			star2TR = TextureRegionFactory.extractFromTexture( star2Text );

			BitmapTexture star3Text = new BitmapTexture( this.getTextureManager(), new IInputStreamOpener()
			{

				@Override
				public InputStream open() throws IOException
				{
					return getAssets().open( "gdx/stars/star3.png" );
				}
			} );
			star3Text.load();
			star3TR = TextureRegionFactory.extractFromTexture( star3Text );

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
		//this.mEngine.registerUpdateHandler( new FPSLogger() );

		final Scene scene = new Scene();
		scene.setBackground( new Background( 0, 0, 0 ) );

		float x = (this.camera.getWidth() - this.shipTextureRegion.getWidth()) / 2;
		float y = (this.camera.getHeight() - this.shipTextureRegion.getHeight()) / 2;

		Sprite face = new Sprite( x, y, this.shipTextureRegion, this.getVertexBufferObjectManager() );
		face.setRotation( 90f );

		//StarsManager stars = new StarsManager( new CelestialBody[] { new CelestialBody( this.star1TR, 0.01f, 0.2f, new Bonuses[] { new Bonus( Bonus.Oil, 100) ),
		//new CelestialBody( this.star2TR, 0.02f, 0.2f ), new CelestialBody( this.star3TR, 0.02f, 0.02f ) } );

		//scene.attachChild(stars);
		scene.attachChild( face );

		//surfaceScrollDetector = new SurfaceScrollDetector( stars );
		//pinchZoomDetector = new PinchZoomDetector( stars );
		//scene.setOnSceneTouchListener( stars );

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
}
