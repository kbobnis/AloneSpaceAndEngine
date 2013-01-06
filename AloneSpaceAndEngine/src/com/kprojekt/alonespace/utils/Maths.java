package com.kprojekt.alonespace.utils;

/**
 * @author Krzysiek Bobnis 
 */
public class Maths
{

	private static float[] intersectionPoint( float x1, float y1, float x2, float y2, float x3, float y3, float x4, float y4 )
	{

		float d = (x1 - x2) * (y3 - y4) - (y1 - y2) * (x3 - x4);
		if( d == 0 )
			return null;

		float xi = ((x3 - x4) * (x1 * y2 - y1 * x2) - (x1 - x2) * (x3 * y4 - y3 * x4)) / d;
		float yi = ((y3 - y4) * (x1 * y2 - y1 * x2) - (y1 - y2) * (x3 * y4 - y3 * x4)) / d;
		return new float[] { xi, yi };
	}

	public static boolean intersectionInBetween( float x1, float y1, float xMiddle, float yMiddle, float x2, float y2 )
	{
		boolean x1Smallest = (x1 <= xMiddle && xMiddle <= x2);
		boolean x2Smallest = (x1 >= xMiddle && xMiddle >= x2);
		boolean y1Smallest = (y1 <= yMiddle && yMiddle <= y2);
		boolean y2Smallest = (y1 >= yMiddle && yMiddle >= y2);
		return ((x1Smallest || x2Smallest) && (y1Smallest || y2Smallest));
	}

	/**
	 * Checking if intersection point if inside tile x3,y3, x4,y4
	 */
	public static float[] intersectingLines( float x1, float y1, float x2, float y2, float x3, float y3, float x4, float y4 )
	{
		float[] intersect = intersectionPoint( x1, y1, x2, y2, x3, y3, x4, y4 );
		if( intersect == null )
		{
			return null;
		}
		return (intersectionInBetween( x3, y3, intersect[0], intersect[1], x4, y4 ) && intersectionInBetween( x1, y1,
				intersect[0], intersect[1], x2, y2 )) ? intersect : null;
	}

	/**
	 * If you rotate point (px, py) around point (ox, oy) by angle theta you'll get:
	 * @param theta in degrees
	 */
	private static float[] rotateAPoint( float px, float py, float ox, float oy, float _theta )
	{
		float theta = (float)Math.toRadians( _theta );
		float ppx = (float)(Math.cos( theta ) * (px - ox) - Math.sin( theta ) * (py - oy) + ox);
		float ppy = (float)(Math.sin( theta ) * (px - ox) + Math.cos( theta ) * (py - oy) + oy);
		return new float[] { ppx, ppy };
	}

	/**
	 * Project point px, py to a line given by ax, ay, bx, by
	 */
	public static float[] projectPointToLine( float ax, float ay, float bx, float by, float px, float py )
	{
		float[] a_to_b = new float[] { bx - ax, by - by }; //# Finding the vector from A to B, This step can be combined with the next
		float[] perpendicular = new float[] { -a_to_b[1], a_to_b[0] }; //# The vector perpendicular to a_to_b; This step can also be combined with the next
		float[] Q = new float[] { px + perpendicular[0], py + perpendicular[1] }; //# Finding Q, the point "in the right direction" If you want a mess, you can also combine this with the next step.
		return new float[] {
				((ax * by - ay * bx) * (px - Q[0]) - (ax - bx) * (px * Q[1] - py * Q[0]))
						/ ((ax - bx) * (py - Q[1]) - (ay - by) * (py - Q[1])),
				((ax * by - ay * bx) * (py - Q[1]) - (ay - by) * (px * Q[1] - py * Q[0]))
						/ ((ax - bx) * (py - Q[1]) - (ay - by) * (py - Q[1])) };
	}

	public static boolean isLeft( float ax, float ay, float bx, float by, float cx, float cy )
	{
		return ((bx - ax) * (cy - ay) - (by - ay) * (cx - ax)) > 0;
	}

	public static float[] increaseLength( float x, float y )
	{

		return null;
	}

	public static float dist( float x, float y )
	{
		return x > y ? Math.abs( x - y ) : Math.abs( y - x );
	}
}