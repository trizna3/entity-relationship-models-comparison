package languageProcessing;

import java.util.HashMap;
import java.util.Map;

public class Dictionary implements LanguageProcessor {

    private Map<String,String> data;

    private Map<String,String> getData() {
        if (data == null) {
            data = new HashMap<>();

            data.put("students","people");
            // ...
            data.put("courses","classes");

        }
        return data;
    }

    @Override
    public double getSimilarity(String word1, String word2) {
        if (word1 == null || word2 == null) {
            throw new IllegalArgumentException("compared word cannot be null!");
        }
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
