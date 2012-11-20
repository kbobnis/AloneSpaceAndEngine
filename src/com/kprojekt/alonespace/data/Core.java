package com.kprojekt.alonespace.data;

import java.util.Random;

import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;

/**
 * 
 */
public class Core
{

	public static Ship ship = new Ship();
	public static final int width = 1500;
	public static int height;
	public static boolean fullScreen = true;
	public static ScreenOrientation orientation = ScreenOrientation.LANDSCAPE_SENSOR;
	public static Random random = new Random( System.currentTimeMillis() );
	public static RatioResolutionPolicy ratioResPolicy;

}
