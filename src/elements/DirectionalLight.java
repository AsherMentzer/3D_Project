/**
 * 
 */
package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

/**
 * class for direction light like sun that not effected by
 * the distance only by the direction 
 * @author 
 *
 */
public class DirectionalLight extends Light implements LightSource {

	private Vector direction;
	

	/**
	 * constructor
	 * @param intensity
	 * @param direction
	 */
	public DirectionalLight(Color intensity, Vector direction) {
		super(intensity);
		this.direction = direction.normalize();
	}

	/**
	 * getter for intensity color
	 */
	@Override
	public Color getIntensity(Point3D p) {
		return super.getIntensity();
	}

	/**
	 * getter for the direction vector
	 */
	@Override
	public Vector getL(Point3D p) {
		return direction;
	}

}
