package common;

import entityRelationshipModel.EntitySet;
import entityRelationshipModel.Relationship;
import entityRelationshipModel.RelationshipSide;

public class PrintUtils extends Utils {

	public static final String DELIMITER_DASH = "-";
	public static final String DELIMITER_COMMA = ",";

	// utils

	public static final String print(EntitySet entitySet) {
		validateNotNull(entitySet);

		return entitySet.getName() + join(entitySet.getAttributes(), DELIMITER_COMMA);
	}

	public static final String print(RelationshipSide relationshipSide) {
		validateNotNull(relationshipSide);
		return "(" + relationshipSide.getEntitySet().toString() + relationshipSide.getRole() + ")";
	}

	public static final String print(Relationship relationship) {
		validateNotNull(relationship);
		return join(relationship.getSides(), DELIMITER_DASH);
	}

	// helpers
	private static String join(Object[] objects, String delimiter) {
		int count = 0;
		StringBuilder result = new StringBuilder();
		for (Object object : objects) {
			if (count > 0) {
				result.append(delimiter);
			}
			result.append(object.toString());
			count++;
		}
		return result.toString();
	}
}
