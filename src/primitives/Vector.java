package primitives;

import static primitives.Point3D.ZERO;

/**
 * class for representing a vector that have the point3D for the head and the
 * vector is from the origin to this point
 */
public class Vector {

	private Point3D head;

	/**
	 * constructor with 3 coordinates
	 * 
	 * @param x the x coordinate
	 * @param y the y coordinate
	 * @param z the z coordinate
	 */
	public Vector(Coordinate x, Coordinate y, Coordinate z) {
		super();
		Point3D p = new Point3D(x, y, z);
		if (p.equals(Point3D.ZERO))
			throw new IllegalArgumentException("the vector can't be the ZERO vector");
		this.head = p;
	}

	/**
	 * constructor with 3 doubles for the coordinates
	 * 
	 * @param x the double for the x coordinate
	 * @param y the double for the y coordinate
	 * @param z the double for the z coordinate
	 */
	public Vector(double x, double y, double z) {
		super();
		Point3D p = new Point3D(x, y, z);
		if (p.equals(Point3D.ZERO))
			throw new IllegalArgumentException("the vector can't be the ZERO vector");
		this.head = p;
	}

	/**
	 * constructor
	 * 
	 * @param head the head of the vector point
	 */
	public Vector(Point3D head) {
		super();
		if (head.equals(Point3D.ZERO))/*---------------------- */
			throw new IllegalArgumentException("the vector can't be the ZERO vector");
		this.head = head;
	}

	public Point3D getHead() {
		return head;
	}

	/**
	 * Func to add vector to our vector
	 * 
	 * @param v the vector to add
	 * @return the new vector of the result
	 */
	public Vector add(Vector v) {
		return new Vector(this.head.x.coord + v.head.x.coord, this.head.y.coord + v.head.y.coord,
				this.head.z.coord + v.head.z.coord);
	}

	/**
	 * Func to sub vector from our vector
	 * 
	 * @param v the vector to sub
	 * @return the new vector of the result
	 */
	public Vector subtract(Vector v) {
		return new Vector(this.head.x.coord - v.head.x.coord, this.head.y.coord - v.head.y.coord,
				this.head.z.coord - v.head.z.coord);
	}

	/**
	 * Func to multy the vector by scale by double
	 * 
	 * @param d the number to scale the vector
	 * @return the new vector of the result
	 */
	public Vector scale(double scale) {
		if (scale == 0.0)
			throw new IllegalArgumentException("the vector can't be the ZERO vector");

		return new Vector(this.head.x.coord * scale, this.head.y.coord * scale, this.head.z.coord * scale);
	}

	/**
	 * Func to do crossProduct between 2 vectors
	 * 
	 * @param v the second vector
	 * @return the new vector of the result
	 */
	public Vector crossProduct(Vector v) {
		return new Vector((this.head.y.coord * v.head.z.coord) - (this.head.z.coord * v.head.y.coord),
				(this.head.z.coord * v.head.x.coord) - (this.head.x.coord * v.head.z.coord),
				(this.head.x.coord * v.head.y.coord) - (this.head.y.coord * v.head.x.coord));
	}

	/**
	 * Func to do dotProduct between 2 vectors
	 * 
	 * @param v the second vector
	 * @return the number of the result
	 */
	public double dotProduct(Vector v) {
		return ((this.head.x.coord * v.head.x.coord) + (this.head.y.coord * v.head.y.coord)
				+ (this.head.z.coord * v.head.z.coord));
	}

	/**
	 * Func for the length^2 of the vector
	 * 
	 * @return the length^2
	 */
	public double lengthSquared() {
		return ((this.head.x.coord * this.head.x.coord) + (this.head.y.coord * this.head.y.coord)
				+ (this.head.z.coord * this.head.z.coord));
	}

	/**
	 * Func to get the length of the vector
	 * 
	 * @return the length of the vector
	 */
	public double length() {
		return Math.sqrt(lengthSquared());
	}

	/**
	 * Func to normelize our vector
	 * 
	 * @return our vector(after the change)
	 */
	public Vector normalize() {
		if (length() == 0)
			throw new ArithmeticException("no divide by zero");

		this.head = this.scale((1 / length())).head;
		return this;
	}

	/**
	 * Func to return the normelized vector
	 * 
	 * @return the normelized vector
	 */
	public Vector normalized() {
		return new Vector(head).normalize();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Vector))
			return false;
		Vector other = (Vector) obj;
		return head.equals(other.head);
	}

	@Override
	public String toString() {
		return "--->" + head;
	}
	
	/**
	 * This function return a normal Vector to "this" vector
	 * @return normalized normal vector
	 */
	public Vector createNormal() {
		int min = 1;
		double x = head.getX(), y = head.getY(), z = head.getZ();
		double minCoor = x > 0 ? x : -x;
		if (y < minCoor ||-y < minCoor) {//(Math.abs(y) < minCoor) {
			minCoor = y > 0 ? y : -y;
			min = 2;
		}
		if (z < minCoor ||-z < minCoor) {///Math.abs(z) < minCoor) {
			min = 3;
		}
		switch (min) {
		case 1: {
			return new Vector(0, -z, y).normalize();
		}
		case 2: {
			return new Vector(-z, 0, x).normalize();
		}
		case 3: {
			return new Vector(y, -x, 0).normalize();
		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + min);
		}
	}

}
