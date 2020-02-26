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
public class Transformation_1NN1_TO_NN extends Transformation{

    /**
     * Associations to be transformed to a single association.
     */
    Association association1;
    Association association2;

    @Override
    public void execute(EntityRelationshipModel model) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void undo(EntityRelationshipModel model) {
        throw new UnsupportedOperationException();
    }

    public Association getAssociation1() {
        return association1;
    }

    public void setAssociation1(Association association1) {
        if (association1 == null) {
            this.association1 = association1;
        }
        throw new IllegalStateException("Cannot reassign association1!");
    }

    public Association getAssociation2() {
        return association2;
    }

    public void setAssociation2(Association association2) {
        if (association2 == null) {
            this.association2 = association2;
        }
        throw new IllegalStateException("Cannot reassign association2!");
    }
}
