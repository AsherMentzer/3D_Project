package geometries;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import geometries.Intersectable.GeoPoint;
import primitives.Point3D;
import primitives.Ray;
import scene.Box;
import scene.Box.*;

/**
 * class for all the intersectable shapes of all the shapes of our image
 */
public class Geometries extends Intersectable {

	private List<Intersectable> intersections;
	private Intersectable _lastAdded;
	
	/**
	 * constructor that start with empty list
	 */
	public Geometries() {
		super();
		this.intersections = new LinkedList();
		_minBoundary = new Point3D(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY);
		_maxBoundary = new Point3D(Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY);
	}

	/**
	 * constructor with array of intersectable shapes to add to our list
	 * 
	 * @param geometries the array
	 */
	public Geometries(Intersectable... geometries) {
		this();
		add(geometries);
		//this.intersections = List.of(geometries);
	}

	/**
	 * function to add array of intersectable shapes to our list
	 * 
	 * @param geometries
	 */
	public void add(Intersectable... geometries) {
		for (Intersectable intersectable : geometries) {
			intersections.add(intersectable);
			_lastAdded = intersectable;
			setMinCoordinates();
			setMaxCoordinates();
		}
	}

	
	/**
	 * Function to find all the intersections between the ray to all the shapes
	 */
	@Override
	public List<GeoPoint> findGeoIntersections(Ray ray,double maxDistance) {
		if (intersections.isEmpty())
			return null;
		
		List<GeoPoint> l = new LinkedList<GeoPoint>();
		for (Intersectable i : intersections) {
			var geoIntersections = i.findGeoIntersections(ray,maxDistance);
			if (geoIntersections != null)
				l.addAll(geoIntersections);
		}
		if (l==null||l.isEmpty())
			return null;
		
		
			return l;
	}

	/**
	 * This function returns only the relevant point of the intersection using the
	 * help of regular grid structure if the box is null that means we call the
	 * regular find intersection (without acceleration)
	 * 
	 * @param ray            Ray that intersect
	 * @param box            box of the scene
	 * @param shadowRaysCase if its shadow ray we traverse always all the way .
	 * @param dis            distannce for find intersection
	 * @return the relevant point
	 */
	public List<GeoPoint> findRelevantIntersections(Ray ray, Box box, boolean shadowRaysCase, double dis) {
		if (box == null)
			return this.findGeoIntersections(ray, dis);
		return box.findIntersectionsInTheBox(ray, shadowRaysCase, dis);
	}
	
	public void setMinCoordinates() {
		double x, y, z;
		x = _lastAdded.getMinCoordinates().getX();
		y = _lastAdded.getMinCoordinates().getY();
		z = _lastAdded.getMinCoordinates().getZ();
		double minX = _minBoundary.getX();
		double minY = _minBoundary.getY();
		double minZ = _minBoundary.getZ();
		if (x < minX)
			minX = x;
		if (y < minY)
			minY = y;
		if (z < minZ)
			minZ = z;
		_minBoundary = new Point3D(minX, minY, minZ);
	}

	public void setMaxCoordinates() {
		double x, y, z;
		x = _lastAdded.getMaxCoordinates().getX();
		y = _lastAdded.getMaxCoordinates().getY();
		z = _lastAdded.getMaxCoordinates().getZ();
		double maxX = _maxBoundary.getX();
		double maxY = _maxBoundary.getY();
		double maxZ = _maxBoundary.getZ();
		if (x > maxX)
			maxX = x;
		if (y > maxY)
			maxY = y;
		if (z > maxZ)
			maxZ = z;
		_maxBoundary = new Point3D(maxX, maxY, maxZ);
	}
	
	public List<Intersectable> getShapes() {
		return this.intersections;
	}
}