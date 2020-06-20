package mappingSearch.mappingFinder;

import java.util.List;
import java.util.Map;

import comparing.Mapping;
import entityRelationshipModel.ERModel;
import entityRelationshipModel.EntitySet;
import mappingSearch.mappingEvaluator.IMappingEvaluator;
import mappingSearch.mappingEvaluator.MappingEvaluator;
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

	IMappingEvaluator mappingEvaluator;

	/**
	 * Uses recursive backtrack algorithm to iterate over all possible mappings,
	 * compute their penalties, get the one with the lowest penalty.
	 * 
	 * @param exemplarModel
	 * @param studentModel
	 * @return Mapping of minimal penalty.
	 */
	public Mapping getBestMapping(ERModel exemplarModel, ERModel studentModel) {
		search(new Mapping(exemplarModel, studentModel));
		return getMappingEvaluator().getBestMapping();
	}

	/**
	 * 
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

		for (EntitySet studentEntitySet : studentModel.getNotMappedEntitySets()) {
			map(mapping, exemplarEntitySet, studentEntitySet);
			search(mapping);
			unmap(mapping, exemplarEntitySet, studentEntitySet);
		}
	}

	private void searchThroughTransformation(Mapping mapping) {
		Map<String, List<Object>> transformations = TransformationAnalyst.getPossibleTransformations(mapping);

		for (String code : transformations.keySet()) {
			List<Object> transformed = Transformator.execute(mapping, code, transformations.get(code));
			search(mapping);
			Transformator.revert(mapping, code, transformed);
		}
	}

	private void map(Mapping mapping, EntitySet exemplarEntitySet, EntitySet studentEntitySet) {
		mapping.map(exemplarEntitySet, studentEntitySet);
		mapping.getExemplarModel().getNotMappedEntitySets().remove(exemplarEntitySet);
		mapping.getStudentModel().getNotMappedEntitySets().remove(studentEntitySet);
	}

	private void unmap(Mapping mapping, EntitySet exemplarEntitySet, EntitySet studentEntitySet) {
		mapping.unmap(exemplarEntitySet, studentEntitySet);
		mapping.getExemplarModel().getNotMappedEntitySets().add(exemplarEntitySet);
		mapping.getStudentModel().getNotMappedEntitySets().add(studentEntitySet);
	}

	private boolean stoppingCriterion(Mapping mapping) {
		return mapping.getExemplarModel().getNotMappedEntitySets().isEmpty();
	}

	private void evaluate(Mapping mapping) {
		getMappingEvaluator().evaluate(mapping);
	}

	private IMappingEvaluator getMappingEvaluator() {
		if (mappingEvaluator == null) {
			mappingEvaluator = new MappingEvaluator();
		}
		return mappingEvaluator;
	}
}
