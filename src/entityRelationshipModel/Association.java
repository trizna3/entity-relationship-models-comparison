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
	private AssociationSide[] sides;
	/**
	 * Attributes list.
	 */
	private List<Attribute> attributes;

	public Association() {
	};

	public Association(AssociationSide[] sides) {
		setSides(sides);
	}

	public Association(String name, AssociationSide[] sides, List<String> attributes) {
		setName(name);
		setSides(sides);
		if (attributes != null) {
			for (String attribute : attributes) {
				getAttributes().add(new Attribute(attribute));
			}
		}
	}

	public Association(AssociationSide[] sides, List<String> attributes) {
		setSides(sides);
		if (attributes != null) {
			for (String attribute : attributes) {
				getAttributes().add(new Attribute(attribute));
			}
		}
	}

	public Association(Association association) {
		setSides(new AssociationSide[association.getSides().length]);
		for (int i = 0; i < sides.length; i++) {
			getSides()[i] = new AssociationSide(association.getSides()[i]);
		}
		this.attributes = new ArrayList<>(association.getAttributes());
	};

	public AssociationSide[] getSides() {
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
		return getSides().length == 2;
	}

	@Override
	protected AssociationSide getFirst() {
		return getSides()[0];
	}

	@Override
	protected AssociationSide getSecond() {
		return getSides()[1];
	}

	public void setSides(AssociationSide[] sides) {
		this.sides = sides;
	}
}
