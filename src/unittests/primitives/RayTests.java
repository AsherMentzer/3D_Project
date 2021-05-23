/**
 * 
 */
package unittests.primitives;

import static org.junit.Assert.*;

import java.util.LinkedList;
import java.util.List;

import org.junit.Test;

import geometries.Intersectable.GeoPoint;
import geometries.*;
import primitives.*;

/**
 * @author
 *
 */
public class RayTests {

	/**
	 * Test method for {@link primitives.Ray#getClosestPoint(java.util.List)}.
	 */
	@Test
	public void testGetClosestPoint() {
		Ray ray = new Ray(new Point3D(0, 0, 0), new Vector(1, 0, 0));
		List<Point3D> l = List.of(new Point3D(6, 0, 0), new Point3D(5, 0, 0), new Point3D(1, 0, 0),
				new Point3D(3, 0, 0), new Point3D(2, 0, 0));

		// ============ Equivalence Partitions Tests ==============

		// TC01:the point in the middle of the list is the closest
		assertEquals("Wrong closest point", new Point3D(1, 0, 0), ray.getClosestPoint(l));

		// =============== Boundary Values Tests ==================

		// TC02:the last point in the list is the closest
		l = List.of(new Point3D(6, 0, 0), new Point3D(5, 0, 0), new Point3D(2, 0, 0), new Point3D(3, 0, 0),
				new Point3D(1, 0, 0));
		assertEquals("Wrong closest point", new Point3D(1, 0, 0), ray.getClosestPoint(l));

		// TC03:the first point in the list is the closest
		l = List.of(new Point3D(1, 0, 0), new Point3D(5, 0, 0), new Point3D(2, 0, 0), new Point3D(3, 0, 0),
				new Point3D(6, 0, 0));
		assertEquals("Wrong closest point", new Point3D(1, 0, 0), ray.getClosestPoint(l));

		// TC04:empty list
		l = List.of();
		assertNull("Empty list", ray.getClosestPoint(l));
	}

	@Test
	public void testGetClosestGeoPoint() {
		Ray ray = new Ray(new Point3D(0, 0, 0), new Vector(1, 0, 0));
		List<GeoPoint> l = List.of(new GeoPoint(new Sphere(new Point3D(7, 0, 0), 1), new Point3D(6, 0, 0)),
				new GeoPoint(new Sphere(new Point3D(6, 0, 0), 1), new Point3D(5, 0, 0)),
				new GeoPoint(new Sphere(new Point3D(2, 0, 0), 1), new Point3D(1, 0, 0)),
				new GeoPoint(new Sphere(new Point3D(4, 0, 0), 1), new Point3D(3, 0, 0)),
				new GeoPoint(new Sphere(new Point3D(3, 0, 0), 1), new Point3D(2, 0, 0)));

		// ============ Equivalence Partitions Tests ==============

		// TC01:the point in the middle of the list is the closest
		assertEquals("Wrong closest point", new GeoPoint(new Sphere(new Point3D(2, 0, 0), 1), new Point3D(1, 0, 0)),
				ray.getClosestGeoPoint(l));

		// =============== Boundary Values Tests ==================

		// TC02:the last point in the list is the closest
		l = List.of(new GeoPoint(new Sphere(new Point3D(7, 0, 0), 1), new Point3D(6, 0, 0)),
				new GeoPoint(new Sphere(new Point3D(6, 0, 0), 1), new Point3D(5, 0, 0)),
				new GeoPoint(new Sphere(new Point3D(3, 0, 0), 1), new Point3D(2, 0, 0)),
				new GeoPoint(new Sphere(new Point3D(4, 0, 0), 1), new Point3D(3, 0, 0)),
				new GeoPoint(new Sphere(new Point3D(2, 0, 0), 1), new Point3D(1, 0, 0)));
		
		assertEquals("Wrong closest point", new GeoPoint(new Sphere(new Point3D(2, 0, 0), 1), new Point3D(1, 0, 0)),
				ray.getClosestGeoPoint(l));

		// TC03:the first point in the list is the closest		
		l = List.of(new GeoPoint(new Sphere(new Point3D(2, 0, 0), 1), new Point3D(1, 0, 0)),
				new GeoPoint(new Sphere(new Point3D(6, 0, 0), 1), new Point3D(5, 0, 0)),
				new GeoPoint(new Sphere(new Point3D(3, 0, 0), 1), new Point3D(2, 0, 0)),
				new GeoPoint(new Sphere(new Point3D(4, 0, 0), 1), new Point3D(3, 0, 0)),
				new GeoPoint(new Sphere(new Point3D(7, 0, 0), 1), new Point3D(6, 0, 0)));
		
		assertEquals("Wrong closest point", new GeoPoint(new Sphere(new Point3D(2, 0, 0), 1), new Point3D(1, 0, 0)),
				ray.getClosestGeoPoint(l));
	
		// TC04:empty list
		l = List.of();
		assertNull("Empty list", ray.getClosestGeoPoint(l));
	}

}
