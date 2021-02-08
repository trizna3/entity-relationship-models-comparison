package sk.trizna.erm_comparison.language_processing;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sk.trizna.erm_comparison.common.multi_key_config.DictConfigManager;

/**
 * @author - Adam Trizna
 */

/**
 * Naive language processor. "Measures" words meaning similarity 1 or 0, whether
 * the word-pair is in manually pre-defined dictionary.
 */
class Dictionary extends AbstractLanguageProcessor {

	private static final Dictionary INSTANCE = new Dictionary();
	private Map<String,Map<String,Double>> cache;
	private StanfordLemmatizer stanfordLemmatizer;
	private DictConfigManager dictConfigManager;
	
	
	static Dictionary getInstance() {
		return INSTANCE;
	}
	
	private Dictionary() {
		loadFromConfig();
	}

	@Override
	public Double getSimilarityInternal(String word1, String word2) {
		if (getCache().containsKey(word1) && getCache().get(word1).containsKey(word2)) {
			return getCache().get(word1).get(word2);
		}
		if (getCache().containsKey(word2) && getCache().get(word2).containsKey(word1)) {
			return getCache().get(word2).get(word1);
		}
		return Double.valueOf(0);
	}
	
	@Override
	public String getLemma(String word) {
		return getStanfordLemmatizer().lemmatizeWord(word);
	}

	private Map<String,Map<String,Double>> getCache() {
		if (cache == null) {
			cache = new HashMap<>();
		}
		return cache;
	}
	
	/**
	 * Saves word pair to cache.
	 * Words must come in lemmatized!
	 * 
	 * @param word1
	 * @param word2
	 * @param similarity
	 */
	private void saveToCache(String word1, String word2, double similarity) {
		if (!getCache().containsKey(word1)) {
			getCache().put(word1,new HashMap<>());
		}
		getCache().get(word1).put(word2, Double.valueOf(similarity));
		
		if (!getCache().containsKey(word2)) {
			getCache().put(word2,new HashMap<>());
		}
		getCache().get(word2).put(word1, Double.valueOf(similarity));
	}
	
	private void loadFromConfig() {
		List<String[]> configData = getDictConfigManager().getResourceData();
		
		for (String[] line : configData) {
			if (line.length == 2) {
				// add similarity pair
				saveToCache(getLemma(line[0]), getLemma(line[1]), 1);
			} else if (line.length > 2) {
				// add similarity pair for all doubles
				for (int i = 0; i < line.length; i++) {
					for (int j = i; j < line.length; j++) {
						saveToCache(getLemma(line[i]), getLemma(line[j]), 1);
					}
				}
			}
		}
	}

	private StanfordLemmatizer getStanfordLemmatizer() {
		if (stanfordLemmatizer == null) {
			stanfordLemmatizer = StanfordLemmatizer.getInstance();
		}
		return stanfordLemmatizer;
	}

	private DictConfigManager getDictConfigManager() {
		if (dictConfigManager == null) {
			dictConfigManager = DictConfigManager.getInstance();
		}
		return dictConfigManager;
	}
}
