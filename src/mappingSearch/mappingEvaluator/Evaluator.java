package mappingSearch.mappingEvaluator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import common.MappingUtils;
import common.PrintUtils;
import common.Utils;
import comparing.Mapping;
import entityRelationshipModel.EntitySet;
import transformations.Transformation;

/**
 * @author Adam Trizna
 */

/**
 *
 * {@inheritDoc}
 * 
 */
public class Evaluator implements IEvaluator {

	Map<EntitySet, EntitySet> bestMapping;
	double bestPenalty;
	TransformationEvaluator transformationEvaluator = new TransformationEvaluator();
	MappingEvaluator mappingEvaluator = new MappingEvaluator();

	@Override
	public void evaluate(Mapping mapping) {
		Utils.validateNotNull(mapping);

		finalizeMapping(mapping);
		evaluate(mapping, getTransformationsPenalty(mapping));
		unfinalizeMapping(mapping);
	}

	@Override
	public Map<EntitySet, EntitySet> getBestMapping() {
		return bestMapping;
	}

	private double getTransformationsPenalty(Mapping mapping) {
		List<Transformation> originalTransformations = new ArrayList<>(mapping.getTransformations());

		mappingEvaluator.expandTransformationList(mapping);
		double penalty = transformationEvaluator.evaluate(mapping);

		mapping.setTransformations(originalTransformations);
		return penalty;
	}

	private void evaluate(Mapping mapping, double actualPenalty) {
		if (bestMapping == null || actualPenalty < bestPenalty) {
			bestPenalty = actualPenalty;
			bestMapping = MappingUtils.createEntitySetMap(mapping);

			System.out.println("\nNew best mapping. penalty = " + bestPenalty);
			System.out.println(PrintUtils.print(bestMapping));
		}
	}

	/**
	 * Maps all not mapped entitySets to an EmptyEntitySet
	 * 
	 * @param mapping
	 */
	private void finalizeMapping(Mapping mapping) {
		assert mapping.getExemplarModel().getNotMappedEntitySets().isEmpty();
		for (EntitySet entitySet : mapping.getStudentModel().getEntitySets()) {
			if (entitySet.getMappedTo() == null) {
				entitySet.setMappedTo(MappingUtils.EMPTY_ENTITY_SET);
			}
		}
	}

	/**
	 * Unmaps all entitySets, which are mapped to an EmptyEntitySet
	 * 
	 * @param mapping
	 */
	private void unfinalizeMapping(Mapping mapping) {
		assert mapping.getExemplarModel().getNotMappedEntitySets().isEmpty();
		for (EntitySet entitySet : mapping.getStudentModel().getEntitySets()) {
			if (MappingUtils.EMPTY_ENTITY_SET.equals(entitySet.getMappedTo())) {
				entitySet.setMappedTo(null);
			}
		}
	}
}