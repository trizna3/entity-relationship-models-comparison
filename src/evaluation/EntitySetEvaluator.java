package evaluation;

import comparing.Mapping;
import entityRelationshipModel.EntityRelationshipModel;

public class EntitySetEvaluator implements ISpecificEvaluator{
    @Override
    public double evaluate(EntityRelationshipModel model1, EntityRelationshipModel model2, Mapping mapping) {
        return 0;
    }

    @Override
    public double getWeight() {
        return 0;
    }
}
