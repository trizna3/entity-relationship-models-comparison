package entityRelationshipModel;

import java.util.List;

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
}
