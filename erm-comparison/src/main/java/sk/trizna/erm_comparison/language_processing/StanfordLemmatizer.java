package sk.trizna.erm_comparison.language_processing;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import edu.stanford.nlp.ling.CoreAnnotations.LemmaAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;
import sk.trizna.erm_comparison.common.Clock;
import sk.trizna.erm_comparison.common.utils.PrintUtils;
import sk.trizna.erm_comparison.common.utils.StringUtils;

class StanfordLemmatizer {

    protected StanfordCoreNLP pipeline;
    private Map<String,String> cache;
    private static StanfordLemmatizer INSTANCE;
    
    public static StanfordLemmatizer getInstance() {
    	if (INSTANCE == null) {
    		Clock clock = new Clock();
    		clock.start();
    		INSTANCE = new StanfordLemmatizer();
    		PrintUtils.log("Lemmatization lib loaded in " + clock.getTimeElapsed() + "ms");    		
    	}
    	return INSTANCE;
    }

    private StanfordLemmatizer() {
        // Create StanfordCoreNLP object properties, with POS tagging
        // (required for lemmatization), and lemmatization
        Properties props;
        props = new Properties();
        props.put("annotators", "tokenize, ssplit, pos, lemma");

        // StanfordCoreNLP loads a lot of models, so you probably
        // only want to do this once per execution
        this.pipeline = new StanfordCoreNLP(props);
    }
    
    public String lemmatizeWord(String word)
    {
    	if (StringUtils.isBlank(word)) {
    		return word;
    	}
    	if (getCache().containsKey(word)) {
    		return getCache().get(word);
    	}
    	
        // create an empty Annotation just with the given text
        Annotation document = new Annotation(word);

        // run all Annotators on this text
        this.pipeline.annotate(document);
        
        StringBuilder resultSB = new StringBuilder();

        // Iterate over all of the sentences found
        for(CoreMap sentence: document.get(SentencesAnnotation.class)) {
            // Iterate over all tokens in a sentence
            for (CoreLabel token: sentence.get(TokensAnnotation.class)) {
                // Retrieve and add the lemma for each word into the list of lemmas
            	if (resultSB.length() > 0) {
            		resultSB.append(" ");
            	}
            	resultSB.append(token.get(LemmaAnnotation.class));
            }
        }
        // case insensitivity
        String result = resultSB.toString().toLowerCase();
        getCache().put(word, result);
        return result;
    }

	private Map<String,String> getCache() {
		if (cache == null) {
			cache = new HashMap<>();
		}
		return cache;
	}
}