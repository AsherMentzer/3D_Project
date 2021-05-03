/**
 * 
 */
package primitives;

/**
 * @author 
 *
 */
public class Material {
	
	public double kD=0;
	public double kS=0;
	public int nShininess=0;
	
	/**
	 * @param kD the kD to set
	 */
	public Material setkD(double kD) {
		this.kD = kD;
		return this;
	}
	/**
	 * @param kS the kS to set
	 */
	public Material setkS(double kS) {
		this.kS = kS;
		return this;
	}
	/**
	 * @param nSininess the nSininess to set
	 */
	public Material setnShininess(int nSininess) {
		this.nShininess = nSininess;
		return this;
	}
	
	

}
