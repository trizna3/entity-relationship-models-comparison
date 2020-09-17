package common;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class ArrayUtils extends Utils {

	/**
	 * Determines if object is contained in the array.
	 * 
	 * @param <T>
	 * @param array
	 * @param target
	 * @return
	 */
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

	/**
	 * Determines if arrays are equal, ignoring element order.
	 * 
	 * @param <T>
	 * @param array1
	 * @param array2
	 * @return
	 */
	public static <T> boolean areEqual(T[] array1, T[] array2) {
		Set<T> objectsToProcess = new HashSet<>();
		objectsToProcess.addAll(Arrays.asList(array1));
		objectsToProcess.addAll(Arrays.asList(array2));

		nextObject1: for (T object1 : array1) {
			for (T object2 : array2) {
				if (!objectsToProcess.contains(object2)) {
					continue;
				}
				if (object1.equals(object2)) {
					objectsToProcess.remove(object1);
					objectsToProcess.remove(object2);
					continue nextObject1;
				}
			}
		}

		return objectsToProcess.isEmpty();
	}
}
