package mappingSearch.mappingEvaluator;

import java.util.Map;

import comparing.Mapping;
import entityRelationshipModel.EntitySet;

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
	Map<EntitySet, EntitySet> getBestMapping();

	/**
	 * @return Penalty of best mapping found so far.
	 */
	double getBestPenalty();
}
