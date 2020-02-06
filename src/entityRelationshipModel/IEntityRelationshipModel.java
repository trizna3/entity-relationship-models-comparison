package entityRelationshipModel;

import java.util.List;

/**
 * Interface of entity relationship models.
 */
public interface IEntityRelationshipModel {

    public List<EntitySet> getAllEntitySets();

    public List<Relationship> getAllRelationships();
}
