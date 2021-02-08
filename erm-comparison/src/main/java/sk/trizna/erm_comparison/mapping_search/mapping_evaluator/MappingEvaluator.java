package sk.trizna.erm_comparison.mapping_search.mapping_evaluator;

import sk.trizna.erm_comparison.common.RelationshipUtils;
import sk.trizna.erm_comparison.common.Utils;
import sk.trizna.erm_comparison.common.enums.EnumTransformation;
import sk.trizna.erm_comparison.comparing.NamedComparator;
import sk.trizna.erm_comparison.comparing.mapping.Mapping;
import sk.trizna.erm_comparison.entity_relationship_model.Association;
import sk.trizna.erm_comparison.entity_relationship_model.Attribute;
import sk.trizna.erm_comparison.entity_relationship_model.Attributed;
import sk.trizna.erm_comparison.entity_relationship_model.ERModel;
import sk.trizna.erm_comparison.entity_relationship_model.EntitySet;
import sk.trizna.erm_comparison.entity_relationship_model.Relationship;
import sk.trizna.erm_comparison.transformations.Transformation;

/**
 * @author Adam Trizna
 */

/**
 * Component for evaluating model mappings.
 *
 */
public class MappingEvaluator {
	
	TransformationEvaluator transformationEvaluator = new TransformationEvaluator();
	private NamedComparator namedComparator;

	/**
	 * Processes mapping. 
	 * 
	 * Original behavior:
	 * 		Extends mapping transformation list by atomic
	 * 		transformations, which would lead to (mapped) isomorphism between models.
	 * 
	 * Current behavior:
	 * 		Same transformations are considered, but they are not longer extending 
	 * 		any transformation list, but are being penalized right away.
	 * 
	 * NotMapped ER models elements are ignored.
	 * 
	 * @param mapping
	 */
	public double computeMappingPenalty(Mapping mapping) {
		Utils.validateNotNull(mapping);
		
		double penalty = 0;
		
		penalty += checkEntitySets(mapping);
		penalty += checkRelationships(mapping);
		penalty += checkMappingPairs(mapping);
		
		for (Transformation t : mapping.getTransformations()) {
			penalty += getTransformationEvaluator().penalizeTransformation(t);
		}
		
		return penalty;
	}

	private double checkEntitySets(Mapping mapping) {
		double penalty = 0;
		
		for (EntitySet entitySet : mapping.getExemplarModel().getEntitySets()) {
			if (entitySet.getMappedTo() == null) {
				continue;
			}
			if (entitySet.getMappedTo().isEmpty()) {
				penalty += getTransformationEvaluator().penalizeTransformation(EnumTransformation.CREATE_ENTITY_SET);
			}
		}
		for (EntitySet entitySet : mapping.getStudentModel().getEntitySets()) {
			if (entitySet.getMappedTo() == null) {
				continue;
			}
			if (entitySet.getMappedTo().isEmpty()) {
				penalty += getTransformationEvaluator().penalizeTransformation(EnumTransformation.REMOVE_ENTITY_SET);
			}
		}
		
		return penalty;
	}

	private double checkRelationships(Mapping mapping) {
		double penalty = 0;
		
		ERModel exemplarModel = mapping.getExemplarModel();
		ERModel studentModel = mapping.getStudentModel();
		
		exemplarModel.prepareRelationshipsForProcessing();
		studentModel.prepareRelationshipsForProcessing();
		
		// check pairs by incidentEntitySets, Cardinalities, Attributes
		for (Relationship exemplarRel : mapping.getExemplarModel().getRelationships()) {
			if (exemplarRel.isProcessed()) {
				continue;
			}
			for (Relationship studentRel : mapping.getStudentModel().getRelationships()) {
				if (studentRel.isProcessed()) {
					continue;
				}
				if (RelationshipUtils.relationshipsAreEquallyMapped(exemplarRel, studentRel, true, true)) {
					exemplarModel.process(exemplarRel);
					studentModel.process(studentRel);
					break;
				}
			}
		}
		// check pairs by incidentEntitySets, Cardinalities --> add/remove attributes
		if (exemplarModel.getToProcess() > 0 || studentModel.getToProcess() > 0) {
			for (Relationship exemplarRel : mapping.getExemplarModel().getRelationships()) {
				if (exemplarRel.isProcessed()) {
					continue;
				}
				for (Relationship studentRel : mapping.getStudentModel().getRelationships()) {
					if (studentRel.isProcessed()) {
						continue;
					}
					if (RelationshipUtils.relationshipsAreEquallyMapped(exemplarRel, studentRel, true, false)) {
						if (exemplarRel instanceof Association) {
							penalty += checkAttributes(mapping, (Association) exemplarRel, (Association) studentRel);
						}
						exemplarModel.process(exemplarRel);
						studentModel.process(studentRel);
						break;
					}
				}
			}
		}
		// check pairs by incidentEntitySets only --> add/remove attribute, edit
		// cardinalities or add/remove whole relationships
		if (exemplarModel.getToProcess() > 0 || studentModel.getToProcess() > 0) {
			for (Relationship exemplarRel : mapping.getExemplarModel().getRelationships()) {
				if (exemplarRel.isProcessed()) {
					continue;
				}
				for (Relationship studentRel : mapping.getStudentModel().getRelationships()) {
					if (studentRel.isProcessed()) {
						continue;
					}
					if (RelationshipUtils.relationshipsAreEquallyMapped(exemplarRel, studentRel, false, false)) {
						if (exemplarRel instanceof Association) {
							penalty += checkAttributes(mapping, (Association) exemplarRel, (Association) studentRel);
						}
						penalty += getTransformationEvaluator().penalizeTransformation(EnumTransformation.CHANGE_CARDINALITY);

						exemplarModel.process(exemplarRel);
						studentModel.process(studentRel);
						break;
					}
				}

				if (exemplarRel instanceof Association) {
					penalty += getTransformationEvaluator().penalizeTransformation(EnumTransformation.CREATE_ASSOCIATION);
				} else {
					penalty += getTransformationEvaluator().penalizeTransformation(EnumTransformation.CREATE_GENERALIZATION);
				}
			}
		}

		for (Relationship studentRelationship : studentModel.getRelationships()) {
			if (studentRelationship.isProcessed()) {
				continue;
			}
			if (studentRelationship instanceof Association) {
				penalty += getTransformationEvaluator().penalizeTransformation(EnumTransformation.REMOVE_ASSOCIATION);
			} else {
				penalty += getTransformationEvaluator().penalizeTransformation(EnumTransformation.REMOVE_GENERALIZATION);
			}
		}
		
		exemplarModel.unprocessAllRelationships();
		studentModel.unprocessAllRelationships();
		
		return penalty;
	}

	private double checkMappingPairs(Mapping mapping) {
		double penalty = 0;
		
		for (EntitySet entitySet : mapping.getExemplarModel().getEntitySets()) {
			EntitySet image = entitySet.getMappedTo();
			if (image != null) {
				if (getNamedComparator().getNamesSimilarity(entitySet.getName(), image.getName()) < 1) {
					penalty += getTransformationEvaluator().penalizeTransformation(EnumTransformation.RENAME_ENTITY_SET);
				}
				penalty += checkAttributes(mapping, entitySet, image);
			}
		}
		return penalty;
	}

	private double checkAttributes(Mapping mapping, Attributed exemplarAttributed, Attributed studentAttributed) {
		double penalty = 0;

		for (Attribute attribute : exemplarAttributed.getAttributes()) {
			if (!studentAttributed.getAttributes().contains(attribute)) {
				penalty += getTransformationEvaluator().penalizeTransformation(EnumTransformation.CREATE_ATTRIBUTE);
			}
		}

		for (Attribute attribute : studentAttributed.getAttributes()) {
			if (!exemplarAttributed.getAttributes().contains(attribute)) {
				penalty += getTransformationEvaluator().penalizeTransformation(EnumTransformation.REMOVE_ATTRIBUTE);
			}
		}
		
		return penalty;
	}

	private TransformationEvaluator getTransformationEvaluator() {
		return transformationEvaluator;
	}

	private NamedComparator getNamedComparator() {
		if (namedComparator == null) {
			namedComparator = NamedComparator.getInstance();
		}
		return namedComparator;
	}
}
