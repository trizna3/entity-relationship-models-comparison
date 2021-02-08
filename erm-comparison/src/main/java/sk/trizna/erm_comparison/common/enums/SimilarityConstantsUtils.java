package sk.trizna.erm_comparison.common.enums;

import sk.trizna.erm_comparison.common.Utils;
import sk.trizna.erm_comparison.entity_relationship_model.Attributed;

public class SimilarityConstantsUtils extends Utils {

	/**
	 * @see SimilarityConstants#NAME_WEIGHT
	 * @return
	 */
	public static final int getNameWeight() {
		return SimilarityConstants.NAME_WEIGHT;
	}
	
	/**
	 * @see SimilarityConstants#ATTRIBUTE_WEIGHT
	 * @return
	 */
	public static final double getAttributeWeight() {
		return SimilarityConstants.ATTRIBUTE_WEIGHT;
	}
	
	/**
	 * Computes attribute weight, based on attributes count
	 * Algorithm:
	 * 		Returns min of (
	 * 			{@link #getAttributeWeight()} * k, where k = (count of all attributes) / 2,
	 * 			{@link #getNameWeight()}}
	 */
	public static final double getAttributeWeight(Attributed attributed1, Attributed attributed2) {
		validateNotNull(attributed1);
		validateNotNull(attributed2);
		
		return Math.min(
					((attributed1.getAttributesCount() + attributed2.getAttributesCount())/2.0)*getAttributeWeight(),
					getNameWeight()
					);
	}
	
	/**
	 * Computes attribute weight, based on attributes count
	 * Algorithm:
	 * 		Returns min of (
	 * 			{@link #getAttributeWeight()} * k, where k = (count of all attributes) / 2,
	 * 			{@link #getNameWeight()}}
	 */
	public static final double getAttributeWeight(Attributed attributed1) {
		validateNotNull(attributed1);
		
		return Math.min(
					((attributed1.getAttributesCount()))*getAttributeWeight(),
					getNameWeight()
					);
	}
	
	/**
	 * @see SimilarityConstants#ENTITY_SET_WEIGHT
	 * @return
	 */
	public static final int getEntitySetWeight() {
		return SimilarityConstants.ENTITY_SET_WEIGHT;
	}
	
	/**
	 * @see SimilarityConstants#SIMILARITY_TRESHOLD_ENTITY_SET
	 * @return
	 */
	public static final double getEntitySetSimilarityTreshold() {
		return SimilarityConstants.SIMILARITY_TRESHOLD_ENTITY_SET;
	}
	
	/**
	 * @see SimilarityConstants#SIMILARITY_TRESHOLD_ENTITY_SET_ASSOCIATION
	 * @return
	 */
	public static final double getEntitySetAssociationSimilarityTreshold() {
		return SimilarityConstants.SIMILARITY_TRESHOLD_ENTITY_SET_ASSOCIATION;
	}
	
	/**
	 * @see SimilarityConstants#SIMILARITY_TRESHOLD_ASSOCIATION
	 * @return
	 */
	public static final double getAssociationTreshold() {
		return SimilarityConstants.SIMILARITY_TRESHOLD_ASSOCIATION;
	}
	
	public static final double getLanguageProcessorEqualityTreshold() {
		return SimilarityConstants.LANGUAGE_PROCESSOR_EQUALITY_TRESHOLD;
	}
}
