package evaluation;

import comparing.Mapping;
import entityRelationshipModel.EntityRelationshipModel;

public class MainEvaluator implements IEvaluator{

    EntitySetEvaluator entitySetEvaluator;
    AttributeEvaluator attributeEvaluator;
    CardinalityEvaluator cardinalityEvaluator;
    RelationshipEvaluator relationshipEvaluator;

    public EntitySetEvaluator getEntitySetEvaluator() {
        if (entitySetEvaluator == null) {
            entitySetEvaluator = new EntitySetEvaluator();
        }
        return entitySetEvaluator;
    }

    public AttributeEvaluator getAttributeEvaluator() {
        if (attributeEvaluator == null) {
            attributeEvaluator = new AttributeEvaluator();
        }
        return attributeEvaluator;
    }

    public CardinalityEvaluator getCardinalityEvaluator() {
        if (cardinalityEvaluator == null) {
            cardinalityEvaluator = new CardinalityEvaluator();
        }
        return cardinalityEvaluator;
    }

    public RelationshipEvaluator getRelationshipEvaluator() {
        if (relationshipEvaluator == null) {
            relationshipEvaluator = new RelationshipEvaluator();
        }
        return relationshipEvaluator;
    }

    /**
     * Computes weighted sum of each evaluation part (done by specific evaluators).
     * @param model1
     * @param model2
     * @param mapping
     * @return
     */
    @Override
    public double evaluate(EntityRelationshipModel model1, EntityRelationshipModel model2, Mapping mapping) {
        if (model1 == null || model2 == null || mapping == null) {
            throw new IllegalArgumentException("Cannot evaluate mapping - model1, model2 or mapping is null!");
        }
        double entitySetEvaluation = getEntitySetEvaluator().evaluate(model1,model2,mapping);
        double attributeEvaluation = getAttributeEvaluator().evaluate(model1,model2,mapping);
        double cardinalityEvaluation = getCardinalityEvaluator().evaluate(model1,model2,mapping);
        double relationshipEvaluation = getRelationshipEvaluator().evaluate(model1,model2,mapping);

        return 0;
    }
}
