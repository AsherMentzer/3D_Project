package scene;

import primitives.Color;

import java.util.LinkedList;

import elements.AmbientLight;
import geometries.Geometries;
import geometries.Intersectable;

/**
 * class for all scene with the name the background color the AmbientLight and
 * all the Geometries
 * 
 * @author
 */
public class Scene {

	public String name;
	public Color background = Color.BLACK;
	public AmbientLight ambientLight;
	public Geometries geometries;

	/**
	 * constructor get the name of the scene and initial the geometries to empty
	 * collection
	 * 
	 * @param name the name of the scene
	 */
	public Scene(String name) {
		this.name = name;
		this.ambientLight=new AmbientLight(Color.BLACK,0);
		geometries = new Geometries();
	}

	/**
	 * setter for background color
	 * 
	 * @param color the color
	 * @return the scene
	 */
	public Scene setBackground(Color color) {
		background = color;
		return this;
	}

	/**
	 * setter for the AmbientLight
	 * 
	 * @param color the color
	 * @param ka    double to scale the color
	 * @return the scene
	 */
	public Scene setAmbientLight(Color color, double ka) {
		ambientLight = new AmbientLight(color, ka);
		return this;
	}

	/**
	 * setter for geometries
	 * 
	 * @param geo collection to add
	 * @return the scene
	 */
	public Scene setGeometry(Intersectable geo) {
		geometries.add(geo);
		return this;
	}
}
