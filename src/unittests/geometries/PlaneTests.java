/**
 * 
 */
package unittests.geometries;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import primitives.*;

import geometries.*;

/**
 * @author ashme
 *
 */
public class PlaneTests {

	/**
	 * Test method for
	 * {@link geometries.Plane#Plane(primitives.Point3D, primitives.Point3D, primitives.Point3D)}.
	 */
	@Test
	public void testPlanePoint3DPoint3DPoint3D() {

		try {
			Plane p1 = new Plane(new Point3D(1, 2, 3), new Point3D(1, 2, 3), new Point3D(2, 2, 2));
			fail("Constructed a plane with the same point");
		} catch (Exception e) {
		}

		try {
			Plane p2 = new Plane(new Point3D(1, 2, 3), new Point3D(2, 4, 6), new Point3D(3, 6, 9));
			fail("Constructed a plane with points on the same line");
		} catch (Exception e) {
		}
	}

	/**
	 * Test method for {@link geometries.Plane#getNormal(primitives.Point3D)}.
	 */
	@Test
	public void testGetNormalPoint3D() {
		Plane p = new Plane(new Point3D(0, 0, 0), new Point3D(1, 0, 0), new Point3D(0, 1, 0));
		Vector v1 = new Vector(0, 0, 1);
		Vector v2 = new Vector(0, 0, -1);
		Vector normal = p.getNormal(new Point3D(0, 0, 0));
		assertTrue("the normal has wrong value", normal.equals(v1) || normal.equals(v2));

		// assertEquals("", p.getNormal(new Point3D(0, 0, 0)), v1);

	}

	/**
	 * Test method for {@link geometries.Plane#findIntersections(primitives.Ray)}.
	 */
	@Test
	public void testFindIntersections() {
		Plane plane = new Plane(new Point3D(1, 1, 0), new Vector(0, 0, 1));
		
		// ============ Equivalence Partitions Tests ==============
		// **** Group: Ray neither orthogonal nor parallel to the plane
		
		// TC01 Ray intersects the plane(1 point)
		Point3D p01 = new Point3D(2, 0, 0);
		List<Point3D> result01 = plane.findIntersections(new Ray(new Point3D(0, 0, 1), new Vector(2, 0, -1)));
		assertEquals("Wrong number of points", 1, result01.size());
		assertEquals("Ray crosses sphere", List.of(p01), result01);

		// TC02 Ray does not intersect the plane(0 point)
		assertNull("Ray does not intersect the plane",
				plane.findIntersections(new Ray(new Point3D(0, 0, 1), new Vector(2, 0, 1))));

		// =============== Boundary Values Tests ==================
		// **** Group: Ray is parallel to the plane
		
		//TC03 the ray included  in the plane(0 point)
		assertNull("Ray does not intersect the plane",
				plane.findIntersections(new Ray(new Point3D(1, 1, 0), new Vector(1, 1, 0))));
		
		//TC04 the ray not included in the plane(0 point)
		assertNull("Ray does not intersect the plane",
				plane.findIntersections(new Ray(new Point3D(0, 0, 1), new Vector(2, 0, 0))));
		
		// **** Group:Ray is orthogonal to the plane
		
		//TC05 the ray p0 is before the plane(1 point)
		Point3D p05 = new Point3D(2, 0, 0);
		List<Point3D> result05 = plane.findIntersections(new Ray(new Point3D(2, 0, 2), new Vector(0, 0, -1)));
		assertEquals("Wrong number of points", 1, result05.size());
		assertEquals("Ray crosses sphere", List.of(p05), result05);

		
		//TC06 the ray p0 is at the plane(0 point)
		assertNull("Ray does not intersect the plane",
				plane.findIntersections(new Ray(new Point3D(2, 0, 0), new Vector(0, 0, 1))));
		
		//TC07 the ray p0 is after the plane(0 point)
		assertNull("Ray does not intersect the plane",
				plane.findIntersections(new Ray(new Point3D(1, 1, 1), new Vector(0, 0, 1))));
		
		// **** Group Ray is neither orthogonal nor parallel to and
		
		//TC08 the ray p0 is at the plane(0 point)
		assertNull("Ray does not intersect the plane",
				plane.findIntersections(new Ray(new Point3D(1, 1, 0), new Vector(1, 1, 1))));
		
		//TC09 the ray p0 is at the plane at the q point of the plane(0 point)
		assertNull("Ray does not intersect the plane",
				plane.findIntersections(new Ray(new Point3D(1, 0, 0), new Vector(1, 1, 1))));
	}
}
