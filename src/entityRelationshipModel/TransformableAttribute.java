package entityRelationshipModel;

import transformations.Transformable;

/**
 * Attribute for use of transformation.
 * 
 * @author Adam Trizna
 *
 */
public class TransformableAttribute extends Transformable {

	private String attribute;

	public TransformableAttribute(String attribute) {
		this.attribute = attribute;
	}

	public String getAttribute() {
		return attribute;
	}

	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}
}
