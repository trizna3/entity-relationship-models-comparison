package sk.trizna.erm_comparison.comparing.translator;

import sk.trizna.erm_comparison.common.Utils;
import sk.trizna.erm_comparison.comparing.ERModelDiff;
import sk.trizna.erm_comparison.comparing.ERModelDiffReport;
import sk.trizna.erm_comparison.transformations.Transformation;

public class TransformationTranslator {
	
	/**
	 * Fills the transformation parts of {@link ERModelDiffReport}.
	 * 
	 * @param report
	 * @param diff
	 */
	public void translate(ERModelDiffReport report, ERModelDiff diff) {
		Utils.validateNotNull(report);
		Utils.validateNotNull(diff);
		
		for (Transformation transformation : diff.getTransformationsMade()) {
			String translation = translateTransformation(transformation);
			if (translation != null) {
				report.getTransformationNotes().add(translation);
			}
		}
	}
	
	/**
	 * Translates transformation to text student-readable message, containing info describing student's solution error.
	 * Ignores equivalent transformations.
	 * 
	 * @param transformation
	 * @return
	 */
	private static String translateTransformation(Transformation transformation) {
		throw new UnsupportedOperationException("Not implemented yet");
	}
}
