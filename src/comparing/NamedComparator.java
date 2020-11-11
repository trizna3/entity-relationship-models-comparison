package comparing;

import common.Utils;
import entityRelationshipModel.Named;
import languageProcessing.Dictionary;
import languageProcessing.LanguageProcessor;

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
		
		max += 2;
		value += 2 * getDictionary().getSimilarity(named1.getName().getName(), named2.getName().getName());
		
		return value/max;
	}
	
	private LanguageProcessor getDictionary() {
		if (dictionary == null) {
			dictionary = new Dictionary();
		}
		return dictionary;
	}
}
