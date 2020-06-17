package transformations.types;

import entityRelationshipModel.Association;
import entityRelationshipModel.AssociationSide;
import entityRelationshipModel.ERModel;

import java.util.List;

/**
 * @author - Adam Trizna
 */
public class Transformation_RemoveAssociation extends Transformation {
    public static final String ASSOCIATION = "ASSOCIATION";
    public static final Class<Association> ASSOCIATION_CLASS = Association.class;

    public Transformation_RemoveAssociation() {
        parameterNames = new String[]{ASSOCIATION};
    }

    @Override
    public void execute(ERModel model) {
        if (!model.getRelationships().contains(getAssociation())) {
            throw new IllegalStateException("model doesn't contain given association!");
        }
        model.removeRelationship(getAssociation());
    }

    @Override
    public void setToOriginalState(ERModel model) {
        model.addRelationship(getAssociation());
    }

    public Association getAssociation() {
        return ASSOCIATION_CLASS.cast(parameterMap.get(ASSOCIATION));
    }
}
