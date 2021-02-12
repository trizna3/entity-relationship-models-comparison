package sk.trizna.erm_comparison.entity_relationship_model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import sk.trizna.erm_comparison.common.PrintUtils;
import sk.trizna.erm_comparison.common.StringUtils;

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
		super(null);
	};

	public Association(List<AssociationSide> sides) {
		super(null);
		setSides(sides);
	}

	public Association(String name, List<AssociationSide> sides, List<String> attributes) {
		super(name);
		setSides(sides);
		if (attributes != null) {
			for (String attribute : attributes) {
				getAttributes().add(new Attribute(attribute));
			}
		}
	}

	public Association(List<AssociationSide> sides, List<String> attributes) {
		super(null);
		setSides(sides);
		if (attributes != null) {
			for (String attribute : attributes) {
				getAttributes().add(new Attribute(attribute));
			}
		}
	}

	public Association(Association association) {
		super(association.getNameText());
		setSides(new ArrayList<AssociationSide>(association.getSides().size()));
		association.getSides().stream().forEach(side -> getSides().add(new AssociationSide(side)));

		this.attributes = new ArrayList<>(association.getAttributes());
	};
	
	public Association(Association association, Map<EntitySet,EntitySet> entitySetMap) {
		super(association.getNameText());
		setSides(new ArrayList<AssociationSide>(association.getSides().size()));
		association.getSides().stream().forEach(side -> {
			getSides().add(new AssociationSide(entitySetMap.get(side.getEntitySet()),side.getRole()));
		});

		this.attributes = new ArrayList<>(association.getAttributes());
	};

	public List<AssociationSide> getSides() {
		if (sides == null) {
			sides = new ArrayList<>();
		}
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

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Association)) {
			return false;
		}
		Association other = (Association) obj;
		
		if (!StringUtils.areEqual(getNameText(), other.getNameText())) {
			return false;
		}
		
		return sidesAreEqual(other) && attributesAreEqual(other);
	}
	
	@Override
	public String getNameText() {
		String nameText = super.getNameText();
		return nameText != null ? nameText : PrintUtils.getNameByIncidentEntitySets(this);
	}
	
	private boolean sidesAreEqual(Association other) {
		if (getSides() == null && other.getSides() == null) {
			return true;
		}
		if (getSides() == null || other.getSides() == null) {
			return false;
		} 
		if (getSides().size() != other.getSides().size()) {
			return false;
		}
		
		return getSides().containsAll(other.getSides());
	}
}
