/**
 * 
 */
package primitives;

/**
 * class for the material details
 * 
 * @author
 *
 */
public class Material {
	/**
	 * the diffuse parameter
	 */
	public double kD = 0;
	/**
	 *  the specular parameter
	 */
	public double kS = 0;
	/**
	 *  the shininess parameter
	 */
	public int nShininess = 0;
	/**
	 * the transparency parameter
	 */
	public double kT=0.0;
	/**
	 * the reflection parameter
	 */
	public double kR=0.0;
	
	/**
	 * setter
	 * 
	 * @param kD the kD to set
	 */
	public Material setkD(double kD) {
		this.kD = kD;
		return this;
	}

	/**
	 * setter
	 * 
	 * @param kS the kS to set
	 */
	public Material setkS(double kS) {
		this.kS = kS;
		return this;
	}

	/**
	 * setter
	 * 
	 * @param nSininess the nSininess to set
	 */
	public Material setnShininess(int nSininess) {
		this.nShininess = nSininess;
		return this;
	}

	/**
	 * @param kT the kT to set
	 */
	public Material setkT(double kT) {
		this.kT = kT;
		return this;
	}

	/**
	 * @param kR the kR to set
	 */
	public Material setkR(double kR) {
		this.kR = kR;
		return this;
	}
	
	
	
}
