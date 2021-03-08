package primitives;

import static primitives.Point3D.ZERO;

public class Vector {

	Point3D head;
	
	public Vector(Coordinate x,Coordinate y,Coordinate z) {
		super();
		Point3D p=new Point3D(x,y,z);
		if(p.equals(Point3D.ZERO))
			throw new IllegalArgumentException("the vector can't be the ZERO vector");
		this.head=p;
	}
	
	public Vector(double x,double y,double z) {
		super();
		Point3D p=new Point3D(x,y,z);
		if(p.equals(Point3D.ZERO))
			throw new IllegalArgumentException("the vector can't be the ZERO vector");
		this.head=p;
	}
	
	public Vector(Point3D head) {
		super();
		if(head.equals(Point3D.ZERO))
			throw new IllegalArgumentException("the vector can't be the ZERO vector");
		this.head = head;
	}

	public Vector add(Vector v) {
		
	}
	
	public Vector subtract(Vector v) {
		
	}
	
	public Vector scale(double d) {
		
	}
	
	public Vector crossProduct(Vector v) {
		
	}
	
	public double dotProduct(Vector v) {
		
	}
	
	public double lengthSquared() {
		
	}
	
	public double length() {
		
	}
	
	public Vector normalize() {
		
	}
	
	public Vector normalized() {
		
	}
	
	@Override
	public boolean equals(Object obj) {
	if (this == obj) return true;
	if (obj == null) return false;
	if (!(obj instanceof Vector)) return false;
	Vector other = (Vector)obj;
	return head.equals(other.head);
	}

}
