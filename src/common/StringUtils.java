package common;

import java.util.HashMap;
import java.util.Map;

import common.enums.EnumConstants;

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
		if (string1 == null && string2 == null) {
			return true;
		}
		if (string1 == null || string2 == null) {
			return false;
		}
		return string1.equalsIgnoreCase(string2);
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
			partIndex = count(compositeName,EnumConstants.DELIMITER_SEMICOLON_STR) - partIndex;

		}

		int cycleIdx = 0;
		int idxFrom = 0;

		while (true) {
			int delimiterIdx = compositeName.indexOf(EnumConstants.DELIMITER_SEMICOLON, idxFrom);
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
		
		int count = count(compositeName,EnumConstants.DELIMITER_SEMICOLON_STR) - excludedPartIndex + 1;
		int idxFrom = 0;
		int upperBound = 0;

		for (int i = 0; i < count; i++) {
			upperBound = compositeName.indexOf(EnumConstants.DELIMITER_SEMICOLON, idxFrom);
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
		return compositeName.split(EnumConstants.DELIMITER_SEMICOLON_STR);
	}

	private static Map<String,Integer> getHashtable() {
		if (hashtable == null) {
			hashtable = new HashMap<>();
		}
		return hashtable;
	}
}
