package entityRelationshipModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import common.PrintUtils;
import common.StringUtils;
import common.Utils;

/**
 * @author - Adam Trizna
 */

/**
 * Base element of entity relationship model. Entails a set of entities with
 * common properties (attributes).
 */
public class EntitySet extends ERModelElement implements Attributed {

	/**
	 * Entity set name.
	 */
	private ERModelElementName name;
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

	public EntitySet(String name) {
		this.name = new ERModelElementName(name);
		this.attributes = new ArrayList<Attribute>();
	}

	public EntitySet(String name, List<String> attributes) {
		this.name = new ERModelElementName(name);
		if (attributes != null) {
			for (String attribute : attributes) {
				getAttributes().add(new Attribute(attribute));
			}
		}
	}

	public EntitySet(EntitySet other) {
		this.name = new ERModelElementName(other.getNameText());
		this.attributes = new ArrayList<>(other.getAttributes());
		this.empty = other.isEmpty();
		this.mappedTo = other.getMappedTo();
		this.neighbours = new HashMap<>(other.getNeighbours());
	}

	public String getNameText() {
		return name.getName();
	}
	
	public ERModelElementName getName() {
		return name;
	}

	public List<Attribute> getAttributes() {
		if (attributes == null) {
			attributes = new ArrayList<Attribute>();
		}
		return attributes;
	}

	public void setNameText(String name) {
		this.name = new ERModelElementName(name);
	}
	
	public void setName(ERModelElementName name) {
		this.name = name;
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

		for (RelationshipSide side : relationship.getSides()) {
			EntitySet neighbour = side.getEntitySet();
			if (this.equals(neighbour)) {
				continue;
			}
			
			// must remove by index, (based on reference equality), in case of multiple duplicate relationships
			int idxForRemoval = -1;
			for (int i = 0; i < getNeighbours().get(neighbour).size(); i++) {
				if (getNeighbours().get(neighbour).get(i) == relationship) {
					idxForRemoval = i;
				}
			}
			if (idxForRemoval != -1) getNeighbours().get(neighbour).remove(idxForRemoval);
			
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
		List<Relationship> result = new ArrayList<>();
		for (EntitySet neighbour : getNeighbours().keySet()) {
			result.addAll(getNeighbours().get(neighbour));
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

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof EntitySet)) {
			return false;
		}
		EntitySet other = (EntitySet) obj;
		
		if (!StringUtils.areEqual(this.getNameText(), other.getNameText())) {
			return false;
		}
		return attributesAreEqual(other);
	}	
}