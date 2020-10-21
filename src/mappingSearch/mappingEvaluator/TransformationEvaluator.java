package mappingSearch.mappingEvaluator;

import java.util.HashMap;
import java.util.Map;

import common.Utils;
import common.enums.EnumTransformation;
import comparing.Mapping;
import transformations.Transformation;

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

	private static final Map<EnumTransformation, Double> transformationPenalties = new HashMap<>();
	static {
		transformationPenalties.put(EnumTransformation.REBIND_MN_TO_1NN1, Double.valueOf(0));
		transformationPenalties.put(EnumTransformation.REBIND_1NN1_TO_MN, Double.valueOf(0));
		transformationPenalties.put(EnumTransformation.REBIND_1NN1_TO_MN, Double.valueOf(0));
		transformationPenalties.put(EnumTransformation.EXTRACT_ATTR_TO_OWN_ENTITY_SET, Double.valueOf(0));
		transformationPenalties.put(EnumTransformation.MOVE_ATTR_TO_INCIDENT_ENTITY_SET, Double.valueOf(0));
		transformationPenalties.put(EnumTransformation.MOVE_ATTR_TO_INCIDENT_ASSOCIATION, Double.valueOf(0));
		transformationPenalties.put(EnumTransformation.GENERALIZATION_TO_11_ASSOCIATION, Double.valueOf(0.5));
		transformationPenalties.put(EnumTransformation.CONTRACT_11_ASSOCIATION, Double.valueOf(0));
		transformationPenalties.put(EnumTransformation.REBIND_NARY_ASSOCIATION, Double.valueOf(0.5));

	}

	/**
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
	private double penalizeTransformation(Transformation transformation) {
		throw new UnsupportedOperationException();
	}
	
	/**
	 * Computes transformation penalty just by it's transformation code.
	 * @param code
	 * @return
	 */
	public double penalizeTransformation(EnumTransformation code) {
		if (transformationPenalties.containsKey(code)) {
			return transformationPenalties.get(code);
		}
		return 1;
	} 
}
