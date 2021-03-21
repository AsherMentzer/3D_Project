package unitestes.geometries;

import static org.junit.Assert.*;

import org.junit.Test;

import geometries.*;

import primitives.*;

public class SphereTests {

	@Test
	public void testGetNormal() {
		Sphere s=new Sphere(new Point3D(0,0,0),2);
		assertEquals("",s.getNormal(new Point3D(2,0,0)).normalize(),new Vector(1,0,0));
	}

}
