package mappingSearch.mappingEvaluator;

import java.util.HashMap;
import java.util.Map;

import common.Utils;
import comparing.Mapping;

/**
 * @author - Adam Trizna
 * 
 *         Component for evaluating transformations -- computing specific
 *         transformations penalties.
 */
public class TransformationEvaluator {

	private static final Map<String, Double> transformationPenalties = new HashMap<>();
	static {
	}

	/**
	 * Compute penalty for given transformations made.
	 */
	public double evaluate(Mapping mapping) {
		Utils.validateNotNull(mapping);

		double penalty = 0;

		// TODO: toto nemoze byt len na zaklade kodov. Potrebne zapracovat logiku pre
		// vyhodnocovanie ekvivalencie transformacii, minimalne pre vybrane
		// transformacie.

// for (String code : mapping.getTransformationCodes()) {
//			penalty += penalizeTransformation(code);
//		}

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
