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

	public boolean isBinary() {
		return sides.length == 2;
	}

	public AssociationSide getFirstSide() {
		checkIsBinary();
		return sides[0];
	}

	public AssociationSide getSecondSide() {
		checkIsBinary();
		return sides[1];
	}

	/**
	 * checks if association is binary. if not, throws an exception.
	 */
	private void checkIsBinary() {
		if (!isBinary()) {
			throw new IllegalStateException("Association is not binary!");
		}
	}
}
