package geometries;

import primitives.*;


/**
 * abstract class for all the shapes with function to return the normal to this shape in
 * specific point
 * 
 * @author Asher Mentzer & Mendy Kahana
 *
 */
public abstract class Geometry implements Intersectable {
	
	protected Color emission=Color.BLACK;
	
	/**
	 * getter function
	 * @return the emission color
	 */
	public Color getEmission() {
		return emission;
	}

	/**
	 * setter function
	 * @param emmission the emisssion color
	 * @return
	 */
	public Geometry setEmission(Color emission) {
		this.emission = emission;
		return this;
	}

	/**
	 * abstract function to get the normal to this 
	 * shape in the giving point
	 * @param p the point
	 * @return the normal vector
	 */
	public abstract Vector getNormal(Point3D p);
}
