package entityRelationshipModel;

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
	private String[] attributes;

	public Association(AssociationSide[] sides, String[] attributes) {
		this.sides = sides;
		this.attributes = attributes;
	}

	public AssociationSide[] getSides() {
		return sides;
	}

	public String[] getAttributes() {
		return attributes;
	}

	@Override
	public boolean isBinary() {
		return sides.length == 2;
	}

	@Override
	protected AssociationSide getFirst() {
		return sides[0];
	}

	@Override
	protected AssociationSide getSecond() {
		return sides[1];
	}
}
