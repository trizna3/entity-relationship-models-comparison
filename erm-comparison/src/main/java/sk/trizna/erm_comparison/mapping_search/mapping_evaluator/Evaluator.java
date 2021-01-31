package sk.trizna.erm_comparison.mapping_search.mapping_evaluator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import sk.trizna.erm_comparison.common.ERModelUtils;
import sk.trizna.erm_comparison.common.MappingUtils;
import sk.trizna.erm_comparison.common.TransformationUtils;
import sk.trizna.erm_comparison.common.Utils;
import sk.trizna.erm_comparison.comparing.Mapping;
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

	private Map<EntitySet, EntitySet> bestMapping;
	private List<Transformation> bestMappingTransformations;
	private double bestPenalty;
	MappingEvaluator mappingEvaluator = new MappingEvaluator();

	@Override
	public void evaluate(Mapping mapping) {
		Utils.validateNotNull(mapping);

		List<EntitySet> finalizedEntitySets = finalizeMapping(mapping);
		evaluate(mapping, getTransformationsPenaltyDirect(mapping));
		unfinalizeMapping(mapping,finalizedEntitySets);
	}

	@Override
	public boolean shallPruneBranch(Mapping mapping) {
		Utils.validateNotNull(mapping);

		if (getBestMapping() == null) {
			return false;
		}

		return getTransformationsPenaltyDirect(mapping) > getBestPenalty();
	}

	@Override
	public Map<EntitySet, EntitySet> getBestMapping() {
		return bestMapping;
	}
	

	public double getTransformationsPenaltyDirect(Mapping mapping) {
		return mappingEvaluator.computeMappingPenalty(mapping);
	}
	
	@Override
	public double getMappingPenalty(Mapping mapping) {
		return getTransformationsPenaltyDirect(mapping);
	}

	private void evaluate(Mapping mapping, double actualPenalty) {
		if (getBestMapping() == null || actualPenalty < getBestPenalty()) {
			setBestPenalty(actualPenalty);
			setBestMapping(MappingUtils.createEntitySetMap(mapping));
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

	public double getBestPenalty() {
		return bestPenalty;
	}

	public void setBestPenalty(double bestPenalty) {
		this.bestPenalty = bestPenalty;
	}

	public void setBestMapping(Map<EntitySet, EntitySet> bestMapping) {
		this.bestMapping = bestMapping;
	}

	@Override
	public List<Transformation> getBestMappingTransformations() {
		return bestMappingTransformations;
	}

	public void setBestMappingTransformations(List<Transformation> bestMappingTransformations) {
		this.bestMappingTransformations = bestMappingTransformations;
	}
}