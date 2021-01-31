package sk.trizna.erm_comparison.entity_relationship_model;

import java.util.ArrayList;
import java.util.List;

import sk.trizna.erm_comparison.transformations.Transformable;

public class TransformableList extends Transformable {
	
	private List<Transformable> elements;

	public List<Transformable> getElements() {
		if (elements == null) {
			elements = new ArrayList<>();
		}
		return elements;
	}

	public void setElements(List<Transformable> elements) {
		this.elements = elements;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof TransformableList)) {
			return false;
		}
		TransformableList other = (TransformableList) obj;
		
		return getElements().containsAll(other.getElements());
	}
}
