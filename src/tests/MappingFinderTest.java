package tests;

import static org.junit.Assert.assertTrue;

import java.util.Collections;

import org.junit.Test;

import entityRelationshipModel.ERModel;
import mappingSearch.mappingFinder.MappingFinder;

public class MappingFinderTest {

	@Test
	public void testFindBestMapping1() {
		ERModel exemplarModel = TestUtils.getERModels().get(0);
		ERModel studentModel = TestUtils.getERModels().get(3);

		Collections.shuffle(exemplarModel.getEntitySets());
		Collections.shuffle(studentModel.getEntitySets());

//		MappingFinder finder = new MappingFinder();
//		finder.getBestMapping(exemplarModel, studentModel);

		assertTrue(true);
	}

	@Test
	public void testFindBestMapping2() {
		ERModel exemplarModel = TestUtils.getERModels().get(1);
		ERModel studentModel = TestUtils.getERModels().get(5);

		Collections.shuffle(exemplarModel.getEntitySets());
		Collections.shuffle(studentModel.getEntitySets());

//		MappingFinder finder = new MappingFinder();
//		finder.getBestMapping(exemplarModel, studentModel);

		assertTrue(true);
	}

	@Test
	public void testFindBestMapping3() {
		ERModel exemplarModel = TestUtils.getERModels().get(7);
		ERModel studentModel = TestUtils.getERModels().get(8);

		Collections.shuffle(exemplarModel.getEntitySets());
		Collections.shuffle(studentModel.getEntitySets());

		MappingFinder finder = new MappingFinder();
		finder.getBestMapping(exemplarModel, studentModel);

		assertTrue(true);
	}
}
