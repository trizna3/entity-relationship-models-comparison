package sk.trizna.erm_comparison.tests;

import static org.junit.Assert.assertTrue;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import sk.trizna.erm_comparison.common.enums.EnumTransformation;
import sk.trizna.erm_comparison.common.enums.TranslationConstants;
import sk.trizna.erm_comparison.common.utils.CollectionUtils;
import sk.trizna.erm_comparison.common.utils.MappingUtils;
import sk.trizna.erm_comparison.common.utils.PrintUtils;
import sk.trizna.erm_comparison.common.utils.StringUtils;
import sk.trizna.erm_comparison.common.utils.Utils;
import sk.trizna.erm_comparison.language_processing.LanguageProcessor;
import sk.trizna.erm_comparison.tests.common.ValidationEvaluatorUtils;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ValidationEvaluator {
	
	private static final Set<String> ARGUMENT_ORDER_INSENSITIVE_TRANSFORMATIONS = new HashSet<String>(Arrays.asList(
			EnumTransformation.REBIND_MN_TO_1NN1.toString(), 
			EnumTransformation.REBIND_1NN1_TO_MN.toString(), 
			EnumTransformation.CONTRACT_11_ASSOCIATION.toString(), 
			EnumTransformation.REBIND_NARY_ASSOCIATION.toString(),
			EnumTransformation.MERGE_ENTITY_SETS.toString())
			);
	
	private static boolean isArgumentOrderInsensitiveTransformation(String transformationType) {
		if (transformationType == null) {
			return false;
		}
		return ARGUMENT_ORDER_INSENSITIVE_TRANSFORMATIONS.contains(transformationType.toUpperCase());
	}

	public class MappingParsed {
		private Map<String,String> map;
		private Map<String,String[]> transformations;
		
		public MappingParsed(Map<String,String> map,Map<String,String[]> transformations) {
			this.map = map;
			this.transformations = transformations;
		}

		public Map<String,String> getMap() {
			if (map == null) {
				return new HashMap<String, String>();
			}
			return map;
		}

		public Map<String,String[]> getTransformations() {
			if (transformations == null) {
				transformations = new HashMap<String, String[]>();
			}
			return transformations;
		}
	}
	
	public class EvaluationResult {
		private MappingParsed missing;
		private MappingParsed overflow;
		
		public EvaluationResult(MappingParsed missing, MappingParsed overflow) {
			this.missing = missing;
			this.overflow = overflow;
		}
		
		public MappingParsed getMissing() {
			return missing;
		}

		public MappingParsed getOverflow() {
			return overflow;
		}		
	}
	
	@Test
	public void _validateInstance01() {
		Utils.setWorkingDictSection(Utils.TRAIN_DICT_SECTION);
		validateInstance(Validation.INSTANCE01);
		assertTrue(true);
	}
	
	@Test
	public void _validateInstance02() {
		Utils.setWorkingDictSection(Utils.TRAIN_DICT_SECTION);
		validateInstance(Validation.INSTANCE02);
		assertTrue(true);
	}
	
	@Test
	public void _validateInstance03() {
		Utils.setWorkingDictSection(Utils.TRAIN_DICT_SECTION);
		validateInstance(Validation.INSTANCE03);
		assertTrue(true);
	}
	
	@Test
	public void _validateInstance04() {
		Utils.setWorkingDictSection(Utils.TRAIN_DICT_SECTION);
		validateInstance(Validation.INSTANCE04);
		assertTrue(true);
	}
	
	@Test
	public void _validateInstance05() {
		Utils.setWorkingDictSection(Utils.TRAIN_DICT_SECTION);
		validateInstance(Validation.INSTANCE05);
		assertTrue(true);
	}
	
	@Test
	public void _validateInstance06() {
		Utils.setWorkingDictSection(Utils.TRAIN_DICT_SECTION);
		validateInstance(Validation.INSTANCE06);
		assertTrue(true);
	}
	
	@Test
	public void _validateInstance07() {
		Utils.setWorkingDictSection(Utils.TRAIN_DICT_SECTION);
		validateInstance(Validation.INSTANCE07);
		assertTrue(true);
	}
	
	@Test
	public void _validateInstance08() {
		Utils.setWorkingDictSection(Utils.TRAIN_DICT_SECTION);
		validateInstance(Validation.INSTANCE08);
		assertTrue(true);
	}
	
	@Test
	public void _validateInstance09() {
		Utils.setWorkingDictSection(Utils.TRAIN_DICT_SECTION);
		validateInstance(Validation.INSTANCE09);
		assertTrue(true);
	}
	
	@Test
	public void _validateInstance61() {
		Utils.setWorkingDictSection(Validation.INSTANCE61);
		validateInstance(Validation.INSTANCE61);
		assertTrue(true);
	}
	
	@Test
	public void _validateInstance62() {
		Utils.setWorkingDictSection(Validation.INSTANCE62);
		validateInstance(Validation.INSTANCE62);
		assertTrue(true);
	}
	
	@Test
	public void _validateInstance63() {
		Utils.setWorkingDictSection(Validation.INSTANCE63);
		validateInstance(Validation.INSTANCE63);
		assertTrue(true);
	}
	
	@Test
	public void _validateInstance64() {
		Utils.setWorkingDictSection(Validation.INSTANCE64);
		validateInstance(Validation.INSTANCE64);
		assertTrue(true);
	}
	
	@Test
	public void _validateInstance65() {
		Utils.setWorkingDictSection(Validation.INSTANCE65);
		validateInstance(Validation.INSTANCE65);
		assertTrue(true);
	}
	
	@Test
	public void _validateInstance71() {
		Utils.setWorkingDictSection(Validation.INSTANCE71);
		validateInstance(Validation.INSTANCE71);
		assertTrue(true);
	}
	
	@Test
	public void _validateInstance72() {
		Utils.setWorkingDictSection(Validation.INSTANCE72);
		validateInstance(Validation.INSTANCE72);
		assertTrue(true);
	}
	
	@Test
	public void _validateInstance73() {
		Utils.setWorkingDictSection(Validation.INSTANCE73);
		validateInstance(Validation.INSTANCE73);
		assertTrue(true);
	}
	
	@Test
	public void _validateInstance91() {
		Utils.setWorkingDictSection(Validation.INSTANCE91);
		validateInstance(Validation.INSTANCE91);
		assertTrue(true);
	}
	
	@Test
	public void _validateInstance92() {
		Utils.setWorkingDictSection(Validation.INSTANCE92);
		validateInstance(Validation.INSTANCE92);
		assertTrue(true);
	}
	
	@Test
	public void _validateInstance93() {
		Utils.setWorkingDictSection(Validation.INSTANCE93);
		validateInstance(Validation.INSTANCE93);
		assertTrue(true);
	}
	
	@Test
	public void _validateInstance94() {
		Utils.setWorkingDictSection(Validation.INSTANCE94);
		validateInstance(Validation.INSTANCE94);
		assertTrue(true);
	}
	
	@Test
	public void _validateInstance95() {
		Utils.setWorkingDictSection(Validation.INSTANCE95);
		validateInstance(Validation.INSTANCE95);
		assertTrue(true);
	}
	
	@Test
	public void _validateInstance96() {
		Utils.setWorkingDictSection(Validation.INSTANCE96);
		validateInstance(Validation.INSTANCE96);
		assertTrue(true);
	}	
	
	
	public void validateInstance(String instanceName) {
		System.out.println("Evaluating instance " + instanceName + " validation.");
		
		for (int i=0; i < Validation.INSTANCES.get(instanceName); i++) {
			int studentId = i+1;
						
			MappingParsed golden = getGolden(instanceName, studentId);
			MappingParsed output = getOutput(instanceName, studentId);
			
			EvaluationResult result = evaluate(golden, output);
			logResult(golden, result, instanceName, studentId);
			saveStats(golden, result, instanceName, studentId);
			
			assertTrue(true);
		}
	}
	
	
	/**
	 * Returns third MappingParsed, holding goldenMapping's info, which wasn't contained in the outputMapping. 
	 * 
	 * @param golden = target mapping
	 * @param output = computed mapping
	 * @return
	 */
	private EvaluationResult evaluate(MappingParsed golden, MappingParsed output) {
		Utils.validateNotNull(golden);
		Utils.validateNotNull(output);
		
		Map<String,String> missingMap = new HashMap<String, String>();
		Map<String,String[]> missingTransformations = new HashMap<String, String[]>();
		
		Map<String,String> overflowMap = new HashMap<String, String>();
		Map<String,String[]> overflowTransformations = new HashMap<String, String[]>();

		/* MAPPING PAIRS */
		for (String entitySet : golden.getMap().keySet()) {
			if (!output.getMap().containsKey(entitySet) || 
				!StringUtils.areEqualByNameParts(golden.getMap().get(entitySet), output.getMap().get(entitySet))) {
				missingMap.put(entitySet, golden.getMap().get(entitySet));
			}
		}
		for (String entitySet : output.getMap().keySet()) {
			if (StringUtils.areEqual(entitySet,MappingUtils.EMPTY_CODE) || StringUtils.areEqual(golden.getMap().get(entitySet),MappingUtils.EMPTY_CODE)) {
				// skip empty pairs
				continue;
			}
			if (!golden.getMap().containsKey(entitySet) || 
				!StringUtils.areEqualByNameParts(output.getMap().get(entitySet), golden.getMap().get(entitySet))) {
				overflowMap.put(entitySet, output.getMap().get(entitySet));
			}
		}
		
		/* MAPPING TRANSFORMATIONS */
		for (String transformation : golden.getTransformations().keySet()) {
			if (!CollectionUtils.containsIgnoreCase(TranslationConstants.TRANSLATABLE_TRANSFORMATIONS, transformation)) {
				// ignore not-translatable transformations
				continue;
			}
			if (!output.getTransformations().containsKey(transformation)) {
				missingTransformations.put(transformation, golden.getTransformations().get(transformation));
			} else {
				if (isArgumentOrderInsensitiveTransformation(transformation)) {
					if (!StringUtils.equalsIgnoreOrder(golden.getTransformations().get(transformation), output.getTransformations().get(transformation), LanguageProcessor.getImplementation())) {
						missingTransformations.put(transformation, golden.getTransformations().get(transformation));
					}
				} else {
					if (!StringUtils.equals(golden.getTransformations().get(transformation), output.getTransformations().get(transformation), LanguageProcessor.getImplementation())) {
						missingTransformations.put(transformation, golden.getTransformations().get(transformation));
					}
				}
			}
		}
		for (String transformation : output.getTransformations().keySet()) {
			if (!CollectionUtils.containsIgnoreCase(TranslationConstants.TRANSLATABLE_TRANSFORMATIONS, transformation)) {
				// ignore not-translatable transformations
				continue;
			}
			if (!golden.getTransformations().containsKey(transformation)) {
				overflowTransformations.put(transformation, output.getTransformations().get(transformation));
			} else {
				if (isArgumentOrderInsensitiveTransformation(transformation)) {
					if (!StringUtils.equalsIgnoreOrder(output.getTransformations().get(transformation),golden.getTransformations().get(transformation), LanguageProcessor.getImplementation())) {
						overflowTransformations.put(transformation, output.getTransformations().get(transformation));
					}
				} else {
					if (!StringUtils.equals(output.getTransformations().get(transformation),golden.getTransformations().get(transformation), LanguageProcessor.getImplementation())) {
						overflowTransformations.put(transformation, output.getTransformations().get(transformation));
					}
				}
			}
		}
		
		return new EvaluationResult(
				new MappingParsed(missingMap,missingTransformations),
				new MappingParsed(overflowMap,overflowTransformations)
				);
	}
	
	private MappingParsed getOutput(String instanceName, int studentId) {
		try {
			return parseFile(Validation.getOutputPath() + instanceName + "\\s" + studentId + ".txt");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private MappingParsed getGolden(String instanceName, int studentId) {
		try {
			return parseFile(Validation.getGoldenPath() + instanceName + "\\s" + studentId + ".txt");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private MappingParsed parseFile(String filepath) throws FileNotFoundException {
		
		Map<String,String> map = new HashMap<String, String>();
		Map<String,String[]> transformations = new HashMap<String, String[]>();
		
		Scanner scanner = new Scanner(new FileReader(filepath));
		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			line = line.trim().toLowerCase(); // evaluation is case insensitive
			if (line.length() < 1) { // filter blank lines
				continue;
			}
			if (line.contains(PrintUtils.DELIMITER_COLON)) {	// it's transformation
				
				String[] lineSplit = line.split(PrintUtils.DELIMITER_COLON);
				transformations.put(lineSplit[0], lineSplit[1].split(PrintUtils.DELIMITER_DASH));
				
			} else {	// it's mapping pair
				String[] lineSplit = line.split(PrintUtils.DELIMITER_DASH);
				
				if (lineSplit.length == 1) {
					map.put(lineSplit[0],MappingUtils.EMPTY_CODE);
				} else {
					map.put(lineSplit[0],lineSplit[1]);
				}
			}
		}
		
		return new MappingParsed(map,transformations);		
	}
	
	private void logResult(MappingParsed golden, EvaluationResult result, String instanceName, int studentId) {
		String filepath = Validation.getResultPath() + instanceName + "\\s" + studentId + ".txt";
		Logger logger = new Logger(filepath);
		logger.logValidationEvaluationResult(golden, result);
		logger.close();
		
	}

	
	@Test
	public void reportResultStatistics() {
		Map<String,int[]> stats = ValidationEvaluatorStatistics.getResultStats();
		
		for (String instanceName : stats.keySet()) {
			int[] stat = stats.get(instanceName);
			System.out.println("\n"+instanceName);
			System.out.println(ValidationEvaluatorUtils.getPairsResultMessage(stat[0], stat[1], stat[2]));
			System.out.println(ValidationEvaluatorUtils.getTransformationResultMessage(stat[3], stat[4], stat[5]));
		}
		
		int[] stat = ValidationEvaluatorStatistics.getTotalStats();
		System.out.println("\nTotal instances ran: " + ValidationEvaluatorStatistics.getInstanceCounter());
		System.out.println(ValidationEvaluatorUtils.getPairsResultMessage(stat[0], stat[1], stat[2]));
		System.out.println(ValidationEvaluatorUtils.getTransformationResultMessage(stat[3], stat[4], stat[5]));
	}

	
	
	private void saveStats(MappingParsed golden, EvaluationResult result, String instanceName, int studentId) {
		// add current instance stats
		int[] stats = ValidationEvaluatorUtils.parseEvaluationResult(golden, result);
		ValidationEvaluatorStatistics.getResultStats().put(instanceName,stats);
		
		// increment total stats
		int[] totalStats = ValidationEvaluatorStatistics.getTotalStats();
		totalStats[0] += stats[0];
		totalStats[1] += stats[1];
		totalStats[2] += stats[2];
		totalStats[3] += stats[3];
		totalStats[4] += stats[4];
		totalStats[5] += stats[5];
		
		ValidationEvaluatorStatistics.incrementInstanceCounter();
	}
}
