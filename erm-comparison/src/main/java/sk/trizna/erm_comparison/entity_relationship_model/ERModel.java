package sk.trizna.erm_comparison.entity_relationship_model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import sk.trizna.erm_comparison.common.utils.MappingUtils;
import sk.trizna.erm_comparison.common.utils.PrintUtils;
import sk.trizna.erm_comparison.common.utils.RelationshipUtils;
import sk.trizna.erm_comparison.common.utils.Utils;
import sk.trizna.erm_comparison.transformations.Transformable;

/**
 * @author - Adam Trizna
 */

/**
 * Entity relationship model, representing a relational database structure.
 */
public class ERModel {

	private List<EntitySet> entitySets;
	private List<Relationship> relationships;
	private List<EntitySet> originalEntitySets;

	private boolean isExemplar;
	
	/**
	 * Used for mapping penalty computation.
	 */
	private Integer toProcess = 0;

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

	public void addRelationship(Relationship relationship, boolean updateNeighbours) {
		Utils.validateNotNull(relationship);
		if (updateNeighbours) {
			// update neighbor maps
			for (EntitySet entitySet : relationship.getIncidentEntitySetsDistinct()) {
				entitySet.addNeighbours(relationship);
			}
		}
		getRelationships().add(relationship);
	}
	
	public void addRelationship(Relationship relationship) {
		addRelationship(relationship,true);
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
		for (EntitySet entitySet : relationship.getIncidentEntitySetsDistinct()) {
			entitySet.removeNeighbours(relationship);
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
	
	public boolean containsAll(Collection<EntitySet> entitySets) {
		return getEntitySets().containsAll(entitySets);
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

	public boolean isExemplar() {
		return isExemplar;
	}

	public void setExemplar(boolean isExemplar) {
		this.isExemplar = isExemplar;
	}

	public List<Transformable> getTransformables() {
		List<Transformable> result = new ArrayList<Transformable>();

		result.addAll(getEntitySets());
		result.addAll(getRelationships());

		return result;
	}
	
	
	public void prepareEntitySetsForProcessing() {
		toProcess = 0;
		for (EntitySet entitySet : getEntitySets()) {
			if (entitySet.getMappedTo() == null) {
				continue;
			}
			entitySet.setProcessed(Boolean.FALSE);
			toProcess = getToProcess() + 1;
		}
	}
	
	public void prepareRelationshipsForProcessing() {
		toProcess = 0;
		for (Relationship relationship : getRelationships()) {
			if (!RelationshipUtils.isMapped(relationship)) {
				continue;
			}
			relationship.setProcessed(Boolean.FALSE);
			toProcess = getToProcess() + 1;
		}
	}
	
	public void unprocessAllEntitySets() {
		getEntitySets().stream().forEach(es -> es.setProcessed(null));
		toProcess = 0;
	}
	
	public void unprocessAllRelationships() {
		getRelationships().stream().forEach(es -> es.setProcessed(null));
		toProcess = 0;
	}
	
	public void process(ERModelElement element) {
		element.setProcessed(Boolean.TRUE);
		toProcess = getToProcess() - 1;
	}
	
	public void unprocess(ERModelElement element) {
		element.setProcessed(Boolean.TRUE);
		toProcess = getToProcess() + 1;
	}

	public Integer getToProcess() {
		if (toProcess == null) {
			toProcess = 0;
		}
		return toProcess;
	}
	
	public void saveOriginalEntitySets() {
		originalEntitySets = new ArrayList<>(getEntitySets());
	}
	
	public boolean isOriginalEntitySet(EntitySet entitySet) {
		return originalEntitySets != null && originalEntitySets.contains(entitySet);
	}
}