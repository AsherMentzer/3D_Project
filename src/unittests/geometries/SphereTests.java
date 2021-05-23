package unittests.geometries;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import geometries.*;

import primitives.*;

public class SphereTests {

	@Test
	public void testGetNormal() {
		Sphere s=new Sphere(new Point3D(0,0,0),2);
		assertEquals("",new Vector(1,0,0),s.getNormal(new Point3D(2,0,0)).normalize());
	}

	/**
     * Test method for {@link geometries.Sphere#findIntersections(primitives.Ray)}.
     */
    @Test
    public void testFindIntersections() {
        Sphere sphere = new Sphere(new Point3D(1, 0, 0), 1d);

        // ============ Equivalence Partitions Tests ==============

        // TC01: Ray's line is outside the sphere (0 points)
        assertNull("Ray's line out of sphere",
                            sphere.findIntersections(new Ray(new Point3D(-1, 0, 0), new Vector(1, 1, 0))));

        // TC02: Ray starts before and crosses the sphere (2 points)
        Point3D p1 = new Point3D(0.0651530771650466, 0.355051025721682, 0);
        Point3D p2 = new Point3D(1.53484692283495, 0.844948974278318, 0);
        List<Point3D> result02 = sphere.findIntersections(new Ray(new Point3D(-1, 0, 0),
                                                                new Vector(3, 1, 0)));
        assertEquals("Wrong number of points", 2, result02.size());
        if (result02.get(0).getX() > result02.get(1).getX())
            result02 = List.of(result02.get(1), result02.get(0));      
        assertEquals("Ray crosses sphere", List.of(p1, p2), result02);
       
        // TC03: Ray starts inside the sphere (1 point)
        Point3D p03 = new Point3D(0.5, 0.8660254037844386, 0);
        List<Point3D> result03 = sphere.findIntersections(new Ray(new Point3D(0.5, 0.3, 0),
                new Vector(0, 1, 0)));
        assertEquals("Wrong number of points", 1, result03.size());
        assertEquals("Ray crosses sphere", List.of(p03), result03);
        
        // TC04: Ray starts after the sphere (0 points)
        assertNull("Ray starts after the sphere",
                sphere.findIntersections(new Ray(new Point3D(-1, 0, 0), new Vector(-1, 0, 0))));

        
        // =============== Boundary Values Tests ==================
        
        // **** Group: Ray's line crosses the sphere (but not the center)
        // TC11: Ray starts at sphere and goes inside (1 points)
         Point3D p11=new Point3D(0.5,-0.8660254037844386,0);
         List<Point3D> result11 = sphere.findIntersections(new Ray(new Point3D(0.5, 0.8660254037844386, 0),
                 new Vector(0, -1, 0)));
			assertEquals("Wrong number of points", 1, result11.size());
			assertEquals("Ray crosses sphere", List.of(p11), result11);
         
        // TC12: Ray starts at sphere and goes outside (0 points)
        assertNull("Ray starts at sphere and goes outside",
                sphere.findIntersections(new Ray(new Point3D(0.13, 0.5, 0), new Vector(-1, 0, 0))));
        
        // **** Group: Ray's line goes through the center
        // TC13: Ray starts before the sphere (2 points)
        Point3D p13 = new Point3D(2, 0, 0);
        List<Point3D> result13 = sphere.findIntersections(new Ray(new Point3D(3, 0, 0),
                                                                new Vector(-1, 0, 0)));
        assertEquals("Wrong number of points", 2, result13.size());
        if (result13.get(0).getX() > result13.get(1).getX())
            result13 = List.of(result13.get(1), result13.get(0));      
        assertEquals("Ray crosses sphere", List.of(Point3D.ZERO,p13), result13);
        
        // TC14: Ray starts at sphere and goes inside (1 points)
        List<Point3D> result14 = sphere.findIntersections(new Ray(new Point3D(2, 0, 0),
                new Vector(-1, 0, 0)));
        assertEquals("Wrong number of points", 1, result14.size());
        assertEquals("Ray crosses sphere", List.of(Point3D.ZERO), result14);
        
        // TC15: Ray starts inside (1 points)
        Point3D p15=new Point3D(2,0,0);
        List<Point3D> result15 = sphere.findIntersections(new Ray(new Point3D(0.5, 0, 0),
                new Vector(1, 0, 0)));
        assertEquals("Wrong number of points", 1, result15.size());
        assertEquals("Ray crosses sphere", List.of(p15), result15);
        
        // TC16: Ray starts at the center (1 points)
        Point3D p16=new Point3D(1,1,0);
        List<Point3D> result16 = sphere.findIntersections(new Ray(new Point3D(1, 0, 0),
                new Vector(0, 1, 0)));
        assertEquals("Wrong number of points", 1, result16.size());
        assertEquals("Ray crosses sphere", List.of(p16), result16);
        
        // TC17: Ray starts at sphere and goes outside (0 points)
        assertNull("Ray starts at sphere and goes outside",
        		sphere.findIntersections(new Ray(new Point3D(2, 0, 0), new Vector(1, 0, 0))));
        
        // TC18: Ray starts after sphere (0 points)
        assertNull("Ray starts after sphere",
        		sphere.findIntersections(new Ray(new Point3D(2.5, 0, 0), new Vector(1, 0, 0))));
        
        // **** Group: Ray's line is tangent to the sphere (all tests 0 points)
        // TC19: Ray starts before the tangent point
        assertNull("Ray starts before the tangent point",
        		sphere.findIntersections(new Ray(new Point3D(2, -0.5, 0), new Vector(0, 1, 0))));
        
        // TC20: Ray starts at the tangent point
        assertNull("Ray starts at the tangent point",
        		sphere.findIntersections(new Ray(new Point3D(2, 0, 0), new Vector(0, 1, 0))));
        
        // TC21: Ray starts after the tangent point
        assertNull("Ray starts after the tangent point",
        		sphere.findIntersections(new Ray(new Point3D(2, 0.5, 0), new Vector(0, 1, 0))));
        
        // **** Group: Special cases
        // TC19: Ray's line is outside, ray is orthogonal to ray start to sphere's center line
        assertNull("Ray's line is outside, ray is orthogonal to ray start to sphere's center line",
        		sphere.findIntersections(new Ray(new Point3D(3, 0, 0), new Vector(0, 1, 0))));
    }

}
