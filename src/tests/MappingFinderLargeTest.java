package tests;

import static org.junit.Assert.assertTrue;

import java.util.Collections;

import org.junit.Test;

import entityRelationshipModel.ERModel;
import mappingSearch.mappingFinder.MappingFinder;

public class MappingFinderLargeTest {

	@Test
	public void testFindBestMapping_Internaty_1() {
		System.out.println("Test - mapping finder: Internaty 1");
		ERModel exemplarModel = TestUtils.makeERModel_Internaty_Vzor();
		ERModel studentModel = TestUtils.makeERModel_Internaty_S1();

		Collections.shuffle(exemplarModel.getEntitySets());
		Collections.shuffle(studentModel.getEntitySets());

		MappingFinder finder = new MappingFinder();
		finder.getBestMapping(exemplarModel, studentModel);

		assertTrue(true);
	}

	@Test
	public void testFindBestMapping_Internaty_2() {
		System.out.println("Test - mapping finder: Internaty 2");
		ERModel exemplarModel = TestUtils.makeERModel_Internaty_Vzor();
		ERModel studentModel = TestUtils.makeERModel_Internaty_S2();

		Collections.shuffle(exemplarModel.getEntitySets());
		Collections.shuffle(studentModel.getEntitySets());

		MappingFinder finder = new MappingFinder();
		finder.getBestMapping(exemplarModel, studentModel);

		assertTrue(true);
	}

	@Test
	public void testFindBestMapping_MHD_1() {
		System.out.println("Test - mapping finder: MHD 1");
		ERModel exemplarModel = TestUtils.makeERModel_MHD_Vzor();
		ERModel studentModel = TestUtils.makeERModel_MHD_S1();

		Collections.shuffle(exemplarModel.getEntitySets());
		Collections.shuffle(studentModel.getEntitySets());

		MappingFinder finder = new MappingFinder();
		finder.getBestMapping(exemplarModel, studentModel);

		assertTrue(true);
	}

	@Test
	public void testFindBestMapping_MHD_2() {
		System.out.println("Test - mapping finder: MHD 2");
		ERModel exemplarModel = TestUtils.makeERModel_MHD_Vzor();
		ERModel studentModel = TestUtils.makeERModel_MHD_S2();

		Collections.shuffle(exemplarModel.getEntitySets());
		Collections.shuffle(studentModel.getEntitySets());

		MappingFinder finder = new MappingFinder();
		finder.getBestMapping(exemplarModel, studentModel);

		assertTrue(true);
	}
}