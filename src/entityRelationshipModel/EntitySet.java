package entityRelationshipModel;

import java.util.ArrayList;
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
	private String[] attributes;

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
		this.attributes = new String[] {};
	}

	public EntitySet(String name, String[] attributes) {
		this.name = name;
		this.attributes = attributes;
	}

	public String getName() {
		return name;
	}

	public String[] getAttributes() {
		return attributes;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setAttributes(String[] attributes) {
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

	public List<Relationship> getRelationshipsByNeighbour(EntitySet neighbour) {
		return neighbours.get(neighbour);
	}

	public void removeNeighbours(Relationship relationship) {
		Utils.validateContains(relationship, this);

		for (RelationshipSide side : relationship.getSides()) {
			EntitySet neighbour = side.getEntitySet();
			if (this.equals(neighbour)) {
				continue;
			}
			neighbours.get(neighbour).remove(relationship);
			if (neighbours.get(neighbour).isEmpty()) {
				neighbours.remove(neighbour);
			}
		}
	}

	public void addNeighbours(Relationship relationship) {
		Utils.validateContains(relationship, this);

		for (RelationshipSide side : relationship.getSides()) {
			EntitySet neighbour = side.getEntitySet();
			if (this.equals(neighbour)) {
				continue;
			}
			if (!neighbours.containsKey(neighbour)) {
				neighbours.put(neighbour, new ArrayList<Relationship>());
			}
			neighbours.get(neighbour).add(relationship);
		}
	}
}