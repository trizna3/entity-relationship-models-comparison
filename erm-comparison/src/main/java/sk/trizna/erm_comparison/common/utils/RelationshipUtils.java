package sk.trizna.erm_comparison.common.utils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import sk.trizna.erm_comparison.common.enums.EnumRelationshipSideRole;
import sk.trizna.erm_comparison.entity_relationship_model.Association;
import sk.trizna.erm_comparison.entity_relationship_model.AssociationSide;
import sk.trizna.erm_comparison.entity_relationship_model.EntitySet;
import sk.trizna.erm_comparison.entity_relationship_model.Generalization;
import sk.trizna.erm_comparison.entity_relationship_model.GeneralizationSide;
import sk.trizna.erm_comparison.entity_relationship_model.Relationship;
import sk.trizna.erm_comparison.entity_relationship_model.RelationshipSide;

public class RelationshipUtils extends Utils {

	/**
	 * Returns given entitySet's role (cardinality/generalizationRole) in given
	 * relationship
	 * 
	 * @param relationship
	 * @param entitySet
	 * @return
	 */
	public static EnumRelationshipSideRole getRole(Relationship relationship, EntitySet entitySet) {
		validateNotNull(relationship);
		validateNotNull(entitySet);
		
		for (RelationshipSide side : relationship.getSides()) {
			if (entitySet.equals(side.getEntitySet())) {
				return side.getRole();
			}
		}

		return null;
	}

	/**
	 * Determines whether given entitySet is contained in given relationship
	 * 
	 * @param relationship
	 * @param entitySet
	 * @return
	 */
	public static boolean contains(Relationship relationship, EntitySet entitySet) {
		validateNotNull(relationship);
		validateNotNull(entitySet);

		if (relationship instanceof Association) {
			return contains((Association) relationship, entitySet);
		}
		if (relationship instanceof Generalization) {
			return contains((Generalization) relationship, entitySet);
		}

		return false;
	}

	/**
	 * Determine whether given relationship lists are equal in mapping.
	 * Relationships are known to be between entity sets and it's images.
	 */
	public static boolean relationshipsAreEquallyMapped(List<Relationship> relationships1, List<Relationship> relationships2, boolean checkRole) {
		if (relationships1 == null && relationships2 == null) {
			return true;
		}

		validateNotNull(relationships1);
		validateNotNull(relationships2);

		Set<Relationship> usedRelationships = new HashSet<>();
		rel1: for (Relationship relationship1 : relationships1) {
			for (Relationship relationship2 : relationships2) {
				if (!usedRelationships.contains(relationship2) && relationshipsAreEquallyMapped(relationship1, relationship2, checkRole, false)) {
					usedRelationships.add(relationship2);
					continue rel1;
				}
			}
			return false;
		}
		return true;
	}

	/**
	 * Determines if given relationships are equal in given mapping.
	 * 
	 * @param relationship1
	 * @param relationship2
	 * @param checkRole
	 * @return
	 */
	public static boolean relationshipsAreEquallyMapped(Relationship relationship1, Relationship relationship2, boolean checkRole, boolean checkAttributes) {

		validateNotNull(relationship1);
		validateNotNull(relationship2);

		if (!isSameClass(relationship1, relationship2)) {
			return false;
		}
		if (relationship1.getSides().size() != relationship2.getSides().size()) {
			return false;
		}
		if (relationship1 instanceof Association && checkAttributes && !CollectionUtils.areEqual(((Association) relationship1).getAttributes(), ((Association) relationship2).getAttributes())) {
			return false;
		}

		Set<RelationshipSide> usedSides = new HashSet<>();
		nextSide1: for (RelationshipSide side1 : relationship1.getSides()) {
			for (RelationshipSide side2 : relationship2.getSides()) {
				if (!usedSides.contains(side2) && sidesAreEquallyMapped(side1, side2, checkRole)) {
					usedSides.add(side2);
					continue nextSide1;
				}
			}
			return false;
		}
		return true;
	}

	/**
	 * Convert relationship to other model. Set each relationship side entitySet to
	 * it's image.
	 * 
	 * @param relationship
	 */
	public static Relationship convertRelationship(Relationship relationship) {
		validateNotNull(relationship);
		Relationship result;
		if (relationship instanceof Association) {
			result = new Association((Association) relationship);
		} else {
			result = new Generalization((Generalization) relationship);
		}

		for (RelationshipSide relationshipSide : result.getSides()) {
			relationshipSide.setEntitySet(relationshipSide.getEntitySet().getMappedTo());
		}

		return relationship;
	}

	/**
	 * Returns the other side of given binary relationship (given 'first'
	 * entitySet).
	 * 
	 * @param relationship
	 * @param entitySet
	 * @return
	 */
	public static RelationshipSide getOtherSide(Relationship relationship, EntitySet entitySet) {
		validateNotNull(entitySet);
		validateNotNull(relationship);
		validateBinary(relationship);

		return entitySet.equals(relationship.getFirstSide().getEntitySet()) ? relationship.getSecondSide() : relationship.getFirstSide();
	}

	/**
	 * Returns the other entitySet of given binary relationship (given 'first'
	 * entitySet).
	 * 
	 * @param relationship
	 * @param entitySet
	 * @return
	 */
	public static EntitySet getOtherEntitySet(Relationship relationship, EntitySet entitySet) {
		validateNotNull(entitySet);
		validateNotNull(relationship);
		validateBinary(relationship);

		return getOtherSide(relationship, entitySet).getEntitySet();
	}

	/**
	 * Rebinds relationship from someES--oldES to someES--newES
	 * 
	 * @param relationship
	 * @param oldEntitySet
	 * @param newEntitySet
	 */
	public static void rebindEntitySets(Relationship relationship, EntitySet oldEntitySet, EntitySet newEntitySet) {
		validateNotNull(relationship);
		validateNotNull(oldEntitySet);
		validateNotNull(newEntitySet);

		oldEntitySet.removeNeighbours(relationship);

		for (RelationshipSide side : relationship.getSides()) {
			if (oldEntitySet.equals(side.getEntitySet())) {
				side.setEntitySet(newEntitySet);
				break;
			}
		}

		newEntitySet.addNeighbours(relationship);

		for (RelationshipSide side : relationship.getSides()) {
			if (!newEntitySet.equals(side.getEntitySet()) && !oldEntitySet.equals(side.getEntitySet())) {
				side.getEntitySet().removeNeighbour(oldEntitySet, relationship);
				side.getEntitySet().addNeighbour(newEntitySet, relationship);
			}
		}
	}

	/**
	 * Get relationship clone
	 * 
	 * @param relationship
	 * @param entitySetMap - map of <originalEntitySet,clonedEntitySet>
	 * @return
	 */
	public static Relationship getClone(Relationship relationship, Map<EntitySet, EntitySet> entitySetMap) {
		validateNotNull(relationship);
		validateNotNull(entitySetMap);

		if (relationship instanceof Association) {
			return getAssociationClone((Association) relationship, entitySetMap);
		} else {
			return getGeneralizationClone((Generalization) relationship, entitySetMap);
		}
	}

	/**
	 * Determines if relationship is mapped, eg. if all it's incident entitySets are
	 * mapped.
	 * 
	 * @param relationship
	 * @return
	 */
	public static boolean isMapped(Relationship relationship) {
		validateNotNull(relationship);

		for (RelationshipSide side : relationship.getSides()) {
			if (side.getEntitySet().getMappedTo() == null) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Determines if given relationships are of the same grade (have same number of incident entity sets) 
	 * @param relationship1
	 * @param relationship2
	 * @return
	 */
	public static boolean sameGrade(Relationship relationship1, Relationship relationship2) {
		validateNotNull(relationship1);
		validateNotNull(relationship2);
		
		return relationship1.getSides().size() == relationship2.getSides().size();
	}
	
	/**
	 * Returns true iff given relationship is incident to exactly all given entitySets and no other.
	 * 
	 * @param relationship
	 * @param entitySets
	 * @return
	 */
	public static boolean isEqual(Relationship relationship, List<EntitySet> entitySets) {
		Utils.validateNotNull(relationship);
		Utils.validateNotNull(entitySets);
		
		if (relationship.getSides().size() != entitySets.size()) {
			return false;
		}
		
		List<EntitySet> relEntitySets = relationship.getEntitySets();
		return entitySets.containsAll(relEntitySets) && relEntitySets.containsAll(entitySets);
	}
	
	public static AssociationSide getSide(Association association, EntitySet entitySet) {
		validateNotNull(entitySet);
		validateNotNull(association);
		validateContains(association, entitySet);
		
		for (AssociationSide side : association.getSides()) {
			if (entitySet.equals(side.getEntitySet())) {
				return side;
			}
		}
		return null;
	}

	private static Association getAssociationClone(Association association, Map<EntitySet, EntitySet> entitySetMap) {
		Association associationClone = new Association();

		associationClone.setNameText(association.getNameText());
		associationClone.setAttributes(new ArrayList<>(association.getAttributes()));
		associationClone.setSides(association.getSides().stream().map(side -> new AssociationSide(entitySetMap.get(side.getEntitySet()), side.getRole())).collect(Collectors.toList()));
		return associationClone;
	}

	private static Generalization getGeneralizationClone(Generalization generalization, Map<EntitySet, EntitySet> entitySetMap) {
		Generalization generalizationClone = new Generalization();

		generalizationClone.setNameText(generalization.getNameText());
		generalizationClone.getSides().add(0, new GeneralizationSide(entitySetMap.get(generalization.getFirstSide().getEntitySet()), generalization.getFirstSide().getRole()));
		generalizationClone.getSides().add(1, new GeneralizationSide(entitySetMap.get(generalization.getSecondSide().getEntitySet()), generalization.getSecondSide().getRole()));

		return generalizationClone;
	}

	private static boolean contains(Association association, EntitySet entitySet) {
		for (AssociationSide side : association.getSides()) {
			if (entitySet.equals(side.getEntitySet())) {
				return true;
			}
		}
		return false;
	}

	private static boolean contains(Generalization generalization, EntitySet entitySet) {
		return entitySet.equals(generalization.getSuperEntitySet()) || entitySet.equals(generalization.getSubEntitySet());
	}

	private static boolean sidesAreEqual(RelationshipSide side1, RelationshipSide side2, boolean checkRole) {
		if (checkRole) {
			return side1.getEntitySet().equals(side2.getEntitySet()) && side1.getRole().equals(side2.getRole());
		} else {
			return side1.getEntitySet().equals(side2.getEntitySet());
		}
	}

	private static boolean sidesAreEquallyMapped(RelationshipSide side1, RelationshipSide side2, boolean checkRole) {
		validateMapped(side1.getEntitySet());
		validateMapped(side2.getEntitySet());
		if (checkRole) {
			return side1.getEntitySet().getMappedTo().equals(side2.getEntitySet()) && side1.getRole().equals(side2.getRole());
		} else {
			return side1.getEntitySet().getMappedTo().equals(side2.getEntitySet());
		}
	}
}
