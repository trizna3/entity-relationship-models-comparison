package sk.trizna.erm_comparison.entity_relationship_model;

import java.util.HashMap;
import java.util.Map;

import sk.trizna.erm_comparison.transformations.Transformable;

public class TransformableMap extends Transformable {

	private Map<Transformable,Transformable> map;

	public Map<Transformable,Transformable> getMap() {
		if (map == null) {
			map = new HashMap<Transformable, Transformable>();
		}
		return map;
	}

	public void setMap(Map<Transformable,Transformable> map) {
		this.map = map;
	}
}
