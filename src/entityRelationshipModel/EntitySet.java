package entityRelationshipModel;

import java.util.List;

/**
 * Base element of entity relationship model. Entails a set of entities with common properties (attributes).
 */
public class EntitySet {

    /**
     * Entity set name.
     */
    private String name;
    /**
     * List of entity set's attributes.
     */
    private List<String> attributes;

    public String getName() {
        return name;
    }

    public List<String> getAttributes() {
        return attributes;
    }
}