package entityRelationshipModel;

import java.util.Collections;
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
		Collections.sort(this.getAttributes(), Attribute.getAttributeComparator());
		Collections.sort(other.getAttributes(), Attribute.getAttributeComparator());
		return this.getAttributes().equals(other.getAttributes());
	}

}
