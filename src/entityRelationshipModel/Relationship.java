package entityRelationshipModel;

import common.PrintUtils;

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
public abstract class Relationship {

	/**
	 * Relationship name.
	 */
	private String name;

	abstract public RelationshipSide[] getSides();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		if (name != null) {
			throw new IllegalStateException("Name reassignment not allowed!");
		}
		this.name = name;
	}

	@Override
	public String toString() {
		return PrintUtils.print(this);

	}
}
