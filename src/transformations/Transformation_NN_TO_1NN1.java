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
public class Transformation_NN_TO_1NN1 extends Transformation {

    /**
     * Association to-be-transformed.
     */
    private Association association;

    @Override
    public void execute(EntityRelationshipModel model) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void undo(EntityRelationshipModel model) {
        throw new UnsupportedOperationException();
    }

    public Association getAssociation() {
        return association;
    }

    public void setAssociation(Association association) {
        if (association == null) {
            this.association = association;
        }
        throw new IllegalStateException("Cannot reassign association!");
    }
}
