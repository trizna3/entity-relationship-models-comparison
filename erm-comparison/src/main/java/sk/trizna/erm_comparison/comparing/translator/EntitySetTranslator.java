package sk.trizna.erm_comparison.comparing.translator;

import java.util.Map;

import sk.trizna.erm_comparison.common.TranslationUtils;
import sk.trizna.erm_comparison.common.Utils;
import sk.trizna.erm_comparison.comparing.ERModelDiff;
import sk.trizna.erm_comparison.comparing.ERModelDiffReport;
import sk.trizna.erm_comparison.entity_relationship_model.EntitySet;
import sk.trizna.erm_comparison.language_processing.LanguageProcessor;

public class EntitySetTranslator {
	
	private LanguageProcessor dictionary;
	
	/**
	 * Fills the entitySet parts of {@link ERModelDiffReport}.
	 * @param report
	 * @param diff
	 */
	public void translate(ERModelDiffReport report, ERModelDiff diff) {
		Utils.validateNotNull(report);
		Utils.validateNotNull(diff);
		
		translateMissingEntitySet(report,diff);
		translateAdditionalEntitySet(report,diff);
		translateEntitySetRenaming(report,diff);
	}
	
	private void translateMissingEntitySet(ERModelDiffReport report, ERModelDiff diff) {
		diff.getNotMappedExemplarEntitySets().stream().forEach(es -> {
			report.getMissingEntitySetsNotes().add(TranslationUtils.translateCreateEntitySet(es.getNameText()));
		});
	}
	
	private void translateAdditionalEntitySet(ERModelDiffReport report, ERModelDiff diff) {
		diff.getNotMappedStudentEntitySets().stream().forEach(es -> {
			report.getAdditionalEntitySetsNotes().add(TranslationUtils.translateRemoveEntitySet(es.getNameText()));
		});
	}
	
	private void translateEntitySetRenaming(ERModelDiffReport report, ERModelDiff diff) {
		Map<EntitySet,EntitySet> entitySetMap = diff.getEntitySetMap();
		for (EntitySet exemlar : entitySetMap.keySet()) {
			EntitySet student = entitySetMap.get(exemlar);
			
			if (!getDictionary().areEqual(exemlar.getNameText(),student.getNameText())) {
				report.getEntitySetNamesNotes().add(TranslationUtils.translateRenameEntitySet(exemlar.getNameText(),student.getNameText()));
			}
		}
	}
	
	public LanguageProcessor getDictionary() {
		if (dictionary == null) {
			dictionary = LanguageProcessor.getImplementation();
		}
		return dictionary;
	}
}
