package renderer;

import java.util.List;

import elements.LightSource;
import primitives.Color;
import primitives.Material;
import primitives.Point3D;
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
	 * static double for moving the head of shadow ray's
	 */
	private static final double DELTA = 0.1;
	private static final int MAX_CALC_COLOR_LEVEL = 10;
	private static final double MIN_CALC_COLOR_K = 0.001;
	private static final double INITIAL_K = 1.0;

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
		GeoPoint closestPoint = findClosestIntersection(ray);
		return closestPoint == null ? Color.BLACK : calcColor(closestPoint, ray);
	}

	/**
	 * function that get ray and return the closest intersection geo point to this
	 * ray
	 * 
	 * @param ray
	 * @return the closest geo point
	 */
	private GeoPoint findClosestIntersection(Ray ray) {
		List<GeoPoint> intersections = scene.geometries.findGeoIntersections(ray);
		if (intersections == null)
			return null;
		return ray.getClosestGeoPoint(intersections);
	}

	/**
	 * function to check if there is shadow in specific point by search if there is
	 * geometries between the light to this point
	 * 
	 * @param light    the light
	 * @param l        vector from light to the point
	 * @param n        the normal in this point
	 * @param geopoint the point
	 * @return if no shadow return true else return false
	 */
	private boolean unshaded(LightSource light, Vector l, Vector n, GeoPoint geopoint) {
		Vector lightDirection = l.scale(-1); // vector from point to light source
		Vector delta = n.scale(n.dotProduct(lightDirection) > 0 ? DELTA : -DELTA);
		Point3D point = geopoint.point.add(delta);
		Ray lightRay = new Ray(point, lightDirection);
		List<GeoPoint> intersections = scene.geometries.findGeoIntersections(lightRay, light.getDistance(point));
		return intersections == null;
	}

	/**
	 * Assistance function to get all the local effect and recursion to the global
	 * effect on the color
	 * 
	 * @param intersection
	 * @param ray
	 * @param level        the times to do the recursion
	 * @param k            the start is 1
	 * @return the color after the calculation
	 */
	private Color calcColor(GeoPoint intersection, Ray ray, int level, double k) {
		Color color = intersection.geometry.getEmission();
		color = (calcLocalEffects(intersection, ray));
		// if is less then 1 we stop the recursion because not effected too much
		return 1 == level ? color : color.add(calcGlobalEffects(intersection, ray, level, k));
	}

	/**
	 * get point and return the color of this point in the scene
	 * 
	 * @param closestPoint
	 * @param ray
	 * @return
	 */
	private Color calcColor(GeoPoint closestPoint, Ray ray) {
		return calcColor(closestPoint, ray, MAX_CALC_COLOR_LEVEL, INITIAL_K).add(scene.ambientLight.getIntensity());
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
				if (unshaded(lightSource, l, n, intersection)) {
					Color lightIntensity = lightSource.getIntensity(intersection.point);
					color = color.add(calcDiffusive(kd, l, n, nl, lightIntensity))
							.add(calcSpecular(ks, l, n, v, nl, nShininess, lightIntensity));
				}
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

	private Color calcGlobalEffects(GeoPoint geopoint, Ray ray, int level, double k) {
		Color color = Color.BLACK;
		Material material = geopoint.geometry.getMaterial();
		Vector n = geopoint.geometry.getNormal(geopoint.point);
		double kr = material.kR, kkr = k * kr;
		if (kkr > MIN_CALC_COLOR_K) {
			double nl = ray.getDir().dotProduct(n);
			Vector inRay = ray.getDir().subtract(n.scale(2 * (nl))).normalize();
			Ray reflectedRay = new Ray(n, geopoint.point, inRay);
			GeoPoint reflectedPoint = findClosestIntersection(reflectedRay);
			color = color.add(calcColor(reflectedPoint, reflectedRay, level - 1, kkr).scale(kr));
		}
		double kt = material.kT, kkt = k * kt;
		if (kkt > MIN_CALC_COLOR_K) {
			Vector inRay = ray.getDir();
			Ray refractedRay = new Ray(n, geopoint.point, inRay);
			GeoPoint refractedPoint = findClosestIntersection(refractedRay);
			color = color.add(calcColor(refractedPoint, refractedRay, level - 1, kkr).scale(kt));
		}
		return color;
	}

}
