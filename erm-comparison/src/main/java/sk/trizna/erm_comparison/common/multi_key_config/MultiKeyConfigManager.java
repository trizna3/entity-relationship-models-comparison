package sk.trizna.erm_comparison.common.multi_key_config;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import sk.trizna.erm_comparison.common.utils.Utils;

public abstract class MultiKeyConfigManager {

	protected abstract String getConfigFileName();
	protected abstract String getDelimiter();
	
	private boolean resourceLoaded = false;
	private List<String[]> data;
	
	public List<String[]> getResourceData() {
		if (!resourceLoaded) {
			loadValues();
		}
		
		return getData();
	}
	
	private void loadValues() {
		try {
			Files.lines(Paths.get(getConfigFileName())).forEach(line -> processLine(line));
			resourceLoaded = true;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void processLine(String line) {
		Utils.validateNotNull(line);
		if (line.length() < 1 || line.charAt(0) == '#') {
			return;
		}
		getData().add(line.split(getDelimiter()));
	}
	
	private List<String[]> getData() {
		if (data == null) {
			data = new ArrayList<>();
		}
		return data;
	}
}
