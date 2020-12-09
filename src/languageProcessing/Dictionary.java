package languageProcessing;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import common.StringUtils;
import common.multiKeyConfig.DictConfigManager;

/**
 * @author - Adam Trizna
 */

/**
 * Naive language processor. "Measures" words meaning similarity 1 or 0, wether
 * the word-pair is in manually pre-defined dictionary.
 */
public class Dictionary implements LanguageProcessor {

	private static final Dictionary INSTANCE = new Dictionary();
	private Map<String,Map<String,Double>> cache;
	private StanfordLemmatizer stanfordLemmatizer;
	private DictConfigManager dictConfigManager;
	
	
	public static Dictionary getInstance() {
		return INSTANCE;
	}
	
	private Dictionary() {
		loadFromConfig();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public double getSimilarity(String word1, String word2) {
		word1 = getStanfordLemmatizer().lemmatizeWord(word1);
		word2 = getStanfordLemmatizer().lemmatizeWord(word2);
		
		Double similarity = getSimilarityInternal(word1, word2);
		return similarity != null ? similarity.doubleValue() : 0;
	}
	
	private Double getSimilarityInternal(String word1, String word2) {
		if (getCache().containsKey(word1) && getCache().get(word1).containsKey(word2)) {
			return getCache().get(word1).get(word2);
		}
		if (getCache().containsKey(word2) && getCache().get(word2).containsKey(word1)) {
			return getCache().get(word2).get(word1);
		}
		
		double similarity = StringUtils.areEqual(word1, word2) ? 1 : 0;
		saveToCache(word1, word2, similarity);
		return similarity;
	}
	


	private Map<String,Map<String,Double>> getCache() {
		if (cache == null) {
			cache = new HashMap<>();
		}
		return cache;
	}
	
	private void saveToCache(String word1, String word2, double similarity) {
		word1 = getStanfordLemmatizer().lemmatizeWord(word1);
		word2 = getStanfordLemmatizer().lemmatizeWord(word2);
		
		if (!getCache().containsKey(word1)) {
			getCache().put(word1,new HashMap<>());
		}
		getCache().get(word1).put(word2, Double.valueOf(similarity));
	}
	
	private void loadFromConfig() {
		List<String[]> configData = getDictConfigManager().getResourceData();
		
		for (String[] line : configData) {
			if (line.length == 2) {
				// add similarity pair
				saveToCache(line[0], line[1], 1);
			} else if (line.length > 2) {
				// add similarity pair for all doubles
				for (int i = 0; i < line.length; i++) {
					for (int j = i; j < line.length; j++) {
						saveToCache(line[i], line[j], 1);
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
