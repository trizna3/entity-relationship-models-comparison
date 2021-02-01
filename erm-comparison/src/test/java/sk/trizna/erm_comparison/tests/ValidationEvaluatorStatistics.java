package sk.trizna.erm_comparison.tests;

import java.util.HashMap;
import java.util.Map;

public class ValidationEvaluatorStatistics {
	
	/**
	 * Keeps key=instance -> evaluation result statistics, format specified in @see ValidationEvaluatorUtils#parseEvaluationResult(MappingParsed, EvaluationResult).
	 */
	private static Map<String,int[]> resultStats;
	/**
	 * keeps result statistics (format specified in @see ValidationEvaluatorUtils#parseEvaluationResult(MappingParsed, EvaluationResult)), summed over all instances run.
	 */
	private static int[] totalStats;
	
	public static Map<String,int[]> getResultStats() {
		if (resultStats == null) {
			resultStats = new HashMap<>();
		}
		return resultStats;
	}
	
	public static int[] getTotalStats() {
		if (totalStats == null) {
			totalStats = new int[] {0,0,0,0,0,0};
		}
		return totalStats;
	}
}
