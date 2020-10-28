package common;

public class ArrayUtils {

	public static boolean contains(int[] array, int element) {
		for (int item : array) {
			if (element == item) {
				return true;
			}
		}
		return false;
	}
}
