/**
 * 
 */
package geometries;

import java.util.List;

import primitives.*;

/**
 * interface that all the geometries shapes implement with function to find all
 * the intersection points with specific ray and return list of them
 * 
 * @author Asher Mentzer & Mendy Kahana
 *
 */
public interface Intersectable {
	public List<Point3D> findIntersections(Ray ray);
}
