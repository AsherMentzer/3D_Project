package unitestes.geometries;

import static org.junit.Assert.*;

import org.junit.Test;

import primitives.*;

import geometries.*;

public class TubeTests {

	@Test
	public void testTube() {
		
	}

	@Test
	public void testGetNormal() {
		
		Tube t=new Tube(new Ray(new Point3D(0,0,1),new Vector(0,0,1)),2);
		assertEquals("get normal() wrong value",new Vector(0,1,0),t.getNormal(new Point3D(0,2,2)) );
		//Boundary check
		assertEquals("get normal() wrong value",new Vector(0,1,0),t.getNormal(new Point3D(0,1,1)) );
	}
}
