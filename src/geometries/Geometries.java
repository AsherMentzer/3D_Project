/**
 * 
 */
package geometries;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import geometries.Intersectable.GeoPoint;
import primitives.Point3D;
import primitives.Ray;

/**
 * class for all the intersectable shapes of all the shapes of our image
 */
public class Geometries implements Intersectable {

	private List<Intersectable> intersections;

	/**
	 * constructor that start with empty list
	 */
	public Geometries() {
		super();
		this.intersections = new LinkedList();
	}

	/**
	 * constructor with array of intersectable shapes to add to our list
	 * 
	 * @param geometries the array
	 */
	public Geometries(Intersectable... geometries) {
		super();
		this.intersections = List.of(geometries);
	}

	/**
	 * function to add array of intersectable shapes to our list
	 * 
	 * @param geometries
	 */
	public void add(Intersectable... geometries) {
		List newObjects = Arrays.asList(geometries);
		this.intersections.addAll(newObjects);
	}

	
	/**
	 * Function to find all the intersections between the ray to all the shapes
	 */
	@Override
	public List<GeoPoint> findGeoIntersections(Ray ray) {
		if (intersections.isEmpty())
			return null;
		
		List<GeoPoint> l = new LinkedList<GeoPoint>();
		for (Intersectable i : intersections) {
			var geoIntersections = i.findGeoIntersections(ray);
			if (geoIntersections != null)
				l.addAll(geoIntersections);
		}
		if (l==null||l.isEmpty())
			return null;
		
		
			return l;
	}
}
