package common;

import common.enums.Enums;

public class StringUtils extends Utils {

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
	public static String getNamePart(String compositeName, int partIndex) {
		validateNotNull(compositeName);

		int cycleIdx = 0;
		int idxFrom = 0;

		while (true) {
			int delimiterIdx = compositeName.indexOf(Enums.ENTITY_SETS_DELIMITER, idxFrom);
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
}
