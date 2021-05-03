package geometries;

import java.util.LinkedList;
import java.util.List;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;
import static primitives.Util.*;

/**
 * plane class represents two-dimensional Triangle in 3D Cartesian coordinate by
 * point and vector normal to the plane
 * 
 * @author Asher Mentzer & Mendy Kahana
 *
 */
public class Plane extends Geometry {
	/**
	 * Associated point in which the plane lays
	 */
	private Point3D p0;
	private Vector normal;

	/**
	 * constructor
	 * 
	 * @param p0     the point
	 * @param normal the vector that normal to the plane
	 */
	public Plane(Point3D p0, Vector normal) {
		super();
		this.p0 = p0;
		this.normal = normal;
	}

	/**
	 * constructor that get 3 points and set one of them to witch the plane lays and
	 * calculate the normal vector
	 * 
	 * @param p0 point 0
	 * @param p1 point 1
	 * @param p2 point 2
	 */
	public Plane(Point3D p0, Point3D p1, Point3D p2) {
		super();
		this.p0 = p0;/** Associated point in which the plane lays */

		Vector v1 = p1.subtract(p0);
		Vector v2 = p2.subtract(p0);
		if (v1.getHead() == Point3D.ZERO || v2.getHead() == Point3D.ZERO)
			throw new IllegalArgumentException("the points are the same");

		if (v1.crossProduct(v2).getHead() == Point3D.ZERO)
			throw new IllegalArgumentException("the points are on the same line");

		this.normal = v1.crossProduct(v2).normalize();
	}

	/**
	 * getter
	 * 
	 * @return the point 0
	 */
	public Point3D getP0() {
		return p0;
	}

	/**
	 * getter
	 * 
	 * @return the normal vector
	 */
	public Vector getNormal() {
		return this.normal;
	}

	@Override
	public String toString() {
		return "Plane [p0=" + p0 + ", normal=" + normal + "]";
	}

	/**
	 * implement the interface to find the normal to this plane by specific point
	 */
	public Vector getNormal(Point3D p) {
		// TODO Auto-generated method stub
		return this.normal;
	}

	
	/**
	 * implement the interface to find all the intersections between ray and this
	 * plane
	 */
	@Override
	public List<GeoPoint> findGeoIntersections(Ray ray) {
		Vector v = ray.getDir();
		Point3D q0 = ray.getP0();
		double nv = normal.dotProduct(v);
		if (isZero(nv)) {
			return null;
		}
		Vector Q0P0;
		try {
			Q0P0 = p0.subtract(q0);
		} catch (Exception e) {
			return null;
		}
		double t = Q0P0.dotProduct(normal) / nv;
		if (t < 0 || isZero(t))
			return null;
		Point3D p = ray.getPoint(t);
		return List.of(new GeoPoint(this,p));
	}
}
