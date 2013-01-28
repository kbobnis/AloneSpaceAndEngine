package com.kprojekt.alonespace.utils;

import java.io.IOException;
import java.io.InputStream;

import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;

/**
 * @author Krzysiek Bobnis
 * since 03:35:01 06-01-2013
 */
public class AssetHelper
{

	public static Drawable loadImage( String name, AssetManager assetManager )
	{
		try
		{
			InputStream ims = assetManager.open( name );
			return Drawable.createFromStream( ims, null );
		}
		catch( IOException ex )
		{
			//throw new RuntimeException( "Image " + name + " could not be loaded" );
			return null;
		}
	}

}
