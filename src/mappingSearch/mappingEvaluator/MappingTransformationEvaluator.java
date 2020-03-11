package mappingSearch.mappingEvaluator;

import com.sun.org.apache.xerces.internal.xs.XSIDCDefinition;
import common.ModelUtils;
import comparing.Mapping;
import entityRelationshipModel.*;
import transformations.TransformationFactory;
import transformations.types.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author - Adam Trizna
 */
public class MappingTransformationEvaluator implements IMappingTransformationEvaluator {

    /**
     @inheritDoc
     */
    @Override
    public List<Transformation> getTransformationList(EntityRelationshipModel exemplarModel, EntityRelationshipModel studentsModel, Mapping mapping) {
        List<Transformation> transformationList = new ArrayList<>();
        List<EntitySet> unresolvedEntitySets = getEntitySetsUnion(exemplarModel,studentsModel);

        // check relationships
        transformationList.addAll(getRelationshipsTransformationList(exemplarModel.getRelationships(),studentsModel,mapping,true));
        transformationList.addAll(getRelationshipsTransformationList(studentsModel.getRelationships(),exemplarModel,mapping,false));

        // check attributes
        transformationList.addAll(getAttributesTransformationList(exemplarModel.getEntitySets(),mapping,true));
        transformationList.addAll(getAttributesTransformationList(studentsModel.getEntitySets(),mapping,false));

        // check "empty pairs"
        transformationList.addAll(getEmptyPairsTransformationList(exemplarModel.getEntitySets(),mapping,true));
        transformationList.addAll(getEmptyPairsTransformationList(studentsModel.getEntitySets(),mapping,false));

        return transformationList;
    }

    private List<Transformation> getRelationshipsTransformationList(List<Relationship> relationships, EntityRelationshipModel otherModel, Mapping mapping, boolean isExemplar) {
        List<Transformation> transformationList = new ArrayList<>();
        List<Relationship> usedRelationships = new ArrayList<>();

        nextRel: for (Relationship relationship : relationships) {
            EntitySet[] entitySets = new EntitySet[relationship.getSides().size()];
            int i = 0;
            for (RelationshipSide side : relationship.getSides()) {
                entitySets[i] = mapping.getImage(side.getEntitySet());
                i ++;
            }
            List<Relationship> oppositeRelationships = otherModel.getRelationshipsByEntitySets(entitySets);
            for (Relationship oppositeRelationship : oppositeRelationships) {
                if (ModelUtils.relationshipsAreEqual(relationship,oppositeRelationship,mapping,false) && !usedRelationships.contains(oppositeRelationship)) {
                    usedRelationships.add(oppositeRelationship);
                    continue nextRel;
                }
            }
            // relationship has no match
            Transformation transformation;
            if (isExemplar) {
                // create such relationship
                if (relationship instanceof Association) {
                    transformation = TransformationFactory.getTransformation(Transformation.CODE_ADD_ASSOCIATION);
                    transformation.setParameter(((Association) relationship).getAttributes(),Transformation_AddAssociation.ASSOCIATION_ATTRIBUTES);
                    transformation.setParameter(relationship.getName(),Transformation_AddAssociation.ASSOCIATION_NAME);
                    transformation.setParameter(createOppositeRelationshipSides(relationship.getSides(),mapping),Transformation_AddAssociation.ASSOCIATION_SIDES);
                } else {
                    transformation = TransformationFactory.getTransformation(Transformation.CODE_ADD_GENERALIZATION);
                    transformation.setParameter(relationship.getName(), Transformation_AddGeneralization.GENERALIZATION_NAME);
                    transformation.setParameter(createOppositeRelationshipSides(relationship.getSides(),mapping), Transformation_AddGeneralization.GENERALIZATION_SIDES);
                }
            } else {
                // remove relationship
                if (relationship instanceof Association) {
                    transformation = TransformationFactory.getTransformation(Transformation.CODE_REMOVE_ASSOCIATION);
                    transformation.setParameter(relationship, Transformation_RemoveAssociation.ASSOCIATION);
                } else {
                    transformation = TransformationFactory.getTransformation(Transformation.CODE_REMOVE_GENERALIZATION);
                    transformation.setParameter(relationship, Transformation_RemoveGeneralization.GENERALIZATION);
                }
            }
            transformationList.add(transformation);
        }

        return transformationList;
    }

    private List<RelationshipSide> createOppositeRelationshipSides(List<? extends RelationshipSide> relationshipSides, Mapping mapping) {
        List<RelationshipSide> sides = new ArrayList<>();
        for (RelationshipSide relationshipSide : relationshipSides) {
            if (relationshipSide instanceof AssociationSide) {
                sides.add(new AssociationSide(mapping.getImage(relationshipSide.getEntitySet()),((AssociationSide) relationshipSide).getCardinality()));
            } else {
                sides.add(new GeneralizationSide(mapping.getImage(relationshipSide.getEntitySet()),((GeneralizationSide)relationshipSide).getRole()));
            }
        }

        return sides;
    }

    private List<Transformation> getAttributesTransformationList(List<EntitySet> entitySets, Mapping mapping, boolean isExemplar) {
        List<Transformation> transformationList = new ArrayList<>();
        for (EntitySet entitySet : entitySets) {
            EntitySet image = mapping.getImage(entitySet);

            for (String attribute : entitySet.getAttributes()) {
                if (!image.getAttributes().contains(attribute)) {
                    Transformation transformation;
                    if (isExemplar) {
                        // add attribute
                        transformation = TransformationFactory.getTransformation(Transformation.CODE_ADD_ATTRIBUTE);
                        transformation.setParameter(attribute,Transformation_AddAttribute.ATTRIBUTE_NAME);
                        transformation.setParameter(image,Transformation_AddAttribute.ENTITY_SET);
                    } else {
                        // remove attribute
                        transformation = TransformationFactory.getTransformation(Transformation.CODE_REMOVE_ATTRIBUTE);
                        transformation.setParameter(attribute,Transformation_AddAttribute.ATTRIBUTE_NAME);
                        transformation.setParameter(entitySet,Transformation_AddAttribute.ENTITY_SET);
                    }
                    transformationList.add(transformation);
                }
            }
        }
        return transformationList;
    }

    private List<Transformation> getEmptyPairsTransformationList(List<EntitySet> entitySets, Mapping mapping, boolean isExemplar) {
        List<Transformation> transformationList = new ArrayList<>();

        for(EntitySet entitySet : entitySets) {
            EntitySet image = mapping.getImage(entitySet);
            if (image.isEmpty()) {
                Transformation transformation;
                if (isExemplar) {
                    // add entity set
                    transformation = TransformationFactory.getTransformation(Transformation.CODE_ADD_ENTITY_SET);
                    transformation.setParameter(entitySet.getName(),Transformation_AddEntitySet.ENTITY_SET_NAME);
                    transformation.setParameter(entitySet.getAttributes(),Transformation_AddEntitySet.ENTITY_SET_ATTRIBUTES);
                } else {
                    // remove entity set
                    transformation = TransformationFactory.getTransformation(Transformation.CODE_REMOVE_ENTITY_SET);
                    transformation.setParameter(entitySet,Transformation_RemoveEntitySet.ENTITY_SET);
                }
                transformationList.add(transformation);
            }
        }

        return transformationList;
    }

    private List<EntitySet> getEntitySetsUnion(EntityRelationshipModel exemplarModel, EntityRelationshipModel studentsModel) {
        List<EntitySet> entitySetsUnion = new ArrayList<>();

        entitySetsUnion.addAll(exemplarModel.getEntitySets());
        entitySetsUnion.addAll(studentsModel.getEntitySets());

        return entitySetsUnion;
    }

    private List<Relationship> getRelationshipsUnion(EntityRelationshipModel exemplarModel, EntityRelationshipModel studentsModel) {
        List<Relationship> relationshipsUnion = new ArrayList<>();

        relationshipsUnion.addAll(exemplarModel.getRelationships());
        relationshipsUnion.addAll(studentsModel.getRelationships());

        return relationshipsUnion;
    }
}
