package sk.trizna.erm_comparison.tests;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

import sk.trizna.erm_comparison.common.utils.PrintUtils;
import sk.trizna.erm_comparison.common.utils.Utils;
import sk.trizna.erm_comparison.tests.ValidationEvaluator.TransformationParsed;
import sk.trizna.erm_comparison.tests.common.ValidationEvaluatorUtils;

public class Logger extends sk.trizna.erm_comparison.common.Logger{
	
	public Logger(String filepath) {
		super(filepath);
	}

	public void preLogMapping(String instanceName, int studentId) {
		String goldenPath = Validation.getGoldenPath() + instanceName + "\\s" + studentId + ".txt";
		String outputPath = Validation.getOutputPath() + instanceName + "\\s" + studentId + ".txt";
		
		getWriter().write("-- GOLDEN --\n");
		preLogMappingInternal(goldenPath);
		getWriter().write("-- OUTPUT --\n");
		preLogMappingInternal(outputPath);
	}
	
	private void preLogMappingInternal(String path) {
		try {
			Scanner scanner = new Scanner(new FileReader(path));
			while (scanner.hasNextLine()) {
				getWriter().write(scanner.nextLine()+"\n");
			}
			getWriter().write("\n"+MAPPING_DELIMITER+"\n");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Writes validation evaluation result
	 * 
	 * @param golden = target mapping
	 * @param result = difference of golden mapping - found mapping
	 */
	public void logValidationEvaluationResult(ValidationEvaluator.MappingParsed golden, ValidationEvaluator.EvaluationResult result) {
		Utils.validateNotNull(golden);
		Utils.validateNotNull(result);
		
		ValidationEvaluator.MappingParsed missing = result.getMissing();
		ValidationEvaluator.MappingParsed overflow = result.getOverflow();
		
		int[] resultStats = ValidationEvaluatorUtils.parseEvaluationResult(golden, result);
		
		/* MAPPING PAIRS */
		int matchedPairs = resultStats[0];
		int goldenPairs = resultStats[1];
		int overPairs = resultStats[2];
		
		getWriter().println(ValidationEvaluatorUtils.getPairsResultMessageWhole(matchedPairs, goldenPairs, overPairs));
		getWriter().println("Missing pairs: " + (missing.getMap().keySet().isEmpty() ? PrintUtils.DELIMITER_DASH : ""));
		for (String entitySet : missing.getMap().keySet()) {
			getWriter().println(" " + entitySet + PrintUtils.DELIMITER_DASH + missing.getMap().get(entitySet));
		}
		getWriter().println("Additional pairs: " + (overflow.getMap().keySet().isEmpty() ? PrintUtils.DELIMITER_DASH : ""));
		for (String entitySet : overflow.getMap().keySet()) {
			getWriter().println(" " + entitySet + PrintUtils.DELIMITER_DASH + overflow.getMap().get(entitySet));
		}
		
		/* MAPPING TRANSFORMATIONS */
		int matchedTrans = resultStats[3];
		int goldenTrans = resultStats[4];
		int overTrans = resultStats[5];
		
		
		getWriter().println("\n"+ValidationEvaluatorUtils.getTransformationResultMessageWhole(matchedTrans, goldenTrans, overTrans));
		getWriter().println("Missing transformations: " + (missing.getTransformations().isEmpty() ? PrintUtils.DELIMITER_DASH : ""));
		for (TransformationParsed transformation : missing.getTransformations()) {
			getWriter().println(" " + transformation.getCode() + PrintUtils.DELIMITER_COLON + String.join(PrintUtils.DELIMITER_DASH,transformation.getArgs()));
		}
		getWriter().println("Additional transformations: " + (overflow.getTransformations().isEmpty() ? PrintUtils.DELIMITER_DASH : ""));
		for (TransformationParsed transformation : overflow.getTransformations()){
			getWriter().println(" " + transformation.getCode() + PrintUtils.DELIMITER_COLON + String.join(PrintUtils.DELIMITER_DASH,transformation.getArgs()));
		}
	}
}
