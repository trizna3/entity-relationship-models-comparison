package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import common.ERModelUtils;
import entityRelationshipModel.ERModel;

public class ERModelUtilsTest {

	@Test
	public void testGetClone() {
		ERModel model = TestUtils.getERModels().get(0);
		ERModel modelClone = ERModelUtils.getClone(model);
		
		assertTrue(ERModelUtils.modelsAreEqual(model, modelClone));
	}
}
