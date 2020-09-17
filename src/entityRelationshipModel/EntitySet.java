package entityRelationshipModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import common.PrintUtils;
import common.Utils;
import transformations.Transformable;

/**
 * @author - Adam Trizna
 */

/**
 * Base element of entity relationship model. Entails a set of entities with
 * common properties (attributes).
 */
public class EntitySet extends Transformable {

	/**
	 * Entity set name.
	 */
	private String name;
	/**
	 * List of entity set's attributes.
	 */
	private List<String> attributes;

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

	public EntitySet(String name) {
		this.name = name;
		this.attributes = new ArrayList<String>();
	}

	public EntitySet(String name, List<String> attributes) {
		this.name = name;
		this.attributes = attributes;
	}
	
	public EntitySet(EntitySet other) {
		this.name = other.getName();
		this.attributes = new ArrayList<>(other.getAttributes());
		this.empty = other.isEmpty();
		this.mappedTo = other.getMappedTo();
		this.neighbours = new HashMap<>(other.getNeighbours());
	}

	public String getName() {
		return name;
	}

	public List<String> getAttributes() {
		if (attributes == null) {
			attributes = new ArrayList<String>();
		}
		return attributes;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setAttributes(List<String> attributes) {
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

		for (RelationshipSide side : relationship.getSides()) {
			EntitySet neighbour = side.getEntitySet();
			if (this.equals(neighbour)) {
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

		for (RelationshipSide side : relationship.getSides()) {
			EntitySet neighbour = side.getEntitySet();
			if (this.equals(neighbour)) {
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

	public void addAttribute(String attribute) {
		Utils.validateNotNull(attribute);

		getAttributes().add(attribute);
	}

	public void removeAttribute(String attribute) {
		Utils.validateNotNull(attribute);
		Utils.validateContains(this, attribute);

		getAttributes().remove(attribute);
	}

	public List<Relationship> getIncidentRelationships() {
		List<Relationship> result = new ArrayList<>();
		for (EntitySet neighbour : getNeighbours().keySet()) {
			result.addAll(getNeighbours().get(neighbour));
		}
		return result;
	}
}