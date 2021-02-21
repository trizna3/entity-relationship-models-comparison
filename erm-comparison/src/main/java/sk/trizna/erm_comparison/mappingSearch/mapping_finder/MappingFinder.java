package sk.trizna.erm_comparison.mappingSearch.mapping_finder;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import sk.trizna.erm_comparison.common.BugfixSnapshot;
import sk.trizna.erm_comparison.common.Clock;
import sk.trizna.erm_comparison.common.UniqueIdGenerator;
import sk.trizna.erm_comparison.common.enums.EnumConstants;
import sk.trizna.erm_comparison.common.enums.SimilarityConstantsUtils;
import sk.trizna.erm_comparison.common.key_config.AppConfigManager;
import sk.trizna.erm_comparison.common.utils.ERModelUtils;
import sk.trizna.erm_comparison.common.utils.MappingUtils;
import sk.trizna.erm_comparison.common.utils.PrintUtils;
import sk.trizna.erm_comparison.common.utils.TransformationUtils;
import sk.trizna.erm_comparison.common.utils.Utils;
import sk.trizna.erm_comparison.comparing.EntitySetComparator;
import sk.trizna.erm_comparison.comparing.NamedComparator;
import sk.trizna.erm_comparison.comparing.mapping.Mapping;
import sk.trizna.erm_comparison.comparing.mapping.MappingEvaluation;
import sk.trizna.erm_comparison.comparing.mapping.MappingExtended;
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
	private boolean earlyStop = Boolean.valueOf(appConfigManager.getResource(EnumConstants.CONFIG_EARLY_STOP).toString());
	private int earlyStopBound = Integer.valueOf(appConfigManager.getResource(EnumConstants.CONFIG_EARLY_STOP_BOUND).toString());
	private boolean bugfixMode = Boolean.valueOf(appConfigManager.getResource(EnumConstants.CONFIG_BUGFIX_MODE).toString());
	
	/**
	 * Stack level counter
	 */
	private int transformationDepthCounter = 0;
	private int maxDepth = 5;
	
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
	public MappingExtended findBestMapping(ERModel exemplarModel, ERModel studentModel) {
		
		preProcessSearch(exemplarModel,studentModel);
		search(new Mapping(exemplarModel, studentModel));
		postProcessSearch();
		
		getMappingEvaluator().getBestMapping();
		
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
	private MappingExtended constructMapping() {
		ERModel exemplarModel = new ERModel();
		ERModel studentModel = new ERModel();
		
		MappingExtended mapping = new MappingExtended(exemplarModel,studentModel,getMappingEvaluator().getBestMapping());
		
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
		
		mapping.getMappingEvaluation().getExemplarRelationships().forEach(relationship -> {
			// model doesn't contain not-mapped entitySets, but relationship may contain such entitySets
			if(exemplarModel.containsAll(relationship.getEntitySets())) { 
				exemplarModel.addRelationship(relationship, true);
			}
		});
		mapping.getMappingEvaluation().getStudentRelationships().forEach(relationship -> {
			// model doesn't contain not-mapped entitySets, but relationship may contain such entitySets
			if(studentModel.containsAll(relationship.getEntitySets())) { 
				studentModel.addRelationship(relationship, true);
			}
		});
		
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
		transformations = filterForbiddenTransformations(transformations,mapping);
		
		List<List<Transformation>> decomposition = decomposePossibleTransformations(transformations);
		
		for (List<Transformation> decPart : decomposition) {
			for (int combSize=1; combSize<=decPart.size();combSize++) {
				for (int[] combinationIndices : Utils.generateCombinations(decPart.size(), combSize)) {
					int[] excludedIndices = Utils.getExcludedIndices(combinationIndices, decPart.size()-1);
					
					BugfixSnapshot snapshot = null;
					if (bugfixMode) {
						snapshot = bugfixExecution(mapping,decPart,combinationIndices);
					}
					
					executeAll(decPart, combinationIndices, mapping);
					forbidAll(decPart, excludedIndices, mapping);
					incrementTransDepthCounter();
					transformationNodesCount ++;
					
					search(mapping);
					
					decrementTransDepthCounter();
					permitAll(decPart, excludedIndices, mapping);
					revertAll(decPart, combinationIndices, mapping);
					
					if (bugfixMode) {
						bugfixRevert(mapping, snapshot, decPart, combinationIndices);
					}
				}
			}
		}
		
		TransformationAnalyst.freeTransformations(transformations);
	}
	
	private BugfixSnapshot bugfixExecution(Mapping mapping, List<Transformation> transformations, int[] transformationIndices) {
		String id = UniqueIdGenerator.generateId();
		PrintUtils.logTransformationBatch(id, transformations, transformationIndices, PrintUtils.DIRECTION_DOWN);
		return new BugfixSnapshot(id,ERModelUtils.getClone(mapping.getExemplarModel()),ERModelUtils.getClone(mapping.getStudentModel()));
	}
	
	private void bugfixRevert(Mapping mapping, BugfixSnapshot bugfixSnapshot, List<Transformation> transformations, int[] transformationIndices) {
		PrintUtils.logTransformationBatch(bugfixSnapshot.getNestingId(), transformations, transformationIndices, PrintUtils.DIRECTION_UP);
		
		if (!ERModelUtils.modelsAreEqual(mapping.getExemplarModel(), bugfixSnapshot.getExemplarModelClone())) {
			PrintUtils.log("!!! models are not equal");
			ERModelUtils.modelsAreEqual(mapping.getExemplarModel(), bugfixSnapshot.getExemplarModelClone());
			throw new IllegalStateException();
		}
		if (!ERModelUtils.modelsAreEqual(mapping.getStudentModel(), bugfixSnapshot.getStudentModelClone())) {
			PrintUtils.log("!!! models are not equal");
			ERModelUtils.modelsAreEqual(mapping.getStudentModel(), bugfixSnapshot.getStudentModelClone());
			throw new IllegalStateException();
		}
	}
	
	private List<Transformation> filterForbiddenTransformations(List<Transformation> possibleTransformations, Mapping mapping) {
		possibleTransformations.removeAll(mapping.getForbiddenTransformations());
		
		
		for (Transformation forbiddenTransformation : mapping.getForbiddenTransformations()) {
			Iterator<Transformation> it = possibleTransformations.iterator();
			while (it.hasNext()) {
				if (TransformationUtils.areEqual(forbiddenTransformation, it.next())) {
					it.remove();
				}
			}
		}
		
		return possibleTransformations;
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
		transformation.getPreconditions().forEach(precondition -> {
			executeTransformation(mapping,precondition);
		});
		Transformator.execute(mapping, transformation);
		mapping.addTransformation(transformation);
	}

	private void revertTransformation(Mapping mapping, Transformation transformation) {
		mapping.removeTransformation(transformation);
		Transformator.revert(mapping, transformation);
		transformation.getPreconditions().forEach(precondition -> {
			revertTransformation(mapping,precondition);
		});
	}

	private void incrementTransDepthCounter() {
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
			transformation.setProcessed(true);
			for (Transformation other : possibleTransformations) {
				if (other.isProcessed() || transformation == other) {
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
