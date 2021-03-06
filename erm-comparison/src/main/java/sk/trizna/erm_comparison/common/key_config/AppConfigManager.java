package sk.trizna.erm_comparison.common.key_config;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import sk.trizna.erm_comparison.common.enums.EnumConstants;

public class AppConfigManager extends KeyConfigManager {
	
	private static AppConfigManager INSTANCE;
	private Map<String,String> valueMap = new HashMap<>();
	
	public static AppConfigManager getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new AppConfigManager();
		}
		return INSTANCE;
	}
	
	@SuppressWarnings("unchecked")
	public String getResource(String resource) {
		preGetResource(resource);
		
		if (valueMap.containsKey(resource)) {
			return valueMap.get(resource);
		}
		throw new IllegalArgumentException("Unknown resource!");
	}
	
	@Override
	protected String getConfigFileName() {
		return EnumConstants.APP_CONFIG_NAME;
	}
	
	@Override
	protected void loadValues(Properties prop) {
		valueMap.put(EnumConstants.CONFIG_PRINT_RESULT, prop.getProperty(EnumConstants.CONFIG_PRINT_RESULT));
		valueMap.put(EnumConstants.CONFIG_EARLY_STOP, prop.getProperty(EnumConstants.CONFIG_EARLY_STOP));
		valueMap.put(EnumConstants.CONFIG_EARLY_STOP_BOUND, prop.getProperty(EnumConstants.CONFIG_EARLY_STOP_BOUND));
		valueMap.put(EnumConstants.CONFIG_LANGUAGE_PROCESSOR, prop.getProperty(EnumConstants.CONFIG_LANGUAGE_PROCESSOR));
		valueMap.put(EnumConstants.CONFIG_VALIDATE_ROLES, prop.getProperty(EnumConstants.CONFIG_VALIDATE_ROLES));
		valueMap.put(EnumConstants.CONFIG_CONDITIONAL_TRANSFORMATION, prop.getProperty(EnumConstants.CONFIG_CONDITIONAL_TRANSFORMATION));
		valueMap.put(EnumConstants.CONFIG_BUGFIX_MODE, prop.getProperty(EnumConstants.CONFIG_BUGFIX_MODE));
	}

	@Override
	protected Set<String> keySetInternal() {
		return valueMap.keySet();
	}
}
