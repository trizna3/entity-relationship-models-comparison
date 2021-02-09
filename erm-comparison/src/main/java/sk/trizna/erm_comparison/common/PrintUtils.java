package sk.trizna.erm_comparison.common;

import java.util.List;
import java.util.Map;

import sk.trizna.erm_comparison.comparing.mapping.Mapping;
import sk.trizna.erm_comparison.entity_relationship_model.Association;
import sk.trizna.erm_comparison.entity_relationship_model.Attribute;
import sk.trizna.erm_comparison.entity_relationship_model.ERModel;
import sk.trizna.erm_comparison.entity_relationship_model.EntitySet;
import sk.trizna.erm_comparison.entity_relationship_model.Relationship;
import sk.trizna.erm_comparison.entity_relationship_model.RelationshipSide;
import sk.trizna.erm_comparison.transformations.Transformable;
import sk.trizna.erm_comparison.transformations.Transformation;

public class PrintUtils extends Utils {

	public static final String DELIMITER_DASH = "-";
	public static final String DELIMITER_COLON = ":";
	public static final String DELIMITER_SEMICOLON = ";";
	public static final String DELIMITER_COMMA = ",";
	public final static String DIRECTION_DOWN = "->";
	public final static String DIRECTION_UP = "<-";

	/**
	 * Returns toString of EntitySet class
	 * 
	 * @param entitySet
	 * @return
	 */
	public static final String print(EntitySet entitySet) {
		validateNotNull(entitySet);

		return entitySet.getNameText() + ": " + join(entitySet.getAttributes(), DELIMITER_COMMA);
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

		StringBuilder result = new StringBuilder("ER Model\n");
		for (EntitySet entitySet : model.getEntitySets()) {
			result.append(entitySet.toString() + '\n');
		}
		for (Relationship relationship : model.getRelationships()) {
			result.append(relationship.toString() + '\n');
		}

		return result.toString();
	}

	public static final String print(Map<EntitySet, EntitySet> map) {
		if (map == null) {
			return "";
		}
		StringBuilder result = new StringBuilder("Entity set map {\n");
		for (EntitySet entitySet : map.keySet()) {
			result.append("    " + entitySet.getNameText() + " : " + map.get(entitySet).getNameText() + "\n");
		}
		result.append("}");
		return result.toString();
	}

	public static final String print(List<Transformation> transformations) {
		if (transformations == null) {
			return "";
		}
		
		StringBuilder result = new StringBuilder();
		for (Transformation transformation : transformations) {
			result.append(print(transformation) + "\n");
		}

		return result.toString();

	}

	public static final String print(Transformation transformation) {
		StringBuilder result = new StringBuilder();

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
		return entitySet.getNameText();
	}

	public static final String getReprName(Relationship relationship) {
		validateNotNull(relationship);

		StringBuilder result = new StringBuilder("([" + relationship.getClass().getSimpleName() + "] ");

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

	public static final String getReprName(Attribute attribute) {
		validateNotNull(attribute);

		return attribute.getAttribute();
	}

	public static final String getReprName(Transformable transformable) {
		validateNotNull(transformable);

		if (transformable instanceof EntitySet) {
			return getReprName((EntitySet) transformable);
		} else if (transformable instanceof Relationship) {
			return getReprName((Relationship) transformable);
		} else if (transformable instanceof RelationshipSide) {
			return getReprName((RelationshipSide) transformable);
		} else if (transformable instanceof Attribute) {
			return getReprName((Attribute) transformable);
		} else {
			return "";
		}
	}

	public static final String print(Attribute attribute) {
		validateNotNull(attribute);
		return attribute.getAttribute();
	}
	
	public static final String print(Mapping mapping) {
		validateNotNull(mapping);
		
		StringBuilder sb =  new StringBuilder("[");
		
		Map<EntitySet,EntitySet> esMap = MappingUtils.createEntitySetMap(mapping);
		for (EntitySet es : esMap.keySet()) {
			sb.append("(" + es.getNameText() + "<->" + esMap.get(es).getNameText() +") ");
		}
		sb.append("]\n");
		sb.append(print(mapping.getTransformations()));
		
		return sb.toString();
	}
	
	public static void log(String text) {
		System.out.println(text);
	}
	
	public static String getNameByIncidentEntitySets(Association association) {
		validateNotNull(association);
		
		StringBuilder assocName = new StringBuilder();
		association.getSides().forEach(side -> {
			if (assocName.length() > 0) {
				assocName.append(",");
			}
			assocName.append(side.getEntitySet().getNameText());
			assocName.append("(");
			assocName.append(side.getRole().toString());
			assocName.append(")");
		});
		
		assocName.insert(0, "(");
		assocName.append(")");
		
		return assocName.toString();
	}

	public static void logTransformation(Transformation transformation, String direction) {
		Utils.validateNotNull(transformation);
		Utils.validateNotNull(direction);

		StringBuilder result = new StringBuilder();

		result.append(direction + PrintUtils.print(transformation));

		log(result.toString());
	}

	private static String join(Iterable<?> objects, String delimiter) {
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
