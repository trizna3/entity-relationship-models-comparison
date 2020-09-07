package common;

public class StringUtils extends Utils {

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
		return string1.equals(string2);
	}
}
