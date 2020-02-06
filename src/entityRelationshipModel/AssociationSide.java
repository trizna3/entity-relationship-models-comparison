package entityRelationshipModel;

public class AssociationSide {

    private EntitySet entitySet;
    private Cardinality cardinality;

    public AssociationSide(EntitySet entitySet, Cardinality cardinality) {
        this.entitySet = entitySet;
        this.cardinality = cardinality;
    }

    public EntitySet getEntitySet() {
        return entitySet;
    }

    public Cardinality getCardinality() {
        return cardinality;
    }
}
