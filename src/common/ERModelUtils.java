package common;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import comparing.Mapping;
import entityRelationshipModel.ERModel;
import entityRelationshipModel.EntitySet;
import entityRelationshipModel.Relationship;
import entityRelationshipModel.RelationshipSide;

public class ERModelUtils extends Utils {

	/**
	 * @param side1
	 * @param side2
	 * @param mapping
	 * @param checkEntitySetsOnly
	 * @return whether given sides are equal (either only by entity sets or
	 *         completely) through given mapping
	 */
	public static boolean sidesAreEqual(RelationshipSide side1, RelationshipSide side2, Mapping mapping, boolean checkEntitySetsOnly) {
		validateNotNull(side1);
		validateNotNull(side2);
		validateNotNull(mapping);

		throw new UnsupportedOperationException();
	}

	/**
	 * @param relationship1
	 * @param relationship2
	 * @param mapping
	 * @param checkByEntitySetsOnly
	 * @return whether given relationships are equal (either only by entity sets or
	 *         by roles/cardinalities as well), when entity sets displays through
	 *         given mapping
	 */
	public static boolean relationshipsAreEqual(Relationship relationship1, Relationship relationship2, Mapping mapping, boolean checkByEntitySetsOnly) {
		validateNotNull(relationship1);
		validateNotNull(relationship2);
		validateNotNull(mapping);

		if (!relationship1.getClass().equals(relationship2.getClass())) {
			return false;
		}
		if (relationship1.getSides().length != relationship2.getSides().length) {
			return false;
		}

		Set<RelationshipSide> usedSides = new HashSet<>();
		rel1: for (RelationshipSide side1 : relationship1.getSides()) {
			for (RelationshipSide side2 : relationship2.getSides()) {
				if (!usedSides.contains(side2) && sidesAreEqual(side1, side2, mapping, checkByEntitySetsOnly)) {
					usedSides.add(side2);
					continue rel1;
				}
			}
			return false;
		}
		return true;
	}

	public static List<Relationship> getRelationshipsByEntitySets(ERModel model, EntitySet[] entitySets) {
		validateNotNull(model);
		validateNotNull(entitySets);

		for (EntitySet entitySet : entitySets) {
			validateContains(model, entitySet);
		}
		List<Relationship> result = new ArrayList<>();
		nextRel: for (Relationship relationship : model.getRelationships()) {
			if (relationship.getSides().length != entitySets.length) {
				continue;
			}
			for (EntitySet entitySet : entitySets) {
				if (!RelationshipUtils.contains(relationship, entitySet)) {
					continue nextRel;
				}
			}
			result.add(relationship);
		}
		return result;
	}

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

		// check relationships mapping

		return true;
	}

}
