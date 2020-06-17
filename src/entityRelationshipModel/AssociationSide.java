package entityRelationshipModel;

/**
 * @author - Adam Trizna
 */

/**
 * Object wrapping entity set and a cardinality.
 * 
 * @see EntitySet
 * @see Cardinality
 */
public class AssociationSide extends RelationshipSide {

	private EntitySet entitySet;
	private String cardinality;

	public AssociationSide(EntitySet entitySet, String cardinality) {
		if (entitySet == null || cardinality == null) {
			throw new IllegalArgumentException("both entity set and cardinality mustn't be null.");
		}
		this.entitySet = entitySet;
		this.cardinality = cardinality;
	}

	public EntitySet getEntitySet() {
		return entitySet;
	}

	public String getRole() {
		return cardinality;
	}
}
