package unittests.primitives;

import static org.junit.Assert.*;

import org.junit.Test;

import primitives.Point3D;
import primitives.Vector;

public class Point3DTests {

	@Test
	public void testAdd() {
		Point3D p1 = new Point3D(1, 1, 1);
		assertEquals("Wrong add point3d value", new Point3D(2, 3, 1), p1.add(new Vector(1, 2, 0)));
	}

	@Test
	public void testSubtract() {
		Vector v = new Vector(1, 1, 1);
		assertEquals("wrong subtract vector value", v, new Point3D(2, 3, 5).subtract(new Point3D(1, 2, 4)));
	}

	@Test
	public void testDistanceSquared() {
		double d = 36.0;
		Point3D p = new Point3D(1, 1, 1);
		assertEquals("wrong DistanceSquared value ", d, p.distanceSquared(new Point3D(3, 5, 5)), 0.00001);
	}

	@Test
	public void testDistance() {
		double d = 6.0;
		assertEquals("wrong DistanceSquared value ", d, new Point3D(1, 1, 1).distance(new Point3D(3, 5, 5)), 0.00001);
	}

}
