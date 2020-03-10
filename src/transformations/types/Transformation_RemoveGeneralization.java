package transformations.types;

import entityRelationshipModel.EntityRelationshipModel;
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
    public void execute(EntityRelationshipModel model) {
        if (!model.getRelationships().contains(getGeneralization())) {
            throw new IllegalStateException("model doesn't contain given generalization!");
        }
        model.removeRelationship(getGeneralization());
    }

    @Override
    public void setToOriginalState(EntityRelationshipModel model) {
        model.addRelationship(getGeneralization());
    }

    public Generalization getGeneralization() {
        return GENERALIZATION_CLASS.cast(parameterMap.get(GENERALIZATION));
    }
}
