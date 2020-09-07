package common;

import java.util.Arrays;
import java.util.HashSet;

import common.enums.EnumTransformation;
import common.enums.EnumTransformationRole;
import comparing.Mapping;
import entityRelationshipModel.Association;
import entityRelationshipModel.EntitySet;
import entityRelationshipModel.Generalization;
import entityRelationshipModel.Relationship;
import entityRelationshipModel.RelationshipSide;
import transformations.Transformation;

public class TransformationUtils extends Utils {

	/**
	 * Adds the 'createEntitySet' transformation to the mapping's transformation
	 * list
	 */
	public static void addCreateEntitySet(Mapping mapping, EntitySet entitySet) {
		entitySet.setTransformationRole(EnumTransformationRole.ENTITY_SET);
		mapping.addTransformation(new Transformation(EnumTransformation.CREATE_ENTITY_SET, new HashSet<>(Arrays.asList(entitySet))));
	}

	/**
	 * Adds the 'removeEntitySet' transformation to the mapping's transformation
	 * list
	 */
	public static void addRemoveEntitySet(Mapping mapping, EntitySet entitySet) {
		entitySet.setTransformationRole(EnumTransformationRole.ENTITY_SET);
		mapping.addTransformation(new Transformation(EnumTransformation.REMOVE_ENTITY_SET, new HashSet<>(Arrays.asList(entitySet))));
	}

	/**
	 * Adds the 'createRelationship' transformation to the mapping's transformation
	 * list
	 */
	public static void addCreateRelationship(Mapping mapping, Relationship relationship) {
		validateNotNull(relationship);
		addRelationshipTransformation(mapping, relationship, true);
	}

	/**
	 * Adds the 'removeRelationship' transformation to the mapping's transformation
	 * list
	 */
	public static void addRemoveRelationship(Mapping mapping, Relationship relationship) {
		validateNotNull(relationship);
		addRelationshipTransformation(mapping, relationship, false);
	}

	/**
	 * Adds the 'changeCardinality' transformation to the mapping's transformation
	 * Assume the relationships are equally mapped.
	 */
	public static void addChangeCardinality(Mapping mapping, Relationship oldRel, Relationship newRel) {
		validateNotNull(oldRel);
		validateNotNull(newRel);

		for (RelationshipSide side : oldRel.getSides()) {
			String oppositeRole = RelationshipUtils.getRole(newRel, side.getEntitySet().getMappedTo());
			if (!StringUtils.areEqual(side.getRole(), oppositeRole)) {
				mapping.addTransformation(new Transformation(EnumTransformation.CHANGE_CARDINALITY, new HashSet<>(Arrays.asList(side))));
			}
		}
	}

	private static void addRelationshipTransformation(Mapping mapping, Relationship relationship, boolean isCreation) {

		if (relationship instanceof Association) {
			relationship.setTransformationRole(EnumTransformationRole.ASSOCIATION);
			String transformationCode = isCreation ? EnumTransformation.CREATE_ASSOCIATION : EnumTransformation.REMOVE_ASSOCIATION;
			mapping.addTransformation(new Transformation(transformationCode, new HashSet<>(Arrays.asList(relationship))));
			return;
		}
		if (relationship instanceof Generalization) {
			relationship.setTransformationRole(EnumTransformationRole.GENERALIZATION);
			String transformationCode = isCreation ? EnumTransformation.CREATE_GENERALIZATION : EnumTransformation.REMOVE_GENERALIZATION;
			mapping.addTransformation(new Transformation(transformationCode, new HashSet<>(Arrays.asList(relationship))));
			return;
		}
	}
}
