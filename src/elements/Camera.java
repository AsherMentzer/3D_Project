package elements;

import primitives.*;
import static primitives.Util.*;

/**
 * class represent camera and view plane the camera is the position of the
 * camera and 3 vectors To vector Up vector and Right all orthogonal to each
 * other the view plane is matrix with height and width
 * 
 * @author Dan
 *
 */
public class Camera {

	private Point3D p0;
	private Vector vUp;
	private Vector vTo;
	private Vector vRight;

	// --view plane---//
	// the width of the view plane
	private double width;
	// the height of the view plane
	private double height;
	// the distance between the camera and the view plane
	private double distance;

	// -----getters---------//
	public Point3D getP0() {
		return p0;
	}

	public Vector getvUp() {
		return vUp;
	}

	public Vector getvTo() {
		return vTo;
	}

	public Vector getvRight() {
		return vRight;
	}

	public double getWidth() {
		return width;
	}

	public double getHeight() {
		return height;
	}

	public double getDistance() {
		return distance;
	}

	/**
	 * constructor
	 * 
	 * @param p0  the position of the camera
	 * @param vTo
	 * @param vUp
	 */
	public Camera(Point3D p0, Vector vTo, Vector vUp) {
		// the vectors vUp must be orthogonal to vTo
		if (!(isZero(vUp.dotProduct(vTo))))
			throw new IllegalArgumentException("vUp and Vto must be ortognals");

		this.p0 = p0;
		this.vUp = vUp.normalized();
		this.vTo = vTo.normalized();

		this.vRight = this.vTo.crossProduct(vUp);
	}

	/**
	 * 
	 * @param width
	 * @param height
	 * @return
	 */
	public Camera setViewPlaneSize(double width, double height) {
		this.width = width;
		this.height = height;
		return this;
	}

	/**
	 * 
	 * @param distance
	 * @return the camera
	 */
	public Camera setDistance(double distance) {
		this.distance = distance;
		return this;
	}

	/**
	 * function that get the number of pixels in the view plane and specific pixel
	 * 
	 * @param nX number of columns
	 * @param nY number of rows
	 * @param j  number of column
	 * @param i  number of row
	 * @return ray from the camera to this pixel
	 */
	public Ray constructRayThroughPixel(int nX, int nY, int j, int i) {
		// the center of the view plane
		Point3D pc = p0.add(vTo.scale(distance));

		double rY = height / nY; // Ratio of height to rows
		double rX = width / nX; // Ratio of width and columns
		double ny = nY;
		double nx = nX;
		double yI = -((i - (ny - 1) / 2) * rY);
		double xJ = (j - (nx - 1) / 2) * rX;
		Point3D pIJ = pc;
		if (xJ != 0)
			pIJ = pIJ.add(vRight.scale(xJ));
		if (yI != 0)
			pIJ = pIJ.add(vUp.scale(yI));
		Vector vIJ = pIJ.subtract(p0);
		return new Ray(p0, vIJ);
	}
}
