package com.kprojekt.alonespace.data;

import java.util.Random;

import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.opengl.font.IFont;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

/**
 * 
 */
public class Core
{
	public static final int widthInMeters = 400;
	public static int heightInMeters;
	public static final float PixelsPerMeterInGraphics = 4f;

	public static boolean fullScreen = true;
	public static ScreenOrientation orientation = ScreenOrientation.LANDSCAPE_SENSOR;
	public static Random random = new Random( System.currentTimeMillis() );
	public static RatioResolutionPolicy ratioResPolicy;

	public static IFont font;
	public static Settings settings = new Settings();

	public static TextureRegions regions = new TextureRegions();
	public static VertexBufferObjectManager manager;
	public static PhysicsWorld mPhysicsWorld;

	public static float pixelsToMeters( float pixels )
	{
		return pixels / Core.PixelsPerMeterInGraphics;
	}

	public static float metersToPixels( float meters )
	{
		return meters * Core.PixelsPerMeterInGraphics;
	}

}
