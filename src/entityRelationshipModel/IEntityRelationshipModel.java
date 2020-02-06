package entityRelationshipModel;

import java.util.List;

public interface IEntityRelationshipModel {

    public List<EntitySet> getAllEntitySets();

    public List<Relationship> getAllRelationships();
}
