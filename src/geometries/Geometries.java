/**
 * 
 */
package geometries;

import java.util.LinkedList;
import java.util.List;

import primitives.Point3D;
import primitives.Ray;

/**
 * @author ashme
 *
 */
public class Geometries implements Intersectable {

	private List<Intersectable>intersections;
	
	public Geometries() {
		super();
		this.intersections=new LinkedList();	
	}
	
	public Geometries(Intersectable... geometries) {
		super();
		this.intersections =List.of(geometries);
	}

	public void add(Intersectable... geometries) {
		this.intersections.addAll(geometries);
	}
	
	@Override
	public List<Point3D> findIntersections(Ray ray) {
		// TODO Auto-generated method stub
		return null;
	}

}
