package evaluation;

import comparing.Mapping;
import entityRelationshipModel.EntityRelationshipModel;

public class AttributeEvaluator implements ISpecificEvaluator{

    double WEIGHT;

    @Override
    public double evaluate(EntityRelationshipModel model1, EntityRelationshipModel model2, Mapping mapping) {
        return 0;
    }

    @Override
    public double getWeight() {
        return WEIGHT;
    }

    public AttributeEvaluator(double WEIGHT) {
        this.WEIGHT = WEIGHT;
    }
}
