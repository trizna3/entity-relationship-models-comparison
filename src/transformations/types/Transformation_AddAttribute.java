package transformations.types;

import entityRelationshipModel.ERModel;
import entityRelationshipModel.EntitySet;

/**
 * @author - Adam Trizna
 */
public class Transformation_AddAttribute extends Transformation {

    public static final String ATTRIBUTE_NAME = "ATTRIBUTE_NAME";
    public static final Class<String> ATTRIBUTE_NAME_CLASS = String.class;

    public static final String ENTITY_SET = "ENTITY_SET";
    public static final Class<EntitySet> ENTITY_SET_CLASS = EntitySet.class;

    public Transformation_AddAttribute() {
        parameterNames = new String[]{ATTRIBUTE_NAME, ENTITY_SET};
    }

    @Override
    protected void execute(ERModel model) {
        if (!model.getEntitySets().contains(getEntitySet())) {
            throw new IllegalArgumentException("Given entity set is not contained in the model!");
        }
        getEntitySet().getAttributes().add(getAttributeName());
    }

    @Override
    protected void setToOriginalState(ERModel model) {
        if (!model.getEntitySets().contains(getEntitySet())) {
            throw new IllegalArgumentException("Given entity set is not contained in the model!");
        }
        getEntitySet().getAttributes().remove(getAttributeName());
    }

    public String getAttributeName() {
        return ATTRIBUTE_NAME_CLASS.cast(parameterMap.get(ATTRIBUTE_NAME));
    }

    public EntitySet getEntitySet() {
        return ENTITY_SET_CLASS.cast(parameterMap.get(ENTITY_SET));
    }
}
