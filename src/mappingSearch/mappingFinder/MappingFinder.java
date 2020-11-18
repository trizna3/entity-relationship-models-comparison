package mappingSearch.mappingFinder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import common.Clock;
import common.ConfigManager;
import common.LoggerUtils;
import common.MappingUtils;
import common.PrintUtils;
import common.TransformationUtils;
import common.Utils;
import common.enums.EnumConstants;
import comparing.EntitySetComparator;
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
	private int depthCounter = 0;
	private int maxDepth = 3;
	private int maxDepthReached = 0;
	
	private int mappingNodesCount = 0;
	private int transformationNodesCount = 0;

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
		} else {
			Clock.getInstance().start();
		}
		
		search(new Mapping(exemplarModel, studentModel));
		if (printResult) {
			System.out.println("Best mapping penalty = " + getMappingEvaluator().getBestPenalty());
			System.out.println("Total mapping nodes = " + mappingNodesCount + ", Total transformation nodes = " + transformationNodesCount);
			System.out.println("Time elapsed = " + Clock.getInstance().getTimeElapsed());
			System.out.println(PrintUtils.print(getMappingEvaluator().getBestMapping()));
			System.out.println(PrintUtils.print(getMappingEvaluator().getBestMappingTransformations()));
			System.out.println("-");
		}
		return getMappingEvaluator().getBestMapping();
	}

	/**
	 * Performs backtracking algorithm to find optimal entity sets mapping.
	 */
	private void search(Mapping mapping) {
		if (stoppingCriterionReached(mapping)) {
			evaluate(mapping);
			return;
		}
		searchThroughTransformation(mapping);
		searchThroughMapping(mapping);
	}
	
	private boolean stoppingCriterionReached(Mapping mapping) {
		// early stop condition satisfied
		if (earlyStop && Clock.getInstance().boundReached()) {
			return true;
		}
		// maximum recursion depth reached
		if (depthCounter >= maxDepth) {
			return true;
		}
		// branch can be pruned due to it's high current penalty
		if (shallPrune(mapping)) {
			return true;
		}
		// branch is complete and cannot be extended more 
		if (branchComplete(mapping)) {
			return true;
		}
		return false;
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
		transformations.removeAll(mapping.getForbiddenTransformations());
		
		List<List<Transformation>> decomposition = decomposePossibleTransformations(transformations);
		
		for (List<Transformation> decPart : decomposition) {
			for (int combSize=1; combSize<=decPart.size();combSize++) {
				for (int[] combinationIndices : Utils.generateCombinations(decPart.size(), combSize)) {
					int[] excludedIndices = Utils.getExcludedIndices(combinationIndices, decPart.size()-1);
					
					executeAll(decPart, combinationIndices, mapping);
					forbidAll(decPart, excludedIndices, mapping);
					incrementDepthCounter();
					transformationNodesCount ++;
					search(mapping);
					
					decrementDepthCounter();
					permitAll(decPart, excludedIndices, mapping);
					revertAll(decPart, combinationIndices, mapping);
				}
			}
		}
		
		TransformationAnalyst.freeTransformations(transformations);
	}

	private void map(Mapping mapping, EntitySet exemplarEntitySet, EntitySet studentEntitySet) {
		mapping.map(exemplarEntitySet, studentEntitySet);
		mappingNodesCount ++;
//		incrementDepthCounter();
	}

	private void unmap(Mapping mapping, EntitySet exemplarEntitySet, EntitySet studentEntitySet) {
		mapping.unmap(exemplarEntitySet, studentEntitySet);
//		decrementDepthCounter();
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
	}

	private void revertTransformation(Mapping mapping, Transformation transformation) {
		if (printTransformationProgress) {
			LoggerUtils.logTransformation(transformation, LoggerUtils.DIRECTION_UP);
		}
		mapping.removeTransformation(transformation);
		Transformator.revert(mapping, transformation);
	}

	private void incrementDepthCounter() {
		if (trackProgress && depthCounter == 0) {
			LoggerUtils.log("Backtrack hit recursive level 0");
		}
		depthCounter++;
		
//		if (depthCounter > maxDepthReached) {
//			System.out.println("Max recursion level reached = " + depthCounter);
//			maxDepthReached = depthCounter;
//		}
	}

	private void decrementDepthCounter() {
		Utils.validatePositive(depthCounter);
		depthCounter--;
	}
	
	private void computeEntitySetsPriority(List<EntitySet> directStudentEntitySets, EntitySet exemplarEntitySet) {
		directStudentEntitySets.forEach(es -> es.setPriority(Double.valueOf(1 - getDictionary().getSimilarity(es.getNameText(), exemplarEntitySet.getNameText()))));
	}

	public LanguageProcessor getDictionary() {
		if (dictionary == null) {
			dictionary = new Dictionary();
		}
		return dictionary;
	}
	
	/**
	 * Decomposes given possibleTransformations into independent 'sets'.
	 * Purpose: Transformations of one independent part CAN be executed in the same time.   
	 * @param possibleTransformations
	 * @return
	 */
	private List<List<Transformation>> decomposePossibleTransformations(List<Transformation> possibleTransformations) {
		List<List<Transformation>> decomposition = new ArrayList<>(possibleTransformations.size());
		for (Transformation transformation : possibleTransformations) {
			if (transformation.isProcessed()) {
				continue;
			}
			List<Transformation> decPart = new ArrayList<>(possibleTransformations.size());
			decPart.add(transformation);
			for (Transformation other : possibleTransformations) {
				if (other.isProcessed()) {
					continue;
				}
				// must be independent with ALL current decPart Transformations
				boolean independent = true;
				for (Transformation currentTransformation : decPart) {
					if (!TransformationUtils.areIndependent(currentTransformation, other)) {
						independent = false;
						break;
					}
				}
				if (independent) {
					decPart.add(other);
					other.setProcessed(true);
				}				
			}
			decomposition.add(decPart);
		}
		return decomposition;
	}
	
	/**
	 * Executes all transformations on given positions
	 * @param transformations
	 * @param indices
	 * @param mapping
	 */
	private void executeAll(List<Transformation> transformations, int[] indices, Mapping mapping) {
		for (int index : indices) {
			executeTransformation(mapping, transformations.get(index));
		}
	}
	
	/**
	 * Reverts all transformations on given positions, working in reverse order
	 * @param transformations
	 * @param indices
	 * @param mapping
	 */
	private void revertAll(List<Transformation> transformations, int[] indices, Mapping mapping) {
		for (int i = indices.length-1; i >= 0 ; i--) {
			int index = indices[i];
			revertTransformation(mapping, transformations.get(index));
		}
	}
	
	private void forbidAll(List<Transformation> transformations, int[] indices, Mapping mapping) {
		for (int index : indices) {
			mapping.addForbiddenTransformation(transformations.get(index));
		}
	} 
	
	private void permitAll(List<Transformation> transformations, int[] indices, Mapping mapping) {
		for (int index : indices) {
			mapping.removeForbiddenTransformation(transformations.get(index));
		}
	}
}
