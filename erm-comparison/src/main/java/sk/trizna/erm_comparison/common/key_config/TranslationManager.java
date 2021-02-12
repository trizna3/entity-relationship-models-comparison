package sk.trizna.erm_comparison.common.key_config;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import sk.trizna.erm_comparison.common.Utils;
import sk.trizna.erm_comparison.common.enums.EnumConstants;

public class TranslationManager extends KeyConfigManager {
	
	private static TranslationManager INSTANCE;
	
	private Map<String,String> valueMap = new HashMap<String, String>(); 
	
	public static TranslationManager getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new TranslationManager();
		}
		return INSTANCE;
	}
	
	@SuppressWarnings("unchecked")
	public String getResource(String resource) {
		preGetResource(resource);

		String value = valueMap.get(resource);
		if (value != null) {
			return value;
		}
		throw new IllegalArgumentException("Unknown resource!");
	}
	
	@Override
	protected String getConfigFileName() {
		return EnumConstants.TRANSLATION_NAME;
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
