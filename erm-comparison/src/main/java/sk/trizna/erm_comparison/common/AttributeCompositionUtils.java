package sk.trizna.erm_comparison.common;

import java.util.List;
import java.util.Set;

import sk.trizna.erm_comparison.common.key_config.AttributeCompositionManager;

public class AttributeCompositionUtils extends Utils {
	
	private static final AttributeCompositionManager ATTRIBUTE_COMPOSITION_MANAGER = AttributeCompositionManager.getInstance();
	
	public static Set<String> getAllComposedAttributes() {
		return ATTRIBUTE_COMPOSITION_MANAGER.keySet();
	}
	
	public static List<String> getAttributeParts(String composedAttribute) {
		Utils.validateNotNull(composedAttribute);
		
		return ATTRIBUTE_COMPOSITION_MANAGER.getResource(composedAttribute);
	}
}