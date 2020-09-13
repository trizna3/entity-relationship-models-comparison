package mappingSearch.mappingEvaluator;

import java.util.List;

import common.RelationshipUtils;
import common.StringUtils;
import common.TransformationUtils;
import common.Utils;
import comparing.Mapping;
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
			if (entitySet.getMappedTo() == null) {
				TransformationUtils.addCreateEntitySet(mapping, entitySet);
			}
		}
		for (EntitySet entitySet : mapping.getStudentModel().getEntitySets()) {
			if (entitySet.getMappedTo() == null) {
				TransformationUtils.addRemoveEntitySet(mapping, entitySet);
			}
		}
	}

	private void checkRelationships(Mapping mapping) {
		List<Relationship> exemplarToProcess = mapping.getExemplarModel().getRelationships();
		List<Relationship> studentsToProcess = mapping.getStudentModel().getRelationships();

		for (Relationship exemplarRel : mapping.getExemplarModel().getRelationships()) {
			for (Relationship studentRel : mapping.getStudentModel().getRelationships()) {
				if (RelationshipUtils.relationshipsAreEquallyMapped(exemplarRel, studentRel, true)) {
					exemplarToProcess.remove(exemplarRel);
					studentsToProcess.remove(studentRel);
					break;
				}
			}
		}

		if (!exemplarToProcess.isEmpty() || !studentsToProcess.isEmpty()) {
			for (Relationship exemplarRel : mapping.getExemplarModel().getRelationships()) {
				if (!exemplarToProcess.contains(exemplarRel)) {
					continue;
				}
				for (Relationship studentRel : mapping.getStudentModel().getRelationships()) {
					if (studentsToProcess.contains(studentRel)) {
						continue;
					}
					if (RelationshipUtils.relationshipsAreEquallyMapped(exemplarRel, studentRel, false)) {
						exemplarToProcess.remove(exemplarRel);
						studentsToProcess.remove(studentRel);

						TransformationUtils.addChangeCardinality(mapping, studentRel, exemplarRel);
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

	private void checkAttributes(Mapping mapping, EntitySet exemplarEntitySet, EntitySet studentEntitySet) {
		// TODO: tuto by bola vhodna nejaka netrivialnejsia logika, aby si to nerobil
		// len tak primitivne

//		public static final String CREATE_ATTRIBUTE = "CREATE_ATTRIBUTE";
//		public static final String REMOVE_ATTRIBUTE = "REMOVE_ATTRIBUTE";
//		public static final String RENAME_ENTITY_SET = "RENAME_ENTITY_SET";
//		public static final String RENAME_ATTRIBUTE = "RENAME_ATTRIBUTE";
	}
}
