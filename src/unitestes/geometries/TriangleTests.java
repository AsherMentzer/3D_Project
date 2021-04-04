package unitestes.geometries;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import geometries.Triangle;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

public class TriangleTests {

	@Test
	public void testFindIntersections() {
		Triangle t =new Triangle(new Point3D(0,0,0),new Point3D(2,0,0)
				,new Point3D(1,1,0));
		// ============ Equivalence Partitions Tests ==============		
		//TC01 Inside triangle(1 point)
		Point3D p1=new Point3D(0.5,0.5,0);
		List<Point3D>result=t.findIntersections(new Ray(new Point3D(0,0,1),
				new Vector(1.5,0.5,-1)));
		 assertEquals("Wrong number of points", 1, result.size());
         assertEquals("Ray crosses triangle", List.of(p1), result);
         
		//TC02 Outside against edge(0 points)
		assertNull("Outside against edge", t.findIntersections(new Ray(new Point3D(0,0,1),
				new Vector(0.1,0.1,-1))));
		
		//TC03 Outside against vertex(0 points)
		assertNull("Outside against vertex", t.findIntersections(new Ray(new Point3D(0,0,1),
				new Vector(1,2,-1))));
		
		// =============== Boundary Values Tests ==================
		
		//TC04 On edge
		assertNull("Outside against vertex", t.findIntersections(new Ray(new Point3D(0,0,1),
				new Vector(1,0,-1))));
		
		//TC05 In vertex
		assertNull("Outside against vertex", t.findIntersections(new Ray(new Point3D(0,0,1),
				new Vector(2,0,-1))));
		
		//TC06 On edge's continuation
		assertNull("Outside against vertex", t.findIntersections(new Ray(new Point3D(0,0,1),
				new Vector(3,0,-1))));
	}

}
