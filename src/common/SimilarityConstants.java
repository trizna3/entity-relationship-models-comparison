package common;

public class SimilarityConstants {
	
	/**
	 * weight of name similarity in Named ER elements comparison
	 */
	public static final int NAME_WEIGHT = 2;
	/**
	 * weight of attributes similarity in Attributed ER elements comparison
	 */
	public static final int ATTRIBUTE_WEIGHT = 2;
	/**
	 * weight of incident entity sets similarity in relationships comparison
	 */
	public static final int ENTITY_SET_WEIGHT = 1;
	/**
	 * Minimal similarity for two entity sets to be 'similar' on <0,1>
	 */
	public static final double SIMILARITY_TRESHOLD_ENTITY_SET = 0.25;
	/**
	 * Minimal similarity for an entity set and an association to be 'similar' on <0,1>
	 */
	public static final double SIMILARITY_TRESHOLD_ENTITY_SET_ASSOCIATION = 0.25;
	/**
	 * Minimal similarity for two associations to be 'similar' on <0,1>
	 */
	public static final double SIMILARITY_TRESHOLD_ASSOCIATION = 0.25;
}
