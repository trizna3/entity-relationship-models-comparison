package common;

import common.enums.EnumTransformation;
import common.enums.EnumTransformationRole;
import common.enums.Enums;
import comparing.Mapping;
import entityRelationshipModel.Association;
import entityRelationshipModel.AssociationSide;
import entityRelationshipModel.Attribute;
import entityRelationshipModel.Attributed;
import entityRelationshipModel.EntitySet;
import entityRelationshipModel.Generalization;
import entityRelationshipModel.Relationship;
import entityRelationshipModel.RelationshipSide;
import transformations.Transformable;
import transformations.Transformation;
import transformations.Transformator;

public class TransformationUtils extends Utils {

	/**
	 * Adds the 'createEntitySet' transformation to the mapping's transformation
	 * list
	 */
	public static void addCreateEntitySet(Mapping mapping, EntitySet entitySet) {
		validateNotNull(mapping);
		validateNotNull(entitySet);

		Transformation transformation = new Transformation(EnumTransformation.CREATE_ENTITY_SET);
		transformation.addArgument(entitySet, EnumTransformationRole.ENTITY_SET);

		mapping.addTransformation(transformation);
	}

	/**
	 * Adds the 'removeEntitySet' transformation to the mapping's transformation
	 * list
	 */
	public static void addRemoveEntitySet(Mapping mapping, EntitySet entitySet) {
		validateNotNull(mapping);
		validateNotNull(entitySet);

		Transformation transformation = new Transformation(EnumTransformation.REMOVE_ENTITY_SET);
		transformation.addArgument(entitySet, EnumTransformationRole.ENTITY_SET);

		mapping.addTransformation(transformation);
	}

	/**
	 * Adds the 'createRelationship' transformation to the mapping's transformation
	 * list
	 */
	public static void addCreateRelationship(Mapping mapping, Relationship relationship) {
		validateNotNull(mapping);
		validateNotNull(relationship);

		addRelationshipTransformation(mapping, relationship, true);
	}

	/**
	 * Adds the 'removeRelationship' transformation to the mapping's transformation
	 * list
	 */
	public static void addRemoveRelationship(Mapping mapping, Relationship relationship) {
		validateNotNull(mapping);
		validateNotNull(relationship);

		addRelationshipTransformation(mapping, relationship, false);
	}

	/**
	 * Adds the 'changeCardinality' transformation to the mapping's transformation
	 * Assume the relationships are equally mapped.
	 */
	public static void addChangeCardinality(Mapping mapping, Relationship oldRel, Relationship newRel) {
		validateNotNull(mapping);
		validateNotNull(oldRel);
		validateNotNull(newRel);

		for (RelationshipSide side : oldRel.getSides()) {
			String oppositeRole = RelationshipUtils.getRole(newRel, side.getEntitySet().getMappedTo());
			if (!StringUtils.areEqual(side.getRole(), oppositeRole)) {

				Transformation transformation = new Transformation(EnumTransformation.CHANGE_CARDINALITY);
				transformation.addArgument(side, EnumTransformationRole.ASSOCIATION_SIDE);

				mapping.addTransformation(transformation);
			}
		}
	}

	/**
	 * Adds the 'renameEntitySet' transformation to the mapping's transformation
	 */
	public static void addRenameEntitySet(Mapping mapping, EntitySet entitySet, EntitySet entitySetTarget) {
		validateNotNull(mapping);
		validateNotNull(entitySet);
		validateNotNull(entitySetTarget);

		Transformation transformation = new Transformation(EnumTransformation.RENAME_ENTITY_SET);
		transformation.addArgument(entitySet, EnumTransformationRole.ENTITY_SET);
		transformation.addArgument(entitySetTarget, EnumTransformationRole.ENTITY_SET_TARGET);

		mapping.addTransformation(transformation);
	}

	/**
	 * Adds the 'renameAttribute' transformation to the mapping's transformation
	 */
	public static void addRenameAttribute(Mapping mapping, EntitySet entitySet, String attribute, String attributeTarget) {
		validateNotNull(mapping);
		validateNotNull(attribute);
		validateNotNull(attributeTarget);
		validateNotNull(entitySet);

		Transformation transformation = new Transformation(EnumTransformation.RENAME_ATTRIBUTE);
		transformation.addArgument(new Attribute(attribute), EnumTransformationRole.ATTRIBUTE);
		transformation.addArgument(new Attribute(attributeTarget), EnumTransformationRole.ATTRIBUTE_TARGET);

		mapping.addTransformation(transformation);
	}

	/**
	 * Adds the 'createAttribute' transformation to the mapping's transformation
	 */
	public static void addCreateAttribute(Mapping mapping, Attributed attributed, Attribute attribute) {
		validateNotNull(mapping);
		validateNotNull(attributed);
		validateNotNull(attribute);

		if (attributed instanceof EntitySet) {
			addAttributeTransformation(mapping, (EntitySet) attributed, attribute, true);
		}
		if (attributed instanceof Association) {
			addAttributeTransformation(mapping, (Association) attributed, attribute, true);
		}
	}

	/**
	 * Adds the 'removeAttribute' transformation to the mapping's transformation
	 */
	public static void addRemoveAttribute(Mapping mapping, Attributed attributed, Attribute attribute) {
		validateNotNull(mapping);
		validateNotNull(attributed);
		validateNotNull(attribute);

		if (attributed instanceof EntitySet) {
			addAttributeTransformation(mapping, (EntitySet) attributed, attribute, false);
		}
		if (attributed instanceof Association) {
			addAttributeTransformation(mapping, (Association) attributed, attribute, false);
		}
	}

	public static Transformable getTransformableByRole(Transformation transformation, EnumTransformationRole role) {
		validateNotNull(transformation);
		validateNotNull(role);

		for (Transformable transformable : transformation.getArguments()) {
			if (role.equals(transformation.getArgumentMap().get(transformable))) {
				return transformable;
			}
		}
		return null;
	}

	/**
	 * Flips the cardinality on given associationSide (between ONE - MANY).
	 * 
	 * @param associationSide
	 */
	public static void flipCardinality(AssociationSide associationSide) {
		validateNotNull(associationSide);

		if (Enums.CARDINALITY_ONE.equals(associationSide.getRole())) {
			associationSide.setRole(Enums.CARDINALITY_MANY);
		}
		if (Enums.CARDINALITY_MANY.equals(associationSide.getRole())) {
			associationSide.setRole(Enums.CARDINALITY_ONE);
		}
	}

	/**
	 * To avoid cycling, a transformation made will leave a flag on the transformed
	 * object. It's reverting transformation will either drop the flag or write it's
	 * own. Flag will serve as indicator in the transformation analysis.
	 * 
	 * @param code = code of currently performed transformation
	 */
	public static void overwriteTransformationFlag(EnumTransformation code, Transformable transformable) {
		EnumTransformation revertingCode = Transformator.getRevertingTransformation(code);

		if (transformable.containsTransformationFlag(revertingCode)) {
			// it's the transformation reverting scenario - drop the flag
			transformable.removeTransformationFlag(revertingCode);
		} else {
			// it's the new transformation execution scenario - write own flag
			transformable.addTransformationFlag(code);
		}

	}

	private static void addRelationshipTransformation(Mapping mapping, Relationship relationship, boolean isCreation) {

		if (relationship instanceof Association) {
			EnumTransformation transformationCode = isCreation ? EnumTransformation.CREATE_ASSOCIATION : EnumTransformation.REMOVE_ASSOCIATION;

			Transformation transformation = new Transformation(transformationCode);
			transformation.addArgument(relationship, EnumTransformationRole.ASSOCIATION);

			mapping.addTransformation(transformation);
			return;
		}
		if (relationship instanceof Generalization) {
			EnumTransformation transformationCode = isCreation ? EnumTransformation.CREATE_GENERALIZATION : EnumTransformation.REMOVE_GENERALIZATION;

			Transformation transformation = new Transformation(transformationCode);
			transformation.addArgument(relationship, EnumTransformationRole.GENERALIZATION);

			mapping.addTransformation(transformation);
			return;
		}
	}

	private static void addAttributeTransformation(Mapping mapping, EntitySet entitySet, Attribute attribute, boolean isCreation) {
		EnumTransformation transformationCode = isCreation ? EnumTransformation.CREATE_ATTRIBUTE : EnumTransformation.REMOVE_ATTRIBUTE;

		Transformation transformation = new Transformation(transformationCode);
		transformation.addArgument(entitySet, EnumTransformationRole.ENTITY_SET);
		transformation.addArgument(attribute, EnumTransformationRole.ATTRIBUTE);

		mapping.addTransformation(transformation);
	}

	private static void addAttributeTransformation(Mapping mapping, Association association, Attribute attribute, boolean isCreation) {
		EnumTransformation transformationCode = isCreation ? EnumTransformation.CREATE_ATTRIBUTE : EnumTransformation.REMOVE_ATTRIBUTE;

		Transformation transformation = new Transformation(transformationCode);
		transformation.addArgument(association, EnumTransformationRole.ASSOCIATION);
		transformation.addArgument(attribute, EnumTransformationRole.ATTRIBUTE);

		mapping.addTransformation(transformation);
	}
}
