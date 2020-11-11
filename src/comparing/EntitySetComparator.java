package comparing;

import java.util.Comparator;

import common.Utils;
import entityRelationshipModel.Attribute;
import entityRelationshipModel.EntitySet;
import languageProcessing.Dictionary;
import languageProcessing.LanguageProcessor;

public class EntitySetComparator implements Comparator<EntitySet> {
	
	private static EntitySetComparator INSTANCE = new EntitySetComparator();
	private LanguageProcessor dictionary;
	
	public static final double SIMILARITY_TRESHOLD = 0.25;
	public static final int ENTITY_SET_NAME_WEIGHT = 2;
	public static final int ATTRIBUTE_WEIGHT = 2;
	
	public static EntitySetComparator getInstance() {
		return INSTANCE;
	}
	
	/**
	 * Compares symmetric entitySets similarity
	 * @param entitySet1
	 * @param entitySet2
	 * @return 
	 */
	public double compareSymmetric(EntitySet entitySet1, EntitySet entitySet2) {
		return 0;
	}
	
	/**
	 * Compares asymmetric entitySets similarity.
	 * Result value is proportional to subEntitySets capacity.
	 * 
	 * @param subEntitySet
	 * @param superEntitySet
	 * @return
	 */
	public double compareAssymetric(EntitySet subEntitySet, EntitySet superEntitySet) {
		Utils.validateNotNull(subEntitySet);
		Utils.validateNotNull(superEntitySet);
		
		double max = 0;
		double value = 0;
		
		// entity sets name
		max += ENTITY_SET_NAME_WEIGHT;
		value += ENTITY_SET_NAME_WEIGHT * getDictionary().getSimilarity(subEntitySet.getNameText(), superEntitySet.getNameText());
		
		// entity sets attributes
		for (Attribute attribute : subEntitySet.getAttributes()) {
			max += ATTRIBUTE_WEIGHT;

			// !! working version
			if (superEntitySet.getAttributes().contains(attribute)) {
				value += ATTRIBUTE_WEIGHT;
			}
		}		
		
		return value/max;
	}

	public LanguageProcessor getDictionary() {
		if (dictionary == null) {
			dictionary = new Dictionary();
		}
		return dictionary;
	}
	
	@Override
	public int compare(EntitySet o1, EntitySet o2) {
		if (o1.getPriority() == null || o2.getPriority() == null) {
			return 1;
		}
		return (int) (o1.getPriority().doubleValue() - o2.getPriority().doubleValue());
	}
	
}
