package evaluation;

import comparing.Mapping;
import entityRelationshipModel.EntityRelationshipModel;

public class RelationshipEvaluator implements ISpecificEvaluator {

    double WEIGHT;

    @Override
    public double evaluate(EntityRelationshipModel model1, EntityRelationshipModel model2, Mapping mapping) {
        return 0;
    }

    @Override
    public double getWeight() {
        return WEIGHT;
    }

    public RelationshipEvaluator(double WEIGHT) {
        this.WEIGHT = WEIGHT;
    }
}
