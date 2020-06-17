package transformations.types;

import entityRelationshipModel.Association;
import entityRelationshipModel.AssociationSide;
import entityRelationshipModel.ERModel;

import java.util.List;

/**
 * @author - Adam Trizna
 */

/**
 * Transformation - adding new association relationship.
 * @see Transformation
 */
public class Transformation_AddAssociation extends Transformation {

    public static final String ASSOCIATION_NAME = "ASSOCIATION_NAME";
    public static final Class<String> ASSOCIATION_NAME_CLASS = String.class;

    public static final String ASSOCIATION_ATTRIBUTES = "ASSOCIATION_ATTRIBUTES";
    public static final Class<List> ASSOCIATION_ATTRIBUTES_CLASS = List.class;

    public static final String ASSOCIATION_SIDES = "ASSOCIATION_SIDES";
    public static final Class<List> ASSOCIATION_SIDES_CLASS = List.class;

    private Association association;

    public Transformation_AddAssociation() {
        parameterNames = new String[]{ASSOCIATION_NAME, ASSOCIATION_ATTRIBUTES, ASSOCIATION_SIDES};
    }

    @Override
    public void execute(ERModel model) {
        if (this.association != null) {
            throw new IllegalStateException("transformation already executed!");
        }
        this.association = new Association(getAssociationSides(),getAssociationAttributes());
        this.association.setName(getAssociationName());
        model.addRelationship(association);
    }

    @Override
    public void setToOriginalState(ERModel model) {
        model.removeRelationship(association);
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

    public Association getAssociation() {
        return association;
    }

    public void setAssociation(Association association) {
        this.association = association;
    }
}
