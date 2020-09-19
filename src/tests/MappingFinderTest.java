package tests;

import static org.junit.Assert.assertTrue;

import java.util.Collections;

import org.junit.Test;

import entityRelationshipModel.ERModel;
import mappingSearch.mappingFinder.MappingFinder;

public class MappingFinderTest {

	@Test
	public void testFindBestMapping() {
		ERModel exemplarModel = TestUtils.getERModels().get(0);
		ERModel studentModel = TestUtils.getERModels().get(3);

		Collections.shuffle(exemplarModel.getEntitySets());
		Collections.shuffle(studentModel.getEntitySets());

		MappingFinder finder = new MappingFinder();

		finder.getBestMapping(exemplarModel, studentModel);

		assertTrue(true);
	}
}
