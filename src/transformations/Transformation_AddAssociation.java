package transformations;

import entityRelationshipModel.AssociationSide;
import entityRelationshipModel.EntityRelationshipModel;

import java.util.List;

/**
 * @author - Adam Trizna
 */

/**
 * Transformation - adding new association relationship.
 * @see transformations.Transformation
 */
public class Transformation_AddAssociation extends NonEquivalentTransformation {

    public static final String ASSOCIATION_NAME = "ASSOCIATION_NAME";
    public static final Class<String> ASSOCIATION_NAME_CLASS = String.class;

    public static final String ASSOCIATION_ATTRIBUTES = "ENTITY_SET_ATTRIBUTES";
    public static final Class<List> ASSOCIATION_ATTRIBUTES_CLASS = List.class;

    public static final String ASSOCIATION_SIDES = "ASSOCIATION_SIDES";
    public static final Class<List> ASSOCIATION_SIDES_CLASS = List.class;

    public Transformation_AddAssociation() {
        parameterNames = new String[]{ASSOCIATION_NAME, ASSOCIATION_ATTRIBUTES, ASSOCIATION_SIDES};
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
        return ASSOCIATION_NAME_CLASS.cast(parameterMap.get(ASSOCIATION_NAME));
    }

    public List<String> getAssociationAttributes() {
        return ASSOCIATION_ATTRIBUTES_CLASS.cast(parameterMap.get(ASSOCIATION_ATTRIBUTES));
    }

    public List<AssociationSide> getAssociationSides() {
        return ASSOCIATION_SIDES_CLASS.cast(parameterMap.get(ASSOCIATION_SIDES));
    }
}
