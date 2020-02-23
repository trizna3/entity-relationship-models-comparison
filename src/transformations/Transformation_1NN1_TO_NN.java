package transformations;

import entityRelationshipModel.EntityRelationshipModel;

/**
 * @author - Adam Trizna
 */

/**
 * Transformation of a two binary associations of cardinalities ONE:MANY, MANY:ONE (the middle entity set being only a "joining" one) to a single binary association of cardinalities MANY:MANY.
 * @see transformations.Transformation
 */
public class Transformation_1NN1_TO_NN extends Transformation{

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
