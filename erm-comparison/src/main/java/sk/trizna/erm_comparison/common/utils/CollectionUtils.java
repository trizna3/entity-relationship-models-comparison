package sk.trizna.erm_comparison.common.utils;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import sk.trizna.erm_comparison.entity_relationship_model.ERText;
import sk.trizna.erm_comparison.entity_relationship_model.RelationshipSide;
import sk.trizna.erm_comparison.language_processing.LanguageProcessor;

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
	 * Collection<T>.removaAll(Collection<T>), but each argument element is removed max once
	 * 
	 * @param <T>
	 * @param list
	 * @param targets
	 */
	public static <T> void removeAllMaxOnce(List<T> list, List<T> targets) {
		validateNotNull(list);
		validateNotNull(targets);
		
		for (T target : targets) {
			list.remove(list.indexOf(target));
		}
	}
	
	public static <T extends ERText> boolean containsText(Collection<T> collection, T target) {
		validateNotNull(collection);
		validateNotNull(target);
		
		for (ERText text : collection) {
			if (ERModelUtils.areEqual(text,target)) {
				return true;
			}
		}
		
		return false;
	}
	
	public static <T extends ERText> boolean containsText(Collection<T> collection, T target, LanguageProcessor lp) {
		validateNotNull(collection);
		validateNotNull(target);
		
		for (ERText text : collection) {
			if (lp.areEqual(text.getText(), target.getText())) {
				return true;
			}
		}
		
		return false;
	}
	
	public static <T extends ERText> boolean containsAllTexts(Collection<T> collection, Collection<T> targets) {
		validateNotNull(collection);
		validateNotNull(targets);
		
		for (T target : targets) {
			if (!containsText(collection,target)) {
				return false;
			}
		}
		
		return true;		
	}
	
	public static <T extends RelationshipSide> boolean containsSides(Collection<T> collection, T target, boolean checkCardinalities) {
		validateNotNull(collection);
		validateNotNull(target);
		
		for (T side : collection) {
			if (ERModelUtils.areEqual(side, target, checkCardinalities)) {
				return true;
			}
		}
		
		return false;		
	}
	
	public static <T extends RelationshipSide> boolean containsAllSides(Collection<T> collection, Collection<T> targets, boolean checkCardinalities) {
		validateNotNull(collection);
		validateNotNull(targets);
		
		for (T target : targets) {
			if (!containsSides(collection,target,checkCardinalities)) {
				return false;
			}
		}
		
		return true;		
	}
	
	public static boolean containsByComparator(Collection<? extends Object> collection, Object target, Comparator<Object> comparator) {
		validateNotNull(collection);
		validateNotNull(target);
		validateNotNull(comparator);
		
		for (Object obj : collection) {
			if (comparator.compare(obj, target) == 0) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean containsAllByComparator(Collection<? extends Object> collection, Collection<? extends Object> targets, Comparator<Object> comparator) {
		validateNotNull(collection);
		validateNotNull(targets);
		validateNotNull(comparator);
		
		for (Object target : targets) {
			if (!containsByComparator(collection, target, comparator)) {
				return false;
			}
		}
		return true;
	}
	
	public static boolean equalByComparator(Collection<? extends Object> collection1, Collection<? extends Object> collection2, Comparator<Object> comparator) {
		validateNotNull(collection1);
		validateNotNull(collection2);
		validateNotNull(comparator);
		
		if (!containsAllByComparator(collection1, collection2, comparator)) {
			return false;
		}
		if (!containsAllByComparator(collection2, collection1, comparator)) {
			return false;
		}		
		return true;
	}
	
	public static <A,B> A getKeyByValue(Map<A,B> map, B value) {
		validateNotNull(map);
		validateNotNull(value);
		
		for (A key : map.keySet()) {
			if (value.equals(map.get(key))) {
				return key;
			}
		}
		return null;
	}
	
	public static boolean containsIgnoreCase(Collection<? extends Enum<?>> collection, String target) {
		validateNotNull(collection);
		if (target == null) return false;
		
		for (Enum<?> item : collection) {
			if (StringUtils.areEqual(String.valueOf(item), target)) {
				return true;
			}
		}
		return false;
	}
}
