package sk.trizna.erm_comparison.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import sk.trizna.erm_comparison.comparing.mapping.Mapping;
import sk.trizna.erm_comparison.entity_relationship_model.Attribute;
import sk.trizna.erm_comparison.entity_relationship_model.ERModel;
import sk.trizna.erm_comparison.entity_relationship_model.EntitySet;
import sk.trizna.erm_comparison.entity_relationship_model.Relationship;
import sk.trizna.erm_comparison.entity_relationship_model.RelationshipSide;

public class ERModelUtils extends Utils {

	/**
	 * Transforms each entitySet, incident to given relationship, to it's mapping image.
	 * Returns list of all relationships from given otherModel which are incident to exactly all entitySet images (and no other).
	 * 
	 * @param relationship
	 * @param otherModel
	 * @return
	 */
	public static List<Relationship> getRelationshipImage(Relationship relationship, ERModel otherModel) {
		Utils.validateNotNull(relationship);
		Utils.validateNotNull(otherModel);
		
		List<EntitySet> entitySetImages = relationship.getSides()
				.stream()
				.map(side -> side.getEntitySet().getMappedTo())
				.collect(Collectors.toList());
		
		return ERModelUtils.getRelationshipsByEntitySets(otherModel,entitySetImages);
	}
	
	/**
	 * Transforms each entitySet, to it's mapping image.
	 * Returns list of all relationships from given otherModel which are incident to exactly all entitySet images (and no other).
	 * 
	 * @param entitySets
	 * @param otherModel
	 * @return
	 */
	public static List<Relationship> getRelationshipImage(List<EntitySet> entitySets, ERModel otherModel) {
		Utils.validateNotNull(entitySets);
		Utils.validateNotNull(otherModel);
		
		List<EntitySet> entitySetImages = entitySets
				.stream()
				.map(es -> es.getMappedTo())
				.collect(Collectors.toList());
		
		return ERModelUtils.getRelationshipsByEntitySets(otherModel,entitySetImages);
	}
	
	/**
	 * Returns all relationships, which contain all of the given entitySets.
	 */
	public static List<Relationship> getRelationshipsByEntitySets(ERModel model, List<EntitySet> entitySets) {
		validateNotNull(model);
		validateNotNull(entitySets);
		entitySets.forEach(entitySet -> {
			validateContains(model, entitySet);
		});

		List<Relationship> result = new ArrayList<>(model.getRelationships().size());
		for (Relationship relationship : model.getRelationships()) {
			if (RelationshipUtils.isEqual(relationship, entitySets)) {
				result.add(relationship);
			}
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
				if (es1.equals(es2)) {
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
				if (rel1.equals(rel2)) {
					relationshipsToProcess.remove(rel1);
					relationshipsToProcess.remove(rel2);
					continue nextRel1;
				}
			}
		}

		return relationshipsToProcess.isEmpty();
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
		if (!side1.getEntitySet().equals(side2.getEntitySet())) {
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
			if (name.equals(entitySet.getNameText())) {
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

		EntitySet copy = new EntitySet(entitySet.getNameText());
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
	public static void unfinalizeModel(ERModel model,List<EntitySet> targetEntitySets) {
		validateNotNull(model);

		for (EntitySet entitySet : model.getEntitySets()) {
			if (CollectionUtils.trueContains(targetEntitySets,entitySet)) {
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
