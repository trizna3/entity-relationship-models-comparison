package mappingSearch.mappingFinder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import common.Clock;
import common.ConfigManager;
import common.LoggerUtils;
import common.MappingUtils;
import common.PrintUtils;
import common.Utils;
import common.enums.EnumConstants;
import comparing.Mapping;
import entityRelationshipModel.ERModel;
import entityRelationshipModel.EntitySet;
import languageProcessing.Dictionary;
import languageProcessing.LanguageProcessor;
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

	private IEvaluator mappingEvaluator;
	private LanguageProcessor dictionary;
	
	private boolean printResult = Boolean.valueOf(ConfigManager.getResource(EnumConstants.CONFIG_PRINT_RESULT).toString());
	private boolean printTransformationProgress = Boolean.valueOf(ConfigManager.getResource(EnumConstants.CONFIG_PRINT_TRANSFORMATION_PROGRESS).toString());
	private boolean trackProgress = Boolean.valueOf(ConfigManager.getResource(EnumConstants.CONFIG_TRACK_PROGRESS).toString());
	private boolean earlyStop = Boolean.valueOf(ConfigManager.getResource(EnumConstants.CONFIG_EARLY_STOP).toString());
	private int earlyStopBound = Integer.valueOf(ConfigManager.getResource(EnumConstants.CONFIG_EARLY_STOP_BOUND).toString());

	/**
	 * Stack level counter
	 */
	private Integer counter = new Integer(0);

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

		if (earlyStop) {
			Clock.getInstance().start(earlyStopBound);
		}
		
		search(new Mapping(exemplarModel, studentModel));
		if (printResult) {
			System.out.println("Best mapping penalty = " + getMappingEvaluator().getBestPenalty());
			System.out.println(PrintUtils.print(getMappingEvaluator().getBestMapping()));
			System.out.println(PrintUtils.print(getMappingEvaluator().getBestMappingTransformations()));
		}
		return getMappingEvaluator().getBestMapping();
	}

	/**
	 * Performs backtracking algorithm to find optimal entity sets mapping.
	 */
	private void search(Mapping mapping) {
		if (shallPrune(mapping) || (earlyStop && Clock.getInstance().boundReached())) {
			return;
		}
		if (branchComplete(mapping)) {
			evaluate(mapping);
			return;
		}
		searchThroughTransformation(mapping);
		searchThroughMapping(mapping);
	}

	private void searchThroughMapping(Mapping mapping) {
		ERModel exemplarModel = mapping.getExemplarModel();
		ERModel studentModel = mapping.getStudentModel();
		
		EntitySet exemplarEntitySet = exemplarModel.getNotMappedEntitySets().get(0);
		
		List<EntitySet> studentEntitySets = new ArrayList<>(studentModel.getEntitySets());
		studentEntitySets.add(MappingUtils.EMPTY_ENTITY_SET);
		
		computeEntitySetsPriority(studentEntitySets,exemplarEntitySet);
		studentModel.getEntitySets().sort(EntitySetComparator.getInstance());
		
		for (EntitySet studentEntitySet : studentEntitySets) {
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
			executeTransformation(mapping, transformation);
			search(mapping);
			revertTransformation(mapping, transformation);
		}
		
		TransformationAnalyst.freeTransformations(transformations);
	}

	private void map(Mapping mapping, EntitySet exemplarEntitySet, EntitySet studentEntitySet) {
		mapping.map(exemplarEntitySet, studentEntitySet);
		mapping.getExemplarModel().getNotMappedEntitySets().remove(exemplarEntitySet);
		if (!studentEntitySet.isEmpty()) {
			mapping.getStudentModel().getNotMappedEntitySets().remove(studentEntitySet);
		}
		incrementCounter();
	}

	private void unmap(Mapping mapping, EntitySet exemplarEntitySet, EntitySet studentEntitySet) {
		mapping.unmap(exemplarEntitySet, studentEntitySet);
		mapping.getExemplarModel().getNotMappedEntitySets().add(0, exemplarEntitySet);
		if (!studentEntitySet.isEmpty()) {
			mapping.getStudentModel().getNotMappedEntitySets().add(0, studentEntitySet);
		}
		decrementCounter();
	}

	private boolean branchComplete(Mapping mapping) {
		return mapping.getExemplarModel().getNotMappedEntitySets().isEmpty();
	}

	private boolean shallPrune(Mapping mapping) {
		return getMappingEvaluator().shallPruneBranch(mapping);
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

	private void executeTransformation(Mapping mapping, Transformation transformation) {
		if (printTransformationProgress) {
			LoggerUtils.logTransformation(transformation, LoggerUtils.DIRECTION_DOWN);
		}
		Transformator.execute(mapping, transformation);
		mapping.addTransformation(transformation);
		incrementCounter();
	}

	private void revertTransformation(Mapping mapping, Transformation transformation) {
		if (printTransformationProgress) {
			LoggerUtils.logTransformation(transformation, LoggerUtils.DIRECTION_UP);
		}
		mapping.removeTransformation(transformation);
		Transformator.revert(mapping, transformation);
		decrementCounter();
	}

	private void incrementCounter() {
		if (trackProgress && counter.intValue() == 0) {
			LoggerUtils.log("Backtrack hit recursive level 0");
		}
		counter++;
	}

	private void decrementCounter() {
		Utils.validatePositive(counter);
		counter--;
	}
	
	private void computeEntitySetsPriority(List<EntitySet> directStudentEntitySets, EntitySet exemplarEntitySet) {
		directStudentEntitySets.forEach(es -> es.setPriority(Double.valueOf(1 - getDictionary().getSimilarity(es.getName(), exemplarEntitySet.getName()))));
	}

	public LanguageProcessor getDictionary() {
		if (dictionary == null) {
			dictionary = new Dictionary();
		}
		return dictionary;
	}

}
