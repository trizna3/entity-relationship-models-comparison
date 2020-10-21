package entityRelationshipModel;

import java.util.List;
import java.util.stream.Collectors;

import common.PrintUtils;
import common.RelationshipUtils;
import common.Utils;

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
public abstract class Relationship extends ERModelElement {

	/**
	 * Relationship name.
	 */
	private String name;

	public abstract List<? extends RelationshipSide> getSides();

	public abstract boolean isBinary();

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

	public List<EntitySet> getEntitySets() {
		return getSides().stream().map(side -> side.getEntitySet()).collect(Collectors.toList());
	}

	public RelationshipSide getFirstSide() {
		Utils.validateBinary(this);
		return getFirst();
	}

	public RelationshipSide getSecondSide() {
		Utils.validateBinary(this);
		return getSecond();
	}

	public RelationshipSide getFirst() {
		Utils.validateBinary(this);
		return getSides().get(0);
	}

	public RelationshipSide getSecond() {
		Utils.validateBinary(this);
		return getSides().get(1);
	}
}
