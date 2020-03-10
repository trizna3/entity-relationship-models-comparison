package transformations.types;

import entityRelationshipModel.EntityRelationshipModel;
import entityRelationshipModel.EntitySet;

/**
 * @author - Adam Trizna
 */
public class Transformation_RemoveEntitySet extends Transformation {
    public static final String ENTITY_SET = "ENTITY_SET";
    public static final Class<EntitySet> ENTITY_SET_CLASS = EntitySet.class;

    public Transformation_RemoveEntitySet() {
        parameterNames = new String[]{ENTITY_SET};
    }

    @Override
    public void execute(EntityRelationshipModel model) {
        if (!model.getEntitySets().contains(getEntitySet())) {
            throw new IllegalStateException("model doesn't contain given entity set!");
        }
        if (!model.getRelationshipsByEntitySets(new EntitySet[]{getEntitySet()}).isEmpty()) {   // entity set is not isolated
            throw new IllegalArgumentException("entity set is not isolated and cannot be removed!");
        }
        model.removeEntitySet(getEntitySet());
    }

    @Override
    public void setToOriginalState(EntityRelationshipModel model) {
        model.addEntitySet(getEntitySet());
    }

    public EntitySet getEntitySet() {
        return ENTITY_SET_CLASS.cast(parameterMap.get(ENTITY_SET));
    }
}
