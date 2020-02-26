package transformations;

import entityRelationshipModel.Association;
import entityRelationshipModel.EntityRelationshipModel;

/**
 * @author - Adam Trizna
 */

/**
 * Transformation of a two binary associations of cardinalities ONE:MANY, MANY:ONE (the middle entity set being only a "joining" one) to a single binary association of cardinalities MANY:MANY.
 * @see transformations.Transformation
 */
public class Transformation_1NN1_TO_NN extends EquivalentTransformation{

    public static final String ASSOCIATION1 = "ASSOCIATION1";
    public static final Class<Association> ASSOCIATION1_CLASS = Association.class;

    public static final String ASSOCIATION2 = "ASSOCIATION2";
    public static final Class<Association> ASSOCIATION2_CLASS = Association.class;

    public Transformation_1NN1_TO_NN() {
        parameterNames = new String[]{ASSOCIATION1,ASSOCIATION2};
    }

    @Override
    public void execute(EntityRelationshipModel model) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setToOriginalState(EntityRelationshipModel model) {
        throw new UnsupportedOperationException();
    }

    public Association getAssociation1() {
        return ASSOCIATION1_CLASS.cast(parameterMap.get(ASSOCIATION1));
    }
    public Association getAssociation2() {
        return ASSOCIATION2_CLASS.cast(parameterMap.get(ASSOCIATION2));
    }
}
