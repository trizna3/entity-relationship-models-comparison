package common;

import entityRelationshipModel.ERModel;
import entityRelationshipModel.EntitySet;
import entityRelationshipModel.Relationship;

public class Utils {

	public static void validateNotNull(Object input) {
		if (input == null) {
			throw new IllegalArgumentException("Null argument!");
		}
	}

	public static void validateContains(ERModel model, EntitySet entitySet) {
		if (!model.contains(entitySet)) {
			throw new IllegalArgumentException("EntitySet is not contained in the model!");
		}
	}

	public static void validateContains(ERModel model, Relationship relationship) {
		if (!model.contains(relationship)) {
			throw new IllegalArgumentException("Relationship is not contained in the model!");
		}
	}

	public static void validateContains(Relationship relationship, EntitySet entitySet) {
		if (!relationship.contains(entitySet)) {
			throw new IllegalArgumentException("EntitySet is not contained in the relationship!");
		}
	}

	public static void validateMapped(EntitySet entitySet) {
		if (entitySet.getMappedTo() == null) {
			throw new IllegalArgumentException("EntitySet is not mapped!");
		}
	}

	public static void validateNotMapped(EntitySet entitySet) {
		if (entitySet.getMappedTo() != null) {
			throw new IllegalArgumentException("EntitySet is mapped!");
		}
	}
}
