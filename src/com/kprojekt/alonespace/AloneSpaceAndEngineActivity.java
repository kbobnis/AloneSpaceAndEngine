package com.kprojekt.alonespace;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.andengine.engine.camera.ZoomCamera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.primitive.Line;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.util.FPSLogger;
import org.andengine.input.touch.TouchEvent;
import org.andengine.input.touch.detector.PinchZoomDetector;
import org.andengine.input.touch.detector.PinchZoomDetector.IPinchZoomDetectorListener;
import org.andengine.input.touch.detector.ScrollDetector;
import org.andengine.input.touch.detector.ScrollDetector.IScrollDetectorListener;
import org.andengine.input.touch.detector.SurfaceScrollDetector;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.ui.activity.SimpleBaseGameActivity;

import android.view.Display;

public class AloneSpaceAndEngineActivity extends SimpleBaseGameActivity implements IScrollDetectorListener,
		IPinchZoomDetectorListener, IOnSceneTouchListener

{
	/* Initializing the Random generator produces a comparable result over different versions. */
	private static final long RANDOM_SEED = 1234567890;

	private static final int CAMERA_WIDTH = 128;
	private static final int CAMERA_HEIGHT = 72;

	private static final int LINE_COUNT = 100;

	private List<Line> lines = new ArrayList<Line>();

	private SurfaceScrollDetector surfaceScrollDetector;

	private ZoomCamera camera;

	private PinchZoomDetector pinchZoomDetector;

	private float startingZoom;

	@Override
	public EngineOptions onCreateEngineOptions()
	{

		this.camera = new ZoomCamera( 0, 0, CAMERA_WIDTH, CAMERA_HEIGHT );

		return new EngineOptions( true, ScreenOrientation.LANDSCAPE_FIXED, new RatioResolutionPolicy( CAMERA_WIDTH,
				CAMERA_HEIGHT ), camera );
	}

	@Override
	public void onCreateResources()
	{

	}

	@Override
	public Scene onCreateScene()
	{
		this.mEngine.registerUpdateHandler( new FPSLogger() );

		final Scene scene = new Scene();
		scene.setBackground( new Background( 0.09804f, 0.6274f, 0.8784f ) );

		final Random random = new Random( RANDOM_SEED );

		final VertexBufferObjectManager vertexBufferObjectManager = this.getVertexBufferObjectManager();
		float lastX = 0;
		float lastY = 0;
		for( int i = 0; i < LINE_COUNT; i++ )
		{
			final float x1 = random.nextFloat() * CAMERA_WIDTH;
			final float y1 = random.nextFloat() * CAMERA_HEIGHT;
			final float lineWidth = 10; // random.nextFloat() * 5;

			final Line line = new Line( x1, y1, lastX, lastY, lineWidth, vertexBufferObjectManager );
			lastX = x1;
			lastY = y1;

			line.setColor( random.nextFloat(), random.nextFloat(), random.nextFloat() );

			scene.attachChild( line );
			this.lines.add( line );
		}
		surfaceScrollDetector = new SurfaceScrollDetector( this );
		pinchZoomDetector = new PinchZoomDetector( this );
		scene.setOnSceneTouchListener( this );

		return scene;
	}

	@Override
	public void onScrollStarted( ScrollDetector pScollDetector, int pPointerID, float pDistanceX, float pDistanceY )
	{
		// TODO @Krzysiek Auto-generated method stub

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

		// TODO @Krzysiek Auto-generated method stub
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
		//for( Line line : this.lines )
		//{
		//line.setLineWidth( line.getLineWidth() * this.camera.getZoomFactor() );
		//}

	}

	@Override
	public void onPinchZoomFinished( PinchZoomDetector pPinchZoomDetector, TouchEvent pTouchEvent, float pZoomFactor )
	{
		// TODO @Krzysiek Auto-generated method stub

	}
}
