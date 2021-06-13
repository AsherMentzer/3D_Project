package geometries;

import java.util.List;

import primitives.*;
import static primitives.Util.*;

/**
 * Tube class represents Tube in the 3D
 * 
 * @author
 *
 */
public class Tube extends Geometry {

	/**
	 * the Ray
	 */
	protected Ray axisRay;
	/**
	 * the radius of the tube
	 */
	protected double radius;

	/**
	 * constructor
	 * 
	 * @param axisRay the Ray
	 * @param radius  the radius
	 */
	public Tube(Ray axisRay, double radius) {
		super();
		this.axisRay = axisRay;
		this.radius = radius;
		setMinCoordinates();
		setMaxCoordinates();
	}

	/**
	 * getter
	 * 
	 * @return the ray
	 */
	public Ray getAxisRay() {
		return axisRay;
	}

	/**
	 * getter
	 * 
	 * @return the radius
	 */
	public double getRadius() {
		return radius;
	}

	@Override
	public String toString() {
		return "Tube [axisRay=" + axisRay + ", radius=" + radius + "]";
	}

	/**
	 * implement the interface to find the normal to this Tube by specific point
	 */
	public Vector getNormal(Point3D point) {

		double t = axisRay.getDir().dotProduct(point.subtract(axisRay.getP0()));
		if (t == 0)
			return point.subtract(axisRay.getP0()).normalize();

		Point3D O = axisRay.getP0().add(axisRay.getDir().scale(t));
		return point.subtract(O).normalize();
	}

	/**
	 * implement the interface to find all the intersections between ray and this
	 * tube
	 */
	/*
	@Override
	public List<GeoPoint> findGeoIntersections(Ray ray,double maxDistance) {
		// TODO Auto-generated method stub
		return null;
	}*/
	/*
	@Override
	public void setMinCoordinates() {
		double x = Double.NEGATIVE_INFINITY, y = x, z = y;
		_minBoundary = new Point3D(x, y, z);
	}
	@Override
	public void setMaxCoordinates() {
		double x = Double.POSITIVE_INFINITY, y = x, z = y;
		_maxBoundary = new Point3D(x, y, z);
	}
*/
	@Override
	public List<GeoPoint> findGeoIntersections(Ray ray, double max) {
		Point3D p0 = ray.getP0();
		Point3D pa = axisRay.getP0();
		Vector v = ray.getDir();
		Vector va = axisRay.getDir();
		Vector daltaVec = null;
		try {
			daltaVec = p0.subtract(pa);
		} catch (Exception e) {
			// the ray of tube and ray that candidate to intersect are the same
			if (v.equals(va) || v.equals(va.scale(-1)))
				return null;
		}
		Vector temp1, temp2;
		double _scale = alignZero(v.dotProduct(va));
		try {
			temp1 = v.subtract(va.scale(_scale));
		} catch (Exception e) {
			if (_scale == 0) {
				// v is ortogonal to va
				temp1 = v;
			} else {
				// v and va are parallal
				return null;
			}
		}
		double a, b, c, discriminant;
		a = alignZero(temp1.lengthSquared()); // if temp1 is null, already returned null
		if (daltaVec == null || isZero(daltaVec.dotProduct(va)))
			temp2 = daltaVec;
		else {
			try {
				temp2 = daltaVec == null ? temp2 = null
						: daltaVec.subtract(va.scale(alignZero(daltaVec.dotProduct(va))));
			} catch (IllegalArgumentException e) {
				// va is equal to dalta vector by scale
				temp2 = null;
			}
		}
		b = temp2 == null ? 0d : 2 * (alignZero(temp1.dotProduct(temp2)));
		c = temp2 == null ? -radius * radius : alignZero(temp2.lengthSquared() - radius * radius);
		discriminant = alignZero(b * b - 4 * a * c);
		if (discriminant < 0)
			// no intersection
			return null;
		if (discriminant == 0) {
			// in this case we got one solution - means maximum one intersection
			// if The ray starting outside and we got only one solution that means is a
			// tangens point - no intersection
			if (p0.distance(pa) > radius)
				return null;
			double t = alignZero((-1 * b) / (2 * a));// a = temp1, so in this point, a for sure not zero

			if (t <= 0) {
				// no intersection(the intersection in the opposite direction)
				// or (if t=0) the ray start at the tube and we have only tangent point
				return null;
			}
			return List.of(new GeoPoint(this, ray.getPoint(t)));
		}
		// at this point we know that we have two solutions we need to choose the
		// relevant(s)(positive ones)
		discriminant = Math.sqrt(discriminant);
		double solution_1 = alignZero(((-b) + discriminant) / (2 * a));
		double solution_2 = alignZero((-b) - discriminant) / (2 * a);
		double dis1 = alignZero(solution_1 - max);
		double dis2 = alignZero(solution_2 - max);
		if (solution_1 > 0 && dis1 <= 0 && solution_2 > 0 && dis2 <= 0) {
			return List.of(new GeoPoint(this, ray.getPoint(solution_1)), new GeoPoint(this, ray.getPoint(solution_2)));
		}
		if (solution_1 > 0 && dis1 <= 0) {
			return List.of(new GeoPoint(this, ray.getPoint(solution_1)));
		}
		if (solution_2 > 0 && dis2 <= 0) {
			return List.of(new GeoPoint(this, ray.getPoint(solution_2)));
		}
		return null;
	}

	@Override
	public void setMinCoordinates() {
		double x = Double.NEGATIVE_INFINITY, y = x, z = y;
		_minBoundary = new Point3D(x, y, z);
	}

	@Override
	public void setMaxCoordinates() {
		double x = Double.POSITIVE_INFINITY, y = x, z = y;
		_maxBoundary = new Point3D(x, y, z);
	}
}
