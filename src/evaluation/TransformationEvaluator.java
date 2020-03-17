package evaluation;

import comparing.Mapping;
import entityRelationshipModel.*;
import transformations.types.*;

import java.util.*;

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

        // create students model clone, create enityset-mapping of students old and new model
        EntityRelationshipModel studentsModelClone = new EntityRelationshipModel();
        Map<EntitySet,EntitySet> studentsModelMapping = cloneEntityRelationshipModel(studentsModel,studentsModelClone);

        // execute all given transformations on the students model
        for (Transformation transformation : mapping.getTransformations()) {
            transformation.doTransformation(studentsModel);
        }

        // pass both student models to TransformationEqualityChecker to get non-equivalent transformations
        List<Transformation> nonEquivalentTransformations =
                TransformationEqualityChecker.getNonEqualTransformations(
                        studentsModelClone,
                        studentsModel,
                        mapping.getTransformations(),
                        studentsModelMapping
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
     * @param actualModel - actualModel
     * @param cloneModelInstance - empty instance of new model
     * @return create enitySet-mapping of old and new model
     */

    private Map<EntitySet, EntitySet> cloneEntityRelationshipModel(EntityRelationshipModel actualModel, EntityRelationshipModel cloneModelInstance) {
        Map<EntitySet,EntitySet> mapping = new HashMap<>();

        for (EntitySet actualEntitySet : actualModel.getEntitySets()) {
            EntitySet cloneEntitySet = new EntitySet(actualEntitySet.getName(),new ArrayList<>(actualEntitySet.getAttributes()));
            cloneModelInstance.addEntitySet(cloneEntitySet);

            mapping.put(actualEntitySet,cloneEntitySet);
            mapping.put(cloneEntitySet,actualEntitySet);
        }

        for (Relationship relationship : actualModel.getRelationships()) {
            if (relationship instanceof Association) {
                List<AssociationSide> sides = new ArrayList<>();
                for (AssociationSide side : ((Association)relationship).getSides()) {
                    sides.add(new AssociationSide(mapping.get(side.getEntitySet()),side.getCardinality()));
                }
                cloneModelInstance.addRelationship(new Association(sides,new ArrayList<>(((Association) relationship).getAttributes())));
            } else if (relationship instanceof Generalization) {
                cloneModelInstance.addRelationship(new Generalization(
                        mapping.get(((Generalization) relationship).getSuperEntitySet()),
                        mapping.get(((Generalization) relationship).getSubEntitySet())
                ));
            }
        }

        return mapping;
    }
}
