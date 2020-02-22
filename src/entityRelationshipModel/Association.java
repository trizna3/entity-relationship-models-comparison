package entityRelationshipModel;

import java.util.ArrayList;
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

    public Association() {
        this.sides = new ArrayList<>();
        this.attributes = new ArrayList<>();
    }

    public Association(List<AssociationSide> sides, List<String> attributes) {
        this.sides = sides;
        this.attributes = attributes;
    }

    public List<AssociationSide> getSides() {
        return sides;
    }

    public List<String> getAttributes() {
        return attributes;
    }


}
