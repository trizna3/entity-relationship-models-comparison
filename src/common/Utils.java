package common;

import entityRelationshipModel.ERModel;
import entityRelationshipModel.EntitySet;
import entityRelationshipModel.Relationship;

public class Utils {

	public static final boolean PRINT_RESULT = true;
	public static final boolean PRINT_TRANSFORMATION_PROGRESS = false;
	public static final boolean TRACK_PROGRESS = true;

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

	public static void validateBinary(Relationship relationship) {
		if (!relationship.isBinary()) {
			throw new IllegalStateException("Relationship is not Binary!");
		}
	}

	public static void validateContains(EntitySet entitySet, String attribute) {
		if (!entitySet.getAttributes().contains(attribute)) {
			throw new IllegalArgumentException("EntitySet doesn't contain this attribute!");
		}
	}
	
	public static void validatePositive(Integer integer) {
		validateNotNull(integer);
		if (integer.intValue() < 1) {
			throw new IllegalStateException("Integer value is not positive!");
		}
		
	}
}
