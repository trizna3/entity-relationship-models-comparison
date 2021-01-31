package sk.trizna.erm_comparison.common;

import java.util.Collection;
import java.util.List;

/**
 * @author - Adam Trizna
 */

/**
 * Utility methods for working with collections.
 */
public class CollectionUtils extends Utils {

	public static <T> boolean areEqual(List<T> list1, List<T> list2) {
		if (list1 == null && list2 == null) {
			return true;
		}
		if (list1 == null || list2 == null) {
			return false;
		}
		return list1.equals(list2);
	}
	
	/**
	 * Determines, if obj is contained in given collection, uses refference id comparison, not overriden compareTo/hashCode. 
	 * @param <T>
	 * @param collection
	 * @param obj
	 * @return
	 */
	public static <T> boolean trueContains(Collection<T> collection, T obj) {
		validateNotNull(collection);
		validateNotNull(obj);
		
		for (T element : collection) {
			if(element == obj) {
				return true;
			}
		}
		return false;
	}
}
