package sk.trizna.erm_comparison.common.utils;

import java.util.HashMap;
import java.util.Map;

import sk.trizna.erm_comparison.language_processing.LanguageProcessor;

public class StringUtils extends Utils {
	
	private static Map<String,Integer> hashtable;

	/**
	 * Determines if strings are equal, case insensitive.
	 * 
	 * @param string1
	 * @param string2
	 * @return
	 */
	public static boolean areEqual(String string1, String string2) {
		return areEqual(string1, string2, null);
	}
	
	/**
	 * Determines if strings are equal by given dictionary, case insensitive.
	 * If not dictionary is provided, computes case insensitive string equality.
	 * 
	 * @param string1
	 * @param string2
	 * @param dict
	 * @return
	 */
	public static boolean areEqual(String string1, String string2, LanguageProcessor dict) {
		if (string1 == null && string2 == null) {
			return true;
		}
		if (string1 == null || string2 == null) {
			return false;
		}
		
		if (dict == null) {
			return string1.equalsIgnoreCase(string2);
		} else {
			return dict.getSimilarity(string1, string2) == 1;
		}
	}
	
	
	/**
	 * Determines if two composite names are equal, ignoring order by name parts
	 * 
	 * @param compositeName1
	 * @param compositeName2
	 * @return
	 */
	public static boolean areEqualByNameParts(String compositeName1, String compositeName2) {
		if (compositeName1 == null && compositeName2 == null) {
			return true;
		}
		if (compositeName1 == null || compositeName2 == null) {
			return false;
		}
		
		return StringUtils.equalsIgnoreOrder(getAllNameParts(compositeName1), getAllNameParts(compositeName2), LanguageProcessor.getImplementation());
	}
	
	/**
	 * string arrays equality
	 * @param array1
	 * @param array2
	 * @return
	 */
	public static boolean equals(String[] array1, String[] array2) {
		return equals(array1,array2,null);
	}
	
	/**
	 * string arrays equality, determined by dictionary
	 * @param array1
	 * @param array2
	 * @param dictionary
	 * @return
	 */
	public static boolean equals(String[] array1, String[] array2, LanguageProcessor dictionary) {
		if (array1 == null || array2 == null) {
			return false;
		}
		if (array1.length != array2.length) {
			return false;
		}
		if (dictionary == null) {
			for (int i=0; i<array1.length; i++) {
				if (!array1[i].equals(array2[i])) {
					return false;
				}
			}
		} else {
			for (int i=0; i<array1.length; i++) {
				if (dictionary.getSimilarity(array1[i], array2[i]) != 1) {
					return false;
				}
			}
		}
		return true;
	}
	
	/**
	 * string arrays order insensitive equality
	 * @param array1
	 * @param array2
	 * @return
	 */
	public static  boolean equalsIgnoreOrder(String[] array1, String[] array2) {
		return equalsIgnoreOrder(array1, array2, null);
	}
	
	/**
	 * string arrays order insensitive equality, determined by dictionary
	 * @param array1
	 * @param array2
	 * @param dictionary
	 * @return
	 */
	public static  boolean equalsIgnoreOrder(String[] array1, String[] array2, LanguageProcessor dictionary) {
		if (array1 == null || array2 == null) {
			return false;
		}
		if (array1.length != array2.length) {
			return false;
		}
		if (dictionary == null) {
			for (int i=0; i<array1.length; i++) {
				if (!contains(array2, array1[i])) {
					return false;
				}
			}
		} else {
			for (int i=0; i<array1.length; i++) {
				if (!contains(array2, array1[i], dictionary)) {
					return false;
				}
			}
		}
		return true;
	}
	
	/**
	 * string array contains string element
	 * 
	 * @param array
	 * @param element
	 * @return
	 */
	public static boolean contains(String[] array, String element) {
		return contains(array, element, null);
	}
	
	/**
	 * string array contains string element, determined by dictionary
	 * @param array
	 * @param element
	 * @param dictionary
	 * @return
	 */
	public static boolean contains(String[] array, String element, LanguageProcessor dictionary) {
		if (array == null || element == null) {
			return false;
		}
		if (dictionary == null) {
			for (String item : array) {
				if (element != null && element.equals(item)) {
					return true;
				}
			}
		} else {
			for (String item : array) {
				if (dictionary.getSimilarity(item,element) == 1) {
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * Given composite name, returns name part.
	 * 
	 * @param name
	 * @return
	 */
	public static String getNamePart(String compositeName, int partIndex, boolean fromBeginning) {
		validateNotNull(compositeName);
		
		if (!fromBeginning) {
			partIndex = count(compositeName,PrintUtils.DELIMITER_SEMICOLON_STR) - partIndex;

		}

		int cycleIdx = 0;
		int idxFrom = 0;

		while (true) {
			int delimiterIdx = compositeName.indexOf(PrintUtils.DELIMITER_SEMICOLON, idxFrom);
			if (partIndex == cycleIdx) {
				return delimiterIdx == -1 ? compositeName.substring(idxFrom) : compositeName.substring(idxFrom, delimiterIdx);
			} else {
				if (delimiterIdx == -1) {
					return "";
				}
				idxFrom = delimiterIdx + 1;
				cycleIdx++;
			}
		}
	}
	
	/**
	 * Returns all name parts, up to (count-excludedPartIndex)
	 * 
	 * @param compositeName
	 * @param partIndex
	 * @return
	 */
	public static String getNamePartsFromBeginning(String compositeName, int excludedPartIndex) {
		validateNotNull(compositeName);
		
		int count = count(compositeName,PrintUtils.DELIMITER_SEMICOLON_STR) - excludedPartIndex + 1;
		int idxFrom = 0;
		int upperBound = 0;

		for (int i = 0; i < count; i++) {
			upperBound = compositeName.indexOf(PrintUtils.DELIMITER_SEMICOLON, idxFrom);
			idxFrom = upperBound + 1;
		}
		return compositeName.substring(0, upperBound);
	}
	
	/**
	 * Counts number of occurrences of given pattern in given string.
	 * @param string
	 * @param character
	 * @return
	 */
	public static int count(String string, String pattern) {
		return string.length() - string.replace(pattern, "").length();
	}
	
	/**
	 * Computes case-insensitive hash.
	 * 
	 * Since String is immutable, cannot make it lower-case without creating new one.
	 * Results are cached to hashtable. 
	 *  
	 * @param string
	 * @return
	 */
	public static int computeStringHash(String string) {
		validateNotNull(string);
		
		if (getHashtable().containsKey(string)) {
			return getHashtable().get(string);
		}
		int hash = string.toLowerCase().hashCode();
		getHashtable().put(string, hash);
		return hash;
	}
	
	/**
	 * Removes otherName part from compositeName
	 * 
	 * We can assume we'll always take from beginning or from the end.
	 * 
	 * @param compositeName
	 * @param otherName
	 */
	public static String decomposeName(String compositeName, String otherName) {
		validateNotNull(compositeName);
		validateNotNull(otherName);
		
		int otherIdx = compositeName.indexOf(otherName);
		
		if (otherIdx == -1) {
			return compositeName;
		}
		else if (otherIdx == 0) {
			return compositeName.substring(otherName.length()+1);	// exclude delimiter (should be 1 char)
		}
		else {
			return compositeName.substring(0, otherIdx-1);	// exclude delimiter (should be 1 char)
		}
	}
	
	public static boolean isBlank(String string) {
		return string == null || string.trim().length() == 0;
	}
	
	/**
	 * Returns String array of composite name parts
	 */
	public static String[] getAllNameParts(String compositeName) {
		validateNotNull(compositeName);
		return compositeName.split(PrintUtils.DELIMITER_SEMICOLON_STR);
	}

	private static Map<String,Integer> getHashtable() {
		if (hashtable == null) {
			hashtable = new HashMap<>();
		}
		return hashtable;
	}
}
