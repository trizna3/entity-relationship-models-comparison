package mappingSearch.mappingEvaluator;

import comparing.Mapping;
import entityRelationshipModel.EntityRelationshipModel;
import entityRelationshipModel.EntitySet;
import evaluation.IEvaluator;
import evaluation.MainEvaluator;

/**
 * @author - Adam Trizna
 */
public class BestMappingEvaluator implements IBestMappingEvaluator {

    Mapping bestMapping;
    double bestPenalty;
    IEvaluator evaluator;

    @Override
    public void evaluate(EntityRelationshipModel exemplarModel, EntityRelationshipModel studentModel, Mapping mapping) {

        double penalty = getEvaluator().evaluate(exemplarModel,studentModel,mapping);

        if (bestMapping == null || penalty < bestPenalty) {
            bestPenalty = penalty;
            saveBestMapping(mapping);
        }
    }

    @Override
    public Mapping getBestMapping() {
        return bestMapping;
    }

    @Override
    public double getBestMappingPenalty() {
        return bestPenalty;
    }

    private IEvaluator getEvaluator() {
        if (evaluator == null) {
            evaluator = new MainEvaluator();
        }
        return evaluator;
    }

    /**
     * Clones given mapping.
     * @param mapping
     */
    private void saveBestMapping(Mapping mapping) {
        bestMapping = new Mapping();
        for (EntitySet entitySet : mapping.getDistinctAllEntitySets()) {
            bestMapping.map(entitySet,mapping.getImage(entitySet));
        }
    }
}
