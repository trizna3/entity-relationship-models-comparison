package sk.trizna.erm_comparison.common.key_config;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Set;

import sk.trizna.erm_comparison.common.Utils;

public abstract class KeyConfigManager {

	private boolean resourceLoaded = false;
	
	protected abstract String getConfigFileName();
	protected abstract String getResourceInternal(String resourceKey);
	protected abstract void loadValues(Properties prop);
	protected abstract Set<String> keySetInternal();
	
	public Set<String> keySet() {
		if (!resourceLoaded) {
			loadConfig();
		}
		return keySetInternal();
	}
	
	public String getResource(String resource) {
		Utils.validateNotNull(resource);
		
		if (!resourceLoaded) {
			loadConfig();
		}
		
		return getResourceInternal(resource);
	}
	
	private void loadConfig() {
		// open configuration file
		Properties prop = createConnection();
		if (prop == null) {
			return;
		}
		
		// load value from configuration file		
		loadValues(prop);
		resourceLoaded = true;
	}
	
	private Properties createConnection() {
		try {
			Properties prop = new Properties();
			InputStream is = new FileInputStream(getConfigFileName());
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
