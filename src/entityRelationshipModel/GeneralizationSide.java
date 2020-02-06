package entityRelationshipModel;

/**
 * Object wrapping entity set and a it's role in the generalization relationship.
 * @see EntitySet
 * @see GeneralizationRole
 */
public class GeneralizationSide {

    private EntitySet entitySet;
    private GeneralizationRole role;

    public GeneralizationSide(EntitySet entitySet, GeneralizationRole role) {
        this.entitySet = entitySet;
        this.role = role;
    }

    public EntitySet getEntitySet() {
        return entitySet;
    }

    public GeneralizationRole getRole() {
        return role;
    }
}
