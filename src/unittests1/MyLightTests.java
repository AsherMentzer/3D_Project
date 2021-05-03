package unittests1;

import org.junit.Test;

import elements.*;
import geometries.*;
import primitives.*;
import renderer.*;
import scene.*;

public class MyLightTests {
	private Scene scene1 = new Scene("Test scene");
	/*private Scene scene2 = new Scene("Test scene") //
			.setAmbientLight(new Color(java.awt.Color.WHITE), 0.15);*/
	private Camera camera1 = new Camera(new Point3D(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
			.setViewPlaneSize(150, 150) //
			.setDistance(1000);
	/*private Camera camera2 = new Camera(new Point3D(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
			.setViewPlaneSize(200, 200) //
			.setDistance(1000);*/

	
	private static Geometry triangle1 = new Triangle( //
			new Point3D(-50, 0, -150), new Point3D(50, 0, -150), new Point3D(0, 30, -150))
			.setEmission(new Color(java.awt.Color.RED))
			.setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(100));
	private static Geometry polygon1 = new Polygon( //
			new Point3D(-50, 0, -150), new Point3D(-50, -50, -150), new Point3D(50, -50, -150),new Point3D(50, 0, -150))
			.setEmission(new Color(java.awt.Color.GRAY))
			.setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(100));
	private static Geometry polygon2 = new Polygon( //
			new Point3D(-10, -30, -20), new Point3D(-10,-50, -149), new Point3D(10, -50, -149),new Point3D(10, -30, -20))
			.setEmission(new Color(java.awt.Color.GREEN))
			.setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(100));
	private static Geometry sphere = new Sphere(new Point3D(0, 0, -50), 50) //
			.setEmission(new Color(java.awt.Color.BLUE)) //
			.setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(100));

	
	
	/**
	 * Produce a picture of a sphere lighted by a directional light
	 */
	/*
	@Test
	public void HomeDirectional() {
		scene1.geometries.add(triangle1,polygon1,polygon2);
		scene1.lights.add(new DirectionalLight(new Color(500, 300, 0), new Vector(1, 1, -1)));

		ImageWriter imageWriter = new ImageWriter("home", 500, 500);
		Render render = new Render()//
				.setImageWriter(imageWriter) //
				.setScene(scene1) //
				.setCamera(camera1) //
				.setRayTracer(new RayTracerBasic(scene1));
		render.renderImage();
		render.writeToImage();
	}*/

	/**
	 * Produce a picture of a sphere lighted by a spot light
	 */
	@Test
	public void sphereSpot() {
		scene1.geometries.add(sphere);
		scene1.lights.add(new SpotLight(new Color(500, 300, 0), new Point3D(-50, -50, 50), new Vector(1, 1, -2), 1,
				0.00001, 0.00000001));
		scene1.lights.add(new SpotLight(new Color(0, 500, 100), new Point3D(50, 50, 50), new Vector(-1, -1, -2), 1,
				0.00001, 0.00000001));
		scene1.lights.add(new SpotLight(new Color(150, 500, 0), new Point3D(-50, 50, 50), new Vector(-1, -1, -2), 1,
				0.00001, 0.00000001));
		

		ImageWriter imageWriter = new ImageWriter("sphere2Spots2", 500, 500);
		Render render = new Render()//
				.setImageWriter(imageWriter) //
				.setScene(scene1) //
				.setCamera(camera1) //
				.setRayTracer(new RayTracerBasic(scene1));
		render.renderImage();
		render.writeToImage();
	}

}
