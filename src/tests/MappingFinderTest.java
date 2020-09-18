package tests;

import java.util.Collections;
import java.util.Map;

import org.junit.Test;

import common.PrintUtils;
import entityRelationshipModel.ERModel;
import entityRelationshipModel.EntitySet;
import mappingSearch.mappingFinder.MappingFinder;

public class MappingFinderTest {

	@Test
	public void testFindBestMapping() {
		ERModel exemplarModel = TestUtils.getERModels().get(0);
		ERModel studentModel = TestUtils.getERModels().get(3);

		EntitySet platby = exemplarModel.getEntitySets().get(3);
		EntitySet zmluvy = exemplarModel.getEntitySets().get(2);
		EntitySet izby = exemplarModel.getEntitySets().get(1);
		EntitySet konta = exemplarModel.getEntitySets().get(5);
		EntitySet studenti = exemplarModel.getEntitySets().get(4);
		EntitySet budovy = exemplarModel.getEntitySets().get(0);

		exemplarModel.getEntitySets().clear();
		exemplarModel.getEntitySets().add(platby);
		exemplarModel.getEntitySets().add(zmluvy);
		exemplarModel.getEntitySets().add(izby);
		exemplarModel.getEntitySets().add(konta);
		exemplarModel.getEntitySets().add(studenti);
		exemplarModel.getEntitySets().add(budovy);

		Collections.shuffle(studentModel.getEntitySets());

		MappingFinder finder = new MappingFinder();

		Map<EntitySet, EntitySet> map = finder.getBestMapping(exemplarModel, studentModel);

		System.out.println(PrintUtils.print(map));
	}

}
