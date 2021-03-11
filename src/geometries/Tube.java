package geometries;
import primitives.*;

/**
 * Tube class represents Tube in the 3D
 * @author 
 *
 */
public class Tube implements Geometry {

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
	 * @param axisRay the Ray
	 * @param radius the radius
	 */
	public Tube(Ray axisRay, double radius) {
		super();
		this.axisRay = axisRay;
		this.radius = radius;
	}

	/**
	 * getter
	 * @return the ray
	 */
	public Ray getAxisRay() {
		return axisRay;
	}

	/**
	 * getter
	 * @return the radius
	 */
	public double getRadius() {
		return radius;
	}

	@Override
	public String toString() {
		return "Tube [axisRay=" + axisRay + ", radius=" + radius + "]";
	}
	
	public Vector getNormal(Point3D point) {
		return null;
	}
}
