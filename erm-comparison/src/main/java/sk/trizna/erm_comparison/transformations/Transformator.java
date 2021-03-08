package sk.trizna.erm_comparison.transformations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import sk.trizna.erm_comparison.common.enums.EnumConstants;
import sk.trizna.erm_comparison.common.enums.EnumRelationshipSideRole;
import sk.trizna.erm_comparison.common.enums.EnumTransformation;
import sk.trizna.erm_comparison.common.enums.EnumTransformationRole;
import sk.trizna.erm_comparison.common.utils.CollectionUtils;
import sk.trizna.erm_comparison.common.utils.ERModelUtils;
import sk.trizna.erm_comparison.common.utils.PrintUtils;
import sk.trizna.erm_comparison.common.utils.RelationshipUtils;
import sk.trizna.erm_comparison.common.utils.StringUtils;
import sk.trizna.erm_comparison.common.utils.TransformationUtils;
import sk.trizna.erm_comparison.common.utils.Utils;
import sk.trizna.erm_comparison.comparing.ERTextComparator;
import sk.trizna.erm_comparison.comparing.mapping.Mapping;
import sk.trizna.erm_comparison.entity_relationship_model.Association;
import sk.trizna.erm_comparison.entity_relationship_model.AssociationSide;
import sk.trizna.erm_comparison.entity_relationship_model.Attribute;
import sk.trizna.erm_comparison.entity_relationship_model.Attributed;
import sk.trizna.erm_comparison.entity_relationship_model.ERModel;
import sk.trizna.erm_comparison.entity_relationship_model.EntitySet;
import sk.trizna.erm_comparison.entity_relationship_model.Generalization;
import sk.trizna.erm_comparison.entity_relationship_model.Named;
import sk.trizna.erm_comparison.entity_relationship_model.Relationship;
import sk.trizna.erm_comparison.entity_relationship_model.RelationshipSide;
import sk.trizna.erm_comparison.entity_relationship_model.TransformableFlag;
import sk.trizna.erm_comparison.entity_relationship_model.TransformableList;
import sk.trizna.erm_comparison.entity_relationship_model.TransformableMap;

public class Transformator {
	
	/**
	 * Executes transformation by given transformation code and arguments. Returns
	 * the arguments in transformed form.
	 */
	public static Transformation execute(Mapping mapping, Transformation transformation) {
		TransformatorValidator.getInstance().preValidate(transformation);
		transformation = executeInternal(mapping, transformation);
		TransformatorValidator.getInstance().postValidate(transformation);
		
		return transformation;
	}
	
	private static Transformation executeInternal(Mapping mapping, Transformation transformation) {
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
		if (EnumTransformation.DECOMPOSE_ATTRIBUTE.equals(transformation.getCode())) {
			return executeDecomposeAttribute(mapping, transformation);
		}
		if (EnumTransformation.COMPOSE_ATTRIBUTE.equals(transformation.getCode())) {
			return executeComposeAttribute(mapping, transformation);
		}
		if (EnumTransformation.MERGE_ENTITY_SETS.equals(transformation.getCode())) {
			return executeMergeEntitySets(mapping, transformation);
		}
		if (EnumTransformation.SPLIT_ENTITY_SETS.equals(transformation.getCode())) {
			return executeSplitEntitySets(mapping, transformation);
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

	public static EnumTransformation getRevertingTransformation(EnumTransformation code) {
		Utils.validateNotNull(code);

		switch (code) {
		case MOVE_ATTR_TO_INCIDENT_ASSOCIATION:
			return EnumTransformation.MOVE_ATTR_TO_INCIDENT_ENTITY_SET;
		case MOVE_ATTR_TO_INCIDENT_ENTITY_SET:
			return EnumTransformation.MOVE_ATTR_TO_INCIDENT_ASSOCIATION;
		case REBIND_MN_TO_1NN1:
			return EnumTransformation.REBIND_1NN1_TO_MN;
		case REBIND_1NN1_TO_MN:
			return EnumTransformation.REBIND_MN_TO_1NN1;
		case EXTRACT_ATTR_TO_OWN_ENTITY_SET:
			return EnumTransformation.MERGE_ATTR_FROM_OWN_ENTITY_SET;
		case MERGE_ATTR_FROM_OWN_ENTITY_SET:
			return EnumTransformation.EXTRACT_ATTR_TO_OWN_ENTITY_SET;
		case GENERALIZATION_TO_11_ASSOCIATION:
			return EnumTransformation._11_ASSOCIATION_TO_GENERALIZATION;
		case _11_ASSOCIATION_TO_GENERALIZATION:
			return EnumTransformation.GENERALIZATION_TO_11_ASSOCIATION;
		case CONTRACT_11_ASSOCIATION:
			return EnumTransformation.UNCONTRACT_11_ASSOCIATION;
		case UNCONTRACT_11_ASSOCIATION:
			return EnumTransformation.CONTRACT_11_ASSOCIATION;
		case REBIND_NARY_ASSOCIATION:
			return EnumTransformation.BIND_TO_NARY_ASSOCIATION;
		case BIND_TO_NARY_ASSOCIATION:
			return EnumTransformation.REBIND_NARY_ASSOCIATION;
		case DECOMPOSE_ATTRIBUTE:
			return EnumTransformation.COMPOSE_ATTRIBUTE;
		case COMPOSE_ATTRIBUTE:
			return EnumTransformation.DECOMPOSE_ATTRIBUTE;
		case MERGE_ENTITY_SETS:
			return EnumTransformation.SPLIT_ENTITY_SETS;
		case SPLIT_ENTITY_SETS:
			return EnumTransformation.MERGE_ENTITY_SETS;
		case CHANGE_CARDINALITY:
			return EnumTransformation.CHANGE_CARDINALITY;
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
		EntitySet entitySet = (EntitySet) TransformationUtils.getTransformableByRole(transformation, EnumTransformationRole.ENTITY_SET);
		Association association = (Association) TransformationUtils.getTransformableByRole(transformation, EnumTransformationRole.ASSOCIATION);

		AssociationSide side = RelationshipUtils.getAssociationSide(association, entitySet);
		TransformationUtils.flipCardinality(side);
		TransformationUtils.overwriteTransformationFlag(EnumTransformation.CHANGE_CARDINALITY, side);

		return transformation;
	}

	private static Transformation executeRenameEntitySet(Mapping mapping, Transformation transformation) {
		EntitySet entitySet = (EntitySet) TransformationUtils.getTransformableByRole(transformation, EnumTransformationRole.ENTITY_SET);
		EntitySet targetEntitySet = (EntitySet) TransformationUtils.getTransformableByRole(transformation, EnumTransformationRole.ENTITY_SET_TARGET);

		entitySet.setNameText(targetEntitySet.getNameText());

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
			entitySet = new EntitySet(association.getNameText());
			entitySet.setAttributes(association.getAttributes());
		}
		if (association1 == null) {
			association1 = new Association(Arrays.asList(new AssociationSide(entitySet, EnumRelationshipSideRole.MANY), new AssociationSide(association.getFirstSide().getEntitySet(), EnumRelationshipSideRole.ONE)), null);
		}
		if (association2 == null)

		{
			association2 = new Association(Arrays.asList(new AssociationSide(entitySet, EnumRelationshipSideRole.MANY), new AssociationSide(association.getSecondSide().getEntitySet(), EnumRelationshipSideRole.ONE)), null);
		}

		ERModel studentModel = mapping.getStudentModel();
		studentModel.removeRelationship(association);
		studentModel.addEntitySet(entitySet);
		studentModel.addRelationship(association1);
		studentModel.addRelationship(association2);

		transformation.clearArguments();
		if (association != null) transformation.addArgument(association, EnumTransformationRole.ASSOCIATION);
		if (association1 != null) transformation.addArgument(association1, EnumTransformationRole.ASSOCIATION_1);
		if (association2 != null) transformation.addArgument(association2, EnumTransformationRole.ASSOCIATION_2);
		if (entitySet != null) transformation.addArgument(entitySet, EnumTransformationRole.ENTITY_SET);

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
			association = new Association(Arrays.asList(new AssociationSide(RelationshipUtils.getOtherEntitySet(association1, entitySet), EnumRelationshipSideRole.MANY), new AssociationSide(RelationshipUtils.getOtherEntitySet(association2, entitySet), EnumRelationshipSideRole.MANY)));
			association.setAttributes(entitySet.getAttributes());
		}
		association.setNameText(entitySet.getNameText());

		ERModel studentModel = mapping.getStudentModel();
		studentModel.removeRelationship(association1);
		studentModel.removeRelationship(association2);
		studentModel.removeEntitySet(entitySet);
		studentModel.addRelationship(association);

		transformation.clearArguments();
		if (association != null) transformation.addArgument(association, EnumTransformationRole.ASSOCIATION);
		if (entitySet != null) transformation.addArgument(entitySet, EnumTransformationRole.ENTITY_SET);
		if (association1 != null) transformation.addArgument(association1, EnumTransformationRole.ASSOCIATION_1);
		if (association2 != null) transformation.addArgument(association2, EnumTransformationRole.ASSOCIATION_2);

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

		Association association = new Association(Arrays.asList(new AssociationSide(generalization.getFirstSide().getEntitySet(), EnumRelationshipSideRole.ONE), new AssociationSide(generalization.getSecondSide().getEntitySet(), EnumRelationshipSideRole.ONE)), null);

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

		Utils.validateContains(mapping.getStudentModel(),sourceEntitySet);
		Utils.validateContains(sourceEntitySet, attribute);
		sourceEntitySet.removeAttribute(attribute);
		sourceEntitySet.addTransformationFlag(EnumTransformation.EXTRACT_ATTR_TO_OWN_ENTITY_SET);
	
		if (targetEntitySet == null) {
			// if no targetEntitySet was sent, try to find it - multiple extract transformations may be analyzed in the same backtrack node, 
			// which could mean that targeEntitySet already exists, but didn't exist at the time of analysis.
			targetEntitySet = ERModelUtils.getEntitySetByName(mapping.getStudentModel(), attribute.getText());			
			if (targetEntitySet == null || targetEntitySet == sourceEntitySet) {
				// if none was found, create new
				targetEntitySet = new EntitySet(attribute.getText(), new ArrayList<>(Arrays.asList(EnumConstants.NAME_ATTRIBUTE)));
				targetEntitySet.addTransformationFlag(EnumTransformation.EXTRACT_ATTR_TO_OWN_ENTITY_SET, new TransformableList(Arrays.asList(attribute)));
				mapping.getStudentModel().addEntitySet(targetEntitySet);
			}
		} else {
			if (!targetEntitySet.getAttributes().contains(attribute) && !StringUtils.areEqual(targetEntitySet.getNameText(),attribute.getText())) {
				targetEntitySet.addAttribute(attribute);
			}
		}
		Relationship edge = null;
		if (!sourceEntitySet.isNeighbour(targetEntitySet)) {
			edge = new Association(Arrays.asList(new AssociationSide(sourceEntitySet, EnumRelationshipSideRole.MANY), new AssociationSide(targetEntitySet, EnumRelationshipSideRole.ONE)), null);
			mapping.getStudentModel().addRelationship(edge);
		}

		transformation.clearArguments();
		transformation.addArgument(sourceEntitySet, EnumTransformationRole.DEST_ENTITY_SET);
		transformation.addArgument(targetEntitySet, EnumTransformationRole.SOURCE_ENTITY_SET);
		transformation.addArgument(attribute, EnumTransformationRole.ATTRIBUTE);
		if (edge != null) transformation.addArgument(edge, EnumTransformationRole.ASSOCIATION);

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
		
		// compute duplicate attribute without merging
		List<Attribute> duplicateEntitySetAttributes = mergeAttributes(entitySet1, entitySet2, false);
		List<Attribute> duplicateAssociationAttributes = mergeAttributes(entitySet1, association, false);
		// merge attributes
		mergeAttributes(entitySet1, entitySet2, true);
		mergeAttributes(entitySet1, association, true);
		entitySet1.setNameText(TransformationUtils.createMergedName(new Named[] {entitySet1,entitySet2}));
		

		// send duplicate EntitySet attributes in transformableMap
		TransformableMap transformableMap = new TransformableMap();
		entitySet2.getIncidentRelationships().forEach(rel -> {
			transformableMap.getMap().put(rel, RelationshipUtils.getSide(rel, entitySet2));
		});
		transformableMap.getMap().remove(association);
		if (duplicateEntitySetAttributes != null) {
			duplicateEntitySetAttributes.forEach(attribute -> {
				transformableMap.getMap().put(attribute, null);
			});
		}
		
		// send duplicate Association attributes in separate transformableList
		TransformableList transformableList = new TransformableList();
		if (duplicateAssociationAttributes != null) {
			transformableList.getElements().addAll(duplicateAssociationAttributes);
		}
		
		for (Relationship relationship : entitySet2.getIncidentRelationships()) {
			RelationshipUtils.rebindEntitySets(relationship, entitySet2, entitySet1);
		}

		if (flag != null)
			mapping.getExemplarModel().removeEntitySet(entitySet2);
		else
			mapping.getStudentModel().removeEntitySet(entitySet2);

		transformation.clearArguments();
		transformation.addArgument(entitySet1, EnumTransformationRole.ENTITY_SET);
		transformation.addArgument(transformableMap, EnumTransformationRole.TRANSFORMABLE_MAP);
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
			entitySet = new EntitySet(TransformationUtils.getNameForNaryRebindEntitySet(association));
			entitySet.setAttributes(association.getAttributes());
		}
		model.addEntitySet(entitySet);

		for (AssociationSide side : association.getSides()) {
			model.addRelationship(new Association(Arrays.asList(new AssociationSide(entitySet, EnumRelationshipSideRole.MANY), new AssociationSide(side.getEntitySet(), EnumRelationshipSideRole.ONE)), null));
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
		Association edge = (Association) TransformationUtils.getTransformableByRole(transformation, EnumTransformationRole.ASSOCIATION);
		
		Utils.validateContains(mapping.getStudentModel(), sourceEntitySet);
		Utils.validateContains(mapping.getStudentModel(), destEntitySet);
		
		transformation.clearArguments();
		transformation.addArgument(sourceEntitySet, EnumTransformationRole.DEST_ENTITY_SET);
		transformation.addArgument(attribute, EnumTransformationRole.ATTRIBUTE);
		transformation.addArgument(destEntitySet, EnumTransformationRole.SOURCE_ENTITY_SET);

		destEntitySet.addAttribute(attribute);
		
		assert sourceEntitySet.getNeighbours().keySet().size() > 0;
		assert sourceEntitySet.getNeighbours().get(destEntitySet) != null;
		if (edge != null) {
			assert sourceEntitySet.getNeighbours().get(destEntitySet).size() > 0;
			assert sourceEntitySet.getNeighbours().get(destEntitySet).contains(edge);
			mapping.getStudentModel().removeRelationship(edge);
		}
		if (sourceEntitySet.getNeighbours().isEmpty()) {
			mapping.getStudentModel().removeEntitySet(sourceEntitySet);
			transformation.removeArgument(sourceEntitySet);
		}
		TransformationUtils.overwriteTransformationFlag(EnumTransformation.MERGE_ATTR_FROM_OWN_ENTITY_SET, destEntitySet);
		
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
		TransformableMap transformableMap = (TransformableMap) TransformationUtils.getTransformableByRole(transformation, EnumTransformationRole.TRANSFORMABLE_MAP);
		TransformableList transformableList = (TransformableList) TransformationUtils.getTransformableByRole(transformation, EnumTransformationRole.TRANSFORMABLE_LIST);
		TransformableFlag flag = (TransformableFlag) TransformationUtils.getTransformableByRole(transformation, EnumTransformationRole.EXEMPLAR_MODEL_FLAG);

		EntitySet otherEntitySet = RelationshipUtils.getOtherEntitySet(association, entitySet);
		
		splitAttributes(entitySet, otherEntitySet);
		splitAttributes(entitySet, association);
		
		entitySet.setNameText(StringUtils.decomposeName(entitySet.getNameText(), otherEntitySet.getNameText()));
		
		if (flag != null)
			mapping.getExemplarModel().addEntitySet(otherEntitySet);
		else
			mapping.getStudentModel().addEntitySet(otherEntitySet);

		for (Transformable transformable : transformableMap.getMap().keySet()) {
			if (transformable instanceof Relationship) {
				Transformable side = transformableMap.getMap().get(transformable);
				RelationshipUtils.rebindEntitySetsByOriginalSide((Relationship) transformable, side != null ? (RelationshipSide) side : null, entitySet, otherEntitySet);
			}
			if (transformable instanceof Attribute) {
				// add duplicate attribute (was removed from merged entitySet in splitAttributes)
				entitySet.addAttribute((Attribute) transformable);
			}
		}
		for (Transformable transformable : transformableList) {
			if (transformable instanceof Attribute) {
				// add duplicate attribute (was removed from merged entitySet in splitAttributes)
				entitySet.addAttribute((Attribute) transformable);
			}
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
	
	private static Transformation executeDecomposeAttribute(Mapping mapping, Transformation transformation) {
		EntitySet entitySet = (EntitySet) TransformationUtils.getTransformableByRole(transformation, EnumTransformationRole.ENTITY_SET);
		Attribute attribute = (Attribute) TransformationUtils.getTransformableByRole(transformation, EnumTransformationRole.ATTRIBUTE);
		TransformableList attributeList = (TransformableList) TransformationUtils.getTransformableByRole(transformation, EnumTransformationRole.TRANSFORMABLE_LIST);
		
		if (entitySet == null || attribute == null || attributeList == null) {
			return transformation;
		}
		Utils.validateContains(entitySet, attribute);
		
		entitySet.removeAttribute(attribute);
		attributeList.forEach(attr -> {
			if (attr instanceof Attribute) {
				entitySet.addAttribute((Attribute) attr);
			}
		});
		
		return transformation;
	}
	
	private static Transformation executeComposeAttribute(Mapping mapping, Transformation transformation) {
		EntitySet entitySet = (EntitySet) TransformationUtils.getTransformableByRole(transformation, EnumTransformationRole.ENTITY_SET);
		Attribute attribute = (Attribute) TransformationUtils.getTransformableByRole(transformation, EnumTransformationRole.ATTRIBUTE);
		TransformableList attributeList = (TransformableList) TransformationUtils.getTransformableByRole(transformation, EnumTransformationRole.TRANSFORMABLE_LIST);
		
		if (entitySet == null || attribute == null || attributeList == null) {
			return transformation;
		}
		attributeList.forEach(attr -> {
			Utils.validateContains(entitySet, (Attribute) attr);
		});
		
		attributeList.forEach(attr -> {
			if (attr instanceof Attribute) {
				entitySet.removeAttribute((Attribute) attr);
			}
		});
		entitySet.addAttribute(attribute);
		
		
		return transformation;
	}

	private static Transformation executeMergeEntitySets(Mapping mapping, Transformation transformation) {
		EntitySet entitySet1 = (EntitySet) TransformationUtils.getTransformableByRole(transformation, EnumTransformationRole.ENTITY_SET1);
		EntitySet entitySet2 = (EntitySet) TransformationUtils.getTransformableByRole(transformation, EnumTransformationRole.ENTITY_SET2);

		Utils.validateContains(mapping.getStudentModel(), entitySet1);
		Utils.validateContains(mapping.getStudentModel(), entitySet2);
		
		List<Attribute> duplicateAttributes = mergeAttributes(entitySet1, entitySet2, true);
		entitySet1.setNameText(entitySet1.getNameText() + PrintUtils.DELIMITER_SEMICOLON + entitySet2.getNameText());

		TransformableList transformableList = new TransformableList();
		transformableList.getElements().addAll(entitySet2.getIncidentRelationships());
		if (duplicateAttributes != null) transformableList.getElements().addAll(duplicateAttributes);

		for (Relationship relationship : entitySet2.getIncidentRelationships()) {
			RelationshipUtils.rebindEntitySets(relationship, entitySet2, entitySet1);
		}

		mapping.getStudentModel().removeEntitySet(entitySet2);

		transformation.clearArguments();
		transformation.addArgument(entitySet1, EnumTransformationRole.ENTITY_SET);
		transformation.addArgument(transformableList, EnumTransformationRole.TRANSFORMABLE_LIST);
		transformation.addArgument(entitySet2, EnumTransformationRole.ENTITY_SET2);

		return transformation;
	}
	
	private static Transformation executeSplitEntitySets(Mapping mapping, Transformation transformation) {
		EntitySet entitySet = (EntitySet) TransformationUtils.getTransformableByRole(transformation, EnumTransformationRole.ENTITY_SET);
		EntitySet entitySet2 = (EntitySet) TransformationUtils.getTransformableByRole(transformation, EnumTransformationRole.ENTITY_SET2);
		TransformableList transformableList = (TransformableList) TransformationUtils.getTransformableByRole(transformation, EnumTransformationRole.TRANSFORMABLE_LIST);

		Utils.validateContains(mapping.getStudentModel(), entitySet);
		Utils.validateNotContains(mapping.getStudentModel(), entitySet2);
		
		splitAttributes(entitySet,entitySet2);
		entitySet.setNameText(StringUtils.decomposeName(entitySet.getNameText(), entitySet2.getNameText()));
		
		mapping.getStudentModel().addEntitySet(entitySet2);

		for (Transformable transformable : transformableList.getElements()) {
			if (transformable instanceof Relationship) {
				RelationshipUtils.rebindEntitySets((Relationship) transformable, entitySet, entitySet2);
			} else if (transformable instanceof Attribute) {
				entitySet.addAttribute((Attribute)transformable);
			}
		}

		transformation.clearArguments();
		transformation.addArgument(entitySet, EnumTransformationRole.ENTITY_SET1);
		transformation.addArgument(entitySet2, EnumTransformationRole.ENTITY_SET2);
		
		return transformation;
	}
	
	/**
	 * 
	 * @param write = true means method only returns duplicate attributes, but doesn't merge any
	 */
	private static List<Attribute> mergeAttributes(Attributed dest, Attributed source, boolean write) {
		List<Attribute> duplicates = null;
		for (Attribute attribute : source.getAttributes()) {
			// avoid duplicate attributes in dest entitySet
			Attribute destAttribute = dest.getAttribute(attribute.getText());
			if (destAttribute != null) { 
				duplicates = Utils.addToList(duplicates, destAttribute);
				continue;
			}
			
			if (dest instanceof Transformable) {
				// don't merge attributes which were previously extracted from the dest entitySet
				TransformableList trList = ((Transformable)dest).getTransformationData(EnumTransformation.EXTRACT_ATTR_TO_OWN_ENTITY_SET);
				if (trList != null &&  CollectionUtils.containsByComparator(trList.getElements(), attribute, ERTextComparator.getInstance())) {
					continue;
				}
			}
			dest.addAttribute(attribute);
		}
		return duplicates;
	}
	
	private static void splitAttributes(Attributed merged, Attributed other) {
		for (Attribute attribute : other.getAttributes()) {
			Iterator<Attribute> it = merged.getAttributes().iterator();
			while(it.hasNext()) {
				if (ERModelUtils.areEqual(attribute, it.next())) {
					it.remove();
					break;
				}
			}
		}
	}
}
