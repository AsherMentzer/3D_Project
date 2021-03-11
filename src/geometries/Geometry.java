package geometries;

import primitives.Vector;
import primitives.Point3D;

/**
 * interface for all the  shapes
 * with Func to return the normal to this shape 
 * @author Asher Mentzer & Mendy Kahana
 *
 */
public interface Geometry {
	public Vector getNormal(Point3D p);
}
