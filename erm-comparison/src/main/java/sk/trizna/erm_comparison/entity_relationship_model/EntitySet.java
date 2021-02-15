package sk.trizna.erm_comparison.entity_relationship_model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import sk.trizna.erm_comparison.common.utils.PrintUtils;
import sk.trizna.erm_comparison.common.utils.RelationshipUtils;
import sk.trizna.erm_comparison.common.utils.StringUtils;
import sk.trizna.erm_comparison.common.utils.Utils;

/**
 * @author - Adam Trizna
 */

/**
 * Base element of entity relationship model. Entails a set of entities with
 * common properties (attributes).
 */
public class EntitySet extends ERModelElement implements Attributed, Named {

	/**
	 * List of entity set's attributes.
	 */
	private List<Attribute> attributes;

	/**
	 * Sign of an empty entity set.
	 */
	private boolean empty;

	/**
	 * Another entity set, which this is mapped to.
	 */
	private EntitySet mappedTo;

	/**
	 * Map entitySet(neighbor) -> list of relationships, connected between them
	 */
	private Map<EntitySet, List<Relationship>> neighbours;
	
	/**
	 * Used to determine order of bactrack tree traversal
	 */
	private Double priority;
	
	/**
	 * Used in case of binary joining entity sets
	 */
	private EntitySet firstNeigbour;
	private EntitySet secondNeigbour;

	public EntitySet(String name) {
		super(name);
		this.attributes = new ArrayList<Attribute>();
	}

	public EntitySet(String name, List<String> attributes) {
		super(name);
		if (attributes != null) {
			for (String attribute : attributes) {
				getAttributes().add(new Attribute(attribute));
			}
		}
	}

	public EntitySet(EntitySet other) {
		super(other.getNameText());
		this.attributes = new ArrayList<>(other.getAttributes());
		this.empty = other.isEmpty();
		this.mappedTo = other.getMappedTo();
		this.neighbours = new HashMap<>(other.getNeighbours());
	}

	public List<Attribute> getAttributes() {
		if (attributes == null) {
			attributes = new ArrayList<Attribute>();
		}
		return attributes;
	}

	public void setAttributes(List<Attribute> attributes) {
		this.attributes = attributes;
	}

	@Override
	public String toString() {
		return PrintUtils.print(this);
	}

	public boolean isEmpty() {
		return empty;
	}

	public void setEmpty(boolean empty) {
		this.empty = empty;
	}

	public EntitySet getMappedTo() {
		return mappedTo;
	}

	public void setMappedTo(EntitySet mappedTo) {
		this.mappedTo = mappedTo;
	}

	public Map<EntitySet, List<Relationship>> getNeighbours() {
		if (neighbours == null) {
			neighbours = new HashMap<EntitySet, List<Relationship>>();
		}
		return neighbours;
	}

	public List<Relationship> getRelationshipsByNeighbour(EntitySet neighbour) {
		return getNeighbours().get(neighbour);
	}

	public void removeNeighbours(Relationship relationship) {
		Utils.validateContains(relationship, this);

		boolean skipSelf = true;
		for (RelationshipSide side : relationship.getSides()) {
			EntitySet neighbour = side.getEntitySet();
			if (skipSelf && this.equals(neighbour)) {
				skipSelf = false;	// skip this entitySet only once
				continue;
			}

			getNeighbours().get(neighbour).remove(relationship);
			
			if (getNeighbours().get(neighbour).isEmpty()) {
				getNeighbours().remove(neighbour);
			}
		}
	}

	public void removeNeighbour(EntitySet neighbour, Relationship relationship) {
		Utils.validateNotNull(neighbour);
		Utils.validateNotNull(relationship);
		Utils.validateContains(relationship, this);

		getNeighbours().get(neighbour).remove(relationship);
		if (getNeighbours().get(neighbour).isEmpty()) {
			getNeighbours().remove(neighbour);
		}
	}

	public void addNeighbours(Relationship relationship) {
		Utils.validateContains(relationship, this);

		boolean skipSelf = true; 
		for (RelationshipSide side : relationship.getSides()) {
			EntitySet neighbour = side.getEntitySet();
			if (skipSelf && this.equals(neighbour)) {
				skipSelf = false;	// skip this entitySet only once 
				continue;
			}
			if (!getNeighbours().containsKey(neighbour)) {
				getNeighbours().put(neighbour, new ArrayList<Relationship>());
			}
			getNeighbours().get(neighbour).add(relationship);
		}
	}

	public void addNeighbour(EntitySet neighbour, Relationship relationship) {
		Utils.validateNotNull(neighbour);
		Utils.validateNotNull(relationship);

		if (getNeighbours().get(neighbour) == null) {
			getNeighbours().put(neighbour, new ArrayList<>());
		}
		getNeighbours().get(neighbour).add(relationship);
	}

	public void addAttribute(Attribute attribute) {
		Utils.validateNotNull(attribute);

		getAttributes().add(attribute);
	}

	public void removeAttribute(Attribute attribute) {
		Utils.validateNotNull(attribute);
		Utils.validateContains(this, attribute);

		getAttributes().remove(attribute);
	}

	public List<Relationship> getIncidentRelationships() {
		List<Relationship> result = new ArrayList<Relationship>();
		for (EntitySet neighbour : getNeighbours().keySet()) {
			for (Relationship rel : getNeighbours().get(neighbour))
				if (!result.contains(rel)) result.add(rel);
		}
		return result;
	}

	public Double getPriority() {
		return priority;
	}

	public void setPriority(Double priority) {
		this.priority = priority;
	}
	
	public void resetPriority() {
		this.priority = null;
	}
	
	public EntitySet getFirstNeighbour() {
		Utils.validateBinary(this);
		if (firstNeigbour == null) {
			Iterator<List<Relationship>> it = getNeighbours().values().iterator();
			firstNeigbour = RelationshipUtils.getOtherEntitySet(it.next().get(0), this);
		}
		return firstNeigbour;
	}
	
	public EntitySet getSecondNeighbour() {
		Utils.validateBinary(this);
		if (secondNeigbour == null) {
			Iterator<List<Relationship>> it = getNeighbours().values().iterator();
			it.next();
			secondNeigbour = RelationshipUtils.getOtherEntitySet(it.next().get(0), this);
		}
		return secondNeigbour;
	}
	
	public boolean isBinary() {
		if (getNeighbours().size() != 2) {
			return false;
		}
		if (getNeighbours().values().stream().anyMatch(list -> list.size() != 1)) {
			return false;
		}
		List<Relationship> incidentRelationships = getIncidentRelationships();
		if (incidentRelationships.size() != 2) {
			return false;
		}
		if (incidentRelationships.stream().anyMatch(rel -> !rel.isBinary())) {
			return false;
		}
		return true;
	}
	
	public boolean containsAttribute(String attribute) {
		if (attribute == null) {
			return false;
		}
		return getAttributes().stream().anyMatch(attr -> StringUtils.areEqual(attribute, attr.getText()));
	}
}