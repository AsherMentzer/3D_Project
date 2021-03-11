package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

/**
 * Cylinder class heir from the Tube class 
 * @author
 *
 */
public class Cylinder extends Tube implements Geometry {
 
	/**
	 * the height of the cylinder
	 */
	private double height;

	/**
	 * constructor
	 * @param axisRay the Ray
	 * @param radius the radius
	 * @param height the height
	 */
	public Cylinder(Ray axisRay, double radius, double height) {
		super(axisRay, radius);
		this.height = height;
	}

	/**
	 * getter
	 * @return the height
	 */
	public double getHeight() {
		return height;
	}

	@Override
	public String toString() {
		return "Cylinder [height=" + height + ", axisRay=" + axisRay + ", radius=" + radius + "]";
	}
	
	public Vector getNormal(Point3D point) {
		return null;
	}
}
