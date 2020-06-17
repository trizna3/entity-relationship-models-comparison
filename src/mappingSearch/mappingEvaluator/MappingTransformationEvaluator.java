package mappingSearch.mappingEvaluator;

import java.util.ArrayList;
import java.util.List;

import common.ArrayUtils;
import common.ERModelUtils;
import comparing.Mapping;
import entityRelationshipModel.Association;
import entityRelationshipModel.AssociationSide;
import entityRelationshipModel.ERModel;
import entityRelationshipModel.EntitySet;
import entityRelationshipModel.GeneralizationSide;
import entityRelationshipModel.Relationship;
import entityRelationshipModel.RelationshipSide;
import transformations.TransformationFactory;
import transformations.types.Transformation;
import transformations.types.Transformation_AddAssociation;
import transformations.types.Transformation_AddAttribute;
import transformations.types.Transformation_AddEntitySet;
import transformations.types.Transformation_AddGeneralization;
import transformations.types.Transformation_RemoveAssociation;
import transformations.types.Transformation_RemoveEntitySet;
import transformations.types.Transformation_RemoveGeneralization;

/**
 * @author - Adam Trizna
 */
public class MappingTransformationEvaluator implements IMappingTransformationEvaluator {

	/**
	 * @inheritDoc
	 */
	@Override
	public List<Transformation> getTransformationList(ERModel exemplarModel, ERModel studentsModel, Mapping mapping) {
		List<Transformation> transformationList = new ArrayList<>();

		// check relationships
		transformationList.addAll(getRelationshipsTransformationList(exemplarModel.getRelationships(), studentsModel, mapping, true));
		transformationList.addAll(getRelationshipsTransformationList(studentsModel.getRelationships(), exemplarModel, mapping, false));

		// check "empty pairs"
		transformationList.addAll(getEmptyPairsTransformationList(exemplarModel.getEntitySets(), mapping, true));
		transformationList.addAll(getEmptyPairsTransformationList(studentsModel.getEntitySets(), mapping, false));

		// check attributes
		transformationList.addAll(getAttributesTransformationList(exemplarModel.getEntitySets(), mapping, true));
		transformationList.addAll(getAttributesTransformationList(studentsModel.getEntitySets(), mapping, false));

		return transformationList;
	}

	private List<Transformation> getRelationshipsTransformationList(List<Relationship> relationships, ERModel otherModel, Mapping mapping,
			boolean isExemplar) {
		List<Transformation> transformationList = new ArrayList<>();
		List<Relationship> usedRelationships = new ArrayList<>();

		nextRel: for (Relationship relationship : relationships) {
			EntitySet[] entitySets = new EntitySet[relationship.getSides().length];
			int i = 0;
			boolean allEntitySetsAreMapped = true; // meaning all entity sets have non-empty mapping image
			for (RelationshipSide side : relationship.getSides()) {
				entitySets[i] = mapping.getImage(side.getEntitySet());
				if (entitySets[i].isEmpty()) {
					allEntitySetsAreMapped = false;
				}
				i++;
			}
			if (allEntitySetsAreMapped) {
				List<Relationship> oppositeRelationships = ERModelUtils.getRelationshipsByEntitySets(otherModel, entitySets);
				for (Relationship oppositeRelationship : oppositeRelationships) {
					if (ERModelUtils.relationshipsAreEqual(relationship, oppositeRelationship, mapping, false)
							&& !usedRelationships.contains(oppositeRelationship)) {
						usedRelationships.add(oppositeRelationship);
						continue nextRel;
					}
				}
			}
			// relationship has no match
			Transformation transformation;
			if (isExemplar) {
				// create such relationship
				if (relationship instanceof Association) {
					transformation = TransformationFactory.getTransformation(Transformation.CODE_ADD_ASSOCIATION);
					transformation.setParameter(Transformation_AddAssociation.ASSOCIATION_ATTRIBUTES, ((Association) relationship).getAttributes());
					transformation.setParameter(Transformation_AddAssociation.ASSOCIATION_NAME, relationship.getName());
					transformation.setParameter(Transformation_AddAssociation.ASSOCIATION_SIDES,
							createOppositeRelationshipSides(relationship.getSides(), mapping));
				} else {
					transformation = TransformationFactory.getTransformation(Transformation.CODE_ADD_GENERALIZATION);
					transformation.setParameter(Transformation_AddGeneralization.GENERALIZATION_NAME, relationship.getName());
					transformation.setParameter(Transformation_AddGeneralization.GENERALIZATION_SIDES,
							createOppositeRelationshipSides(relationship.getSides(), mapping));
				}
			} else {
				// remove relationship
				if (relationship instanceof Association) {
					transformation = TransformationFactory.getTransformation(Transformation.CODE_REMOVE_ASSOCIATION);
					transformation.setParameter(Transformation_RemoveAssociation.ASSOCIATION, relationship);
				} else {
					transformation = TransformationFactory.getTransformation(Transformation.CODE_REMOVE_GENERALIZATION);
					transformation.setParameter(Transformation_RemoveGeneralization.GENERALIZATION, relationship);
				}
			}
			transformationList.add(transformation);
		}

		return transformationList;
	}

	private List<RelationshipSide> createOppositeRelationshipSides(RelationshipSide[] relationshipSides, Mapping mapping) {
		List<RelationshipSide> sides = new ArrayList<>();
		for (RelationshipSide relationshipSide : relationshipSides) {
			if (relationshipSide instanceof AssociationSide) {
				sides.add(new AssociationSide(mapping.getImage(relationshipSide.getEntitySet()), ((AssociationSide) relationshipSide).getRole()));
			} else {
				sides.add(
						new GeneralizationSide(mapping.getImage(relationshipSide.getEntitySet()), ((GeneralizationSide) relationshipSide).getRole()));
			}
		}

		return sides;
	}

	private List<Transformation> getAttributesTransformationList(List<EntitySet> entitySets, Mapping mapping, boolean isExemplar) {
		List<Transformation> transformationList = new ArrayList<>();
		for (EntitySet entitySet : entitySets) {
			EntitySet image = mapping.getImage(entitySet);

			for (String attribute : entitySet.getAttributes()) {
				if (!ArrayUtils.contains(image.getAttributes(), attribute)) {
					Transformation transformation;
					if (isExemplar) {
						// add attribute
						transformation = TransformationFactory.getTransformation(Transformation.CODE_ADD_ATTRIBUTE);
						transformation.setParameter(Transformation_AddAttribute.ATTRIBUTE_NAME, attribute);
						transformation.setParameter(Transformation_AddAttribute.ENTITY_SET, image);
					} else {
						// remove attribute
						transformation = TransformationFactory.getTransformation(Transformation.CODE_REMOVE_ATTRIBUTE);
						transformation.setParameter(Transformation_AddAttribute.ATTRIBUTE_NAME, attribute);
						transformation.setParameter(Transformation_AddAttribute.ENTITY_SET, entitySet);
					}
					transformationList.add(transformation);
				}
			}
		}
		return transformationList;
	}

	private List<Transformation> getEmptyPairsTransformationList(List<EntitySet> entitySets, Mapping mapping, boolean isExemplar) {
		List<Transformation> transformationList = new ArrayList<>();

		for (EntitySet entitySet : entitySets) {
			EntitySet image = mapping.getImage(entitySet);
			if (image.isEmpty()) {
				Transformation transformation;
				if (isExemplar) {
					// add entity set
					transformation = TransformationFactory.getTransformation(Transformation.CODE_ADD_ENTITY_SET);
					// make the empty image a normal entity set
					image.setEmpty(false);
					image.setName(entitySet.getName());
					// set it as transformation input for addition to the model
					transformation.setParameter(Transformation_AddEntitySet.ENTITY_SET, image);
				} else {
					// remove entity set
					transformation = TransformationFactory.getTransformation(Transformation.CODE_REMOVE_ENTITY_SET);
					transformation.setParameter(Transformation_RemoveEntitySet.ENTITY_SET, entitySet);
				}
				transformationList.add(transformation);
			}
		}

		return transformationList;
	}
}
