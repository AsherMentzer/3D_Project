package primitives;

/**
 * base class to rpresent ray by Point
 * and normal vector of the direction
 *
 */
public class Ray {
Point3D p0;
Vector dir;

/**
 * constructor
 * @param p the point
 * @param v the direction vector
 */
public Ray(Point3D p,Vector v){
	/*check if the vector is normelized*/
if(!(v.length()==1))
	v=v.normalize();
p0=p;
dir=v;
}

}
