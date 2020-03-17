package entityRelationshipModel;

import java.util.ArrayList;
import java.util.List;

/**
 * @author - Adam Trizna
 */

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

    /**
     * Sign of an empty entity set.
     */
    private boolean empty;

    public EntitySet(String name) {
        this.name = name;
        this.attributes = new ArrayList<>();
    }

    public EntitySet(String name, List<String> attributes) {
        this.name = name;
        this.attributes = attributes;
    }

    public String getName() {
        return name;
    }

    public List<String> getAttributes() {
        return attributes;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAttributes(List<String> attributes) {
        this.attributes = attributes;
    }

    @Override
    public String toString() {
        return name;
    }

    public boolean isEmpty() {
        return empty;
    }

    public void setEmpty(boolean empty) {
        this.empty = empty;
    }
}