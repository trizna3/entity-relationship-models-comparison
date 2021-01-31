package sk.trizna.erm_comparison.common;

public class MathUtils {

	/**
	 * Computes vector linalg norm
	 * @param vec
	 * @return
	 */
	public static double norm(double[] vec) {
		Utils.validateNotNull(vec);
		
		double norm = 0;
		
		for(int i=0; i<vec.length; i++) {
			norm += Math.pow(vec[i],2);
		}
		
		return Math.pow(norm, 0.5);
	}
	
	/**
	 * Computes vector dot product (scalar)
	 * @param vec1
	 * @param vec2
	 * @return
	 */
	public static double dot(double[] vec1, double[] vec2) {
		Utils.validateNotNull(vec1);
		Utils.validateNotNull(vec2);
		if (vec1.length != vec2.length) {
			throw new IllegalArgumentException("Vectors of different length!");
		}
		
		double dot = 0;
		for (int i=0; i<vec1.length; i++) {
			dot += vec1[i] * vec2[i];
		}
		
		return dot;
	}
}
