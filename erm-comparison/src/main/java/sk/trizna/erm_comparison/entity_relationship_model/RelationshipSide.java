package sk.trizna.erm_comparison.entity_relationship_model;

import sk.trizna.erm_comparison.common.enums.EnumRelationshipSideRole;
import sk.trizna.erm_comparison.common.utils.PrintUtils;
import sk.trizna.erm_comparison.transformations.Transformable;

/**
 * @author - Adam Trizna
 */

public abstract class RelationshipSide extends Transformable {

	public abstract EntitySet getEntitySet();

	public abstract void setEntitySet(EntitySet entitySet);

	public abstract EnumRelationshipSideRole getRole();

	public abstract void setRole(EnumRelationshipSideRole role);

	@Override
	public String toString() {
		return PrintUtils.print(this);
	}
}
