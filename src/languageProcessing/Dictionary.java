package languageProcessing;

import java.util.HashMap;
import java.util.Map;

import common.Utils;

/**
 * @author - Adam Trizna
 */

/**
 * Naive language processor. "Measures" words meaning similarity 1 or 0, wether
 * the word-pair is in manually pre-defined dictionary.
 */
public class Dictionary implements LanguageProcessor {

	private Map<String, String> data;

	private Map<String, String> getData() {
		if (data == null) {
			data = new HashMap<>();

			data.put("students", "people");
			data.put("courses", "classes");

			data.put("employees", "people");
			data.put("jobs", "positions");
			data.put("job_history", "position_history");
			data.put("departments", "areas");

			data.put("AA1", "AA2");
			data.put("BB1", "BB2");
		}
		return data;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public double getSimilarity(String word1, String word2) {
		Utils.validateNotNull(word1);
		Utils.validateNotNull(word2);

		String word1LowerCase = word1.toLowerCase();
		String word2LowerCase = word2.toLowerCase();

		if (getData().containsKey(word1LowerCase) && word2LowerCase.equals(getData().get(word1LowerCase))) {
			return 1;
		}
		if (getData().containsKey(word2LowerCase) && word1LowerCase.equals(getData().get(word2LowerCase))) {
			return 1;
		}

		return 0;
	}
}
