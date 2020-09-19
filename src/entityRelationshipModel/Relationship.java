package entityRelationshipModel;

import common.PrintUtils;
import common.RelationshipUtils;
import common.Utils;
import transformations.Transformable;

/**
 * @author - Adam Trizna
 */

/**
 * General relationship of a database structure representing er model.
 * Superclass for Association and Generalization.
 * 
 * @see Association
 * @see Generalization
 */
public abstract class Relationship extends Transformable {

	/**
	 * Relationship name.
	 */
	private String name;

	public abstract RelationshipSide[] getSides();

	public abstract boolean isBinary();

	protected abstract RelationshipSide getFirst();

	protected abstract RelationshipSide getSecond();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return PrintUtils.print(this);

	}

	public boolean contains(EntitySet entitySet) {
		return RelationshipUtils.contains(this, entitySet);
	}

	public EntitySet[] getEntitySets() {
		EntitySet[] result = new EntitySet[getSides().length];
		int i = 0;
		for (RelationshipSide side : getSides()) {
			result[i] = side.getEntitySet();
			i++;
		}
		return result;
	}

	public RelationshipSide getFirstSide() {
		Utils.validateBinary(this);
		return getFirst();
	}

	public RelationshipSide getSecondSide() {
		Utils.validateBinary(this);
		return getSecond();
	}
}
