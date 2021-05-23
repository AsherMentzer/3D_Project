/**
 * 
 */
package elements;

import primitives.Color;

/**
 * abstract class for light
 * 
 * @author
 *
 */
abstract class Light {

	/** the light color
	 */
	protected Color intensity;

	/**
	 * constructor
	 * 
	 * @param intensity the color of the light
	 */
	public Light(Color intensity) {
		super();
		this.intensity = intensity;
	}

	/**
	 * getter to the light color
	 * 
	 * @return the intensity
	 */
	public Color getIntensity() {
		return intensity;
	}

}
