package transformations;

import java.util.ArrayList;
import java.util.Arrays;

import common.RelationshipUtils;
import common.StringUtils;
import common.TransformationUtils;
import common.Utils;
import common.enums.Enums;
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
		transformation.setCode(getRevertingTransformation(transformation));
		return execute(mapping, transformation);
	}

	public static String getRevertingTransformation(Transformation transformation) {
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
		Association association1 = new Association(new AssociationSide[] { new AssociationSide(entitySet, Enums.CARDINALITY_MANY), new AssociationSide(association.getFirstSide().getEntitySet(), Enums.CARDINALITY_ONE) }, null);
		Association association2 = new Association(new AssociationSide[] { new AssociationSide(entitySet, Enums.CARDINALITY_MANY), new AssociationSide(association.getSecondSide().getEntitySet(), Enums.CARDINALITY_ONE) }, null);

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

		Association association = new Association(new AssociationSide[] { new AssociationSide(RelationshipUtils.getOtherEntitySet(association1, entitySet), Enums.CARDINALITY_MANY), new AssociationSide(RelationshipUtils.getOtherEntitySet(association2, entitySet), Enums.CARDINALITY_MANY) }, entitySet.getAttributes());

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

		Association association = new Association(new AssociationSide[] { new AssociationSide(generalization.getFirstSide().getEntitySet(), Enums.CARDINALITY_ONE), new AssociationSide(generalization.getSecondSide().getEntitySet(), Enums.CARDINALITY_ONE) }, null);

		mapping.getStudentModel().removeRelationship(generalization);
		mapping.getStudentModel().addRelationship(association);

		association.setTransformationRole(EnumTransformationRole.ASSOCIATION);
		transformation.getArguments().clear();
		transformation.getArguments().add(association);
		transformation.getArguments().add(generalization);

		return transformation;
	}

	private static Transformation executeExtractAttributeToOwnEntitySet(Mapping mapping, Transformation transformation) {
		TransformableAttribute attribute = (TransformableAttribute) TransformationUtils.getTransformableByRole(transformation.getArguments(), EnumTransformationRole.ATTRIBUTE);
		EntitySet sourceEntitySet = (EntitySet) TransformationUtils.getTransformableByRole(transformation.getArguments(), EnumTransformationRole.SOURCE_ENTITY_SET);
		EntitySet targetEntitySet = (EntitySet) TransformationUtils.getTransformableByRole(transformation.getArguments(), EnumTransformationRole.DEST_ENTITY_SET);

		sourceEntitySet.removeAttribute(attribute.getAttribute());

		if (targetEntitySet == null) {
			targetEntitySet = new EntitySet(attribute.getAttribute(), new ArrayList<>(Arrays.asList(Enums.NAME_ATTRIBUTE)));
			transformation.getArguments().add(targetEntitySet);
			mapping.getStudentModel().addEntitySet(targetEntitySet);
		} else {
			if (!targetEntitySet.getAttributes().contains(attribute.getAttribute())) {
				targetEntitySet.addAttribute(attribute.getAttribute());
			}
		}
		mapping.getStudentModel().addRelationship(new Association(new AssociationSide[] { new AssociationSide(sourceEntitySet, Enums.CARDINALITY_MANY), new AssociationSide(targetEntitySet, Enums.CARDINALITY_ONE) }, null));
		sourceEntitySet.setTransformationRole(EnumTransformationRole.DEST_ENTITY_SET);
		targetEntitySet.setTransformationRole(EnumTransformationRole.SOURCE_ENTITY_SET);

		return transformation;
	}

	private static Transformation executeContract11Association(Mapping mapping, Transformation transformation) {
		Association association = (Association) TransformationUtils.getTransformableByRole(transformation.getArguments(), EnumTransformationRole.ASSOCIATION);
		TransformableFlag flag = (TransformableFlag) TransformationUtils.getTransformableByRole(transformation.getArguments(), EnumTransformationRole.EXEMPLAR_MODEL_FLAG);

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

		entitySet1.setTransformationRole(EnumTransformationRole.ENTITY_SET);
		transformableList.setTransformationRole(EnumTransformationRole.TRANSFORMABLE_LIST);
		transformation.getArguments().add(entitySet1);
		transformation.getArguments().add(transformableList);

		return transformation;
	}

	private static Transformation executeRebindNaryAssociation(Mapping mapping, Transformation transformation) {
		Association association = (Association) TransformationUtils.getTransformableByRole(transformation.getArguments(), EnumTransformationRole.ASSOCIATION);

		mapping.getStudentModel().removeRelationship(association);

		EntitySet entitySet = new EntitySet(association.getName(), association.getAttributes());
		mapping.getStudentModel().addEntitySet(entitySet);

		for (AssociationSide side : association.getSides()) {
			mapping.getStudentModel().addRelationship(new Association(new AssociationSide[] { new AssociationSide(entitySet, Enums.CARDINALITY_MANY), new AssociationSide(side.getEntitySet(), Enums.CARDINALITY_ONE) }, null));
		}

		entitySet.setTransformationRole(EnumTransformationRole.ENTITY_SET);
		transformation.getArguments().add(entitySet);

		return transformation;
	}

	private static Transformation executeMergeAttributeFromOwnEntitySet(Mapping mapping, Transformation transformation) {
		TransformableAttribute attribute = (TransformableAttribute) TransformationUtils.getTransformableByRole(transformation.getArguments(), EnumTransformationRole.ATTRIBUTE);
		EntitySet destEntitySet = (EntitySet) TransformationUtils.getTransformableByRole(transformation.getArguments(), EnumTransformationRole.DEST_ENTITY_SET);
		EntitySet sourceEntitySet = (EntitySet) TransformationUtils.getTransformableByRole(transformation.getArguments(), EnumTransformationRole.SOURCE_ENTITY_SET);

		assert sourceEntitySet.getNeighbours().keySet().size() > 0;
		if (sourceEntitySet.getNeighbours().keySet().size() > 1) {
			assert sourceEntitySet.getNeighbours().get(sourceEntitySet).size() == 1;
			mapping.getStudentModel().removeRelationship(sourceEntitySet.getNeighbours().get(destEntitySet).get(0));
		} else {
			mapping.getStudentModel().removeEntitySet(sourceEntitySet);
		}
		destEntitySet.addAttribute(attribute.getAttribute());

		sourceEntitySet.setTransformationRole(EnumTransformationRole.SOURCE_ENTITY_SET);
		destEntitySet.setTransformationRole(EnumTransformationRole.DEST_ENTITY_SET);
		return transformation;
	}

	private static Transformation execute11AssociationToGeneralization(Mapping mapping, Transformation transformation) {
		Association association = (Association) TransformationUtils.getTransformableByRole(transformation.getArguments(), EnumTransformationRole.ASSOCIATION);
		Generalization generalization = (Generalization) TransformationUtils.getTransformableByRole(transformation.getArguments(), EnumTransformationRole.GENERALIZATION);

		mapping.getStudentModel().removeRelationship(association);
		mapping.getStudentModel().addRelationship(generalization);

		transformation.getArguments().clear();
		transformation.getArguments().add(generalization);
		return transformation;
	}

	private static Transformation executeUncontract11Association(Mapping mapping, Transformation transformation) {
		EntitySet entitySet = (EntitySet) TransformationUtils.getTransformableByRole(transformation.getArguments(), EnumTransformationRole.ENTITY_SET);
		Association association = (Association) TransformationUtils.getTransformableByRole(transformation.getArguments(), EnumTransformationRole.ASSOCIATION);
		TransformableList transformableList = (TransformableList) TransformationUtils.getTransformableByRole(transformation.getArguments(), EnumTransformationRole.TRANSFORMABLE_LIST);
		TransformableFlag flag = (TransformableFlag) TransformationUtils.getTransformableByRole(transformation.getArguments(), EnumTransformationRole.EXEMPLAR_MODEL_FLAG);

		EntitySet otherEntitySet = RelationshipUtils.getOtherEntitySet(association, entitySet);
		entitySet.getAttributes().removeAll(otherEntitySet.getAttributes());
		entitySet.setName(StringUtils.getFirstNamePart(entitySet.getName()));

		if (flag != null)
			mapping.getExemplarModel().addEntitySet(otherEntitySet);
		else
			mapping.getStudentModel().addEntitySet(otherEntitySet);

		for (Transformable transformable : transformableList.getElements()) {
			RelationshipUtils.rebindEntitySets((Relationship) transformable, entitySet, otherEntitySet);
		}

		if (flag != null)
			mapping.getExemplarModel().addRelationship(association);
		else
			mapping.getStudentModel().addRelationship(association);

		transformation.getArguments().clear();
		transformation.getArguments().add(entitySet);
		transformation.getArguments().add(flag);

		return transformation;
	}

	private static Transformation executeBindToNaryAssociation(Mapping mapping, Transformation transformation) {
		EntitySet entitySet = (EntitySet) TransformationUtils.getTransformableByRole(transformation.getArguments(), EnumTransformationRole.ENTITY_SET);
		Association association = (Association) TransformationUtils.getTransformableByRole(transformation.getArguments(), EnumTransformationRole.ASSOCIATION);

		mapping.getStudentModel().removeEntitySet(entitySet);
		mapping.getStudentModel().addRelationship(association);

		transformation.getArguments().clear();
		transformation.getArguments().add(association);
		return transformation;
	}
}
