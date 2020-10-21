package common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import comparing.Mapping;
import entityRelationshipModel.Association;
import entityRelationshipModel.Attribute;
import entityRelationshipModel.ERModel;
import entityRelationshipModel.EntitySet;
import entityRelationshipModel.Relationship;
import entityRelationshipModel.RelationshipSide;

public class ERModelUtils extends Utils {

	/**
	 * Returns all relationships, which contain all of the given entitySets.
	 */
	public static List<Relationship> getRelationshipsByEntitySets(ERModel model, List<EntitySet> entitySets) {
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
	public static boolean modelsAreEquallyMapped(Mapping mapping) {
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

	/**
	 * Determines if models are equal by their content.
	 * 
	 * @param model1
	 * @param model2
	 * @return
	 */
	public static boolean modelsAreEqual(ERModel model1, ERModel model2) {
		validateNotNull(model1);
		validateNotNull(model2);

		Set<EntitySet> entitySetsToProcess = new HashSet<>();
		entitySetsToProcess.addAll(model1.getEntitySets());
		entitySetsToProcess.addAll(model2.getEntitySets());

		nextEs1: for (EntitySet es1 : model1.getEntitySets()) {
			for (EntitySet es2 : model2.getEntitySets()) {
				if (!entitySetsToProcess.contains(es2)) {
					continue;
				}
				if (entitySetsAreEqual(es1, es2)) {
					entitySetsToProcess.remove(es1);
					entitySetsToProcess.remove(es2);
					continue nextEs1;
				}
			}
		}

		if (!entitySetsToProcess.isEmpty()) {
			return false;
		}

		Set<Relationship> relationshipsToProcess = new HashSet<>();
		relationshipsToProcess.addAll(model1.getRelationships());
		relationshipsToProcess.addAll(model2.getRelationships());

		nextRel1: for (Relationship rel1 : model1.getRelationships()) {
			for (Relationship rel2 : model2.getRelationships()) {
				if (!relationshipsToProcess.contains(rel2)) {
					continue;
				}
				if (relationshipsAreEqual(rel1, rel2)) {
					relationshipsToProcess.remove(rel1);
					relationshipsToProcess.remove(rel2);
					continue nextRel1;
				}
			}
		}

		return relationshipsToProcess.isEmpty();
	}

	/**
	 * Determines if entity sets are equal by their content.
	 * 
	 * @param entitySet1
	 * @param entitySet2
	 * @return
	 */
	public static boolean entitySetsAreEqual(EntitySet entitySet1, EntitySet entitySet2) {
		validateNotNull(entitySet1);
		validateNotNull(entitySet2);

		if (!StringUtils.areEqual(entitySet1.getName(), entitySet2.getName())) {
			return false;
		}
		if (entitySet1.getAttributes() == null && entitySet2.getAttributes() == null) {
			return true;
		}
		if (entitySet1.getAttributes() == null || entitySet2.getAttributes() == null) {
			return false;
		}
		return entitySet1.getAttributes().containsAll(entitySet2.getAttributes()) && entitySet2.getAttributes().containsAll(entitySet1.getAttributes());
	}

	/**
	 * Determines if relationships are equal by their content.
	 * 
	 * @param relationship1
	 * @param relationship2
	 * @return
	 */
	public static boolean relationshipsAreEqual(Relationship relationship1, Relationship relationship2) {
		validateNotNull(relationship1);
		validateNotNull(relationship2);

		if (!relationship1.getClass().equals(relationship2.getClass())) {
			return false;
		}
		if (relationship1.getSides().size() != relationship2.getSides().size()) {
			return false;
		}

		Set<RelationshipSide> sidesToProcess = new HashSet<>();
		sidesToProcess.addAll(relationship1.getSides());
		sidesToProcess.addAll(relationship2.getSides());

		nextSide1: for (RelationshipSide side1 : relationship1.getSides()) {
			for (RelationshipSide side2 : relationship2.getSides()) {
				if (!sidesToProcess.contains(side2)) {
					continue;
				}
				if (relationshipSidesAreEqual(side1, side2)) {
					sidesToProcess.remove(side1);
					sidesToProcess.remove(side2);
					continue nextSide1;
				}
			}
		}
		if (!sidesToProcess.isEmpty()) {
			return false;
		}

		if (relationship1 instanceof Association) {
			Association assoc1 = (Association) relationship1;
			Association assoc2 = (Association) relationship2;

			if (assoc1.getAttributes() == null && assoc2.getAttributes() == null) {
				return true;
			}
			if (assoc1.getAttributes() == null || assoc2.getAttributes() == null) {
				return false;
			}
			return assoc1.getAttributes().containsAll(assoc2.getAttributes()) && assoc2.getAttributes().containsAll(assoc1.getAttributes());
		} else {
			return true;
		}
	}

	/**
	 * Determines if relationship sides are equal by their content.
	 * 
	 * @param side1
	 * @param side2
	 * @return
	 */
	public static boolean relationshipSidesAreEqual(RelationshipSide side1, RelationshipSide side2) {
		validateNotNull(side1);
		validateNotNull(side2);

		if (!side1.getClass().equals(side2.getClass())) {
			return false;
		}
		if (!entitySetsAreEqual(side1.getEntitySet(), side2.getEntitySet())) {
			return false;
		}
		if (side1.getRole() != side2.getRole()) {
			return false;
		}

		return true;
	}

	/**
	 * Returns entitySet from given model with given name.
	 * 
	 * @param model
	 * @param name
	 * @return
	 */
	public static EntitySet getEntitySetByName(ERModel model, String name) {
		validateNotNull(model);
		validateNotNull(name);

		for (EntitySet entitySet : model.getEntitySets()) {
			if (name.equals(entitySet.getName())) {
				return entitySet;
			}
		}
		return null;
	}

	/**
	 * Creates and returns model deep clone. All embedded objects are cloned as
	 * well.
	 * 
	 * @param model
	 * @return
	 */
	public static ERModel getClone(ERModel model) {
		Map<EntitySet, EntitySet> entitySetMap = new HashMap<>();
		ERModel modelClone = new ERModel();

		for (EntitySet entitySet : model.getEntitySets()) {
			entitySetMap.put(entitySet, new EntitySet(entitySet));
		}
		modelClone.addAllEntitySets(entitySetMap.values());

		for (Relationship relationship : model.getRelationships()) {
			modelClone.addRelationship(RelationshipUtils.getClone(relationship, entitySetMap));
		}

		return modelClone;
	}

	/**
	 * Returns a copy of given entitySet, with relations cut off
	 * 
	 * @param entitySet
	 * @return
	 */
	public static EntitySet copyEntitySetDetached(EntitySet entitySet) {
		validateNotNull(entitySet);

		EntitySet copy = new EntitySet(entitySet.getName());
		copy.setAttributes(new ArrayList<Attribute>(entitySet.getAttributes()));

		return copy;
	}

	/**
	 * Finalizes mapping before penalty computation. Maps all unmapped entitySets to
	 * an emptyEntitySet.
	 * 
	 * @param model
	 */
	public static void finalizeModel(ERModel model, List<EntitySet> targetEntitySets) {
		validateNotNull(model);

		for (EntitySet entitySet : model.getEntitySets()) {
			if (entitySet.getMappedTo() == null) {
				entitySet.setMappedTo(MappingUtils.EMPTY_ENTITY_SET);
				targetEntitySets.add(entitySet);
			}
		}
	}

	/**
	 * Unfinalizes mapping after penalty computation. All entitySet, which are
	 * mapped to an emptyEntitySets are unmapped.
	 * 
	 * @param model
	 */
	public static void unfinalizeModel(ERModel model) {
		validateNotNull(model);

		for (EntitySet entitySet : model.getEntitySets()) {
			if (MappingUtils.EMPTY_ENTITY_SET.equals(entitySet.getMappedTo())) {
				entitySet.setMappedTo(null);
			}
		}
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
