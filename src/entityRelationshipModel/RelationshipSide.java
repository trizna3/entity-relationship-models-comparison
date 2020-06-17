package entityRelationshipModel;

import common.PrintUtils;

/**
 * @author - Adam Trizna
 */

public abstract class RelationshipSide {

	public abstract EntitySet getEntitySet();

	public abstract String getRole();

	@Override
	public String toString() {
		return PrintUtils.print(this);
	}
}
