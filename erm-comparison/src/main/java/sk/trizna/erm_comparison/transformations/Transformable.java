package sk.trizna.erm_comparison.transformations;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import sk.trizna.erm_comparison.common.enums.EnumTransformation;
import sk.trizna.erm_comparison.entity_relationship_model.TransformableList;

public abstract class Transformable {

	private Map<EnumTransformation,TransformableList> transformationFlags;

	public Map<EnumTransformation,TransformableList> getTransformationFlags() {
		if (transformationFlags == null) {
			transformationFlags = new HashMap<EnumTransformation, TransformableList>();
		}
		return transformationFlags;
	}

	public void addTransformationFlag(EnumTransformation flag) {
		getTransformationFlags().put(flag,null);
	}
	
	public void addTransformationFlag(EnumTransformation flag, TransformableList list) {
		getTransformationFlags().put(flag,list);
	}

	public void removeTransformationFlag(EnumTransformation flag) {
		getTransformationFlags().remove(flag);
	}
	
	public TransformableList getTransformationData(EnumTransformation flag) {
		return getTransformationFlags().get(flag);
	}

	public boolean containsTransformationFlag(EnumTransformation flag) {
		return getTransformationFlags().containsKey(flag);
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
