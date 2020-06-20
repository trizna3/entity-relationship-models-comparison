package mappingSearch.mappingEvaluator;

import comparing.Mapping;
import evaluation.IModelEvaluator;
import evaluation.ModelEvaluator;

/**
 * @author - Adam Trizna
 */
public class MappingEvaluator implements IMappingEvaluator {

	Mapping bestMapping;
	double bestPenalty;
	IModelEvaluator modelEvaluator;

	@Override
	public void evaluate(Mapping mapping) {

		// if models are not equal in mapping -> refuse mapping

		// compute penalties for transformations
		double penalty = 0;

		if (bestMapping == null || penalty < bestPenalty) {
			bestPenalty = penalty;
		}
	}

	@Override
	public Mapping getBestMapping() {
		return bestMapping;
	}

	private IModelEvaluator getModelEvaluator() {
		if (modelEvaluator == null) {
			modelEvaluator = new ModelEvaluator();
		}
		return modelEvaluator;
	}
}