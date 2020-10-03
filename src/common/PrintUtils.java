package common;

import java.util.List;
import java.util.Map;

import entityRelationshipModel.Attribute;
import entityRelationshipModel.ERModel;
import entityRelationshipModel.EntitySet;
import entityRelationshipModel.Relationship;
import entityRelationshipModel.RelationshipSide;
import transformations.Transformable;
import transformations.Transformation;

public class PrintUtils extends Utils {

	public static final String DELIMITER_DASH = "-";
	public static final String DELIMITER_COMMA = ",";

	/**
	 * Returns toString of EntitySet class
	 * 
	 * @param entitySet
	 * @return
	 */
	public static final String print(EntitySet entitySet) {
		validateNotNull(entitySet);

		return entitySet.getName() + ": " + join(entitySet.getAttributes().toArray(), DELIMITER_COMMA);
	}

	/**
	 * Returns toString of RelationshipSide class
	 * 
	 * @param relationshipSide
	 * @return
	 */
	public static final String print(RelationshipSide relationshipSide) {
		validateNotNull(relationshipSide);
		return "(" + relationshipSide.getEntitySet().toString() + relationshipSide.getRole() + ")";
	}

	/**
	 * Returns toString of Relationship class
	 * 
	 * @param relationship
	 * @return
	 */
	public static final String print(Relationship relationship) {
		validateNotNull(relationship);
		return join(relationship.getSides(), DELIMITER_DASH);
	}

	/**
	 * Returns toString of ERModel class
	 * 
	 * @param model
	 * @return
	 */
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

	public static final String print(Map<EntitySet, EntitySet> map) {
		StringBuffer result = new StringBuffer("Entity set map {\n");
		for (EntitySet entitySet : map.keySet()) {
			result.append("    " + entitySet.getName() + " : " + map.get(entitySet).getName() + "\n");
		}
		result.append("}");
		return result.toString();
	}

	public static final String print(List<Transformation> transformations) {
		StringBuffer result = new StringBuffer();

		for (Transformation transformation : transformations) {
			result.append(print(transformation) + "\n");
		}

		return result.toString();

	}

	public static final String print(Transformation transformation) {
		StringBuffer result = new StringBuffer();

		result.append(transformation.getCode() + ": (");
		boolean first = true;
		for (Transformable argument : transformation.getArguments()) {
			if (first) {
				first = false;
			} else {
				result.append(", ");
			}
			result.append(transformation.getArgumentMap().get(argument));
			result.append(":");
			result.append(PrintUtils.getReprName(argument));
		}
		result.append(")");

		return result.toString();
	}

	public static final String getReprName(EntitySet entitySet) {
		validateNotNull(entitySet);
		return entitySet.getName();
	}

	public static final String getReprName(Relationship relationship) {
		validateNotNull(relationship);

		StringBuffer result = new StringBuffer("([" + relationship.getClass().toString() + "] ");

		boolean first = true;
		for (RelationshipSide side : relationship.getSides()) {
			if (first) {
				first = false;
			} else {
				result.append("-");
			}
			result.append(getReprName(side.getEntitySet()));
		}
		result.append(")");

		return result.toString();
	}

	public static final String getReprName(RelationshipSide side) {
		validateNotNull(side);

		return getReprName(side.getEntitySet());
	}

	public static final String getReprName(Transformable transformable) {
		validateNotNull(transformable);

		if (transformable instanceof EntitySet) {
			return getReprName((EntitySet) transformable);
		} else if (transformable instanceof Relationship) {
			return getReprName((Relationship) transformable);
		} else if (transformable instanceof RelationshipSide) {
			return getReprName((RelationshipSide) transformable);
		} else {
			return "";
		}
	}

	public static final String print(Attribute attribute) {
		validateNotNull(attribute);
		return attribute.getAttribute();
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
