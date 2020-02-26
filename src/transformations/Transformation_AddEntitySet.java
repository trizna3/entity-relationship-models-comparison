package transformations;

import entityRelationshipModel.EntityRelationshipModel;

import java.util.List;
import java.util.Set;

/**
 * @author - Adam Trizna
 */

/**
 * Transformation - creating new Entity Set.
 * @see transformations.Transformation
 */
public class Transformation_AddEntitySet extends Transformation {

    public static final String ENTITY_SET_NAME = "ENTITY_SET_NAME";
    public static final String ENTITY_SET_ATTRIBUTES = "ENTITY_SET_ATTRIBUTES";

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
        return (String) parameterMap.get(ENTITY_SET_NAME);
    }

    public List<String> getEntitySetAttributes() {
        return (List<String>) parameterMap.get(ENTITY_SET_ATTRIBUTES);
    }
}
