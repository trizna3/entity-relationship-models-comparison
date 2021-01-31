package sk.trizna.erm_comparison.entity_relationship_model;

import java.util.List;

public interface Attributed {

	List<Attribute> getAttributes();
	
	default boolean attributesAreEqual(Attributed other) {
		if (other == null) {
			return false;
		}
		if (this.getAttributes() == null && other.getAttributes() == null) {
			return true;
		}
		if (this.getAttributes() == null || other.getAttributes() == null) {
			return false;
		}
		if (this.getAttributes().size() != other.getAttributes().size()) {
			return false;
		}
		return this.getAttributes().containsAll(other.getAttributes());
	}
	
	default int getAttributesCount() {
		return getAttributes() == null ? 0 : getAttributes().size();
	}

}
