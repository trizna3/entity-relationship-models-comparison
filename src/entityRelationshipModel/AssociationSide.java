package entityRelationshipModel;

/**
 * Object wrapping entity set and a cardinality.
 * @see EntitySet
 * @see Cardinality
 */
public class AssociationSide implements RelationshipSide{

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

    @Override
    public RelationshipSideRole getRole() {
        return getCardinality();
    }
}
