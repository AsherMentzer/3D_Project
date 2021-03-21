package unitestes.geometries;

import static org.junit.Assert.*;

import org.junit.Test;

import primitives.*;

import geometries.*;

public class CylinderTests {

	@Test
	public void testGetNormal() {
		Cylinder c=new Cylinder(new Ray(new Point3D(0,0,1),new Vector(0,0,1)),2,2);
		assertEquals("get normal() wrong value",new Vector(0,1,0),c.getNormal(new Point3D(0,2,1.5)) );
		assertEquals("get normal() wrong value",new Vector(0,0,1),c.getNormal(new Point3D(0,1,3)) );
		assertEquals("get normal() wrong value",new Vector(0,0,1),c.getNormal(new Point3D(1,1,-1)) );
	}

}
