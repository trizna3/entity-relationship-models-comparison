package entityRelationshipModel;

import common.PrintUtils;
import transformations.Transformable;

/**
 * @author - Adam Trizna
 */

public abstract class RelationshipSide extends Transformable {

	public abstract EntitySet getEntitySet();

	public abstract void setEntitySet(EntitySet entitySet);

	public abstract String getRole();

	public abstract void setRole(String role);

	@Override
	public String toString() {
		return PrintUtils.print(this);
	}
}
