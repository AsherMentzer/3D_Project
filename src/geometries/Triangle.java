package geometries;

import java.util.List;

import primitives.*;

import static primitives.Util.*;

/**
 * Triangle class represents two-dimensional Triangle in 3D Cartesian coordinate
 * system heir from the polygon
 * 
 * @author Asher Mentzer & Mendy Kahana
 *
 */
public class Triangle extends Polygon {

	@Override
	public String toString() {
		return "Triangle [vertices=" + vertices + ", plane=" + plane + "]";
	}

	/**
	 * constructor that get 3 points and use the polygon constructor
	 * 
	 * @param p1 the first point
	 * @param p2 the second point
	 * @param p3 the third point
	 */
	public Triangle(Point3D p1, Point3D p2, Point3D p3) {
		super(p1, p2, p3);
	}

	/*
	public List<Point3D> findIntersections(Ray ray) {
		Point3D p0 = ray.getP0();
		Vector v = ray.getDir();

		List<Point3D> l = null;
		l = this.plane.findIntersections(ray);
		if (l == null)
			return null;

		// all the vectors from the p0 of the ray
		// to all the points of the edges of the polygon
		Vector v1 = this.vertices.get(0).subtract(p0);
		Vector v2 = this.vertices.get(1).subtract(p0);
		Vector v3 = this.vertices.get(2).subtract(p0);

		// all the vectors that normals to each
		// triangle that created from the vectors
		Vector N1 = v1.crossProduct(v2);
		Vector N2 = v2.crossProduct(v3);
		Vector N3 = v3.crossProduct(v1);

		/*
		 * all the double t of dot product between the vector v of the ray and each
		 * normal vector we found if all the t doubles have the same sign so the point
		 * is in the polygon else is out or if 1t is 0 is on vertices or if 2 are 0 is
		 * in edge
		 *//*
		double t1 = alignZero(v.dotProduct(N1));
		double t2 = alignZero(v.dotProduct(N2));
		double t3 = alignZero(v.dotProduct(N3));
		if ((t1 > 0 && t2 > 0 && t3 > 0) || (t1 < 0 && t2 < 0 && t3 < 0)) {
			return l;
		}
		return null;
	}
	*/
	
	/**
	 * implement the interface to find all the intersections between ray and this
	 * triangle
	 */
	public List<GeoPoint> findGeoIntersections(Ray ray) {
		Point3D p0 = ray.getP0();
		Vector v = ray.getDir();

		List<GeoPoint> l = null;
		l = this.plane.findGeoIntersections(ray);
		if (l == null)
			return null;

		// all the vectors from the p0 of the ray
		// to all the points of the edges of the polygon
		Vector v1 = this.vertices.get(0).subtract(p0);
		Vector v2 = this.vertices.get(1).subtract(p0);
		Vector v3 = this.vertices.get(2).subtract(p0);

		// all the vectors that normals to each
		// triangle that created from the vectors
		Vector N1 = v1.crossProduct(v2);
		Vector N2 = v2.crossProduct(v3);
		Vector N3 = v3.crossProduct(v1);

		/*
		 * all the double t of dot product between the vector v of the ray and each
		 * normal vector we found if all the t doubles have the same sign so the point
		 * is in the polygon else is out or if 1t is 0 is on vertices or if 2 are 0 is
		 * in edge
		 */
		double t1 = alignZero(v.dotProduct(N1));
		double t2 = alignZero(v.dotProduct(N2));
		double t3 = alignZero(v.dotProduct(N3));
		if ((t1 > 0 && t2 > 0 && t3 > 0) || (t1 < 0 && t2 < 0 && t3 < 0)) {
			l.get(0).geometry = this;
			return l;
		}
		return null;
	}

}
