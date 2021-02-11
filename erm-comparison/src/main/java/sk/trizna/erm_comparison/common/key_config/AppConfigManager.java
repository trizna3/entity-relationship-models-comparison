package sk.trizna.erm_comparison.common.key_config;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import sk.trizna.erm_comparison.common.enums.EnumConstants;

public class AppConfigManager extends KeyConfigManager {
	
	private static AppConfigManager INSTANCE;
	private static Map<String,String> valueMap = new HashMap<>();
	
	public static AppConfigManager getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new AppConfigManager();
		}
		return INSTANCE;
	}
	
	@Override
	protected String getConfigFileName() {
		return EnumConstants.APP_CONFIG_NAME;
	}
	
	@Override
	protected String getResourceInternal(String resourceKey) {
		if (valueMap.containsKey(resourceKey)) {
			return valueMap.get(resourceKey);
		}
		throw new IllegalArgumentException("Unknown resource!");
	}

	@Override
	protected void loadValues(Properties prop) {
		valueMap.put(EnumConstants.CONFIG_PRINT_RESULT, prop.getProperty(EnumConstants.CONFIG_PRINT_RESULT));
		valueMap.put(EnumConstants.CONFIG_PRINT_TRANSFORMATION_PROGRESS, prop.getProperty(EnumConstants.CONFIG_PRINT_TRANSFORMATION_PROGRESS));
		valueMap.put(EnumConstants.CONFIG_TRACK_PROGRESS, prop.getProperty(EnumConstants.CONFIG_TRACK_PROGRESS));
		valueMap.put(EnumConstants.CONFIG_EARLY_STOP, prop.getProperty(EnumConstants.CONFIG_EARLY_STOP));
		valueMap.put(EnumConstants.CONFIG_EARLY_STOP_BOUND, prop.getProperty(EnumConstants.CONFIG_EARLY_STOP_BOUND));
		valueMap.put(EnumConstants.CONFIG_LANGUAGE_PROCESSOR, prop.getProperty(EnumConstants.CONFIG_LANGUAGE_PROCESSOR));
	}

	@Override
	protected Set<String> keySetInternal() {
		return valueMap.keySet();
	}
}
