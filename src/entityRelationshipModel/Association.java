package entityRelationshipModel;

import java.util.List;

/**
 * Type of relationship: Association.
 * Joins 2 or more entity sets, each with specific cardinality.
 * @see AssociationSide
 */
public class Association extends Relationship{

    /**
     * List of association sides.
     */
    private List<AssociationSide> sides;
    /**
     * Attributes list.
     */
    private List<String> attributes;

    public List<AssociationSide> getSides() {
        return sides;
    }

    public List<String> getAttributes() {
        return attributes;
    }
}
