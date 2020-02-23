package entityRelationshipModel;

import java.util.ArrayList;
import java.util.List;

/**
 * @author - Adam Trizna
 */

/**
 * Entity relationship model, representing a relational database structure.
 */
public class EntityRelationshipModel implements IEntityRelationshipModel {

    private List<EntitySet> entitySets;
    private List<Relationship> relationships;

    /**
     * @return all entity sets
     */
    @Override
    public List<EntitySet> getEntitySets() {
        if (entitySets == null) {
            entitySets = new ArrayList<>();
        }
        return entitySets;
    }

    /**
     * @return all relationships
     */
    @Override
    public List<Relationship> getRelationships() {
        if (relationships == null) {
            relationships = new ArrayList<>();
        }
        return relationships;
    }

    @Override
    public void addEntitySet(EntitySet entitySet) {
        getEntitySets().add(entitySet);
    }

    @Override
    public void addRelationship(Relationship relationship) {
        getRelationships().add(relationship);
    }

}
