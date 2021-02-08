package sk.trizna.erm_comparison.comparing.translator;

import sk.trizna.erm_comparison.common.Utils;
import sk.trizna.erm_comparison.comparing.ERModelDiff;
import sk.trizna.erm_comparison.comparing.ERModelDiffReport;

public class RelationshipTranslator {

	/**
	 * Fills the relationships parts of {@link ERModelDiffReport}.
	 * @param report
	 * @param diff
	 */
	public void translate(ERModelDiffReport report, ERModelDiff diff) {
		Utils.validateNotNull(report);
		Utils.validateNotNull(diff);
		
	}
}
