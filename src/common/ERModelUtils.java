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
	public static boolean sidesAreEqual(RelationshipSide side1, RelationshipSide side2, Mapping mapping,
			boolean checkEntitySetsOnly) {
		validateInput(side1);
		validateInput(side2);
		validateInput(mapping);

		if (mapping.getImage(side1.getEntitySet()) == null || mapping.getImage(side2.getEntitySet()) == null) {
			return false;
		}
		if (side1.getEntitySet().equals(mapping.getImage(side2.getEntitySet()))
				&& side2.getEntitySet().equals(mapping.getImage(side1.getEntitySet()))) {
			if (checkEntitySetsOnly) {
				return true;
			}
			return side1.getRole().equals(side2.getRole());
		}
		return false;
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
	public static boolean relationshipsAreEqual(Relationship relationship1, Relationship relationship2, Mapping mapping,
			boolean checkByEntitySetsOnly) {
		validateInput(relationship1);
		validateInput(relationship2);
		validateInput(mapping);

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

	public static List<Relationship> getRelationshipsByEntitySets(ERModel model,
			EntitySet[] entitySets) {
		validateInput(model);
		validateInput(entitySets);

		for (EntitySet entitySet : entitySets) {
			if (!model.getEntitySets().contains(entitySet)) {
				throw new IllegalArgumentException("model doesn't contain given entity set!");
			}
		}
		List<Relationship> incidentRelationships = new ArrayList<>();
		nextRel: for (Relationship relationship : model.getRelationships()) {
			for (EntitySet entitySet : entitySets) {
				if (!RelationshipUtils.contains(relationship, entitySet)) {
					continue nextRel;
				}
			}
			incidentRelationships.add(relationship);
		}
		return incidentRelationships;
	}
}
