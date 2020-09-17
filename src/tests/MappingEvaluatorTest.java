package tests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import common.ERModelUtils;
import comparing.Mapping;
import entityRelationshipModel.ERModel;
import mappingSearch.mappingEvaluator.MappingEvaluator;
import transformations.Transformation;
import transformations.Transformator;

public class MappingEvaluatorTest {

	@Test
	public void test() {

		ERModel model1 = TestUtils.getERModels().get(0);
		ERModel model2 = TestUtils.getERModels().get(1);
		Mapping mapping = new Mapping(model1, model2);

		assert model1.getEntitySets().size() == model2.getEntitySets().size();
		for (int i = 0; i < model1.getEntitySets().size(); i++) {
			mapping.map(model1.getEntitySets().get(i), model2.getEntitySets().get(i));
		}

		new MappingEvaluator().expandTransformationList(mapping);

		for (Transformation transformation : mapping.getTransformations()) {
			Transformator.execute(mapping, transformation);
		}

		assertTrue(ERModelUtils.modelsAreEqual(model1, model2));
	}
}
