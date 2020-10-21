package mappingSearch.mappingEvaluator;

import common.RelationshipUtils;
import common.StringUtils;
import common.Utils;
import common.enums.EnumTransformation;
import comparing.Mapping;
import entityRelationshipModel.Association;
import entityRelationshipModel.Attribute;
import entityRelationshipModel.Attributed;
import entityRelationshipModel.ERModel;
import entityRelationshipModel.EntitySet;
import entityRelationshipModel.Relationship;

/**
 * @author Adam Trizna
 */

/**
 * Component for evaluating model mappings.
 *
 */
public class MappingEvaluator {
	
	TransformationEvaluator transformationEvaluator = new TransformationEvaluator();

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
		
		return penalty;
	}

	private double checkEntitySets(Mapping mapping) {
		double penalty = 0;
		
		for (EntitySet entitySet : mapping.getExemplarModel().getEntitySets()) {
			if (entitySet.getMappedTo() == null) {
				continue;
			}
			if (entitySet.getMappedTo().isEmpty()) {
				penalty += transformationEvaluator.penalizeTransformation(EnumTransformation.CREATE_ENTITY_SET);
			}
		}
		for (EntitySet entitySet : mapping.getStudentModel().getEntitySets()) {
			if (entitySet.getMappedTo() == null) {
				continue;
			}
			if (entitySet.getMappedTo().isEmpty()) {
				penalty += transformationEvaluator.penalizeTransformation(EnumTransformation.REMOVE_ENTITY_SET);
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
						penalty += transformationEvaluator.penalizeTransformation(EnumTransformation.CHANGE_CARDINALITY);

						exemplarModel.process(exemplarRel);
						studentModel.process(studentRel);
						break;
					}
				}

				if (exemplarRel instanceof Association) {
					penalty += transformationEvaluator.penalizeTransformation(EnumTransformation.CREATE_ASSOCIATION);
				} else {
					penalty += transformationEvaluator.penalizeTransformation(EnumTransformation.CREATE_GENERALIZATION);
				}
			}
		}

		for (Relationship studentRelationship : studentModel.getRelationships()) {
			if (studentRelationship.isProcessed()) {
				continue;
			}
			if (studentRelationship instanceof Association) {
				penalty += transformationEvaluator.penalizeTransformation(EnumTransformation.REMOVE_ASSOCIATION);
			} else {
				penalty += transformationEvaluator.penalizeTransformation(EnumTransformation.REMOVE_GENERALIZATION);
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
				if (!StringUtils.areEqual(entitySet.getName(), image.getName())) {
					penalty += transformationEvaluator.penalizeTransformation(EnumTransformation.RENAME_ENTITY_SET);
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
				penalty += transformationEvaluator.penalizeTransformation(EnumTransformation.CREATE_ATTRIBUTE);
			}
		}

		for (Attribute attribute : studentAttributed.getAttributes()) {
			if (!exemplarAttributed.getAttributes().contains(attribute)) {
				penalty += transformationEvaluator.penalizeTransformation(EnumTransformation.REMOVE_ATTRIBUTE);
			}
		}
		
		return penalty;
	}
}
