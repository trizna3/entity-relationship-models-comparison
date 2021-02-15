package sk.trizna.erm_comparison.entity_relationship_model;

import java.util.Comparator;

import sk.trizna.erm_comparison.common.utils.PrintUtils;
import sk.trizna.erm_comparison.common.utils.StringUtils;
import sk.trizna.erm_comparison.transformations.Transformable;

/**
 * Attribute for use of transformation.
 * 
 * @author Adam Trizna
 *
 */
public class Attribute extends Transformable implements ERText {

	private String attribute;

	public Attribute(String attribute) {
		this.attribute = attribute;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof ERText)) {
			return false;
		}
		return StringUtils.areEqual(this.getText(), ((ERText) obj).getText());
	}
	
	@Override
	public int hashCode() {
		return StringUtils.computeStringHash(attribute);
	}

	@Override
	public String toString() {
		return PrintUtils.print(this);
	}
	
	public static Comparator<Attribute> getAttributeComparator() {
		return new Comparator<Attribute>() {
			@Override
			public int compare(Attribute o1, Attribute o2) {
				if (o1 == null && o2 == null) return 0;
				if (o1 == null) return 1;
				if (o2 == null) return -1;
				return o1.getText().compareTo(o2.getText());
			}
		}; 
	}

	@Override
	public String getText() {
		return attribute;
	}
}
