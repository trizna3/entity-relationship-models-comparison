package entityRelationshipModel;

import java.util.List;

/**
 * @author - Adam Trizna
 */

/**
 * Interface of entity relationship models.
 */
public interface IEntityRelationshipModel {

    /**
     * @return list of all entity sets
     */
    List<EntitySet> getEntitySets();

    /**
     * @return list of all relationships
     */
    List<Relationship> getRelationships();

    /**
     * Adds new entity set
     * @param entitySet
     */
    void addEntitySet(EntitySet entitySet);

    /**
     * Adds new relationship.
     * @param relationship
     */
    void addRelationship(Relationship relationship);

    /**
     * Removes entity set. Removes all relationships incident to removed entity set.
     * @param entitySet
     */
    void removeEntitySet(EntitySet entitySet);

    /**
     * Removes relationship.
     * @param relationship
     */
    void removeRelationship(Relationship relationship);

    // ** utilities **

    /**
     * @return number of entity sets.
     */
    int getEntitySetsCount();

    /**
     * @param entitySets
     * @return List of all relationships incident to given entity set.
     */
    List<Relationship> getRelationshipsByEntitySets(EntitySet[] entitySets);
}
