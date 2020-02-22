package entityRelationshipModel;

/**
 * Object wrapping entity set and a it's role in the generalization relationship.
 * @see EntitySet
 * @see GeneralizationRole
 */
public class GeneralizationSide implements RelationshipSide {

    private EntitySet entitySet;
    private GeneralizationRole role;

    public GeneralizationSide(EntitySet entitySet, GeneralizationRole role) {
        if (entitySet == null || role == null) {
            throw new IllegalArgumentException("both entity set and role mustn't be null.");
        }
        this.entitySet = entitySet;
        this.role = role;
    }

    public EntitySet getEntitySet() {
        return entitySet;
    }

    public GeneralizationRole getRole() {
        return role;
    }

    @Override
    public String toString() {
        return getEntitySet().toString() + "(" + getRole() + ")";
    }
}
