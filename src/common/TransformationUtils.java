package common;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import common.enums.Enum;
import common.enums.EnumTransformation;
import common.enums.EnumTransformationRole;
import comparing.Mapping;
import entityRelationshipModel.Association;
import entityRelationshipModel.AssociationSide;
import entityRelationshipModel.EntitySet;
import entityRelationshipModel.Generalization;
import entityRelationshipModel.Relationship;
import entityRelationshipModel.RelationshipSide;
import entityRelationshipModel.TransformableAttribute;
import transformations.Transformable;
import transformations.Transformation;

public class TransformationUtils extends Utils {

	/**
	 * Adds the 'createEntitySet' transformation to the mapping's transformation
	 * list
	 */
	public static void addCreateEntitySet(Mapping mapping, EntitySet entitySet) {
		validateNotNull(mapping);
		validateNotNull(entitySet);

		entitySet.setTransformationRole(EnumTransformationRole.ENTITY_SET);
		mapping.addTransformation(new Transformation(EnumTransformation.CREATE_ENTITY_SET, new HashSet<>(Arrays.asList(entitySet))));
	}

	/**
	 * Adds the 'removeEntitySet' transformation to the mapping's transformation
	 * list
	 */
	public static void addRemoveEntitySet(Mapping mapping, EntitySet entitySet) {
		validateNotNull(mapping);
		validateNotNull(entitySet);

		entitySet.setTransformationRole(EnumTransformationRole.ENTITY_SET);
		mapping.addTransformation(new Transformation(EnumTransformation.REMOVE_ENTITY_SET, new HashSet<>(Arrays.asList(entitySet))));
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
				side.setTransformationRole(EnumTransformationRole.ASSOCIATION_SIDE);
				mapping.addTransformation(new Transformation(EnumTransformation.CHANGE_CARDINALITY, new HashSet<>(Arrays.asList(side))));
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

		entitySet.setTransformationRole(EnumTransformationRole.ENTITY_SET);
		entitySetTarget.setTransformationRole(EnumTransformationRole.ENTITY_SET_TARGET);
		mapping.addTransformation(new Transformation(EnumTransformation.RENAME_ENTITY_SET, new HashSet<>(Arrays.asList(entitySet, entitySetTarget))));
	}

	/**
	 * Adds the 'renameAttribute' transformation to the mapping's transformation
	 */
	public static void addRenameAttribute(Mapping mapping, EntitySet entitySet, String attribute, String attributeTarget) {
		validateNotNull(mapping);
		validateNotNull(attribute);
		validateNotNull(attributeTarget);
		validateNotNull(entitySet);

		TransformableAttribute attr = new TransformableAttribute(attribute);
		attr.setTransformationRole(EnumTransformationRole.ATTRIBUTE);
		TransformableAttribute attrTarget = new TransformableAttribute(attributeTarget);
		attrTarget.setTransformationRole(EnumTransformationRole.ATTRIBUTE_TARGET);

		mapping.addTransformation(new Transformation(EnumTransformation.RENAME_ATTRIBUTE, new HashSet<>(Arrays.asList(entitySet, attr, attrTarget))));
	}

	/**
	 * Adds the 'createAttribute' transformation to the mapping's transformation
	 */
	public static void addCreateAttribute(Mapping mapping, EntitySet entitySet, String attribute) {
		validateNotNull(mapping);
		validateNotNull(entitySet);
		validateNotNull(attribute);

		addAttributeTransformation(mapping, entitySet, attribute, true);
	}

	/**
	 * Adds the 'removeAttribute' transformation to the mapping's transformation
	 */
	public static void addRemoveAttribute(Mapping mapping, EntitySet entitySet, String attribute) {
		validateNotNull(mapping);
		validateNotNull(entitySet);
		validateNotNull(attribute);

		addAttributeTransformation(mapping, entitySet, attribute, false);
	}

	public static Transformable getTransformableByRole(Set<Transformable> transformables, String role) {
		validateNotNull(transformables);
		validateNotNull(role);
		for (Transformable transformable : transformables) {
			if (role.equals(transformable.getTransformationRole())) {
				return transformable;
			}
		}
		throw new IllegalArgumentException("Transformable of given role doesn't exist!");
	}

	public static void flipCardinality(AssociationSide associationSide) {
		validateNotNull(associationSide);

		if (Enum.CARDINALITY_ONE.equals(associationSide.getRole())) {
			associationSide.setTransformationRole(Enum.CARDINALITY_MANY);
		}
		if (Enum.CARDINALITY_MANY.equals(associationSide.getRole())) {
			associationSide.setTransformationRole(Enum.CARDINALITY_ONE);
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

	private static void addAttributeTransformation(Mapping mapping, EntitySet entitySet, String attribute, boolean isCreation) {
		TransformableAttribute attr = new TransformableAttribute(attribute);
		attr.setTransformationRole(EnumTransformationRole.ATTRIBUTE);

		String transformationCode = isCreation ? EnumTransformation.CREATE_ATTRIBUTE : EnumTransformation.REMOVE_ATTRIBUTE;
		mapping.addTransformation(new Transformation(transformationCode, new HashSet<>(Arrays.asList(entitySet, attr))));
	}
}
