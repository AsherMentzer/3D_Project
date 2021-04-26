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
	 * @author Dan
	 *
	 */
	public static class GeoPoint {
	    public Geometry geometry;
	    public Point3D point;
	    
	    /**
	     *constructor 
	     * @param geometry the geometry shape
	     * @param point the point
	     */
		public GeoPoint(Geometry geometry, Point3D point) {
			super();
			this.geometry = geometry;
			this.point = point;
		}

		@Override
		public boolean equals(Object obj) {
			// TODO Auto-generated method stub
			if (this == obj)
				return true;
			if (obj == null)
				return false;	
			if (!(obj instanceof GeoPoint))
				return false;
			GeoPoint geo= (GeoPoint)obj;
			if(!(geo.geometry instanceof Plane && geometry instanceof Plane
				||geo.geometry instanceof Sphere && geometry instanceof Sphere
				||geo.geometry instanceof Tube && geometry instanceof Tube
				||geo.geometry instanceof Triangle && geometry instanceof Triangle
				||geo.geometry instanceof Cylinder && geometry instanceof Cylinder
				||geo.geometry instanceof Polygon && geometry instanceof Polygon))
				return false;
			return geo.point.equals(point);
		}
	    
	}

	default List<Point3D> findIntersections(Ray ray) {
	    var geoList = findGeoIntersections(ray);
	    return geoList == null ? null
	                           : geoList.stream().map(gp -> gp.point).collect(Collectors.toList());
	}

	
	//public List<Point3D> findIntersections(Ray ray);
	public List<GeoPoint> findGeoIntersections(Ray ray);
	
}
