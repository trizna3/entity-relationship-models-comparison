package common.multiKeyConfig;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

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
			Files.lines(Paths.get(getConfigFileName())).forEach(line -> getData().add(line.split(getDelimiter())));
			resourceLoaded = true;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private List<String[]> getData() {
		if (data == null) {
			data = new ArrayList<>();
		}
		return data;
	}
}
