package common.config;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import common.Utils;
import common.enums.EnumConstants;

public class ConfigManager {
	
	private static String PRINT_RESULT;
	private static String PRINT_TRANSFORMATION_PROGRESS;
	private static String TRACK_PROGRESS;
	private static String EARLY_STOP;
	private static String EARLY_STOP_BOUND;
	
	private static boolean resourceLoaded = false;
	
	
	public static Object getResource(String resource) {
		Utils.validateNotNull(resource);
		
		if (!resourceLoaded) {
			loadConfig();
		}
		
		switch(resource) {
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
	
	private static void loadConfig() {
		// open configuration file
		Properties prop = createConnection();
		if (prop == null) {
			return;
		}
		
		// load resources
		PRINT_RESULT = prop.getProperty(EnumConstants.CONFIG_PRINT_RESULT);
		PRINT_TRANSFORMATION_PROGRESS = prop.getProperty(EnumConstants.CONFIG_PRINT_TRANSFORMATION_PROGRESS);
		TRACK_PROGRESS = prop.getProperty(EnumConstants.CONFIG_TRACK_PROGRESS);
		EARLY_STOP = prop.getProperty(EnumConstants.CONFIG_EARLY_STOP);
		EARLY_STOP_BOUND = prop.getProperty(EnumConstants.CONFIG_EARLY_STOP_BOUND); 
		
		resourceLoaded = true;
	}
	
	private static Properties createConnection() {
		try {
			Properties prop = new Properties();
			InputStream is = new FileInputStream(EnumConstants.CONFIG_NAME);
		    prop.load(is);
		    is.close();
		    return prop;
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
			return null;
		} catch (IOException ex) {
			ex.printStackTrace();
			return null;
		}
	}
}
