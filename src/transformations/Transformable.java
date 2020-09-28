package transformations;

import java.util.HashSet;
import java.util.Set;

public abstract class Transformable {

	private Set<String> transformationFlags;

	public Set<String> getTransformationFlags() {
		if (transformationFlags == null) {
			transformationFlags = new HashSet<>();
		}
		return transformationFlags;
	}

	public void setTransformationFlags(Set<String> transformationFlags) {
		this.transformationFlags = transformationFlags;
	}

	public void addTransformationFlag(String flag) {
		getTransformationFlags().add(flag);
	}

	public void removeTransformationFlag(String flag) {
		getTransformationFlags().remove(flag);
	}

	public boolean containsTransformationFlag(String flag) {
		return getTransformationFlags().contains(flag);
	}

}
