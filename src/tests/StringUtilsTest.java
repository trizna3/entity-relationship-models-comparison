package tests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import common.StringUtils;
import common.enums.Enums;

public class StringUtilsTest {

	private static String getCompName(int count) {
		StringBuilder result = new StringBuilder();

		for (int i = 0; i < count; i++) {
			result.append("afsklfasjklfjaskljfaslk" + i);
			result.append(Enums.ENTITY_SETS_DELIMITER);
		}

		return result.toString();
	}

	@Test
	public void test1() {

		int count = 100;
		String compName = getCompName(count);
		for (int i = 0; i < count; i++) {
			assertEquals("afsklfasjklfjaskljfaslk" + (count - (i + 1)), StringUtils.getNamePart(compName, count - (i + 1)));
		}
	}
}
