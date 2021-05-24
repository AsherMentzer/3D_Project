/**
 * 
 */
package unittests1;

import static org.junit.Assert.*;

import org.junit.Test;

import elements.Camera;
import elements.DirectionalLight;
import elements.SpotLight;
import geometries.Geometry;
import geometries.Plane;
import geometries.Polygon;
import geometries.Sphere;
import geometries.Triangle;
import geometries.Tube;
import primitives.Color;
import primitives.Material;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;
import renderer.ImageWriter;
import renderer.RayTracerBasic;
import renderer.Render;
import scene.Scene;

/**
 * @author ashme
 *
 */
public class Tests {
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
			.setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(100).setkT(0.3));
	private static Geometry polygon1 = new Polygon( //
			new Point3D(-50, 0, -150), new Point3D(50, 0, -150), new Point3D(50, -50, -150),new Point3D(-50, -50, -150))
			.setEmission(new Color(java.awt.Color.RED))
			.setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(100).setkR(0.6));
	private static Geometry polygon2 = new Polygon( //
			new Point3D(-10, -30, -150), new Point3D(10,-30, -150), new Point3D(10, -50, -150),new Point3D(-10, -50, -150))
			.setEmission(new Color(java.awt.Color.GREEN))
			.setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(100).setkT(0.5));
	private static Geometry sphere = new Sphere(new Point3D(0, 0, -50), 20) //
			.setEmission(new Color(java.awt.Color.BLUE)) //
			.setMaterial(new Material().setkD(0.2).setkS(0.2).setnShininess(30).setkT(0.7));

	
	
	/**
	 * Produce a picture of a sphere lighted by a directional light
	 */
	@Test
	public void HomeDirectional() {
		scene1.geometries.add(triangle1,polygon1,polygon2,sphere);
		scene1.lights.add(new SpotLight(new Color(700, 400, 400), new Point3D(0, 0, 0), new Vector(0, 0, -1)) //
				.setkL(4E-5).setkQ(2E-7));
		scene1.setAmbientLight(new Color(java.awt.Color.GREEN), 2);


		ImageWriter imageWriter = new ImageWriter("house", 500, 500);
		Render render = new Render()//
				.setImageWriter(imageWriter) //
				.setCamera(camera1) //
				.setRayTracer(new RayTracerBasic(scene1));
		render.renderImage();
		render.writeToImage();
	}
	@Test
	public void BlurryGlass() {
		scene1.geometries.add(
				new Polygon(new Point3D(-100,-100,-100),new Point3D(100,-100,-100),new Point3D(100,100,-100),new Point3D(-100,100,-100))
				.setEmission(new Color(0,0,75))
				.setMaterial(new Material().setkD(0.2).setkS(0.2).setnShininess(25).setkT(0.9)),
				new Sphere(new Point3D(0, -30, -150), 20) //
				.setEmission(new Color(java.awt.Color.GREEN)) //
				.setMaterial(new Material().setnShininess(25).setkT(0))
				);
		scene1.setAmbientLight(new Color(java.awt.Color.WHITE), 0.15);
		scene1.lights.add(new SpotLight(new Color(700, 400, 400), new Point3D(60, 30, 0), new Vector(0, 0, -1))
				.setkL(4E-5).setkQ(2E-7));
				//new DirectionalLight(new Color(java.awt.Color.YELLOW),new Vector(0,0,-1)));
		
		ImageWriter imageWriter = new ImageWriter("BlurryGlass", 500, 500);
		Render render = new Render()//
				.setImageWriter(imageWriter) //
				.setCamera(camera1) //
				.setRayTracer(new RayTracerBasic(scene1));
		render.renderImage();
		render.writeToImage();
	}
	
	/**
	 * Produce a picture of a sphere that reflected on glossy surface
	 */
	 @Test
	public void glossySurfaceTest() {
		Scene scene = new Scene("Test scene");
		Camera camera=new Camera(new Point3D(-1000, 0, 90), new Vector(1, 0, -0.09), new Vector(0.09, 0, 1))
		.setViewPlaneSize(150,150)
		.setDistance(1000);
		scene.setBackground(new Color(3, 3, 3));
		scene.setBackground(new Color(java.awt.Color.red));
		scene.setAmbientLight(Color.BLACK, 0);

		scene.geometries.add(new Plane(new Point3D(300, 0, 0), new Vector(1, 0, 0))
				.setEmission(new Color(java.awt.Color.BLACK))
				.setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(100))
				,new Sphere(new Point3D(50, 0, 20), 20)
				.setEmission(new Color(java.awt.Color.green))
				.setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(100)),
				new Polygon(new Point3D(-700, -75, 0), new Point3D(900, -75, 0), new Point3D(900, 75, 0),
						new Point3D(-700, 75, 0)).setEmission(new Color(10, 10, 10))
				.setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(100).setkT(0).setkR(0.5).setkB(0).setkG(0.1)));
								
		scene.lights.add(new DirectionalLight(new Color(255, 255, 255), new Vector(1, 0.1, 0)));
		
		ImageWriter imageWriter = new ImageWriter("power_s300", 1000, 1000);
		Render render = new Render()//
				.setImageWriter(imageWriter) //
				.setCamera(camera) //
				.setRayTracer(new RayTracerBasic(scene,300));
		render.renderImage();
		render.writeToImage();
		
		/*ImageWriter imageWriter = new ImageWriter("power_s", 1000, 1000);
		Render render = new Render(imageWriter, scene, 200).setDebugPrint().setMultithreading(3);
		render.renderImage();
		render.writeToImage();*/
	}

	 /**
		 * Produce a picture of a few glasses with diffrent level of matte lighted by
		 * spot light
		 */
		@Test
		public void diffusedGlass() {
			Scene scene = new Scene("Test scene");
			Camera camera=new Camera(new Point3D(-900, 50, 20), new Vector(1, 0, 0.02), new Vector(-0.02, 0, 1))
			.setDistance(1000)
			.setViewPlaneSize(250, 250);
			scene.setBackground(new Color(java.awt.Color.red));
			scene.setAmbientLight(Color.BLACK, 0);
			// fron wall
			scene.geometries.add(new Plane(new Point3D(60, 0, 0), new Vector(1, 0, 0))
					.setEmission(new Color(java.awt.Color.gray))
					.setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(100)));
			// floor
			scene.geometries.add(new Plane(new Point3D(0, 0, 0), new Vector(0, 0, 1))
					.setEmission(new Color(java.awt.Color.BLACK))
					.setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(100)));
			// ceiling
			scene.geometries.add(new Plane(new Point3D(0, 0, 400), new Vector(0, 0, 1))
					.setEmission(new Color(java.awt.Color.WHITE))
					.setMaterial(new Material().setkD(0).setkS(1).setnShininess(100)),
					/*new Tube(new Ray(new Point3D(50, 0, 50), new Vector(0, 1, 0)),10)
					.setEmission(new Color(java.awt.Color.blue))
					.setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(100)));
					*/	
					new Sphere(new Point3D(50,15,50),15)
					.setEmission(new Color(java.awt.Color.blue))
					.setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(100)),
					new Sphere(new Point3D(50,55,50),15)
					.setEmission(new Color(java.awt.Color.blue))
					.setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(100)),
					new Sphere(new Point3D(50,95,50),15)
					.setEmission(new Color(java.awt.Color.blue))
					.setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(100)),
					new Sphere(new Point3D(50,135,50),15)
					.setEmission(new Color(java.awt.Color.blue))
					.setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(100)));
					 
			scene.geometries.add(
					new Polygon(new Point3D(0, 150, 0),new Point3D(0, 150, 90), new Point3D(0, 120, 90), new Point3D(0, 120, 0))
						.setEmission(new Color(10, 10, 10))
						.setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(100).setkT(0.8).setkR(0).setkB(0.05).setkG(0)),
					new Polygon( new Point3D(0, 110, 0),new Point3D(0, 110, 90), new Point3D(0, 80, 90), new Point3D(0, 80, 0))
						.setEmission(new Color(10, 10, 10))
						.setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(100).setkT(0.8).setkR(0).setkB(0.15).setkG(0)),
					new Polygon(new Point3D(0, 70, 0),new Point3D(0, 70, 90), new Point3D(0, 40, 90), new Point3D(0, 40, 0))
						.setEmission(new Color(10, 10, 10))
						.setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(100).setkT(0.8).setkR(0).setkB(0.25).setkG(0)),
					new Polygon(new Point3D(0, 30, 0),new Point3D(0, 30, 90), new Point3D(0, 0, 90), new Point3D(0, 0, 0))
						.setEmission(new Color(10, 10, 10))
						.setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(100).setkT(0.8).setkR(0).setkB(0.35).setkG(0)),
					new Polygon(new Point3D(0, 30, 0),new Point3D(0, 30, 90), new Point3D(0, 0, 90), new Point3D(0, 0, 0))
						.setEmission(new Color(10, 10, 10))
						.setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(100).setkT(0.8).setkR(0).setkB(1).setkG(0)));

			
			scene.lights.add(new SpotLight(new Color(500, 500, 500), new Point3D(-400, 55, 100), new Vector(-0.1, 0, -1))
					.setkL(0.0000001).setkQ(0.000000001).setBeam(4));
			scene.lights.add(new SpotLight(new Color(java.awt.Color.yellow), new Point3D(-100, 55, 30), new Vector(1, 0, 0))
					.setkL(0.0000001).setkQ(0.0000001));
			scene.lights.add(new SpotLight(new Color(500, 500, 500), new Point3D(-600, 55, 100), new Vector(-0.1, 0, -1))
					.setkL(0.0000001).setkQ(0.000000001).setBeam(4));
				
			ImageWriter imageWriter = new ImageWriter("MiniProject1_s300", 500, 500);
			Render render = new Render()//
					.setImageWriter(imageWriter) //
					.setCamera(camera) //
					.setRayTracer(new RayTracerBasic(scene,100));
			render.renderImage();
			render.writeToImage();
			/*
			// fron wall
			scene.addGeometries(new Plane(new Color(java.awt.Color.gray), new Material(0.5, 0.5, 100),
					new Point3D(60, 0, 0), new Vector(1, 0, 0)));
			// floor
			scene.addGeometries(
					new Plane(Color.BLACK, new Material(0.5, 0.5, 100), new Point3D(0, 0, 0), new Vector(0, 0, 1)));
			// ceiling
			scene.addGeometries(
					new Plane(new Color(java.awt.Color.WHITE), new Material(0, 1, 100), new Point3D(0, 0, 400),
							new Vector(0, 0, 1)),
					new Tube(new Color(java.awt.Color.blue), new Material(0.5, 0.5, 100), 10,
							new Ray(new Point3D(50, 0, 50), new Vector(0, 1, 0))));
			scene.addGeometries(
					new Polygon(new Color(10, 10, 10), new Material(0.5, 0.5, 100, 0.8, 0, 0.05, 0), new Point3D(0, 150, 0),
							new Point3D(0, 150, 90), new Point3D(0, 120, 90), new Point3D(0, 120, 0)),
					/new Polygon(new Color(10, 10, 10), new Material(0.5, 0.5, 100, 0.8, 0, 0.15, 0), new Point3D(0, 110, 0),
							new Point3D(0, 110, 90), new Point3D(0, 80, 90), new Point3D(0, 80, 0)),
					/new Polygon(new Color(10, 10, 10), new Material(0.5, 0.5, 100, 0.8, 0, 0.25, 0), new Point3D(0, 70, 0),
							new Point3D(0, 70, 90), new Point3D(0, 40, 90), new Point3D(0, 40, 0)),
					new Polygon(new Color(10, 10, 10), new Material(0.5, 0.5, 100, 0.8, 0, 0.35, 0), new Point3D(0, 30, 0),
							new Point3D(0, 30, 90), new Point3D(0, 0, 90), new Point3D(0, 0, 0)),
					new Polygon(new Color(10, 10, 10), new Material(0.5, 0.5, 100, 0.8, 0, 1, 0), new Point3D(0, 30, 0),
							new Point3D(0, 30, 90), new Point3D(0, 0, 90), new Point3D(0, 0, 0)));
			scene.addLights(
					new SpotLight(new Color(500, 500, 500), new Point3D(-400, 55, 100), new Vector(-0.1, 0, -1), 1,
							0.0000001, 0.000000001, 4),
					new SpotLight(new Color(java.awt.Color.yellow), new Point3D(-100, 55, 30), new Vector(1, 0, 0), 1,
							0.0000001, 0.0000001),
					new SpotLight(new Color(500, 500, 500), new Point3D(-600, 55, 100), new Vector(-0.1, 0, -1), 1,
							0.0000001, 0.000000001, 4));
			ImageWriter imageWriter = new ImageWriter("MiniProject1_s", 300, 300, 200, 200);
			Render render = new Render(imageWriter, scene, 100).setDebugPrint().setMultithreading(3);
			render.renderImage();
			render.writeToImage();
			*/
		}

}
