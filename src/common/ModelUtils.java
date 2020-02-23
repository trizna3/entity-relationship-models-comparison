package common;

import comparing.Mapping;
import entityRelationshipModel.Relationship;
import entityRelationshipModel.RelationshipSide;

import java.util.HashSet;
import java.util.Set;

/**
 * @author - Adam Trizna
 */

public class ModelUtils {

    /**
     * @param side1
     * @param side2
     * @param mapping
     * @param checkEntitySetsOnly
     * @return wether given sides are equal (either only by entity sets or completely) through given mapping
     */
    public static boolean sidesAreEqual(RelationshipSide side1, RelationshipSide side2, Mapping mapping, boolean checkEntitySetsOnly) {
        if (side1 == null || side2 == null || mapping == null) {
            throw new IllegalArgumentException("Illegal argument: both given sides and mapping mustn't be null!");
        }
        if (mapping.getImage(side1.getEntitySet()) == null || mapping.getImage(side2.getEntitySet()) == null){
            return false;
        }
        if (side1.getEntitySet().equals(mapping.getImage(side2.getEntitySet())) &&
            side2.getEntitySet().equals(mapping.getImage(side1.getEntitySet()))) {
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
     * @return wether given relationships are equal (either only by entity sets or by roles/cardinalities as well), when entity sets displayes through given mapping
     */
    public static boolean relationshipsAreEqual(Relationship relationship1, Relationship relationship2, Mapping mapping, boolean checkByEntitySetsOnly) {
        if (relationship1 == null || relationship2 == null || mapping == null) {
            throw new IllegalArgumentException("Illegal argument: both relationships and mapping mustn't be null!");
        }
        if (!relationship1.getClass().equals(relationship2.getClass())) {
            return false;
        }
        if (relationship1.getSides().size() != relationship2.getSides().size()) {
            return false;
        }

        Set<RelationshipSide> usedSides = new HashSet<>();
        rel1: for (RelationshipSide side1 : relationship1.getSides()) {
            rel2: for (RelationshipSide side2 : relationship2.getSides()) {
                if (!usedSides.contains(side2) && sidesAreEqual(side1,side2,mapping,checkByEntitySetsOnly)){
                    usedSides.add(side2);
                    continue rel1;
                }
            }
            return false;
        }
        return true;
    }
}
