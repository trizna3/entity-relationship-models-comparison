package sk.trizna.erm_comparison.common;

public class ArrayUtils extends Utils {

	public static boolean contains(int[] array, int element) {
		for (int item : array) {
			if (element == item) {
				return true;
			}
		}
		return false;
	}
	
	public static <T> boolean contains(T[] array, T element) {
		for (T item : array) {
			if (element != null && element.equals(item)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Element-wise equals
	 * 
	 * @param <T>
	 * @param array1
	 * @param array2
	 * @return
	 */
	public static <T> boolean equals(T[] array1, T[] array2) {
		if (array1 == null || array2 == null) {
			return false;
		}
		if (array1.length != array2.length) {
			return false;
		}
		for (int i=0; i<array1.length; i++) {
			if (!array1[i].equals(array2[i])) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Returns arrays equality, ignoring order.
	 * 
	 * @param <T>
	 * @param array1
	 * @param array2
	 * @return
	 */
	public static <T> boolean equalsIgnoreOrder(T[] array1, T[] array2) {
		if (array1 == null || array2 == null) {
			return false;
		}
		if (array1.length != array2.length) {
			return false;
		}
		for (int i=0; i<array1.length; i++) {
			if (!contains(array2, array1[i])) {
				return false;
			}
		}
		return true;
	}
}
