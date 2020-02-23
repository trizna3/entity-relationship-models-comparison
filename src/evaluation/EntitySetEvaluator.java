package evaluation;

import comparing.Mapping;
import entityRelationshipModel.EntityRelationshipModel;
import entityRelationshipModel.EntitySet;
import languageProcessing.Dictionary;
import languageProcessing.LanguageProcessor;

/**
 * @author - Adam Trizna
 */

public class EntitySetEvaluator implements ISpecificEvaluator{

    LanguageProcessor lp = new Dictionary();

    double WEIGHT;

    /**
     * @param model1
     * @param model2
     * @param mapping
     * @return "EntitySet penalty part" = sum of mapped pairs names dissimilarites (according to LanguageProcessor's similarity calculation)
     */
    @Override
    public double evaluate(EntityRelationshipModel model1, EntityRelationshipModel model2, Mapping mapping) {

        double penalty = 0;
        for (EntitySet es : mapping.getDistinctAllEntitySets()) {
            penalty += 1 - (lp.getSimilarity(es.getName(),mapping.getImage(es).getName()));
        }
        return penalty;
    }

    @Override
    public double getWeight() {
        return WEIGHT;
    }

    public EntitySetEvaluator(double WEIGHT) {
        this.WEIGHT = WEIGHT;
    }
}
