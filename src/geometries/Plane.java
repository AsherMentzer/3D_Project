package geometries;

import primitives.Point3D;
import primitives.Vector;

public class Plane implements Geometry {
	private Point3D p0;
	private Vector normal;
	
	public Plane(Point3D p0, Vector normal) {
		super();
		this.p0 = p0;
		this.normal = normal;	
	}
	
	public Plane(Point3D p0, Point3D p1,Point3D p2) {
		super();
		this.p0 = p0;
		this.normal = null;
	}


	public Point3D getP0() {
		return p0;
	}

	public Vector getNormal() {
		return normal;
	}

	@Override
	public String toString() {
		return "Plane [p0=" + p0 + ", normal=" + normal + "]";
	}

	@Override
	public Vector getNormal(Point3D p) {
		// TODO Auto-generated method stub
		return null;
	}

}
