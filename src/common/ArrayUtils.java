package common;

public class ArrayUtils extends Utils {

	public static <T> boolean contains(T[] array, T target) {
		validateNotNull(array);
		validateNotNull(target);

		for (int i = 0; i < array.length; i++) {
			if (target.equals(array[i])) {
				return true;
			}
		}
		return false;
	}
}
