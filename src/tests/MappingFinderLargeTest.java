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

//	@Test
//	public void testFindBestMapping_MHD_1() {
//		System.out.println("Test - mapping finder: MHD 1");
//		ERModel exemplarModel = TestUtils.makeERModel_MHD_Vzor();
//		ERModel studentModel = TestUtils.makeERModel_MHD_S1();
//
//		Collections.shuffle(exemplarModel.getEntitySets());
//		Collections.shuffle(studentModel.getEntitySets());
//
//		MappingFinder finder = new MappingFinder();
//		finder.getBestMapping(exemplarModel, studentModel);
//
//		assertTrue(true);
//	}
//
//	@Test
//	public void testFindBestMapping_MHD_2() {
//		System.out.println("Test - mapping finder: MHD 2");
//		ERModel exemplarModel = TestUtils.makeERModel_MHD_Vzor();
//		ERModel studentModel = TestUtils.makeERModel_MHD_S2();
//
//		Collections.shuffle(exemplarModel.getEntitySets());
//		Collections.shuffle(studentModel.getEntitySets());
//
//		MappingFinder finder = new MappingFinder();
//		finder.getBestMapping(exemplarModel, studentModel);
//
//		assertTrue(true);
//	}
	
//	@Test
//	public void testFindBestMapping_NRSR_1() {
//		System.out.println("Test - mapping finder: NRSR 1");
//		ERModel exemplarModel = TestUtils.makeERModel_NRSR_Vzor();
//		ERModel studentModel = TestUtils.makeERModel_NRSR_S1();
//
//		Collections.shuffle(exemplarModel.getEntitySets());
//		Collections.shuffle(studentModel.getEntitySets());
//
//		MappingFinder finder = new MappingFinder();
//		finder.getBestMapping(exemplarModel, studentModel);
//
//		assertTrue(true);
//	}
//	
//	@Test
//	public void testFindBestMapping_NRSR_2() {
//		System.out.println("Test - mapping finder: NRSR 2");
//		ERModel exemplarModel = TestUtils.makeERModel_NRSR_Vzor();
//		ERModel studentModel = TestUtils.makeERModel_NRSR_S2();
//
//		Collections.shuffle(exemplarModel.getEntitySets());
//		Collections.shuffle(studentModel.getEntitySets());
//
//		MappingFinder finder = new MappingFinder();
//		finder.getBestMapping(exemplarModel, studentModel);
//
//		assertTrue(true);
//	}
	
	@Test
	public void testFindBestMapping_BattleOfTitans_1() {
		System.out.println("Test - mapping finder: BattleOfTitans 1");
		ERModel exemplarModel = TestUtils.makeERModel_BattleOfTitans_Vzor();
		ERModel studentModel = TestUtils.makeERModel_BattleOfTitans_S1();

		Collections.shuffle(exemplarModel.getEntitySets());
		Collections.shuffle(studentModel.getEntitySets());

		MappingFinder finder = new MappingFinder();
		finder.getBestMapping(exemplarModel, studentModel);

		assertTrue(true);
	}
	
	@Test
	public void testFindBestMapping_BattleOfTitans_2() {
		System.out.println("Test - mapping finder: BattleOfTitans 2");
		ERModel exemplarModel = TestUtils.makeERModel_BattleOfTitans_Vzor();
		ERModel studentModel = TestUtils.makeERModel_BattleOfTitans_S2();

		Collections.shuffle(exemplarModel.getEntitySets());
		Collections.shuffle(studentModel.getEntitySets());

		MappingFinder finder = new MappingFinder();
		finder.getBestMapping(exemplarModel, studentModel);

		assertTrue(true);
	}
	
	@Test
	public void testFindBestMapping_Poistovna_1() {
		System.out.println("Test - mapping finder: Poistovna 1");
		ERModel exemplarModel = TestUtils.makeERModel_Poistovna_Vzor();
		ERModel studentModel = TestUtils.makeERModel_Poistovna_S1();

		Collections.shuffle(exemplarModel.getEntitySets());
		Collections.shuffle(studentModel.getEntitySets());

		MappingFinder finder = new MappingFinder();
		finder.getBestMapping(exemplarModel, studentModel);

		assertTrue(true);
	}
	
	@Test
	public void testFindBestMapping_Poistovna_2() {
		System.out.println("Test - mapping finder: Poistovna 2");
		ERModel exemplarModel = TestUtils.makeERModel_Poistovna_Vzor();
		ERModel studentModel = TestUtils.makeERModel_Poistovna_S2();

		Collections.shuffle(exemplarModel.getEntitySets());
		Collections.shuffle(studentModel.getEntitySets());

		MappingFinder finder = new MappingFinder();
		finder.getBestMapping(exemplarModel, studentModel);

		assertTrue(true);
	}
	
	@Test
	public void testFindBestMapping_UFO_1() {
		System.out.println("Test - mapping finder: UFO 1");
		ERModel exemplarModel = TestUtils.makeERModel_UFO_Vzor();
		ERModel studentModel = TestUtils.makeERModel_UFO_S1();

		Collections.shuffle(exemplarModel.getEntitySets());
		Collections.shuffle(studentModel.getEntitySets());

		MappingFinder finder = new MappingFinder();
		finder.getBestMapping(exemplarModel, studentModel);

		assertTrue(true);
	}
	
	@Test
	public void testFindBestMapping_UFO_2() {
		System.out.println("Test - mapping finder: UFO 2");
		ERModel exemplarModel = TestUtils.makeERModel_UFO_Vzor();
		ERModel studentModel = TestUtils.makeERModel_UFO_S2();

		Collections.shuffle(exemplarModel.getEntitySets());
		Collections.shuffle(studentModel.getEntitySets());

		MappingFinder finder = new MappingFinder();
		finder.getBestMapping(exemplarModel, studentModel);

		assertTrue(true);
	}

//	@Test
//	public void testFindBestMapping_Poistovna_1() {
//		System.out.println("Test - mapping finder: Poistovna 1");
//		ERModel exemplarModel = TestUtils.makeERModel_Poistovna_Vzor();
//		ERModel studentModel = TestUtils.makeERModel_Poistovna_S1();
//
//		Collections.shuffle(exemplarModel.getEntitySets());
//		Collections.shuffle(studentModel.getEntitySets());
//
//		MappingFinder finder = new MappingFinder();
//		finder.getBestMapping(exemplarModel, studentModel);
//
//		assertTrue(true);
//	}
//	
//	@Test
//	public void testFindBestMapping_Poistovna_2() {
//		System.out.println("Test - mapping finder: Poistovna 2");
//		ERModel exemplarModel = TestUtils.makeERModel_Poistovna_Vzor();
//		ERModel studentModel = TestUtils.makeERModel_Poistovna_S2();
//
//		Collections.shuffle(exemplarModel.getEntitySets());
//		Collections.shuffle(studentModel.getEntitySets());
//
//		MappingFinder finder = new MappingFinder();
//		finder.getBestMapping(exemplarModel, studentModel);
//
//		assertTrue(true);
//	}
	
//	@Test
//	public void testFindBestMapping_UFO_1() {
//		System.out.println("Test - mapping finder: UFO 1");
//		ERModel exemplarModel = TestUtils.makeERModel_UFO_Vzor();
//		ERModel studentModel = TestUtils.makeERModel_UFO_S1();
//
//		Collections.shuffle(exemplarModel.getEntitySets());
//		Collections.shuffle(studentModel.getEntitySets());
//
//		MappingFinder finder = new MappingFinder();
//		finder.getBestMapping(exemplarModel, studentModel);
//
//		assertTrue(true);
//	}
//	
//	@Test
//	public void testFindBestMapping_UFO_2() {
//		System.out.println("Test - mapping finder: UFO 2");
//		ERModel exemplarModel = TestUtils.makeERModel_UFO_Vzor();
//		ERModel studentModel = TestUtils.makeERModel_UFO_S2();
//
//		Collections.shuffle(exemplarModel.getEntitySets());
//		Collections.shuffle(studentModel.getEntitySets());
//
//		MappingFinder finder = new MappingFinder();
//		finder.getBestMapping(exemplarModel, studentModel);
//
//		assertTrue(true);
//	}
}