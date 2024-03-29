package sk.trizna.erm_comparison.common.enums;

/**
 * Similarity constants.
 * Class fields are package-private on purpose and should be accessed by {@link SimilarityConstantsUtils} only.
 * 
 * @author Adam Trizna
 *
 */
public class SimilarityConstants {
	
	/**
	 * weight of name similarity in Named ER elements comparison
	 */
	static final int NAME_WEIGHT = 2;
	/**
	 * weight of attributes similarity in Attributed ER elements comparison
	 */
	static final double ATTRIBUTE_WEIGHT = 0.5;
	/**
	 * weight of incident entity sets similarity in relationships comparison
	 */
	static final int ENTITY_SET_WEIGHT = 1;
	/**
	 * Minimal similarity for two entity sets to be 'similar' on <0,1>
	 */
	static final double SIMILARITY_TRESHOLD_ENTITY_SET = 0.25;
	/**
	 * Minimal similarity for two entity sets to be 'very similar' (to be considered duplicate) on <0,1>
	 */
	static final double SIMILARITY_TRESHOLD_STRONG_ENTITY_SET = 0.6;
	/**
	 * Minimal similarity for an entity set and an association to be 'similar' on <0,1>
	 */
	static final double SIMILARITY_TRESHOLD_ENTITY_SET_ASSOCIATION = 0.25;
	/**
	 * Minimal similarity for two associations to be 'similar' on <0,1>
	 */
	static final double SIMILARITY_TRESHOLD_ASSOCIATION = 0.25;
	/**
	 * Word pairs of greater(or equal) similarity value are considered equal by meaning.
	 */
	static final double LANGUAGE_PROCESSOR_EQUALITY_TRESHOLD = 0.8;
}
