package sk.trizna.erm_comparison.tests;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import sk.trizna.erm_comparison.common.Logger;
import sk.trizna.erm_comparison.common.utils.Utils;
import sk.trizna.erm_comparison.comparing.mapping.Mapping;
import sk.trizna.erm_comparison.entity_relationship_model.ERModel;
import sk.trizna.erm_comparison.language_processing.LanguageProcessor;
import sk.trizna.erm_comparison.mappingSearch.mapping_finder.MappingFinder;
import sk.trizna.erm_comparison.parser.Parser;
import sk.trizna.erm_comparison.parser.SyntaxException;

public class Validation {
	
	public static final String BASE_PATH = "..\\erm-comparison-data\\";
	private static final Boolean PRINT_PARSE_MESSAGES = Boolean.FALSE;
	
	/**
	 * Map: instance name -> max student id
	 */
	public static final Map<String, Integer> INSTANCES = new HashMap<>();

	public static final String INSTANCE01 = "0.1";
	public static final String INSTANCE02 = "0.2";
	public static final String INSTANCE03 = "0.3";
	public static final String INSTANCE04 = "0.4";
	public static final String INSTANCE05 = "0.5";
	public static final String INSTANCE06 = "0.6";
	public static final String INSTANCE07 = "0.7";
	public static final String INSTANCE08 = "0.8";
	public static final String INSTANCE09 = "0.9";
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
		INSTANCES.put(INSTANCE01, 2);
		INSTANCES.put(INSTANCE02, 2);
		INSTANCES.put(INSTANCE03, 2);
		INSTANCES.put(INSTANCE04, 2);
		INSTANCES.put(INSTANCE05, 2);
		INSTANCES.put(INSTANCE06, 2);
		INSTANCES.put(INSTANCE07, 2);
		INSTANCES.put(INSTANCE08, 2);
		INSTANCES.put(INSTANCE09, 2);
		INSTANCES.put(INSTANCE61, 4);
		INSTANCES.put(INSTANCE62, 4);
		INSTANCES.put(INSTANCE63, 5);
		INSTANCES.put(INSTANCE64, 4);
		INSTANCES.put(INSTANCE65, 4);
		INSTANCES.put(INSTANCE71, 2);
		INSTANCES.put(INSTANCE72, 3);
		INSTANCES.put(INSTANCE73, 2);
		INSTANCES.put(INSTANCE91, 14);
		INSTANCES.put(INSTANCE92, 12);
		INSTANCES.put(INSTANCE93, 14);
		INSTANCES.put(INSTANCE94, 20);
		INSTANCES.put(INSTANCE95, 2);
		INSTANCES.put(INSTANCE96, 15);
	} 
	
	
	public final static String getScriptsPath() {
		return BASE_PATH + "scripts\\";
	}
	
	public final static String getOutputPath() {
		return BASE_PATH + "output\\";
	}
	
	public final static String getOutputBpPath() {
		return BASE_PATH + "output_bp\\";
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
//	@Test
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
	
	
	// 0.x
	@Test
	public void runInstance01_1() {
		Utils.setWorkingDictSection(Utils.TRAIN_DICT_SECTION);
		assertTrue(runInstance(INSTANCE01));
	}
	
	@Test
	public void runInstance02_1() {
		Utils.setWorkingDictSection(Utils.TRAIN_DICT_SECTION);
		assertTrue(runInstance(INSTANCE02));
	}
	
	@Test
	public void runInstance03_1() {
		Utils.setWorkingDictSection(Utils.TRAIN_DICT_SECTION);
		assertTrue(runInstance(INSTANCE03));
	}
	
	@Test
	public void runInstance04_1() {
		Utils.setWorkingDictSection(Utils.TRAIN_DICT_SECTION);
		assertTrue(runInstance(INSTANCE04));
	}
	
	@Test
	public void runInstance05_1() {
		Utils.setWorkingDictSection(Utils.TRAIN_DICT_SECTION);
		assertTrue(runInstance(INSTANCE05));
	}
	
	@Test
	public void runInstance06_1() {
		Utils.setWorkingDictSection(Utils.TRAIN_DICT_SECTION);
		assertTrue(runInstance(INSTANCE06));
	}
	
	@Test
	public void runInstance07_1() {
		Utils.setWorkingDictSection(Utils.TRAIN_DICT_SECTION);
		assertTrue(runInstance(INSTANCE07));
	}
	
	@Test
	public void runInstance08_1() {
		Utils.setWorkingDictSection(Utils.TRAIN_DICT_SECTION);
		assertTrue(runInstance(INSTANCE08));
	}
	
	@Test
	public void runInstance09_1() {
		Utils.setWorkingDictSection(Utils.TRAIN_DICT_SECTION);
		assertTrue(runInstance(INSTANCE09));
	}
	
	/*
	@Test
	public void runInstance61_1() {
		Utils.setWorkingDictSection(INSTANCE61);
		assertTrue(runInstance(INSTANCE61));
	}
	
	@Test
	public void runInstance62_1() {
		Utils.setWorkingDictSection(INSTANCE62);
		assertTrue(runInstance(INSTANCE62));
	}
	
	@Test
	public void runInstance63_1() {
		Utils.setWorkingDictSection(INSTANCE63);
		assertTrue(runInstance(INSTANCE63));
	}
	
	@Test
	public void runInstance64_1() {
		Utils.setWorkingDictSection(INSTANCE64);
		assertTrue(runInstance(INSTANCE64));
	}
	
	@Test
	public void runInstance65_1() {
		Utils.setWorkingDictSection(INSTANCE65);
		assertTrue(runInstance(INSTANCE65));
	}
	
	@Test
	public void runInstance71_1() {
		Utils.setWorkingDictSection(INSTANCE71);
		assertTrue(runInstance(INSTANCE71));
	}
	
	@Test
	public void runInstance72_1() {
		Utils.setWorkingDictSection(INSTANCE72);
		assertTrue(runInstance(INSTANCE72));
	}
	
	@Test
	public void runInstance73_1() {
		Utils.setWorkingDictSection(INSTANCE73);
		assertTrue(runInstance(INSTANCE73));
	}
	
	@Test
	public void runInstance91_1() {
		Utils.setWorkingDictSection(INSTANCE91);
		assertTrue(runInstance(INSTANCE91));
	}	
	
	@Test
	public void runInstance92_1() {
		Utils.setWorkingDictSection(INSTANCE92);
		assertTrue(runInstance(INSTANCE92));
	}	
	
	@Test
	public void runInstance93_1() {
		Utils.setWorkingDictSection(INSTANCE93);
		assertTrue(runInstance(INSTANCE93));
	}	
	
	@Test
	public void runInstance94_1() {
		Utils.setWorkingDictSection(INSTANCE94);
		assertTrue(runInstance(INSTANCE94));
	}	
	
	@Test
	public void runInstance95_1() {
		Utils.setWorkingDictSection(INSTANCE95);
		assertTrue(runInstance(INSTANCE95));
	}
	
	@Test
	public void runInstance96_1() {
		Utils.setWorkingDictSection(INSTANCE96);
		assertTrue(runInstance(INSTANCE96));
	}
	*/
	
	public boolean runInstance(String instanceName) {
		System.out.println("Running instance " + instanceName);
		LanguageProcessor.getImplementation().getSimilarity("", ""); // load libs
//		LanguageProcessor.getImplementation().clearInstance();
		
		try {
			ERModel exemplarModel = getExemplarModel(instanceName);
			long startNanoSeconds = System.nanoTime();
			for (int i=0; i < INSTANCES.get(instanceName); i++) {
				int studentId = i+1;
				long studentStartNanoSeconds = System.nanoTime();
				try {
					ERModel studentModel = getStudentModel(instanceName, studentId);
					
					MappingFinder finder = new MappingFinder();
					Mapping mapping = finder.findBestMapping(exemplarModel, studentModel);
					logResult(mapping, instanceName, studentId);
				} catch (Exception e) {
					System.out.println("Exception was thrown while executing instance="+instanceName + ", studentId="+studentId);
					e.printStackTrace();
				}
				long timeElapsedMillis = (System.nanoTime() - startNanoSeconds) / 1000000;
				System.out.println("Instance = " + instanceName + ", studentId = " + studentId + ", millis = " + timeElapsedMillis);
			}
			long totalNanoSeconds = System.nanoTime() - startNanoSeconds;
			
//			System.out.println("Instance " + instanceName + " avg time = " + ((totalNanoSeconds/INSTANCES.get(instanceName))/1000000) + " milliseconds");
			
			return true;
		} catch (SyntaxException | IOException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	private boolean runComparison(String instanceName, int studentId) {
		System.out.println("Running instance " + instanceName + " student " + studentId);
		try {
			ERModel exemplarModel = getExemplarModel(instanceName);
			ERModel studentModel = getStudentModel(instanceName, studentId);
			
			MappingFinder finder = new MappingFinder();
			Mapping mapping = finder.findBestMapping(exemplarModel, studentModel);
			logResult(mapping, instanceName, studentId);
			
			return true;
		} catch (SyntaxException | IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	private ERModel getExemplarModel(String instanceName) throws SyntaxException, IOException {
		if (Boolean.TRUE.equals(PRINT_PARSE_MESSAGES)) {
			System.out.println("Parsing exemplar model " + instanceName);
		}
		return Parser.fromString(Parser.fileToString(getScriptsPath() + instanceName + "\\exemplar.txt"));
	}
	
	private ERModel getStudentModel( String instanceName, int studentId) throws SyntaxException, IOException {
		if (Boolean.TRUE.equals(PRINT_PARSE_MESSAGES)) {
			System.out.println("Parsing model " + instanceName + " s" + (studentId) + ".txt");
		}
		return Parser.fromString(Parser.fileToString(getScriptsPath() + instanceName + "\\s" + (studentId) + ".txt"));
	}
	
	private void logResult(Mapping mapping, String instanceName, int studentId) {
		String filepath = getOutputPath() + instanceName + "\\s" + studentId + ".txt";
		Logger logger = new Logger(filepath);
		logger.logMapping(mapping);
		logger.close();
	}
}
