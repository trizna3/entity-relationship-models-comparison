package evaluation;

import comparing.Mapping;
import entityRelationshipModel.*;
import transformations.types.*;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author - Adam Trizna
 */
public class TransformationEvaluator implements ISpecificEvaluator {

    double WEIGHT;

    public TransformationEvaluator(double WEIGHT) {
        this.WEIGHT = WEIGHT;
    }

    private static final Map<String,Double> transformationPenalties = new HashMap<>();
    static {
        transformationPenalties.put(Transformation.CODE_ADD_ASSOCIATION, 1d);
        transformationPenalties.put(Transformation.CODE_ADD_GENERALIZATION, 1d);
        transformationPenalties.put(Transformation.CODE_ADD_ENTITY_SET, 1d);
        transformationPenalties.put(Transformation.CODE_ADD_ATTRIBUTE, 1d);
        transformationPenalties.put(Transformation.CODE_REMOVE_ASSOCIATION, 1d);
        transformationPenalties.put(Transformation.CODE_REMOVE_GENERALIZATION, 1d);
        transformationPenalties.put(Transformation.CODE_REMOVE_ENTITY_SET, 1d);
        transformationPenalties.put(Transformation.CODE_REMOVE_ATTRIBUTE, 1d);
    }

    public Map<String, Double> getTransformationPenalties() {
        return transformationPenalties;
    }

    @Override
    public double getWeight() {
        return WEIGHT;
    }

    /**
     * @param exemplarModel
     * @param studentsModel
     * @param mapping
     * @return Penalty value for used transformations
     */
    @Override
    public double evaluate(EntityRelationshipModel exemplarModel, EntityRelationshipModel studentsModel, Mapping mapping) {
        double penalty = 0;

        // create students model clone, create (entity-set & relationship) mappings of student's old and new model
        Map<EntitySet,EntitySet> studentsModelEntitySetMapping = new HashMap<>();
        Map<Relationship,Relationship> studentsModelRelationshipMapping = new HashMap<>();
        EntityRelationshipModel studentsModelClone = cloneEntityRelationshipModel(studentsModel,studentsModelEntitySetMapping,studentsModelRelationshipMapping);

        // execute all given transformations on the students model
        for (Transformation transformation : mapping.getTransformations()) {
            transformation.doTransformation(studentsModel);
        }

        // pass both student models and both mappings to TransformationEqualityChecker to get non-equivalent transformations
        List<Transformation> nonEquivalentTransformations =
                TransformationEqualityChecker.getNonEqualTransformations(
                        studentsModelClone,
                        studentsModel,
                        mapping.getTransformations(),
                        studentsModelEntitySetMapping,
                        studentsModelRelationshipMapping
                );

        // penalize non-equivalent transformations
        for (Transformation transformation : nonEquivalentTransformations) {
            penalty += penalizeTransformation(transformation);
        }
        return penalty;
    }

    /**
     * Computes penalty for used transformation, based on transformation type.
     * @param transformation
     * @return penalty value
     */
    private double penalizeTransformation(Transformation transformation) {

        if (transformation instanceof Transformation_AddAssociation) {
            return transformationPenalties.get(Transformation.CODE_ADD_ASSOCIATION);
        }
        else if (transformation instanceof Transformation_AddGeneralization) {
            return transformationPenalties.get(Transformation.CODE_ADD_GENERALIZATION);
        }
        else if (transformation instanceof Transformation_AddEntitySet) {
            return transformationPenalties.get(Transformation.CODE_ADD_ENTITY_SET);
        }
        else if (transformation instanceof Transformation_AddAttribute) {
            return transformationPenalties.get(Transformation.CODE_ADD_ATTRIBUTE);
        }
        else if (transformation instanceof Transformation_RemoveAssociation) {
            return transformationPenalties.get(Transformation.CODE_REMOVE_ASSOCIATION);
        }
        else if (transformation instanceof Transformation_RemoveGeneralization) {
            return transformationPenalties.get(Transformation.CODE_REMOVE_GENERALIZATION);
        }
        else if (transformation instanceof Transformation_RemoveEntitySet) {
            return transformationPenalties.get(Transformation.CODE_REMOVE_ENTITY_SET);
        }
        else if (transformation instanceof Transformation_RemoveAttribute) {
            return transformationPenalties.get(Transformation.CODE_REMOVE_ATTRIBUTE);
        }
        else {
            throw new IllegalArgumentException("unknown transformation");
        }
    }

    /**
     *
     * @param actualModel - source model
     * @param entitySetMapping - empty map instance for entity set mapping (between source model and cloned model)
     * @param relationshipMapping - empty map instance for relationship mapping (between source model and cloned model)
     * @return create entity relationship model clone
     */
    private EntityRelationshipModel cloneEntityRelationshipModel(EntityRelationshipModel actualModel, Map<EntitySet, EntitySet> entitySetMapping, Map<Relationship,Relationship> relationshipMapping) {
        EntityRelationshipModel modelClone = new EntityRelationshipModel();

        for (EntitySet actualEntitySet : actualModel.getEntitySets()) {
            EntitySet cloneEntitySet = new EntitySet(actualEntitySet.getName(),new ArrayList<>(actualEntitySet.getAttributes()));
            modelClone.addEntitySet(cloneEntitySet);

            entitySetMapping.put(actualEntitySet,cloneEntitySet);
            entitySetMapping.put(cloneEntitySet,actualEntitySet);
        }

        for (Relationship relationship : actualModel.getRelationships()) {
            Relationship newRelationship;
            if (relationship instanceof Association) {
                List<AssociationSide> sides = new ArrayList<>();
                for (AssociationSide side : ((Association)relationship).getSides()) {
                    sides.add(new AssociationSide(entitySetMapping.get(side.getEntitySet()),side.getCardinality()));
                }
                newRelationship = new Association(sides,new ArrayList<>(((Association) relationship).getAttributes()));

            } else {
                newRelationship = new Generalization(
                        entitySetMapping.get(((Generalization) relationship).getSuperEntitySet()),
                        entitySetMapping.get(((Generalization) relationship).getSubEntitySet())
                );
            }
            modelClone.addRelationship(newRelationship);
            relationshipMapping.put(relationship,newRelationship);
            relationshipMapping.put(newRelationship,relationship);
        }

        return modelClone;
    }
}
