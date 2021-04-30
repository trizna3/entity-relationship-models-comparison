package sk.trizna.erm_comparison.language_processing;

import java.util.Collection;

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
	 * Returns similarity measurement of given word pair. possible value from interval <0,1> 
     * @param word1
     * @param word2
     */
	public double getSimilarity(String word1, String word2);
    
	/**
	 * Returns true iff target word is equal with any of given words element.
	 * Equality is determined by languageProcessor's similarity measure.
	 * 
	 * @param words
	 * @param word
	 */
	public boolean contains(Collection<? extends ERText> words, String word);
	
	/**
	 * Returns true iff given word pair is equal.
	 * Equality is determined by languageProcessor's similarity measure.
	 * 
	 * @param word1
	 * @param word2
	 */
	public boolean areEqual(String word1, String word2);
	
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
	
	public void clearCache();
	
	public void clearInstance();
}
