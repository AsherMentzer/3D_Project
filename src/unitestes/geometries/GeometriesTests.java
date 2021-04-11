package unitestes.geometries;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import primitives.*;

import geometries.*;

public class GeometriesTests {

	@Test
	public void testFindIntersections() {
		Geometries geo = new Geometries();

		// =============== Boundary Values Tests ==================
		// TC01 Empty list(0 points)
		assertNull("Wrong number of points", geo.findIntersections(new Ray(new Point3D(1, 1, 1), new Vector(1, 1, 1))));

		Triangle t = new Triangle(new Point3D(0, 0, 0), new Point3D(2, 0, 0), new Point3D(1, 1, 0));
		Sphere sphere = new Sphere(new Point3D(1, 0, 0), 1d);
		Plane plane = new Plane(new Point3D(1, 1, 0), new Vector(0, 0, 1));
		geo.add(t,sphere,plane);

		//TC02 all shapes not intersected(0 points)
		assertNull("Wrong number of points", geo.findIntersections(new Ray(new Point3D(-1, -1, -1), new Vector(-1, -1, -1))));
		
		//TC03 only 1 shape intersected()
		List<Point3D> result03 = geo.findIntersections(new Ray(new Point3D(3, 3, 1), new Vector(0, 0, -1)));
		assertEquals("Wrong number of points", 1, result03.size());
		
		//TC04 all the shapes intersected
		List<Point3D> result04 = geo.findIntersections(new Ray(new Point3D(0, 0, 1), new Vector(1, 0.1, -1)));
		assertEquals("Wrong number of points", 4, result04.size());
		
		// ============ Equivalence Partitions Tests ==============
		//TC05 some of the shapes are intersected but not all
		List<Point3D> result05 = geo.findIntersections(new Ray(new Point3D(0, 0, 1), new Vector(0.2, 0, -0.2)));
		assertEquals("Wrong number of points", 3, result05.size());
		
	}

}
