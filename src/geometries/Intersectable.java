/**
 * 
 */
package geometries;

import java.util.List;
import java.util.stream.Collectors;

import primitives.*;

/**
 * interface that all the geometries shapes implement with function to find all
 * the intersection points with specific ray and return list of them
 * 
 * @author Asher Mentzer & Mendy Kahana
 *
 */
public interface Intersectable {
	/**
	 * assist class for point on geometry
	 * 
	 * @author Dan
	 *
	 */
	public static class GeoPoint {
		public Geometry geometry;
		public Point3D point;

		/**
		 * constructor
		 * 
		 * @param geometry the geometry shape
		 * @param point    the point
		 */
		public GeoPoint(Geometry geometry, Point3D point) {
			super();
			this.geometry = geometry;
			this.point = point;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (!(obj instanceof GeoPoint))
				return false;
			GeoPoint geo = (GeoPoint) obj;
			return geo.geometry == geometry && geo.point.equals(point);
		}
		
		@Override
		public String toString() {
			return "" + geometry + ": " + point;
		}
	}

	/**
	 * default function to find point of intersections between the ray to geometry
	 * 
	 * @param ray
	 * @return list of intersections points
	 */
	default List<Point3D> findIntersections(Ray ray) {
		var geoList = findGeoIntersections(ray);
		return geoList == null ? null : geoList.stream().map(gp -> gp.point).collect(Collectors.toList());
	}

	/**
	 * function to find point of intersections between the ray to geometry
	 * 
	 * @param ray
	 * @return the GeoPoints
	 */
	// public List<GeoPoint> findGeoIntersections(Ray ray);
	default List<GeoPoint> findGeoIntersections(Ray ray) {
		return findGeoIntersections(ray, Double.POSITIVE_INFINITY);
	}

	List<GeoPoint> findGeoIntersections(Ray ray, double maxDistance);

}
