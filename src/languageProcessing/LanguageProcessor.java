package languageProcessing;

/**
 * Interface of language processors - any objects, able to measure words similarity (by their meaning / usage).
 */
public interface LanguageProcessor {

    /**
     * @param word1
     * @param word2
     * @return similarity measure of given word pair.
     */
    public double getSimilarity(String word1, String word2);
}
