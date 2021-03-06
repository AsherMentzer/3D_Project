package geometries;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import primitives.*;
import static primitives.Util.*;

/**
 * Polygon class represents two-dimensional polygon in 3D Cartesian coordinate
 * system
 * 
 * @author Dan
 */
public class Polygon extends Geometry {
	private static final double DELTA = 0.1;
	/**
	 * List of polygon's vertices
	 */
	protected List<Point3D> vertices;
	/**
	 * Associated plane in which the polygon lays
	 */
	protected Plane plane;

	/**
	 * Polygon constructor based on vertices list. The list must be ordered by edge
	 * path. The polygon must be convex.
	 * 
	 * @param vertices list of vertices according to their order by edge path
	 * @throws IllegalArgumentException in any case of illegal combination of
	 *                                  vertices:
	 *                                  <ul>
	 *                                  <li>Less than 3 vertices</li>
	 *                                  <li>Consequent vertices are in the same
	 *                                  point
	 *                                  <li>The vertices are not in the same
	 *                                  plane</li>
	 *                                  <li>The order of vertices is not according
	 *                                  to edge path</li>
	 *                                  <li>Three consequent vertices lay in the
	 *                                  same line (180&#176; angle between two
	 *                                  consequent edges)
	 *                                  <li>The polygon is concave (not convex)</li>
	 *                                  </ul>
	 */
	public Polygon(Point3D... vertices) {
		if (vertices.length < 3)
			throw new IllegalArgumentException("A polygon can't have less than 3 vertices");
		this.vertices = List.of(vertices);
		// Generate the plane according to the first three vertices and associate the
		// polygon with this plane.
		// The plane holds the invariant normal (orthogonal unit) vector to the polygon
		plane = new Plane(vertices[0], vertices[1], vertices[2]);
		if (vertices.length == 3) {
			setMinCoordinates();
		    setMaxCoordinates();
			return; // no need for more tests for a Triangle
		}
		Vector n = plane.getNormal();

		// Subtracting any subsequent points will throw an IllegalArgumentException
		// because of Zero Vector if they are in the same point
		Vector edge1 = vertices[vertices.length - 1].subtract(vertices[vertices.length - 2]);
		Vector edge2 = vertices[0].subtract(vertices[vertices.length - 1]);

		// Cross Product of any subsequent edges will throw an IllegalArgumentException
		// because of Zero Vector if they connect three vertices that lay in the same
		// line.
		// Generate the direction of the polygon according to the angle between last and
		// first edge being less than 180 deg. It is hold by the sign of its dot product
		// with
		// the normal. If all the rest consequent edges will generate the same sign -
		// the
		// polygon is convex ("kamur" in Hebrew).
		boolean positive = edge1.crossProduct(edge2).dotProduct(n) > 0;
		for (int i = 1; i < vertices.length; ++i) {
			// Test that the point is in the same plane as calculated originally
			if (!isZero(vertices[i].subtract(vertices[0]).dotProduct(n)))
				throw new IllegalArgumentException("All vertices of a polygon must lay in the same plane");
			// Test the consequent edges have
			edge1 = edge2;
			edge2 = vertices[i].subtract(vertices[i - 1]);
			if (positive != (edge1.crossProduct(edge2).dotProduct(n) > 0))
				throw new IllegalArgumentException("All vertices must be ordered and the polygon must be convex");
		}
		setMinCoordinates();
		setMaxCoordinates();
	}

	/**
	 * implement the interface to find the normal to this Polygon by specific point
	 */
	public Vector getNormal(Point3D point) {
		return plane.getNormal();
	}

	/**
	 * implement the interface to find all the intersections between ray and this
	 * polygon
	 */
	@Override
	public List<GeoPoint> findGeoIntersections(Ray ray,double maxDistance) {
		Point3D p0 = ray.getP0();
		Vector v = ray.getDir();

		// check if the point not intersect the plane
		// of the polygon so for sure not intersect the polygon
		List<GeoPoint> l = null;
		l = this.plane.findGeoIntersections(ray);
		if (l == null)
			return null;

		/*
		 * create vectors from the p0 of the ray to all the points of the edges of the
		 * polygon list of all the vectors that normals to each triangle that created
		 * from the vectors
		 */
		List<Vector> normals = new LinkedList<Vector>();
		List<Double> t = new ArrayList<Double>();
		int size = this.vertices.size();
		Vector v1;
		v1 = p0.subtract(vertices.get(0));
		for (int i = 1; i < size; i++) {
			Vector v2 = p0.subtract(vertices.get(i));
			Vector n = v1.crossProduct(v2);
			Double d = alignZero(v.dotProduct(n));
			t.add(d);
			v1 = v2;
			normals.add(n);
		}

		Vector v2 = p0.subtract(vertices.get(0));
		Vector n = v1.crossProduct(v2);
		normals.add(n);
		Double d = alignZero(v.dotProduct(n));
		t.add(d);
		/*
		 * list of double t of dot product between the vector v of the ray and each
		 * normal vector we found if all the t doubles have the same sign so the point
		 * is in the polygon else is out or if 1t is 0 is on vertices or if 2 are 0 is
		 * in edge
		 */

		double t1 = t.get(0);
		if (isZero(t1)|| !(alignZero(t1-maxDistance) <= 0))
			return null;

		if (t1 > 0) {
			for (double d1 : t) {
				if (d1 < 0 || isZero(d1)|| !(alignZero(d1-maxDistance) <= 0))
					return null;
			}
		} else if (t1 < 0) {
			for (double d1 : t) {
				if (d1 > 0 || isZero(d1)||!(alignZero(d1-maxDistance) <= 0))
					return null;
			}
		}

		l.get(0).geometry = this;
		return l;
	}

	/*@Override
	public Point3D getMinCoordinates() {
		double minX = Double.POSITIVE_INFINITY;
		double minY = Double.POSITIVE_INFINITY;
		double minZ = Double.POSITIVE_INFINITY;
		double x, y, z;
		for (Point3D p : vertices) {
			x = p.getX();
			y = p.getY();
			z = p.getZ();
			if (x < minX)
				minX = x;
			if (y < minY)
				minY = y;
			if (z < minZ)
				minZ = z;
		}
		return  new Point3D(minX - DELTA, minY - DELTA, minZ - DELTA);
	}

	@Override
	public Point3D getMaxCoordinates() {
		double maxX = Double.NEGATIVE_INFINITY;
		double maxY = Double.NEGATIVE_INFINITY;
		double maxZ = Double.NEGATIVE_INFINITY;
		double x, y, z;
		for (Point3D p : vertices) {
			x = p.getX();
			y = p.getY();
			z = p.getZ();
			if (x > maxX)
				maxX = x;
			if (y > maxY)
				maxY = y;
			if (z > maxZ)
				maxZ = z;
		}
		return new Point3D(maxX + DELTA, maxY + DELTA, maxZ + DELTA);
	}*/
	@Override
	public void setMinCoordinates() {
		double minX = Double.POSITIVE_INFINITY;
		double minY = Double.POSITIVE_INFINITY;
		double minZ = Double.POSITIVE_INFINITY;
		double x, y, z;
		for (Point3D p : vertices) {
			x = p.getX();
			y = p.getY();
			z = p.getZ();
			if (x < minX)
				minX = x;
			if (y < minY)
				minY = y;
			if (z < minZ)
				minZ = z;
		}
		_minBoundary = new Point3D(minX - DELTA, minY - DELTA, minZ - DELTA);
	}

	@Override
	public void setMaxCoordinates() {
		double maxX = Double.NEGATIVE_INFINITY;
		double maxY = Double.NEGATIVE_INFINITY;
		double maxZ = Double.NEGATIVE_INFINITY;
		double x, y, z;
		for (Point3D p : vertices) {
			x = p.getX();
			y = p.getY();
			z = p.getZ();
			if (x > maxX)
				maxX = x;
			if (y > maxY)
				maxY = y;
			if (z > maxZ)
				maxZ = z;
		}
		_maxBoundary = new Point3D(maxX + DELTA, maxY + DELTA, maxZ + DELTA);
	}
	
}