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
	double beam = 1;

	/**
	 * 
	 * @param intensity
	 * @param position
	 * @param direction
	 */
	public SpotLight(Color intensity, Point3D position, Vector direction) {
		super(intensity, position);// , kC, kL, kQ);
		this.direction = direction.normalized();
	}

	/**
	 * @param beam the beam to set
	 */
	public SpotLight setBeam(double beam) {
		this.beam = beam;
		return this;
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
	/*
	 * public SpotLight(Color intensity, Point3D position, Vector direction, double
	 * kC, double kL, double kQ, double beam) { super(intensity, position);//, kC,
	 * kL, kQ); this.direction = direction.normalize(); this.beam = beam; }
	 */

	/**
	 * @param kC the kC to set
	 */
	public SpotLight setkC(double kC) {
		super.setkC(kC);
		return this;
	}

	/**
	 * @param kL the kL to set
	 */
	public SpotLight setkL(double kL) {
		super.setkL(kL);
		return this;
	}

	/**
	 * @param kQ the kQ to set
	 */
	public SpotLight setkQ(double kQ) {
		super.setkQ(kQ);
		return this;
	}

	/**
	 * get the color of the light in specific point Calculate how much fade by the
	 * distance
	 */
	@Override
	public Color getIntensity(Point3D p) {
		double cos = alignZero(getL(p).dotProduct(direction));
		if (cos <= 0)
			return Color.BLACK;
		if (beam != 1)
			cos = Math.pow(cos, beam);
		return super.getIntensity(p).scale(cos);
	}

	/**
	 * get the vector from the light to the point
	 */
	@Override
	public Vector getL(Point3D p) {
		return super.getL(p);
	}

	/**
	 * 
	 */
	@Override
	public double getDistance(Point3D point) {
		return super.getDistance(point);
	}
}
