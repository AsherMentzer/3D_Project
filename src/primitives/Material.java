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
	 * the Blurry glass parameter
	 */
	public double kB=0.0;
	
	/**
	 * the Glossy glass parameter
	 */
	public double kG=0.0;
	
	/**
	 * setter
	 * 
	 * @param kD the diffuse parameter to set
	 */
	public Material setkD(double kD) {
		this.kD = kD;
		return this;
	}

	/**
	 * setter
	 * 
	 * @param kS the specular parameter to set
	 */
	public Material setkS(double kS) {
		this.kS = kS;
		return this;
	}

	/**
	 * setter
	 * 
	 * @param nSininess the shininess parameter to set
	 */
	public Material setnShininess(int nSininess) {
		this.nShininess = nSininess;
		return this;
	}

	/**
	 * @param kT the transparency parameter to set
	 */
	public Material setkT(double kT) {
		this.kT = kT;
		return this;
	}

	/**
	 * @param kR the reflection parameter to set
	 */
	public Material setkR(double kR) {
		this.kR = kR;
		return this;
	}
	
	/**
	 * setter
	 * @param kB the Blurry glass parameter
	 * @return the material
	 */
	public Material setkB(double kB) {
		this.kB=kB;
		return this;
	}
	
	/**
	 * setter
	 * @param kG the Glossy glass parameter
	 * @return the material
	 */
	public Material setkG(double kG) {
		this.kG=kG;
		return this;
	}
	
}
