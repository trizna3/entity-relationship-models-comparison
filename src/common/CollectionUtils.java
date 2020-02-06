package common;

import java.util.Collection;
import java.util.function.Predicate;

/**
 * Utility methods for working with collections.
 */
public class CollectionUtils {

    /**
     * @param list
     * @param clazz
     * @param condition
     * @param <T>
     * @return First object in given list, which is of given class and satisfies given condition.
     */
    public static <T> T getFirst(Collection<?> list, Class<T> clazz, Predicate<T> condition) {
        if (list == null || clazz == null || condition == null) {
            throw new IllegalArgumentException("Illegal argument: list, clazz or condition is null!");
        }
        for (Object obj : list) {
            if (obj != null && clazz.isAssignableFrom(obj.getClass())) {
                if (condition.test((T)obj)) {
                    return clazz.cast(obj);
                }
            }
        }
        return null;
    }
}
