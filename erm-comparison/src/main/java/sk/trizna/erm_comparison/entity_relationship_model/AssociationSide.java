package sk.trizna.erm_comparison.entity_relationship_model;

import sk.trizna.erm_comparison.common.Utils;
import sk.trizna.erm_comparison.common.enums.EnumRelationshipSideRole;

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
	private EnumRelationshipSideRole cardinality;

	public AssociationSide(EntitySet entitySet, EnumRelationshipSideRole cardinality) {
		Utils.validateNotNull(entitySet);
		Utils.validateNotNull(cardinality);

		this.entitySet = entitySet;
		this.cardinality = cardinality;
	}

	public AssociationSide(AssociationSide associationSide) {
		this.entitySet = associationSide.getEntitySet();
		this.cardinality = associationSide.getRole();
	}

	public EntitySet getEntitySet() {
		return entitySet;
	}

	public EnumRelationshipSideRole getRole() {
		return cardinality;
	}

	public void setEntitySet(EntitySet entitySet) {
		this.entitySet = entitySet;
	}

	@Override
	public void setRole(EnumRelationshipSideRole role) {
		this.cardinality = role;
	}	
}
