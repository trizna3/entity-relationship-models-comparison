package entityRelationshipModel;

import transformations.Transformable;

public class TransformableFlag extends Transformable {

	public TransformableFlag() {
	}

	@Override
	public boolean equals(Object obj) {
		return (obj instanceof TransformableFlag);
	}

	@Override
	public int hashCode() {
		return 5171;
	}
}
