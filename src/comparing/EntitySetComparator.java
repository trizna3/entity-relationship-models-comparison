package comparing;

import java.util.Comparator;

import common.SimilarityConstants;
import common.Utils;
import entityRelationshipModel.EntitySet;

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
		
		// entity sets name
		max += SimilarityConstants.NAME_WEIGHT;
		value += SimilarityConstants.NAME_WEIGHT * getNamedComparator().compareSymmetric(entitySet1, entitySet2);
		
		// entity sets attributes
		max += SimilarityConstants.ATTRIBUTE_WEIGHT;
		value += SimilarityConstants.ATTRIBUTE_WEIGHT * getAttributedComparator().compareSymmetric(entitySet1, entitySet2);
		
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
		max += SimilarityConstants.NAME_WEIGHT;
		value += SimilarityConstants.NAME_WEIGHT * getNamedComparator().compareSymmetric(subEntitySet, superEntitySet);
		
		// entity sets attributes
		max += SimilarityConstants.ATTRIBUTE_WEIGHT;
		value += SimilarityConstants.ATTRIBUTE_WEIGHT * getAttributedComparator().compareAsymmetric(subEntitySet, superEntitySet);		
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
