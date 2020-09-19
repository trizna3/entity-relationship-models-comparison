package tests;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import common.ERModelUtils;
import common.TransformationAnalystUtils;
import common.enums.EnumTransformation;
import comparing.Mapping;
import entityRelationshipModel.ERModel;
import transformations.Transformation;
import transformations.Transformator;

public class TransformationAnalystTest {

//	@Test
//	public void getPossibleExtractAttributeToOwnEntitySetTransformations() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void getPossibleExtractAttributeToOwnEntitySetTransformationsExecution() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void getPossibleMoveAttributeToIncidentEntitySetTransformations() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void getPossibleMoveAttributeToIncidentEntitySetTransformationsExecution() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void getPossibleMoveAttributeToIncidentAssociationTransformations() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void getPossibleMoveAttributeToIncidentAssociationTransformationsExecution() {
//		fail("Not yet implemented");
//	}

	@Test
	public void getPossibleRebindMNTo1NN1Transformations() {
		ERModel model = TestUtils.getERModels().get(1);
		List<Transformation> transformations = new ArrayList<>();

		TransformationAnalystUtils.getPossibleRebindMNTo1NN1Transformations(transformations, model);

		assertTrue(TestUtils.containsTransformation(transformations, EnumTransformation.REBIND_MN_TO_1NN1));
	}

	@Test
	public void getPossibleRebindMNTo1NN1TransformationsExecution() {
		ERModel model = TestUtils.getERModels().get(1);
		ERModel modelClone = ERModelUtils.getClone(model);
		Mapping mapping = new Mapping(null, model);
		List<Transformation> transformations = new ArrayList<>();

		TransformationAnalystUtils.getPossibleRebindMNTo1NN1Transformations(transformations, model);

		for (Transformation transformation : transformations) {
			Transformator.execute(mapping, transformation);
			Transformator.revert(mapping, transformation);
		}

		assertTrue(ERModelUtils.modelsAreEqual(model, modelClone));
	}

	@Test
	public void getPossibleRebind1NN1ToMNTransformations() {
		ERModel model = TestUtils.getERModels().get(4);
		List<Transformation> transformations = new ArrayList<>();

		TransformationAnalystUtils.getPossibleRebind1NN1ToMNTransformations(transformations, model);

		assertTrue(TestUtils.containsTransformation(transformations, EnumTransformation.REBIND_1NN1_TO_MN));
	}

	@Test
	public void getPossibleRebind1NN1ToMNTransformationsExecution() {
		ERModel model = TestUtils.getERModels().get(4);
		ERModel modelClone = ERModelUtils.getClone(model);
		Mapping mapping = new Mapping(null, model);
		List<Transformation> transformations = new ArrayList<>();

		TransformationAnalystUtils.getPossibleRebind1NN1ToMNTransformations(transformations, model);

		for (Transformation transformation : transformations) {
			Transformator.execute(mapping, transformation);
			Transformator.revert(mapping, transformation);
		}

		assertTrue(ERModelUtils.modelsAreEqual(model, modelClone));
	}

	@Test
	public void getPossibleGeneralizationToAssociationTransformations() {
		ERModel model = TestUtils.getERModels().get(2);
		List<Transformation> transformations = new ArrayList<>();

		TransformationAnalystUtils.getPossibleGeneralizationToAssociationTransformations(transformations, model);

		assertTrue(TestUtils.containsTransformation(transformations, EnumTransformation.GENERALIZATION_TO_11_ASSOCIATION));
	}

	@Test
	public void getPossibleGeneralizationToAssociationTransformationsExecution() {
		ERModel model = TestUtils.getERModels().get(2);
		ERModel modelClone = ERModelUtils.getClone(model);
		Mapping mapping = new Mapping(null, model);
		List<Transformation> transformations = new ArrayList<>();

		TransformationAnalystUtils.getPossibleGeneralizationToAssociationTransformations(transformations, model);

		for (Transformation transformation : transformations) {
			Transformator.execute(mapping, transformation);
			Transformator.revert(mapping, transformation);
		}

		assertTrue(ERModelUtils.modelsAreEqual(model, modelClone));
	}

	@Test
	public void getPossibleContract11AssociationTransformations() {
		ERModel model = TestUtils.getERModels().get(0);
		List<Transformation> transformations = new ArrayList<>();

		TransformationAnalystUtils.getPossibleContract11AssociationTransformations(transformations, model);

		assertTrue(TestUtils.containsTransformation(transformations, EnumTransformation.CONTRACT_11_ASSOCIATION));
	}

	@Test
	public void getPossibleContract11AssociationTransformationsExecution() {
		ERModel model = TestUtils.getERModels().get(0);
		ERModel modelClone = ERModelUtils.getClone(model);
		Mapping mapping = new Mapping(null, model);
		List<Transformation> transformations = new ArrayList<>();

		TransformationAnalystUtils.getPossibleContract11AssociationTransformations(transformations, model);

		for (Transformation transformation : transformations) {
			Transformator.execute(mapping, transformation);
			Transformator.revert(mapping, transformation);
		}

		assertTrue(ERModelUtils.modelsAreEqual(model, modelClone));
	}

	@Test
	public void getPossibleRebindNaryAssociationTransformations() {
		ERModel model = TestUtils.getERModels().get(1);
		List<Transformation> transformations = new ArrayList<>();

		TransformationAnalystUtils.getPossibleRebindNaryAssociationTransformations(transformations, model);

		assertTrue(TestUtils.containsTransformation(transformations, EnumTransformation.REBIND_NARY_ASSOCIATION));
	}

	@Test
	public void getPossibleRebindNaryAssociationTransformationsExecution() {
		ERModel model = TestUtils.getERModels().get(1);
		ERModel modelClone = ERModelUtils.getClone(model);
		Mapping mapping = new Mapping(null, model);
		List<Transformation> transformations = new ArrayList<>();

		TransformationAnalystUtils.getPossibleRebindNaryAssociationTransformations(transformations, model);

		for (Transformation transformation : transformations) {
			Transformator.execute(mapping, transformation);
			Transformator.revert(mapping, transformation);
		}

		assertTrue(ERModelUtils.modelsAreEqual(model, modelClone));
	}
}