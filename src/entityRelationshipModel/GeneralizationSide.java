package entityRelationshipModel;

import common.Utils;

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
		Utils.validateNotNull(entitySet);
		Utils.validateNotNull(role);

		this.entitySet = entitySet;
		this.role = role;
	}

	public GeneralizationSide(GeneralizationSide generalizationSide) {
		Utils.validateNotNull(generalizationSide);

		this.entitySet = generalizationSide.getEntitySet();
		this.role = generalizationSide.getRole();
	}

	public EntitySet getEntitySet() {
		return entitySet;
	}

	public String getRole() {
		return role;
	}

	public void setEntitySet(EntitySet entitySet) {
		this.entitySet = entitySet;
	}
}
