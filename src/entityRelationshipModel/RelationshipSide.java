package entityRelationshipModel;

import common.PrintUtils;
import common.enums.EnumRelationshipSideRole;
import transformations.Transformable;

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
