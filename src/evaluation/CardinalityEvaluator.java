package evaluation;

import comparing.Mapping;
import entityRelationshipModel.EntityRelationshipModel;

public class CardinalityEvaluator implements ISpecificEvaluator{

    double WEIGHT;

    @Override
    public double evaluate(EntityRelationshipModel model1, EntityRelationshipModel model2, Mapping mapping) {
        return 0;
    }

    @Override
    public double getWeight() {
        return WEIGHT;
    }

    public CardinalityEvaluator(double WEIGHT) {
        this.WEIGHT = WEIGHT;
    }
}
