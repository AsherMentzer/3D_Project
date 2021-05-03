package renderer;

import java.util.List;

import elements.LightSource;
import primitives.Color;
import primitives.Material;
import primitives.Ray;
import primitives.Vector;
import static primitives.Util.*;
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
		return calcColor(closestPoint, ray);
	}

	/**
	 * get point and return the color of this point in the scene
	 * 
	 * @param point
	 * @return the result of this color
	 */

	private Color calcColor(GeoPoint point, Ray ray) {
		return scene.ambientLight.getIntensity().add(point.geometry.getEmission()).add(calcLocalEffects(point, ray));
	}

	/**
	 * calculate all the local effects on the color
	 * 
	 * @param intersection
	 * @param ray
	 * @return the color
	 */
	private Color calcLocalEffects(GeoPoint intersection, Ray ray) {
		Material material = intersection.geometry.getMaterial();
		Vector v = ray.getDir();
		Vector n = intersection.geometry.getNormal(intersection.point);
		double nv = alignZero(n.dotProduct(v));
		if (nv == 0)
			return Color.BLACK;
		int nShininess = material.nShininess;
		double kd = material.kD, ks = material.kS;
		Color color = Color.BLACK;
		for (LightSource lightSource : scene.lights) {
			Vector l = lightSource.getL(intersection.point);
			double nl = alignZero(n.dotProduct(l));
			if (nl * nv > 0) { // sing(nl) == sign(nv)
				Color lightIntensity = lightSource.getIntensity(intersection.point);
				color = color.add(calcDiffusive(kd, l, n, nl, lightIntensity))
						.add(calcSpecular(ks, l, n, v, nl, nShininess, lightIntensity));
			}
		}
		return color;
	}

	/**
	 * calculate the diffuse effect on the color
	 * 
	 * @param kd             the parameter of the material diffuse
	 * @param l              vector from the light to the point
	 * @param n              the normal vector of the geometry
	 * @param nl             dot product between n and l
	 * @param lightIntensity the light color before the diffuse effect
	 * @return the color after the diffuse effect
	 */
	private Color calcDiffusive(double kd, Vector l, Vector n, double nl, Color lightIntensity) {
		return lightIntensity.scale(kd * (Math.abs(nl)));
	}

	/**
	 * calculate the specular effect on the color
	 * 
	 * @param ks             the parameter of the material specular
	 * @param l              vector from the light to the point
	 * @param n              the normal vector of the geometry
	 * @param v              vector from camera to the point
	 * @param nl             dot product between n and l
	 * @param nShininess     the parameter of the material shininess
	 * @param lightIntensity the light color before the specular effect
	 * @return the color after the specular effect
	 */
	private Color calcSpecular(double ks, Vector l, Vector n, Vector v, double nl, double nShininess,
			Color lightIntensity) {
		Vector r = l.subtract(n.scale(2 * (nl))).normalize();
		double vr = alignZero(v.scale(-1).dotProduct(r));
		if (vr > 0)
			return lightIntensity.scale(ks * Math.pow(vr, nShininess));
		else
			return lightIntensity.scale(ks * 0);
	}
}
