package sk.trizna.erm_comparison.common.key_config;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import sk.trizna.erm_comparison.common.Utils;
import sk.trizna.erm_comparison.common.enums.EnumConstants;

public class TranslationManager extends KeyConfigManager {
	
	private static TranslationManager INSTANCE;
	
	private static Map<String,String> valueMap = new HashMap<String, String>(); 
	
	public static TranslationManager getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new TranslationManager();
		}
		return INSTANCE;
	}
	
	@Override
	protected String getConfigFileName() {
		return EnumConstants.TRANSLATION_NAME;
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
}
