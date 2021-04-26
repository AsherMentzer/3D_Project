package renderer;

import java.util.List;

import primitives.Color;
import primitives.Point3D;
import primitives.Ray;
import scene.Scene;
import geometries.Intersectable.GeoPoint;

/**
 * basic ray trace heir from the rayTraceBase class the class is to calculate
 * the closest point to the ray from all the intersections and calculate the
 * color in this point
 * 
 * @author
 *
 */
public class RayTracerBasic extends RayTracerBase {

	/**
	 * constructor that activate the father constructor
	 * 
	 * @param scene Scene
	 */
	public RayTracerBasic(Scene scene) {
		super(scene);
	}

	@Override
	/**
	 * get ray and return the color of the closest point that this ray intersect
	 */
	public Color traceRay(Ray ray) {
		List<GeoPoint> intersections = scene.geometries.findGeoIntersections(ray);
		if (intersections == null)
			return scene.background;
		GeoPoint closestPoint = ray.getClosestGeoPoint(intersections);
		return calcColor(closestPoint);
	}

	/**
	 * get point and return the color of this point in the scene
	 * 
	 * @param point
	 * @return the result of this color
	 */
	private Color calcColor(GeoPoint point) {
		return scene.ambientLight.getIntensity().add(point.geometry.getEmission());
	}
}
