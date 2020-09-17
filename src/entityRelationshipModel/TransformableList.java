package entityRelationshipModel;

import java.util.ArrayList;
import java.util.List;

import transformations.Transformable;

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
	
	
}
