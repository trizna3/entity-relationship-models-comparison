package tests;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import common.Logger;
import comparing.Mapping;
import entityRelationshipModel.ERModel;
import mappingSearch.mappingFinder.MappingFinder;
import parser.Parser;
import parser.SyntaxException;

public class Validation {
	
	public static final String BASE_PATH = "C:\\Users\\adamt\\OneDrive\\Documents\\Materials\\entity-relationship-models-comparison\\data\\";
	
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
	
	static {
		INSTANCES.put(INSTANCE61, 4);
//		INSTANCES.put(INSTANCE62, 4);
//		INSTANCES.put(INSTANCE63, 5);
//		INSTANCES.put(INSTANCE64, 4);
//		INSTANCES.put(INSTANCE65, 4);
//		INSTANCES.put(INSTANCE71, 2);
//		INSTANCES.put(INSTANCE72, 3);
//		INSTANCES.put(INSTANCE73, 2);
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
		}
	}
	
	@Test
	public void validateInstance61() {
		String instanceName = INSTANCE61;
		System.out.println("Running instance " + instanceName);
		
		try {
			ERModel exemplarModel = getExemplarModel(instanceName);
			for (int i=0; i < INSTANCES.get(instanceName); i++) {
				int studentId = i+1;
				ERModel studentModel = getStudentModel(instanceName, studentId);
				
				MappingFinder finder = new MappingFinder();
				Mapping mapping = finder.getBestMapping(exemplarModel, studentModel);
				logResult(mapping, instanceName, studentId);
				assertTrue(true);
			}
		} catch (SyntaxException | IOException e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	private ERModel getExemplarModel(String instanceName) throws SyntaxException, IOException {
		return Parser.fromString(Parser.fileToString(getScriptsPath() + instanceName + "\\exemplar.txt"));
	}
	
	private ERModel getStudentModel( String instanceName, int studentId) throws SyntaxException, IOException {
		return Parser.fromString(Parser.fileToString(getScriptsPath() + instanceName + "\\s" + (studentId) + ".txt"));
	}
	
	private void logResult(Mapping mapping, String instanceName, int studentId) {
		String filepath = getOutputPath() + instanceName + "\\s" + studentId + ".txt";
		Logger logger = new Logger(filepath);
		logger.logMapping(mapping);
		logger.close();
	}
}
