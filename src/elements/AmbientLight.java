package elements;

import primitives.Color;

/**
 * class for AmbientLight with field that contain the color after scale by kA
 * 
 * @author
 *
 */
public class AmbientLight {

	private Color intensity;

	/**
	 * constructor get the color and the scale and keep the result in intensity
	 * 
	 * @param iA the color
	 * @param kA the scale
	 */
	public AmbientLight(Color iA, double kA) {
		this.intensity = iA.scale(kA);
	}

	/**
	 * getter
	 * 
	 * @return
	 */
	public Color getIntensity() {
		return this.intensity;
	}
}
