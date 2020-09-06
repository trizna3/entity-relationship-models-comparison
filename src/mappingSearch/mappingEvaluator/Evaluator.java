package mappingSearch.mappingEvaluator;

import common.ERModelUtils;
import common.Utils;
import comparing.Mapping;

/**
 * 
 * @author Adam Trizna
 * 
 *         {@inheritDoc}
 *
 */
public class Evaluator implements IEvaluator {

	Mapping bestMapping;
	double bestPenalty;
	TransformationEvaluator transformationEvaluator = new TransformationEvaluator();

	@Override
	public void evaluate(Mapping mapping) {

		Utils.validateNotNull(mapping);

		if (!ERModelUtils.modelsAreEqual(mapping)) {
			return;
		}
		evaluate(mapping, getTransformationsPenalty(mapping));
	}

	@Override
	public Mapping getBestMapping() {
		return bestMapping;
	}

	private double getTransformationsPenalty(Mapping mapping) {
		return transformationEvaluator.evaluate(mapping);
	}

	private void evaluate(Mapping mapping, double actualPenalty) {
		if (bestMapping == null || actualPenalty < bestPenalty) {
			bestPenalty = actualPenalty;
			bestMapping = mapping;
		}
	}
}