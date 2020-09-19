package mappingSearch.mappingFinder;

import java.util.List;
import java.util.Map;

import common.MappingUtils;
import common.PrintUtils;
import common.Utils;
import comparing.Mapping;
import entityRelationshipModel.ERModel;
import entityRelationshipModel.EntitySet;
import mappingSearch.mappingEvaluator.Evaluator;
import mappingSearch.mappingEvaluator.IEvaluator;
import transformations.Transformation;
import transformations.TransformationAnalyst;
import transformations.Transformator;

/**
 * @author - Adam Trizna
 */

/**
 * Object for finding best mapping of given double of entity relationship
 * models.
 */
public class MappingFinder {

	IEvaluator mappingEvaluator;

	/**
	 * Uses recursive backtrack algorithm to iterate over all possible mappings,
	 * compute their penalties, get the one with the lowest penalty.
	 * 
	 * @param exemplarModel
	 * @param studentModel
	 * @return Mapping of minimal penalty.
	 */
	public Map<EntitySet, EntitySet> getBestMapping(ERModel exemplarModel, ERModel studentModel) {
		exemplarModel.setExemplar(true);
		studentModel.setExemplar(false);

		search(new Mapping(exemplarModel, studentModel));
		if (Utils.PRINT_MODE) {
			System.out.println("Best mapping penalty = " + getMappingEvaluator().getBestPenalty());
			System.out.println(PrintUtils.print(getMappingEvaluator().getBestMapping()));
		}
		return getMappingEvaluator().getBestMapping();
	}

	/**
	 * Performs backtracking algorithm to find optimal entity sets mapping.
	 */
	private void search(Mapping mapping) {
		if (stoppingCriterion(mapping)) {
			evaluate(mapping);
			return;
		}
		searchThroughMapping(mapping);
		searchThroughTransformation(mapping);
	}

	private void searchThroughMapping(Mapping mapping) {
		ERModel exemplarModel = mapping.getExemplarModel();
		ERModel studentModel = mapping.getStudentModel();

		EntitySet exemplarEntitySet = exemplarModel.getNotMappedEntitySets().get(0);

		for (int i = 0; i < studentModel.getEntitySets().size() + 1; i++) {
			EntitySet studentEntitySet;
			if (i == studentModel.getEntitySets().size()) {
				studentEntitySet = MappingUtils.EMPTY_ENTITY_SET;
			} else {
				studentEntitySet = studentModel.getEntitySets().get(i);
			}
			if (studentEntitySet.getMappedTo() != null) {
				continue;
			}
			map(mapping, exemplarEntitySet, studentEntitySet);
			search(mapping);
			unmap(mapping, exemplarEntitySet, studentEntitySet);
		}
	}

	private void searchThroughTransformation(Mapping mapping) {
		List<Transformation> transformations = TransformationAnalyst.getPossibleTransformations(mapping);

		for (Transformation transformation : transformations) {
			Transformator.execute(mapping, transformation);
			search(mapping);
			Transformator.revert(mapping, transformation);
		}
	}

	private void map(Mapping mapping, EntitySet exemplarEntitySet, EntitySet studentEntitySet) {
		mapping.map(exemplarEntitySet, studentEntitySet);
		mapping.getExemplarModel().getNotMappedEntitySets().remove(exemplarEntitySet);
		if (!studentEntitySet.isEmpty()) {
			mapping.getStudentModel().getNotMappedEntitySets().remove(studentEntitySet);
		}
	}

	private void unmap(Mapping mapping, EntitySet exemplarEntitySet, EntitySet studentEntitySet) {
		mapping.unmap(exemplarEntitySet, studentEntitySet);
		mapping.getExemplarModel().getNotMappedEntitySets().add(0, exemplarEntitySet);
		if (!studentEntitySet.isEmpty()) {
			mapping.getStudentModel().getNotMappedEntitySets().add(0, studentEntitySet);
		}
	}

	private boolean stoppingCriterion(Mapping mapping) {
		return mapping.getExemplarModel().getNotMappedEntitySets().isEmpty();
	}

	private void evaluate(Mapping mapping) {
		getMappingEvaluator().evaluate(mapping);
	}

	private IEvaluator getMappingEvaluator() {
		if (mappingEvaluator == null) {
			mappingEvaluator = new Evaluator();
		}
		return mappingEvaluator;
	}
}
