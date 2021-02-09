package sk.trizna.erm_comparison.comparing.translator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import sk.trizna.erm_comparison.common.PrintUtils;
import sk.trizna.erm_comparison.common.TranslationUtils;
import sk.trizna.erm_comparison.common.Utils;
import sk.trizna.erm_comparison.comparing.ERModelDiff;
import sk.trizna.erm_comparison.comparing.ERModelDiffReport;
import sk.trizna.erm_comparison.entity_relationship_model.Association;
import sk.trizna.erm_comparison.entity_relationship_model.Attribute;
import sk.trizna.erm_comparison.entity_relationship_model.Attributed;
import sk.trizna.erm_comparison.entity_relationship_model.EntitySet;
import sk.trizna.erm_comparison.entity_relationship_model.Named;
import sk.trizna.erm_comparison.entity_relationship_model.Relationship;
import sk.trizna.erm_comparison.language_processing.LanguageProcessor;

public class AttributedTranslator {

	private LanguageProcessor dictionary;
	
	/**
	 * Fills the entitySet parts of {@link ERModelDiffReport}.
	 * @param report
	 * @param diff
	 */
	public void translate(ERModelDiffReport report, ERModelDiff diff) {
		Utils.validateNotNull(report);
		Utils.validateNotNull(diff);
		
		translateEntitySets(report, diff);
		translateRelationships(report, diff);
	}
	
	private void translateEntitySets(ERModelDiffReport report, ERModelDiff diff) {
		for (EntitySet studentEntitySet : diff.getStudentModel().getEntitySets()) {
			if (studentEntitySet.getMappedTo() == null) {
				continue;
			}
			translateAttributed(studentEntitySet.getMappedTo(),studentEntitySet,report);
		}
	}
	
	private void translateRelationships(ERModelDiffReport report, ERModelDiff diff) {
		Map<Relationship,Relationship> relMap = diff.getRelationshipMap();
		
		for (Relationship rel : relMap.keySet()) {
			Relationship relMapped = relMap.get(rel);
			if (rel instanceof Association && relMapped instanceof Association) {
				translateAttributed((Association)rel,(Association)relMapped, report);
			}
		}
	}
	
	private void translateAttributed(Attributed exemplarAttributed, Attributed studentAttributed, ERModelDiffReport report) {
		
		List<Attribute> missingAttrs = new ArrayList<Attribute>();
		List<Attribute> additionalAttrs = new ArrayList<Attribute>();
		
		for (Attribute studentAttribute : studentAttributed.getAttributes()) {
			if (!getDictionary().contains(exemplarAttributed.getAttributes(), studentAttribute.getText())) {
				missingAttrs.add(studentAttribute);
			}
		}
		for (Attribute exemplarAttribute : exemplarAttributed.getAttributes()) {
			if (!getDictionary().contains(studentAttributed.getAttributes(), exemplarAttribute.getText())) {
				additionalAttrs.add(exemplarAttribute);
			}
		}
		
		if (!missingAttrs.isEmpty()) {
			missingAttrs.forEach(attr -> {
				report.getMissingAttributesNotes().add(getAttributedTranslation(studentAttributed, attr, true));
			});
		}
		if (!additionalAttrs.isEmpty()) {
			additionalAttrs.forEach(attr -> {
				report.getAdditionalAttributesNotes().add(getAttributedTranslation(studentAttributed, attr, false));
			});
		}
	}
	
	private String getAttributedTranslation(Attributed studentAttributed, Attribute attribute, boolean isMissing) {
		if (isMissing) {
			return TranslationUtils.translateCreateAttribute(getAttributedName(studentAttributed), attribute.getText());
		} else {
			return TranslationUtils.translateRemoveAttribute(getAttributedName(studentAttributed), attribute.getText());
		}
	}
	
	private String getAttributedName(Attributed attributed) {
		if (attributed instanceof EntitySet) {
			return ((Named)attributed).getName().getText();
		} else if (attributed instanceof Association) {
			return PrintUtils.getNameByIncidentEntitySets((Association)attributed);
		}
		return attributed.toString();
	}
	
	public LanguageProcessor getDictionary() {
		if (dictionary == null) {
			dictionary = LanguageProcessor.getImplementation();
		}
		return dictionary;
	}
}
