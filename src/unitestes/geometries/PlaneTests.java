/**
 * 
 */
package unitestes.geometries;

import static org.junit.Assert.*;

import org.junit.Test;

import primitives.*;

import geometries.*;

/**
 * @author ashme
 *
 */
public class PlaneTests {

	/**
	 * Test method for {@link geometries.Plane#Plane(primitives.Point3D, primitives.Point3D, primitives.Point3D)}.
	 */
	@Test
	public void testPlanePoint3DPoint3DPoint3D() {
	
		try {
			Plane p1=new Plane(new Point3D(1,2,3),new Point3D(1,2,3),new Point3D(2,2,2));
			fail("Constructed a plane with the same point");
		}catch(Exception e) {}
		
		try {
			Plane p2=new Plane(new Point3D(1,2,3),new Point3D(2,4,6),new Point3D(3,6,9));
			fail("Constructed a plane with points on the same line");
		}catch(Exception e) {}
	}

	/**
	 * Test method for {@link geometries.Plane#getNormal(primitives.Point3D)}.
	 */
	@Test
	public void testGetNormalPoint3D() {
		Plane p = new Plane(new Point3D(0, 0, 0), new Point3D(1, 0, 0), new Point3D(0, 1, 0));
		Vector v1 = new Vector(0, 0, 1);
		Vector v2 = new Vector(0, 0, -1);
		Vector normal=p.getNormal(new Point3D(0,0,0));
		  assertTrue("the normal has wrong value",normal.equals(v1) || normal.equals(v2));
		 
		//assertEquals("", p.getNormal(new Point3D(0, 0, 0)), v1);

	}

}
