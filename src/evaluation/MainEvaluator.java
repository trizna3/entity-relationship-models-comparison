package evaluation;

import comparing.Mapping;
import entityRelationshipModel.EntityRelationshipModel;

public class MainEvaluator implements IEvaluator{

    double ES_WEIGHT = 1;
    double ATTR_WEIGHT = 1;
    double R_WEIGHT = 1;

    EntitySetEvaluator entitySetEvaluator;
    AttributeEvaluator attributeEvaluator;
    RelationshipEvaluator relationshipEvaluator;

    public EntitySetEvaluator getEntitySetEvaluator() {
        if (entitySetEvaluator == null) {
            entitySetEvaluator = new EntitySetEvaluator(ES_WEIGHT);
        }
        return entitySetEvaluator;
    }

    public AttributeEvaluator getAttributeEvaluator() {
        if (attributeEvaluator == null) {
            attributeEvaluator = new AttributeEvaluator(ATTR_WEIGHT);
        }
        return attributeEvaluator;
    }

    public RelationshipEvaluator getRelationshipEvaluator() {
        if (relationshipEvaluator == null) {
            relationshipEvaluator = new RelationshipEvaluator(R_WEIGHT);
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
        double relationshipEvaluation = getRelationshipEvaluator().evaluate(model1,model2,mapping);

        System.out.println("entity set evaluation = " + entitySetEvaluation);
        System.out.println("attribute evaluation = " + attributeEvaluation);
        System.out.println("relationship evaluation = " + relationshipEvaluation);

        return  Math.pow(entitySetEvaluation,getEntitySetEvaluator().getWeight()) +
                Math.pow(attributeEvaluation,getAttributeEvaluator().getWeight()) +
                Math.pow(relationshipEvaluation,getRelationshipEvaluator().getWeight());
    }
}
