package transformations;

import common.TransformationUtils;
import common.Utils;
import common.enums.EnumTransformation;
import common.enums.EnumTransformationRole;
import comparing.Mapping;
import entityRelationshipModel.Association;
import entityRelationshipModel.AssociationSide;
import entityRelationshipModel.EntitySet;
import entityRelationshipModel.Generalization;
import entityRelationshipModel.TransformableAttribute;

public class Transformator {

	/**
	 * Executes transformation by given transformation code and arguments. Returns
	 * the arguments in transformed form.
	 */
	public static Transformation execute(Mapping mapping, Transformation transformation) {
		Utils.validateNotNull(mapping);
		Utils.validateNotNull(transformation);

		if (EnumTransformation.CREATE_ENTITY_SET.equals(transformation.getCode())) {
			return executeCreateEntitySet(mapping, transformation);
		}
		if (EnumTransformation.REMOVE_ENTITY_SET.equals(transformation.getCode())) {
			return executeRemoveEntitySet(mapping, transformation);
		}
		if (EnumTransformation.CREATE_ASSOCIATION.equals(transformation.getCode())) {
			return executeCreateAssociation(mapping, transformation);
		}
		if (EnumTransformation.REMOVE_ASSOCIATION.equals(transformation.getCode())) {
			return executeRemoveAssociation(mapping, transformation);
		}
		if (EnumTransformation.CREATE_GENERALIZATION.equals(transformation.getCode())) {
			return executeCreateGeneralization(mapping, transformation);
		}
		if (EnumTransformation.REMOVE_GENERALIZATION.equals(transformation.getCode())) {
			return executeRemoveGeneralization(mapping, transformation);
		}
		if (EnumTransformation.CREATE_ATTRIBUTE.equals(transformation.getCode())) {
			return executeCreateAttribute(mapping, transformation);
		}
		if (EnumTransformation.REMOVE_ATTRIBUTE.equals(transformation.getCode())) {
			return executeRemoveAttribute(mapping, transformation);
		}
		if (EnumTransformation.CHANGE_CARDINALITY.equals(transformation.getCode())) {
			return executeChangeCardinality(mapping, transformation);
		}
		if (EnumTransformation.RENAME_ENTITY_SET.equals(transformation.getCode())) {
			return executeRenameEntitySet(mapping, transformation);
		}
		if (EnumTransformation.RENAME_ATTRIBUTE.equals(transformation.getCode())) {
			return executeRenameAttribute(mapping, transformation);
		}
		throw new IllegalArgumentException("Unknown transformation type!");
	}

	/**
	 * Reverts transformation given by transformation code and arguments. Returns
	 * the arguments in transformation-reverted form.
	 */
	public static Transformation revert(Mapping mapping, Transformation transformation) {
		throw new UnsupportedOperationException();
	}

	private static Transformation executeCreateEntitySet(Mapping mapping, Transformation transformation) {
		EntitySet entitySet = (EntitySet) TransformationUtils.getTransformableByRole(transformation.getArguments(), EnumTransformationRole.ENTITY_SET);
		mapping.getStudentModel().addEntitySet(entitySet);

		return transformation;
	}

	private static Transformation executeRemoveEntitySet(Mapping mapping, Transformation transformation) {
		EntitySet entitySet = (EntitySet) TransformationUtils.getTransformableByRole(transformation.getArguments(), EnumTransformationRole.ENTITY_SET);
		mapping.getStudentModel().removeEntitySet(entitySet);

		return transformation;
	}

	private static Transformation executeCreateAssociation(Mapping mapping, Transformation transformation) {
		Association association = (Association) TransformationUtils.getTransformableByRole(transformation.getArguments(), EnumTransformationRole.ASSOCIATION);
		mapping.getStudentModel().addRelationship(association);

		return transformation;
	}

	private static Transformation executeRemoveAssociation(Mapping mapping, Transformation transformation) {
		Association association = (Association) TransformationUtils.getTransformableByRole(transformation.getArguments(), EnumTransformationRole.ASSOCIATION);
		mapping.getStudentModel().removeRelationship(association);

		return transformation;
	}

	private static Transformation executeCreateGeneralization(Mapping mapping, Transformation transformation) {
		Generalization generalization = (Generalization) TransformationUtils.getTransformableByRole(transformation.getArguments(), EnumTransformationRole.GENERALIZATION);
		mapping.getStudentModel().addRelationship(generalization);

		return transformation;
	}

	private static Transformation executeRemoveGeneralization(Mapping mapping, Transformation transformation) {
		Generalization generalization = (Generalization) TransformationUtils.getTransformableByRole(transformation.getArguments(), EnumTransformationRole.GENERALIZATION);
		mapping.getStudentModel().removeRelationship(generalization);

		return transformation;
	}

	private static Transformation executeCreateAttribute(Mapping mapping, Transformation transformation) {
		EntitySet entitySet = (EntitySet) TransformationUtils.getTransformableByRole(transformation.getArguments(), EnumTransformationRole.ENTITY_SET);
		TransformableAttribute attribute = (TransformableAttribute) TransformationUtils.getTransformableByRole(transformation.getArguments(), EnumTransformationRole.ATTRIBUTE);

		entitySet.addAttribute(attribute.getAttribute());

		return transformation;
	}

	private static Transformation executeRemoveAttribute(Mapping mapping, Transformation transformation) {
		EntitySet entitySet = (EntitySet) TransformationUtils.getTransformableByRole(transformation.getArguments(), EnumTransformationRole.ENTITY_SET);
		TransformableAttribute attribute = (TransformableAttribute) TransformationUtils.getTransformableByRole(transformation.getArguments(), EnumTransformationRole.ATTRIBUTE);

		entitySet.removeAttribute(attribute.getAttribute());

		return transformation;
	}

	private static Transformation executeChangeCardinality(Mapping mapping, Transformation transformation) {
		AssociationSide associationSide = (AssociationSide) TransformationUtils.getTransformableByRole(transformation.getArguments(), EnumTransformationRole.ASSOCIATION_SIDE);
		TransformationUtils.flipCardinality(associationSide);

		return transformation;
	}

	private static Transformation executeRenameEntitySet(Mapping mapping, Transformation transformation) {
		EntitySet entitySet = (EntitySet) TransformationUtils.getTransformableByRole(transformation.getArguments(), EnumTransformationRole.ENTITY_SET);
		EntitySet targetEntitySet = (EntitySet) TransformationUtils.getTransformableByRole(transformation.getArguments(), EnumTransformationRole.ENTITY_SET_TARGET);

		entitySet.setName(targetEntitySet.getName());

		return transformation;
	}

	private static Transformation executeRenameAttribute(Mapping mapping, Transformation transformation) {
		return transformation;
	}
}
