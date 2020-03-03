package entityRelationshipModel;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
     * {@inheritDoc}
     */
    @Override
    public List<EntitySet> getEntitySets() {
        if (entitySets == null) {
            entitySets = new ArrayList<>();
        }
        return entitySets;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Relationship> getRelationships() {
        if (relationships == null) {
            relationships = new ArrayList<>();
        }
        return relationships;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addEntitySet(EntitySet entitySet) {
        getEntitySets().add(entitySet);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addRelationship(Relationship relationship) {
        getRelationships().add(relationship);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeEntitySet(EntitySet entitySet) {
        if (entitySet == null) {
            throw new IllegalArgumentException("entity set cannot be null");
        }
        if (!getEntitySets().contains(entitySet)) {
            throw new IllegalArgumentException("model doesn't contain this entity set!");
        }
        // remove all incident relationships
        for (Relationship relationshipForRemoval : getRelationshipsByEntitySets(new EntitySet[]{entitySet})) {
            removeRelationship(relationshipForRemoval);
        }
        getEntitySets().remove(entitySet);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeRelationship(Relationship relationship) {
        if (relationship == null) {
            throw new IllegalArgumentException("relationship cannot be null");
        }
        if (!getRelationships().contains(relationship)) {
            throw new IllegalArgumentException("model doesn't contain this relationship!");
        }
        getRelationships().remove(relationship);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getEntitySetsCount() {
        return getEntitySets().size();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Relationship> getRelationshipsByEntitySets(EntitySet[] entitySets) {
        if (entitySets == null || entitySets.length == 0) {
            throw new IllegalArgumentException("entity set array cannot be null or empty");
        }
        for (EntitySet entitySet : entitySets) {
            if (!getEntitySets().contains(entitySet)) {
                throw new IllegalArgumentException("model doesn't contain given entity set!");
            }
        }
        List<Relationship> incidentRelationships = new ArrayList<>();
        for (Relationship relationship : getRelationships()) {
            for (EntitySet entitySet : entitySets) {
                if (relationship.getSides().stream().map(RelationshipSide::getEntitySet).collect(Collectors.toList()).contains(entitySet)) {
                    incidentRelationships.add(relationship);
                }
            }
        }
        return incidentRelationships;
    }
}