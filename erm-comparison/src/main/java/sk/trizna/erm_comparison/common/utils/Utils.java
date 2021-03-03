package sk.trizna.erm_comparison.common.utils;

import java.util.ArrayList;
import java.util.List;

import sk.trizna.erm_comparison.entity_relationship_model.Attribute;
import sk.trizna.erm_comparison.entity_relationship_model.ERModel;
import sk.trizna.erm_comparison.entity_relationship_model.EntitySet;
import sk.trizna.erm_comparison.entity_relationship_model.Relationship;

public class Utils {
	
	public static final String TRAIN_DICT_SECTION = "trenovacie_priklady";
	private static String workingDictSection = null;
	
	public static String getWorkingDictSection() {
		return workingDictSection;
	}

	public static void setWorkingDictSection(String workingDictSection) {
		Utils.workingDictSection = workingDictSection;
	}

	public static void validateNotNull(Object input) {
		if (input == null) {
			throw new IllegalArgumentException("Null argument!");
		}
	}

	public static void validateContains(ERModel model, EntitySet entitySet) {
		if (!model.contains(entitySet)) {
			throw new IllegalArgumentException("EntitySet is not contained in the model!");
		}
	}
	
	public static void validateNotContains(ERModel model, EntitySet entitySet) {
		if (model.contains(entitySet)) {
			throw new IllegalArgumentException("EntitySet is contained in the model!");
		}
	}

	public static void validateContains(ERModel model, Relationship relationship) {
		if (!model.contains(relationship)) {
			throw new IllegalArgumentException("Relationship is not contained in the model!");
		}
	}
	
	public static void validateEqual(ERModel model1, ERModel model2) {
		if (!ERModelUtils.modelsAreEqual(model1, model2)) {
			throw new IllegalArgumentException("Models are not equal!");
		}
	}

	public static void validateContains(Relationship relationship, EntitySet entitySet) {
		if (!relationship.contains(entitySet)) {
			throw new IllegalArgumentException("EntitySet is not contained in the relationship!");
		}
	}

	public static void validateMapped(EntitySet entitySet) {
		if (entitySet.getMappedTo() == null) {
			throw new IllegalArgumentException("EntitySet is not mapped!");
		}
	}

	public static void validateNotMapped(EntitySet entitySet) {
		if (entitySet.getMappedTo() != null) {
			throw new IllegalArgumentException("EntitySet is mapped!");
		}
	}

	public static void validateBinary(Relationship relationship) {
		if (!relationship.isBinary()) {
			throw new IllegalStateException("Relationship is not Binary!");
		}
	}
	
	public static void validateBinary(EntitySet entitySet) {
		if (!entitySet.isBinary()) {
			throw new IllegalStateException("EntitySet is not binary joining entity set!");
		}
	}

	public static void validateContains(EntitySet entitySet, Attribute attribute) {
		if (!entitySet.getAttributes().contains(attribute)) {
			throw new IllegalArgumentException("EntitySet doesn't contain this attribute!");
		}
	}

	public static void validatePositive(Integer integer) {
		validateNotNull(integer);
		if (integer.intValue() < 1) {
			throw new IllegalStateException("Integer value is not positive!");
		}
	}
	
	public static void validateSameGrade(Relationship relationship1, Relationship relationship2) {
		if (!RelationshipUtils.sameGrade(relationship1, relationship2)) {
			throw new IllegalStateException("Relationships are not of the same grade!");
		}
	}

	public static boolean isSameClass(Object obj1, Object obj2) {
		if (obj1 == null && obj2 == null) {
			return true;
		}
		if (obj1 == null || obj2 == null) {
			return false;
		}
		if (obj1.getClass().equals(obj2.getClass())) {
			return true;
		}
		return false;
	}
	
	/**
	 * https://www.baeldung.com/java-combinations-algorithm
	 * @param n
	 * @param r
	 * @return
	 */
	public static List<int[]> generateCombinations(int n, int r) {
	    List<int[]> combinations = new ArrayList<>();
	    int[] combination = new int[r];
	 
	    // initialize with lowest lexicographic combination
	    for (int i = 0; i < r; i++) {
	        combination[i] = i;
	    }
	 
	    while (combination[r - 1] < n) {
	        combinations.add(combination.clone());
	 
	         // generate next combination in lexicographic order
	        int t = r - 1;
	        while (t != 0 && combination[t] == n - r + t) {
	            t--;
	        }
	        combination[t]++;
	        for (int i = t + 1; i < r; i++) {
	            combination[i] = combination[i - 1] + 1;
	        }
	    }
	 
	    return combinations;
	}
	
	/**
	 * Returns array of all ints, which are not present in given array, up to given index
	 * @param originalIndices
	 * @param maxIdx
	 * @return
	 */
	public static int[] getExcludedIndices(int[] originalIndices,int maxIdx) {
		int[] excludedIndices = new int[maxIdx - originalIndices.length + 1];
		if (excludedIndices.length == 0) {
			return excludedIndices;
		}
		int workingIdx = 0;
		
		for (int index=0;index<=maxIdx;index++) {
			if (!ArrayUtils.contains(originalIndices, index)) {
				excludedIndices[workingIdx] = index;
				workingIdx ++;
			}
		} 
		
		return excludedIndices;
	}
	
	public static Object firstNonNull(Object... objects) {
		if (objects != null) {
			for (Object obj : objects) {
				if (obj != null) return obj;
			}
		}
		return null;
	}
	
	public static boolean areEqual(Object obj1, Object obj2) {
		if (obj1 == null || obj2 == null) {
			return false;
		}
		if (!isSameClass(obj1, obj2)) {
			return false;
		}
		return obj1.equals(obj2);
	}
	
	public static List<String> trimAll(String[] stringArray) {
		validateNotNull(stringArray);
		
		List<String> result = new ArrayList<String>(stringArray.length);
		for (int i = 0; i < stringArray.length; i++) {
			result.add(stringArray[i].trim());
		}
		return result;
	}
	
	/**
	 * Adds new T instance.
	 * Moved to separate method, so we initialize preconditions ArrayList only when it's needed.
	 * 
	 * @param list
	 * @param element
	 */
	public static <T> List<T> addToList(List<T> list, T element) {
		if (list == null) {
			list = new ArrayList<T>();
		}
		list.add(element);
		
		return list;
	}
}
