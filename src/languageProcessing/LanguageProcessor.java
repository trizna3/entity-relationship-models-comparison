package languageProcessing;

import java.util.Collection;

import common.Utils;
import entityRelationshipModel.ERText;

/**
 * @author - Adam Trizna
 */

/**
 * Interface of language processors - any objects, able to measure words similarity (by their meaning / usage).
 */
public interface LanguageProcessor {

    /**
     * @param word1
     * @param word2
     * @return similarity measure of given word pair. <0,1>
     */
    public double getSimilarity(String word1, String word2);
    
	public default boolean contains(Collection<? extends ERText> words, String word) {
		Utils.validateNotNull(words);
		Utils.validateNotNull(word);
		
		for (ERText text : words) {
			if (getSimilarity(text.getText(), word) == 1) {	// greater than threshold?
				return true;
			}
		}
		
		return false;
	}
}
