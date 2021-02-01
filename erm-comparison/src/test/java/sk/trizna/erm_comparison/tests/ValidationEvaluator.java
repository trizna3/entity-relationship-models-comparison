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

import sk.trizna.erm_comparison.common.ArrayUtils;
import sk.trizna.erm_comparison.common.PrintUtils;
import sk.trizna.erm_comparison.common.StringUtils;
import sk.trizna.erm_comparison.common.Utils;
import sk.trizna.erm_comparison.common.enums.EnumTransformation;
import sk.trizna.erm_comparison.tests.common.ValidationEvaluatorUtils;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ValidationEvaluator {
	
	private static final Set<String> ARGUMENT_ORDER_INSENSITIVE_TRANSFORMATIONS = new HashSet<String>(Arrays.asList(
			EnumTransformation.REBIND_MN_TO_1NN1.toString(), 
			EnumTransformation.REBIND_1NN1_TO_MN.toString(), 
			EnumTransformation.CONTRACT_11_ASSOCIATION.toString(), 
			EnumTransformation.REBIND_NARY_ASSOCIATION.toString())
			);
	

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
	public void _validateInstance61() {
		validateInstance(Validation.INSTANCE61);
		assertTrue(true);
	}
	
	@Test
	public void _validateInstance62() {
		validateInstance(Validation.INSTANCE62);
		assertTrue(true);
	}
	
	@Test
	public void _validateInstance63() {
		validateInstance(Validation.INSTANCE63);
		assertTrue(true);
	}
	
	@Test
	public void _validateInstance64() {
		validateInstance(Validation.INSTANCE64);
		assertTrue(true);
	}
	
	@Test
	public void _validateInstance65() {
		validateInstance(Validation.INSTANCE65);
		assertTrue(true);
	}
	
	@Test
	public void _validateInstance71() {
		validateInstance(Validation.INSTANCE71);
		assertTrue(true);
	}
	
	@Test
	public void _validateInstance72() {
		validateInstance(Validation.INSTANCE72);
		assertTrue(true);
	}
	
	@Test
	public void _validateInstance73() {
		validateInstance(Validation.INSTANCE73);
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
			if (!golden.getMap().containsKey(entitySet) || 
				!StringUtils.areEqualByNameParts(output.getMap().get(entitySet), golden.getMap().get(entitySet))) {
				overflowMap.put(entitySet, output.getMap().get(entitySet));
			}
		}
		
		/* MAPPING TRANSFORMATIONS */
		for (String transformation : golden.getTransformations().keySet()) {
			if (!output.getTransformations().containsKey(transformation)) {
				missingTransformations.put(transformation, golden.getTransformations().get(transformation));
			} else {
				if (ARGUMENT_ORDER_INSENSITIVE_TRANSFORMATIONS.contains(transformation)) {
					if (!ArrayUtils.equalsIgnoreOrder(golden.getTransformations().get(transformation), output.getTransformations().get(transformation))) {
						missingTransformations.put(transformation, golden.getTransformations().get(transformation));
					}
				} else {
					if (!ArrayUtils.equals(golden.getTransformations().get(transformation), output.getTransformations().get(transformation))) {
						missingTransformations.put(transformation, golden.getTransformations().get(transformation));
					}
				}
			}
		}
		for (String transformation : output.getTransformations().keySet()) {
			if (!golden.getTransformations().containsKey(transformation)) {
				overflowTransformations.put(transformation, output.getTransformations().get(transformation));
			} else {
				if (ARGUMENT_ORDER_INSENSITIVE_TRANSFORMATIONS.contains(transformation)) {
					if (!ArrayUtils.equalsIgnoreOrder(output.getTransformations().get(transformation),golden.getTransformations().get(transformation))) {
						overflowTransformations.put(transformation, output.getTransformations().get(transformation));
					}
				} else {
					if (!ArrayUtils.equals(output.getTransformations().get(transformation),golden.getTransformations().get(transformation))) {
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
				
				map.put(lineSplit[0],lineSplit[1]);
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
		System.out.println("\nTotal");
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
	}
}
