package entityRelationshipModel;

import common.PrintUtils;
import common.StringUtils;
import transformations.Transformable;

/**
 * Attribute for use of transformation.
 * 
 * @author Adam Trizna
 *
 */
public class Attribute extends Transformable {

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
	public boolean equals(Object arg0) {
		if (!(arg0 instanceof Attribute)) {
			return false;
		}
		return StringUtils.areEqual(this.getAttribute(), ((Attribute) arg0).getAttribute());
	}

	@Override
	public String toString() {
		return PrintUtils.print(this);
	}
}
