package sk.trizna.erm_comparison.comparing;

import java.util.HashSet;
import java.util.Set;

import sk.trizna.erm_comparison.common.SimilarityConstantsUtils;
import sk.trizna.erm_comparison.entity_relationship_model.Association;
import sk.trizna.erm_comparison.entity_relationship_model.RelationshipSide;

public class AssociationComparator {
	private static AssociationComparator INSTANCE = new AssociationComparator();
	
	private EntitySetComparator entitySetComparator;
	private AttributedComparator attributedComparator;
	private NamedComparator namedComparator;
	
	public static AssociationComparator getInstance() {
		return INSTANCE;
	}


	public double compareSymmetric(Association association1, Association association2) {
		double max = 0;
		double value = 0;
		
		max += SimilarityConstantsUtils.getNameWeight();
		value += SimilarityConstantsUtils.getNameWeight() * getNamedComparator().compareSymmetric(association1, association2);
		
		max += SimilarityConstantsUtils.getAttributeWeight(association1,association2);
		value += SimilarityConstantsUtils.getAttributeWeight(association1,association2) * getAttributedComparator().compareSymmetric(association1, association2);
		
		// greedy matching, not a perfect marriage solution
		Set<Integer> usedIndices = new HashSet<>(association2.getSides().size());
		for (RelationshipSide side1 : association1.getSides()) {
			double maxSimilarity = -1;
			int matchedSideIdx = -1;
			for (int i = 0; i < association2.getSides().size(); i++) {
				if (usedIndices.contains(i)) {
					continue;
				}
				RelationshipSide side2 = association2.getSides().get(i);
				double similarity = getEntitySetComparator().compareSymmetric(side1.getEntitySet(), side2.getEntitySet());
				if (similarity > maxSimilarity) {
					maxSimilarity = similarity;
					matchedSideIdx = i;
				}
			}
			max += SimilarityConstantsUtils.getEntitySetWeight();
			value += maxSimilarity;
			usedIndices.add(matchedSideIdx);
		}
		
		return value == max ? 1 : value / max;
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
	
	private NamedComparator getNamedComparator() {
		if (namedComparator == null) {
			namedComparator = NamedComparator.getInstance();
		}
		return namedComparator;
	}
}
