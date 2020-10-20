package tests;

import static org.junit.Assert.assertTrue;

import java.util.Collections;

import org.junit.Test;

import entityRelationshipModel.ERModel;
import mappingSearch.mappingFinder.MappingFinder;

public class MappingFinderSmallTest {

	@Test
	public void testFindBestMapping_Dennik_1() {
		System.out.println("Test - mapping finder: Chem Dennik 1");
		ERModel exemplarModel = TestUtils.makeERModel_Dennik_vzor();
		ERModel studentModel = TestUtils.makeERModel_Dennik_S1();

		Collections.shuffle(exemplarModel.getEntitySets());
		Collections.shuffle(studentModel.getEntitySets());

		MappingFinder finder = new MappingFinder();
		finder.getBestMapping(exemplarModel, studentModel);

		assertTrue(true);
	}

	@Test
	public void testFindBestMapping_Dennik_2() {
		System.out.println("Test - mapping finder: Chem Dennik 2");
		ERModel exemplarModel = TestUtils.makeERModel_Dennik_vzor();
		ERModel studentModel = TestUtils.makeERModel_Dennik_S2();

		Collections.shuffle(exemplarModel.getEntitySets());
		Collections.shuffle(studentModel.getEntitySets());

		MappingFinder finder = new MappingFinder();
		finder.getBestMapping(exemplarModel, studentModel);

		assertTrue(true);
	}

	@Test
	public void testFindBestMapping_Eshop_1() {
		System.out.println("Test - mapping finder: Eshop 1");
		ERModel exemplarModel = TestUtils.makeERModel_Eshop_Vzor();
		ERModel studentModel = TestUtils.makeERModel_Eshop_S1();

		Collections.shuffle(exemplarModel.getEntitySets());
		Collections.shuffle(studentModel.getEntitySets());

		MappingFinder finder = new MappingFinder();
		finder.getBestMapping(exemplarModel, studentModel);

		assertTrue(true);
	}

	@Test
	public void testFindBestMapping_Eshop_2() {
		System.out.println("Test - mapping finder: Eshop 2");
		ERModel exemplarModel = TestUtils.makeERModel_Eshop_Vzor();
		ERModel studentModel = TestUtils.makeERModel_Eshop_S2();

		Collections.shuffle(exemplarModel.getEntitySets());
		Collections.shuffle(studentModel.getEntitySets());

		MappingFinder finder = new MappingFinder();
		finder.getBestMapping(exemplarModel, studentModel);

		assertTrue(true);
	}

	@Test
	public void testFindBestMapping_Vztahy_1() {
		System.out.println("Test - mapping finder: Vztahy 1");
		ERModel exemplarModel = TestUtils.makeERModel_Vztahy_vzor();
		ERModel studentModel = TestUtils.makeERModel_Vztahy_S1();

		Collections.shuffle(exemplarModel.getEntitySets());
		Collections.shuffle(studentModel.getEntitySets());

		MappingFinder finder = new MappingFinder();
		finder.getBestMapping(exemplarModel, studentModel);

		assertTrue(true);
	}

	@Test
	public void testFindBestMapping_Vztahy_2() {
		System.out.println("Test - mapping finder: Vztahy 2");
		ERModel exemplarModel = TestUtils.makeERModel_Vztahy_vzor();
		ERModel studentModel = TestUtils.makeERModel_Vztahy_S2();

		Collections.shuffle(exemplarModel.getEntitySets());
		Collections.shuffle(studentModel.getEntitySets());

		MappingFinder finder = new MappingFinder();
		finder.getBestMapping(exemplarModel, studentModel);

		assertTrue(true);
	}
}
