package common;

import common.enums.Enum;

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
		if (string1 == null) {
			return false;
		}
		if (string2 == null) {
			return false;
		}
		return string1.toLowerCase().equals(string2.toLowerCase());
	}

	/**
	 * Given combined name, returns the first part.
	 * 
	 * @param name
	 * @return
	 */
	public static String getFirstNamePart(String name) {
		validateNotNull(name);
		return name.split(Enum.ENTITY_SETS_DELIMITER)[0];
	}
}
