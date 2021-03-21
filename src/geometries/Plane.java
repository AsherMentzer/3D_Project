package geometries;

import primitives.Point3D;
import primitives.Vector;

/**
 * plane class represents two-dimensional Triangle in 3D Cartesian coordinate
 * by point and vector normal to the plane
 * @author Asher Mentzer & Mendy Kahana 
 *
 */
public class Plane implements Geometry {
	/**
	 * Associated point in which the plane lays
	 */
	private Point3D p0;
	private Vector normal;
	
	/**
	 * constructor 
	 * @param p0 the point
	 * @param normal the vector that normal to the plane
	 */
	public Plane(Point3D p0, Vector normal) {
		super();
		this.p0 = p0;
		this.normal = normal;	
	}
	
	/**
	 * constructor that get 3 points and set one of them to witch the plane lays
	 * and calculate the normal vector 
	 * @param p0 point 0
	 * @param p1 point 1
	 * @param p2 point 2
	 */
	public Plane(Point3D p0, Point3D p1,Point3D p2) {
		super();
		this.p0 = p0;/** Associated point in which the plane lays*/
		
		Vector v1=p1.subtract(p0);
		Vector v2=p2.subtract(p0);
		if(v1.getHead()==Point3D.ZERO || v2.getHead()==Point3D.ZERO)
			throw new IllegalArgumentException("the points are the same");
	
		if(v1.crossProduct(v2).getHead()==Point3D.ZERO)
			throw new IllegalArgumentException("the points are on the same line");
		
		this.normal = v1.crossProduct(v2).normalize();
	}

	/**
	 * getter
	 * @return the point 0
	 */
	public Point3D getP0() {
		return p0;
	}
    
	/**
	 * getter
	 * @return the normal vector
	 */
	public Vector getNormal() {
		return this.normal;
	}

	@Override
	public String toString() {
		return "Plane [p0=" + p0 + ", normal=" + normal + "]";
	}

	public Vector getNormal(Point3D p) {
		// TODO Auto-generated method stub
		return this.normal;
	}
	
}
