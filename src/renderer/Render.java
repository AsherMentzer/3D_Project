package renderer;

import scene.Scene;
import renderer.*;

import java.util.MissingResourceException;

import elements.*;
import primitives.Color;
import primitives.Ray;

/**
 * class to create from the scene the colors matrix
 * 
 * @author
 *
 */
public class Render {

	private Camera camera;
	private ImageWriter imageWriter;
	private RayTracerBase rayTracer;

	/**
	 * setter
	 * 
	 * @param camera
	 * @return this Render class
	 */
	public Render setCamera(Camera camera) {
		this.camera = camera;
		return this;
	}

	/**
	 * setter
	 * 
	 * @param imageWriter
	 * @return this Render class
	 */
	public Render setImageWriter(ImageWriter imageWriter) {
		this.imageWriter = imageWriter;
		return this;
	}

	/**
	 * setter
	 * 
	 * @param rayTracer
	 * @return this Render class
	 */
	public Render setRayTracer(RayTracerBase rayTracer) {
		this.rayTracer = rayTracer;
		return this;
	}

	/**
	 * print all the scene by construct ray from camera to every pixel and find the
	 * color by ray tracer this ray
	 */
	public void renderImage() {
		if (camera == null || imageWriter == null || rayTracer == null)
			throw new MissingResourceException(null, null, null);

		int nY = imageWriter.getNy();
		int nX = imageWriter.getNx();
		for (int i = 0; i < nY; ++i) {
			for (int j = 0; j < nX; ++j) {
				Ray ray = camera.constructRayThroughPixel(nX, nY, j, i);
				Color c = rayTracer.traceRay(ray);
				imageWriter.writePixel(j, i, c);
			}
		}
	}

	/**
	 * print net on the scene by dividing the scene width and the scene height by
	 * interval
	 * 
	 * @param interval
	 * @param color    the color of the grid
	 */
	public void printGrid(int interval, Color color) {
		if (imageWriter == null)
			throw new MissingResourceException(null, null, null);

		int nY = imageWriter.getNy();
		int nX = imageWriter.getNx();
		for (int i = 0; i < nY; ++i) {
			for (int j = 0; j < nX; ++j) {
				if (i % 50 == 0 || j % 50 == 0)
					imageWriter.writePixel(j, i, color);
			}
		}
	}

	/**
	 * use the write to image of ImageWriter
	 */
	public void writeToImage() {
		if (imageWriter == null)
			throw new MissingResourceException(null, null, null);
		imageWriter.writeToImage();
	}
}
