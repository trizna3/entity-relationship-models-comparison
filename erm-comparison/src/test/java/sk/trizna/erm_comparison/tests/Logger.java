package sk.trizna.erm_comparison.tests;

import sk.trizna.erm_comparison.common.PrintUtils;
import sk.trizna.erm_comparison.common.Utils;

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
		
		/* MAPPING PAIRS */
		int goldenPairs = golden.getMap().keySet().size();
		int missingPairs = missing.getMap().keySet().size();
		int overPairs = overflow.getMap().keySet().size();
		
		getWriter().println("Mapping " + (goldenPairs-missingPairs) + "/" + goldenPairs + " pairs, " + overPairs + " additional pairs.");
		getWriter().println("Missing pairs: " + (missing.getMap().keySet().isEmpty() ? PrintUtils.DELIMITER_DASH : ""));
		for (String entitySet : missing.getMap().keySet()) {
			getWriter().println(" " + entitySet + PrintUtils.DELIMITER_DASH + missing.getMap().get(entitySet));
		}
		getWriter().println("Additional pairs: " + (overflow.getMap().keySet().isEmpty() ? PrintUtils.DELIMITER_DASH : ""));
		for (String entitySet : overflow.getMap().keySet()) {
			getWriter().println(" " + entitySet + PrintUtils.DELIMITER_DASH + overflow.getMap().get(entitySet));
		}
		
		/* MAPPING TRANSFORMATIONS */
		int goldenTrans = golden.getTransformations().keySet().size();
		int missingTrans = missing.getTransformations().keySet().size();
		int overTrans = overflow.getTransformations().keySet().size();
		
		getWriter().println("\nMapping  " + (goldenTrans-missingTrans) + "/" + goldenTrans + " transformations, " + overTrans + " additional transformations.");
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
