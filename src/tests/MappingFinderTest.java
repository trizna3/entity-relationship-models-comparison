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

		Collections.shuffle(exemplarModel.getEntitySets());
		Collections.shuffle(studentModel.getEntitySets());

		MappingFinder finder = new MappingFinder();

		Map<EntitySet, EntitySet> map = finder.getBestMapping(exemplarModel, studentModel);

		System.out.println(PrintUtils.print(map));
	}

}
