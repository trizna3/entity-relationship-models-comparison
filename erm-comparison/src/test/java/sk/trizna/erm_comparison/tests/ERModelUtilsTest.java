package sk.trizna.erm_comparison.tests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import sk.trizna.erm_comparison.common.ERModelUtils;
import sk.trizna.erm_comparison.entity_relationship_model.ERModel;



public class ERModelUtilsTest {

	@Test
	public void testGetClone() {
		ERModel model = TestUtils.getERModels().get(0);
		ERModel modelClone = ERModelUtils.getClone(model);
		
		assertTrue(ERModelUtils.modelsAreEqual(model, modelClone));
	}
}
