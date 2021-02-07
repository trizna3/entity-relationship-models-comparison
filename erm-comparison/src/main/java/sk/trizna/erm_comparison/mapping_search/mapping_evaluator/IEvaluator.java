package sk.trizna.erm_comparison.mapping_search.mapping_evaluator;

import sk.trizna.erm_comparison.comparing.Mapping;
import sk.trizna.erm_comparison.comparing.MappingEvaluation;

/**
 * @author - Adam Trizna
 */

/**
 * Object for mapping evaluation, used to handle a single branch of the
 * backtrack-search tree. This kind of evaluator computes mapping penalty and
 * keeps record of mapping with minimal penalty.
 */
public interface IEvaluator {

	/**
	 * Evaluates given mapping, returns mapping penalty.
	 * 
	 * 
	 * @param mapping = mapping to evaluate
	 * @param finalize = true means EntitySet map and Transformation List will be saved. False means only penalty will be computed.
	 * @return
	 */
	public MappingEvaluation evaluateMapping(Mapping mapping, boolean finalize);

	/**
	 * Returns best mapping evaluation ({@link MappingEvaluation}).
	 * 
	 * @return
	 */
	public MappingEvaluation getBestMapping();
}
