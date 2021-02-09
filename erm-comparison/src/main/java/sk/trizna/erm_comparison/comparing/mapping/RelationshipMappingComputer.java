package sk.trizna.erm_comparison.comparing.mapping;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sk.trizna.erm_comparison.common.ERModelUtils;
import sk.trizna.erm_comparison.common.RelationshipUtils;
import sk.trizna.erm_comparison.common.Utils;
import sk.trizna.erm_comparison.comparing.AttributedComparator;
import sk.trizna.erm_comparison.entity_relationship_model.Association;
import sk.trizna.erm_comparison.entity_relationship_model.ERModel;
import sk.trizna.erm_comparison.entity_relationship_model.EntitySet;
import sk.trizna.erm_comparison.entity_relationship_model.Generalization;
import sk.trizna.erm_comparison.entity_relationship_model.Relationship;

public class RelationshipMappingComputer {

	private RelationshipMappingEvaluator evaluator = new RelationshipMappingEvaluator();
	
	/**
	 * Returns relationship Map<exemplar,student>.
	 * 
	 * @param mapping
	 * @return
	 */
	public Map<Relationship,Relationship> computeRelationshipMapping(MappingExtended mapping) {
		Utils.validateNotNull(mapping);
		Utils.validateNotNull(mapping.getExemplarModel());
		Utils.validateNotNull(mapping.getStudentModel());
		
		Map<Relationship,Relationship> relMapping = new HashMap<Relationship, Relationship>();
		
		relMapping.putAll(computeBinaryRelationshipsMapping(mapping.getExemplarModel(), mapping.getStudentModel(), relMapping));
		relMapping.putAll(computeNaryRelationshipsMapping(mapping.getExemplarModel(), mapping.getStudentModel(), relMapping));

		return relMapping;
	}
	
	private Map<Relationship,Relationship> computeBinaryRelationshipsMapping(ERModel exemplarModel, ERModel studentModel, Map<Relationship,Relationship> relMapping) {
		// lower matrix triangle only to avoid duplicate pairs
		for (int i = 0; i < exemplarModel.getEntitySets().size(); i++) {
			for (int j = i; j < exemplarModel.getEntitySets().size(); j++) {
				
				EntitySet entitySet1 = exemplarModel.getEntitySets().get(i);
				EntitySet entitySet2 = exemplarModel.getEntitySets().get(j);
				
				List<Relationship> exemplarRels = ERModelUtils.getRelationshipsByEntitySets(exemplarModel, Arrays.asList(entitySet1,entitySet2));
				List<Relationship> studentRels = ERModelUtils.getRelationshipImage(Arrays.asList(entitySet1,entitySet2), studentModel);
				
				if (exemplarRels.isEmpty() || studentRels.isEmpty()) {
					continue;
				} else if (exemplarRels.size() == 1 && studentRels.size() == 1) {
					if (Utils.isSameClass(exemplarRels.get(0), studentRels.get(0))) {
						relMapping.put(exemplarRels.get(0), studentRels.get(0));
					}
				} else {
					relMapping.putAll(findBestMapping(exemplarRels,studentRels));
				}
			}
		}
		return relMapping;
	}
	
	private Map<Relationship,Relationship> computeNaryRelationshipsMapping(ERModel exemplarModel, ERModel studentModel, Map<Relationship,Relationship> relMapping) {
		
		// in case of multiple N-ary relationships (it hasn't happen yet, who knows).
		// Map pairs are <studentRels,exemplarRels>
		Map<List<Relationship>,List<Relationship>> postProcessRelationships = null;
		
		for (Relationship exemplarRel : exemplarModel.getRelationships()) {
			if (exemplarRel.getSides().size() < 3) {
				continue;
			}
			
			List<Relationship> studentRels = ERModelUtils.getRelationshipImage(exemplarRel, studentModel);
			if (studentRels.size() == 1) {
				relMapping.put(exemplarRel, studentRels.get(0));
			} else if (studentRels.size() > 1) {
				if (postProcessRelationships == null) {
					postProcessRelationships = new HashMap<List<Relationship>, List<Relationship>>();
				}
				if (postProcessRelationships.containsKey(studentRels)) {
					postProcessRelationships.get(studentRels).add(exemplarRel);
				} else {
					postProcessRelationships.put(studentRels,Arrays.asList(exemplarRel));					
				}
			}
		}
		if (postProcessRelationships != null && !postProcessRelationships.isEmpty()) {
			for (List<Relationship> studentRels : postProcessRelationships.keySet()) {
				relMapping.putAll(findBestMapping(postProcessRelationships.get(studentRels),studentRels));
			}
		}
		return relMapping;
	}
	
	private Map<Relationship,Relationship> findBestMapping(List<Relationship> exemplarRelationships, List<Relationship> studentRelationships) {
		// do some brute force backtrack
		searchMapping(new HashMap<Relationship, Relationship>(), exemplarRelationships, studentRelationships);
		return getEvaluator().getBestMapping();
	}
	
	private void searchMapping(Map<Relationship,Relationship> mapping, List<Relationship> exemplarRelationships, List<Relationship> studentRelationships) {
		if (exemplarRelationships.isEmpty() || studentRelationships.isEmpty()) {
			getEvaluator().evaluate(mapping);
			return;
		}
		
		Relationship exemplarRel = exemplarRelationships.get(0);
		for (int i = 0; i < studentRelationships.size(); i++) {
			Relationship studentRel = studentRelationships.get(i);
			
			mapping.put(exemplarRel, studentRel);
			exemplarRelationships.remove(exemplarRel);
			studentRelationships.remove(studentRel);
			
			searchMapping(mapping, exemplarRelationships, studentRelationships);
			
			studentRelationships.add(i, studentRel);
			exemplarRelationships.add(0, exemplarRel);
			mapping.remove(exemplarRel, studentRel);
		}
	}
	
	private RelationshipMappingEvaluator getEvaluator() {
		return evaluator;
	}

	private class RelationshipMappingEvaluator {
		private double bestScore = -1;
		private Map<Relationship,Relationship> bestMapping;
		private AttributedComparator attributedComparator = AttributedComparator.getInstance();
		
		private static final double RELATIONSHIP_WEIGHT = 5;
		
		public void evaluate(Map<Relationship,Relationship> mapping) {
			Utils.validateNotNull(mapping);
			double currentScore = rateMapping(mapping);
			
			if (currentScore > bestScore) {
				bestScore = currentScore;
				bestMapping = new HashMap<Relationship, Relationship>(mapping);
			}
		}
		
		private double rateMapping(Map<Relationship,Relationship> mapping) {
			double score = 0;
			for (Relationship exemplar : mapping.keySet()) {
				Relationship student = mapping.get(exemplar);
				
				if (!Utils.isSameClass(exemplar, student)) {
					continue;
				}
				if (exemplar.getSides().size() != student.getSides().size()) {
					continue;
				}
				if (exemplar instanceof Association) {
					Association exemplarAssoc = (Association) exemplar;
					Association studentAssoc = (Association) student;
					// half points for attributes
					score += (RELATIONSHIP_WEIGHT / 2.0) * attributedComparator.compareSymmetric(exemplarAssoc, studentAssoc);
					// half points for cardinalities
					for (EntitySet exemplarEs : exemplarAssoc.getEntitySets()) {
						if (Utils.areEqual(
								RelationshipUtils.getRole(exemplarAssoc, exemplarEs),
								RelationshipUtils.getRole(studentAssoc, exemplarEs.getMappedTo()))) {
							score += (RELATIONSHIP_WEIGHT / 2.0) / exemplarAssoc.getEntitySets().size();
						}
					}
					
				} else {
					Generalization exemplarGen = (Generalization) exemplar;
					Generalization studentGen = (Generalization) student;
					score += Utils.areEqual(exemplarGen.getSuperEntitySet().getMappedTo(),studentGen.getSuperEntitySet()) ? RELATIONSHIP_WEIGHT : 0;
				}
				
			}
			return score / (double) mapping.keySet().size();
		}

		public Map<Relationship,Relationship> getBestMapping() {
			return bestMapping;
		}
	}
}
