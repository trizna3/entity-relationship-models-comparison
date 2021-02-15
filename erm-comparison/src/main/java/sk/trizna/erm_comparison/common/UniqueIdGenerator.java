package sk.trizna.erm_comparison.common;

import sk.trizna.erm_comparison.common.utils.StringUtils;

public class UniqueIdGenerator {

	public static final UniqueIdGenerator INSTANCE = new UniqueIdGenerator();
	
	private static final int ID_LENGTH = 10;
	private static int currentId = 0;
	
	public static String generateId() {
		currentId ++;
		return translateId(currentId);
	}
	
	private static String translateId(int id) {
		return StringUtils.fillUpToLength(String.valueOf(id), ID_LENGTH, '0');
	}
}
