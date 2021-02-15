package sk.trizna.erm_comparison.entity_relationship_model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import sk.trizna.erm_comparison.transformations.Transformable;

public class TransformableList extends Transformable implements Iterable<Transformable>{
	
	private List<Transformable> elements;
	
	public TransformableList() {
	}
	
	public TransformableList(List<Transformable> elements) {
		this.elements = elements;
	}

	public List<Transformable> getElements() {
		if (elements == null) {
			elements = new ArrayList<>();
		}
		return elements;
	}

	public void setElements(List<Transformable> elements) {
		this.elements = elements;
	}
	
	public int size() {
		return getElements().size();
	}
	
	public boolean isEmpty() {
		return getElements().isEmpty();
	}

	@Override
	public Iterator<Transformable> iterator() {
		return getElements().iterator();
	}
}
