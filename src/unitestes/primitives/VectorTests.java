/**
 * 
 */
package unitestes.primitives;

import static org.junit.Assert.*;

import org.junit.Test;

import primitives.*;


/**
 * @author ashme
 *
 */
public class VectorTests {

	/**
	 * Test method for {@link primitives.Vector#Vector(primitives.Coordinate, primitives.Coordinate, primitives.Coordinate)}.
	 */
	@Test
	public void testVectorCoordinateCoordinateCoordinate() {
		Coordinate x=new Coordinate(0);
		Coordinate y=new Coordinate(0);
		Coordinate z=new Coordinate(0);
		try {
			Vector v1=new Vector(x,y,z);
			 fail("constructor() for zero vector does not throw an exception");
        } catch (Exception e) {}
	}

	/**
	 * Test method for {@link primitives.Vector#Vector(double, double, double)}.
	 */
	@Test
	public void testVectorDoubleDoubleDouble() {
		double x=0;
		double y=0;
		double z=0;
		try {
			Vector v1=new Vector(x,y,z);
			 fail("constructor() for zero vector does not throw an exception");
        } catch (Exception e) {}
	}

	/**
	 * Test method for {@link primitives.Vector#Vector(primitives.Point3D)}.
	 */
	@Test
	public void testVectorPoint3D() {
		Point3D p=new Point3D(0,0,0);
		try {
			Vector v1=new Vector(p);
			 fail("constructor() for zero vector does not throw an exception");
        } catch (Exception e) {}
	}

	/**
	 * Test method for {@link primitives.Vector#add(primitives.Vector)}.
	 */
	@Test
	public void testAdd() {
		Vector v1 =new Vector(1,2,3);
		// ============ Equivalence Partitions Tests ==============
		Vector v2 =new Vector(2,3,-1);
		Vector v3 =new Vector(3,5,2);
			
		assertEquals("Vectro,add() wrong result", v3, v1.add(v2));
		
		  // =============== Boundary Values Tests ==================
        // test zero vector from add vectors
		Vector v4 =new Vector(-1,-2,-3);
		try {
			Vector vr=v1.add(v4);
			 fail("add() for result Zero vector does not throw an exception");
		}catch(Exception e) {}
		
	}

	/**
	 * Test method for {@link primitives.Vector#subtract(primitives.Vector)}.
	 */
	@Test
	public void testSubtract() {
		Vector v1 =new Vector(1,2,3);
		// ============ Equivalence Partitions Tests ==============
		Vector v2 =new Vector(2,3,-1);
		Vector v3 =new Vector(-1,-1,4);
			
		assertEquals("Subtract() wrong result", v3, v1.subtract(v2));
		
		  // =============== Boundary Values Tests ==================
        // test zero vector from subtract vectors
		Vector v4 =new Vector(1,2,3);
		try {
			Vector vr=v1.subtract(v4);
			 fail("add() for result Zero vector does not throw an exception");
		}catch(Exception e) {}
		
	}

	/**
	 * Test method for {@link primitives.Vector#scale(double)}.
	 */
	@Test
	public void testScale() {
		Vector v1 =new Vector(2.5,5.2,3);
		// ============ Equivalence Partitions Tests ==============
		double scale=2.1;
		Vector v2 =new Vector(5.25,10.92,6.3);
		
		assertEquals("Subtract() wrong result", v2, v1.scale(scale));
		
		  // =============== Boundary Values Tests ==================
        // test zero vector from subtract vectors
		double scale2=0;
		try {
			Vector vr=v1.scale(scale2);
			 fail("scale() for result Zero vector does not throw an exception");
		}catch(Exception e) {}
		
	}

	/**
	 * Test method for {@link primitives.Vector#crossProduct(primitives.Vector)}.
	 */
	@Test
	public void testCrossProduct() {
		Vector v1 = new Vector(1, 2, 3);
        Vector v2 = new Vector(-2, -4, -6);

        // ============ Equivalence Partitions Tests ==============
        Vector v3 = new Vector(0, 3, -2);
        Vector vr = v1.crossProduct(v3);
        
        // Test that length of cross-product is proper (orthogonal vectors taken for simplicity)
        assertEquals("crossProduct() wrong result length", v1.length() * v3.length(), vr.length(), 0.00001);
        
        // Test cross-product result orthogonality to its operands
        assertTrue("crossProduct() result is not orthogonal to 1st operand", Util.isZero(vr.dotProduct(v1)));
        assertTrue("crossProduct() result is not orthogonal to 2nd operand", Util.isZero(vr.dotProduct(v3)));

        // =============== Boundary Values Tests ==================
        // test zero vector from cross-productof co-lined vectors
        try {
            v1.crossProduct(v2);
            fail("crossProduct() for parallel vectors does not throw an exception");
        } catch (Exception e) {}
	}

	/**
	 * Test method for {@link primitives.Vector#dotProduct(primitives.Vector)}.
	 */
	@Test
	public void testDotProduct() {
		Vector v1 = new Vector(1, 2, 3);
        Vector v2 = new Vector(0, 3, -2);
		 // ============ Equivalence Partitions Tests ==============     
        assertEquals("dotProduct()wrong result", 0,v1.dotProduct(v2),0.00001);
	}

	/**
	 * Test method for {@link primitives.Vector#lengthSquared()}.
	 */
	@Test
	public void testLengthSquared() {
		Vector v1 =new Vector(1,2,3);
		// ============ Equivalence Partitions Tests ==============
		assertEquals("LengthSquared() wrong result", 14, v1.lengthSquared(),0.00001);
	}

	/**
	 * Test method for {@link primitives.Vector#length()}.
	 */
	@Test
	public void testLength() {
		Vector v1 =new Vector(4,0,3);
		// ============ Equivalence Partitions Tests ==============
		assertEquals("LengthSquared() wrong result", 5, v1.length(),0.00001);
	}

	/**
	 * Test method for {@link primitives.Vector#normalize()}.
	 */
	@Test
	public void testNormalize() {
		Vector v = new Vector(3.5,-5,10);
		v.normalize();
		assertEquals("", 1, v.length(),1e-10);
		
		/*v = new Vector(0,0,0);
		try {
			v.normalize();
			fail("Didn't throw divide by zero exception!");
		} catch (ArithmeticException e) {
			assertTrue(true);
		}*/
	}

	/**
	 * Test method for {@link primitives.Vector#normalized()}.
	 */
	@Test
	public void testNormalized() {
		Vector v1=new Vector(5,10,2);
		Vector v2=v1.normalized();
		assertEquals(null, 1,v2.length(),1e-10);
	}

}
