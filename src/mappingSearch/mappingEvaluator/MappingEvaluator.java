package mappingSearch.mappingEvaluator;

import java.util.ArrayList;
import java.util.List;

import common.ERModelUtils;
import common.RelationshipUtils;
import common.StringUtils;
import common.TransformationUtils;
import common.Utils;
import comparing.Mapping;
import entityRelationshipModel.Association;
import entityRelationshipModel.Attribute;
import entityRelationshipModel.Attributed;
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

	/**
	 * Processes mapping. Extends mapping transformation list by atomic
	 * transformations, which would lead to (mapped) isomorphism between models.
	 * 
	 * @param mapping
	 */
	public void expandTransformationList(Mapping mapping) {
		Utils.validateNotNull(mapping);

		checkEntitySets(mapping);
		checkRelationships(mapping);
		checkMappingPairs(mapping);
	}

	private void checkEntitySets(Mapping mapping) {
		for (EntitySet entitySet : mapping.getExemplarModel().getEntitySets()) {
			if (entitySet.getMappedTo() == null || entitySet.getMappedTo().isEmpty()) {
				EntitySet esCopy = ERModelUtils.copyEntitySetDetached(entitySet);
				TransformationUtils.addCreateEntitySet(mapping, esCopy);
			}
		}
		for (EntitySet entitySet : mapping.getStudentModel().getEntitySets()) {
			if (entitySet.getMappedTo() == null || entitySet.getMappedTo().isEmpty()) {
				TransformationUtils.addRemoveEntitySet(mapping, entitySet);
			}
		}
	}

	private void checkRelationships(Mapping mapping) {
		List<Relationship> exemplarToProcess = new ArrayList<>(mapping.getExemplarModel().getRelationships());
		List<Relationship> studentsToProcess = new ArrayList<>(mapping.getStudentModel().getRelationships());

		// check pairs by incidentEntitySets, Cardinalities, Attributes
		for (Relationship exemplarRel : mapping.getExemplarModel().getRelationships()) {
			for (Relationship studentRel : mapping.getStudentModel().getRelationships()) {
				if (!studentsToProcess.contains(studentRel)) {
					continue;
				}
				if (RelationshipUtils.relationshipsAreEquallyMapped(exemplarRel, studentRel, true, true)) {
					exemplarToProcess.remove(exemplarRel);
					studentsToProcess.remove(studentRel);
					break;
				}
			}
		}
		// check pairs by incidentEntitySets, Cardinalities --> add/remove attributes
		if (!exemplarToProcess.isEmpty() || !studentsToProcess.isEmpty()) {
			for (Relationship exemplarRel : mapping.getExemplarModel().getRelationships()) {
				for (Relationship studentRel : mapping.getStudentModel().getRelationships()) {
					if (!studentsToProcess.contains(studentRel)) {
						continue;
					}
					if (RelationshipUtils.relationshipsAreEquallyMapped(exemplarRel, studentRel, true, false)) {
						if (exemplarRel instanceof Association) {
							checkAttributes(mapping, (Association) exemplarRel, (Association) studentRel);
						}
						exemplarToProcess.remove(exemplarRel);
						studentsToProcess.remove(studentRel);
						break;
					}
				}
			}
		}
		// check pairs by incidentEntitySets only --> add/remove attribute, edit
		// cardinalities or add/remove whole relationships
		if (!exemplarToProcess.isEmpty() || !studentsToProcess.isEmpty()) {
			for (Relationship exemplarRel : mapping.getExemplarModel().getRelationships()) {
				if (!exemplarToProcess.contains(exemplarRel)) {
					continue;
				}
				for (Relationship studentRel : mapping.getStudentModel().getRelationships()) {
					if (studentsToProcess.contains(studentRel)) {
						continue;
					}
					if (RelationshipUtils.relationshipsAreEquallyMapped(exemplarRel, studentRel, false, false)) {
						if (exemplarRel instanceof Association) {
							checkAttributes(mapping, (Association) exemplarRel, (Association) studentRel);
						}
						TransformationUtils.addChangeCardinality(mapping, studentRel, exemplarRel);

						exemplarToProcess.remove(exemplarRel);
						studentsToProcess.remove(studentRel);
						break;
					}
				}

				TransformationUtils.addCreateRelationship(mapping, RelationshipUtils.convertRelationship(exemplarRel));
			}
		}

		for (Relationship studentRelationship : studentsToProcess) {
			TransformationUtils.addRemoveRelationship(mapping, studentRelationship);
		}
	}

	private void checkMappingPairs(Mapping mapping) {
		for (EntitySet entitySet : mapping.getExemplarModel().getEntitySets()) {
			EntitySet image = entitySet.getMappedTo();
			if (image != null) {
				if (!StringUtils.areEqual(entitySet.getName(), image.getName())) {
					TransformationUtils.addRenameEntitySet(mapping, image, entitySet);
				}
				checkAttributes(mapping, entitySet, image);
			}
		}
	}

	private void checkAttributes(Mapping mapping, Attributed exemplarAttributed, Attributed studentAttributed) {
		// TODO: tuto by bola vhodna nejaka netrivialnejsia logika, aby si to nerobil
		// len tak primitivne
		// "RENAME_ENTITY_SET";
		// "RENAME_ATTRIBUTE";
		for (Attribute attribute : exemplarAttributed.getAttributes()) {
			if (!studentAttributed.getAttributes().contains(attribute)) {
				TransformationUtils.addCreateAttribute(mapping, studentAttributed, attribute);
			}
		}

		for (Attribute attribute : studentAttributed.getAttributes()) {
			if (!exemplarAttributed.getAttributes().contains(attribute)) {
				TransformationUtils.addRemoveAttribute(mapping, studentAttributed, attribute);
			}
		}
	}
}
