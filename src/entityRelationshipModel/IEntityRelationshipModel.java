package entityRelationshipModel;

import java.util.List;

/**
 * @author - Adam Trizna
 */

/**
 * Interface of entity relationship models.
 */
public interface IEntityRelationshipModel {

    List<EntitySet> getEntitySets();

    List<Relationship> getRelationships();

    void addEntitySet(EntitySet entitySet);

    void addRelationship(Relationship relationship);

    int getEntitySetsCount();
}
