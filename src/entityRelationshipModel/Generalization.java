package entityRelationshipModel;

import java.util.ArrayList;
import java.util.List;

import common.enums.Enums;

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
	private List<GeneralizationSide> sides;

	public Generalization() {
		this.sides = new ArrayList<GeneralizationSide>(2);
	};

	public Generalization(List<GeneralizationSide> sides) {
		this.sides = sides;
	}

	public Generalization(EntitySet superEntitySet, EntitySet subEntitySet) {
		sides = new ArrayList<GeneralizationSide>(2);
		sides.add(0, new GeneralizationSide(superEntitySet, Enums.ROLE_SUPER));
		sides.add(1, new GeneralizationSide(subEntitySet, Enums.ROLE_SUB));
	}

	public Generalization(Generalization generalization) {
		sides = new ArrayList<GeneralizationSide>(generalization.getSides());
	}

	public List<GeneralizationSide> getSides() {
		return sides;
	}

	public EntitySet getSuperEntitySet() {
		return getSides().get(0).getEntitySet();
	}

	public EntitySet getSubEntitySet() {
		return getSides().get(1).getEntitySet();
	}

	@Override
	public boolean isBinary() {
		return true;
	}
}
