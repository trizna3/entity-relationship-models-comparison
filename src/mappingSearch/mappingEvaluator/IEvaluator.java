package mappingSearch.mappingEvaluator;

import comparing.Mapping;

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
	 * Evaluates given mapping.
	 * 
	 * @param exemplarModel
	 * @param studentModel
	 * @param mapping
	 */
	void evaluate(Mapping mapping);

	/**
	 * @return Mapping with minimal penalty evaluated so far.
	 */
	Mapping getBestMapping();
}
