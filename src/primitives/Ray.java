package primitives;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;
import geometries.Intersectable.GeoPoint;

/**
 * base class to rpresent ray by Point and normal vector of the direction
 *
 */
public class Ray {
	private Point3D p0;
	private Vector dir;

	private static final double DELTA = 0.1;

	/**
	 * constructor
	 * 
	 * @param p the point
	 * @param v the direction vector
	 */
	public Ray(Point3D p, Vector v) {
		// check if the vector is normalized
		if (!(v.length() == 1))
			v = v.normalize();
		p0 = p;
		dir = v;
	}

	/**
	 * constructor to assist to move the new ray to reflection and refraction 
	 * @param normal the normal vector to this geometry in this point
	 * @param point the point of intersection
	 * @param direction the direction of the new ray
	 */
	public Ray(Vector normal, Point3D point, Vector direction) {
		double sign = normal.dotProduct(direction);
		if (sign >= 0)
			p0 = point.add(normal.scale(DELTA));
		else
			p0 = point.add(normal.scale(-DELTA));
		dir = direction;
	}

	/**
	 * getter
	 * 
	 * @return the p0
	 */
	public Point3D getP0() {
		return p0;
	}

	/**
	 * getter
	 * 
	 * @return the vector of the direction
	 */
	public Vector getDir() {
		return dir;
	}

	/**
	 * function to get a point on the ray where v scale by t
	 * 
	 * @param t double to scale the direction vector
	 * @return the point on the ray where v scale by t
	 */
	public Point3D getPoint(double t) {
		return p0.add(dir.scale(t));
	}

	@Override
	public String toString() {
		return "Ray " + p0 + dir + "---";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Ray))
			return false;
		Ray other = (Ray) obj;
		return p0.equals(other.p0) && dir.equals(other.dir);
	}

	/**
	 * get list of points and return the closest point
	 * 
	 * @param points the list
	 * @return the closest point
	 */
	public Point3D getClosestPoint(List<Point3D> points) {
		if (points.isEmpty())
			return null;
		else {
			Point3D point = points.get(0);
			double distance = point.distance(p0);
			for (var p : points) {
				if (p.distance(p0) < distance) {
					point = p;
					distance = p.distance(p0);
				}
			}
			return point;
		}

	}

	/**
	 * get list of GeoPoints and return the closest GeoPoint
	 * 
	 * @param points
	 * @return the closest GeoPoint
	 */
	public GeoPoint getClosestGeoPoint(List<GeoPoint> points) {
		if (points.isEmpty())
			return null;
		else {
			GeoPoint point = points.get(0);
			double distance = point.point.distance(p0);
			for (var p : points) {
				if (p.point.distance(p0) < distance) {
					point = p;
					distance = p.point.distance(p0);
				}
			}
			return point;
		}

	}
}
