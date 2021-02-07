package sk.trizna.erm_comparison.comparing;

import java.util.List;
import java.util.Map;

import sk.trizna.erm_comparison.entity_relationship_model.EntitySet;
import sk.trizna.erm_comparison.mappingSearch.mapping_finder.MappingFinder;
import sk.trizna.erm_comparison.transformations.Transformation;

/**
 * Mapping, returned from MappingEvaluator.
 * Has form of Map<EntitySet,EntitySet>, because working Mapping will be subject to additional MappingSearch - it's ERModels will be modified. Using Map<ES,ES> is better than cloning whole ERModel double.
 * 
 * MappingEvaluation class is only a format of response from {@link Evaluator} to {@link MappingFinder}, it may not have all it's fields filled! 
 * 
 * @author Adam Trizna
 *
 */
public class MappingEvaluation {

	private Map<EntitySet,EntitySet> entitySetMap;
	private List<Transformation> transformations;
	private Double penalty;
	
	public MappingEvaluation(Map<EntitySet, EntitySet> entitySetMap, List<Transformation> transformations, Double penalty) {
		this.entitySetMap = entitySetMap;
		this.transformations = transformations;
		this.penalty = penalty;
	}

	public Map<EntitySet,EntitySet> getEntitySetMap() {
		return entitySetMap;
	}

	public List<Transformation> getTransformations() {
		return transformations;
	}

	public Double getPenalty() {
		return penalty;
	}
}
