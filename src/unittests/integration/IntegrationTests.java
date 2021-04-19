/**
 * 
 */
package unittests.integration;

import static org.junit.Assert.*;

import primitives.*;
import elements.*;
import geometries.*;

import java.util.LinkedList;
import java.util.List;

import org.junit.Test;

/**
 * 
 */
public class IntegrationTests {

	/**
	 * Test method for
	 * {@link elements.Camera#constructRayThroughPixel(int, int, int, int)} with
	 * {@link geometries.Sphere#Sphere(Point3D, double)}
	 */
	@Test
	public void testIntegrationCtorRayToSphere() {
		Camera camera = new Camera(Point3D.ZERO, new Vector(0, 0, -1), new Vector(0, 1, 0)).setDistance(1);
		camera.setViewPlaneSize(3, 3);
		List<Ray> rays = raysForCameraVP(camera, 3, 3);

		// TC01: Sphere( p(0,0,-3), r=1)) need to return only 2 intersections from 1
		// pixel
		assertEquals("intersection of sphere only in center fail", 2,
				countIntersectionsFromRays(rays, new Sphere(new Point3D(0, 0, -3), 1)));

		camera = new Camera(new Point3D(0, 0, 0.5), new Vector(0, 0, -1), new Vector(0, 1, 0)).setDistance(1);
		camera.setViewPlaneSize(3, 3);
		rays = raysForCameraVP(camera, 3, 3);

		// TC02: Sphere( p(0,0,-2.5), r=2.5)) need to return all 18 intersections from
		// all 9 pixels
		assertEquals("intersection of sphere in all pixels fail", 18,
				countIntersectionsFromRays(rays, new Sphere(new Point3D(0, 0, -2.5), 2.5)));

		// TC03: Sphere( p(0,0,-2), r=2)) need to return 10 intersections from centers
		// pixels
		assertEquals("intersection of sphere in centers pixels fail", 10,
				countIntersectionsFromRays(rays, new Sphere(new Point3D(0, 0, -2), 2)));

		// TC04: Sphere( p(0,0,-2), r=4)) need to return 9 intersections from inside the
		// sphere out
		assertEquals("intersection of sphere from inside camera fail", 9,
				countIntersectionsFromRays(rays, new Sphere(new Point3D(0, 0, -2), 4)));

		// TC05: Sphere behind camera need to return 0 intersections
		assertEquals("intersection of sphere behind camera fail", 0,
				countIntersectionsFromRays(rays, new Sphere(new Point3D(0, 0, 1), 0.5)));

	}

	/**
	 * Test method for
	 * {@link elements.Camera#constructRayThroughPixel(int, int, int, int)} with
	 * {@link geometries.Plane#Plane(Point3D, Vector)}
	 */
	@Test
	public void testIntegrationCtorRayToPlane() {
		Vector vTo = new Vector(0, 0, -1);
		Point3D p = new Point3D(0, 0, -3);

		Camera camera = new Camera(Point3D.ZERO, vTo, new Vector(0, 1, 0)).setDistance(1);
		camera.setViewPlaneSize(3, 3);
		List<Ray> rays = raysForCameraVP(camera, 3, 3);

		// TC01: plane orthogonal to camera need to return all intersections from camera
		assertEquals("intersection of plane in all pixels fail", 9,
				countIntersectionsFromRays(rays, new Plane(p, vTo)));

		// TC02: plane sloping (normal 0,0.5,-1) need to return all intersections from
		// camera
		assertEquals("intersection of plane in all pixels fail", 9,
				countIntersectionsFromRays(rays, new Plane(p, new Vector(0, 0.5, -1))));

		// TC03: plane deep sloping (normal 0,1,-1) need to return 6 intersections from
		// camera
		assertEquals("intersection of plane in all pixels fail", 6,
				countIntersectionsFromRays(rays, new Plane(p, new Vector(0, 1, -1))));

	}

	/**
	 * Test method for
	 * {@link elements.Camera#constructRayThroughPixel(int, int, int, int)} with
	 * {@link geometries.Triangle#Triangle(Point3D, Point3D, Point3D)}
	 */
	@Test
	public void testIntegrationCtorRayToTriangle() {
		Camera camera = new Camera(Point3D.ZERO, new Vector(0, 0, -1), new Vector(0, 1, 0)).setDistance(1);
		camera.setViewPlaneSize(3, 3);
		List<Ray> rays = raysForCameraVP(camera, 3, 3);

		Triangle triangle = new Triangle(new Point3D(0, 1, -2), new Point3D(-1, -1, -2), new Point3D(1, -1, -2));

		// TC01: small triangle return intersection only from center pixel
		assertEquals("intersection of triangle in center pixel fail", 1, countIntersectionsFromRays(rays, triangle));

		triangle = new Triangle(new Point3D(0, 20, -2), new Point3D(-1, -1, -2), new Point3D(1, -1, -2));

		// TC02: tall triangle return 2 intersections from middle up and center pixel
		assertEquals("intersection of tall triangle fail", 2, countIntersectionsFromRays(rays, triangle));

	}

	/**
	 * get all rays from camera after set all configs
	 * 
	 * @param cam
	 * @param nX
	 * @param nY
	 * @return
	 */
	private List<Ray> raysForCameraVP(Camera cam, int nX, int nY) {
		if ((cam.getHeight() == 0) || (cam.getWidth() == 0) || (cam.getDistance() == 0))
			throw new IllegalArgumentException("camera not config size or distance");

		List<Ray> rays = new LinkedList<Ray>();

		for (int i = 0; i < nY; i++) {
			for (int j = 0; j < nX; j++) {
				rays.add(cam.constructRayThroughPixel(nX, nY, j, i));
			}
		}

		return rays;
	}

	/**
	 * count all intersections between rays list and geometries shapes
	 * 
	 * @param rays
	 * @param intersectables
	 * @return number of intersections as point3D
	 */
	private int countIntersectionsFromRays(List<Ray> rays, Intersectable... geometries) {
		if (rays.size() == 0)
			throw new IllegalArgumentException("rays array is null!");

		int intersections = 0;

		for (Intersectable geo : geometries) {
			for (Ray ray : rays) {
				var inters = geo.findIntersections(ray);
				if (inters != null) {
					intersections += inters.size();
				}
			}
		}

		return intersections;
	}

}



