package renderer;

import scene.Scene;
import primitives.*;

/**
 * abstract class for ray trace to find the 
 * color of the ray in the scene
 * @author
 *
 */
public abstract class RayTracerBase {

	protected Scene scene;

	/**
	 * constructor
	 * 
	 * @param scene
	 */
	public RayTracerBase(Scene scene) {
		// TODO Auto-generated constructor stub
		this.scene = scene;
	}

	/**
	 * abstract function
	 * @param ray
	 * @return Color
	 */
	public abstract Color traceRay(Ray ray);

}
