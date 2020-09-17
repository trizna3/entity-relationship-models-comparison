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
public class Association extends Relationship {

	/**
	 * List of association sides.
	 */
	private AssociationSide[] sides;
	/**
	 * Attributes list.
	 */
	private List<String> attributes;

	public Association() {};
	
	public Association(AssociationSide[] sides, List<String> attributes) {
		this.setSides(sides);
		this.attributes = attributes;
	}
	
	public AssociationSide[] getSides() {
		return sides;
	}

	public List<String> getAttributes() {
		if (attributes == null) {
			attributes = new ArrayList<>();
		}
		return attributes;
	}
	
	public void setAttributes(List<String> attributes) {
		this.attributes = attributes;
	}

	public void addAttribute(String attribute) {
		getAttributes().add(attribute);
	}

	public void removeAttribute(String attribute) {
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
