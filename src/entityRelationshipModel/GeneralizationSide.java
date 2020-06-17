package entityRelationshipModel;

/**
 * @author - Adam Trizna
 */

/**
 * Object wrapping entity set and a it's role in the generalization
 * relationship.
 * 
 * @see EntitySet
 * @see GeneralizationRole
 */
public class GeneralizationSide extends RelationshipSide {

	private EntitySet entitySet;
	private String role;

	public GeneralizationSide(EntitySet entitySet, String role) {
		if (entitySet == null || role == null) {
			throw new IllegalArgumentException("both entity set and role mustn't be null.");
		}
		this.entitySet = entitySet;
		this.role = role;
	}

	public EntitySet getEntitySet() {
		return entitySet;
	}

	public String getRole() {
		return role;
	}
}
