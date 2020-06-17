package common;

import java.util.Collection;
import java.util.function.Predicate;

/**
 * @author - Adam Trizna
 */

/**
 * Utility methods for working with collections.
 */
public class CollectionUtils extends Utils {

    /**
     * @param collection
     * @param clazz
     * @param condition
     * @param <T>
     * @return First object in given collection, which is of given class and satisfies given condition.
     */
    public static <T> T getFirst(Collection<?> collection, Class<T> clazz, Predicate<T> condition) {
    	validateInput(collection);
    	validateInput(clazz);
    	validateInput(condition);
        
        for (Object obj : collection) {
            if (obj != null && clazz.isAssignableFrom(obj.getClass())) {
                if (condition.test((T)obj)) {
                    return clazz.cast(obj);
                }
            }
        }
        return null;
    }
}
