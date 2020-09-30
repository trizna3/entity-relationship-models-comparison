package mappingSearch.mappingEvaluator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import common.MappingUtils;
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

	private Map<EntitySet, EntitySet> bestMapping;
	private List<Transformation> bestMappingTransformations;
	private double bestPenalty;
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
		if (getBestMapping() == null || actualPenalty < getBestPenalty()) {
			setBestPenalty(actualPenalty);
			setBestMapping(MappingUtils.createEntitySetMap(mapping));
			setBestMappingTransformations(new ArrayList<>(mapping.getTransformations()));
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
	 * Unmaps all entitySets, which are mapped to an EmptyEntitySet. Resets all
	 * transformables transformationRole, which were overwritten.
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

		mapping.getExemplarModel().resetTransformableRoles();
		mapping.getStudentModel().resetTransformableRoles();
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