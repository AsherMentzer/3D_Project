package geometries;

import java.util.LinkedList;
import java.util.List;

import primitives.*;
import static primitives.Util.*;

/**
 * Sphere class represents sphere in 3D
 * 
 * @author Asher Mentzer & Mendy Kahana
 *
 */
public class Sphere extends Geometry {

	/**
	 * the point of the center of the sphere
	 */
	private Point3D center;

	/**
	 * the radius of the sphere
	 */
	private double radius;

	/**
	 * constructor
	 * 
	 * @param center the center point
	 * @param radius the radius of the sphere
	 */
	public Sphere(Point3D center, double radius) {
		super();
		this.center = center;
		this.radius = radius;
	}

	/**
	 * getter
	 * 
	 * @return the center point
	 */
	public Point3D getCenter() {
		return center;
	}

	/**
	 * getter
	 * 
	 * @return the radius of the sphere
	 */
	public double getRadius() {
		return radius;
	}

	@Override
	public String toString() {
		return "Sphere [center=" + center + ", radius=" + radius + "]";
	}

	/**
	 * implement the interface to find the normal to this sphere by specific point
	 */
	public Vector getNormal(Point3D point) {
		return point.subtract(center).normalize();
	}

	/*
	public List<Point3D> findIntersections(Ray ray) {
		Point3D p0 = ray.getP0();
		Vector v = ray.getDir();
		Vector u;
		try {//check if is not the same point /get vector zero
			u = center.subtract(p0);

			double Tm = alignZero(v.dotProduct(u));
			double d = alignZero(Math.sqrt(u.lengthSquared() - Tm * Tm));
			if (d >= radius)//no intersections points
				return null;
			double Th = alignZero(Math.sqrt(radius * radius - d * d));
			double t1 = Tm + Th;
			double t2 = Tm - Th;
			Point3D p1;
			Point3D p2;
			List<Point3D> l = null;
			
			//check that any point exist else don't create list
			if (t1 > 0 || t2 > 0) {
				l = new LinkedList<Point3D>();

				if (t1 > 0) {
					p1 = ray.getPoint(t1);
					l.add(p1);
				}
				if (t2 > 0) {
					p2 = ray.getPoint(t2);
					l.add(p2);
				}
			}
			return l;
		} catch (IllegalArgumentException e) {
			List<Point3D> l = new LinkedList<Point3D>();
			Point3D p1 =ray.getPoint(radius);
			l.add(p1);
			return l;
		}
	}
	*/
	
	/**
	 * implement the interface to find all the intersections
	 * between ray and this sphere 
	 */
	@Override
	public List<GeoPoint> findGeoIntersections(Ray ray) {
		Point3D p0 = ray.getP0();
		Vector v = ray.getDir();
		Vector u;
		try {//check if is not the same point /get vector zero
			u = center.subtract(p0);

			double Tm = alignZero(v.dotProduct(u));
			double d = alignZero(Math.sqrt(u.lengthSquared() - Tm * Tm));
			if (d >= radius)//no intersections points
				return null;
			double Th = alignZero(Math.sqrt(radius * radius - d * d));
			double t1 = Tm + Th;
			double t2 = Tm - Th;
			Point3D p1;
			Point3D p2;
			List<GeoPoint> l = null;
			
			//check that any point exist else don't create list
			if (t1 > 0 || t2 > 0) {
				l = new LinkedList<GeoPoint>();

				if (t1 > 0) {
					p1 = ray.getPoint(t1);
					l.add(new GeoPoint(this,p1));
				}
				if (t2 > 0) {
					p2 = ray.getPoint(t2);
					l.add(new GeoPoint(this,p2));
				}
			}
			return l;
		} catch (IllegalArgumentException e) {
			/*List<GeoPoint> l = new LinkedList<GeoPoint>();
			Point3D p1 =ray.getPoint(radius);
			l.add(p1);*/
			Point3D p1 =ray.getPoint(radius);
			return List.of(new GeoPoint(this,p1));
		}
	}
}
