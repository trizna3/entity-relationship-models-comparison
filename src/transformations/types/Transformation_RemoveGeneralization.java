package transformations.types;

import entityRelationshipModel.ERModel;
import entityRelationshipModel.Generalization;

/**
 * @author - Adam Trizna
 */
public class Transformation_RemoveGeneralization extends Transformation {
    public static final String GENERALIZATION = "GENERALIZATION";
    public static final Class<Generalization> GENERALIZATION_CLASS = Generalization.class;

    public Transformation_RemoveGeneralization() {
        parameterNames = new String[]{GENERALIZATION};
    }

    @Override
    public void execute(ERModel model) {
        if (!model.getRelationships().contains(getGeneralization())) {
            throw new IllegalStateException("model doesn't contain given generalization!");
        }
        model.removeRelationship(getGeneralization());
    }

    @Override
    public void setToOriginalState(ERModel model) {
        model.addRelationship(getGeneralization());
    }

    public Generalization getGeneralization() {
        return GENERALIZATION_CLASS.cast(parameterMap.get(GENERALIZATION));
    }
}
