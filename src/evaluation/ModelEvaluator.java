package evaluation;

import comparing.Mapping;
import entityRelationshipModel.ERModel;

/**
 * @author - Adam Trizna
 */

public class ModelEvaluator implements IModelEvaluator {

	double ES_WEIGHT = 0;
	double ATTR_WEIGHT = 0;
	double R_WEIGHT = 0;

	double WEIGHT = 2;

	/**
	 * Computes weighted sum of each evaluation part (done by specific evaluators).
	 * 
	 * @param model1
	 * @param model2
	 * @param mapping
	 * @return
	 */
	@Override
	public double evaluate(ERModel model1, ERModel model2, Mapping mapping) {
		throw new UnsupportedOperationException();
	}
}
