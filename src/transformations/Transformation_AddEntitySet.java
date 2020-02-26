package transformations;

import entityRelationshipModel.EntityRelationshipModel;
import java.util.List;

/**
 * @author - Adam Trizna
 */

/**
 * Transformation - creating new Entity Set.
 * @see transformations.Transformation
 */
public class Transformation_AddEntitySet extends NonEquivalentTransformation {

    public static final String ENTITY_SET_NAME = "ENTITY_SET_NAME";
    public static final Class<String> ENTITY_SET_NAME_CLASS = String.class;

    public static final String ENTITY_SET_ATTRIBUTES = "ENTITY_SET_ATTRIBUTES";
    public static final Class<List> ENTITY_SET_ATTRIBUTES_CLASS = List.class;

    public Transformation_AddEntitySet() {
        parameterNames = new String[]{ENTITY_SET_NAME,ENTITY_SET_ATTRIBUTES};
    }

    @Override
    public void execute(EntityRelationshipModel model) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setToOriginalState(EntityRelationshipModel model) {
        throw new UnsupportedOperationException();
    }

    public String getEntitySetName() {
        return ENTITY_SET_NAME_CLASS.cast(parameterMap.get(ENTITY_SET_NAME));
    }

    public List<String> getEntitySetAttributes() {
        return ENTITY_SET_ATTRIBUTES_CLASS.cast(parameterMap.get(ENTITY_SET_ATTRIBUTES));
    }
}
