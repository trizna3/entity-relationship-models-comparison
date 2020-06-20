package evaluation;

import comparing.Mapping;
import entityRelationshipModel.ERModel;

/**
 * @author - Adam Trizna
 */

/**
 * Interface for entity relationship models mapping evaluator.
 */
public interface IModelEvaluator {

    /**
     * @param model1
     * @param model2
     * @param mapping
     * @return penalty value of given mapping of given entity relationship models
     */
    double evaluate(ERModel model1, ERModel model2, Mapping mapping);
}
