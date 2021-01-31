package sk.trizna.erm_comparison.mapping_search.mapping_evaluator;

import java.util.HashSet;
import java.util.Set;

import sk.trizna.erm_comparison.common.PenaltyConstants;
import sk.trizna.erm_comparison.common.Utils;
import sk.trizna.erm_comparison.common.enums.EnumTransformation;
import sk.trizna.erm_comparison.comparing.Mapping;
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

	
	private static final Set<EnumTransformation> freeTransformations = new HashSet<>();
	private static final Set<EnumTransformation> lightTransformations = new HashSet<>();
	private static final Set<EnumTransformation> mediumTransformations = new HashSet<>();
	private static final Set<EnumTransformation> heavyTransformations = new HashSet<>();
	static {
		freeTransformations.add(EnumTransformation.REBIND_MN_TO_1NN1);
		freeTransformations.add(EnumTransformation.REBIND_1NN1_TO_MN);
		freeTransformations.add(EnumTransformation.EXTRACT_ATTR_TO_OWN_ENTITY_SET);
		freeTransformations.add(EnumTransformation.CONTRACT_11_ASSOCIATION);
		
		lightTransformations.add(EnumTransformation.MOVE_ATTR_TO_INCIDENT_ENTITY_SET);
		lightTransformations.add(EnumTransformation.MOVE_ATTR_TO_INCIDENT_ASSOCIATION);
		lightTransformations.add(EnumTransformation.GENERALIZATION_TO_11_ASSOCIATION);
		lightTransformations.add(EnumTransformation._11_ASSOCIATION_TO_GENERALIZATION);
		lightTransformations.add(EnumTransformation.REBIND_NARY_ASSOCIATION);
		lightTransformations.add(EnumTransformation.CREATE_ATTRIBUTE);
		lightTransformations.add(EnumTransformation.REMOVE_ATTRIBUTE);
		lightTransformations.add(EnumTransformation.CHANGE_CARDINALITY);
		lightTransformations.add(EnumTransformation.RENAME_ATTRIBUTE);
		
		mediumTransformations.add(EnumTransformation.CREATE_ASSOCIATION);
		mediumTransformations.add(EnumTransformation.CREATE_GENERALIZATION);
		mediumTransformations.add(EnumTransformation.REMOVE_ASSOCIATION);
		mediumTransformations.add(EnumTransformation.REMOVE_GENERALIZATION);
		
		heavyTransformations.add(EnumTransformation.CREATE_ENTITY_SET);
		heavyTransformations.add(EnumTransformation.REMOVE_ENTITY_SET);
		heavyTransformations.add(EnumTransformation.RENAME_ENTITY_SET);		
	}

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
		if (freeTransformations.contains(code)) {
			return PenaltyConstants.TRANSFORMATION_PENALTY_FREE;
		}
		if (lightTransformations.contains(code)) {
			return PenaltyConstants.TRANSFORMATION_PENALTY_LIGHT;
		}
		if (mediumTransformations.contains(code)) {
			return PenaltyConstants.TRANSFORMATION_PENALTY_MEDIUM;
		}
		if (heavyTransformations.contains(code)) {
			return PenaltyConstants.TRANSFORMATION_PENALTY_HEAVY;
		}
		throw new IllegalArgumentException("Unknown Transformation code - cannot compute penalty!");
	} 
}
