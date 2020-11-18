package comparing;

import common.Utils;
import entityRelationshipModel.Association;
import entityRelationshipModel.EntitySet;

public class EntitySetAssociationComparator {
	
	private static EntitySetAssociationComparator INSTANCE = new EntitySetAssociationComparator();
	
	private EntitySetComparator entitySetComparator;
	private AttributedComparator attributedComparator;
	private NamedComparator namedComparator;
	
	public static final double SIMILARITY_TRESHOLD = 0.25;
	
	public static EntitySetAssociationComparator getInstance() {
		return INSTANCE;
	}

	/**
	 * Compares entitySet and association (elements are from opposing er models).
	 * Used in special scenario:
	 * 		- entitySet is considered to be a joining entitySet (joining other two entitySets with binary associations of cardinality 1:N)
	 * 		- association of cardinality Many:Many
	 * 
	 * Method is used to determine if elements are similar enough to transform (M:N to 1:N,N:1 or reverse) & match. 
	 *  
	 * @param entitySet
	 * @param association
	 * @return
	 */
	public double compareSymmetric(EntitySet entitySet, Association association) {
		Utils.validateNotNull(entitySet);
		Utils.validateNotNull(association);
		
		double max = 0;
		double value = 0;
		
		// elements names
		max += ERComparator.NAME_WEIGHT;
		value += ERComparator.NAME_WEIGHT * getNamedComparator().compareSymmetric(entitySet, association); 
		
		// elements attributes
		max += ERComparator.ATTRIBUTE_WEIGHT;
		value += ERComparator.ATTRIBUTE_WEIGHT * getAttributedComparator().compareSymmetric(entitySet, association);
		
		// elements incident entity sets (exactly 2 in both cases)
		EntitySet assocEntitySet1 = association.getFirstSide().getEntitySet();
		EntitySet assocEntitySet2 = association.getSecondSide().getEntitySet();
		EntitySet entitySetNeighbour1 = entitySet.getFirstNeighbour();
		EntitySet entitySetNeighbour2 = entitySet.getSecondNeighbour();
		
		// we don't know, which entity sets to pair
		double a = getEntitySetComparator().compareSymmetric(assocEntitySet1, entitySetNeighbour1);
		double b = getEntitySetComparator().compareSymmetric(assocEntitySet1, entitySetNeighbour2);
		double c = getEntitySetComparator().compareSymmetric(assocEntitySet2, entitySetNeighbour2);
		double d = getEntitySetComparator().compareSymmetric(assocEntitySet2, entitySetNeighbour1);
		
		max += 2 * ERComparator.ENTITY_SET_WEIGHT;
		value += ERComparator.ENTITY_SET_WEIGHT * Math.max(a+c, b+d);
		
		return value == max ? 1 : value/max;
	}

	private EntitySetComparator getEntitySetComparator() {
		if (entitySetComparator == null) {
			entitySetComparator = EntitySetComparator.getInstance();
		}
		return entitySetComparator;
	}

	private AttributedComparator getAttributedComparator() {
		if (attributedComparator == null) {
			attributedComparator = AttributedComparator.getInstance();
		}
		return attributedComparator;
	}

	public NamedComparator getNamedComparator() {
		if(namedComparator == null) {
			namedComparator = NamedComparator.getInstance();
		}
		return namedComparator;
	}
}