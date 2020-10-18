package transformations;

import java.util.ArrayList;
import java.util.Arrays;

import common.RelationshipUtils;
import common.StringUtils;
import common.TransformationUtils;
import common.Utils;
import common.enums.EnumTransformation;
import common.enums.EnumTransformationRole;
import common.enums.Enums;
import comparing.Mapping;
import entityRelationshipModel.Association;
import entityRelationshipModel.AssociationSide;
import entityRelationshipModel.Attribute;
import entityRelationshipModel.ERModel;
import entityRelationshipModel.EntitySet;
import entityRelationshipModel.Generalization;
import entityRelationshipModel.Relationship;
import entityRelationshipModel.TransformableFlag;
import entityRelationshipModel.TransformableList;

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
		transformation.setCode(getRevertingTransformation(transformation.getCode()));
		execute(mapping, transformation);
		transformation.setCode(getRevertingTransformation(transformation.getCode()));
		return transformation;
	}

	public static String getRevertingTransformation(String code) {
		Utils.validateNotNull(code);

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
		case EnumTransformation.MERGE_ATTR_FROM_OWN_ENTITY_SET:
			return EnumTransformation.EXTRACT_ATTR_TO_OWN_ENTITY_SET;
		case EnumTransformation.GENERALIZATION_TO_11_ASSOCIATION:
			return EnumTransformation._11_ASSOCIATION_TO_GENERALIZATION;
		case EnumTransformation._11_ASSOCIATION_TO_GENERALIZATION:
			return EnumTransformation.GENERALIZATION_TO_11_ASSOCIATION;
		case EnumTransformation.CONTRACT_11_ASSOCIATION:
			return EnumTransformation.UNCONTRACT_11_ASSOCIATION;
		case EnumTransformation.UNCONTRACT_11_ASSOCIATION:
			return EnumTransformation.CONTRACT_11_ASSOCIATION;
		case EnumTransformation.REBIND_NARY_ASSOCIATION:
			return EnumTransformation.BIND_TO_NARY_ASSOCIATION;
		case EnumTransformation.BIND_TO_NARY_ASSOCIATION:
			return EnumTransformation.REBIND_NARY_ASSOCIATION;
		default:
			return null;
		}
	}

	private static Transformation executeCreateEntitySet(Mapping mapping, Transformation transformation) {
		EntitySet entitySet = (EntitySet) TransformationUtils.getTransformableByRole(transformation, EnumTransformationRole.ENTITY_SET);
		mapping.getStudentModel().addEntitySet(entitySet);

		return transformation;
	}

	private static Transformation executeRemoveEntitySet(Mapping mapping, Transformation transformation) {
		EntitySet entitySet = (EntitySet) TransformationUtils.getTransformableByRole(transformation, EnumTransformationRole.ENTITY_SET);
		mapping.getStudentModel().removeEntitySet(entitySet);

		return transformation;
	}

	private static Transformation executeCreateAssociation(Mapping mapping, Transformation transformation) {
		Association association = (Association) TransformationUtils.getTransformableByRole(transformation, EnumTransformationRole.ASSOCIATION);
		mapping.getStudentModel().addRelationship(association);

		return transformation;
	}

	private static Transformation executeRemoveAssociation(Mapping mapping, Transformation transformation) {
		Association association = (Association) TransformationUtils.getTransformableByRole(transformation, EnumTransformationRole.ASSOCIATION);
		mapping.getStudentModel().removeRelationship(association);

		return transformation;
	}

	private static Transformation executeCreateGeneralization(Mapping mapping, Transformation transformation) {
		Generalization generalization = (Generalization) TransformationUtils.getTransformableByRole(transformation, EnumTransformationRole.GENERALIZATION);
		mapping.getStudentModel().addRelationship(generalization);

		return transformation;
	}

	private static Transformation executeRemoveGeneralization(Mapping mapping, Transformation transformation) {
		Generalization generalization = (Generalization) TransformationUtils.getTransformableByRole(transformation, EnumTransformationRole.GENERALIZATION);
		mapping.getStudentModel().removeRelationship(generalization);

		return transformation;
	}

	private static Transformation executeCreateAttribute(Mapping mapping, Transformation transformation) {
		EntitySet entitySet = (EntitySet) TransformationUtils.getTransformableByRole(transformation, EnumTransformationRole.ENTITY_SET);
		Association association = (Association) TransformationUtils.getTransformableByRole(transformation, EnumTransformationRole.ASSOCIATION);
		Attribute attribute = (Attribute) TransformationUtils.getTransformableByRole(transformation, EnumTransformationRole.ATTRIBUTE);

		if (entitySet != null) {
			entitySet.addAttribute(attribute);
		} else if (association != null) {
			association.addAttribute(attribute);
		}

		return transformation;
	}

	private static Transformation executeRemoveAttribute(Mapping mapping, Transformation transformation) {
		EntitySet entitySet = (EntitySet) TransformationUtils.getTransformableByRole(transformation, EnumTransformationRole.ENTITY_SET);
		Association association = (Association) TransformationUtils.getTransformableByRole(transformation, EnumTransformationRole.ASSOCIATION);
		Attribute attribute = (Attribute) TransformationUtils.getTransformableByRole(transformation, EnumTransformationRole.ATTRIBUTE);

		if (entitySet != null) {
			entitySet.removeAttribute(attribute);
		} else if (association != null) {
			association.removeAttribute(attribute);
		}

		return transformation;
	}

	private static Transformation executeChangeCardinality(Mapping mapping, Transformation transformation) {
		AssociationSide associationSide = (AssociationSide) TransformationUtils.getTransformableByRole(transformation, EnumTransformationRole.ASSOCIATION_SIDE);
		TransformationUtils.flipCardinality(associationSide);

		return transformation;
	}

	private static Transformation executeRenameEntitySet(Mapping mapping, Transformation transformation) {
		EntitySet entitySet = (EntitySet) TransformationUtils.getTransformableByRole(transformation, EnumTransformationRole.ENTITY_SET);
		EntitySet targetEntitySet = (EntitySet) TransformationUtils.getTransformableByRole(transformation, EnumTransformationRole.ENTITY_SET_TARGET);

		entitySet.setName(targetEntitySet.getName());

		return transformation;
	}

	private static Transformation executeRenameAttribute(Mapping mapping, Transformation transformation) {
		return transformation;
	}

	private static Transformation executeRebindMNto1NN1(Mapping mapping, Transformation transformation) {
		Association association = (Association) TransformationUtils.getTransformableByRole(transformation, EnumTransformationRole.ASSOCIATION);
		Association association1 = (Association) TransformationUtils.getTransformableByRole(transformation, EnumTransformationRole.ASSOCIATION_1);
		Association association2 = (Association) TransformationUtils.getTransformableByRole(transformation, EnumTransformationRole.ASSOCIATION_2);
		EntitySet entitySet = (EntitySet) TransformationUtils.getTransformableByRole(transformation, EnumTransformationRole.ENTITY_SET);

		if (entitySet == null) {
			entitySet = new EntitySet(association.getName());
			entitySet.setAttributes(association.getAttributes());
		}
		if (association1 == null) {
			association1 = new Association(Arrays.asList(new AssociationSide(entitySet, Enums.CARDINALITY_MANY), new AssociationSide(association.getFirstSide().getEntitySet(), Enums.CARDINALITY_ONE)), null);
		}
		if (association2 == null)

		{
			association2 = new Association(Arrays.asList(new AssociationSide(entitySet, Enums.CARDINALITY_MANY), new AssociationSide(association.getSecondSide().getEntitySet(), Enums.CARDINALITY_ONE)), null);
		}

		ERModel studentModel = mapping.getStudentModel();
		studentModel.removeRelationship(association);
		studentModel.addEntitySet(entitySet);
		studentModel.addRelationship(association1);
		studentModel.addRelationship(association2);

		transformation.clearArguments();
		transformation.addArgument(association, EnumTransformationRole.ASSOCIATION);
		transformation.addArgument(association1, EnumTransformationRole.ASSOCIATION_1);
		transformation.addArgument(association2, EnumTransformationRole.ASSOCIATION_2);
		transformation.addArgument(entitySet, EnumTransformationRole.ENTITY_SET);

		TransformationUtils.overwriteTransformationFlag(EnumTransformation.REBIND_MN_TO_1NN1, entitySet);
		TransformationUtils.overwriteTransformationFlag(EnumTransformation.REBIND_MN_TO_1NN1, association);

		return transformation;
	}

	private static Transformation executeRebind1NN1toMN(Mapping mapping, Transformation transformation) {
		Association association = (Association) TransformationUtils.getTransformableByRole(transformation, EnumTransformationRole.ASSOCIATION);
		Association association1 = (Association) TransformationUtils.getTransformableByRole(transformation, EnumTransformationRole.ASSOCIATION_1);
		Association association2 = (Association) TransformationUtils.getTransformableByRole(transformation, EnumTransformationRole.ASSOCIATION_2);
		EntitySet entitySet = (EntitySet) TransformationUtils.getTransformableByRole(transformation, EnumTransformationRole.ENTITY_SET);

		if (association == null) {
			association = new Association(Arrays.asList(new AssociationSide(RelationshipUtils.getOtherEntitySet(association1, entitySet), Enums.CARDINALITY_MANY), new AssociationSide(RelationshipUtils.getOtherEntitySet(association2, entitySet), Enums.CARDINALITY_MANY)));
			association.setAttributes(entitySet.getAttributes());
		}
		association.setName(entitySet.getName());

		ERModel studentModel = mapping.getStudentModel();
		studentModel.removeRelationship(association1);
		studentModel.removeRelationship(association2);
		studentModel.removeEntitySet(entitySet);
		studentModel.addRelationship(association);

		transformation.clearArguments();
		transformation.addArgument(association, EnumTransformationRole.ASSOCIATION);
		transformation.addArgument(entitySet, EnumTransformationRole.ENTITY_SET);
		transformation.addArgument(association1, EnumTransformationRole.ASSOCIATION_1);
		transformation.addArgument(association2, EnumTransformationRole.ASSOCIATION_2);

		TransformationUtils.overwriteTransformationFlag(EnumTransformation.REBIND_1NN1_TO_MN, entitySet);
		TransformationUtils.overwriteTransformationFlag(EnumTransformation.REBIND_1NN1_TO_MN, association);

		return transformation;
	}

	private static Transformation executeMoveAttributeToIncidentEntitySet(Mapping mapping, Transformation transformation) {
		Attribute attribute = (Attribute) TransformationUtils.getTransformableByRole(transformation, EnumTransformationRole.ATTRIBUTE);
		Association association = (Association) TransformationUtils.getTransformableByRole(transformation, EnumTransformationRole.ASSOCIATION);
		EntitySet entitySet = (EntitySet) TransformationUtils.getTransformableByRole(transformation, EnumTransformationRole.ENTITY_SET);

		association.removeAttribute(attribute);
		entitySet.addAttribute(attribute);

		TransformationUtils.overwriteTransformationFlag(EnumTransformation.MOVE_ATTR_TO_INCIDENT_ENTITY_SET, attribute);

		return transformation;
	}

	private static Transformation executeMoveAttributeToIncidentAssociation(Mapping mapping, Transformation transformation) {
		Attribute attribute = (Attribute) TransformationUtils.getTransformableByRole(transformation, EnumTransformationRole.ATTRIBUTE);
		Association association = (Association) TransformationUtils.getTransformableByRole(transformation, EnumTransformationRole.ASSOCIATION);
		EntitySet entitySet = (EntitySet) TransformationUtils.getTransformableByRole(transformation, EnumTransformationRole.ENTITY_SET);

		association.addAttribute(attribute);
		entitySet.removeAttribute(attribute);

		TransformationUtils.overwriteTransformationFlag(EnumTransformation.MOVE_ATTR_TO_INCIDENT_ASSOCIATION, attribute);

		return transformation;
	}

	private static Transformation executeGeneralizationTo11Association(Mapping mapping, Transformation transformation) {
		Generalization generalization = (Generalization) TransformationUtils.getTransformableByRole(transformation, EnumTransformationRole.GENERALIZATION);

		Association association = new Association(Arrays.asList(new AssociationSide(generalization.getFirstSide().getEntitySet(), Enums.CARDINALITY_ONE), new AssociationSide(generalization.getSecondSide().getEntitySet(), Enums.CARDINALITY_ONE)), null);

		mapping.getStudentModel().removeRelationship(generalization);
		mapping.getStudentModel().addRelationship(association);

		transformation.clearArguments();
		transformation.addArgument(association, EnumTransformationRole.ASSOCIATION);
		transformation.addArgument(generalization, EnumTransformationRole.GENERALIZATION);

		return transformation;
	}

	private static Transformation executeExtractAttributeToOwnEntitySet(Mapping mapping, Transformation transformation) {
		Attribute attribute = (Attribute) TransformationUtils.getTransformableByRole(transformation, EnumTransformationRole.ATTRIBUTE);
		EntitySet sourceEntitySet = (EntitySet) TransformationUtils.getTransformableByRole(transformation, EnumTransformationRole.SOURCE_ENTITY_SET);
		EntitySet targetEntitySet = (EntitySet) TransformationUtils.getTransformableByRole(transformation, EnumTransformationRole.DEST_ENTITY_SET);

		sourceEntitySet.removeAttribute(attribute);

		if (targetEntitySet == null) {
			targetEntitySet = new EntitySet(attribute.getAttribute(), new ArrayList<>(Arrays.asList(Enums.NAME_ATTRIBUTE)));
			mapping.getStudentModel().addEntitySet(targetEntitySet);
		} else {
			if (!targetEntitySet.getAttributes().contains(attribute)) {
				targetEntitySet.addAttribute(attribute);
			}
		}
		mapping.getStudentModel().addRelationship(new Association(Arrays.asList(new AssociationSide(sourceEntitySet, Enums.CARDINALITY_MANY), new AssociationSide(targetEntitySet, Enums.CARDINALITY_ONE)), null));

		transformation.clearArguments();
		transformation.addArgument(sourceEntitySet, EnumTransformationRole.DEST_ENTITY_SET);
		transformation.addArgument(targetEntitySet, EnumTransformationRole.SOURCE_ENTITY_SET);
		transformation.addArgument(attribute, EnumTransformationRole.ATTRIBUTE);

		return transformation;
	}

	private static Transformation executeContract11Association(Mapping mapping, Transformation transformation) {
		Association association = (Association) TransformationUtils.getTransformableByRole(transformation, EnumTransformationRole.ASSOCIATION);
		TransformableFlag flag = (TransformableFlag) TransformationUtils.getTransformableByRole(transformation, EnumTransformationRole.EXEMPLAR_MODEL_FLAG);

		EntitySet entitySet1 = association.getFirstSide().getEntitySet();
		EntitySet entitySet2 = association.getSecondSide().getEntitySet();

		if (flag != null)
			mapping.getExemplarModel().removeRelationship(association);
		else
			mapping.getStudentModel().removeRelationship(association);
		entitySet1.getAttributes().addAll(entitySet2.getAttributes());
		entitySet1.getAttributes().addAll(association.getAttributes());
		entitySet1.setName(entitySet1.getName() + Enums.ENTITY_SETS_DELIMITER + entitySet2.getName());

		TransformableList transformableList = new TransformableList();
		transformableList.getElements().addAll(entitySet2.getIncidentRelationships());
		transformableList.getElements().remove(association);

		for (Relationship relationship : entitySet2.getIncidentRelationships()) {
			RelationshipUtils.rebindEntitySets(relationship, entitySet2, entitySet1);
		}

		if (flag != null)
			mapping.getExemplarModel().removeEntitySet(entitySet2);
		else
			mapping.getStudentModel().removeEntitySet(entitySet2);

		transformation.clearArguments();
		transformation.addArgument(entitySet1, EnumTransformationRole.ENTITY_SET);
		transformation.addArgument(transformableList, EnumTransformationRole.TRANSFORMABLE_LIST);
		if (flag != null) {
			transformation.addArgument(flag, EnumTransformationRole.EXEMPLAR_MODEL_FLAG);
		}
		transformation.addArgument(association, EnumTransformationRole.ASSOCIATION);

		return transformation;
	}

	private static Transformation executeRebindNaryAssociation(Mapping mapping, Transformation transformation) {
		Association association = (Association) TransformationUtils.getTransformableByRole(transformation, EnumTransformationRole.ASSOCIATION);
		TransformableFlag flag = (TransformableFlag) TransformationUtils.getTransformableByRole(transformation, EnumTransformationRole.EXEMPLAR_MODEL_FLAG);
		EntitySet entitySet = (EntitySet) TransformationUtils.getTransformableByRole(transformation, EnumTransformationRole.ENTITY_SET);

		ERModel model;
		if (flag != null) {
			model = mapping.getExemplarModel();
		} else {
			model = mapping.getStudentModel();
		}

		model.removeRelationship(association);

		if (entitySet == null) {
			entitySet = new EntitySet(association.getName());
			entitySet.setAttributes(association.getAttributes());
		}
		model.addEntitySet(entitySet);

		for (AssociationSide side : association.getSides()) {
			model.addRelationship(new Association(Arrays.asList(new AssociationSide(entitySet, Enums.CARDINALITY_MANY), new AssociationSide(side.getEntitySet(), Enums.CARDINALITY_ONE)), null));
		}

		transformation.clearArguments();
		transformation.addArgument(association, EnumTransformationRole.ASSOCIATION);
		transformation.addArgument(entitySet, EnumTransformationRole.ENTITY_SET);
		if (flag != null) {
			transformation.addArgument(flag, EnumTransformationRole.EXEMPLAR_MODEL_FLAG);
		}

		return transformation;
	}

	private static Transformation executeMergeAttributeFromOwnEntitySet(Mapping mapping, Transformation transformation) {
		Attribute attribute = (Attribute) TransformationUtils.getTransformableByRole(transformation, EnumTransformationRole.ATTRIBUTE);
		EntitySet destEntitySet = (EntitySet) TransformationUtils.getTransformableByRole(transformation, EnumTransformationRole.DEST_ENTITY_SET);
		EntitySet sourceEntitySet = (EntitySet) TransformationUtils.getTransformableByRole(transformation, EnumTransformationRole.SOURCE_ENTITY_SET);

		transformation.clearArguments();
		transformation.addArgument(sourceEntitySet, EnumTransformationRole.DEST_ENTITY_SET);
		transformation.addArgument(attribute, EnumTransformationRole.ATTRIBUTE);
		transformation.addArgument(destEntitySet, EnumTransformationRole.SOURCE_ENTITY_SET);

		assert sourceEntitySet.getNeighbours().keySet().size() > 0;
		if (sourceEntitySet.getNeighbours().keySet().size() > 1) {
			assert sourceEntitySet.getNeighbours().get(sourceEntitySet).size() == 1;
			mapping.getStudentModel().removeRelationship(sourceEntitySet.getNeighbours().get(destEntitySet).get(0));
		} else {
			mapping.getStudentModel().removeEntitySet(sourceEntitySet);
			transformation.removeArgument(sourceEntitySet);
		}
		destEntitySet.addAttribute(attribute);

		return transformation;
	}

	private static Transformation execute11AssociationToGeneralization(Mapping mapping, Transformation transformation) {
		Association association = (Association) TransformationUtils.getTransformableByRole(transformation, EnumTransformationRole.ASSOCIATION);
		Generalization generalization = (Generalization) TransformationUtils.getTransformableByRole(transformation, EnumTransformationRole.GENERALIZATION);

		mapping.getStudentModel().removeRelationship(association);
		mapping.getStudentModel().addRelationship(generalization);

		transformation.clearArguments();
		transformation.addArgument(generalization, EnumTransformationRole.GENERALIZATION);
		transformation.addArgument(association, EnumTransformationRole.ASSOCIATION);

		return transformation;
	}

	private static Transformation executeUncontract11Association(Mapping mapping, Transformation transformation) {
		EntitySet entitySet = (EntitySet) TransformationUtils.getTransformableByRole(transformation, EnumTransformationRole.ENTITY_SET);
		Association association = (Association) TransformationUtils.getTransformableByRole(transformation, EnumTransformationRole.ASSOCIATION);
		TransformableList transformableList = (TransformableList) TransformationUtils.getTransformableByRole(transformation, EnumTransformationRole.TRANSFORMABLE_LIST);
		TransformableFlag flag = (TransformableFlag) TransformationUtils.getTransformableByRole(transformation, EnumTransformationRole.EXEMPLAR_MODEL_FLAG);

		EntitySet otherEntitySet = RelationshipUtils.getOtherEntitySet(association, entitySet);
		entitySet.getAttributes().removeAll(otherEntitySet.getAttributes());
		entitySet.setName(StringUtils.getNamePart(entitySet.getName(), 0));

		if (flag != null)
			mapping.getExemplarModel().addEntitySet(otherEntitySet);
		else
			mapping.getStudentModel().addEntitySet(otherEntitySet);

		for (Transformable transformable : transformableList.getElements()) {
			RelationshipUtils.rebindEntitySets((Relationship) transformable, entitySet, otherEntitySet);
		}

		if (flag != null) {
			mapping.getExemplarModel().addRelationship(association);
		} else {
			mapping.getStudentModel().addRelationship(association);
		}

		transformation.clearArguments();
		transformation.addArgument(association, EnumTransformationRole.ASSOCIATION);
		if (flag != null) {
			transformation.addArgument(flag, EnumTransformationRole.EXEMPLAR_MODEL_FLAG);
		}

		return transformation;
	}

	private static Transformation executeBindToNaryAssociation(Mapping mapping, Transformation transformation) {
		EntitySet entitySet = (EntitySet) TransformationUtils.getTransformableByRole(transformation, EnumTransformationRole.ENTITY_SET);
		Association association = (Association) TransformationUtils.getTransformableByRole(transformation, EnumTransformationRole.ASSOCIATION);
		TransformableFlag flag = (TransformableFlag) TransformationUtils.getTransformableByRole(transformation, EnumTransformationRole.EXEMPLAR_MODEL_FLAG);

		ERModel model;
		if (flag != null) {
			model = mapping.getExemplarModel();
		} else {
			model = mapping.getStudentModel();
		}

		model.removeEntitySet(entitySet);
		model.addRelationship(association);

		transformation.clearArguments();
		transformation.addArgument(entitySet, EnumTransformationRole.ENTITY_SET);
		transformation.addArgument(association, EnumTransformationRole.ASSOCIATION);
		if (flag != null) {
			transformation.addArgument(flag, EnumTransformationRole.EXEMPLAR_MODEL_FLAG);
		}

		return transformation;
	}
}
