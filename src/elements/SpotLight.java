/**
 * 
 */
package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;
import static primitives.Util.*;

/**
 * class for Models point light source with direction (such as a luxo lamp)
 * 
 * @author
 *
 */
public class SpotLight extends PointLight {

	// the direction of this spot
	private Vector direction;
	// the how narrow the beam will be
	double beam = 0;

	/**
	 * constructor without beam parameter
	 * 
	 * @param intensity
	 * @param position
	 * @param kC
	 * @param kL
	 * @param kQ
	 * @param direction
	 */
	public SpotLight(Color intensity, Point3D position, Vector direction, double kC, double kL, double kQ) {
		super(intensity, position, kC, kL, kQ);
		this.direction = direction.normalize();
	}

	/**
	 * constructor with the beam parameter
	 * 
	 * @param intensity
	 * @param position
	 * @param kC
	 * @param kL
	 * @param kQ
	 * @param direction
	 * @param beam
	 */
	public SpotLight(Color intensity, Point3D position, Vector direction, double kC, double kL, double kQ,
			double beam) {
		super(intensity, position, kC, kL, kQ);
		this.direction = direction.normalize();
		this.beam = beam;
	}

	/**
	 * get the color of the light in specific point Calculate how much fade by the
	 * distance
	 */
	@Override
	public Color getIntensity(Point3D p) {
		double s = alignZero(getL(p).dotProduct(direction));
		if (s > 0) {
			if (beam == 0)
				return getIntensity().scale(s);
			else// calculate the narrow of the beam
				return getIntensity().scale(Math.pow(s, beam));
		} else
			return super.getIntensity().scale(0);
	}

	/**
	 * get the vector from the light to the point
	 */
	@Override
	public Vector getL(Point3D p) {
		return super.getL(p);
	}

}
