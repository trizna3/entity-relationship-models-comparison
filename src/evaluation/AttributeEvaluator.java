package evaluation;

import comparing.Mapping;
import entityRelationshipModel.EntityRelationshipModel;
import entityRelationshipModel.EntitySet;
import languageProcessing.Dictionary;
import languageProcessing.LanguageProcessor;

/**
 * @author - Adam Trizna
 */

public class AttributeEvaluator implements ISpecificEvaluator{

    LanguageProcessor lp = new Dictionary();

    double WEIGHT;
    double SIMILARITY_TRESHOLD = 1;


    /**
     * @param model1
     * @param model2
     * @param mapping
     * @return "Attribute penalty part" = number of attributes, which do not have a similar(beyond some treshold similarity) attribute among the image's attributes - summed for all mapped pairs.
     */
    @Override
    public double evaluate(EntityRelationshipModel model1, EntityRelationshipModel model2, Mapping mapping) {

        double penalty = 0;
        for (EntitySet es : mapping.getDistinctAllEntitySets()) {
            penalty += getEntitySetPairPenalty(es,mapping.getImage(es));
        }
        return penalty;
    }

    private double getEntitySetPairPenalty(EntitySet entitySet1, EntitySet entitySet2) {
        return getAttributesPenalty(entitySet1,entitySet2) + getAttributesPenalty(entitySet2,entitySet1);
    }

    private double getAttributesPenalty(EntitySet entitySet1, EntitySet entitySet2) {

        double penalty = 0;
        attr1 : for (String attribute1 : entitySet1.getAttributes()) {
            attr2 : for (String attribute2 : entitySet2.getAttributes()) {
                if ((lp.getSimilarity(attribute1,attribute2)) >= SIMILARITY_TRESHOLD) {
                    continue attr1;
                }
            }
            penalty ++;
        }
        return penalty;
    }

    @Override
    public double getWeight() {
        return WEIGHT;
    }

    public AttributeEvaluator(double WEIGHT) {
        this.WEIGHT = WEIGHT;
    }
}
