package common.keyConfig;

import java.util.Properties;

import common.enums.EnumConstants;

public class AppConfigManager extends KeyConfigManager {
	
	private static AppConfigManager INSTANCE;
	
	private static String PRINT_RESULT;
	private static String PRINT_TRANSFORMATION_PROGRESS;
	private static String TRACK_PROGRESS;
	private static String EARLY_STOP;
	private static String EARLY_STOP_BOUND;
	
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
	protected Object getResourceInternal(String resourceKey) {
		switch(resourceKey) {
		case EnumConstants.CONFIG_PRINT_RESULT:
			return PRINT_RESULT;
		case EnumConstants.CONFIG_PRINT_TRANSFORMATION_PROGRESS:
			return PRINT_TRANSFORMATION_PROGRESS;
		case EnumConstants.CONFIG_TRACK_PROGRESS:
			return TRACK_PROGRESS;
		case EnumConstants.CONFIG_EARLY_STOP:
			return EARLY_STOP;
		case EnumConstants.CONFIG_EARLY_STOP_BOUND:
			return EARLY_STOP_BOUND;
		default:
			throw new IllegalArgumentException("Unknown resource!");
		}
	}

	@Override
	protected void loadValues(Properties prop) {
		PRINT_RESULT = prop.getProperty(EnumConstants.CONFIG_PRINT_RESULT);
		PRINT_TRANSFORMATION_PROGRESS = prop.getProperty(EnumConstants.CONFIG_PRINT_TRANSFORMATION_PROGRESS);
		TRACK_PROGRESS = prop.getProperty(EnumConstants.CONFIG_TRACK_PROGRESS);
		EARLY_STOP = prop.getProperty(EnumConstants.CONFIG_EARLY_STOP);
		EARLY_STOP_BOUND = prop.getProperty(EnumConstants.CONFIG_EARLY_STOP_BOUND); 
	}
}
