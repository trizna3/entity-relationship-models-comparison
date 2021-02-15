package sk.trizna.erm_comparison.entity_relationship_model;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import sk.trizna.erm_comparison.common.utils.PrintUtils;
import sk.trizna.erm_comparison.common.utils.RelationshipUtils;
import sk.trizna.erm_comparison.common.utils.Utils;

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
public abstract class Relationship extends ERModelElement implements Named {

	public abstract List<? extends RelationshipSide> getSides();

	public abstract boolean isBinary();
	
	public Relationship(String name) {
		super(name);
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
	
	public Set<EntitySet> getIncidentEntitySetsDistinct() {
		return getSides().stream().map(side -> side.getEntitySet()).collect(Collectors.toSet());
	}

	protected RelationshipSide getFirst() {
		Utils.validateBinary(this);
		return getSides().get(0);
	}

	protected RelationshipSide getSecond() {
		Utils.validateBinary(this);
		return getSides().get(1);
	}
}
