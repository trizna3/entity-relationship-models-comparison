package entityRelationshipModel;

import java.util.ArrayList;
import java.util.List;

import common.MappingUtils;
import common.PrintUtils;
import common.Utils;

/**
 * @author - Adam Trizna
 */

/**
 * Entity relationship model, representing a relational database structure.
 */
public class ERModel {

	private List<EntitySet> entitySets;
	private List<Relationship> relationships;

	private boolean isExemplar;

	/**
	 * Entity sets which are not mapped.
	 */
	private List<EntitySet> notMappedEntitySets;

	public ERModel() {
	}

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
		Utils.validateNotNull(entitySet);
		getEntitySets().add(entitySet);
		if (!getNotMappedEntitySets().contains(entitySet) && entitySet.getMappedTo() == null) {
			getNotMappedEntitySets().add(entitySet);
		}
	}

	public void addAllEntitySets(Iterable<EntitySet> entitySets) {
		for (EntitySet es : entitySets) {
			addEntitySet(es);
		}
	}

	public void addRelationship(Relationship relationship) {
		Utils.validateNotNull(relationship);
		// update neighbor maps
		for (RelationshipSide side : relationship.getSides()) {
			side.getEntitySet().addNeighbours(relationship);
		}
		getRelationships().add(relationship);
	}

	public void addAllRelationships(Iterable<Relationship> relationships) {
		for (Relationship rel : relationships) {
			addRelationship(rel);
		}
	}

	public void removeEntitySet(EntitySet entitySet) {
		Utils.validateNotNull(entitySet);
		Utils.validateContains(this, entitySet);

		// remove all incident relationships
		for (Relationship relationshipForRemoval : entitySet.getIncidentRelationships()) {
			removeRelationship(relationshipForRemoval);
		}
		// remove the entity set
		getEntitySets().remove(entitySet);
		if (getNotMappedEntitySets().contains(entitySet)) {
			getNotMappedEntitySets().remove(entitySet);
		}
	}

	public void removeRelationship(Relationship relationship) {
		Utils.validateNotNull(relationship);
		Utils.validateContains(this, relationship);

		// update neighbor maps
		for (RelationshipSide side : relationship.getSides()) {
			side.getEntitySet().removeNeighbours(relationship);
		}

		getRelationships().remove(relationship);
	}

	public int getEntitySetsCount() {
		return getEntitySets().size();
	}

	public int getRelationshipsCount() {
		return getRelationships().size();
	}

	public boolean contains(EntitySet entitySet) {
		return getEntitySets().contains(entitySet);
	}

	public boolean contains(Relationship relationship) {
		return getRelationships().contains(relationship);
	}

	public List<EntitySet> getNotMappedEntitySets() {
		if (notMappedEntitySets == null) {
			notMappedEntitySets = MappingUtils.getNotMappedEntitySets(this);

		}
		return notMappedEntitySets;
	}

	@Override
	public String toString() {
		return PrintUtils.print(this);
	}

	public void resetTransformableRoles() {
		for (EntitySet entitySet : getEntitySets()) {
			entitySet.resetTransformationRole();
		}
		for (Relationship relationship : getRelationships()) {
			relationship.resetTransformationRole();
			for (RelationshipSide side : relationship.getSides()) {
				side.resetTransformationRole();
			}
		}
	}

	public boolean isExemplar() {
		return isExemplar;
	}

	public void setExemplar(boolean isExemplar) {
		this.isExemplar = isExemplar;
	}
}