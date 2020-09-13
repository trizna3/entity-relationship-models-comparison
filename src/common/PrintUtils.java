package common;

import entityRelationshipModel.ERModel;
import entityRelationshipModel.EntitySet;
import entityRelationshipModel.Relationship;
import entityRelationshipModel.RelationshipSide;

public class PrintUtils extends Utils {

	public static final String DELIMITER_DASH = "-";
	public static final String DELIMITER_COMMA = ",";

	public static final String print(EntitySet entitySet) {
		validateNotNull(entitySet);

		return entitySet.getName() + ": " + join(entitySet.getAttributes().toArray(), DELIMITER_COMMA);
	}

	public static final String print(RelationshipSide relationshipSide) {
		validateNotNull(relationshipSide);
		return "(" + relationshipSide.getEntitySet().toString() + relationshipSide.getRole() + ")";
	}

	public static final String print(Relationship relationship) {
		validateNotNull(relationship);
		return join(relationship.getSides(), DELIMITER_DASH);
	}

	public static final String print(ERModel model) {
		validateNotNull(model);

		StringBuffer result = new StringBuffer("ER Model\n");
		for (EntitySet entitySet : model.getEntitySets()) {
			result.append(entitySet.toString() + '\n');
		}
		for (Relationship relationship : model.getRelationships()) {
			result.append(relationship.toString() + '\n');
		}

		return result.toString();
	}

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
