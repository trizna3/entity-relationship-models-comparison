package entityRelationshipModel;

import java.util.Comparator;

import common.PrintUtils;
import common.StringUtils;
import transformations.Transformable;

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

	public String getAttribute() {
		return attribute;
	}

	public void setAttribute(String attribute) {
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
				return o1.getAttribute().compareTo(o2.getAttribute());
			}
		}; 
	}

	@Override
	public String getText() {
		return attribute;
	}
}
