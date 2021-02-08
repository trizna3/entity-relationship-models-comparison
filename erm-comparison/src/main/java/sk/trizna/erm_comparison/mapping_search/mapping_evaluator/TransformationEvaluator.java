package sk.trizna.erm_comparison.mapping_search.mapping_evaluator;

import sk.trizna.erm_comparison.common.Utils;
import sk.trizna.erm_comparison.common.enums.EnumTransformation;
import sk.trizna.erm_comparison.common.enums.PenaltyConstants;
import sk.trizna.erm_comparison.comparing.mapping.Mapping;
import sk.trizna.erm_comparison.transformations.Transformation;

/**
 * @author - Adam Trizna
 * 
 */

/**
 * Component for evaluating transformations -- computing specific
 * transformations penalties.
 *
 */
public class TransformationEvaluator {

	/**
	 * 
	 * Compute penalty for given transformations made.
	 */
	public double evaluate(Mapping mapping) {
		Utils.validateNotNull(mapping);

		double penalty = 0;

		for (Transformation transformation : mapping.getTransformations()) {
			penalty += penalizeTransformation(transformation);
		}

		return penalty;
	}
	
	/**
	 * Computes transformation penalty by it's code and inputs. 
	 */
	public double penalizeTransformation(Transformation transformation) {
		return penalizeTransformation(transformation.getCode());
	}
	
	/**
	 * Computes transformation penalty just by it's transformation code.
	 * @param code
	 * @return
	 */
	public double penalizeTransformation(EnumTransformation code) {
		if (PenaltyConstants.FREE_TRANSFORMATIONS.contains(code)) {
			return PenaltyConstants.TRANSFORMATION_PENALTY_FREE;
		}
		if (PenaltyConstants.LIGHT_TRANSFORMATIONS.contains(code)) {
			return PenaltyConstants.TRANSFORMATION_PENALTY_LIGHT;
		}
		if (PenaltyConstants.MEDIUM_TRANSFORMATIONS.contains(code)) {
			return PenaltyConstants.TRANSFORMATION_PENALTY_MEDIUM;
		}
		if (PenaltyConstants.HEAVY_TRANSFORMATIONS.contains(code)) {
			return PenaltyConstants.TRANSFORMATION_PENALTY_HEAVY;
		}
		throw new IllegalArgumentException("Unknown Transformation code - cannot compute penalty!");
	} 
}
