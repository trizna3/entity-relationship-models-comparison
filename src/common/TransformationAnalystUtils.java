package common;

import java.util.List;
import java.util.Set;

import common.enums.EnumRelationshipSideRole;
import common.enums.EnumTransformation;
import common.enums.EnumTransformationRole;
import common.objectPools.TransformationPool;
import comparing.AssociationComparator;
import comparing.EntitySetAssociationComparator;
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
	
	private static EntitySetComparator entitySetComparator;
	private static EntitySetAssociationComparator entitySetAssociationComparator;
	private static AssociationComparator associationComparator;

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
				if (getEntitySetComparator().compareAssymetric(association.getFirstSide().getEntitySet(), entitySet) >= SimilarityConstants.SIMILARITY_TRESHOLD_ENTITY_SET &&
					getEntitySetComparator().compareAssymetric(association.getSecondSide().getEntitySet(), entitySet) >= SimilarityConstants.SIMILARITY_TRESHOLD_ENTITY_SET) {
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

	public static void getPossibleRebindMNTo1NN1Transformations(List<Transformation> target, ERModel model, ERModel otherModel) {
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
			
			boolean matchingEntitySetFound = false;
			for (EntitySet entitySet : otherModel.getEntitySets()) {
				if (entitySet.getMappedTo() != null) {
					continue;
				}
				if (!entitySet.isBinary()) {
					continue;
				}
				if (getEntitySetAssociationComparator().compareSymmetric(entitySet, association) <= SimilarityConstants.SIMILARITY_TRESHOLD_ENTITY_SET_ASSOCIATION) {
					continue;
				}
				
				matchingEntitySetFound = true;
				break;				
			}
			
			if (!matchingEntitySetFound) {
				continue;
			}
			
			Transformation transformation = TransformationPool.getInstance().getTransformation();
			transformation.setCode(EnumTransformation.REBIND_MN_TO_1NN1);
			transformation.addArgument(association, EnumTransformationRole.ASSOCIATION);

			target.add(transformation);
		}
	}

	public static void getPossibleRebind1NN1ToMNTransformations(List<Transformation> target, ERModel model, ERModel otherModel) {
		Utils.validateNotNull(target);
		Utils.validateNotNull(model);

		nextES: for (EntitySet entitySet : model.getEntitySets()) {
			// entity set validations
			if (!entitySet.isBinary()) {
				continue;
			}
			if (entitySet.getMappedTo() != null) {
				continue;
			}
			Set<Relationship> incidentRelationships = entitySet.getIncidentRelationships();
			if (incidentRelationships.size() != 2) {
				continue;
			}
			if (entitySet.containsTransformationFlag(EnumTransformation.REBIND_MN_TO_1NN1)) {
				continue;
			}
			// validations on entity set's incident associations
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
			
			// searching possible match candidate in other model
			boolean matchingAssociationFound = false;
			for (Relationship relationship : otherModel.getRelationships()) {
				if (!(relationship instanceof Association)) {
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
				if (getEntitySetAssociationComparator().compareSymmetric(entitySet, association) <= SimilarityConstants.SIMILARITY_TRESHOLD_ENTITY_SET_ASSOCIATION) {
					continue;
				} 
				
				matchingAssociationFound = true;
				break;
			}
			
			
			if (!matchingAssociationFound) {
				continue;
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

	public static void getPossibleRebindNaryAssociationTransformations(List<Transformation> target, ERModel model, ERModel otherModel) {
		Utils.validateNotNull(target);
		Utils.validateNotNull(model);

		nextRel: for (Relationship relationship : model.getRelationships()) {
			if (relationship instanceof Association && !relationship.isBinary()) {
				for (RelationshipSide side : relationship.getSides()) {
					if (side.getEntitySet().getMappedTo() != null) {
						continue nextRel;
					}
				}
				
				// search for match candidate
				boolean matchCandidateFound = false;
				nextCandidate: for (Relationship relationship2 : otherModel.getRelationships()) {
					if (!(relationship2 instanceof Association)) {
						continue nextCandidate;
					}
					if (!RelationshipUtils.sameGrade(relationship, relationship2)) {
						continue nextCandidate;
					}
					for (RelationshipSide side : relationship2.getSides()) {
						if (side.getEntitySet().getMappedTo() != null) {
							continue nextCandidate;
						}
					}
					if (getAssociationComparator().compareSymmetric((Association) relationship, (Association) relationship2) < SimilarityConstants.SIMILARITY_TRESHOLD_ASSOCIATION) {
						continue;
					}
					matchCandidateFound = true;
					break;
				}
				
				if (matchCandidateFound) {
					continue;
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

	private static EntitySetComparator getEntitySetComparator() {
		if (entitySetComparator == null) {
			entitySetComparator = EntitySetComparator.getInstance();
		}
		return entitySetComparator;
	}

	private static EntitySetAssociationComparator getEntitySetAssociationComparator() {
		if (entitySetAssociationComparator == null) {
			entitySetAssociationComparator = EntitySetAssociationComparator.getInstance();
		}
		return entitySetAssociationComparator;
	}

	private static AssociationComparator getAssociationComparator() {
		if (associationComparator == null) {
			associationComparator = AssociationComparator.getInstance();
		}
		return associationComparator;
	} 
}
