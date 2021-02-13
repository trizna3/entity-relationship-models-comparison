package sk.trizna.erm_comparison.common;

import sk.trizna.erm_comparison.common.enums.EnumTransformation;
import sk.trizna.erm_comparison.common.enums.EnumTransformationRole;
import sk.trizna.erm_comparison.common.object_pools.TransformationPool;
import sk.trizna.erm_comparison.common.utils.Utils;
import sk.trizna.erm_comparison.entity_relationship_model.Association;
import sk.trizna.erm_comparison.entity_relationship_model.Attribute;
import sk.trizna.erm_comparison.entity_relationship_model.Attributed;
import sk.trizna.erm_comparison.entity_relationship_model.EntitySet;
import sk.trizna.erm_comparison.entity_relationship_model.Generalization;
import sk.trizna.erm_comparison.entity_relationship_model.Relationship;
import sk.trizna.erm_comparison.entity_relationship_model.TransformableFlag;
import sk.trizna.erm_comparison.entity_relationship_model.TransformableList;
import sk.trizna.erm_comparison.transformations.Transformation;

public class TransformationFactory {

	/**
	 * Creates the 'createEntitySet' transformation.
	 * list
	 */
	public static Transformation getCreateEntitySet(EntitySet entitySet) {
		Utils.validateNotNull(entitySet);

		Transformation transformation = TransformationPool.getInstance().getTransformation();
		transformation.setCode(EnumTransformation.CREATE_ENTITY_SET);
		transformation.addArgument(entitySet, EnumTransformationRole.ENTITY_SET);

		return transformation;
	}

	/**
	 * Creates the 'removeEntitySet' transformation.
	 * list
	 */
	public static Transformation getRemoveEntitySet(EntitySet entitySet) {
		Utils.validateNotNull(entitySet);

		Transformation transformation = TransformationPool.getInstance().getTransformation();
		transformation.setCode(EnumTransformation.REMOVE_ENTITY_SET);
		transformation.addArgument(entitySet, EnumTransformationRole.ENTITY_SET);

		return transformation;
	}

	/**
	 * Creates the 'createRelationship' transformation.
	 * list
	 */
	public static Transformation getCreateRelationship(Relationship relationship) {
		Utils.validateNotNull(relationship);

		return getRelationshipTransformation(relationship, true);
	}

	/**
	 * Creates the 'removeRelationship' transformation.
	 * list
	 */
	public static Transformation getRemoveRelationship(Relationship relationship) {
		Utils.validateNotNull(relationship);

		return getRelationshipTransformation(relationship, false);
	}

	/**
	 * Create the 'changeCardinality' transformation.
	 * 
	 */
	public static Transformation getChangeCardinality(Association association, EntitySet entitySet) {
		Utils.validateNotNull(association);
		
		Transformation transformation = TransformationPool.getInstance().getTransformation();
		transformation.setCode(EnumTransformation.CHANGE_CARDINALITY);
		transformation.addArgument(entitySet, EnumTransformationRole.ENTITY_SET);
		transformation.addArgument(association, EnumTransformationRole.ASSOCIATION);
		
		return transformation;
	}
	
	/**
	 * Creates the 'renameEntitySet' transformation.
	 */
	public static Transformation getRenameEntitySet(EntitySet entitySet, EntitySet targetEntitySet) {
		Utils.validateNotNull(entitySet);
		Utils.validateNotNull(targetEntitySet);

		Transformation transformation = TransformationPool.getInstance().getTransformation();
		transformation.setCode(EnumTransformation.RENAME_ENTITY_SET);
		transformation.addArgument(entitySet, EnumTransformationRole.ENTITY_SET);
		transformation.addArgument(targetEntitySet, EnumTransformationRole.ENTITY_SET_TARGET);

		return transformation;
	}

	/**
	 * Create the 'renameAttribute' transformation.
	 */
	public static Transformation getRenameAttribute(EntitySet entitySet, Attribute attribute, Attribute attributeTarget) {
		Utils.validateNotNull(attribute);
		Utils.validateNotNull(attributeTarget);
		Utils.validateNotNull(entitySet);

		Transformation transformation = TransformationPool.getInstance().getTransformation();
		transformation.setCode(EnumTransformation.RENAME_ATTRIBUTE);
		transformation.addArgument(attribute, EnumTransformationRole.ATTRIBUTE);
		transformation.addArgument(attributeTarget, EnumTransformationRole.ATTRIBUTE_TARGET);

		return transformation;
	}

	/**
	 * Create the 'createAttribute' transformation.
	 */
	public static Transformation getCreateAttribute(Attributed attributed, Attribute attribute) {
		Utils.validateNotNull(attributed);
		Utils.validateNotNull(attribute);

		if (attributed instanceof EntitySet) {
			return getAttributeTransformation((EntitySet) attributed, attribute, true);
		}
		if (attributed instanceof Association) {
			return getAttributeTransformation((Association) attributed, attribute, true);
		}
		return null;
	}

	/**
	 * Create the 'removeAttribute' transformation.
	 */
	public static Transformation getRemoveAttribute( Attributed attributed, Attribute attribute) {
		Utils.validateNotNull(attributed);
		Utils.validateNotNull(attribute);

		if (attributed instanceof EntitySet) {
			return getAttributeTransformation((EntitySet) attributed, attribute, false);
		}
		if (attributed instanceof Association) {
			return getAttributeTransformation((Association) attributed, attribute, false);
		}
		return null;
	}

	public static Transformation getExtractAttrToOwnEntitySet(Attribute attribute, EntitySet sourceEntitySet, EntitySet destEntitySet) {
		Utils.validateNotNull(attribute);
		Utils.validateNotNull(sourceEntitySet);
		
		Transformation transformation = TransformationPool.getInstance().getTransformation();
		
		transformation.setCode(EnumTransformation.EXTRACT_ATTR_TO_OWN_ENTITY_SET);
		transformation.addArgument(attribute, EnumTransformationRole.ATTRIBUTE);
		transformation.addArgument(sourceEntitySet, EnumTransformationRole.SOURCE_ENTITY_SET);
		if (destEntitySet != null) transformation.addArgument(destEntitySet, EnumTransformationRole.DEST_ENTITY_SET);
		
		return transformation;
	}
	
	public static Transformation getMoveAttrToIncidentEntitySet(Attribute attribute, Association association, EntitySet entitySet) {
		Utils.validateNotNull(attribute);
		Utils.validateNotNull(association);
		Utils.validateNotNull(entitySet);
		
		Transformation transformation = TransformationPool.getInstance().getTransformation();
		transformation.setCode(EnumTransformation.MOVE_ATTR_TO_INCIDENT_ENTITY_SET);
		
		transformation.addArgument(attribute, EnumTransformationRole.ATTRIBUTE);
		transformation.addArgument(association, EnumTransformationRole.ASSOCIATION);
		transformation.addArgument(entitySet, EnumTransformationRole.ENTITY_SET);
		return transformation;
	}

	public static Transformation getMoveAttrToIncidentAssociation(Attribute attribute, EntitySet entitySet, Association association) {
		Utils.validateNotNull(attribute);
		Utils.validateNotNull(association);
		Utils.validateNotNull(entitySet);
		
		Transformation transformation = TransformationPool.getInstance().getTransformation();
		transformation.setCode(EnumTransformation.MOVE_ATTR_TO_INCIDENT_ASSOCIATION);
		
		transformation.addArgument(attribute, EnumTransformationRole.ATTRIBUTE);
		transformation.addArgument(entitySet, EnumTransformationRole.ENTITY_SET);
		transformation.addArgument(association, EnumTransformationRole.ASSOCIATION);
		
		return transformation;
	}

	public static Transformation getRebindMNTo1NN1(Association association, EntitySet entitySet, Association association1, Association association2) {
		Utils.validateNotNull(association);
		
		Transformation transformation = TransformationPool.getInstance().getTransformation();
		transformation.setCode(EnumTransformation.REBIND_MN_TO_1NN1);
		
		transformation.addArgument(association, EnumTransformationRole.ASSOCIATION);
		if (entitySet != null) transformation.addArgument(entitySet, EnumTransformationRole.ENTITY_SET);
		if (association1 != null) transformation.addArgument(association1, EnumTransformationRole.ASSOCIATION_1);
		if (association2 != null) transformation.addArgument(association2, EnumTransformationRole.ASSOCIATION_2);
		
		return transformation;
	}
	
	public static Transformation getRebind1NN1ToMN(Association association, EntitySet entitySet, Association association1, Association association2) {
		Utils.validateNotNull(entitySet);
		Utils.validateNotNull(association1);
		Utils.validateNotNull(association2);
		
		Transformation transformation = TransformationPool.getInstance().getTransformation();
		transformation.setCode(EnumTransformation.REBIND_1NN1_TO_MN);
		
		if (association != null) transformation.addArgument(association, EnumTransformationRole.ASSOCIATION);
		transformation.addArgument(entitySet, EnumTransformationRole.ENTITY_SET);
		transformation.addArgument(association1, EnumTransformationRole.ASSOCIATION_1);
		transformation.addArgument(association2, EnumTransformationRole.ASSOCIATION_2);
		
		return transformation;
	}

	public static Transformation getGeneralizationTo11Association(Association association, Generalization generalization) {
		Utils.validateNotNull(generalization);
		
		Transformation transformation = TransformationPool.getInstance().getTransformation();
		transformation.setCode(EnumTransformation.GENERALIZATION_TO_11_ASSOCIATION);
		
		transformation.addArgument(generalization, EnumTransformationRole.GENERALIZATION);
		if (association != null) transformation.addArgument(association, EnumTransformationRole.ASSOCIATION);
		
		return transformation;
	}

	public static Transformation getContract11Association(Association association, TransformableFlag exemplarModelFlag) {
		Utils.validateNotNull(association);
		
		Transformation transformation = TransformationPool.getInstance().getTransformation();
		transformation.setCode(EnumTransformation.CONTRACT_11_ASSOCIATION);
		
		transformation.addArgument(association, EnumTransformationRole.ASSOCIATION);
		if (exemplarModelFlag != null) transformation.addArgument(exemplarModelFlag, EnumTransformationRole.EXEMPLAR_MODEL_FLAG);
		
		return transformation;
	}

	public static Transformation getRebindNaryAssociation(Association association, EntitySet entitySet, TransformableFlag exemplarModelFlag) {
		Utils.validateNotNull(association);
		
		Transformation transformation = TransformationPool.getInstance().getTransformation();
		transformation.setCode(EnumTransformation.REBIND_NARY_ASSOCIATION);
		
		transformation.addArgument(association, EnumTransformationRole.ASSOCIATION);
		if (entitySet != null) transformation.addArgument(entitySet, EnumTransformationRole.ENTITY_SET);
		if (exemplarModelFlag != null) transformation.addArgument(exemplarModelFlag, EnumTransformationRole.EXEMPLAR_MODEL_FLAG);
		
		return transformation;
	}

	public static Transformation getDecomposeAttribute(EntitySet entitySet, Attribute attribute, TransformableList transformableList, TransformableFlag exemplarModelFlag) {
		Utils.validateNotNull(entitySet);
		Utils.validateNotNull(attribute);
		Utils.validateNotNull(transformableList);
		
		Transformation transformation = TransformationPool.getInstance().getTransformation();
		transformation.setCode(EnumTransformation.DECOMPOSE_ATTRIBUTE);
		
		transformation.addArgument(entitySet, EnumTransformationRole.ENTITY_SET);
		transformation.addArgument(attribute, EnumTransformationRole.ATTRIBUTE);
		transformation.addArgument(transformableList, EnumTransformationRole.TRANSFORMABLE_LIST);
		if (exemplarModelFlag != null) transformation.addArgument(exemplarModelFlag, EnumTransformationRole.EXEMPLAR_MODEL_FLAG);
		
		return transformation;
	}
	
	public static Transformation getMergeEntitySets(EntitySet entitySet1, EntitySet entitySet2) {
		Utils.validateNotNull(entitySet1);
		Utils.validateNotNull(entitySet2);
		
		Transformation transformation = TransformationPool.getInstance().getTransformation();
		transformation.setCode(EnumTransformation.MERGE_ENTITY_SETS);
		
		transformation.addArgument(entitySet1, EnumTransformationRole.ENTITY_SET1);
		transformation.addArgument(entitySet2, EnumTransformationRole.ENTITY_SET2);
		
		return transformation;
	}
	
	private static Transformation getRelationshipTransformation(Relationship relationship, boolean isCreation) {

		if (relationship instanceof Association) {
			EnumTransformation transformationCode = isCreation ? EnumTransformation.CREATE_ASSOCIATION : EnumTransformation.REMOVE_ASSOCIATION;

			Transformation transformation = TransformationPool.getInstance().getTransformation();
			transformation.setCode(transformationCode);
			transformation.addArgument(relationship, EnumTransformationRole.ASSOCIATION);

			return transformation;
		}
		if (relationship instanceof Generalization) {
			EnumTransformation transformationCode = isCreation ? EnumTransformation.CREATE_GENERALIZATION : EnumTransformation.REMOVE_GENERALIZATION;

			Transformation transformation = TransformationPool.getInstance().getTransformation();
			transformation.setCode(transformationCode);
			transformation.addArgument(relationship, EnumTransformationRole.GENERALIZATION);

			return transformation;
		}
		return null;
	}

	private static Transformation getAttributeTransformation(EntitySet entitySet, Attribute attribute, boolean isCreation) {
		EnumTransformation transformationCode = isCreation ? EnumTransformation.CREATE_ATTRIBUTE : EnumTransformation.REMOVE_ATTRIBUTE;

		Transformation transformation = TransformationPool.getInstance().getTransformation();
		transformation.setCode(transformationCode);
		transformation.addArgument(entitySet, EnumTransformationRole.ENTITY_SET);
		transformation.addArgument(attribute, EnumTransformationRole.ATTRIBUTE);

		return transformation;
	}

	private static Transformation getAttributeTransformation(Association association, Attribute attribute, boolean isCreation) {
		EnumTransformation transformationCode = isCreation ? EnumTransformation.CREATE_ATTRIBUTE : EnumTransformation.REMOVE_ATTRIBUTE;

		Transformation transformation = TransformationPool.getInstance().getTransformation();
		transformation.setCode(transformationCode);
		transformation.addArgument(association, EnumTransformationRole.ASSOCIATION);
		transformation.addArgument(attribute, EnumTransformationRole.ATTRIBUTE);

		return transformation;
	}
}
