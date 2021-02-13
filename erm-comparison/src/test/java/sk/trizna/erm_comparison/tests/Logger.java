package sk.trizna.erm_comparison.tests;

import sk.trizna.erm_comparison.common.utils.PrintUtils;
import sk.trizna.erm_comparison.common.utils.Utils;
import sk.trizna.erm_comparison.tests.common.ValidationEvaluatorUtils;

public class Logger extends sk.trizna.erm_comparison.common.Logger{
	
	public Logger(String filepath) {
		super(filepath);
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
		
		getWriter().println(ValidationEvaluatorUtils.getPairsResultMessage(matchedPairs, goldenPairs, overPairs));
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
		
		
		getWriter().println("\n"+ValidationEvaluatorUtils.getTransformationResultMessage(matchedTrans, goldenTrans, overTrans));
		getWriter().println("Missing transformations: " + (missing.getTransformations().keySet().isEmpty() ? PrintUtils.DELIMITER_DASH : ""));
		for (String entitySet : missing.getTransformations().keySet()) {
			getWriter().println(" " + entitySet + PrintUtils.DELIMITER_COLON + String.join(PrintUtils.DELIMITER_DASH,missing.getTransformations().get(entitySet)));
		}
		getWriter().println("Additional transformations: " + (overflow.getTransformations().keySet().isEmpty() ? PrintUtils.DELIMITER_DASH : ""));
		for (String entitySet : overflow.getTransformations().keySet()) {
			getWriter().println(" " + entitySet + PrintUtils.DELIMITER_COLON + String.join(PrintUtils.DELIMITER_DASH,overflow.getTransformations().get(entitySet)));
		}
	}
}
