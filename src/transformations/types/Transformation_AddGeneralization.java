package transformations.types;

import entityRelationshipModel.EntityRelationshipModel;
import entityRelationshipModel.Generalization;
import entityRelationshipModel.GeneralizationSide;

import java.util.List;

/**
 * @author - Adam Trizna
 */

/**
 * Transformation - adding new generalization relationship.
 * @see Transformation
 */
public class Transformation_AddGeneralization extends Transformation {
    public static final String GENERALIZATION_NAME = "GENERALIZATION_NAME";
    public static final Class<String> GENERALIZATION_NAME_CLASS = String.class;

    public static final String GENERALIZATION_SIDES = "GENERALIZATION_SIDES";
    public static final Class<List> GENERALIZATION_SIDES_CLASS = List.class;

    public Transformation_AddGeneralization() {
        parameterNames = new String[]{GENERALIZATION_NAME, GENERALIZATION_SIDES};
    }

    private Generalization generalization;

    @Override
    public void execute(EntityRelationshipModel model) {
        if (this.generalization != null) {
            throw new IllegalStateException("transformation already executed!");
        }
        this.generalization = new Generalization(getGeneralizationSides());
        this.generalization.setName(getGeneralizationName());
        model.addRelationship(generalization);
    }

    @Override
    public void setToOriginalState(EntityRelationshipModel model) {
        model.removeRelationship(generalization);
    }

    public String getGeneralizationName() {
        return GENERALIZATION_NAME_CLASS.cast(parameterMap.get(GENERALIZATION_NAME));
    }

    public List<GeneralizationSide> getGeneralizationSides() {
        return GENERALIZATION_SIDES_CLASS.cast(parameterMap.get(GENERALIZATION_SIDES));
    }

    public Generalization getGeneralization() {
        return generalization;
    }

    public void setGeneralization(Generalization generalization) {
        this.generalization = generalization;
    }
}
