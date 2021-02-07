package sk.trizna.erm_comparison.entity_relationship_model;

import java.util.ArrayList;
import java.util.List;

import sk.trizna.erm_comparison.common.StringUtils;
import sk.trizna.erm_comparison.common.enums.EnumRelationshipSideRole;

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
		super(null);
		this.sides = new ArrayList<GeneralizationSide>(2);
	};

	public Generalization(List<GeneralizationSide> sides) {
		super(null);
		this.sides = sides;
	}

	public Generalization(EntitySet superEntitySet, EntitySet subEntitySet) {
		super(null);
		sides = new ArrayList<GeneralizationSide>(2);
		sides.add(0, new GeneralizationSide(superEntitySet, EnumRelationshipSideRole.ROLE_SUPER));
		sides.add(1, new GeneralizationSide(subEntitySet, EnumRelationshipSideRole.ROLE_SUB));
	}

	public Generalization(Generalization generalization) {
		super(null);
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
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Generalization)) {
			return false;
		}
		Generalization other = (Generalization) obj;
		
		return StringUtils.areEqual(getNameText(), other.getNameText()) && 
				getSuperEntitySet().equals(other.getSuperEntitySet()) && 
				getSubEntitySet().equals(other.getSubEntitySet());
	}
}
