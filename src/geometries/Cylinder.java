package geometries;

import java.util.LinkedList;
import java.util.List;

import geometries.Intersectable.GeoPoint;
import primitives.Point3D;
import primitives.Ray;
import primitives.Util;
import primitives.Vector;

/**
 * Cylinder class heir from the Tube class
 * 
 * @author
 *
 */
public class Cylinder extends Tube {

	/**
	 * the height of the cylinder
	 */
	private double height;

	/**
	 * constructor
	 * 
	 * @param axisRay the Ray
	 * @param radius  the radius
	 * @param height  the height
	 */
	public Cylinder(Ray axisRay, double radius, double height) {
		super(axisRay, radius);
		this.height = height;
		setMinCoordinates();
		setMaxCoordinates();
	}

	/**
	 * getter
	 * 
	 * @return the height
	 */
	public double getHeight() {
		return height;
	}

	@Override
	public String toString() {
		return "Cylinder [height=" + height + ", axisRay=" + axisRay + ", radius=" + radius + "]";
	}

	/**
	 * implement the interface to find the normal to this Cylinder by specific point
	 */
	public Vector getNormal(Point3D point) {
		// case point is on the edge we take the normal Vector of Ray to be the normal
		// vector
		double t;
		try {
			// in case the point is very close to p0
			t = axisRay.getDir().dotProduct(point.subtract(axisRay.getP0()));
		} catch (IllegalArgumentException e) {
			return getAxisRay().getDir();
		}
		// if the point is on the top or on the edge of the top where the height=_height
		// or on the edge of the buttom
		if (Util.isZero(t - height) || Util.isZero(t))
			return getAxisRay().getDir();
		Point3D o = axisRay.getP0().add(axisRay.getDir().scale(t));
		return point.subtract(o).normalize();
	}

	/*
	 * public List<Point3D> findIntersections(Ray ray) { 
	 * method stub return super.findIntersections(ray); }
	 */
	
	/**
	 * implement the interface to find all the intersections between ray and this
	 * Cylinder
	 */
	@Override
	public List<GeoPoint> findGeoIntersections(Ray ray, double max) {
		Point3D centerP = axisRay.getP0();
		Vector cylinderDir = axisRay.getDir();
		List<GeoPoint> intersectios = super.findGeoIntersections(ray, max);
		List<GeoPoint> toReturn = null;
		// Check if there are intersections with the bottum of cylinder and/or the top
		// cylinder
		Plane buttomCap = new Plane(centerP, cylinderDir);
		Point3D pointAtTop = centerP.add(cylinderDir.scale(height));
		Plane topCap = new Plane(pointAtTop, cylinderDir);
		List<GeoPoint> intsB = buttomCap.findGeoIntersections(ray, max);
		List<GeoPoint> intsT = topCap.findGeoIntersections(ray, max);
		if (intsT != null) {
			GeoPoint topInter = intsT.get(0);
			double d = Util.alignZero(topInter.point.distance(pointAtTop) - radius);
			if (d < 0) {
				// intersect the top
				if (toReturn == null)
					toReturn = new LinkedList<GeoPoint>();
				topInter.geometry = this;
				toReturn.add(topInter);
			}
		}
		if (intsB != null) {
			GeoPoint bInter = intsB.get(0);
			double d = Util.alignZero(bInter.point.distance(centerP) - radius);
			if (d < 0) {
				// intersect the buttom
				if (toReturn == null)
					toReturn = new LinkedList<GeoPoint>();
				bInter.geometry = this;
				toReturn.add(bInter);
			}
		}
		if (toReturn != null && toReturn.size() == 2) // The maximum intersection points are 2
			return toReturn;
		if (intersectios == null) {
			return toReturn;
		}
		// In this point We knows that we got minimum 1 intersection point from the
		// tube.
		// check if intersection point(s) of tube relevant also for the cylinder
		GeoPoint gPoint = intersectios.get(0);
		gPoint.geometry = this;
		intsT = topCap.findGeoIntersections(new Ray(gPoint.point, cylinderDir), max);
		intsB = buttomCap.findGeoIntersections(new Ray(gPoint.point, cylinderDir.scale(-1)), max);
		if (intsT != null && intsB != null) {
			if (toReturn == null)
				toReturn = new LinkedList<GeoPoint>();
			toReturn.add(gPoint);
		}
		if (intersectios.size() == 2) {
			gPoint = intersectios.get(1);
			gPoint.geometry = this;
			intsT = topCap.findGeoIntersections(new Ray(gPoint.point, cylinderDir), max);
			intsB = buttomCap.findGeoIntersections(new Ray(gPoint.point, cylinderDir.scale(-1)), max);
			if (intsT != null && intsB != null) {
				if (toReturn == null)
					toReturn = new LinkedList<GeoPoint>();
				toReturn.add(gPoint);
			}
		}
		return toReturn;
	}
	
	@Override
	public void setMinCoordinates() {
		Point3D center = this.axisRay.getP0();
		double minX = center.getX();
		double minY = center.getY();
		double minZ = center.getZ();
		Point3D centerHeight = this.axisRay.getPoint(height);
		double centerHeightX = centerHeight.getX();
		double centerHeightY = centerHeight.getY();
		double centerHeightZ = centerHeight.getZ();
		if (minX > centerHeightX)
			minX = centerHeightX;
		if (minY > centerHeightY)
			minY = centerHeightY;
		if (minZ > centerHeightZ)
			minZ = centerHeightZ;
		minX -= radius;
		minY -= radius;
		minZ -= radius;
		_minBoundary = new Point3D(minX, minY, minZ);
	}

	@Override
	public void setMaxCoordinates() {
		Point3D center = this.axisRay.getP0();
		double maxX = center.getX();
		double maxY = center.getY();
		double maxZ = center.getZ();
		Point3D centerHeight = this.axisRay.getPoint(height);
		double centerHeightX = centerHeight.getX();
		double centerHeightY = centerHeight.getY();
		double centerHeightZ = centerHeight.getZ();
		if (maxX < centerHeightX)
			maxX = centerHeightX;
		if (maxY < centerHeightY)
			maxY = centerHeightY;
		if (maxZ < centerHeightZ)
			maxZ = centerHeightZ;
		maxX += radius;
		maxY += radius;
		maxZ += radius;
		_maxBoundary = new Point3D(maxX, maxY, maxZ);
	}

	
}