package transformations.types;

import entityRelationshipModel.EntityRelationshipModel;
import entityRelationshipModel.EntitySet;

/**
 * @author - Adam Trizna
 */
public class Transformation_RemoveAttribute extends Transformation {

    public static final String ATTRIBUTE_NAME = "ATTRIBUTE_NAME";
    public static final Class<String> ATTRIBUTE_NAME_CLASS = String.class;

    public static final String ENTITY_SET = "ENTITY_SET";
    public static final Class<EntitySet> ENTITY_SET_CLASS = EntitySet.class;

    public Transformation_RemoveAttribute() {
        parameterNames = new String[]{ATTRIBUTE_NAME, ENTITY_SET};
    }

    @Override
    protected void execute(EntityRelationshipModel model) {
        if (!model.getEntitySets().contains(getEntitySet())) {
            throw new IllegalArgumentException("Given entity set is not contained in the model!");
        }
        if (!getEntitySet().getAttributes().contains(getAttributeName())) {
            throw new IllegalArgumentException("Given entity set doesn't contain said attribute!");
        }
        getEntitySet().getAttributes().remove(getAttributeName());
    }

    @Override
    protected void setToOriginalState(EntityRelationshipModel model) {
        if (!model.getEntitySets().contains(getEntitySet())) {
            throw new IllegalArgumentException("Given entity set is not contained in the model!");
        }
        getEntitySet().getAttributes().add(getAttributeName());
    }

    public String getAttributeName() {
        return ATTRIBUTE_NAME_CLASS.cast(parameterMap.get(ATTRIBUTE_NAME));
    }

    public EntitySet getEntitySet() {
        return ENTITY_SET_CLASS.cast(parameterMap.get(ENTITY_SET));
    }
}
