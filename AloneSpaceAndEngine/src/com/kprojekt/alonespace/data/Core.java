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

	public static final int width = 1500;
	public static int height;
	public static boolean fullScreen = true;
	public static ScreenOrientation orientation = ScreenOrientation.LANDSCAPE_SENSOR;
	public static Random random = new Random( System.currentTimeMillis() );
	public static RatioResolutionPolicy ratioResPolicy;

	private static Map<Integer, HashMap<Integer, SectorData>> sectorsData = new HashMap<Integer, HashMap<Integer, SectorData>>();

	public static SectorData getSectorData( int sectorX, int sectorY )
	{
		if( Core.sectorsData.containsKey( Integer.valueOf( sectorX ) )
				&& Core.sectorsData.get( Integer.valueOf( sectorX ) ).containsKey( Integer.valueOf( sectorY ) ) )
			return Core.sectorsData.get( sectorX ).get( sectorY );
		return null;
	}
}
