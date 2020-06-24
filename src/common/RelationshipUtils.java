package common;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import entityRelationshipModel.Association;
import entityRelationshipModel.AssociationSide;
import entityRelationshipModel.EntitySet;
import entityRelationshipModel.Generalization;
import entityRelationshipModel.Relationship;
import entityRelationshipModel.RelationshipSide;

public class RelationshipUtils extends Utils {

	// utils
	public static String getRole(Relationship relationship, EntitySet entitySet) {
		validateNotNull(relationship);
		validateNotNull(entitySet);

		for (RelationshipSide side : relationship.getSides()) {
			if (entitySet.equals(side.getEntitySet())) {
				return side.getRole();
			}
		}

		throw new IllegalArgumentException("This Association doesn't contain given entity set!");
	}

	public static boolean contains(Relationship relationship, EntitySet entitySet) {
		validateNotNull(relationship);
		validateNotNull(entitySet);

		if (relationship instanceof Association) {
			return contains((Association) relationship, entitySet);
		} else if (relationship instanceof Generalization) {
			return contains((Generalization) relationship, entitySet);
		} else {
			throw new IllegalArgumentException("Invalid relationship type!");
		}
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
				if (!usedRelationships.contains(relationship2) && relationshipsAreEquallyMapped(relationship1, relationship2, checkRole)) {
					usedRelationships.add(relationship2);
					continue rel1;
				}
			}
			return false;
		}
		return true;
	}

	public static boolean relationshipsAreEqual(Relationship relationship1, Relationship relationship2, boolean checkRole) {
		validateNotNull(relationship1);
		validateNotNull(relationship2);

		if (relationship1.isBinary() && relationship2.isBinary()) {
			return binaryRelationshipsAreEqual(relationship1, relationship2, checkRole);
		} else {
			return multiRelationshipsAreEqual(relationship1, relationship2, checkRole);
		}
	}

	public static boolean relationshipsAreEquallyMapped(Relationship relationship1, Relationship relationship2, boolean checkRole) {

		validateNotNull(relationship1);
		validateNotNull(relationship2);

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

	// helpers
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

	private static boolean binaryRelationshipsAreEqual(Relationship relationship1, Relationship relationship2, boolean checkRole) {
		RelationshipSide first1 = relationship1.getFirstSide();
		RelationshipSide first2 = relationship2.getFirstSide();
		RelationshipSide second1 = relationship1.getSecondSide();
		RelationshipSide second2 = relationship2.getSecondSide();

		return (sidesAreEqual(first1, first2, checkRole) && sidesAreEqual(second1, second2, checkRole)) || (sidesAreEqual(first1, second2, checkRole) && sidesAreEqual(second1, first2, checkRole));
	}

	private static boolean multiRelationshipsAreEqual(Relationship relationship1, Relationship relationship2, boolean checkRole) {
		throw new UnsupportedOperationException();
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
