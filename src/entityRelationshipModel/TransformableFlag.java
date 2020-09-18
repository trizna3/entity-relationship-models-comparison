package entityRelationshipModel;

import common.Utils;
import transformations.Transformable;

public class TransformableFlag extends Transformable {

	public TransformableFlag() {
	}

	public TransformableFlag(String role) {
		Utils.validateNotNull(role);

		setTransformationRole(role);
	}
}
