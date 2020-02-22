package evaluation;

import common.ModelUtils;
import comparing.Mapping;
import entityRelationshipModel.EntityRelationshipModel;
import entityRelationshipModel.Relationship;

import java.util.HashSet;
import java.util.Set;

public class RelationshipEvaluator implements ISpecificEvaluator {

    double WEIGHT;

    double R_WEIGHT = 1;
    double C_WEIGHT = 0.5;

    double r = 0;
    double c = 0;

    /**
     * @param model1
     * @param model2
     * @param mapping
     * @return "Relationship and Cardinality penalty parts"
     */
    @Override
    public double evaluate(EntityRelationshipModel model1, EntityRelationshipModel model2, Mapping mapping) {

        this.r = 0;
        this.c = 0;

        evaluateRelationshipsAssymetric(model1,model2,mapping);
        evaluateRelationshipsAssymetric(model2,model1,mapping);

        System.out.println("relationship r evaluation = " + r);
        System.out.println("relationship c evaluation = " + c);
        return (r*R_WEIGHT) + (c*C_WEIGHT);
    }

    private void evaluateRelationshipsAssymetric(EntityRelationshipModel model1, EntityRelationshipModel model2, Mapping mapping) {
        Set<Relationship> usedRelationships = new HashSet<>();
        rel1: for (Relationship relationship1 : model1.getRelationships()) {
            rel2: for (Relationship relationship2 : model2.getRelationships()) {
                if (!usedRelationships.contains(relationship2) && ModelUtils.relationshipsAreEqual(relationship1,relationship2,mapping,false)) {
                    usedRelationships.add(relationship2);
                    continue rel1;
                }
            }
            this.c ++;
            rel2: for (Relationship relationship2 : model2.getRelationships()) {
                if (!usedRelationships.contains(relationship2) && ModelUtils.relationshipsAreEqual(relationship1,relationship2,mapping,true)) {
                    usedRelationships.add(relationship2);
                    continue rel1;
                }
            }
            this.r ++;
        }
    }

    @Override
    public double getWeight() {
        return WEIGHT;
    }

    public RelationshipEvaluator(double WEIGHT) {
        this.WEIGHT = WEIGHT;
    }
}
