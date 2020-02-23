package entityRelationshipModel;

import java.util.List;

/**
 * @author - Adam Trizna
 */

/**
 * Interface of entity relationship models.
 */
public interface IEntityRelationshipModel {

    public List<EntitySet> getEntitySets();

    public List<Relationship> getRelationships();

    public void addEntitySet(EntitySet entitySet);

    public void addRelationship(Relationship relationship);
}
