package primitives;

import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;
import geometries.Intersectable.GeoPoint;
import static primitives.Util.*;

/**
 * base class to rpresent ray by Point and normal vector of the direction
 *
 */
public class Ray {
	private Point3D p0;
	private Vector dir;

	private static final double DELTA = 0.1;

	/**
	 * constructor
	 * 
	 * @param p the point
	 * @param v the direction vector
	 */
	public Ray(Point3D p, Vector v) {
		// check if the vector is normalized
		if (!(v.length() == 1))
			v = v.normalize();
		p0 = p;
		dir = v;
	}

	/**
	 * constructor to assist to move the new ray to reflection and refraction
	 * 
	 * @param normal    the normal vector to this geometry in this point
	 * @param point     the point of intersection
	 * @param direction the direction of the new ray
	 */
	public Ray(Vector normal, Point3D point, Vector direction) {
		double sign = normal.dotProduct(direction);
		if (sign >= 0)
			p0 = point.add(normal.scale(DELTA));
		else
			p0 = point.add(normal.scale(-DELTA));
		dir = direction;
	}

	/**
	 * getter
	 * 
	 * @return the p0
	 */
	public Point3D getP0() {
		return p0;
	}

	/**
	 * getter
	 * 
	 * @return the vector of the direction
	 */
	public Vector getDir() {
		return dir;
	}

	/**
	 * function to get a point on the ray where v scale by t
	 * 
	 * @param t double to scale the direction vector
	 * @return the point on the ray where v scale by t
	 */
	public Point3D getPoint(double t) {
		if (Util.isZero(t))
			return p0;

		try {
			return p0.add(dir.scale(t));
		} catch (IllegalArgumentException e) {
			return p0;
		}
	}

	@Override
	public String toString() {
		return "Ray " + p0 + dir + "---";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Ray))
			return false;
		Ray other = (Ray) obj;
		return p0.equals(other.p0) && dir.equals(other.dir);
	}

	/**
	 * get list of points and return the closest point
	 * 
	 * @param points the list
	 * @return the closest point
	 */
	public Point3D getClosestPoint(List<Point3D> points) {
		if (points.isEmpty())
			return null;
		else {
			Point3D point = points.get(0);
			double distance = point.distance(p0);
			for (var p : points) {
				if (p.distance(p0) < distance) {
					point = p;
					distance = p.distance(p0);
				}
			}
			return point;
		}

	}

	/**
	 * get list of GeoPoints and return the closest GeoPoint
	 * 
	 * @param points
	 * @return the closest GeoPoint
	 */
	public GeoPoint getClosestGeoPoint(List<GeoPoint> points) {
		if (points.isEmpty())
			return null;
		else {
			GeoPoint point = points.get(0);
			double distance = point.point.distance(p0);
			for (var p : points) {
				if (p.point.distance(p0) < distance) {
					point = p;
					distance = p.point.distance(p0);
				}
			}
			return point;
		}
	}

	/**
	 * this function generate beam of rays when radius is bigger our beam spread on
	 * more area
	 * 
	 * @param n         normal vector of the point where beam start
	 * @param radius    radius of virtual circle
	 * @param distance  Distance between The intersection point to the virtual
	 *                  circle
	 * @param numOfRays The number of the rays of the beam
	 * @return beam of rays
	 */
	public List<Ray> generateBeam(Vector n, double radius, double distance, int numOfRays) {
		List<Ray> rays = new LinkedList<Ray>();
		rays.add(this);// Including the main ray
		if (numOfRays == 1 || isZero(radius))// The component (glossy surface /diffuse glass) is turned off
			return rays;

		// the 2 vectors that create the virtual grid for the beam
		Vector nX = dir.createNormal();
		Vector nY = dir.crossProduct(nX);

		Point3D centerCircle = this.getPoint(distance);
		Point3D randomPoint;
		double x, y, d;
		double nv = alignZero(n.dotProduct(dir));

		// the number of rows and columns
		int nYX = (int) Math.sqrt(numOfRays);
		// the height and width of the virtual grid
		double r = (radius * 2) / nYX;
		// the radius for each circle for each point in the grid
		double rad = radius / nYX;

		// for each point in the grid get random point in circle around this point
		for (int i = 0; i < nYX; ++i) {
			for (int j = 0; j < nYX; ++j) {
				double yI = -((i - (nYX - 1) / 2) * r);
				double xJ = (j - (nYX - 1) / 2) * r;
				Point3D pIJ = centerCircle;
				if (xJ != 0)
					pIJ = pIJ.add(nX.scale(xJ));
				if (yI != 0)
					pIJ = pIJ.add(nY.scale(yI));
				x = random(-1, 1);
				y = Math.sqrt(1 - x * x);
				d = random(-rad, rad);
				x = alignZero(x * d);
				y = alignZero(y * d);
				randomPoint = pIJ;
				if (x != 0)
					randomPoint = randomPoint.add(nX.scale(x));
				if (y != 0)
					randomPoint = randomPoint.add(nY.scale(y));
				Vector tPoint = randomPoint.subtract(p0);
				double nt = alignZero(n.dotProduct(tPoint));
				if (nv * nt > 0) {
					rays.add(new Ray(p0, tPoint));
				}
			}
		}
		return rays;
	}
}
