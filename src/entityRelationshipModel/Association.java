package entityRelationshipModel;

import java.util.ArrayList;
import java.util.List;

/**
 * @author - Adam Trizna
 */

/**
 * Type of relationship: Association. Joins 2 or more entity sets, each with
 * specific cardinality.
 * 
 * @see AssociationSide
 */
public class Association extends Relationship implements Attributed {

	/**
	 * List of association sides.
	 */
	private List<AssociationSide> sides;
	/**
	 * Attributes list.
	 */
	private List<Attribute> attributes;

	public Association() {
	};

	public Association(List<AssociationSide> sides) {
		setSides(sides);
	}

	public Association(String name, List<AssociationSide> sides, List<String> attributes) {
		setName(name);
		setSides(sides);
		if (attributes != null) {
			for (String attribute : attributes) {
				getAttributes().add(new Attribute(attribute));
			}
		}
	}

	public Association(List<AssociationSide> sides, List<String> attributes) {
		setSides(sides);
		if (attributes != null) {
			for (String attribute : attributes) {
				getAttributes().add(new Attribute(attribute));
			}
		}
	}

	public Association(Association association) {
		setSides(new ArrayList<AssociationSide>(association.getSides().size()));
		association.getSides().stream().forEach(side -> getSides().add(new AssociationSide(side)));

		this.attributes = new ArrayList<>(association.getAttributes());
	};

	public List<AssociationSide> getSides() {
		return sides;
	}

	public List<Attribute> getAttributes() {
		if (attributes == null) {
			attributes = new ArrayList<>();
		}
		return attributes;
	}

	public void setAttributes(List<Attribute> attributes) {
		this.attributes = attributes;
	}

	public void addAttribute(Attribute attribute) {
		getAttributes().add(attribute);
	}

	public void removeAttribute(Attribute attribute) {
		getAttributes().remove(attribute);
	}

	@Override
	public boolean isBinary() {
		return getSides().size() == 2;
	}

	public void setSides(List<AssociationSide> sides) {
		this.sides = sides;
	}
}
