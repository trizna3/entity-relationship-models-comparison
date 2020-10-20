package transformations;

import java.util.HashSet;
import java.util.Set;

import common.enums.EnumTransformation;

public abstract class Transformable {

	private Set<EnumTransformation> transformationFlags;

	public Set<EnumTransformation> getTransformationFlags() {
		if (transformationFlags == null) {
			transformationFlags = new HashSet<>();
		}
		return transformationFlags;
	}

	public void setTransformationFlags(Set<EnumTransformation> transformationFlags) {
		this.transformationFlags = transformationFlags;
	}

	public void addTransformationFlag(EnumTransformation flag) {
		getTransformationFlags().add(flag);
	}

	public void removeTransformationFlag(EnumTransformation flag) {
		getTransformationFlags().remove(flag);
	}

	public boolean containsTransformationFlag(EnumTransformation flag) {
		return getTransformationFlags().contains(flag);
	}

}
