/**
 * 
 */
package elements;

import primitives.*;

/**
 * interface for all the light sources to implement 2 functions
 * 
 * @author
 *
 */
public interface LightSource {

	/**
	 * get the intensity color in the point
	 * 
	 * @param p the point
	 * @return the color in this point
	 */
	public Color getIntensity(Point3D p);

	/**
	 * get vector from the light to this point
	 * 
	 * @param p
	 * @return
	 */
	public Vector getL(Point3D p);

}
