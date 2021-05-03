package geometries;

import java.util.List;

import primitives.*;

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
	@Override
	public List<GeoPoint> findGeoIntersections(Ray ray) {
		// TODO Auto-generated method stub
		return null;
	}
}
