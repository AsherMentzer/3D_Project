package unittests.geometries;

import static org.junit.Assert.*;

import org.junit.Test;

import geometries.*;
import primitives.*;

public class findGeoIntersection {

	/**
	   * Test method for
	   * {@link geometries.Geometries#findGeoIntersections(Ray, double)}.
	   */
	  @Test
	  public void testFindGeoIntersections() {

	    Geometries geo = new Geometries();

	    geo.add(new Triangle(new Point3D(1, 1, 2), new Point3D(-1, 1, 2), new Point3D(0, -1, 2)),
	        new Sphere(new Point3D(0, 0, 4), 1));

	    Vector v1 = new Vector(0, 0, 1);
	    Point3D p1 = new Point3D(0, 0, 0);
	    Ray r1 = new Ray(p1, v1);

	    // ============ Equivalence Partitions Tests ==============

	    p1 = new Point3D(3, -1.5, 0.5);

	    // TC01: check without maxDistance, need to return 3 intersections
	    assertEquals("not all intersection return from the ray", 3, geo.findGeoIntersections(r1).size());

	    // TC02: check maxDistance 2.5, need to return intersection only from triangle
	    assertEquals("not only triangle return", 1, geo.findGeoIntersections(r1, 2.5).size());

	    // TC03: check maxDistance 4, need to return intersection from triangle, and 1
	    // from sphere
	    assertEquals("not only triangle and 1 from sphere return", 2, geo.findGeoIntersections(r1, 4).size());

	    // TC04: check maxDistance 6, need to return 3 intersections
	    assertEquals("not all intersection return from the ray", 3, geo.findGeoIntersections(r1, 6).size());

	    // =============== Boundary Values Tests ==================
	    
	    // TC05: check maxDistance 3 - exactly on face of sphere, need to return only
	    // from triangle
	    assertEquals("return intersection from the sphere", 1, geo.findGeoIntersections(r1, 3).size());
	    
	    // TC06: add sphere and put the camera inside, to get only the second intersection from this sphere
	    
	    geo.add(new Sphere(new Point3D(0, 0, 0.5), 1));
	    assertEquals("not return intersection from the sphere", 1, geo.findGeoIntersections(r1, 2).size());    
	     
	  }

}
