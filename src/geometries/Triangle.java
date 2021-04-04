package geometries;

import java.util.List;

import primitives.*;

/**
 * Triangle class represents two-dimensional Triangle in 3D Cartesian coordinate
 * system heir from the polygon 
 * @author Asher Mentzer & Mendy Kahana 
 *
 */
public class Triangle extends Polygon {

	/**
	 * constructor that get 3 points and use the polygon constructor
	 * @param vertices
	 */
	public Triangle(Point3D... vertices) {
		super(vertices);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "Triangle [vertices=" + vertices + ", plane=" + plane + "]";
	}

	public Triangle (Point3D p1,Point3D p2,Point3D p3) {
		super(p1,p2,p3);
	}
	
	/**
	 * implement the interface to find all the intersections
	 * between ray and this triangle 
	 */
	public List<Point3D>findIntersections(Ray ray){
		return null;
	}
}
