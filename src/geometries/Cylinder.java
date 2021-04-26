package geometries;

import java.util.List;

import geometries.Intersectable.GeoPoint;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

/**
 * Cylinder class heir from the Tube class
 * 
 * @author
 *
 */
public class Cylinder extends Tube {

	/**
	 * the height of the cylinder
	 */
	private double height;

	/**
	 * constructor
	 * 
	 * @param axisRay the Ray
	 * @param radius  the radius
	 * @param height  the height
	 */
	public Cylinder(Ray axisRay, double radius, double height) {
		super(axisRay, radius);
		this.height = height;
	}

	/**
	 * getter
	 * 
	 * @return the height
	 */
	public double getHeight() {
		return height;
	}

	@Override
	public String toString() {
		return "Cylinder [height=" + height + ", axisRay=" + axisRay + ", radius=" + radius + "]";
	}

	/**
	 * implement the interface to find the normal to this Cylinder by specific point
	 */
	public Vector getNormal(Point3D point) {
		Vector v = point.subtract(axisRay.getP0());
		/*
		 * try { v.crossProduct(axisRay.getDir()); }catch(IllegalArgumentException e){
		 * return axisRay.getDir(); }
		 */

		double t = axisRay.getDir().dotProduct(v);
		if (t == 0 || t == height || t == -height)// the point are on the bases
			return axisRay.getDir().normalize();

		Point3D O = axisRay.getP0().add(axisRay.getDir().scale(t));
		return point.subtract(O).normalize();
	}

	/*
	 * public List<Point3D> findIntersections(Ray ray) { 
	 * method stub return super.findIntersections(ray); }
	 */
	
	/**
	 * implement the interface to find all the intersections between ray and this
	 * Cylinder
	 */
	@Override
	public List<GeoPoint> findGeoIntersections(Ray ray) {
		return super.findGeoIntersections(ray);
	}
}
