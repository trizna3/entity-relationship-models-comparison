package sk.trizna.erm_comparison.comparing.translator;

import java.util.Collection;

import sk.trizna.erm_comparison.common.PrintUtils;
import sk.trizna.erm_comparison.common.TranslationUtils;
import sk.trizna.erm_comparison.common.Utils;
import sk.trizna.erm_comparison.comparing.ERModelDiff;
import sk.trizna.erm_comparison.comparing.ERModelDiffReport;
import sk.trizna.erm_comparison.entity_relationship_model.Association;
import sk.trizna.erm_comparison.entity_relationship_model.Generalization;
import sk.trizna.erm_comparison.entity_relationship_model.Relationship;

public class RelationshipTranslator {

	/**
	 * Fills the relationships parts of {@link ERModelDiffReport}.
	 * @param report
	 * @param diff
	 */
	public void translate(ERModelDiffReport report, ERModelDiff diff) {
		Utils.validateNotNull(report);
		Utils.validateNotNull(diff);
		
		Collection<Relationship> exemplarRels = diff.getRelationshipMap().keySet();
		Collection<Relationship> studentRels = diff.getRelationshipMap().values();
		
		diff.getExemplarModel().getRelationships().forEach(relationship -> {
			if (!exemplarRels.contains(relationship)) {
				report.getMissingRelationshipNotes().add(getTranslation(relationship, true));
			}
		});
		diff.getStudentModel().getRelationships().forEach(relationship -> {
			if (!studentRels.contains(relationship)) {
				report.getAdditionalRelationshipNotes().add(getTranslation(relationship, true));
			}
		});
	}
	
	private String getTranslation(Relationship relationship, boolean isMissing) {
		if (isMissing) {
			if (relationship instanceof Association) {
				return TranslationUtils.translateCreateAssociation(PrintUtils.getNameByIncidentEntitySets((Association)relationship));
			} else {
				Generalization gen = (Generalization) relationship;
				return TranslationUtils.translateCreateGeneralization(gen.getSuperEntitySet().getNameText(), gen.getSubEntitySet().getNameText());
			}
		} else {
			if (relationship instanceof Association) {
				return TranslationUtils.translateRemoveAssociation(PrintUtils.getNameByIncidentEntitySets((Association)relationship));
			} else {
				Generalization gen = (Generalization) relationship;
				return TranslationUtils.translateRemoveGeneralization(gen.getSuperEntitySet().getNameText(), gen.getSubEntitySet().getNameText());
			}
		}
	}
}
