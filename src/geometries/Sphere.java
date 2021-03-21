package geometries;
import primitives.*;

/**
 * Sphere class represents sphere in 3D
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
	 * @return the center point
	 */
	public Point3D getCenter() {
		return center;
	}

	/**
	 * getter
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
}
