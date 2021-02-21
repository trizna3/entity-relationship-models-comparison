package sk.trizna.erm_comparison.common.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import sk.trizna.erm_comparison.common.enums.EnumConstants;
import sk.trizna.erm_comparison.common.key_config.AppConfigManager;
import sk.trizna.erm_comparison.comparing.RelationshipComparator;
import sk.trizna.erm_comparison.comparing.mapping.Mapping;
import sk.trizna.erm_comparison.entity_relationship_model.Association;
import sk.trizna.erm_comparison.entity_relationship_model.Attribute;
import sk.trizna.erm_comparison.entity_relationship_model.Attributed;
import sk.trizna.erm_comparison.entity_relationship_model.ERModel;
import sk.trizna.erm_comparison.entity_relationship_model.ERText;
import sk.trizna.erm_comparison.entity_relationship_model.EntitySet;
import sk.trizna.erm_comparison.entity_relationship_model.Generalization;
import sk.trizna.erm_comparison.entity_relationship_model.Relationship;
import sk.trizna.erm_comparison.entity_relationship_model.RelationshipSide;

public class ERModelUtils extends Utils {

	private static boolean bugfixMode = Boolean.valueOf(AppConfigManager.getInstance().getResource(EnumConstants.CONFIG_BUGFIX_MODE).toString());
	
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
	
	public static List<Relationship> getRelationshipsByEntitySet(ERModel model, EntitySet entitySet) {
		validateNotNull(model);
		validateNotNull(entitySet);

		return model.getRelationships().stream().filter(rel -> rel.contains(entitySet)).collect(Collectors.toList());
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
				if (ERModelUtils.areEqual(es1, es2,true)) {
					entitySetsToProcess.remove(es1);
					entitySetsToProcess.remove(es2);
					continue nextEs1;
				}
			}
		}

		if (!entitySetsToProcess.isEmpty()) {
			if (bugfixMode) {
				PrintUtils.log("Model equality failure: not-mapped entitySets: " + entitySetsToProcess.stream().map(entitySet -> entitySet.getNameText()).collect(Collectors.toList()).toString());
				
			}
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
				if (ERModelUtils.areEqual(rel1,rel2,true)) {
					relationshipsToProcess.remove(rel1);
					relationshipsToProcess.remove(rel2);
					continue nextRel1;
				}
			}
		}

		if (!relationshipsToProcess.isEmpty()) {
			if (bugfixMode) {
				PrintUtils.log("Model equality failure: not-mapped relationships: " + relationshipsToProcess.stream().map(relationship -> relationship.getNameText()).toString());
				
			}
			return false;
		} 
		return true;
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
			if (StringUtils.areEqual(name, entitySet.getNameText())) {
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
		ERModel modelClone = new ERModel();
		
		// clone all entitySets
		Map<EntitySet, EntitySet> entitySetMap = new HashMap<EntitySet, EntitySet>();
		for (EntitySet entitySet : model.getEntitySets()) {
			EntitySet clone = new EntitySet(entitySet);
			clone.setNeighbours(null);
			entitySetMap.put(entitySet, clone);
		} 
		modelClone.addAllEntitySets(entitySetMap.values());

		// clone all relationships
		Map<Relationship, Relationship> relationshipMap = new HashMap<Relationship, Relationship>();
		for (Relationship relationship : model.getRelationships()) {
			relationshipMap.put(relationship,RelationshipUtils.getClone(relationship, entitySetMap));
		}
		modelClone.addAllRelationships(relationshipMap.values());

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
			if (targetEntitySets.contains(entitySet)) {
				entitySet.setMappedTo(null);
			}
		}
	}
	
	public static boolean areEqual(ERText text1, ERText text2) {
		validateNotNull(text1);
		validateNotNull(text2);
		
		return StringUtils.areEqual(text1.getText(), text2.getText());
	}
	
	public static boolean areEqual(EntitySet entitySet1, EntitySet entitySet2) {
		return areEqual(entitySet1, entitySet2, false);
	}
	
	public static boolean areEqual(EntitySet entitySet1, EntitySet entitySet2, boolean checkNeighbours) {
		if (!StringUtils.areEqual(entitySet1.getNameText(), entitySet2.getNameText())) {
			return false;
		}
		if (!attributesAreEqual(entitySet1,entitySet2)) {
			return false;
		}
		if (checkNeighbours) {
			return areEqualByNeighbours(entitySet1, entitySet2);
		} else {
			return true;
		}
	}
	
	private static boolean areEqualByNeighbours(EntitySet entitySet1, EntitySet entitySet2) {
		neigh1 : for (EntitySet neighbour1 : entitySet1.getNeighbours().keySet()) {
			// search for equal counterpart
			for (EntitySet neighbour2 : entitySet2.getNeighbours().keySet()) {
				if (!areEqual(neighbour1, neighbour2, false)) {
					continue;
				}
				if (entitySet1.getNeighbours().get(neighbour1).size() != entitySet2.getNeighbours().get(neighbour2).size()) {
					continue;
				}
				if (!CollectionUtils.equalByComparator(entitySet1.getNeighbours().get(neighbour1), entitySet2.getNeighbours().get(neighbour2), RelationshipComparator.getInstance())) {
					continue;
				}
				continue neigh1;
			}
			return false;
		}
		
		return true;
	}
	
	public static boolean areEqual(Relationship relationship1, Relationship relationship2, boolean checkRoles) {
		if (relationship1 instanceof Association && relationship2 instanceof Association) {
			return areEqual((Association)relationship1, (Association)relationship2,checkRoles);
		}
		if (relationship1 instanceof Generalization && relationship2 instanceof Generalization) {
			return areEqual((Generalization)relationship1, (Generalization)relationship2,checkRoles);
		}
		return false;
	}
	
	public static boolean areEqual(Association association1, Association association2, boolean checkCardinalities) {
		return sidesAreEqual(association1,association2,checkCardinalities) && attributesAreEqual(association1,association2);
	}
	
	public static boolean areEqual(RelationshipSide side1, RelationshipSide side2, boolean checkCardinalities) {
		if (checkCardinalities) {
			return side1.getRole() == side2.getRole() && areEqual(side1.getEntitySet(),side2.getEntitySet());
		} else {
			return areEqual(side1.getEntitySet(),side2.getEntitySet());
		}
	} 
	
	public static boolean areEqual(Generalization generalization1, Generalization generalization2, boolean checkRoles) {
		if (!StringUtils.areEqual(generalization1.getNameText(), generalization2.getNameText())) {
			return false;
		}
		if (checkRoles) {
			return areEqual(generalization1.getSuperEntitySet(),generalization2.getSuperEntitySet()) && areEqual(generalization1.getSubEntitySet(),generalization2.getSubEntitySet());
		} else {
			return 	(areEqual(generalization1.getSuperEntitySet(),generalization2.getSuperEntitySet()) && areEqual(generalization1.getSubEntitySet(),generalization2.getSubEntitySet())) ||
					(areEqual(generalization1.getSuperEntitySet(),generalization2.getSubEntitySet()) && areEqual(generalization1.getSubEntitySet(),generalization2.getSuperEntitySet()));
		}
	}
	
	public static boolean sidesAreEqual(Association association1, Association association2, boolean checkCardinalities) {
		if (association1.getSides() == null && association2.getSides() == null) {
			return true;
		}
		if (association1.getSides() == null || association2.getSides() == null) {
			return false;
		} 
		if (association1.getSides().size() != association2.getSides().size()) {
			return false;
		}
		
		return CollectionUtils.containsAllSides(association1.getSides(), association2.getSides(),checkCardinalities);
	}
	
	public static boolean attributesAreEqual(Attributed attributed1, Attributed attribute2) {
		if (attribute2 == null) {
			return false;
		}
		if (attributed1.getAttributes() == null && attribute2.getAttributes() == null) {
			return true;
		}
		if (attributed1.getAttributes() == null || attribute2.getAttributes() == null) {
			return false;
		}
		if (attributed1.getAttributes().size() != attribute2.getAttributes().size()) {
			return false;
		}
		return CollectionUtils.containsAllTexts(attributed1.getAttributes(), attribute2.getAttributes());
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
