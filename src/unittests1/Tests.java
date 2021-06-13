/**
 * 
 */
package unittests1;

import static org.junit.Assert.*;

import org.junit.Test;

import elements.*;
import geometries.*;
import primitives.*;
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
		
		ImageWriter imageWriter = new ImageWriter("power_s300B", 1000, 1000);
		Render render = new Render()//
				.setImageWriter(imageWriter) //
				.setCamera(camera) //
				.setDebugPrint()//
				.setMultithreading(3)//
				.setRayTracer(new RayTracerBasic(scene,300));
		render.renderImage();
		render.writeToImage();
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
			.setViewPlaneSize(300, 300);
			scene.setBackground(new Color(java.awt.Color.red));
			scene.setAmbientLight(Color.BLACK, 0);
			
			// front wall
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
					.setMaterial(new Material().setkD(0).setkS(1).setnShininess(100)));//,
					/*new Tube(new Ray(new Point3D(50, 0, 50), new Vector(0, 1, 0)),10)
					.setEmission(new Color(java.awt.Color.blue))
					.setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(100)));*/
					
			scene.geometries.add(new Sphere(new Point3D(50,140,45),15)
					.setEmission(new Color(java.awt.Color.BLUE))
					.setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(100)),
					new Sphere(new Point3D(50,97,45),15)
					.setEmission(new Color(java.awt.Color.BLUE))
					.setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(100)),
					new Sphere(new Point3D(50,55,45),15)
					.setEmission(new Color(java.awt.Color.BLUE))
					.setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(100)),
					new Sphere(new Point3D(50,13,45),15)
					.setEmission(new Color(java.awt.Color.BLUE))
					.setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(100)),
					new Sphere(new Point3D(50,-30,45),15)
					.setEmission(new Color(java.awt.Color.BLUE))
					.setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(100)));
					 
			scene.geometries.add(
					new Polygon(new Point3D(0, 150, 0),new Point3D(0, 150, 90), new Point3D(0, 120, 90), new Point3D(0, 120, 0))
						.setEmission(new Color(10, 10, 10))
						.setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(100).setkT(0.9).setkR(0).setkB(0.05).setkG(0)),
					new Polygon( new Point3D(0, 110, 0),new Point3D(0, 110, 90), new Point3D(0, 80, 90), new Point3D(0, 80, 0))
						.setEmission(new Color(10, 10, 10))
						.setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(100).setkT(0.9).setkR(0).setkB(0.25).setkG(0)),
					new Polygon(new Point3D(0, 70, 0),new Point3D(0, 70, 90), new Point3D(0, 40, 90), new Point3D(0, 40, 0))
						.setEmission(new Color(10, 10, 10))
						.setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(100).setkT(0.9).setkR(0).setkB(0.45).setkG(0)),
					new Polygon(new Point3D(0, 30, 0),new Point3D(0, 30, 90), new Point3D(0, 0, 90), new Point3D(0, 0, 0))
						.setEmission(new Color(10, 10, 10))
						.setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(100).setkT(0.9).setkR(0).setkB(0.65).setkG(0)),
					new Polygon(new Point3D(0, -40, 0),new Point3D(0, -40, 90), new Point3D(0, -10, 90), new Point3D(0, -10, 0))
						.setEmission(new Color(10, 10, 10))
						.setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(100).setkT(0.9).setkR(0).setkB(1).setkG(0)));
			
			//scene.lights.add(new SpotLight(new Color(500, 500, 500), new Point3D(-400, 55, 100), new Vector(-0.1, 0, -1))
				//	.setkL(0.0000001).setkQ(0.000000001).setBeam(4));
			scene.lights.add(new SpotLight(new Color(java.awt.Color.yellow), new Point3D(-100, 55, 150), new Vector(1, 0, 0))
					.setkL(0.0000001).setkQ(0.0000001));
			//scene.lights.add(new SpotLight(new Color(500, 500, 500), new Point3D(-600, 55, 100), new Vector(-0.1, 0, -1))
				//	.setkL(0.0000001).setkQ(0.000000001).setBeam(4));
						
			ImageWriter imageWriter = new ImageWriter("MiniProject1_S300", 1000, 1000);
			Render render = new Render()//
					.setImageWriter(imageWriter) //
					.setCamera(camera) //
					.setDebugPrint()//
					.setMultithreading(3)//
					.setRayTracer(new RayTracerBasic(scene,300));
			render.renderImage();
			render.writeToImage();
		}

		@Test
		public void showRoom() {
			Scene scene = new Scene("Test show room");
			Camera camera=new Camera(new Point3D(0, 90, -1000), new Vector(0, -0.09, 1), new Vector(0, 1, 0.09))
			.setDistance(1000)
			.setViewPlaneSize(300, 300);
			scene.setBackground(new Color(java.awt.Color.red));
			scene.setAmbientLight(new Color(java.awt.Color.BLACK),0);
			
			scene.geometries.add(new Plane(new Point3D(0, 0, 900), new Vector(0, 0, 1))
					.setEmission(new Color(10,10,10))
					.setMaterial(new Material().setkD(0.5).setkS(0.1).setnShininess(10)),
					//floor
					new Polygon(new Point3D(-100, -75, -700), new Point3D(100, -75, -700), new Point3D(200, -75, 900),
							new Point3D(-200, -75, 900)).setEmission(new Color(40, 40, 40))
					.setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(100).setkT(0).setkR(0.5).setkB(0).setkG(0)),
					//5 R wall up
					new Polygon(new Point3D(-150, 25, 0), new Point3D(-76, 25, 0), new Point3D(-76, 75, 0),
							new Point3D(-150, 75, 0)).setEmission(new Color(120, 170, 60))
					.setMaterial(new Material().setkD(0.1).setkS(0.1).setnShininess(10)),
					//R wall down
					new Polygon(new Point3D(-150, -75, 0), new Point3D(-76, -75, 0), new Point3D(-76, -25, 0),
							new Point3D(-150, -25, 0)).setEmission(new Color(120, 170, 60))
					.setMaterial(new Material().setkD(0.1).setkS(0.1).setnShininess(10)),
					//R window
					new Polygon(new Point3D(-150, 25, 0), new Point3D(-76, 25, 0), new Point3D(-76, -25, 0),
							new Point3D(-150, -25, 0)).setEmission(new Color(10, 10, 10))
							.setMaterial(new Material().setkD(0.1).setkS(0.1).setnShininess(10).setkT(0.9).setkR(0).setkB(0).setkG(0)),
					//door RR
					new Polygon(new Point3D(-36, -75, 0), new Point3D(-76, -75, 0), new Point3D(-76, 75, 0),
							new Point3D(-36, 75, 0)).setEmission(new Color(10, 10, 10))
					.setMaterial(new Material().setkD(0.1).setkS(0.1).setnShininess(10).setkT(0.9).setkR(0).setkB(0).setkG(0)),
					//4 door RL
					new Polygon(new Point3D(5, -75, 0), new Point3D(-35, -75, 0), new Point3D(-35, 75, 0),
							new Point3D(5, 75, 0)).setEmission(new Color(10, 10, 10))
					.setMaterial(new Material().setkD(0.1).setkS(0.1).setnShininess(10).setkT(0.9).setkR(0).setkB(0).setkG(0)),
					//3 M wall
					new Polygon(new Point3D(24, -75, 0), new Point3D(5, -75, 0), new Point3D(5, 75, 0),
							new Point3D(24, 75, 0)).setEmission(new Color(120, 170, 60))
					.setMaterial(new Material().setkD(0.1).setkS(0.1).setnShininess(10)),
					//2 door LR
					new Polygon(new Point3D(64, -75, 0), new Point3D(24, -75, 0), new Point3D(24, 75, 0),
							new Point3D(64, 75, 0)).setEmission(new Color(10, 10, 10))
					.setMaterial(new Material().setkD(0.1).setkS(0.1).setnShininess(10).setkT(0.9).setkR(0).setkB(0.7).setkG(0)),
					//2 door LL
					new Polygon(new Point3D(105, -75, 0), new Point3D(65, -75, 0), new Point3D(65, 75, 0),
							new Point3D(105, 75, 0)).setEmission(new Color(10, 10, 10))
					.setMaterial(new Material().setkD(0.1).setkS(0.1).setnShininess(10).setkT(0.9).setkR(0).setkB(0.2).setkG(0)),
					//handle LL
					new Polygon(new Point3D(70, -5, -2), new Point3D(66, -5, -2), new Point3D(66, 5, -2),
							new Point3D(70, 5, -2)).setEmission(new Color(120, 170, 60))
					.setMaterial(new Material().setkD(0.1).setkS(0.1).setnShininess(10)),
					//1 L wall
					new Polygon(new Point3D(250, -75, 0), new Point3D(105, -75, 0), new Point3D(105, 75, 0),
							new Point3D(250, 75, 0)).setEmission(new Color(120, 170, 60))
					.setMaterial(new Material().setkD(0.1).setkS(0.1).setnShininess(10)),
					/////high wall
					new Polygon(new Point3D(250, 75, 0), new Point3D(-150, 75, 0), new Point3D(-150, 175, 0),
							new Point3D(250, 175, 0)).setEmission(new Color(120, 170, 60))
					.setMaterial(new Material().setkD(0.1).setkS(0.5).setnShininess(100)),
					//logo
					new Polygon(new Point3D(40, 85, -1), new Point3D(33, 105, -1), new Point3D(31, 102, -1),
							new Point3D(38, 88, -1)).setEmission(new Color(java.awt.Color.red))
					.setMaterial(new Material().setkD(0.1).setkS(0.1).setnShininess(10)),
					//new Plane(new Point3D(0, -250, 0), new Vector(0, 0, 1))
					//.setEmission(new Color(java.awt.Color.BLACK))
					//.setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(100)),
					/*new Polygon(new Point3D(-150,-210, 200),new Point3D(150, -210,200 ),
					new Point3D(150, -100, 300), new Point3D(-150, -100, 300))
			.setEmission(new Color(250, 250, 250))
			.setMaterial(new Material().setkD(0.2).setkS(1).setnShininess(100).setkT(0).setkR(0.8).setkB(0).setkG(0.5)),
		new Polygon( new Point3D(-150, -100, 300),new Point3D(-50, -100, 300), new Point3D(-50, 150, 300), new Point3D(-150, 150, 300))
			.setEmission(new Color(0,0,10))
			.setMaterial(new Material().setkD(0.9).setkS(0.5).setnShininess(100).setkT(0).setkR(0).setkB(0).setkG(0)),
		new Polygon(new Point3D(-50, 100, 300),new Point3D(50, 100, 300), new Point3D(50, 150, 300), new Point3D(-50, 150, 300))
			.setEmission(new Color(java.awt.Color.YELLOW))
			.setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(100).setkT(0.9).setkR(0).setkB(0.1).setkG(0)),
			new Polygon(new Point3D(-50, -100, 300),new Point3D(50, -100, 300), new Point3D(50, 100, 300), new Point3D(-50, 100, 300))
			.setEmission(new Color(230,230,230))
			.setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(100).setkT(0.9).setkR(0).setkB(0.1).setkG(0)),
		new Polygon(new Point3D(50, -100, 300),new Point3D(150, -100, 300), new Point3D(150, 150, 300), new Point3D(50, 150, 300))
		.setEmission(new Color(java.awt.Color.GRAY))
		.setMaterial(new Material().setkD(0.9).setkS(0.5).setnShininess(100).setkT(0).setkR(0).setkB(0).setkG(0)),
		new Polygon(new Point3D(-20, -40, 500),new Point3D(20, -40, 500), new Point3D(20, -10, 500), new Point3D(-20, -10, 500))
			.setEmission(new Color(java.awt.Color.GREEN))
			.setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(100).setkT(0.3).setkR(0.5).setkB(1).setkG(0))*/
					//R ball
					new Sphere(new Point3D(-45, 0, 50), 20)
					.setEmission(new Color(java.awt.Color.green))
					.setMaterial(new Material().setkD(0.1).setkS(0.1).setnShininess(100).setkT(0.8)),
					//L ball
					new Sphere(new Point3D(65, 0, 50), 20)
					.setEmission(new Color(java.awt.Color.green))
					.setMaterial(new Material().setkD(0.1).setkS(0.1).setnShininess(100).setkT(0.8)));

			//scene.lights.add(new SpotLight(new Color(java.awt.Color.WHITE), new Point3D(0, 0, -450), new Vector(0, 0, 1))
				//	.setkL(0.0000001).setkQ(0.0000001));
			//scene.lights.add(new DirectionalLight(new Color(255, 255, 255), new Vector(-0.01, 0, 1)));
			//scene.lights.add(new PointLight(new Color(255, 255, 255),new Point3D(0, 0,-100))
			//		.setkC(1).setkL(0.0001).setkQ(0.000001));
			scene.lights.add(new SpotLight(new Color(500, 500, 500), new Point3D(0, 155, 100), new Vector(-0.1, -1, -1))
				.setkL(0.0000001).setkQ(0.000000001).setBeam(4));
			scene.lights.add(new SpotLight(new Color(500, 500, 500), new Point3D(0, 155, 0), new Vector(-0.1, -1, 1))
					.setkL(0.0000001).setkQ(0.000000001));
			//scene.lights.add(new SpotLight(new Color(500, 500, 500), new Point3D(36, 90, -10), new Vector(0, 0, 1))
			//.setkL(0.0000001).setkQ(0.000000001).setBeam(4));
			
			ImageWriter imageWriter = new ImageWriter("Show Room", 1000, 1000);
			Render render = new Render()//
					.setImageWriter(imageWriter) //
					.setCamera(camera) //
					.setDebugPrint()//
					.setMultithreading(3)//
					.setRayTracer(new RayTracerBasic(scene).setBox(4));
			render.renderImage();
			render.writeToImage();
		}
		
		 @Test
			public void glossyTest() {
				Scene scene = new Scene("Test scene");
				Camera camera=new Camera(new Point3D(-1000, 0, 90), new Vector(1, 0, -0.09), new Vector(0.09, 0, 1))
				.setViewPlaneSize(150,150)
				.setDistance(1000);
				scene.setBackground(new Color(3, 3, 3));
				scene.setBackground(new Color(java.awt.Color.red));
				scene.setAmbientLight(Color.BLACK, 0);

				scene.geometries.add(new Plane(new Point3D(300, 0, 0), new Vector(1, 0, 0))
						.setEmission(new Color(java.awt.Color.BLACK))
						.setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(100)),
						new Sphere(new Point3D(50, 0, 20), 20)
						.setEmission(new Color(java.awt.Color.green))
						.setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(100)),
						 new Sphere(new Point3D(50, 47, 20), 20)
						.setEmission(new Color(java.awt.Color.green))
						.setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(100)),
						new Polygon(new Point3D(0, -20, 50), new Point3D(0, 20, 50), new Point3D(0, 20, -20),
								new Point3D(0, -20, -20)).setEmission(new Color(10, 10, 10))
						.setMaterial(new Material().setkD(0.1).setkS(0.1).setnShininess(10).setkT(0.9).setkR(0).setkB(0).setkG(0.1)),
						new Polygon(new Point3D(0, 25, 50), new Point3D(0, 65, 50), new Point3D(0, 65, -20),
								new Point3D(0, 25, -20)).setEmission(new Color(10, 10, 10))
						.setMaterial(new Material().setkD(0.1).setkS(0.1).setnShininess(10).setkT(0.9).setkR(0).setkB(0.5).setkG(0)),
						new Polygon(new Point3D(-700, -75, 0), new Point3D(900, -75, 0), new Point3D(900, 75, 0),
								new Point3D(-700, 75, 0)).setEmission(new Color(10, 10, 10))
						.setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(100).setkT(0).setkR(0.5).setkB(0).setkG(0.1)));
										
				scene.lights.add(new DirectionalLight(new Color(255, 255, 255), new Vector(1, 0.1, 0)));
				
				ImageWriter imageWriter = new ImageWriter("MP2", 1000, 1000);
				Render render = new Render()//
						.setImageWriter(imageWriter) //
						.setCamera(camera) //
						.setDebugPrint()//
						.setMultithreading(3)//
						.setRayTracer(new RayTracerBasic(scene));
				render.renderImage();
				render.writeToImage();
			}
}
