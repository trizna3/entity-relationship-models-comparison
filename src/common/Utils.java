package common;

public class Utils {

	protected static void validateInput(Object input) {
		if (input == null) {
			throw new IllegalArgumentException("Null argument!");
		}
	}

	public static <T> boolean contains(T[] array, T target) {
		validateInput(array);
		validateInput(target);

		for (int i = 0; i < array.length; i++) {
			if (target.equals(array[i])) {
				return true;
			}
		}
		return false;
	}
}
