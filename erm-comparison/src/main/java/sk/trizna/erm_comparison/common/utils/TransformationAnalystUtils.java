package sk.trizna.erm_comparison.common.utils;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import sk.trizna.erm_comparison.common.TransformationFactory;
import sk.trizna.erm_comparison.common.enums.EnumConstants;
import sk.trizna.erm_comparison.common.enums.EnumRelationshipSideRole;
import sk.trizna.erm_comparison.common.enums.EnumTransformation;
import sk.trizna.erm_comparison.common.enums.SimilarityConstantsUtils;
import sk.trizna.erm_comparison.common.exceptions.PreconditionsNotMetException;
import sk.trizna.erm_comparison.common.key_config.AppConfigManager;
import sk.trizna.erm_comparison.common.object_pools.PreconditionsNotMetExceptionPool;
import sk.trizna.erm_comparison.common.object_pools.TransformableFlagPool;
import sk.trizna.erm_comparison.comparing.AssociationComparator;
import sk.trizna.erm_comparison.comparing.EntitySetAssociationComparator;
import sk.trizna.erm_comparison.comparing.EntitySetComparator;
import sk.trizna.erm_comparison.entity_relationship_model.Association;
import sk.trizna.erm_comparison.entity_relationship_model.AssociationSide;
import sk.trizna.erm_comparison.entity_relationship_model.Attribute;
import sk.trizna.erm_comparison.entity_relationship_model.ERModel;
import sk.trizna.erm_comparison.entity_relationship_model.ERText;
import sk.trizna.erm_comparison.entity_relationship_model.EntitySet;
import sk.trizna.erm_comparison.entity_relationship_model.Generalization;
import sk.trizna.erm_comparison.entity_relationship_model.Relationship;
import sk.trizna.erm_comparison.entity_relationship_model.RelationshipSide;
import sk.trizna.erm_comparison.entity_relationship_model.TransformableList;
import sk.trizna.erm_comparison.transformations.Transformation;

/**
 * Transformation analyst utilities
 * 
 * getPossible[transformation]Transformations method structure:
 * 	- validate strong (mandatory) preconditions
 * 	- validate soft preconditions (bypassable by minor precondition transformations)
 * 
 * 	- (if validation above passed successfully) add transformation to mapping
 * 
 * @author Adam Trizna
 *
 */
public class TransformationAnalystUtils extends Utils {
	
	private static EntitySetComparator entitySetComparator;
	private static EntitySetAssociationComparator entitySetAssociationComparator;
	private static AssociationComparator associationComparator;
	
	private static final boolean conditionalTransformation = Boolean.valueOf(AppConfigManager.getInstance().getResource(EnumConstants.CONFIG_CONDITIONAL_TRANSFORMATION));
	
	private static final PreconditionsNotMetExceptionPool PRECONDITION_NOT_MET_POOL = PreconditionsNotMetExceptionPool.getInstance();
	private static final TransformableFlagPool TRANSFORMABLE_FLAG_POOL = TransformableFlagPool.getInstance();

	public static void getPossibleContract11AssociationTransformations(List<Transformation> target, ERModel model, ERModel otherModel) {
		Utils.validateNotNull(target);
		Utils.validateNotNull(model);

		for (Relationship relationship : model.getRelationships()) {
			try {
				contract11AssociationPreconditionsStrong(relationship,otherModel);
				List<Transformation> preconditions = contract11AssociationPreconditionsSoft(relationship, model);
				
				Transformation transformation = TransformationFactory.getContract11Association((Association) relationship, model.isExemplar() ? TRANSFORMABLE_FLAG_POOL.getObject() : null);
				transformation.setPreconditions(preconditions);
				
				target.add(transformation);
			} catch (PreconditionsNotMetException e) {
				PRECONDITION_NOT_MET_POOL.freeObject(e);
				continue;
			}
		}
	}
	
	private static void contract11AssociationPreconditionsStrong(Relationship relationship, ERModel otherModel) throws PreconditionsNotMetException {
		if (relationship instanceof Association == false) {
			throw PRECONDITION_NOT_MET_POOL.getObject();
		}
		Association association = (Association) relationship;
		if (!association.isBinary()) {
			throw PRECONDITION_NOT_MET_POOL.getObject();
		}
		if (association.getFirstSide().getEntitySet().getMappedTo() != null) {
			throw PRECONDITION_NOT_MET_POOL.getObject();
		}
		if (association.getSecondSide().getEntitySet().getMappedTo() != null) {
			throw PRECONDITION_NOT_MET_POOL.getObject();
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
			throw PRECONDITION_NOT_MET_POOL.getObject();
		}
	}

	private static List<Transformation> contract11AssociationPreconditionsSoft(Relationship relationship, ERModel model) throws PreconditionsNotMetException {
		List<Transformation> preconditions = null;
		
		if (!EnumRelationshipSideRole.ONE.equals(relationship.getFirstSide().getRole())) {
			if (conditionalTransformation && !relationship.getFirstSide().containsTransformationFlag(EnumTransformation.CHANGE_CARDINALITY) && !model.isExemplar()) {
				preconditions = Utils.addToList(preconditions,TransformationFactory.getChangeCardinality((Association) relationship, relationship.getFirstSide().getEntitySet()));
			} else {
				throw PRECONDITION_NOT_MET_POOL.getObject();
			}
		}
		if (!EnumRelationshipSideRole.ONE.equals(relationship.getSecondSide().getRole())) {
			if (conditionalTransformation && !relationship.getSecondSide().containsTransformationFlag(EnumTransformation.CHANGE_CARDINALITY) && !model.isExemplar()) {
				preconditions = Utils.addToList(preconditions,TransformationFactory.getChangeCardinality((Association) relationship, relationship.getSecondSide().getEntitySet()));
			} else {
				throw PRECONDITION_NOT_MET_POOL.getObject();
			}
		}
		
		return preconditions;
	}

	public static void getPossibleRebindMNTo1NN1Transformations(List<Transformation> target, ERModel model, ERModel otherModel) {
		Utils.validateNotNull(target);
		Utils.validateNotNull(model);

		for (Relationship relationship : model.getRelationships()) {
			try {
				rebindMNTo1NN1PreconditionsStrong(relationship,otherModel);
				List<Transformation> preconditions = rebindMNTo1NN1PreconditionsSoft(relationship, model);
				
				Transformation transformation = TransformationFactory.getRebindMNTo1NN1((Association)relationship,null,null,null);
				transformation.setPreconditions(preconditions);
				
				target.add(transformation);
			} catch (PreconditionsNotMetException e) {
				PRECONDITION_NOT_MET_POOL.freeObject(e);
				continue;
			}
		}
	}

	private static void rebindMNTo1NN1PreconditionsStrong(Relationship relationship, ERModel otherModel) throws PreconditionsNotMetException {
		if (relationship instanceof Association == false) {
			throw PRECONDITION_NOT_MET_POOL.getObject();
		}
		Association association = (Association) relationship;
		if (!association.isBinary()) {
			throw PRECONDITION_NOT_MET_POOL.getObject();
		}
		if (association.getFirstSide().getEntitySet().getMappedTo() != null) {
			throw PRECONDITION_NOT_MET_POOL.getObject();
		}
		if (association.getSecondSide().getEntitySet().getMappedTo() != null) {
			throw PRECONDITION_NOT_MET_POOL.getObject();
		}
		if (association.containsTransformationFlag(EnumTransformation.REBIND_1NN1_TO_MN)) {
			throw PRECONDITION_NOT_MET_POOL.getObject();
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
			throw PRECONDITION_NOT_MET_POOL.getObject();
		}
	}
	
	private static List<Transformation> rebindMNTo1NN1PreconditionsSoft(Relationship relationship, ERModel model) throws PreconditionsNotMetException {
		List<Transformation> preconditions = null;
		
		if (!EnumRelationshipSideRole.MANY.equals(relationship.getFirstSide().getRole())) {
			if (conditionalTransformation && relationship.getFirstSide().containsTransformationFlag(EnumTransformation.CHANGE_CARDINALITY) && !model.isExemplar()) {
				preconditions = Utils.addToList(preconditions,TransformationFactory.getChangeCardinality((Association) relationship, relationship.getFirstSide().getEntitySet()));
			} else {
				throw PRECONDITION_NOT_MET_POOL.getObject();
			}
		}
		if (!EnumRelationshipSideRole.MANY.equals(relationship.getSecondSide().getRole())) {
			if (conditionalTransformation && relationship.getSecondSide().containsTransformationFlag(EnumTransformation.CHANGE_CARDINALITY) && !model.isExemplar()) {
				preconditions = Utils.addToList(preconditions,TransformationFactory.getChangeCardinality((Association) relationship, relationship.getSecondSide().getEntitySet()));
			} else {
				throw PRECONDITION_NOT_MET_POOL.getObject();
			}
		}
		
		return preconditions;
	}

	public static void getPossibleRebind1NN1ToMNTransformations(List<Transformation> target, ERModel model, ERModel otherModel) {
		Utils.validateNotNull(target);
		Utils.validateNotNull(model);

		for (EntitySet entitySet : model.getEntitySets()) {
			try {
				rebind1NN1ToMNPreconditionsStrong(entitySet,otherModel);
				List<Transformation> preconditions = rebind1NN1ToMNPreconditionsSoft(entitySet, model);
				
				List<Relationship> incidentRels = entitySet.getIncidentRelationships();
				Association association1 = (Association) incidentRels.get(0);
				Association association2 = (Association) incidentRels.get(1);
				Transformation transformation = TransformationFactory.getRebind1NN1ToMN(null, entitySet, association1, association2);
				transformation.setPreconditions(preconditions);
				
				target.add(transformation);
			} catch (PreconditionsNotMetException e) {
				PRECONDITION_NOT_MET_POOL.freeObject(e);
				continue;
			}
		}
	}
	
	public static void rebind1NN1ToMNPreconditionsStrong(EntitySet entitySet, ERModel otherModel) throws PreconditionsNotMetException {
		// entity set validations
		if (!entitySet.isBinary()) {
			throw PRECONDITION_NOT_MET_POOL.getObject();
		}
		if (entitySet.getMappedTo() != null) {
			throw PRECONDITION_NOT_MET_POOL.getObject();
		}
		if (entitySet.containsTransformationFlag(EnumTransformation.REBIND_MN_TO_1NN1)) {
			throw PRECONDITION_NOT_MET_POOL.getObject();
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
			if (!EnumRelationshipSideRole.MANY.equals(association.getFirstSide().getRole()) || association.getFirstSide().getEntitySet().getMappedTo() != null) {
				continue;
			}
			if (!EnumRelationshipSideRole.MANY.equals(association.getSecondSide().getRole()) || association.getSecondSide().getEntitySet().getMappedTo() != null) {
				continue;
			}
			if (getEntitySetAssociationComparator().compareSymmetric(entitySet, association) <= SimilarityConstantsUtils.getEntitySetAssociationSimilarityTreshold()) {
				continue;
			} 
			
			matchingAssociationFound = true;
			break;
		}
		if (!matchingAssociationFound) {
			throw PRECONDITION_NOT_MET_POOL.getObject();
		}
	}
	
	private static List<Transformation> rebind1NN1ToMNPreconditionsSoft(EntitySet entitySet, ERModel model) throws PreconditionsNotMetException  {
		List<Transformation> preconditions = null;
		
		// validations on entity set's incident associations
		for (Relationship relationship : entitySet.getIncidentRelationships()) {
			if (relationship instanceof Association == false) {
				throw PRECONDITION_NOT_MET_POOL.getObject();
			}
			if (RelationshipUtils.getOtherSide(relationship, entitySet).getEntitySet().getMappedTo() != null) {
				throw PRECONDITION_NOT_MET_POOL.getObject();
			}
			AssociationSide side = RelationshipUtils.getSide((Association)relationship, entitySet);
			if (!EnumRelationshipSideRole.MANY.equals(side.getRole())) {
				if (conditionalTransformation && !side.containsTransformationFlag(EnumTransformation.CHANGE_CARDINALITY) && !model.isExemplar()) {
					preconditions = Utils.addToList(preconditions,TransformationFactory.getChangeCardinality((Association) relationship, entitySet));
				} else {
					throw PRECONDITION_NOT_MET_POOL.getObject();
				}
			}
			RelationshipSide otherSide = RelationshipUtils.getOtherSide(relationship, entitySet);
			if (!EnumRelationshipSideRole.ONE.equals(otherSide.getRole())) {
				if (conditionalTransformation && !otherSide.containsTransformationFlag(EnumTransformation.CHANGE_CARDINALITY) && !model.isExemplar()) {
					preconditions = Utils.addToList(preconditions,TransformationFactory.getChangeCardinality((Association) relationship, otherSide.getEntitySet()));
				} else {
					throw PRECONDITION_NOT_MET_POOL.getObject();
				}
			}
		}
		
		return preconditions;
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

				target.add(TransformationFactory.getGeneralizationTo11Association(null, (Generalization) relationship));
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
				if (!CollectionUtils.containsText(notMappedEntitySetNames, attribute)) {
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
				
				target.add(TransformationFactory.getExtractAttrToOwnEntitySet(attribute, entitySet, destEntitySet));
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
						try {
							moveAttributeToIncidentEntitySetPreconditionsStrong(attribute,otherModelAttributes);
							List<Transformation> preconditions = moveAttributeToIncidentEntitySetPreconditionsSoft(relationship, entitySet, model);

							Transformation transformation = TransformationFactory.getMoveAttrToIncidentEntitySet(attribute, (Association) relationship, entitySet);
							transformation.setPreconditions(preconditions);
							
							target.add(transformation);
							
						} catch (PreconditionsNotMetException e) {
							PRECONDITION_NOT_MET_POOL.freeObject(e);
							continue;
						}
					}
				}
			}
		}
	}
	
	private static void moveAttributeToIncidentEntitySetPreconditionsStrong(Attribute attribute, Set<Attribute> otherModelAttributes) throws PreconditionsNotMetException {
		if (attribute.containsTransformationFlag(EnumTransformation.MOVE_ATTR_TO_INCIDENT_ASSOCIATION)) {
			throw PRECONDITION_NOT_MET_POOL.getObject();
		}
		if (!CollectionUtils.containsText(otherModelAttributes, attribute)) {
			throw PRECONDITION_NOT_MET_POOL.getObject();
		}
	}
	
	private static List<Transformation> moveAttributeToIncidentEntitySetPreconditionsSoft(Relationship relationship, EntitySet entitySet, ERModel model) throws PreconditionsNotMetException {
		List<Transformation> preconditions = null;
		
		AssociationSide side = RelationshipUtils.getSide((Association)relationship, entitySet);
		if (!EnumRelationshipSideRole.MANY.equals(side.getRole())) {
			if (conditionalTransformation && !side.containsTransformationFlag(EnumTransformation.CHANGE_CARDINALITY) && !model.isExemplar()) {
				preconditions = Utils.addToList(preconditions,TransformationFactory.getChangeCardinality((Association) relationship, entitySet));
			} else {
				throw PRECONDITION_NOT_MET_POOL.getObject();
			}
		}
		
		return preconditions;
	}

	public static void getPossibleMoveAttributeToIncidentAssociationTransformations(List<Transformation> target, ERModel model, ERModel otherModel) {
		Utils.validateNotNull(target);
		Utils.validateNotNull(model);
		
		Set<Attribute> otherModelAttributes = MappingUtils.getNotMappedAssociationAttributes(otherModel);

		for (EntitySet entitySet : model.getEntitySets()) {
			for (Attribute attribute : entitySet.getAttributes()) {
				for (Relationship relationship : entitySet.getIncidentRelationships()) {
					
					try {
						moveAttributeToIncidentAssociationPreconditionsStrong(relationship,attribute,otherModelAttributes);
						List<Transformation> preconditions = moveAttributeToIncidentAssociationPreconditionsSoft(relationship,entitySet, model);

						Transformation transformation = TransformationFactory.getMoveAttrToIncidentAssociation(attribute, entitySet, (Association) relationship);
						transformation.setPreconditions(preconditions);
						
						target.add(transformation);
					} catch (PreconditionsNotMetException e) {
						PRECONDITION_NOT_MET_POOL.freeObject(e);
						continue;
					}
				}
			}
		}
	}
	
	private static void moveAttributeToIncidentAssociationPreconditionsStrong(Relationship relationship, Attribute attribute, Set<Attribute> otherModelAttributes) throws PreconditionsNotMetException {
		if (!(relationship instanceof Association)) {
			throw PRECONDITION_NOT_MET_POOL.getObject();
		}
		if (attribute.containsTransformationFlag(EnumTransformation.MOVE_ATTR_TO_INCIDENT_ENTITY_SET)) {
			throw PRECONDITION_NOT_MET_POOL.getObject();
		}
		if (!CollectionUtils.containsText(otherModelAttributes, attribute)) {
			throw PRECONDITION_NOT_MET_POOL.getObject();
		}
	}
	
	private static List<Transformation> moveAttributeToIncidentAssociationPreconditionsSoft(Relationship relationship, EntitySet entitySet, ERModel model) throws PreconditionsNotMetException {
		List<Transformation> preconditions = null;
		
		AssociationSide side = RelationshipUtils.getSide((Association)relationship, entitySet);
		if (!EnumRelationshipSideRole.MANY.equals(side.getRole())) {
			if (conditionalTransformation && !side.containsTransformationFlag(EnumTransformation.CHANGE_CARDINALITY) && !model.isExemplar()) {
				preconditions = Utils.addToList(preconditions,TransformationFactory.getChangeCardinality((Association) relationship, entitySet));
			} else {
				throw PRECONDITION_NOT_MET_POOL.getObject();
			}
		}
		
		return preconditions;
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

				target.add(TransformationFactory.getRebindNaryAssociation((Association) relationship, null, model.isExemplar() ? TRANSFORMABLE_FLAG_POOL.getObject() : null));
			}
		}
	}
	
	public static void getPossibleDecomposeAttributeTransformations(List<Transformation> target, ERModel model) {
		Utils.validateNotNull(target);
		Utils.validateNotNull(model);
		
		AttributeCompositionUtils.getAllComposedAttributes().forEach(attrText -> {
			model.getEntitySets().forEach(entitySet -> {
				if (entitySet.containsAttribute(attrText)) {
					List<String> parts = AttributeCompositionUtils.getAttributeParts(attrText);
					TransformableList transformableList = new TransformableList();
					transformableList.setElements(parts.stream().map(part -> new Attribute(part)).collect(Collectors.toList()));
					
					target.add(TransformationFactory.getDecomposeAttribute(entitySet, entitySet.getAttribute(attrText), transformableList, model.isExemplar() ? TRANSFORMABLE_FLAG_POOL.getObject() : null));
				}
			});
		});
	}
	
	public static void getPossibleMergeEntitySetsTransformations(List<Transformation> target, ERModel model) {
		Utils.validateNotNull(target);
		Utils.validateNotNull(model);
		
		for (int i = 0; i < model.getEntitySetsCount(); i++) {
			EntitySet entitySet1 = model.getEntitySets().get(i);
			if (entitySet1.containsTransformationFlag(EnumTransformation.EXTRACT_ATTR_TO_OWN_ENTITY_SET)) {
				continue;
			}
			for (int j = i; j < model.getEntitySetsCount(); j++) {
				EntitySet entitySet2 = model.getEntitySets().get(j);
				if (entitySet2.containsTransformationFlag(EnumTransformation.EXTRACT_ATTR_TO_OWN_ENTITY_SET)) {
					continue;
				}
				if (entitySet1 == entitySet2) {
					continue;
				}
				if (getEntitySetComparator().compareSymmetric(entitySet1, entitySet2) < SimilarityConstantsUtils.getEntitySetStrongSimilarityTreshold()) {
					continue;
				}
				
				target.add(TransformationFactory.getMergeEntitySets(entitySet1, entitySet2));
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
