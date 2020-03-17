package entityRelationshipModel;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author - Adam Trizna
 */

/**
 * General relationship of a database structure representing er model.
 * Superclass for Association and Generalization.
 * @see Association
 * @see Generalization
 */
public abstract class Relationship {

    /**
     * Relationship name.
     */
    private String name;

    abstract public List<? extends RelationshipSide> getSides();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name != null) {
            throw new IllegalStateException("Name reassignment not allowed!");
        }
        this.name = name;
    }

    @Override
    public String toString() {
        return String.join("-",getSides().stream().map(side -> side.toString()).collect(Collectors.toList()));

    }
}
