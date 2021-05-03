/**
 * 
 */
package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

/**
 * class for Models omni directional point source (such as a bulb)
 * 
 * @author
 *
 */
public class PointLight extends Light implements LightSource {

	// the location of the light
	private Point3D position;

	// the parameters to calculate the light in specific point
	private double kC, kL, kQ;

	/**
	 * constructor
	 * 
	 * @param intensity the color of the light in the source
	 * @param position  the position of the light
	 * @param kC        usually=1
	 * @param kL        linear
	 * @param kQ        for d^2
	 */
	public PointLight(Color intensity, Point3D position, double kC, double kL, double kQ) {
		super(intensity);
		this.position = position;
		this.kC = kC;
		this.kL = kL;
		this.kQ = kQ;
	}

	@Override
	/**
	 * get the color of the light in specific point Calculate how much fade by the
	 * distance
	 */
	public Color getIntensity(Point3D p) {
		double d = p.distance(position);
		return super.getIntensity().reduce(kC + kL * d + kQ * (d * d));
	}

	@Override
	/**
	 * get the vector from the light to the point
	 */
	public Vector getL(Point3D p) {
		return p.subtract(position).normalize();
	}

}
