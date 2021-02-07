package sk.trizna.erm_comparison.mappingSearch.mapping_finder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import sk.trizna.erm_comparison.common.Clock;
import sk.trizna.erm_comparison.common.MappingUtils;
import sk.trizna.erm_comparison.common.PrintUtils;
import sk.trizna.erm_comparison.common.TransformationUtils;
import sk.trizna.erm_comparison.common.Utils;
import sk.trizna.erm_comparison.common.enums.EnumConstants;
import sk.trizna.erm_comparison.common.enums.SimilarityConstantsUtils;
import sk.trizna.erm_comparison.common.key_config.AppConfigManager;
import sk.trizna.erm_comparison.comparing.EntitySetComparator;
import sk.trizna.erm_comparison.comparing.Mapping;
import sk.trizna.erm_comparison.comparing.MappingEvaluation;
import sk.trizna.erm_comparison.comparing.NamedComparator;
import sk.trizna.erm_comparison.entity_relationship_model.ERModel;
import sk.trizna.erm_comparison.entity_relationship_model.EntitySet;
import sk.trizna.erm_comparison.mapping_search.mapping_evaluator.Evaluator;
import sk.trizna.erm_comparison.mapping_search.mapping_evaluator.IEvaluator;
import sk.trizna.erm_comparison.transformations.Transformation;
import sk.trizna.erm_comparison.transformations.TransformationAnalyst;
import sk.trizna.erm_comparison.transformations.Transformator;

/**
 * @author - Adam Trizna
 */

/**
 * Object for finding best mapping of given double of entity relationship
 * models.
 */
public class MappingFinder {

	private IEvaluator mappingEvaluator;
	private NamedComparator namedComparator;
	private AppConfigManager appConfigManager = AppConfigManager.getInstance();
	private Clock clock = new Clock();
	private boolean printResult = Boolean.valueOf(appConfigManager.getResource(EnumConstants.CONFIG_PRINT_RESULT).toString());
	private boolean printTransformationProgress = Boolean.valueOf(appConfigManager.getResource(EnumConstants.CONFIG_PRINT_TRANSFORMATION_PROGRESS).toString());
	private boolean trackProgress = Boolean.valueOf(appConfigManager.getResource(EnumConstants.CONFIG_TRACK_PROGRESS).toString());
	private boolean earlyStop = Boolean.valueOf(appConfigManager.getResource(EnumConstants.CONFIG_EARLY_STOP).toString());
	private int earlyStopBound = Integer.valueOf(appConfigManager.getResource(EnumConstants.CONFIG_EARLY_STOP_BOUND).toString());
	
	/**
	 * Stack level counter
	 */
	private int transformationDepthCounter = 0;
	private int maxDepth = 3;
	
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
	public Mapping getBestMapping(ERModel exemplarModel, ERModel studentModel) {
		
		preProcessSearch(exemplarModel,studentModel);
		search(new Mapping(exemplarModel, studentModel));
		postProcessSearch();
		
		return constructMapping();
	}
	
	/**
	 * MappingEvaluator returns Map<EntitySet,EntitySet> and List<Transformation>.
	 * Reason is we want to minimize created objects count, so we don't copy whole mappings in the search process.
	 * 
	 * Method constructs final Mapping object from MappingEvaluator results.
	 * 
	 * @param exemplarModel
	 * @param studentModel
	 * @return
	 */
	private Mapping constructMapping() {
		ERModel exemplarModel = new ERModel();
		ERModel studentModel = new ERModel();
		
		Mapping mapping = new Mapping(exemplarModel,studentModel);
		
		Map<EntitySet,EntitySet> esMap = getMappingEvaluator().getBestMapping().getEntitySetMap();
		for (EntitySet esKey : esMap.keySet()) {
			if (!exemplarModel.contains(esKey)) {
				exemplarModel.addEntitySet(esKey);
			}
			esKey.setMappedTo(esMap.get(esKey));
			if (!MappingUtils.EMPTY_ENTITY_SET.equals(esMap.get(esKey))) {
				if (!studentModel.contains(esMap.get(esKey))) {
					studentModel.addEntitySet(esMap.get(esKey));
				}
				esMap.get(esKey).setMappedTo(esKey);
			}
		}
		
		mapping.setTransformations(getMappingEvaluator().getBestMapping().getTransformations());
		
		return mapping;
	}
	
	private void preProcessSearch(ERModel exemplarModel, ERModel studentModel) {
		exemplarModel.setExemplar(true);
		studentModel.setExemplar(false);

		if (earlyStop) {
			clock.start(earlyStopBound);
		} else {
			clock.start();
		}
		
		exemplarModel.saveOriginalEntitySets();
		studentModel.saveOriginalEntitySets();
	}
	
	private void postProcessSearch() {
		if (printResult) {
			PrintUtils.log("Best mapping penalty = " + getMappingEvaluator().getBestMapping().getPenalty());
			PrintUtils.log("Total mapping nodes = " + mappingNodesCount + ", Total transformation nodes = " + transformationNodesCount);
			PrintUtils.log("Time elapsed = " + clock.getTimeElapsed());
			PrintUtils.log(PrintUtils.print(getMappingEvaluator().getBestMapping().getEntitySetMap()));
			PrintUtils.log(PrintUtils.print(getMappingEvaluator().getBestMapping().getTransformations()));
			PrintUtils.log("-");
		}
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
		if (earlyStop && clock.boundReached()) {
			return true;
		}
		// maximum recursion depth reached
		if (transformationDepthCounter >= maxDepth) {
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
		
		List<EntitySet> studentEntitySets = new ArrayList<>(studentModel.getNotMappedEntitySets());
		studentEntitySets.add(MappingUtils.EMPTY_ENTITY_SET);
		
		computeEntitySetsPriority(studentEntitySets,exemplarEntitySet);
		studentEntitySets.sort(EntitySetComparator.getInstance());
		
		for (EntitySet studentEntitySet : studentEntitySets) {
			if (studentEntitySet.getMappedTo() != null) {
				continue;
			}
			if (!isSuitablePair(mapping, exemplarEntitySet, studentEntitySet)) {
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
					incrementTransDepthCounter();
					transformationNodesCount ++;
					
					search(mapping);
					
					decrementTransDepthCounter();
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
	}

	private void unmap(Mapping mapping, EntitySet exemplarEntitySet, EntitySet studentEntitySet) {
		mapping.unmap(exemplarEntitySet, studentEntitySet);
	}

	private boolean branchComplete(Mapping mapping) {
		return mapping.getExemplarModel().getNotMappedEntitySets().isEmpty();
	}

	private boolean shallPrune(Mapping mapping) {
		MappingEvaluation mappingEvaluation = getMappingEvaluator().evaluateMapping(mapping, false);
		MappingEvaluation bestMappingEvaluation = getMappingEvaluator().getBestMapping();
		
		if (bestMappingEvaluation.getPenalty() == null) {
			// no best mapping has been found yet
			return false;
		}
		return mappingEvaluation.getPenalty() > bestMappingEvaluation.getPenalty();
	}

	private void evaluate(Mapping mapping) {
		getMappingEvaluator().evaluateMapping(mapping, true);
	}

	private IEvaluator getMappingEvaluator() {
		if (mappingEvaluator == null) {
			mappingEvaluator = new Evaluator();
		}
		return mappingEvaluator;
	}

	private void executeTransformation(Mapping mapping, Transformation transformation) {
		if (printTransformationProgress) {
			PrintUtils.logTransformation(transformation, PrintUtils.DIRECTION_DOWN);
		}
		Transformator.execute(mapping, transformation);
		mapping.addTransformation(transformation);
	}

	private void revertTransformation(Mapping mapping, Transformation transformation) {
		if (printTransformationProgress) {
			PrintUtils.logTransformation(transformation, PrintUtils.DIRECTION_UP);
		}
		mapping.removeTransformation(transformation);
		Transformator.revert(mapping, transformation);
	}

	private void incrementTransDepthCounter() {
		if (trackProgress && transformationDepthCounter == 0) {
			PrintUtils.log("Backtrack hit recursive level 0");
		}
		transformationDepthCounter++;
	}

	private void decrementTransDepthCounter() {
		Utils.validatePositive(transformationDepthCounter);
		transformationDepthCounter--;
	}
	
	private void computeEntitySetsPriority(List<EntitySet> directStudentEntitySets, EntitySet exemplarEntitySet) {
		directStudentEntitySets.forEach(es -> es.setPriority(Double.valueOf(1 - getNamedComparator().compareSymmetric(es, exemplarEntitySet))));
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

	private NamedComparator getNamedComparator() {
		if (namedComparator == null) {
			namedComparator = NamedComparator.getInstance();
		}
		return namedComparator;
	}
		
	private boolean isSuitablePair(Mapping mapping, EntitySet exemplarEntitySet, EntitySet studentEntitySet) {
		if (MappingUtils.EMPTY_ENTITY_SET.equals(studentEntitySet)) {
			return true;
		}
		if (!mapping.getExemplarModel().isOriginalEntitySet(exemplarEntitySet) || !mapping.getStudentModel().isOriginalEntitySet(studentEntitySet)) {
			// let entitySets, which were created during the search, pass
			return true;
		}
		if (EntitySetComparator.getInstance().compareSymmetric(exemplarEntitySet, studentEntitySet) >= SimilarityConstantsUtils.getEntitySetSimilarityTreshold()) {
			return true;
		}
		return false;
	}
}
