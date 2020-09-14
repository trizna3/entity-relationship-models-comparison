package transformations;

import java.util.ArrayList;
import java.util.Arrays;

import common.RelationshipUtils;
import common.TransformationUtils;
import common.Utils;
import common.enums.Enum;
import common.enums.EnumTransformation;
import common.enums.EnumTransformationRole;
import comparing.Mapping;
import entityRelationshipModel.Association;
import entityRelationshipModel.AssociationSide;
import entityRelationshipModel.ERModel;
import entityRelationshipModel.EntitySet;
import entityRelationshipModel.Generalization;
import entityRelationshipModel.Relationship;
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
		if (EnumTransformation.REBIND_MN_TO_1NN1.equals(transformation.getCode())) {
			return executeRebindMNto1NN1(mapping, transformation);
		}
		if (EnumTransformation.REBIND_1NN1_TO_MN.equals(transformation.getCode())) {
			return executeRebind1NN1toMN(mapping, transformation);
		}
		if (EnumTransformation.MOVE_ATTR_TO_INCIDENT_ENTITY_SET.equals(transformation.getCode())) {
			return executeMoveAttributeToIncidentEntitySet(mapping, transformation);
		}
		if (EnumTransformation.MOVE_ATTR_TO_INCIDENT_ASSOCIATION.equals(transformation.getCode())) {
			return executeMoveAttributeToIncidentAssociation(mapping, transformation);
		}
		if (EnumTransformation.GENERALIZATION_TO_11_ASSOCIATION.equals(transformation.getCode())) {
			return executeGeneralizationTo11Association(mapping, transformation);
		}
		if (EnumTransformation.EXTRACT_ATTR_TO_OWN_ENTITY_SET.equals(transformation.getCode())) {
			return executeExtractAttributeToOwnEntitySet(mapping, transformation);
		}
		if (EnumTransformation.CONTRACT_11_ASSOCIATION.equals(transformation.getCode())) {
			return executeContract11Association(mapping, transformation);
		}
		if (EnumTransformation.REBIND_NARY_ASSOCIATION.equals(transformation.getCode())) {
			return executeRebindNaryAssociation(mapping, transformation);
		}
		if (EnumTransformation.MERGE_ATTR_FROM_OWN_ENTITY_SET.equals(transformation.getCode())) {
			return executeMergeAttributeFromOwnEntitySet(mapping, transformation);
		}
		if (EnumTransformation._11_ASSOCIATION_TO_GENERALIZATION.equals(transformation.getCode())) {
			return execute11AssociationToGeneralization(mapping, transformation);
		}
		if (EnumTransformation.UNCONTRACT_11_ASSOCIATION.equals(transformation.getCode())) {
			return executeUncontract11Association(mapping, transformation);
		}
		if (EnumTransformation.BIND_TO_NARY_ASSOCIATION.equals(transformation.getCode())) {
			return executeBindToNaryAssociation(mapping, transformation);
		}
		throw new IllegalArgumentException("Unknown transformation type!");
	}

	/**
	 * Reverts transformation given by transformation code and arguments. Returns
	 * the arguments in transformation-reverted form.
	 */
	public static Transformation revert(Mapping mapping, Transformation transformation) {
		Utils.validateNotNull(transformation);
		transformation.setCode(getRevertingTransformation(transformation));
		return execute(mapping, transformation);
	}
	
	private static String getRevertingTransformation(Transformation transformation) {
		String code = transformation.getCode();
		switch (code) {
		case EnumTransformation.MOVE_ATTR_TO_INCIDENT_ASSOCIATION:
			return EnumTransformation.MOVE_ATTR_TO_INCIDENT_ENTITY_SET;
		case EnumTransformation.MOVE_ATTR_TO_INCIDENT_ENTITY_SET:
			return EnumTransformation.MOVE_ATTR_TO_INCIDENT_ASSOCIATION;
		case EnumTransformation.REBIND_MN_TO_1NN1:
			return EnumTransformation.REBIND_1NN1_TO_MN;
		case EnumTransformation.REBIND_1NN1_TO_MN:
			return EnumTransformation.REBIND_MN_TO_1NN1;
		case EnumTransformation.EXTRACT_ATTR_TO_OWN_ENTITY_SET:
			return EnumTransformation.MERGE_ATTR_FROM_OWN_ENTITY_SET;
		case EnumTransformation.GENERALIZATION_TO_11_ASSOCIATION:
			return EnumTransformation._11_ASSOCIATION_TO_GENERALIZATION;
		case EnumTransformation.CONTRACT_11_ASSOCIATION:
			return EnumTransformation.UNCONTRACT_11_ASSOCIATION;
		case EnumTransformation.REBIND_NARY_ASSOCIATION:
			return EnumTransformation.BIND_TO_NARY_ASSOCIATION;
		default:
			return null;
		}
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

	private static Transformation executeRebindMNto1NN1(Mapping mapping, Transformation transformation) {
		Association association = (Association) TransformationUtils.getTransformableByRole(transformation.getArguments(), EnumTransformationRole.ASSOCIATION);

		EntitySet entitySet = new EntitySet(association.getName(), new ArrayList<>(association.getAttributes()));
		Association association1 = new Association(
				new AssociationSide[] { new AssociationSide(entitySet, Enum.CARDINALITY_MANY), new AssociationSide(association.getFirstSide().getEntitySet(), Enum.CARDINALITY_ONE) }, null);
		Association association2 = new Association(
				new AssociationSide[] { new AssociationSide(entitySet, Enum.CARDINALITY_MANY), new AssociationSide(association.getSecondSide().getEntitySet(), Enum.CARDINALITY_ONE) }, null);

		ERModel studentModel = mapping.getStudentModel();
		studentModel.removeRelationship(association);
		studentModel.addEntitySet(entitySet);
		studentModel.addRelationship(association1);
		studentModel.addRelationship(association2);

		association1.setTransformationRole(EnumTransformationRole.ASSOCIATION_1);
		association2.setTransformationRole(EnumTransformationRole.ASSOCIATION_2);
		entitySet.setTransformationRole(EnumTransformationRole.ENTITY_SET);

		transformation.getArguments().clear();
		transformation.getArguments().add(association1);
		transformation.getArguments().add(association2);
		transformation.getArguments().add(entitySet);

		return transformation;
	}

	private static Transformation executeRebind1NN1toMN(Mapping mapping, Transformation transformation) {
		Association association1 = (Association) TransformationUtils.getTransformableByRole(transformation.getArguments(), EnumTransformationRole.ASSOCIATION_1);
		Association association2 = (Association) TransformationUtils.getTransformableByRole(transformation.getArguments(), EnumTransformationRole.ASSOCIATION_2);
		EntitySet entitySet = (EntitySet) TransformationUtils.getTransformableByRole(transformation.getArguments(), EnumTransformationRole.ENTITY_SET);

		Association association = new Association(new AssociationSide[] { new AssociationSide(RelationshipUtils.getOtherEntitySet(association1, entitySet), Enum.CARDINALITY_MANY),
				new AssociationSide(RelationshipUtils.getOtherEntitySet(association2, entitySet), Enum.CARDINALITY_ONE) }, entitySet.getAttributes());

		ERModel studentModel = mapping.getStudentModel();
		studentModel.removeRelationship(association1);
		studentModel.removeRelationship(association2);
		studentModel.removeEntitySet(entitySet);
		studentModel.addRelationship(association);

		association.setTransformationRole(EnumTransformationRole.ASSOCIATION);

		transformation.getArguments().clear();
		transformation.getArguments().add(association);

		return transformation;
	}

	private static Transformation executeMoveAttributeToIncidentEntitySet(Mapping mapping, Transformation transformation) {
		TransformableAttribute attribute = (TransformableAttribute) TransformationUtils.getTransformableByRole(transformation.getArguments(), EnumTransformationRole.ATTRIBUTE);
		Association association = (Association) TransformationUtils.getTransformableByRole(transformation.getArguments(), EnumTransformationRole.ASSOCIATION);
		EntitySet entitySet = (EntitySet) TransformationUtils.getTransformableByRole(transformation.getArguments(), EnumTransformationRole.ENTITY_SET);

		association.removeAttribute(attribute.getAttribute());
		entitySet.addAttribute(attribute.getAttribute());

		return transformation;
	}

	private static Transformation executeMoveAttributeToIncidentAssociation(Mapping mapping, Transformation transformation) {
		TransformableAttribute attribute = (TransformableAttribute) TransformationUtils.getTransformableByRole(transformation.getArguments(), EnumTransformationRole.ATTRIBUTE);
		Association association = (Association) TransformationUtils.getTransformableByRole(transformation.getArguments(), EnumTransformationRole.ASSOCIATION);
		EntitySet entitySet = (EntitySet) TransformationUtils.getTransformableByRole(transformation.getArguments(), EnumTransformationRole.ENTITY_SET);

		association.addAttribute(attribute.getAttribute());
		entitySet.removeAttribute(attribute.getAttribute());

		return transformation;
	}

	private static Transformation executeGeneralizationTo11Association(Mapping mapping, Transformation transformation) {
		Generalization generalization = (Generalization) TransformationUtils.getTransformableByRole(transformation.getArguments(), EnumTransformationRole.GENERALIZATION);

		Association association = new Association(new AssociationSide[] { new AssociationSide(generalization.getFirstSide().getEntitySet(), Enum.CARDINALITY_ONE),
				new AssociationSide(generalization.getSecondSide().getEntitySet(), Enum.CARDINALITY_ONE) }, null);

		mapping.getStudentModel().removeRelationship(generalization);
		mapping.getStudentModel().addRelationship(association);

		association.setTransformationRole(EnumTransformationRole.ASSOCIATION);
		transformation.getArguments().clear();
		transformation.getArguments().add(association);

		return transformation;
	}

	private static Transformation executeExtractAttributeToOwnEntitySet(Mapping mapping, Transformation transformation) {
		TransformableAttribute attribute = (TransformableAttribute) TransformationUtils.getTransformableByRole(transformation.getArguments(), EnumTransformationRole.ATTRIBUTE);
		EntitySet sourceEntitySet = (EntitySet) TransformationUtils.getTransformableByRole(transformation.getArguments(), EnumTransformationRole.SOURCE_ENTITY_SET);
		EntitySet targetEntitySet = (EntitySet) TransformationUtils.getTransformableByRole(transformation.getArguments(), EnumTransformationRole.DEST_ENTITY_SET);

		sourceEntitySet.removeAttribute(attribute.getAttribute());

		if (targetEntitySet == null) {
			targetEntitySet = new EntitySet(attribute.getAttribute(), new ArrayList<>(Arrays.asList(Enum.NAME_ATTRIBUTE)));
			targetEntitySet.setTransformationRole(EnumTransformationRole.SOURCE_ENTITY_SET);
			mapping.getStudentModel().addEntitySet(targetEntitySet);
			mapping.getStudentModel().addRelationship(
					new Association(new AssociationSide[] { new AssociationSide(sourceEntitySet, Enum.CARDINALITY_MANY), new AssociationSide(targetEntitySet, Enum.CARDINALITY_ONE) }, null));
		} else {
			if (!targetEntitySet.getAttributes().contains(attribute.getAttribute())) {
				targetEntitySet.addAttribute(attribute.getAttribute());
			}
		}

		sourceEntitySet.setTransformationRole(EnumTransformationRole.DEST_ENTITY_SET);

		return transformation;
	}

	private static Transformation executeContract11Association(Mapping mapping, Transformation transformation) {
		Association association = (Association) TransformationUtils.getTransformableByRole(transformation.getArguments(), EnumTransformationRole.ASSOCIATION);

		EntitySet entitySet1 = association.getFirstSide().getEntitySet();
		EntitySet entitySet2 = association.getSecondSide().getEntitySet();

		mapping.getStudentModel().removeRelationship(association);
		entitySet1.getAttributes().addAll(entitySet2.getAttributes());
		entitySet1.getAttributes().addAll(association.getAttributes());
		entitySet1.setName(entitySet1.getName() + Enum.ENTITY_SETS_DELIMITER + entitySet2.getName());

		for (Relationship relationship : entitySet2.getIncidentRelationships()) {
			RelationshipUtils.rebindEntitySets(relationship, entitySet2, entitySet1);
		}

		entitySet1.setTransformationRole(EnumTransformationRole.ENTITY_SET);
		transformation.getArguments().clear();
		transformation.getArguments().add(entitySet1);

		return transformation;
	}

	private static Transformation executeRebindNaryAssociation(Mapping mapping, Transformation transformation) {
		Association association = (Association) TransformationUtils.getTransformableByRole(transformation.getArguments(), EnumTransformationRole.ASSOCIATION);

		mapping.getStudentModel().removeRelationship(association);

		EntitySet entitySet = new EntitySet(association.getName(), association.getAttributes());
		mapping.getStudentModel().addEntitySet(entitySet);

		for (AssociationSide side : association.getSides()) {
			mapping.getStudentModel().addRelationship(
					new Association(new AssociationSide[] { new AssociationSide(entitySet, Enum.CARDINALITY_MANY), new AssociationSide(side.getEntitySet(), Enum.CARDINALITY_ONE) }, null));
		}

		entitySet.setTransformationRole(EnumTransformationRole.ENTITY_SET);
		transformation.getArguments().clear();
		transformation.getArguments().add(entitySet);

		return transformation;
	}
	
	private static Transformation executeMergeAttributeFromOwnEntitySet(Mapping mapping, Transformation transformation) {
		throw new UnsupportedOperationException();
	}
	private static Transformation execute11AssociationToGeneralization(Mapping mapping, Transformation transformation) {
		throw new UnsupportedOperationException();
	}
	private static Transformation executeUncontract11Association(Mapping mapping, Transformation transformation) {
		throw new UnsupportedOperationException();
	}
	private static Transformation executeBindToNaryAssociation(Mapping mapping, Transformation transformation) {
		throw new UnsupportedOperationException();
	}	
}
