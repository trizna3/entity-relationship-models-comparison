package common;

public class Utils {

	protected static void validateInput(Object input) {
		if (input == null) {
			throw new IllegalArgumentException("Null argument!");
		}
	}
}
