package common;

import java.util.List;
import java.util.Set;

import common.enums.EnumRelationshipSideRole;
import common.enums.EnumTransformation;
import common.enums.EnumTransformationRole;
import common.objectPools.TransformationPool;
import comparing.EntitySetComparator;
import entityRelationshipModel.Association;
import entityRelationshipModel.Attribute;
import entityRelationshipModel.ERModel;
import entityRelationshipModel.ERText;
import entityRelationshipModel.EntitySet;
import entityRelationshipModel.Generalization;
import entityRelationshipModel.Relationship;
import entityRelationshipModel.RelationshipSide;
import entityRelationshipModel.TransformableFlag;
import transformations.Transformation;

public class TransformationAnalystUtils {

	public static void getPossibleContract11AssociationTransformations(List<Transformation> target, ERModel model, ERModel otherModel) {
		Utils.validateNotNull(target);
		Utils.validateNotNull(model);

		for (Relationship relationship : model.getRelationships()) {
			if (relationship instanceof Association == false) {
				continue;
			}
			Association association = (Association) relationship;
			if (!association.isBinary()) {
				continue;
			}
			if (!EnumRelationshipSideRole.CARDINALITY_ONE.equals(association.getFirstSide().getRole()) || association.getFirstSide().getEntitySet().getMappedTo() != null) {
				continue;
			}
			if (!EnumRelationshipSideRole.CARDINALITY_ONE.equals(association.getSecondSide().getRole()) || association.getSecondSide().getEntitySet().getMappedTo() != null) {
				continue;
			}
			
			boolean opposingEntitySetFound = false;
			for (EntitySet entitySet : otherModel.getNotMappedEntitySets()) {
				if (EntitySetComparator.getInstance().compareAssymetric(association.getFirstSide().getEntitySet(), entitySet) >= EntitySetComparator.SIMILARITY_TRESHOLD &&
					EntitySetComparator.getInstance().compareAssymetric(association.getSecondSide().getEntitySet(), entitySet) >= EntitySetComparator.SIMILARITY_TRESHOLD) {
					opposingEntitySetFound = true;
					break;
				}
			}
			
			if (!opposingEntitySetFound) {
				continue;
			}

			Transformation transformation = TransformationPool.getInstance().getTransformation();
			transformation.setCode(EnumTransformation.CONTRACT_11_ASSOCIATION);
			transformation.addArgument(association, EnumTransformationRole.ASSOCIATION);

			if (model.isExemplar()) {
				transformation.addArgument(new TransformableFlag(), EnumTransformationRole.EXEMPLAR_MODEL_FLAG);
			}

			target.add(transformation);

		}
	}

	public static void getPossibleRebindMNTo1NN1Transformations(List<Transformation> target, ERModel model) {
		Utils.validateNotNull(target);
		Utils.validateNotNull(model);

		for (Relationship relationship : model.getRelationships()) {
			if (relationship instanceof Association == false) {
				continue;
			}
			Association association = (Association) relationship;
			if (!association.isBinary()) {
				continue;
			}
			if (!EnumRelationshipSideRole.CARDINALITY_MANY.equals(association.getFirstSide().getRole()) || association.getFirstSide().getEntitySet().getMappedTo() != null) {
				continue;
			}
			if (!EnumRelationshipSideRole.CARDINALITY_MANY.equals(association.getSecondSide().getRole()) || association.getSecondSide().getEntitySet().getMappedTo() != null) {
				continue;
			}
			if (association.containsTransformationFlag(EnumTransformation.REBIND_1NN1_TO_MN)) {
				continue;
			}
			
			Transformation transformation = TransformationPool.getInstance().getTransformation();
			transformation.setCode(EnumTransformation.REBIND_MN_TO_1NN1);
			transformation.addArgument(association, EnumTransformationRole.ASSOCIATION);

			target.add(transformation);
		}
	}

	public static void getPossibleRebind1NN1ToMNTransformations(List<Transformation> target, ERModel model) {
		Utils.validateNotNull(target);
		Utils.validateNotNull(model);

		nextES: for (EntitySet entitySet : model.getEntitySets()) {
			if (entitySet.getNeighbours().size() != 2) {
				continue;
			}
			if (entitySet.getMappedTo() != null) {
				continue;
			}
			List<Relationship> incidentRelationships = entitySet.getIncidentRelationships();
			if (incidentRelationships.size() != 2) {
				continue;
			}
			if (entitySet.containsTransformationFlag(EnumTransformation.REBIND_MN_TO_1NN1)) {
				continue;
			}
			Association association1 = null;
			Association association2 = null;
			for (Relationship relationship : incidentRelationships) {
				if (relationship instanceof Association == false) {
					continue nextES;
				}
				if (!EnumRelationshipSideRole.CARDINALITY_MANY.equals(RelationshipUtils.getRole(relationship, entitySet))) {
					continue nextES;
				}
				if (!EnumRelationshipSideRole.CARDINALITY_ONE.equals(RelationshipUtils.getOtherSide(relationship, entitySet).getRole())) {
					continue nextES;
				}
				if (RelationshipUtils.getOtherSide(relationship, entitySet).getEntitySet().getMappedTo() != null) {
					continue nextES;
				}

				if (association1 == null) {
					association1 = (Association) relationship;
				} else {
					association2 = (Association) relationship;
				}
			}
			
			Transformation transformation = TransformationPool.getInstance().getTransformation();
			transformation.setCode(EnumTransformation.REBIND_1NN1_TO_MN);
			transformation.addArgument(entitySet, EnumTransformationRole.ENTITY_SET);
			transformation.addArgument(association1, EnumTransformationRole.ASSOCIATION_1);
			transformation.addArgument(association2, EnumTransformationRole.ASSOCIATION_2);

			target.add(transformation);
		}
	}

	public static void getPossibleGeneralizationToAssociationTransformations(List<Transformation> target, ERModel model) {
		Utils.validateNotNull(target);
		Utils.validateNotNull(model);

		for (Relationship relationship : model.getRelationships()) {
			if (relationship instanceof Generalization) {
				if (relationship.getFirstSide().getEntitySet().getMappedTo() != null) {
					continue;
				}
				if (relationship.getSecondSide().getEntitySet().getMappedTo() != null) {
					continue;
				}
				
				Transformation transformation = TransformationPool.getInstance().getTransformation();
				transformation.setCode(EnumTransformation.GENERALIZATION_TO_11_ASSOCIATION);
				transformation.addArgument(relationship, EnumTransformationRole.GENERALIZATION);

				target.add(transformation);
			}
		}
	}

	public static void getPossibleExtractAttributeToOwnEntitySetTransformations(List<Transformation> target, ERModel model, ERModel otherModel) {
		Utils.validateNotNull(target);
		Utils.validateNotNull(model);

		Set<ERText> notMappedEntitySetNames = MappingUtils.getNotMappedEntitySetNames(otherModel);
		
		for (EntitySet entitySet : model.getEntitySets()) {
			for (Attribute attribute : entitySet.getAttributes()) {
				if (!notMappedEntitySetNames.contains(attribute)) {
					continue;
				}
				
				Transformation transformation = TransformationPool.getInstance().getTransformation();
				transformation.setCode(EnumTransformation.EXTRACT_ATTR_TO_OWN_ENTITY_SET);
				transformation.addArgument(attribute, EnumTransformationRole.ATTRIBUTE);
				transformation.addArgument(entitySet, EnumTransformationRole.SOURCE_ENTITY_SET);

				target.add(transformation);
			}
		}

	}

	public static void getPossibleMoveAttributeToIncidentEntitySetTransformations(List<Transformation> target, ERModel model, ERModel otherModel) {
		Utils.validateNotNull(target);
		Utils.validateNotNull(model);

		Set<Attribute> otherModelAttributes = MappingUtils.getNotMappedEntitySetAttributes(otherModel);
		
		for (EntitySet entitySet : model.getEntitySets()) {
			for (Relationship relationship : entitySet.getIncidentRelationships()) {
				if (relationship instanceof Association) {
					for (Attribute attribute : ((Association) relationship).getAttributes()) {
						if (!EnumRelationshipSideRole.CARDINALITY_MANY.equals(RelationshipUtils.getRole(relationship, entitySet))) {
							continue;
						}
						if (attribute.containsTransformationFlag(EnumTransformation.MOVE_ATTR_TO_INCIDENT_ASSOCIATION)) {
							continue;
						}
						if (!otherModelAttributes.contains(attribute)) {
							continue;
						}
						
						Transformation transformation = TransformationPool.getInstance().getTransformation();
						transformation.setCode(EnumTransformation.MOVE_ATTR_TO_INCIDENT_ENTITY_SET);
						transformation.addArgument(attribute, EnumTransformationRole.ATTRIBUTE);
						transformation.addArgument(entitySet, EnumTransformationRole.ENTITY_SET);
						transformation.addArgument(relationship, EnumTransformationRole.ASSOCIATION);

						target.add(transformation);
					}
				}
			}
		}
	}

	public static void getPossibleMoveAttributeToIncidentAssociationTransformations(List<Transformation> target, ERModel model, ERModel otherModel) {
		Utils.validateNotNull(target);
		Utils.validateNotNull(model);
		
		Set<Attribute> otherModelAttributes = MappingUtils.getNotMappedAssociationAttributes(otherModel);

		for (EntitySet entitySet : model.getEntitySets()) {
			for (Attribute attribute : entitySet.getAttributes()) {
				for (Relationship relationship : entitySet.getIncidentRelationships()) {
					if (!EnumRelationshipSideRole.CARDINALITY_MANY.equals(RelationshipUtils.getRole(relationship, entitySet))) {
						continue;
					}
					if (attribute.containsTransformationFlag(EnumTransformation.MOVE_ATTR_TO_INCIDENT_ENTITY_SET)) {
						continue;
					}
					if (!otherModelAttributes.contains(attribute)) {
						continue;
					}
					if (relationship instanceof Association) {
						
						Transformation transformation = TransformationPool.getInstance().getTransformation();
						transformation.setCode(EnumTransformation.MOVE_ATTR_TO_INCIDENT_ASSOCIATION);
						transformation.addArgument(attribute, EnumTransformationRole.ATTRIBUTE);
						transformation.addArgument(entitySet, EnumTransformationRole.ENTITY_SET);
						transformation.addArgument(relationship, EnumTransformationRole.ASSOCIATION);

						target.add(transformation);
					}
				}
			}
		}
	}

	public static void getPossibleRebindNaryAssociationTransformations(List<Transformation> target, ERModel model) {
		Utils.validateNotNull(target);
		Utils.validateNotNull(model);

		nextRel: for (Relationship relationship : model.getRelationships()) {
			if (relationship instanceof Association && !relationship.isBinary()) {
				for (RelationshipSide side : relationship.getSides()) {
					if (side.getEntitySet().getMappedTo() != null) {
						continue nextRel;
					}
				}

				Transformation transformation = TransformationPool.getInstance().getTransformation();
				transformation.setCode(EnumTransformation.REBIND_NARY_ASSOCIATION);
				transformation.addArgument(relationship, EnumTransformationRole.ASSOCIATION);
				if (model.isExemplar()) {
					transformation.addArgument(new TransformableFlag(), EnumTransformationRole.EXEMPLAR_MODEL_FLAG);
				}

				target.add(transformation);
			}
		}

	}
}
