package languageProcessing;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import common.Clock;
import common.MathUtils;
import common.StringUtils;
import common.Utils;

public class Word2VecDictionary implements LanguageProcessor{
	
	private static Map<String,double[]> word2vec;
	private static final String vectorFileName = "glove.6B.300d.txt";
	private static boolean initialized = false;
	
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
	            	System.out.println("word2vec: " + counter + " words processed");
	            }
	        }
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.out.println("Word vectors loaded in " + clock.getTimeElapsed() + "ms.\n");
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
	public double getSimilarity(String word1, String word2) {
		initialize();
		
		if (StringUtils.areEqual(word1, word2)) {
			return 1;
		}
		if (StringUtils.isBlank(word1) || StringUtils.isBlank(word2)) {
			return 0;
		}
		
		double[] vec1 = getVector(word1);
		double[] vec2 = getVector(word2);
		
		if (vec1 == null || vec2 == null) {
			// unknown word
			return 0;
		}
		
		return MathUtils.dot(vec1, vec2) / (MathUtils.norm(vec1) * MathUtils.norm(vec2));
	}
}
