package transformations.types;

import entityRelationshipModel.ERModel;
import entityRelationshipModel.EntitySet;

import java.util.List;

/**
 * @author - Adam Trizna
 */

/**
 * Transformation - creating new Entity Set.
 * @see Transformation
 */
public class Transformation_AddEntitySet extends Transformation {

    public static final String ENTITY_SET = "ENTITY_SET";
    public static final Class<EntitySet> ENTITY_SET_CLASS = EntitySet.class;

    public Transformation_AddEntitySet() {
        parameterNames = new String[]{ENTITY_SET};
    }

    @Override
    public void execute(ERModel model) {
        EntitySet entitySet = getEntitySet();
        if (model.contains(entitySet)) {
            throw new IllegalStateException("transformation already executed!");
        }
        model.addEntitySet(entitySet);
    }

    @Override
    public void setToOriginalState(ERModel model) {
        model.removeEntitySet(getEntitySet());
    }

    public EntitySet getEntitySet() {
        return ENTITY_SET_CLASS.cast(parameterMap.get(ENTITY_SET));
    }
}
