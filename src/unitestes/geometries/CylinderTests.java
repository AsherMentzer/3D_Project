package unitestes.geometries;

import static org.junit.Assert.*;

import org.junit.Test;

import primitives.*;

import geometries.*;

public class CylinderTests {

	@Test
	public void testGetNormal() {
		Cylinder c = new Cylinder(new Ray(new Point3D(0, 0, 1), new Vector(0, 0, 1)), 2, 2);
		// ============ Equivalence Partitions Tests ==============
		// TC01 the point is on the side of the Cylinder
		assertEquals("get normal() wrong value", new Vector(0, 1, 0), c.getNormal(new Point3D(0, 2, 1.5)));

		// TC02 the point is on the far base of the Cylinder
		assertEquals("get normal() wrong value", new Vector(0, 0, 1), c.getNormal(new Point3D(0, 1, 3)));

		// TC03 the point is on the base of the Cylinder
		assertEquals("get normal() wrong value", new Vector(0, 0, 1), c.getNormal(new Point3D(1, 1, 1)));

		// =============== Boundary Values Tests ==================
		// TC04 the point is on between the base and the side of the Cylinder
		assertEquals("get normal() wrong value", new Vector(0, 0, 1), c.getNormal(new Point3D(0, 2, 1)));

		// TC05 the point is on between the far base and the side of the Cylinder
		assertEquals("get normal() wrong value", new Vector(0, 0, 1), c.getNormal(new Point3D(0, 2, 3)));
	}

}
