package sk.trizna.erm_comparison.language_processing;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import sk.trizna.erm_comparison.common.Clock;
import sk.trizna.erm_comparison.common.utils.MathUtils;
import sk.trizna.erm_comparison.common.utils.PrintUtils;
import sk.trizna.erm_comparison.common.utils.Utils;

class Word2VecDictionary extends AbstractLanguageProcessor {
	
	private static Word2VecDictionary INSTANCE;
	private static Map<String,double[]> word2vec;
	private static final String vectorFileName = "..//glove.6B.300d.txt";
	private StanfordLemmatizer stanfordLemmatizer;
	private static boolean initialized = false;
	
	static Word2VecDictionary getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new Word2VecDictionary();
		}
		return INSTANCE;
	}
	
	private void initialize() {
		if (!initialized) {
			load();
			initialized = true;
		}
	}
	
	private void load() {
		Clock clock = new Clock();
		clock.start();
		try(BufferedReader br = new BufferedReader(new FileReader(vectorFileName))) {
		    String line = br.readLine();
		    int counter = 0;
		    while (line != null) {
	        	String[] lineSplit = line.split(" ");
			    getWord2VecMap().put(lineSplit[0], parseVector(lineSplit));
			    
			    line = br.readLine();
	            counter ++;
	            if (counter % 50000 == 0) {
	            	PrintUtils.log("word2vec: " + counter + " words processed");
	            }
	        }
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		PrintUtils.log("Word vectors loaded in " + clock.getTimeElapsed() + "ms.\n");
	}
	
	/**
	 * Gets split line: [word, x1, x2, x3, .. , xn]
	 * returns double array [x1,x2,x3,...,xn]
	 * @param line
	 * @return
	 */
	private double[] parseVector(String[] line) {
		Utils.validateNotNull(line);

		double[] intVec = new double[line.length - 1];
		for (int i=0; i<intVec.length; i++) {
			intVec[i] = Double.valueOf(line[i+1]);
		}
		
		return intVec;
	}
	
	private Map<String,double[]> getWord2VecMap() {
		if (word2vec == null) {
			word2vec = new HashMap<String, double[]>();
		}
		return word2vec;
	}
	
	private double[] getVector(String word) {
		Utils.validateNotNull(word);
		
		return getWord2VecMap().get(word);
	}

	@Override
	public Double getSimilarityInternal(String word1, String word2) {
		initialize();
		
		if (word1 == null || word2 == null) {
			return Double.valueOf(0);
		}
		
		double[] vec1 = getVector(word1);
		double[] vec2 = getVector(word2);
		
		if (vec1 == null || vec2 == null) {
			// unknown word
			return Double.valueOf(0);
		}
		
		return MathUtils.dot(vec1, vec2) / (MathUtils.norm(vec1) * MathUtils.norm(vec2));
	}
	
	@Override
	public String getLemma(String word) {
		return getStanfordLemmatizer().lemmatizeWord(word);
	}

	private StanfordLemmatizer getStanfordLemmatizer() {
		if (stanfordLemmatizer == null) {
			stanfordLemmatizer = StanfordLemmatizer.getInstance();
		}
		return stanfordLemmatizer;
	}

	@Override
	public void clearCache() {
	}
	
	@Override
	public void clearInstance() {
		INSTANCE = null;
	}
}
