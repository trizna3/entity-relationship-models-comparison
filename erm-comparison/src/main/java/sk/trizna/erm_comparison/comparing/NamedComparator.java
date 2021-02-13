package sk.trizna.erm_comparison.comparing;

import sk.trizna.erm_comparison.common.utils.StringUtils;
import sk.trizna.erm_comparison.common.utils.Utils;
import sk.trizna.erm_comparison.entity_relationship_model.ERModelElementName;
import sk.trizna.erm_comparison.entity_relationship_model.Named;
import sk.trizna.erm_comparison.language_processing.LanguageProcessor;

public class NamedComparator {
	private static NamedComparator INSTANCE = new NamedComparator();
	private LanguageProcessor dictionary;
	
	public static NamedComparator getInstance() {
		return INSTANCE;
	}

	public double compareSymmetric(Named named1, Named named2) {
		Utils.validateNotNull(named1);
		Utils.validateNotNull(named2);
		
		if (named1.getName() == null && named2.getName() == null) {
			return 1;
		}
		if (named1.getName() == null || named2.getName() == null) {
			return 0;
		}
		
		double max = 0;
		double value = 0;
		
		max += 1;
		value += getNamesSimilarity(named1.getName(),named2.getName());
		
		return value/max;
	}
	
	public double getNamesSimilarity(ERModelElementName name1, ERModelElementName name2) {
		if (!name1.isComposite() && !name2.isComposite()) {
			return getSimpleNamesSimilarity(name1, name2);
		}
		if (name1.isComposite() && name2.isComposite()) {
			return getCompositeNamesSimilarity(name1, name2);
		}
		if (name1.isComposite()) {
			return getSimpleCompositeNamesSimilarity(name2, name1);
		}
		if (name2.isComposite()) {
			return getSimpleCompositeNamesSimilarity(name1, name2);
		}
		return 0;
	}
	
	private double getSimpleNamesSimilarity(ERModelElementName name1, ERModelElementName name2) {
		return getDictionary().getSimilarity(name1.getName(), name2.getName());
	}
	
	private double getSimpleCompositeNamesSimilarity(ERModelElementName simpleName, ERModelElementName compositeName) {
		
		double max = 0;
		
		for (String namePart : StringUtils.getAllNameParts(compositeName.getText())) {
			double similarity = getDictionary().getSimilarity(namePart, simpleName.getName());
			
			if (similarity > max) {
				max = similarity;
			}
		}
		
		return max;
	}
	
	private double getCompositeNamesSimilarity(ERModelElementName name1, ERModelElementName name2) {
		
		double max = 0;
		
		for (String namePart1 : StringUtils.getAllNameParts(name1.getText())) {
			for (String namePart2 : StringUtils.getAllNameParts(name2.getText())) {
				double similarity = getDictionary().getSimilarity(namePart1, namePart2);
				
				if (similarity > max) {
					max = similarity;
				}
			}
		}
		
		return max;
	}
	
	private LanguageProcessor getDictionary() {
		if (dictionary == null) {
			dictionary = LanguageProcessor.getImplementation();
		}
		return dictionary;
	}
}
