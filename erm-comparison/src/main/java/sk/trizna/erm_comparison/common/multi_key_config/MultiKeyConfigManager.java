package sk.trizna.erm_comparison.common.multi_key_config;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sk.trizna.erm_comparison.common.utils.Utils;

public abstract class MultiKeyConfigManager {

	protected abstract String getConfigFileName();
	protected abstract String getDelimiter();
	
	/**
	 * Section supporting dictionaries usage:
	 * 
	 * Section start:
	 * # section [sectionName]
	 * 
	 * (current) Section end:
	 * # section end
	 * 
	 */
	protected abstract boolean isSplitSections();
	protected final static String DEFAULT_SECTION = "default_section";
	
	private boolean resourceLoaded = false;
	private Map<String,List<String[]>> data;
	private String currentSection = null;
	
	public List<String[]> getResourceData() {
		if (!resourceLoaded) {
			loadValues();
		}
		
		return getData().get(DEFAULT_SECTION);
	}
	
	@SuppressWarnings("unchecked")
	public List<String[]> getResourceData(String section) {
		if (!isSplitSections()) {
			throw new IllegalStateException("Configuration section requested but config doesn't support sections.");
		}
		if (!resourceLoaded) {
			loadValues();
		}
		
		return (List<String[]>) Utils.firstNonNull(getData().get(section),new ArrayList<String[]>());
	}
	
	private void loadValues() {
		try {
			Files.lines(Paths.get(getConfigFileName())).forEach(line -> processLine(line));
			resourceLoaded = true;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void handleSection(String line) {
		if (!isSplitSections()) {
			return;
		}
		if (line == null || line.trim().isEmpty() || line.trim().charAt(0) != '#') {
			return;
		}
		line = line.trim();
		if (line.contains("section end")) {
			currentSection = null;
			return;
		}
		else if (line.contains("section")) {
			int sectionNameIdx = line.indexOf("section") + 8;
			currentSection = line.substring(sectionNameIdx);
		}
	}
	
	private void processLine(String line) {
		Utils.validateNotNull(line);
		handleSection(line);
		if (line.length() < 1 || line.charAt(0) == '#') {
			return;
		}
		if (currentSection != null && isSplitSections()) {
			if (getData().get(currentSection) == null) {
				getData().put(currentSection, new ArrayList<String[]>());
			}
			getData().get(currentSection).add(line.split(getDelimiter()));			
		} else {
			getData().get(DEFAULT_SECTION).add(line.split(getDelimiter()));
		}
	}
	
	private Map<String, List<String[]>> getData() {
		if (data == null) {
			data = new HashMap<String, List<String[]>>();
			data.put(DEFAULT_SECTION, new ArrayList<String[]>());
		}
		return data;
	}
}
