package sk.trizna.erm_comparison.tests;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import sk.trizna.erm_comparison.common.Logger;
import sk.trizna.erm_comparison.comparing.mapping.Mapping;
import sk.trizna.erm_comparison.entity_relationship_model.ERModel;
import sk.trizna.erm_comparison.mappingSearch.mapping_finder.MappingFinder;
import sk.trizna.erm_comparison.parser.Parser;
import sk.trizna.erm_comparison.parser.SyntaxException;

public class Validation {
	
	public static final String BASE_PATH = "..\\erm-comparison-data\\";
	
	/**
	 * Map: instance name -> max student id
	 */
	public static final Map<String, Integer> INSTANCES = new HashMap<>();

	public static final String INSTANCE61 = "6.1";
	public static final String INSTANCE62 = "6.2";
	public static final String INSTANCE63 = "6.3";
	public static final String INSTANCE64 = "6.4";
	public static final String INSTANCE65 = "6.5";
	public static final String INSTANCE71 = "7.1";
	public static final String INSTANCE72 = "7.2";
	public static final String INSTANCE73 = "7.3";
	public static final String INSTANCE91 = "9.1";
	public static final String INSTANCE92 = "9.2";
	public static final String INSTANCE93 = "9.3";
	public static final String INSTANCE94 = "9.4";
	public static final String INSTANCE95 = "9.5";
	public static final String INSTANCE96 = "9.6";
	
	static {
		INSTANCES.put(INSTANCE61, 5);
		INSTANCES.put(INSTANCE62, 5);
		INSTANCES.put(INSTANCE63, 6);
		INSTANCES.put(INSTANCE64, 5);
		INSTANCES.put(INSTANCE65, 5);
		INSTANCES.put(INSTANCE71, 2);
		INSTANCES.put(INSTANCE72, 3);
		INSTANCES.put(INSTANCE73, 2);
		INSTANCES.put(INSTANCE91, 14);
		INSTANCES.put(INSTANCE92, 12);
		INSTANCES.put(INSTANCE93, 14);
		INSTANCES.put(INSTANCE94, 20);
		INSTANCES.put(INSTANCE95, 3);
		INSTANCES.put(INSTANCE96, 15);
	} 
	
	
	public final static String getScriptsPath() {
		return BASE_PATH + "scripts\\";
	}
	
	public final static String getOutputPath() {
		return BASE_PATH + "output\\";
	}
	
	public final static String getGoldenPath() {
		return BASE_PATH + "golden\\";
	}
	
	public final static String getResultPath() {
		return BASE_PATH + "result\\";
	}
	
	/**
	 * Parse all scripts, detect typos.  
	 */
	@Test
	public void validateScripts() {
		try {
			for (String instanceName : INSTANCES.keySet()) {
				// parse exemplar model
				getExemplarModel(instanceName);
				for (int i=0; i<INSTANCES.get(instanceName); i++) {
					// parse student model
					getStudentModel(instanceName,(i+1));
				}
			}
		} catch (SyntaxException | IOException e) {
			e.printStackTrace();
			assertTrue(false);
		} catch (IllegalArgumentException e) {
			System.out.println("Error parsing/creating model.");
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	@Test
	public void runInstance61() {
		runInstance(INSTANCE61);
		assertTrue(true);
	}
	
	@Test
	public void runInstance62() {
		runInstance(INSTANCE62);
		assertTrue(true);
	}
	
	@Test
	public void runInstance63() {
		runInstance(INSTANCE63);
		assertTrue(true);
	}
	
	@Test
	public void runInstance64() {
		runInstance(INSTANCE64);
		assertTrue(true);
	}
	
	@Test
	public void runInstance65() {
		runInstance(INSTANCE65);
		assertTrue(true);
	}
	
	@Test
	public void runInstance71() {
		runInstance(INSTANCE71);
		assertTrue(true);
	}
	
	@Test
	public void runInstance72() {
		runInstance(INSTANCE72);
		assertTrue(true);
	}
	
	@Test
	public void runInstance73() {
		runInstance(INSTANCE73);
		assertTrue(true);
	}

	public void runInstance(String instanceName) {
		System.out.println("Running instance " + instanceName);
		
		try {
			ERModel exemplarModel = getExemplarModel(instanceName);
			for (int i=0; i < INSTANCES.get(instanceName); i++) {
				int studentId = i+1;
				ERModel studentModel = getStudentModel(instanceName, studentId);
				
				MappingFinder finder = new MappingFinder();
				Mapping mapping = finder.findBestMapping(exemplarModel, studentModel);
				logResult(mapping, instanceName, studentId);
				assertTrue(true);
			}
		} catch (SyntaxException | IOException e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	private ERModel getExemplarModel(String instanceName) throws SyntaxException, IOException {
		System.out.println("Parsing exemplar model " + instanceName);
		return Parser.fromString(Parser.fileToString(getScriptsPath() + instanceName + "\\exemplar.txt"));
	}
	
	private ERModel getStudentModel( String instanceName, int studentId) throws SyntaxException, IOException {
		System.out.println("Parsing model " + instanceName + " s" + (studentId) + ".txt");
		return Parser.fromString(Parser.fileToString(getScriptsPath() + instanceName + "\\s" + (studentId) + ".txt"));
	}
	
	private void logResult(Mapping mapping, String instanceName, int studentId) {
		String filepath = getOutputPath() + instanceName + "\\s" + studentId + ".txt";
		Logger logger = new Logger(filepath);
		logger.logMapping(mapping);
		logger.close();
	}
}
