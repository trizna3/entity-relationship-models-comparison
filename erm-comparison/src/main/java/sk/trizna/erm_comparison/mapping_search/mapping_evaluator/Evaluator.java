package sk.trizna.erm_comparison.mapping_search.mapping_evaluator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sk.trizna.erm_comparison.common.utils.ERModelUtils;
import sk.trizna.erm_comparison.common.utils.TransformationUtils;
import sk.trizna.erm_comparison.common.utils.Utils;
import sk.trizna.erm_comparison.comparing.mapping.Mapping;
import sk.trizna.erm_comparison.comparing.mapping.MappingEvaluation;
import sk.trizna.erm_comparison.entity_relationship_model.Association;
import sk.trizna.erm_comparison.entity_relationship_model.EntitySet;
import sk.trizna.erm_comparison.entity_relationship_model.Generalization;
import sk.trizna.erm_comparison.entity_relationship_model.Relationship;
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
	private List<Relationship> bestMappingRelationshipsExemplar;
	private List<Relationship> bestMappingRelationshipsStudent;
	private List<Transformation> bestMappingTransformations;
	private Double bestMappingPenalty;
	MappingEvaluator mappingEvaluator = new MappingEvaluator();

	@Override
	public MappingEvaluation evaluateMapping(Mapping mapping, boolean finalize) {
		Utils.validateNotNull(mapping);
		
		double penalty;
		if (finalize) {
			List<EntitySet> finalizedEntitySets = finalizeMapping(mapping);
			penalty = computePenalty(mapping);
			unfinalizeMapping(mapping, finalizedEntitySets);
			evaluate(mapping, penalty);
		} else {
			penalty = computePenalty(mapping);
		}	 		
		
		return new MappingEvaluation(null, null, null, null, penalty);
	}

	@Override
	public MappingEvaluation getBestMapping() {
		return new MappingEvaluation(getBestEntitySetMap(),
				getBestMappingRelationshipsExemplar(),
				getBestMappingRelationshipsStudent(),
				getBestMappingTransformations(),
				getBestMappingPenalty());
	}
	
	private double computePenalty(Mapping mapping) {
		return mappingEvaluator.computeMappingPenalty(mapping);
	}

	private void evaluate(Mapping mapping, double actualPenalty) {
		if (getBestMappingPenalty() == null || actualPenalty < getBestMappingPenalty()) {
			setBestMappingPenalty(actualPenalty);
			setBestMappingTransformations(TransformationUtils.copyTransformationList(mapping.getTransformations()));
			saveBestModelsState(mapping);
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
		if (targetEntitySets == null) {
			return;
		}
		ERModelUtils.unfinalizeModel(mapping.getStudentModel(),targetEntitySets);
		ERModelUtils.unfinalizeModel(mapping.getExemplarModel(),targetEntitySets);
	}
	
	/**
	 * Saves models snapshot (clones key parts of models), since input models will be subject to further search
	 * 
	 * @param mapping
	 */
	private void saveBestModelsState(Mapping mapping) {
		Map<EntitySet,EntitySet> cloneMap = saveBestEntitySetsMapping(mapping);
		saveBestRelationships(mapping, cloneMap);	
	}
	
	/**
	 * Saves best entitySet mapping.
	 * Returns map of original->cloned entity sets.
	 * 
	 * @param mapping
	 * @return
	 */
	private Map<EntitySet,EntitySet> saveBestEntitySetsMapping(Mapping mapping) {
		Map<EntitySet,EntitySet> cloneMap = new HashMap<EntitySet, EntitySet>();
		
		Map<EntitySet, EntitySet> bestMapping = new HashMap<EntitySet, EntitySet>();
		for (EntitySet entitySet : mapping.getExemplarModel().getEntitySets()) {
			if (entitySet.getMappedTo() != null) {
				EntitySet entitySet1Clone = new EntitySet(entitySet); 
				EntitySet entitySet2Clone = new EntitySet(entitySet.getMappedTo());
				
				cloneMap.put(entitySet, entitySet1Clone);
				cloneMap.put(entitySet.getMappedTo(), entitySet2Clone);
				bestMapping.put(entitySet1Clone, entitySet2Clone);
			}
		}
		// finalize cloneMap
		mapping.getExemplarModel().getEntitySets().forEach(entitySet -> {
			if (!cloneMap.containsKey(entitySet)) {
				cloneMap.put(entitySet, new EntitySet(entitySet));
			}
		});
		mapping.getStudentModel().getEntitySets().forEach(entitySet -> {
			if (!cloneMap.containsKey(entitySet)) {
				cloneMap.put(entitySet, new EntitySet(entitySet));
			}
		});
		
		setBestEntitySetMap(bestMapping);
		return cloneMap;
	}
	
	/**
	 * Saves best relationships, using original->clone entity set map.
	 * 
	 * @param mapping
	 * @param cloneMap
	 */
	private void saveBestRelationships(Mapping mapping, Map<EntitySet,EntitySet> cloneMap) {
		List<Relationship> bestRelationshipsExemplar = new ArrayList<Relationship>();
		List<Relationship> bestRelationshipsStudent = new ArrayList<Relationship>();
		
		for (Relationship rel : mapping.getExemplarModel().getRelationships()) {
			Relationship relClone;
			if (rel instanceof Association) {
				relClone = new Association((Association) rel, cloneMap);
			} else {
				relClone = new Generalization((Generalization) rel, cloneMap);
			}
			bestRelationshipsExemplar.add(relClone);
		}
		
		for (Relationship rel : mapping.getStudentModel().getRelationships()) {
			Relationship relClone;
			if (rel instanceof Association) {
				relClone = new Association((Association) rel, cloneMap);
			} else {
				relClone = new Generalization((Generalization) rel, cloneMap);
			}
			bestRelationshipsStudent.add(relClone);
		}
		
		setBestMappingRelationshipsExemplar(bestRelationshipsExemplar);
		setBestMappingRelationshipsStudent(bestRelationshipsStudent);
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
	
	private List<Relationship> getBestMappingRelationshipsExemplar() {
		if (bestMappingRelationshipsExemplar == null) {
			bestMappingRelationshipsExemplar = new ArrayList<Relationship>();
		}
		return bestMappingRelationshipsExemplar;
	}

	private void setBestMappingRelationshipsExemplar(List<Relationship> bestMappingRelationships) {
		this.bestMappingRelationshipsExemplar = bestMappingRelationships;
	}

	private List<Relationship> getBestMappingRelationshipsStudent() {
		if (bestMappingRelationshipsStudent == null) {
			bestMappingRelationshipsStudent = new ArrayList<Relationship>();
		}
		return bestMappingRelationshipsStudent;
	}

	private void setBestMappingRelationshipsStudent(List<Relationship> bestMappingRelationshipsStudent) {
		this.bestMappingRelationshipsStudent = bestMappingRelationshipsStudent;
	}
}