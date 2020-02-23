package transformations;

import entityRelationshipModel.EntityRelationshipModel;

/**
 * @author - Adam Trizna
 */

/**
 * Transformation of a binary association of cardinalities MANY:MANY, creating a "joining" entity set and additional binary association, resulting in two binary associations of cardinalities ONE:MANY, MANY:ONE.
 * @see transformations.Transformation
 */
public class Transformation_NN_TO_1NN1 extends Transformation {

    // todo: figure out how to pass specific transformation execution arguments

    @Override
    public void execute() {
        throw new UnsupportedOperationException();
    }

    @Override
    public EntityRelationshipModel getOriginalState() {
        throw new UnsupportedOperationException();
    }
}
