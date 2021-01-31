package sk.trizna.erm_comparison.common;

import java.util.List;
import java.util.Set;

import sk.trizna.erm_comparison.common.enums.EnumRelationshipSideRole;
import sk.trizna.erm_comparison.common.enums.EnumTransformation;
import sk.trizna.erm_comparison.common.enums.EnumTransformationRole;
import sk.trizna.erm_comparison.common.object_pools.TransformationPool;
import sk.trizna.erm_comparison.comparing.AssociationComparator;
import sk.trizna.erm_comparison.comparing.EntitySetAssociationComparator;
import sk.trizna.erm_comparison.comparing.EntitySetComparator;
import sk.trizna.erm_comparison.entity_relationship_model.Association;
import sk.trizna.erm_comparison.entity_relationship_model.Attribute;
import sk.trizna.erm_comparison.entity_relationship_model.ERModel;
import sk.trizna.erm_comparison.entity_relationship_model.ERText;
import sk.trizna.erm_comparison.entity_relationship_model.EntitySet;
import sk.trizna.erm_comparison.entity_relationship_model.Generalization;
import sk.trizna.erm_comparison.entity_relationship_model.Relationship;
import sk.trizna.erm_comparison.entity_relationship_model.RelationshipSide;
import sk.trizna.erm_comparison.entity_relationship_model.TransformableFlag;
import sk.trizna.erm_comparison.transformations.Transformation;

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
				if (getEntitySetComparator().compareAssymetric(association.getFirstSide().getEntitySet(), entitySet) >= SimilarityConstantsUtils.getEntitySetSimilarityTreshold() &&
					getEntitySetComparator().compareAssymetric(association.getSecondSide().getEntitySet(), entitySet) >= SimilarityConstantsUtils.getEntitySetSimilarityTreshold()) {
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
				if (getEntitySetAssociationComparator().compareSymmetric(entitySet, association) <= SimilarityConstantsUtils.getEntitySetAssociationSimilarityTreshold()) {
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
			if (entitySet.containsTransformationFlag(EnumTransformation.REBIND_MN_TO_1NN1)) {
				continue;
			}
			// validations on entity set's incident associations
			Association association1 = null;
			Association association2 = null;
			for (Relationship relationship : entitySet.getIncidentRelationships()) {
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
				if (getEntitySetAssociationComparator().compareSymmetric(entitySet, association) <= SimilarityConstantsUtils.getEntitySetAssociationSimilarityTreshold()) {
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
			if (entitySet.containsTransformationFlag(EnumTransformation.EXTRACT_ATTR_TO_OWN_ENTITY_SET)) {
				continue;
			}
			if (entitySet.getMappedTo() != null) {
				continue;
			}
			for (Attribute attribute : entitySet.getAttributes()) {
				if (!notMappedEntitySetNames.contains(attribute)) {
					continue;
				}
				
				// check if we've extracted this same attribute before
				EntitySet destEntitySet = null;
				for (EntitySet destEntitySetCandidate : model.getEntitySets()) {
					if (destEntitySetCandidate == entitySet) {
						continue;
					}
					if (StringUtils.areEqual(attribute.getText(),destEntitySetCandidate.getNameText())) {
						destEntitySet = destEntitySetCandidate;
						break;
					}
				}
				
				Transformation transformation = TransformationPool.getInstance().getTransformation();
				transformation.setCode(EnumTransformation.EXTRACT_ATTR_TO_OWN_ENTITY_SET);
				transformation.addArgument(attribute, EnumTransformationRole.ATTRIBUTE);
				transformation.addArgument(entitySet, EnumTransformationRole.SOURCE_ENTITY_SET);
				if (destEntitySet != null) {
					transformation.addArgument(destEntitySet, EnumTransformationRole.DEST_ENTITY_SET);
				}

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
					if (getAssociationComparator().compareSymmetric((Association) relationship, (Association) relationship2) < SimilarityConstantsUtils.getAssociationTreshold()) {
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
