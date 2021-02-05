package sk.trizna.erm_comparison.language_processing;

import java.util.Collection;

import sk.trizna.erm_comparison.common.Utils;
import sk.trizna.erm_comparison.common.enums.EnumConstants;
import sk.trizna.erm_comparison.common.key_config.AppConfigManager;
import sk.trizna.erm_comparison.entity_relationship_model.ERText;

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
	
	public static LanguageProcessor getImplementation() {
		String languageProcessorImpl = AppConfigManager.getInstance().getResource(EnumConstants.CONFIG_LANGUAGE_PROCESSOR);
		
		if (EnumConstants.LP_DICTIONARY_IMPL.equals(languageProcessorImpl)) {
			return Dictionary.getInstance();
		}
		if (EnumConstants.LP_WORD2VEC_DICT_IMPL.equals(languageProcessorImpl)) {
			return Word2VecDictionary.getInstance();
		} 
		throw new IllegalStateException("No language processor implementation configured!");
	}
}