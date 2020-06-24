package common;

import java.util.ArrayList;
import java.util.List;

import comparing.Mapping;
import entityRelationshipModel.ERModel;
import entityRelationshipModel.EntitySet;
import entityRelationshipModel.Relationship;

public class ERModelUtils extends Utils {

	/**
	 * Returns all relationships, which contain all of the given entitySets.
	 */
	public static List<Relationship> getRelationshipsByEntitySets(ERModel model, EntitySet[] entitySets) {
		validateNotNull(model);
		validateNotNull(entitySets);

		for (EntitySet entitySet : entitySets) {
			validateContains(model, entitySet);
		}
		List<Relationship> result = new ArrayList<>();
		nextRel: for (Relationship relationship : model.getRelationships()) {
			for (EntitySet entitySet : entitySets) {
				if (!RelationshipUtils.contains(relationship, entitySet)) {
					continue nextRel;
				}
			}
			result.add(relationship);
		}
		return result;
	}

	/**
	 * Determines if exemplar and student model are equal in given mapping.
	 */
	public static boolean modelsAreEqual(Mapping mapping) {
		validateNotNull(mapping);

		ERModel model1 = mapping.getExemplarModel();
		ERModel model2 = mapping.getStudentModel();

		if (model1.getEntitySetsCount() != model2.getEntitySetsCount()) {
			return false;
		}
		if (model1.getRelationshipsCount() != model2.getRelationshipsCount()) {
			return false;
		}
		if (model1.getNotMappedEntitySets().size() > 0 || model2.getNotMappedEntitySets().size() > 0) {
			return false;
		}
		if (!modelsAreEqualByRelationships(model1, model2)) {
			return false;
		}

		return true;
	}

	private static boolean modelsAreEqualByRelationships(ERModel model1, ERModel model2) {

		for (Relationship relationship : model1.getRelationships()) {
			if (relationship.isBinary()) {
				EntitySet first = relationship.getFirstSide().getEntitySet();
				EntitySet second = relationship.getSecondSide().getEntitySet();
				EntitySet firstImage = first.getMappedTo();
				EntitySet secondImage = second.getMappedTo();

				if (RelationshipUtils.relationshipsAreEquallyMapped(first.getRelationshipsByNeighbour(second), firstImage.getRelationshipsByNeighbour(secondImage), true)) {
					return false;
				}
			}
		}
		return true;
	}
}
