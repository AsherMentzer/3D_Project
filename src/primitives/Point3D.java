package primitives;

import static primitives.Util.isZero;

/**
 * class Point3d is base class to represent 1
 * point in 3d with 3 coordinate
 * 
 * @author Asher Mentzer & Mendy Kahana 
 *
 */
public final class Point3D {
 Coordinate x;
 Coordinate y;
 Coordinate z;

 /**
  * final static for the origin point
  */
public static final Point3D ZERO=new Point3D(0,0,0);
 
 /**
  * constructor with 3 coordinates
  * @param x the x coordinate
  * @param y the y coordinate
  * @param z the z coordinate
  */
public Point3D(Coordinate x,Coordinate y,Coordinate z) {
	super();
	this.x=x;
	this.y=y;
	this.z=z;
}

/**
 * constructor with 3 double
 * @param x the double for x coordinate
 * @param y the double for y coordinate
 * @param z the double for z coordinate
 */
public Point3D(double x,double y,double z){
	super();
	this.x=new Coordinate(x);
	this.y=new Coordinate(y);
	this.z=new Coordinate(z);
}

/**
 * func to add vector to point
 * @param v the vector to add
 * @return new point3d
 */
public Point3D add(Vector v) {
	return new Point3D(new Coordinate(this.x.coord+v.getHead().x.coord),
			new Coordinate(this.y.coord+v.getHead().y.coord),new Coordinate(this.z.coord+v.getHead().z.coord));
}

/**
 * func that return the vector that created by the point2- this point
 * @param p2 the second point3d
 * @return the vector
 */
public Vector subtract(Point3D p2){
	return new Vector(this.x.coord-p2.x.coord,this.y.coord-p2.y.coord,this.z.coord-p2.z.coord);
}

/**
 * Func to get the distance^2 of the point
 * 
 * @param p the point
 * @return the distance^2
 */
public double distanceSquared(Point3D p) {
	return((p.x.coord*p.x.coord)+(p.y.coord*p.y.coord)+(p.z.coord*p.z.coord));
}

/**
 * Func to get the distance of the point
 * @param p the point 
 * @return the distance
 */
public double distance(Point3D p) {
	return Math.sqrt(distanceSquared(p));
}

@Override
public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (!(obj instanceof Point3D)) return false;
    Point3D other = (Point3D)obj;
    return x.equals(other.x) && y.equals(other.y) && z.equals(other.z);
}

}
