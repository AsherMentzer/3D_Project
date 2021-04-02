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
public class Sphere implements Geometry {

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

	public Vector getNormal(Point3D point) {
		return point.subtract(center).normalize();
	}

	public List<Point3D> findIntersections(Ray ray) {
		Point3D p0 = ray.getP0();
		Vector v = ray.getDir();
		Vector u;
		try {
			u = center.subtract(p0);

			double Tm = alignZero(v.dotProduct(u));
			double d = alignZero(Math.sqrt(u.lengthSquared() - Tm * Tm));
			if (d >= radius)
				return null;
			double Th = alignZero(Math.sqrt(radius * radius - d * d));
			double t1 = Tm + Th;
			double t2 = Tm - Th;
			Point3D p1;
			Point3D p2;
			List<Point3D> l = null;
			if (t1 > 0 || t2 > 0) {
				l = new LinkedList<Point3D>();

				if (t1 > 0) {
					p1 = p0.add(v.scale(t1));
					l.add(p1);
				}
				if (t2 > 0) {
					p2 = p0.add(v.scale(t2));
					l.add(p2);
				}
			}
			return l;
		} catch (IllegalArgumentException e) {
			List<Point3D> l = new LinkedList<Point3D>();
			Point3D p1 = p0.add(v.scale(radius));
			l.add(p1);
			return l;
		}
	}
}
