package evaluation;

import comparing.Mapping;
import entityRelationshipModel.EntityRelationshipModel;

/**
 * @author - Adam Trizna
 */

/**
 * Interface for entity relationship models mapping evaluator.
 */
public interface IEvaluator {

    /**
     * @param model1
     * @param model2
     * @param mapping
     * @return penalty value of given mapping of given entity relationship models
     */
    double evaluate(EntityRelationshipModel model1, EntityRelationshipModel model2, Mapping mapping);
}
