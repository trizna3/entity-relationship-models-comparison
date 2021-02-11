package sk.trizna.erm_comparison.common.key_config;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import sk.trizna.erm_comparison.common.Utils;
import sk.trizna.erm_comparison.common.enums.EnumConstants;

public class AttributeCompositionManager extends KeyConfigManager {
	
	private static AttributeCompositionManager INSTANCE;
	
	private static Map<String,String> valueMap = new HashMap<String, String>();
	
	public static AttributeCompositionManager getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new AttributeCompositionManager();
		}
		return INSTANCE;
	}
	
	@Override
	protected String getConfigFileName() {
		return EnumConstants.ATTRIBUTE_COMPOSITION_NAME;
	}
	
	@Override
	protected String getResourceInternal(String resourceKey) {
		Utils.validateNotNull(resourceKey);
		
		String value = valueMap.get(resourceKey);
		if (value != null) {
			return value;
		}
		throw new IllegalArgumentException("Unknown resource!");
	}

	@Override
	protected void loadValues(Properties prop) {
		Utils.validateNotNull(prop);
		
		for (Object key : prop.keySet()) {
			valueMap.put(key.toString(), prop.get(key).toString());
		}
	}

	@Override
	protected Set<String> keySetInternal() {
		return valueMap.keySet();
	}
}
