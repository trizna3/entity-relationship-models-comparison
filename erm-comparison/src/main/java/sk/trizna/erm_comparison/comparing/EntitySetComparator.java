package sk.trizna.erm_comparison.comparing;

import java.util.Comparator;

import sk.trizna.erm_comparison.common.enums.SimilarityConstantsUtils;
import sk.trizna.erm_comparison.common.utils.StringUtils;
import sk.trizna.erm_comparison.common.utils.Utils;
import sk.trizna.erm_comparison.entity_relationship_model.EntitySet;

public class EntitySetComparator implements Comparator<EntitySet> {
	
	private static EntitySetComparator INSTANCE = new EntitySetComparator();
	private AttributedComparator attributedComparator;
	private NamedComparator namedComparator;
	
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
		Utils.validateNotNull(entitySet1);
		Utils.validateNotNull(entitySet2);
		
		double max = 0;
		double value = 0;
		
		if (StringUtils.areEqual(entitySet1.getNameText(),entitySet2.getNameText())) {
			return 1;
		}
		
		// entity sets name
		max += SimilarityConstantsUtils.getNameWeight();
		value += SimilarityConstantsUtils.getNameWeight() * getNamedComparator().compareSymmetric(entitySet1, entitySet2);
		
		// entity sets attributes
		max += SimilarityConstantsUtils.getAttributeWeight(entitySet1, entitySet2);
		value += SimilarityConstantsUtils.getAttributeWeight(entitySet1, entitySet2) * getAttributedComparator().compareSymmetric(entitySet1, entitySet2);
		
		return value == max ? 1 : value/max;
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
		max += SimilarityConstantsUtils.getNameWeight();
		value += SimilarityConstantsUtils.getNameWeight() * getNamedComparator().compareSymmetric(subEntitySet, superEntitySet);
		
		// entity sets attributes
		max += 2 * SimilarityConstantsUtils.getAttributeWeight(subEntitySet);
		value += 2 * SimilarityConstantsUtils.getAttributeWeight(subEntitySet) * getAttributedComparator().compareAsymmetric(subEntitySet, superEntitySet);		
		return value == max ? 1 : value/max;
	}
	
	@Override
	public int compare(EntitySet o1, EntitySet o2) {
		if (o1.getPriority() == null || o2.getPriority() == null) {
			return 1;
		}
		return (int) (o1.getPriority().doubleValue() - o2.getPriority().doubleValue());
	}

	private AttributedComparator getAttributedComparator() {
		if (attributedComparator == null) {
			attributedComparator = AttributedComparator.getInstance();
		}
		return attributedComparator;
	}

	private NamedComparator getNamedComparator() {
		if (namedComparator == null) {
			namedComparator = NamedComparator.getInstance();
		}
		return namedComparator;
	}
}
