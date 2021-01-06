package mappingSearch.mappingEvaluator;

import java.util.List;
import java.util.Map;

import comparing.Mapping;
import entityRelationshipModel.EntitySet;
import transformations.Transformation;

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
	 * Evaluates current (not finished) branch, determines if it can be pruned (eg.
	 * the optimal solution is not located in the subtree, rooted in the current
	 * node)
	 * 
	 * @param mapping
	 * @return
	 */
	boolean shallPruneBranch(Mapping mapping);
	
	/**
	 * Computes mapping's current penalty.
	 * @param mapping
	 * @return
	 */
	double getMappingPenalty(Mapping mapping);
	
	/**
	 * @return Mapping with minimal penalty evaluated so far.
	 */
	Map<EntitySet, EntitySet> getBestMapping();

	/**
	 * @return transformations done in bestMapping
	 */
	List<Transformation> getBestMappingTransformations();

	/**
	 * @return Penalty of best mapping found so far.
	 */
	double getBestPenalty();
	
	public double getTransformationsPenaltyDirect(Mapping mapping);
}
