package renderer;

import java.util.LinkedList;
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
	private int numOfRays;
	private static final double DISTANCE = 10;
	
	/**
	 * constructor that activate the father constructor
	 * 
	 * @param scene Scene
	 */
	public RayTracerBasic(Scene scene) {
		this(scene,1);
	}

	/**
	 * constructor that activate the father constructor
	 * 
	 * @param scene Scene
	 */
	public RayTracerBasic(Scene scene,int numOfRays) {
		super(scene);
		this.numOfRays=numOfRays;
	}
	@Override
	/**
	 * get ray and return the color of the closest point that this ray intersect
	 */
	public Color traceRay(Ray ray) {
		GeoPoint closestPoint = findClosestIntersection(ray);
		return closestPoint == null ? scene.background : calcColor(closestPoint, ray);
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
		Ray lightRay = new Ray(n, point, lightDirection);

		List<GeoPoint> intersections = scene.geometries.findGeoIntersections(lightRay);// , light.getDistance(point));
		if (intersections == null)
			return true;
		double lightDistance = light.getDistance(geopoint.point);
		for (GeoPoint gp : intersections) {
			if (alignZero(gp.point.distance(geopoint.point) - lightDistance) <= 0 && gp.geometry.getMaterial().kT == 0)
				return false;
		}
		return true;
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
		color = color.add((calcLocalEffects(intersection, ray, k)));
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
	private Color calcLocalEffects(GeoPoint intersection, Ray ray, double k) {
		Vector v = ray.getDir();
		Vector n = intersection.geometry.getNormal(intersection.point);
		double nv = alignZero(n.dotProduct(v));
		if (nv == 0)
			return Color.BLACK;
		Material material = intersection.geometry.getMaterial();
		int nShininess = material.nShininess;
		double kd = material.kD, ks = material.kS;
		Color color = Color.BLACK;
		for (LightSource lightSource : scene.lights) {
			Vector l = lightSource.getL(intersection.point);
			double nl = alignZero(n.dotProduct(l));
			if (nl * nv > 0) { // sing(nl) == sign(nv)
				double ktr = transparency(lightSource, l, n, intersection);
				if (ktr * k > MIN_CALC_COLOR_K) {
					Color lightIntensity = lightSource.getIntensity(intersection.point).scale(ktr);
					color = color.add(calcDiffusive(kd, nl, lightIntensity))
							.add(calcSpecular(ks, l, n, v, nl, nShininess, lightIntensity));
					// }
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
	private Color calcDiffusive(double kd, double nl, Color lightIntensity) {
		if (nl < 0)
			nl = -nl;
		return lightIntensity.scale(kd * nl);
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
		double vr = -alignZero(v.dotProduct(r));
		if (vr > 0)
			return lightIntensity.scale(ks * Math.pow(vr, nShininess));
		else
			return Color.BLACK;
	}

	/**
	 * function to calculate the effects on the color in point by the reflection and
	 * refraction by recursive the ray's in each point of the new rays
	 * 
	 * @param geopoint the point
	 * @param ray      the light ray to this point
	 * @param level    the number of times to do the recursive
	 * @param k        the initial k
	 * @return the result of the effects on the color
	 */
	private Color calcGlobalEffects(GeoPoint geopoint, Ray ray, int level, double k) {
		Color color = Color.BLACK;
		Material material = geopoint.geometry.getMaterial();
		Vector n = geopoint.geometry.getNormal(geopoint.point);
		double kr = material.kR, kkr = k * kr,kg=material.kG;
		// if is too small stop the recursive
		if (kkr > MIN_CALC_COLOR_K) {
			double nl = ray.getDir().dotProduct(n);
			Vector inRay = ray.getDir().subtract(n.scale(2 * (nl))).normalize();
			Ray reflectedRay = new Ray(n, geopoint.point, inRay);
			color = calcBeamColor(color, n, reflectedRay, level, kr, kkr, kg);
			//color = calcGlobalEffect(reflectedRay, level, kr, kkr);
			/*
			 * GeoPoint reflectedPoint = findClosestIntersection(reflectedRay); if
			 * (reflectedPoint != null) color = color.add(calcColor(reflectedPoint,
			 * reflectedRay, level - 1, kkr).scale(kr));
			 */
		}
		double kt = material.kT, kkt = k * kt,kb=material.kB;
		// if is too small stop the recursive
		if (kkt > MIN_CALC_COLOR_K) {
			Vector inRay = ray.getDir();
			Ray refractedRay = new Ray(n, geopoint.point, inRay);
			color = calcBeamColor(color, n, refractedRay, level, kt, kkt, kb);
			//color = color.add(calcGlobalEffect(refractedRay, level, kt, kkt));
			
			/*
			 * GeoPoint refractedPoint = findClosestIntersection(refractedRay); if
			 * (refractedPoint != null) color = color.add(calcColor(refractedPoint,
			 * refractedRay, level - 1, kkt).scale(kt));
			 */
		}
		return color;
	}

	private Color calcGlobalEffect(Ray ray, int level, double kx, double kkx) {
		GeoPoint gp = findClosestIntersection(ray);
		return (gp == null ? scene.background : calcColor(gp, ray, level - 1, kkx)).scale(kx);
	}

	/**
	 * function to calculate the power of transparency to scale to know how many
	 * shadow we will get
	 * 
	 * @param light    the light
	 * @param l        vector from light to the point
	 * @param n        the normal vector to this point
	 * @param geopoint the point
	 * @return the result
	 */
	private double transparency(LightSource light, Vector l, Vector n, GeoPoint geopoint) {
		Vector lightDirection = l.scale(-1); // vector from point to light source
		Ray lightRay = new Ray(n, geopoint.point, lightDirection);
		double lightDistance = light.getDistance(geopoint.point);
		var intersections = scene.geometries.findGeoIntersections(lightRay, lightDistance);
		if (intersections == null)
			return 1.0;
		double ktr = 1.0;
		for (GeoPoint gp : intersections) {
			ktr *= gp.geometry.getMaterial().kT;
			if (ktr < MIN_CALC_COLOR_K)
				return 0.0;

		}
		return ktr;
	}

	/**
	 * this function calculate the color of point with the help of beam
	 * 
	 * @param color  the color of the intersection point
	 * @param n      The normal vector of the point where beam start
	 * @param refRay reflected/refracted ray
	 * @param level  The level of recursion
	 * @param k      kt/kr
	 * @param kk     kkt/kkr
	 * @param rad    radius/kMatte ,when radius is bigger the mattiut is more
	 *               intense
	 * @return The color
	 */
	private Color calcBeamColor(Color color, Vector n, Ray refRay, int level, double k, double kk, double rad) {
		Color addColor = Color.BLACK;
		List<Ray> rays = refRay.generateBeam(n, rad, DISTANCE, numOfRays);
		for (Ray ray : rays) {
			GeoPoint refPoint = findClosestIntersection(ray);
			if (refPoint != null) {
				addColor = addColor.add(calcColor(refPoint, ray, level - 1, kk).scale(k));
			}
		}
		int size = rays.size();
		color = color.add(size > 1 ? addColor.reduce(size) : addColor);
		return color;
	}
	
	/*
	public Ray constructRayThroughPixel(Point3D p0,Vector vRight,Vector vUp,int nX, int nY, int j, int i) {
		// the center of the view plane
		Point3D pc = p0.add(vTo.scale(distance));
		double height=2.0;
		double width=2.0;
		double rY = height / nY; // Ratio of height to rows
		double rX = width / nX; // Ratio of width and columns
		double ny = nY;
		double nx = nX;
		double yI = -((i - (ny - 1) / 2) * rY);
		double xJ = (j - (nx - 1) / 2) * rX;
		Point3D pIJ = pc;
		if (xJ != 0)
			pIJ = pIJ.add(vRight.scale(xJ));
		if (yI != 0)
			pIJ = pIJ.add(vUp.scale(yI));
		Vector vIJ = pIJ.subtract(p0);
		return new Ray(p0, vIJ);
	}

*/
}
