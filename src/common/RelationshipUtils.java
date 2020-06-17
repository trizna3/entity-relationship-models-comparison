package common;

import entityRelationshipModel.Association;
import entityRelationshipModel.AssociationSide;
import entityRelationshipModel.EntitySet;
import entityRelationshipModel.Generalization;
import entityRelationshipModel.Relationship;

public class RelationshipUtils extends Utils {

	// utils
	public static String getCardinality(Association association, EntitySet entitySet) {
		validateInput(association);
		validateInput(entitySet);

		for (AssociationSide side : association.getSides()) {
			if (entitySet.equals(side.getEntitySet())) {
				return side.getRole();
			}
		}

		throw new IllegalArgumentException("This Association doesn't contain given entity set!");
	}

	public static boolean contains(Relationship relationship, EntitySet entitySet) {
		validateInput(relationship);
		validateInput(entitySet);

		if (relationship instanceof Association) {
			return contains((Association) relationship, entitySet);
		} else if (relationship instanceof Generalization) {
			return contains((Generalization) relationship, entitySet);
		} else {
			throw new IllegalArgumentException("Invalid relationship type!");
		}
	}

	// helpers
	private static boolean contains(Association association, EntitySet entitySet) {
		for (AssociationSide side : association.getSides()) {
			if (entitySet.equals(side.getEntitySet())) {
				return true;
			}
		}
		return false;
	}

	private static boolean contains(Generalization generalization, EntitySet entitySet) {
		return entitySet.equals(generalization.getSuperEntitySet()) || entitySet.equals(generalization.getSubEntitySet());
	}
}
