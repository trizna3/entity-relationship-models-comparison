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
	 * Given combined name, returns the first part.
	 * 
	 * @param name
	 * @return
	 */
	public static String getFirstNamePart(String name) {
		validateNotNull(name);
		return name.split(Enums.ENTITY_SETS_DELIMITER)[0];
	}
}
