package com.kprojekt.alonespace.data;

import java.util.ArrayList;
import java.util.List;
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

	private static List<List<SectorData>> sectorsData = new ArrayList<List<SectorData>>();

	public static SectorData getSectorData( int sectorX, int sectorY )
	{
		return Core.sectorsData.get( sectorX ).get( sectorY );
	}
}
