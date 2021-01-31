package sk.trizna.erm_comparison.comparing;

import sk.trizna.erm_comparison.common.Utils;
import sk.trizna.erm_comparison.entity_relationship_model.Attribute;
import sk.trizna.erm_comparison.entity_relationship_model.Attributed;
import sk.trizna.erm_comparison.language_processing.LanguageProcessor;

public class AttributedComparator {
	
	private static AttributedComparator INSTANCE = new AttributedComparator();
	private LanguageProcessor dictionary;
	
	public static AttributedComparator getInstance() {
		return INSTANCE;
	}

	public double compareSymmetric(Attributed attributed1, Attributed attributed2) {
		Utils.validateNotNull(attributed1);
		Utils.validateNotNull(attributed2);
		
		double max = 0;
		double value = 0;
		
		for (Attribute attribute : attributed1.getAttributes()) {
			max += 1;
			if (getDictionary().contains(attributed2.getAttributes(), attribute != null ? attribute.getText() : null)) {
				value += 1;
			}
		}
		for (Attribute attribute : attributed2.getAttributes()) {
			max += 1;
			if (getDictionary().contains(attributed1.getAttributes(), attribute != null ? attribute.getText() : null)) {
				value += 1;
			}
		}
		
		return value == max ? 1 : value/max;
	}
	
	public double compareAsymmetric(Attributed subAttributed, Attributed superAttributed) {
		Utils.validateNotNull(subAttributed);
		Utils.validateNotNull(superAttributed);
		
		double max = 0;
		double value = 0;
		
		for (Attribute attribute : subAttributed.getAttributes()) {
			max += 1;
			if (getDictionary().contains(superAttributed.getAttributes(), attribute != null ? attribute.getText() : null)) {
				value += 1;
			}
		}
		
		return value == max ? 1 : value/max;
	}

	private LanguageProcessor getDictionary() {
		if (dictionary == null) {
			dictionary = LanguageProcessor.getImplementation();
		}
		return dictionary;
	}
}
