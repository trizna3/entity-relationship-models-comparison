package evaluation;

/**
 * @author - Adam Trizna
 */

import comparing.Mapping;
import entityRelationshipModel.EntityRelationshipModel;
import transformations.types.Transformation;

import java.util.ArrayList;
import java.util.List;

/**
 * Agent for checking, whether a series of transformations represent an equal or non-equal model structure modification.
 * The equality check means, whether the transformations made solely structural difference, but kept the same model's ability to store data.
 */
public class TransformationEqualityChecker {

    public List<Transformation> getNonEqualTransformations(EntityRelationshipModel modelBefore, EntityRelationshipModel modelAfter, List<Transformation> transformationsMade, Mapping mapping) {
        List<Transformation> nonEqualTransformations = new ArrayList<>(transformationsMade);

        /*
        chces (pre oba modely, najprv komplet jeden, potom komplet druhy):
        - hladat rozdiely (chybajuci ES / chybajuci vztah)
        - overit, ci nejde o nejaky ekvivalentny pripad (pre kazdy specificky scenar ich je viac, bude asi treba zvlast implementovat kazdy)
        - najst transformacie, ktore su za to zodpovedne (zavisle na tom, ci som v modeli PRED alebo modeli ZA)
         */
        return nonEqualTransformations;
    }
}