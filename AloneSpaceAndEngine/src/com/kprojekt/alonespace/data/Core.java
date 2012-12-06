package com.kprojekt.alonespace.data;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;

import com.kprojekt.alonespace.data.minigame.SectorData;

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

	private static Map<Integer, HashMap<Integer, SectorData>> sectorsData = new HashMap<Integer, HashMap<Integer, SectorData>>();

	public static float pixelsToMeters( float pixels )
	{
		return pixels / Core.PixelsPerMeterInGraphics;
	}

	public static float metersToPixels( float meters )
	{
		return meters * Core.PixelsPerMeterInGraphics;
	}

	public static SectorData getSectorData( int sectorX, int sectorY )
	{
		if( Core.sectorsData.containsKey( Integer.valueOf( sectorX ) )
				&& Core.sectorsData.get( Integer.valueOf( sectorX ) ).containsKey( Integer.valueOf( sectorY ) ) )
			return Core.sectorsData.get( sectorX ).get( sectorY );
		return null;
	}
}
