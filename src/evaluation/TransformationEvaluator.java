package evaluation;

import java.util.HashMap;
import java.util.Map;

import common.Utils;
import comparing.Mapping;
import transformations.types.Transformation;

/**
 * @author - Adam Trizna
 */
public class TransformationEvaluator {

	private static final Map<String, Double> transformationPenalties = new HashMap<>();
	static {
		transformationPenalties.put(Transformation.CODE_ADD_ASSOCIATION, 1d);
		transformationPenalties.put(Transformation.CODE_ADD_GENERALIZATION, 1d);
		transformationPenalties.put(Transformation.CODE_ADD_ENTITY_SET, 1d);
		transformationPenalties.put(Transformation.CODE_ADD_ATTRIBUTE, 1d);
		transformationPenalties.put(Transformation.CODE_REMOVE_ASSOCIATION, 1d);
		transformationPenalties.put(Transformation.CODE_REMOVE_GENERALIZATION, 1d);
		transformationPenalties.put(Transformation.CODE_REMOVE_ENTITY_SET, 1d);
		transformationPenalties.put(Transformation.CODE_REMOVE_ATTRIBUTE, 1d);
	}

	/**
	 * Compute penalty for given transformations made.
	 */
	public double evaluate(Mapping mapping) {
		Utils.validateNotNull(mapping);

		double penalty = 0;

		for (String code : mapping.getTransformationCodes()) {
			penalty += penalizeTransformation(code);
		}
		return penalty;
	}

	/**
	 * Computes penalty for used transformation, based on transformation type.
	 */
	private double penalizeTransformation(String transformationCode) {
		Double penalty = transformationPenalties.get(transformationCode);
		if (penalty == null) {
			throw new IllegalArgumentException("Invalid transformation code!");
		}
		return penalty;
	}
}
