package entityRelationshipModel;

import common.PrintUtils;

/**
 * @author - Adam Trizna
 */

/**
 * Base element of entity relationship model. Entails a set of entities with
 * common properties (attributes).
 */
public class EntitySet {

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
}