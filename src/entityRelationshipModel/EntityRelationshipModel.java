package entityRelationshipModel;

import java.util.List;

/**
 * Entity relationship model, representing a relational database structure.
 */
public class EntityRelationshipModel implements IEntityRelationshipModel {

    /**
     * @return all entity sets
     */
    @Override
    public List<EntitySet> getAllEntitySets() {
        throw new UnsupportedOperationException("todo");
    }

    /**
     * @return all relationships
     */
    @Override
    public List<Relationship> getAllRelationships() {
        throw new UnsupportedOperationException("todo");
    }
}
