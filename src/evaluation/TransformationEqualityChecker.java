package evaluation;

/**
 * @author - Adam Trizna
 */

import common.CollectionUtils;
import common.Enums;
import common.ModelUtils;
import comparing.Mapping;
import entityRelationshipModel.*;
import transformations.types.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Agent for checking, whether a series of transformations represent an equal or non-equal model structure modification.
 * The equality check means, whether the transformations made solely structural difference, but kept the same model's ability to store data.
 */
public class TransformationEqualityChecker {

    /**
     * Takes student's model before and after transformation list application.
     * Compares given models, using entity-sets and relationships mappings between the models.
     * Searches for specific scenarios, when models are different in structure but equal in ability to store data.
     * Returns only those transformations (out of the 'transformationsMade' list) which do not play any role in none of the equality scenarios.
     *
     * @param modelBefore = student's model before transformation list application
     * @param modelAfter = student's model after transformation list application
     * @param transformationsMade = instances of the transformations applied
     * @param entitySetMapping = entity-set mapping between the old and the new model
     * @param relationshipMapping = relationship mapping between the old and the new model
     * @return non-equal transformations list
     */
    public static List<Transformation> getNonEqualTransformations(
            EntityRelationshipModel modelBefore,
            EntityRelationshipModel modelAfter,
            List<Transformation> transformationsMade,
            Map<EntitySet,EntitySet> entitySetMapping,
            Map<Relationship,Relationship> relationshipMapping
    ) {
        List<Transformation> nonEqualTransformations = new ArrayList<>(transformationsMade);

        nonEqualTransformations.removeAll(
                getEqualTransformationsByRelationships(modelAfter, modelBefore, transformationsMade, entitySetMapping, relationshipMapping, true)
        );
        nonEqualTransformations.removeAll(
                getEqualTransformationsByRelationships(modelBefore, modelAfter, transformationsMade, entitySetMapping, relationshipMapping, false)
        );

        return nonEqualTransformations;
    }

    /**
     * Iterates over relationships of one given model, searching for structural difference.
     * When it finds one, checks whether it's one of the known equality cases.
     * If yes, uses model elements mappings and given transformations references to get the transformation instances which are responsible for the equality scenario.
     *
     * @param thisModel = entity relationship model to iterate over
     * @param otherModel = the opposite entity relationship model
     * @param transformationsMade = see {@link #getNonEqualTransformations(EntityRelationshipModel, EntityRelationshipModel, List, Map, Map)}
     * @param entitySetMapping = see {@link #getNonEqualTransformations(EntityRelationshipModel, EntityRelationshipModel, List, Map, Map)}
     * @param relationshipMapping = see {@link #getNonEqualTransformations(EntityRelationshipModel, EntityRelationshipModel, List, Map, Map)}
     * @param isTransformed = whether the 'thisModel' (model to iterate over) is the one after the transformation application
     * @return all transformations which are responsible for any equality scenarios.
     */
    private static List<Transformation> getEqualTransformationsByRelationships(
            EntityRelationshipModel thisModel,
            EntityRelationshipModel otherModel,
            List<Transformation> transformationsMade,
            Map<EntitySet,EntitySet> entitySetMapping,
            Map<Relationship,Relationship> relationshipMapping,
            boolean isTransformed
    ) {
        List<Transformation> equalTransformations = new ArrayList<>();
        List<Relationship> usedRelationships = new ArrayList<>();

        nextRel: for (Relationship relationship : thisModel.getRelationships()) {
            EntitySet[] oppositeEntitySets = new EntitySet[relationship.getSides().size()];
            int i = 0;
            boolean allEntitySetsAreMapped = true;  // meaning all entity sets have non-null mapping image, or their mapping image wasn't removed due to transformations
            for (RelationshipSide side : relationship.getSides()) {
                oppositeEntitySets[i] = entitySetMapping.get(side.getEntitySet());
                if (oppositeEntitySets[i] == null || !otherModel.contains(oppositeEntitySets[i])) {
                    allEntitySetsAreMapped = false;
                }
                i ++;
            }

            if (allEntitySetsAreMapped) {
                List<Relationship> oppositeRelationships = otherModel.getRelationshipsByEntitySets(oppositeEntitySets);
                for (Relationship oppositeRelationship : oppositeRelationships) {
                    if (ModelUtils.relationshipsAreEqual(relationship, oppositeRelationship, new Mapping(entitySetMapping), false) && !usedRelationships.contains(oppositeRelationship)) {
                        usedRelationships.add(oppositeRelationship);
                        continue nextRel;
                    }
                }
            }

            // relationship has no match
            if (relationship instanceof Association) {
                Association association = (Association) relationship;

                // check MANY:MANY <--> ONE:MANY - MANY:ONE equality
                if (association.isBinary()
                    && Enums.CARDINALITY_MANY.equals(association.getFirstSide().getCardinality())
                    && Enums.CARDINALITY_MANY.equals(association.getSecondSide().getCardinality())
                ) {
                    EntitySet firstImage = entitySetMapping.get(association.getFirstSide().getEntitySet());
                    EntitySet secondImage = entitySetMapping.get(association.getSecondSide().getEntitySet());

                    // there must be a joining entity set between the two images
                    for (EntitySet joiningEntitySet : otherModel.getEntitySets()) {
                        Optional<Association> optionalImage1Association = getOptionalJoiningAssociation(firstImage, joiningEntitySet, otherModel);
                        Optional<Association> optionalImage2Association = getOptionalJoiningAssociation(secondImage, joiningEntitySet, otherModel);

                        // both associations must be present
                        // joining entity set mustn't have any other relationships
                        // joining entity set (from other model) doesn't have an existing mapping image in the first model (if it does, it wasn't created due to transformation)
                        if (optionalImage1Association.isPresent() &&
                            optionalImage2Association.isPresent() &&
                            otherModel.getRelationshipsByEntitySets(new EntitySet[]{joiningEntitySet}).size() == 2
                                &&
                            !thisModel.contains(entitySetMapping.get(joiningEntitySet))
                        ) {
                            // 'relationship' --> is a subject of an AddAssociation transformation
                            equalTransformations.add(CollectionUtils.getFirst(
                                    transformationsMade,
                                    Transformation_AddAssociation.class,
                                    transformation -> relationship.equals(transformation.getAssociation()))
                            );
                            // entitySetMapping.get(joiningEntitySet) --> is a subject of a RemoveEntitySet transformation
                            equalTransformations.add(CollectionUtils.getFirst(
                                    transformationsMade,
                                    Transformation_RemoveEntitySet.class,
                                    transformation -> entitySetMapping.get(joiningEntitySet).equals(transformation.getEntitySet()))
                            );
                            // relationshipMapping.get(optionalImage1Association.get()) --> is a subject of a RemoveAssociation transformation
                            equalTransformations.add(CollectionUtils.getFirst(
                                    transformationsMade,
                                    Transformation_RemoveAssociation.class,
                                    transformation -> relationshipMapping.get(optionalImage1Association.get()).equals(transformation.getAssociation()))
                            );
                            // relationshipMapping.get(optionalImage2Association.get()) --> is a subject of a RemoveAssociation transformation
                            equalTransformations.add(CollectionUtils.getFirst(
                                    transformationsMade,
                                    Transformation_RemoveAssociation.class,
                                    transformation -> relationshipMapping.get(optionalImage2Association.get()).equals(transformation.getAssociation()))
                            );
                        }
                    }
                }
            }
        }
        return equalTransformations;
    }

    private static Optional<Association> getOptionalJoiningAssociation(EntitySet image, EntitySet joiningEntitySet, EntityRelationshipModel otherModel) {
        return  otherModel.getRelationshipsByEntitySets(new EntitySet[]{image,joiningEntitySet})
                .stream()
                .filter(association1 ->
                        association1 instanceof Association &&
                        Enums.CARDINALITY_MANY.equals(((Association) association1).getCardinality(joiningEntitySet)) &&
                        Enums.CARDINALITY_ONE.equals(((Association) association1).getCardinality(image))
                ).map(rel -> (Association) rel)
                .findAny();
    }
}