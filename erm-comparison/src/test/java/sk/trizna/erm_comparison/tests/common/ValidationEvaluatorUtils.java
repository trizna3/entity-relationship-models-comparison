package sk.trizna.erm_comparison.tests.common;

import sk.trizna.erm_comparison.common.utils.Utils;
import sk.trizna.erm_comparison.tests.ValidationEvaluator;

public class ValidationEvaluatorUtils {
	
	/**
	 * Computes evaluation result statistics, returns array: [
	 * 		target pairs found count, 
	 * 		target pairs count, 
	 * 		additional pairs found count,
	 * 		target transformations found count,
	 * 		target transformations count,
	 * 		additional transformations found count
	 * ] 
	 */
	public static int[] parseEvaluationResult(ValidationEvaluator.MappingParsed golden, ValidationEvaluator.EvaluationResult result) {
		Utils.validateNotNull(golden);
		Utils.validateNotNull(result);
		
		ValidationEvaluator.MappingParsed missing = result.getMissing();
		ValidationEvaluator.MappingParsed overflow = result.getOverflow();
		
		/* MAPPING PAIRS */
		int goldenPairs = golden.getMap().keySet().size();
		int missingPairs = missing.getMap().keySet().size();
		int overPairs = overflow.getMap().keySet().size();
		
		/* MAPPING TRANSFORMATIONS */
		int goldenTrans = golden.getTransformations().size();
		int missingTrans = missing.getTransformations().size();
		int overTrans = overflow.getTransformations().size();
		
		return new int[] {
				(goldenPairs-missingPairs),
				goldenPairs,
				overPairs,
				(goldenTrans-missingTrans),
				goldenTrans,
				overTrans
		};
	}
	
	public static String getPairsResultMessageWhole(int matchedPairs, int goldenPairs, int overPairs) {
		return "Mapping " + matchedPairs + "/" + goldenPairs + " pairs, " + overPairs + " additional pairs.";
	}
	
	public static String getTransformationResultMessageWhole(int matchedTrans, int goldenTrans, int overTrans) {
		return "Mapping  " + matchedTrans + "/" + goldenTrans + " transformations, " + overTrans + " additional transformations.";
	}
	
	public static String getPairsResultMessage(double matchedPairs, double goldenPairs, double overPairs) {
		return "Mapping " + matchedPairs + "/" + goldenPairs + " pairs, " + overPairs + " additional pairs.";
	}
	
	public static String getTransformationResultMessage(double matchedTrans, double goldenTrans, double overTrans) {
		return "Mapping  " + matchedTrans + "/" + goldenTrans + " transformations, " + overTrans + " additional transformations.";
	}
}
