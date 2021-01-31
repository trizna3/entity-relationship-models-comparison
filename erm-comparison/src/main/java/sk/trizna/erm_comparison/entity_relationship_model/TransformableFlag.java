package sk.trizna.erm_comparison.entity_relationship_model;

import sk.trizna.erm_comparison.transformations.Transformable;

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
