package transformations;

import entityRelationshipModel.EntityRelationshipModel;
import entityRelationshipModel.GeneralizationSide;

import java.util.List;

/**
 * @author - Adam Trizna
 */

/**
 * Transformation - adding new generalization relationship.
 * @see transformations.Transformation
 */
public class Transformation_AddGeneralization extends NonEquivalentTransformation {
    public static final String GENERALIZATION_NAME = "GENERALIZATION_NAME";
    public static final Class<String> GENERALIZATION_NAME_CLASS = String.class;

    public static final String GENERALIZATION_SIDES = "GENERALIZATION_SIDES";
    public static final Class<List> GENERALIZATION_SIDES_CLASS = List.class;

    public Transformation_AddGeneralization() {
        parameterNames = new String[]{GENERALIZATION_NAME, GENERALIZATION_SIDES};
    }

    @Override
    public void execute(EntityRelationshipModel model) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setToOriginalState(EntityRelationshipModel model) {
        throw new UnsupportedOperationException();
    }

    public String getAssociationName() {
        return GENERALIZATION_NAME_CLASS.cast(parameterMap.get(GENERALIZATION_NAME));
    }

    public List<GeneralizationSide> getAssociationSides() {
        return GENERALIZATION_SIDES_CLASS.cast(parameterMap.get(GENERALIZATION_SIDES));
    }
}
