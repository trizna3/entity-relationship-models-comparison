package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import common.StringUtils;
import common.enums.EnumConstants;

public class StringUtilsTest {

	private static String getCompName(int count) {
		StringBuilder result = new StringBuilder();

		for (int i = 0; i < count; i++) {
			result.append("afsklfasjklfjaskljfaslk" + i);
			result.append(EnumConstants.ENTITY_SETS_DELIMITER);
		}

		return result.toString();
	}

	@Test
	public void test1() {
		int count = 100;
		String compName = getCompName(count);
		for (int i = 0; i < count; i++) {
			assertEquals("afsklfasjklfjaskljfaslk" + (count - (i + 1)), StringUtils.getNamePart(compName, count - (i + 1),true));
		}
	}
	
	@Test
	public void testHashCaseSensitivity() {
		String string1 = "jasfjkljfksaljfsakl";
		String string2 = "jasfjkljfKsaljfsakl";
		
		assertFalse(string1.hashCode() == string2.hashCode());
		assertTrue(StringUtils.computeStringHash(string1) == StringUtils.computeStringHash(string2));
	} 
}