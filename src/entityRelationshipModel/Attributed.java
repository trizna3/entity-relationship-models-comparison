package entityRelationshipModel;

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

}
