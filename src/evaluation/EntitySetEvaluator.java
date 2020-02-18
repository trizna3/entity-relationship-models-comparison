package evaluation;

import comparing.Mapping;
import entityRelationshipModel.EntityRelationshipModel;
import entityRelationshipModel.EntitySet;
import languageProcessing.Dictionary;
import languageProcessing.LanguageProcessor;

import java.util.HashSet;
import java.util.Set;

public class EntitySetEvaluator implements ISpecificEvaluator{

    LanguageProcessor lp = new Dictionary();

    double WEIGHT;

    @Override
    public double evaluate(EntityRelationshipModel model1, EntityRelationshipModel model2, Mapping mapping) {

        double penalty = 0;
        for (EntitySet es : mapping.getAllEntitySets()) {
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
