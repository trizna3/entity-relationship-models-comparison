package evaluation;

/**
 * @author - Adam Trizna
 */

import common.ModelUtils;
import comparing.Mapping;
import entityRelationshipModel.*;
import transformations.TransformationFactory;
import transformations.types.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Agent for checking, whether a series of transformations represent an equal or non-equal model structure modification.
 * The equality check means, whether the transformations made solely structural difference, but kept the same model's ability to store data.
 */
public class TransformationEqualityChecker {

    public static List<Transformation> getNonEqualTransformations(EntityRelationshipModel modelBefore, EntityRelationshipModel modelAfter, List<Transformation> transformationsMade, Map<EntitySet,EntitySet> mapping) {
        List<Transformation> nonEqualTransformations = new ArrayList<>(transformationsMade);

        /*
        chces (pre oba modely, najprv komplet jeden, potom komplet druhy):
        - hladat rozdiely (chybajuci ES / chybajuci vztah)
        - overit, ci nejde o nejaky ekvivalentny pripad (pre kazdy specificky scenar ich je viac, bude asi treba zvlast implementovat kazdy)
        - najst transformacie, ktore su za to zodpovedne (zavisle na tom, ci som v modeli PRED alebo modeli ZA)
         */

        checkRelationships(modelAfter.getRelationships(), modelBefore, mapping, true);
        System.out.println("-");
        checkRelationships(modelBefore.getRelationships(), modelAfter, mapping, false);

        return nonEqualTransformations;
    }

    private static List<Transformation> checkRelationships(List<Relationship> relationships, EntityRelationshipModel otherModel, Map<EntitySet,EntitySet> mapping, boolean isTransformed) {
        List<Relationship> usedRelationships = new ArrayList<>();

        nextRel: for (Relationship relationship : relationships) {
            EntitySet[] oppositeEntitySets = new EntitySet[relationship.getSides().size()];
            int i = 0;
            boolean allEntitySetsAreMapped = true;  // meaning all entity sets have non-null mapping image
            for (RelationshipSide side : relationship.getSides()) {
                oppositeEntitySets[i] = mapping.get(side.getEntitySet());
                if (oppositeEntitySets[i] == null) {
                    allEntitySetsAreMapped = false;
                }
                i ++;
            }

            if (allEntitySetsAreMapped) {
                List<Relationship> oppositeRelationships = otherModel.getRelationshipsByEntitySets(oppositeEntitySets);
                for (Relationship oppositeRelationship : oppositeRelationships) {
                    if (ModelUtils.relationshipsAreEqual(relationship, oppositeRelationship, new Mapping(mapping), false) && !usedRelationships.contains(oppositeRelationship)) {
                        usedRelationships.add(oppositeRelationship);
                        continue nextRel;
                    }
                }
            }
            System.out.println("relationship has no match: " + relationship);
            // relationship has no match

            // magic
        }

        return null;
    }
}