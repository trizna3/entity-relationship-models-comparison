package transformations;

import entityRelationshipModel.Association;
import entityRelationshipModel.EntityRelationshipModel;

/**
 * @author - Adam Trizna
 */

/**
 * Transformation of a binary association of cardinalities MANY:MANY, creating a "joining" entity set and additional binary association, resulting in two binary associations of cardinalities ONE:MANY, MANY:ONE.
 * @see transformations.Transformation
 */
public class Transformation_NN_TO_1NN1 extends EquivalentTransformation {

    public static final String ASSOCIATION = "ASSOCIATION";

    public Transformation_NN_TO_1NN1() {
        parameterNames = new String[]{ASSOCIATION};
    }

    @Override
    public void execute(EntityRelationshipModel model) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setToOriginalState(EntityRelationshipModel model) {
        throw new UnsupportedOperationException();
    }

    public Association getAssociation() {
        return (Association) parameterMap.get(ASSOCIATION);
    }
}
