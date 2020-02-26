package evaluation;

import comparing.Mapping;
import comparing.TransformationPack;
import entityRelationshipModel.EntityRelationshipModel;
import transformations.*;

import java.util.HashMap;
import java.util.Map;

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
        transformationPenalties.put(Transformation.CODE_1NN1_TO_NN, 4d);
        transformationPenalties.put(Transformation.CODE_NN_TO_1NN1, 4d);
        transformationPenalties.put(Transformation.CODE_ADD_ASSOCIATION, 1d);
        transformationPenalties.put(Transformation.CODE_ADD_GENERALIZATION, 1d);
        transformationPenalties.put(Transformation.CODE_ADD_ENTITY_SET, 1d);
    }


    public Map<String, Double> getTransformationPenalties() {
        return transformationPenalties;
    }

    @Override
    public double getWeight() {
        return WEIGHT;
    }

    /**
     * @param model1
     * @param model2
     * @param mapping
     * @return Penalty value for used transformations.
     */
    @Override
    public double evaluate(EntityRelationshipModel model1, EntityRelationshipModel model2, Mapping mapping) {
        double penalty = 0;
        for (TransformationPack transformationPack : mapping.getTransformationPacks()) {
            penalty += penalizeTransformationPack(transformationPack);
        }
        return penalty;
    }


    private double penalizeTransformationPack(TransformationPack transformationPack) {
        double penalty = 0;
        for (Transformation transformation : transformationPack) {
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
        if (transformation instanceof Transformation_1NN1_TO_NN) {
            return transformationPenalties.get(Transformation.CODE_1NN1_TO_NN);
        }
        else if (transformation instanceof Transformation_NN_TO_1NN1) {
            return transformationPenalties.get(Transformation.CODE_NN_TO_1NN1);
        }
        else if (transformation instanceof Transformation_AddAssociation) {
            return transformationPenalties.get(Transformation.CODE_ADD_ASSOCIATION);
        }
        else if (transformation instanceof Transformation_AddGeneralization) {
            return transformationPenalties.get(Transformation.CODE_ADD_GENERALIZATION);
        }
        else if (transformation instanceof Transformation_AddEntitySet) {
            return transformationPenalties.get(Transformation.CODE_ADD_ENTITY_SET);
        }
        else {
            throw new IllegalArgumentException("unknown transformation");
        }
    }
}
