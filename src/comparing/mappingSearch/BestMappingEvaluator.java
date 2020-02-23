package comparing.mappingSearch;

import comparing.Mapping;
import entityRelationshipModel.EntityRelationshipModel;
import evaluation.IEvaluator;

/**
 * @author - Adam Trizna
 */
public class BestMappingEvaluator implements IBestMappingEvaluator {

    Mapping bestMapping;
    double bestPenalty;
    IEvaluator evaluator;

    @Override
    public void evaluate(EntityRelationshipModel exemplarModel, EntityRelationshipModel studentModel, Mapping mapping) {

        double penalty = evaluator.evaluate(exemplarModel,studentModel,mapping);

        if (bestMapping == null || penalty < bestPenalty) {
            bestPenalty = penalty;
            bestMapping = mapping;
        }
    }

    @Override
    public Mapping getBestMapping() {
        return bestMapping;
    }
}
