package sk.trizna.erm_comparison.mapping_search.mapping_evaluator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sk.trizna.erm_comparison.common.ERModelUtils;
import sk.trizna.erm_comparison.common.MappingUtils;
import sk.trizna.erm_comparison.common.TransformationUtils;
import sk.trizna.erm_comparison.common.Utils;
import sk.trizna.erm_comparison.comparing.mapping.Mapping;
import sk.trizna.erm_comparison.comparing.mapping.MappingEvaluation;
import sk.trizna.erm_comparison.entity_relationship_model.EntitySet;
import sk.trizna.erm_comparison.transformations.Transformation;

/**
 * @author Adam Trizna
 */

/**
 *
 * {@inheritDoc}
 * 
 */
public class Evaluator implements IEvaluator {

	private Map<EntitySet, EntitySet> bestEntitySetMap;
	private List<Transformation> bestMappingTransformations;
	private Double bestMappingPenalty;
	MappingEvaluator mappingEvaluator = new MappingEvaluator();

	@Override
	public MappingEvaluation evaluateMapping(Mapping mapping, boolean finalize) {
		Utils.validateNotNull(mapping);
		
		List<EntitySet> finalizedEntitySets = finalizeMapping(mapping);
		double penalty = computePenalty(mapping);
		unfinalizeMapping(mapping, finalizedEntitySets);
		
		if (finalize) {
			evaluate(mapping, penalty);
		}
		
		return new MappingEvaluation(null, null, penalty);
	}

	@Override
	public MappingEvaluation getBestMapping() {
		return new MappingEvaluation(getBestEntitySetMap(),
				getBestMappingTransformations(),
				getBestMappingPenalty());
	}
	
	private double computePenalty(Mapping mapping) {
		return mappingEvaluator.computeMappingPenalty(mapping);
	}

	private void evaluate(Mapping mapping, double actualPenalty) {
		if (getBestMappingPenalty() == null || actualPenalty < getBestMappingPenalty()) {
			setBestMappingPenalty(actualPenalty);
			setBestEntitySetMap(MappingUtils.createEntitySetMap(mapping));
			setBestMappingTransformations(TransformationUtils.copyTransformationList(mapping.getTransformations()));
		}
	}

	/**
	 * Maps all not mapped entitySets to an EmptyEntitySet
	 * 
	 * @param mapping
	 */
	private List<EntitySet> finalizeMapping(Mapping mapping) {
		List<EntitySet> result = new ArrayList<>();

		ERModelUtils.finalizeModel(mapping.getStudentModel(), result);
		ERModelUtils.finalizeModel(mapping.getExemplarModel(), result);
		return result;
	}

	/**
	 * Unmaps all entitySets, which are mapped to an EmptyEntitySet.
	 * 
	 * @param mapping
	 */
	private void unfinalizeMapping(Mapping mapping, List<EntitySet> targetEntitySets) {
		ERModelUtils.unfinalizeModel(mapping.getStudentModel(),targetEntitySets);
		ERModelUtils.unfinalizeModel(mapping.getExemplarModel(),targetEntitySets);
	}

	private Map<EntitySet, EntitySet> getBestEntitySetMap() {
		if (bestEntitySetMap == null) {
			bestEntitySetMap = new HashMap<EntitySet, EntitySet>();
		}
		return bestEntitySetMap;
	}
	
	private void setBestEntitySetMap(Map<EntitySet, EntitySet> bestMapping) {
		this.bestEntitySetMap = bestMapping;
	}

	private List<Transformation> getBestMappingTransformations() {
		if (bestMappingTransformations == null) {
			bestMappingTransformations = new ArrayList<Transformation>();
		}
		return bestMappingTransformations;
	}

	private void setBestMappingTransformations(List<Transformation> bestMappingTransformations) {
		this.bestMappingTransformations = bestMappingTransformations;
	}

	private Double getBestMappingPenalty() {
		return bestMappingPenalty;
	}

	private void setBestMappingPenalty(Double bestPenalty) {
		this.bestMappingPenalty = bestPenalty;
	}
}