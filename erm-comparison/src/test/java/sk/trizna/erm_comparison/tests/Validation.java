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
import sk.trizna.erm_comparison.mappingSearch.mapping_finder.MappingFinder;
import sk.trizna.erm_comparison.parser.Parser;
import sk.trizna.erm_comparison.parser.SyntaxException;

public class Validation {
	
	public static final String BASE_PATH = "..\\erm-comparison-data\\";
	
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
	
	// 0.x
	@Test
	public void runInstance01_1() {
		Utils.setWorkingDictSection(Utils.TRAIN_DICT_SECTION);
		assertTrue(runComparison(INSTANCE01, 1));
	}
	
	@Test
	public void runInstance01_2() {
		Utils.setWorkingDictSection(Utils.TRAIN_DICT_SECTION);
		assertTrue(runComparison(INSTANCE01, 2));
	}

	@Test
	public void runInstance02_1() {
		Utils.setWorkingDictSection(Utils.TRAIN_DICT_SECTION);
		assertTrue(runComparison(INSTANCE02, 1));
	}
	
	@Test
	public void runInstance02_2() {
		Utils.setWorkingDictSection(Utils.TRAIN_DICT_SECTION);
		assertTrue(runComparison(INSTANCE02, 2));
	}

	@Test
	public void runInstance03_1() {
		Utils.setWorkingDictSection(Utils.TRAIN_DICT_SECTION);
		assertTrue(runComparison(INSTANCE03, 1));
	}
	
	@Test
	public void runInstance03_2() {
		Utils.setWorkingDictSection(Utils.TRAIN_DICT_SECTION);
		assertTrue(runComparison(INSTANCE03, 2));
	}
	
	@Test
	public void runInstance04_1() {
		Utils.setWorkingDictSection(Utils.TRAIN_DICT_SECTION);
		assertTrue(runComparison(INSTANCE04, 1));
	}
	
	@Test
	public void runInstance04_2() {
		Utils.setWorkingDictSection(Utils.TRAIN_DICT_SECTION);
		assertTrue(runComparison(INSTANCE04, 2));
	}
	
	@Test
	public void runInstance05_1() {
		Utils.setWorkingDictSection(Utils.TRAIN_DICT_SECTION);
		assertTrue(runComparison(INSTANCE05, 1));
	}
	
	@Test
	public void runInstance05_2() {
		Utils.setWorkingDictSection(Utils.TRAIN_DICT_SECTION);
		assertTrue(runComparison(INSTANCE05, 2));
	}
	
	@Test
	public void runInstance06_1() {
		Utils.setWorkingDictSection(Utils.TRAIN_DICT_SECTION);
		assertTrue(runComparison(INSTANCE06, 1));
	}
	
	@Test
	public void runInstance06_2() {
		Utils.setWorkingDictSection(Utils.TRAIN_DICT_SECTION);
		assertTrue(runComparison(INSTANCE06, 2));
	}
	
	@Test
	public void runInstance07_1() {
		Utils.setWorkingDictSection(Utils.TRAIN_DICT_SECTION);
		assertTrue(runComparison(INSTANCE07, 1));
	}
	
	@Test
	public void runInstance07_2() {
		Utils.setWorkingDictSection(Utils.TRAIN_DICT_SECTION);
		assertTrue(runComparison(INSTANCE07, 2));
	}
	
	@Test
	public void runInstance08_1() {
		Utils.setWorkingDictSection(Utils.TRAIN_DICT_SECTION);
		assertTrue(runComparison(INSTANCE08, 1));
	}
	
	@Test
	public void runInstance08_2() {
		Utils.setWorkingDictSection(Utils.TRAIN_DICT_SECTION);
		assertTrue(runComparison(INSTANCE08, 2));
	}
	
	@Test
	public void runInstance09_1() {
		Utils.setWorkingDictSection(Utils.TRAIN_DICT_SECTION);
		assertTrue(runComparison(INSTANCE09, 1));
	}
	
	@Test
	public void runInstance09_2() {
		Utils.setWorkingDictSection(Utils.TRAIN_DICT_SECTION);
		assertTrue(runComparison(INSTANCE09, 2));
	}
	
	/*
	// 6.1
	
	@Test
	public void runInstance61_1() {
		Utils.setWorkingDictSection(INSTANCE61);
		assertTrue(runComparison(INSTANCE61, 1));
	}
	
	@Test
	public void runInstance61_2() {
		Utils.setWorkingDictSection(INSTANCE61);
		assertTrue(runComparison(INSTANCE61, 2));
	}
	
	@Test
	public void runInstance61_3() {
		Utils.setWorkingDictSection(INSTANCE61);
		assertTrue(runComparison(INSTANCE61, 3));
	}
	
	@Test
	public void runInstance61_4() {
		Utils.setWorkingDictSection(INSTANCE61);
		assertTrue(runComparison(INSTANCE61, 4));
	}
	
	// 6.2
	
	@Test
	public void runInstance62_1() {
		Utils.setWorkingDictSection(INSTANCE62);
		assertTrue(runComparison(INSTANCE62, 1));
	}
	
	@Test
	public void runInstance62_2() {
		Utils.setWorkingDictSection(INSTANCE62);
		assertTrue(runComparison(INSTANCE62, 2));
	}
	
	@Test
	public void runInstance62_3() {
		Utils.setWorkingDictSection(INSTANCE62);
		assertTrue(runComparison(INSTANCE62, 3));
	}
	
	@Test
	public void runInstance62_4() {
		Utils.setWorkingDictSection(INSTANCE62);
		assertTrue(runComparison(INSTANCE62, 4));
	}
	
	// 6.3
	
	@Test
	public void runInstance63_1() {
		Utils.setWorkingDictSection(INSTANCE63);
		assertTrue(runComparison(INSTANCE63, 1));
	}
	
	@Test
	public void runInstance63_2() {
		Utils.setWorkingDictSection(INSTANCE63);
		assertTrue(runComparison(INSTANCE63, 2));
	}
	
	@Test
	public void runInstance63_3() {
		Utils.setWorkingDictSection(INSTANCE63);
		assertTrue(runComparison(INSTANCE63, 3));
	}
	
	@Test
	public void runInstance63_4() {
		Utils.setWorkingDictSection(INSTANCE63);
		assertTrue(runComparison(INSTANCE63, 4));
	}
	
	@Test
	public void runInstance63_5() {
		Utils.setWorkingDictSection(INSTANCE63);
		assertTrue(runComparison(INSTANCE63, 5));
	}
	
	// 6.4
	
	@Test
	public void runInstance64_1() {
		Utils.setWorkingDictSection(INSTANCE64);
		assertTrue(runComparison(INSTANCE64, 1));
	}
	
	@Test
	public void runInstance64_2() {
		Utils.setWorkingDictSection(INSTANCE64);
		assertTrue(runComparison(INSTANCE64, 2));
	}
	
	@Test
	public void runInstance64_3() {
		Utils.setWorkingDictSection(INSTANCE64);
		assertTrue(runComparison(INSTANCE64, 3));
	}
	
	@Test
	public void runInstance64_4() {
		Utils.setWorkingDictSection(INSTANCE64);
		assertTrue(runComparison(INSTANCE64, 4));
	}
	
	// 6.5

	@Test
	public void runInstance65_1() {
		Utils.setWorkingDictSection(INSTANCE65);
		assertTrue(runComparison(INSTANCE65, 1));
	}
	
	@Test
	public void runInstance65_2() {
		Utils.setWorkingDictSection(INSTANCE65);
		assertTrue(runComparison(INSTANCE65, 2));
	}
	
	@Test
	public void runInstance65_3() {
		Utils.setWorkingDictSection(INSTANCE65);
		assertTrue(runComparison(INSTANCE65, 3));
	}
	
	@Test
	public void runInstance65_4() {
		Utils.setWorkingDictSection(INSTANCE65);
		assertTrue(runComparison(INSTANCE65, 4));
	}
	
	// 7.1 - 2
	
	@Test
	public void runInstance71_1() {
		Utils.setWorkingDictSection(INSTANCE71);
		assertTrue(runComparison(INSTANCE71, 1));
	}
	
	@Test
	public void runInstance71_2() {
		Utils.setWorkingDictSection(INSTANCE71);
		assertTrue(runComparison(INSTANCE71, 2));
	}
	
	// 7.2 - 3
	
	@Test
	public void runInstance72_1() {
		Utils.setWorkingDictSection(INSTANCE72);
		assertTrue(runComparison(INSTANCE72, 1));
	}
	
	@Test
	public void runInstance72_2() {
		Utils.setWorkingDictSection(INSTANCE72);
		assertTrue(runComparison(INSTANCE72, 2));
	}
	
	@Test
	public void runInstance72_3() {
		Utils.setWorkingDictSection(INSTANCE72);
		assertTrue(runComparison(INSTANCE72, 3));
	}
	
	// 7.3 - 2
	
	@Test
	public void runInstance73_1() {
		Utils.setWorkingDictSection(INSTANCE73);
		assertTrue(runComparison(INSTANCE73, 1));
	}
	
	@Test
	public void runInstance73_2() {
		Utils.setWorkingDictSection(INSTANCE73);
		assertTrue(runComparison(INSTANCE73, 2));
	}
	
	// 9.1
	
	@Test
	public void runInstance91_1() {
		Utils.setWorkingDictSection(INSTANCE91);
		assertTrue(runComparison(INSTANCE91, 1));
	}	
	
	@Test
	public void runInstance91_2() {
		Utils.setWorkingDictSection(INSTANCE91);
		assertTrue(runComparison(INSTANCE91, 2));
	}
	
	@Test
	public void runInstance91_3() {
		Utils.setWorkingDictSection(INSTANCE91);
		assertTrue(runComparison(INSTANCE91, 3));
	}
	
	@Test
	public void runInstance91_4() {
		Utils.setWorkingDictSection(INSTANCE91);
		assertTrue(runComparison(INSTANCE91, 4));
	}
	
	@Test
	public void runInstance91_5() {
		Utils.setWorkingDictSection(INSTANCE91);
		assertTrue(runComparison(INSTANCE91, 5));
	}
	
	@Test
	public void runInstance91_6() {
		Utils.setWorkingDictSection(INSTANCE91);
		assertTrue(runComparison(INSTANCE91, 6));
	}
	
	@Test
	public void runInstance91_7() {
		Utils.setWorkingDictSection(INSTANCE91);
		assertTrue(runComparison(INSTANCE91, 7));
	}
	
	@Test
	public void runInstance91_8() {
		Utils.setWorkingDictSection(INSTANCE91);
		assertTrue(runComparison(INSTANCE91, 8));
	}
	
	@Test
	public void runInstance91_9() {
		Utils.setWorkingDictSection(INSTANCE91);
		assertTrue(runComparison(INSTANCE91, 9));
	}
	
	@Test
	public void runInstance91_10() {
		Utils.setWorkingDictSection(INSTANCE91);
		assertTrue(runComparison(INSTANCE91, 10));
	}
	
	@Test
	public void runInstance91_11() {
		Utils.setWorkingDictSection(INSTANCE91);
		assertTrue(runComparison(INSTANCE91, 11));
	}
	
	@Test
	public void runInstance91_12() {
		Utils.setWorkingDictSection(INSTANCE91);
		assertTrue(runComparison(INSTANCE91, 12));
	}
	
	@Test
	public void runInstance91_13() {
		Utils.setWorkingDictSection(INSTANCE91);
		assertTrue(runComparison(INSTANCE91, 13));
	}
	
	@Test
	public void runInstance91_14() {
		Utils.setWorkingDictSection(INSTANCE91);
		assertTrue(runComparison(INSTANCE91, 14));
	}
	
	// 9.2
	
	@Test
	public void runInstance92_1() {
		Utils.setWorkingDictSection(INSTANCE92);
		assertTrue(runComparison(INSTANCE92, 1));
	}	
	
	@Test
	public void runInstance92_2() {
		Utils.setWorkingDictSection(INSTANCE92);
		assertTrue(runComparison(INSTANCE92, 2));
	}
	
	@Test
	public void runInstance92_3() {
		Utils.setWorkingDictSection(INSTANCE92);
		assertTrue(runComparison(INSTANCE92, 3));
	}
	
	@Test
	public void runInstance92_4() {
		Utils.setWorkingDictSection(INSTANCE92);
		assertTrue(runComparison(INSTANCE92, 4));
	}
	
	@Test
	public void runInstance92_5() {
		Utils.setWorkingDictSection(INSTANCE92);
		assertTrue(runComparison(INSTANCE92, 5));
	}
	
	@Test
	public void runInstance92_6() {
		Utils.setWorkingDictSection(INSTANCE92);
		assertTrue(runComparison(INSTANCE92, 6));
	}
	
	@Test
	public void runInstance92_7() {
		Utils.setWorkingDictSection(INSTANCE92);
		assertTrue(runComparison(INSTANCE92, 7));
	}
	
	@Test
	public void runInstance92_8() {
		Utils.setWorkingDictSection(INSTANCE92);
		assertTrue(runComparison(INSTANCE92, 8));
	}
	
	@Test
	public void runInstance92_9() {
		Utils.setWorkingDictSection(INSTANCE92);
		assertTrue(runComparison(INSTANCE92, 9));
	}
	
	@Test
	public void runInstance92_10() {
		Utils.setWorkingDictSection(INSTANCE92);
		assertTrue(runComparison(INSTANCE92, 10));
	}
	
	@Test
	public void runInstance92_11() {
		Utils.setWorkingDictSection(INSTANCE92);
		assertTrue(runComparison(INSTANCE92, 11));
	}
	
	@Test
	public void runInstance92_12() {
		Utils.setWorkingDictSection(INSTANCE92);
		assertTrue(runComparison(INSTANCE92, 12));
	}
	
	// 9.3
	
	@Test
	public void runInstance93_1() {
		Utils.setWorkingDictSection(INSTANCE93);
		assertTrue(runComparison(INSTANCE93, 1));
	}	
	
	@Test
	public void runInstance93_2() {
		Utils.setWorkingDictSection(INSTANCE93);
		assertTrue(runComparison(INSTANCE93, 2));
	}
	
	@Test
	public void runInstance93_3() {
		Utils.setWorkingDictSection(INSTANCE93);
		assertTrue(runComparison(INSTANCE93, 3));
	}
	
	@Test
	public void runInstance93_4() {
		Utils.setWorkingDictSection(INSTANCE93);
		assertTrue(runComparison(INSTANCE93, 4));
	}
	
	@Test
	public void runInstance93_5() {
		Utils.setWorkingDictSection(INSTANCE93);
		assertTrue(runComparison(INSTANCE93, 5));
	}
	
	@Test
	public void runInstance93_6() {
		Utils.setWorkingDictSection(INSTANCE93);
		assertTrue(runComparison(INSTANCE93, 6));
	}
	
	@Test
	public void runInstance93_7() {
		Utils.setWorkingDictSection(INSTANCE93);
		assertTrue(runComparison(INSTANCE93, 7));
	}
	
	@Test
	public void runInstance93_8() {
		Utils.setWorkingDictSection(INSTANCE93);
		assertTrue(runComparison(INSTANCE93, 8));
	}
	
	@Test
	public void runInstance93_9() {
		Utils.setWorkingDictSection(INSTANCE93);
		assertTrue(runComparison(INSTANCE93, 9));
	}
	
	@Test
	public void runInstance93_10() {
		Utils.setWorkingDictSection(INSTANCE93);
		assertTrue(runComparison(INSTANCE93, 10));
	}
	
	@Test
	public void runInstance93_11() {
		Utils.setWorkingDictSection(INSTANCE93);
		assertTrue(runComparison(INSTANCE93, 11));
	}
	
	@Test
	public void runInstance93_12() {
		Utils.setWorkingDictSection(INSTANCE93);
		assertTrue(runComparison(INSTANCE93, 12));
	}
	
	@Test
	public void runInstance93_13() {
		Utils.setWorkingDictSection(INSTANCE93);
		assertTrue(runComparison(INSTANCE93, 13));
	}
	
	@Test
	public void runInstance93_14() {
		Utils.setWorkingDictSection(INSTANCE93);
		assertTrue(runComparison(INSTANCE93, 14));
	}
	
	// 9.4
	
	@Test
	public void runInstance94_1() {
		Utils.setWorkingDictSection(INSTANCE94);
		assertTrue(runComparison(INSTANCE94, 1));
	}	
	
	@Test
	public void runInstance94_2() {
		Utils.setWorkingDictSection(INSTANCE94);
		assertTrue(runComparison(INSTANCE94, 2));
	}
	
	@Test
	public void runInstance94_3() {
		Utils.setWorkingDictSection(INSTANCE94);
		assertTrue(runComparison(INSTANCE94, 3));
	}
	
	@Test
	public void runInstance94_4() {
		Utils.setWorkingDictSection(INSTANCE94);
		assertTrue(runComparison(INSTANCE94, 4));
	}
	
	@Test
	public void runInstance94_5() {
		Utils.setWorkingDictSection(INSTANCE94);
		assertTrue(runComparison(INSTANCE94, 5));
	}
	
	@Test
	public void runInstance94_6() {
		Utils.setWorkingDictSection(INSTANCE94);
		assertTrue(runComparison(INSTANCE94, 6));
	}
	
	@Test
	public void runInstance94_7() {
		Utils.setWorkingDictSection(INSTANCE94);
		assertTrue(runComparison(INSTANCE94, 7));
	}
	
	@Test
	public void runInstance94_8() {
		Utils.setWorkingDictSection(INSTANCE94);
		assertTrue(runComparison(INSTANCE94, 8));
	}
	
	@Test
	public void runInstance94_9() {
		Utils.setWorkingDictSection(INSTANCE94);
		assertTrue(runComparison(INSTANCE94, 9));
	}
	
	@Test
	public void runInstance94_10() {
		Utils.setWorkingDictSection(INSTANCE94);
		assertTrue(runComparison(INSTANCE94, 10));
	}
	
	@Test
	public void runInstance94_11() {
		Utils.setWorkingDictSection(INSTANCE94);
		assertTrue(runComparison(INSTANCE94, 11));
	}
	
	@Test
	public void runInstance94_12() {
		Utils.setWorkingDictSection(INSTANCE94);
		assertTrue(runComparison(INSTANCE94, 12));
	}
	
	@Test
	public void runInstance94_13() {
		Utils.setWorkingDictSection(INSTANCE94);
		assertTrue(runComparison(INSTANCE94, 13));
	}
	
	@Test
	public void runInstance94_14() {
		Utils.setWorkingDictSection(INSTANCE94);
		assertTrue(runComparison(INSTANCE94, 14));
	}
	
	@Test
	public void runInstance94_15() {
		Utils.setWorkingDictSection(INSTANCE94);
		assertTrue(runComparison(INSTANCE94, 15));
	}
	
	@Test
	public void runInstance94_16() {
		Utils.setWorkingDictSection(INSTANCE94);
		assertTrue(runComparison(INSTANCE94, 16));
	}
	
	@Test
	public void runInstance94_17() {
		Utils.setWorkingDictSection(INSTANCE94);
		assertTrue(runComparison(INSTANCE94, 17));
	}
	
	@Test
	public void runInstance94_18() {
		Utils.setWorkingDictSection(INSTANCE94);
		assertTrue(runComparison(INSTANCE94, 18));
	}
	
	@Test
	public void runInstance94_19() {
		Utils.setWorkingDictSection(INSTANCE94);
		assertTrue(runComparison(INSTANCE94, 19));
	}
	
	@Test
	public void runInstance94_20() {
		Utils.setWorkingDictSection(INSTANCE94);
		assertTrue(runComparison(INSTANCE94, 20));
	}
	
	// 9.5
	
	@Test
	public void runInstance95_1() {
		Utils.setWorkingDictSection(INSTANCE95);
		assertTrue(runComparison(INSTANCE95, 1));
	}
	
	@Test
	public void runInstance95_2() {
		Utils.setWorkingDictSection(INSTANCE95);
		assertTrue(runComparison(INSTANCE95, 2));
	}
	
	// 9.6 - 15
	
	@Test
	public void runInstance96_1() {
		Utils.setWorkingDictSection(INSTANCE96);
		assertTrue(runComparison(INSTANCE96, 1));
	}	
	
	@Test
	public void runInstance96_2() {
		Utils.setWorkingDictSection(INSTANCE96);
		assertTrue(runComparison(INSTANCE96, 2));
	}
	
	@Test
	public void runInstance96_3() {
		Utils.setWorkingDictSection(INSTANCE96);
		assertTrue(runComparison(INSTANCE96, 3));
	}
	
	@Test
	public void runInstance96_4() {
		Utils.setWorkingDictSection(INSTANCE96);
		assertTrue(runComparison(INSTANCE96, 4));
	}
	
	@Test
	public void runInstance96_5() {
		Utils.setWorkingDictSection(INSTANCE96);
		assertTrue(runComparison(INSTANCE96, 5));
	}
	
	@Test
	public void runInstance96_6() {
		Utils.setWorkingDictSection(INSTANCE96);
		assertTrue(runComparison(INSTANCE96, 6));
	}
	
	@Test
	public void runInstance96_7() {
		Utils.setWorkingDictSection(INSTANCE96);
		assertTrue(runComparison(INSTANCE96, 7));
	}
	
	@Test
	public void runInstance96_8() {
		Utils.setWorkingDictSection(INSTANCE96);
		assertTrue(runComparison(INSTANCE96, 8));
	}
	
	@Test
	public void runInstance96_9() {
		Utils.setWorkingDictSection(INSTANCE96);
		assertTrue(runComparison(INSTANCE96, 9));
	}
	
	@Test
	public void runInstance96_10() {
		Utils.setWorkingDictSection(INSTANCE96);
		assertTrue(runComparison(INSTANCE96, 10));
	}
	
	@Test
	public void runInstance96_11() {
		Utils.setWorkingDictSection(INSTANCE96);
		assertTrue(runComparison(INSTANCE96, 11));
	}
	
	@Test
	public void runInstance96_12() {
		Utils.setWorkingDictSection(INSTANCE96);
		assertTrue(runComparison(INSTANCE96, 12));
	}
	
	@Test
	public void runInstance96_13() {
		Utils.setWorkingDictSection(INSTANCE96);
		assertTrue(runComparison(INSTANCE96, 13));
	}
	
	@Test
	public void runInstance96_14() {
		Utils.setWorkingDictSection(INSTANCE96);
		assertTrue(runComparison(INSTANCE96, 14));
	}
	
	@Test
	public void runInstance96_15() {
		Utils.setWorkingDictSection(INSTANCE96);
		assertTrue(runComparison(INSTANCE96, 15));
	}
	*/
	// --
	
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
