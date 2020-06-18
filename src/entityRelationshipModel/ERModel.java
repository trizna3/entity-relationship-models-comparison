package entityRelationshipModel;

import java.util.ArrayList;
import java.util.List;

import common.ERModelUtils;

/**
 * @author - Adam Trizna
 */

/**
 * Entity relationship model, representing a relational database structure.
 */
public class ERModel {

	private List<EntitySet> entitySets;
	private List<Relationship> relationships;

	public List<EntitySet> getEntitySets() {
		if (entitySets == null) {
			entitySets = new ArrayList<>();
		}
		return entitySets;
	}

	public List<Relationship> getRelationships() {
		if (relationships == null) {
			relationships = new ArrayList<>();
		}
		return relationships;
	}

	public void addEntitySet(EntitySet entitySet) {
		getEntitySets().add(entitySet);
	}

	public void addRelationship(Relationship relationship) {
		getRelationships().add(relationship);
	}

	public void removeEntitySet(EntitySet entitySet) {
		if (entitySet == null) {
			throw new IllegalArgumentException("entity set cannot be null");
		}
		if (!contains(entitySet)) {
			throw new IllegalArgumentException("model doesn't contain this entity set!");
		}
		// remove all incident relationships
		for (Relationship relationshipForRemoval : ERModelUtils.getRelationshipsByEntitySets(this, new EntitySet[] { entitySet })) {
			removeRelationship(relationshipForRemoval);
		}
		// remove the entity set
		getEntitySets().remove(entitySet);
	}

	public void removeRelationship(Relationship relationship) {
		if (relationship == null) {
			throw new IllegalArgumentException("relationship cannot be null");
		}
		if (!contains(relationship)) {
			throw new IllegalArgumentException("model doesn't contain this relationship!");
		}
		getRelationships().remove(relationship);
	}

	public int getEntitySetsCount() {
		return getEntitySets().size();
	}

	public boolean contains(EntitySet entitySet) {
		return getEntitySets().contains(entitySet);
	}

	public boolean contains(Relationship relationship) {
		return getRelationships().contains(relationship);
	}
}