package unittests1;

import java.util.List;
import java.util.Random;
import org.junit.Test;
import elements.*;
import geometries.*;
import primitives.*;
import renderer.ImageWriter;
import renderer.RayTracerBasic;
import renderer.Render;
import scene.Scene;

public class RegularGrid {
	 @Test
	public void createSpheres() {
		Scene scene = new Scene("Test scene");
		Camera camera=new Camera(new Point3D(-1000, 0, 0), new Vector(1, 0, 0), new Vector(0, 0, 1))
		.setViewPlaneSize(1400,1400)
		.setDistance(500);
		scene.setBackground(Color.BLACK);
		scene.setAmbientLight(new Color(132, 124, 65), 0);
		Random rand = new Random();
		final int NUM = 1000, MOVE = 50;
		Geometries geometries = new Geometries();
		for (int i = 1; i <= NUM; ++i) {
			geometries.add(new Sphere(new Point3D(i * 100 - 200, -MOVE * i, MOVE * i / 2), 50)
				.setEmission(new Color(Math.abs(rand.nextInt() % 255), Math.abs(rand.nextInt() % 255),
							Math.abs(rand.nextInt() % 255)))
				.setMaterial(new Material().setkD(0.4).setkS(0.7).setnShininess(100)));
		}
		for (int i = NUM; i >= 0; --i) {
			geometries.add(new Sphere(new Point3D(i * 100 - 200, MOVE * i, MOVE * i / 2), 50)
					.setEmission(new Color(Math.abs(rand.nextInt() % 255), Math.abs(rand.nextInt() % 255),
							Math.abs(rand.nextInt() % 255)))
					.setMaterial(new Material().setkD(0.4).setkS(0.7).setnShininess(100)));
		}
		for (int i = 1; i <= NUM; ++i) {
			geometries.add(new Sphere(new Point3D(i * 100 - 200, -MOVE * i, -MOVE * i / 2), 50)
					.setEmission(new Color(Math.abs(rand.nextInt() % 255), Math.abs(rand.nextInt() % 255),
							Math.abs(rand.nextInt() % 255)))
					.setMaterial(new Material().setkD(0.4).setkS(0.7).setnShininess(100)));
		}
		for (int i = NUM; i > 1; i--) {
			geometries.add(new Sphere(new Point3D(i * 100, MOVE * i, -MOVE * i / 2), 50)
					.setEmission(new Color(Math.abs(rand.nextInt() % 255), Math.abs(rand.nextInt() % 255),
							Math.abs(rand.nextInt() % 255)))
					.setMaterial(new Material().setkD(0.7).setkS(0.3).setnShininess(45)));
		}

		scene.geometries.add(geometries);
		scene.lights.add(new DirectionalLight(new Color(48, 170, 176), new Vector(0, -1, 0)));
		scene.lights.add(new PointLight(new Color(103, 110, 13), new Point3D(0, -100, 0)).setkC(1).setkL( 0).setkQ(0));
		
		ImageWriter imageWriter = new ImageWriter("withoutBox", 2000, 2000);
		Render render = new Render()//
				.setImageWriter(imageWriter) //
				.setCamera(camera) //
				.setDebugPrint()//
				.setMultithreading(3)//
				.setRayTracer(new RayTracerBasic(scene));
		render.renderImage();
		render.writeToImage();
		
	/*render.writeToImage();
		ImageWriter imageWriter = new ImageWriter("withoutBox", 2000, 2000);
		Render render = new Render(imageWriter, scene).setDebugPrint().setMultithreading(3);// .setBox(4);
		render.renderImage();
		render.writeToImage();*/
	}

	 @Test
	public void testSetMap() {
		Scene scene = new Scene("test2_1");
		Camera camera=new Camera(new Point3D(-1000, 0, 0), new Vector(1, 0, 0), new Vector(0, 0, 1))
		.setViewPlaneSize(30,30)
		.setDistance(1000);
		scene.setBackground(Color.BLACK);
		scene.setAmbientLight(Color.BLACK, 0);
		
		scene.geometries.add(new Sphere(new Point3D(100, 100, 100), 50)
				.setEmission(new Color(100, 100, 100))
				.setMaterial( new Material().setkD(0.5).setkS(0.5).setnShininess(100).setkT(0.5)),
				new Sphere(new Point3D(100, -100, 100), 50)
				.setEmission(new Color(150, 150, 150))
				.setMaterial( new Material().setkD(0.5).setkS(0.5).setnShininess(100).setkT(0.5)),
				new Sphere(new Point3D(100, 100, -100), 50)
				.setEmission(new Color(100, 100, 0))
				.setMaterial( new Material().setkD(0.5).setkS(0.5).setnShininess(100).setkT(0.5)),
				new Sphere(new Point3D(100, -100, -100), 50)
				.setEmission(new Color(100, 0,100))
				.setMaterial( new Material().setkD(0.5).setkS(0.5).setnShininess(100).setkT(0.5)),
				new Sphere(new Point3D(100, 0, 0), 50)
				.setEmission(new Color(100, 0, 100))
				.setMaterial( new Material().setkD(0.5).setkS(0.5).setnShininess(100).setkT(0.5)));
		
		scene.lights.add(new DirectionalLight(new Color(400, 235, 486), new Vector(1, 0, 0)));
		/*ImageWriter imageWriter = new ImageWriter("rr", 300, 300, 500, 500);
		Render render = new Render(imageWriter, scene).setDebugPrint().setMultithreading(3).setBox(4);
		render.renderImage();
		render.writeToImage();*/
		ImageWriter imageWriter = new ImageWriter("rr", 500, 500);
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
	public void test() {
		 Scene scene = new Scene("test2_1");
		Camera camera = new Camera(new Point3D(0, 0, 150), new Vector(0, -1, 0), new Vector(0, 0, -1))
		.setViewPlaneSize(500,500)
		.setDistance(149);
		scene.setBackground(new Color(0, 0, 0));
		scene.setAmbientLight(new Color(130, 130, 130), 0.1);
		
		SpotLight spot = new SpotLight(new Color(100, 100, 100), new Point3D(100, 0, 0),new Vector(-100, 0, -250).normalize())
		 .setkC(1).setkL(2).setkQ(0.01); 
		PointLight pointLight = new PointLight(new Color(77, 0, 0), new Point3D(200, -200, -100))
		.setkC(1).setkL(0.01).setkQ(0.01);
		DirectionalLight directionalLight = new DirectionalLight(new Color(1, 1, 0), new Vector(1, 0, 0));

		for (int i = 0; i < 150; i++) {
			for (int j = 0; j < 150; j++) {
				Sphere sphere = (Sphere) new Sphere(new Point3D(800 - 25 * i, 800 - 25 * j, -300), 10)
						.setEmission(new Color(100 * (i % 2), 100 * (i % 2 + 1), 0))
						.setMaterial(new Material().setkD(500).setkS(20).setnShininess(100).setkT(0).setkR(0));
						
				scene.geometries.add(sphere);
			}
		}
		
		scene.lights.add(spot);
		scene.lights.add(pointLight);
		scene.lights.add(directionalLight);
		
		ImageWriter imageWriter = new ImageWriter("test grid malan sphere", 1000, 1000);
		Render render = new Render()//
				.setImageWriter(imageWriter) //
				.setCamera(camera) //
				.setDebugPrint()//
				.setMultithreading(3)//
				.setRayTracer(new RayTracerBasic(scene).setBox(4));
		render.renderImage();
		render.writeToImage();
	}
}