package transformations;

import java.util.Comparator;
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
	
	public static Comparator<Transformable> getComparator() {
		return new Comparator<Transformable>() {
			@Override
			public int compare(Transformable o1, Transformable o2) {
				if (o1 == null && o2 == null) return 0;
				if (o1 == null) return 1;
				if (o2 == null) return -1;
				
				return o1.equals(o2) ? 0 : 1;
			}
		}; 
	}
}
