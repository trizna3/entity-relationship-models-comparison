package entityRelationshipModel;

import common.Enums;

/**
 * @author - Adam Trizna
 */

/**
 * Relationship type: Generalization. Describes a binary relationship of "super"
 * entity set and "sub" entity set. Denotes the "sub" ES being a specific kind
 * of the "super" ES ("super" is a generalization of "sub"). Equivalent to an
 * Association with cardinalities 1:1.
 * 
 * @see GeneralizationSide
 */
public class Generalization extends Relationship {

	/**
	 * List of generalization sides.
	 */
	private GeneralizationSide[] sides;

	public Generalization(GeneralizationSide[] sides) {
		this.sides = sides;
	}

	public Generalization(EntitySet superEntitySet, EntitySet subEntitySet) {
		sides = new GeneralizationSide[2];
		sides[0] = new GeneralizationSide(superEntitySet, Enums.ROLE_SUPER);
		sides[1] = new GeneralizationSide(subEntitySet, Enums.ROLE_SUB);
	}

	public GeneralizationSide[] getSides() {
		return sides;
	}

	public EntitySet getSuperEntitySet() {
		return sides[0].getEntitySet();
	}

	public EntitySet getSubEntitySet() {
		return sides[1].getEntitySet();
	}

	@Override
	public boolean isBinary() {
		return true;
	}

	@Override
	protected GeneralizationSide getFirst() {
		return sides[0];
	}

	@Override
	protected GeneralizationSide getSecond() {
		return sides[1];
	}
}
