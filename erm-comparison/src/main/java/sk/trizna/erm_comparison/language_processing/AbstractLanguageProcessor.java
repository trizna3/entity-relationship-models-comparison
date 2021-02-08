package sk.trizna.erm_comparison.language_processing;

import java.util.Collection;

import sk.trizna.erm_comparison.common.StringUtils;
import sk.trizna.erm_comparison.common.Utils;
import sk.trizna.erm_comparison.common.enums.SimilarityConstantsUtils;
import sk.trizna.erm_comparison.entity_relationship_model.ERText;

public abstract class AbstractLanguageProcessor implements LanguageProcessor {

	protected abstract Double getSimilarityInternal(String word1, String word2);
    protected abstract String getLemma(String word);
   
    /**
     * {@inheritDoc}
     */
    public double getSimilarity(String word1, String word2) {
		word1 = getLemma(word1);
		word2 = getLemma(word2);
		
		if (StringUtils.areEqual(word1, word2)) {
			return 1;
		}
		return (Double) Utils.firstNonNull(getSimilarityInternal(word1, word2), Double.valueOf(0));
	}
    
    /**
     * {@inheritDoc}
     */
	public boolean contains(Collection<? extends ERText> words, String word) {
		Utils.validateNotNull(words);
		Utils.validateNotNull(word);
		
		for (ERText text : words) {
			if (areEqual(text.getText(), word)) {
				return true;
			}
		}
		
		return false;
	}
	
	public boolean areEqual(String word1, String word2) {
		Utils.validateNotNull(word1);
		Utils.validateNotNull(word2);
		
		return getSimilarity(word1, word2) >= SimilarityConstantsUtils.getLanguageProcessorEqualityTreshold();
	}
}
