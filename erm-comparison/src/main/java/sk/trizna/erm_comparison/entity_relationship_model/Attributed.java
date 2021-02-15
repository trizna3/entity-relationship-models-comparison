package sk.trizna.erm_comparison.entity_relationship_model;

import java.util.List;

import sk.trizna.erm_comparison.common.utils.StringUtils;
import sk.trizna.erm_comparison.common.utils.Utils;

public interface Attributed {

	List<Attribute> getAttributes();
	
	default Attribute getAttribute(String string) {
		Utils.validateNotNull(string);
		
		for (Attribute attribute : getAttributes()) {
			if (StringUtils.areEqual(attribute.getText(), string)) {
				return attribute;
			}
		}
		
		return null;
	}
	
	default int getAttributesCount() {
		return getAttributes() == null ? 0 : getAttributes().size();
	}

}
