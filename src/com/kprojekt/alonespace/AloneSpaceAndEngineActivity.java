package com.kprojekt.alonespace;

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
import org.andengine.entity.util.FPSLogger;
import org.andengine.input.touch.TouchEvent;
import org.andengine.input.touch.detector.PinchZoomDetector;
import org.andengine.input.touch.detector.PinchZoomDetector.IPinchZoomDetectorListener;
import org.andengine.input.touch.detector.ScrollDetector;
import org.andengine.input.touch.detector.ScrollDetector.IScrollDetectorListener;
import org.andengine.input.touch.detector.SurfaceScrollDetector;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.bitmap.BitmapTexture;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.texture.region.TextureRegionFactory;
import org.andengine.ui.activity.SimpleBaseGameActivity;
import org.andengine.util.adt.io.in.IInputStreamOpener;
import org.andengine.util.debug.Debug;

import android.view.Display;

public class AloneSpaceAndEngineActivity extends SimpleBaseGameActivity implements IScrollDetectorListener,
		IPinchZoomDetectorListener, IOnSceneTouchListener

{
	private static final int CAMERA_WIDTH = 1280;
	private static final int CAMERA_HEIGHT = 720;

	private SurfaceScrollDetector surfaceScrollDetector;
	private ZoomCamera camera;
	private PinchZoomDetector pinchZoomDetector;
	private float startingZoom;
	private BitmapTexture mTexture;
	private TextureRegion mFaceTextureRegion;

	@Override
	public EngineOptions onCreateEngineOptions()
	{

		this.camera = new ZoomCamera( 0, 0, CAMERA_WIDTH, CAMERA_HEIGHT );

		return new EngineOptions( true, ScreenOrientation.LANDSCAPE_SENSOR, new RatioResolutionPolicy( CAMERA_WIDTH,
				CAMERA_HEIGHT ), camera );
	}

	@Override
	public void onCreateResources()
	{
		try
		{
			this.mTexture = new BitmapTexture( this.getTextureManager(), new IInputStreamOpener()
			{
				@Override
				public InputStream open() throws IOException
				{
					return getAssets().open( "gfx/ship.png" );
				}
			} );

			this.mTexture.load();
			this.mFaceTextureRegion = TextureRegionFactory.extractFromTexture( this.mTexture );
		}
		catch( IOException e )
		{
			Debug.e( e );
		}
	}

	@Override
	public Scene onCreateScene()
	{
		this.mEngine.registerUpdateHandler( new FPSLogger() );

		final Scene scene = new Scene();
		scene.setBackground( new Background( 0, 0, 0 ) );

		float x = (this.camera.getWidth() - this.mFaceTextureRegion.getWidth()) / 2;
		float y = (this.camera.getHeight() - this.mFaceTextureRegion.getHeight()) / 2;

		Sprite face = new Sprite( x, y, this.mFaceTextureRegion, this.getVertexBufferObjectManager() );
		face.setRotation( 90f );
		scene.attachChild( face );

		surfaceScrollDetector = new SurfaceScrollDetector( this );
		pinchZoomDetector = new PinchZoomDetector( this );
		scene.setOnSceneTouchListener( this );

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
		this.camera.setCenter(
				this.camera.getCenterX() - pDistanceX * this.CAMERA_WIDTH / w / this.camera.getZoomFactor(),
				this.camera.getCenterY() - pDistanceY * this.CAMERA_HEIGHT / h / this.camera.getZoomFactor() );
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
