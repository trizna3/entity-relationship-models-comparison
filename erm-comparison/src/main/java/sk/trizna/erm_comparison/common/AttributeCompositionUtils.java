package sk.trizna.erm_comparison.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import sk.trizna.erm_comparison.common.key_config.AttributeCompositionManager;

public class AttributeCompositionUtils {
	
	private static final AttributeCompositionManager ATTRIBUTE_COMPOSITION_MANAGER = AttributeCompositionManager.getInstance();
	private static Map<String,List<String>> cache = new HashMap<String,List<String>>();
	
	public static Set<String> getAllComposedAttributes() {
		return ATTRIBUTE_COMPOSITION_MANAGER.keySet();
	}
	
	public static List<String> getAttributeParts(String composedAttribute) {
		Utils.validateNotNull(composedAttribute);
		
		if (cache.containsKey(composedAttribute)) {
			return cache.get(composedAttribute);
		}
		
		List<String> attributeParts = ATTRIBUTE_COMPOSITION_MANAGER.getResource(composedAttribute);
		cache.put(composedAttribute, attributeParts);
		return attributeParts;
	}
}