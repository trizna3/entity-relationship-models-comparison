package entityRelationshipModel;

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
