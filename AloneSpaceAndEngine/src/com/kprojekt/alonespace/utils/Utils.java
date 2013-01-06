package com.kprojekt.alonespace.utils;

/**
 * @author Krzysiek Bobnis
 * @since 17:58:24 06-01-2013
 */
public class Utils<T>
{
	public static <T> T safeGet( T one, T two )
	{
		return one == null ? two : one;
	}

}
