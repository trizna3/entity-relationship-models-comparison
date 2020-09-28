package tests;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;

import common.ERModelUtils;
import common.enums.EnumTransformation;
import common.enums.EnumTransformationRole;
import common.enums.Enums;
import comparing.Mapping;
import entityRelationshipModel.Association;
import entityRelationshipModel.ERModel;
import entityRelationshipModel.EntitySet;
import entityRelationshipModel.Generalization;
import entityRelationshipModel.TransformableAttribute;
import entityRelationshipModel.TransformableFlag;
import transformations.Transformation;
import transformations.Transformator;

public class TransformatorRevertTest {

	@Test
	public void testExtractAttributeToOwnEntitySetNewRevert() {
		ERModel model = TestUtils.getERModels().get(0);
		ERModel modelClone = ERModelUtils.getClone(model);
		Mapping mapping = new Mapping(null, model);

		EntitySet entitySet = model.getEntitySets().get(1);
		assert "Izby".equals(entitySet.getName());

		TransformableAttribute attribute = new TransformableAttribute("Cislo");

		assert entitySet.getAttributes().contains(attribute.getAttribute());
		assert model.getEntitySets().size() == 6;

		Transformation transformation = new Transformation(EnumTransformation.EXTRACT_ATTR_TO_OWN_ENTITY_SET);
		transformation.addArgument(entitySet, EnumTransformationRole.SOURCE_ENTITY_SET);
		transformation.addArgument(attribute, EnumTransformationRole.ATTRIBUTE);

		Transformator.execute(mapping, transformation);
		Transformator.revert(mapping, transformation);
		Transformator.execute(mapping, transformation);
		Transformator.revert(mapping, transformation);

		assertTrue(ERModelUtils.modelsAreEqual(model, modelClone));
	}

	@Test
	public void testExtractAttributeToOwnEntitySetExistingRevert() {
		ERModel model = TestUtils.getERModels().get(0);
		ERModel modelClone = ERModelUtils.getClone(model);
		Mapping mapping = new Mapping(null, model);

		EntitySet entitySet = model.getEntitySets().get(1);
		assert "Izby".equals(entitySet.getName());

		TransformableAttribute attribute = new TransformableAttribute("Cislo");

		EntitySet newEntitySet = new EntitySet(attribute.getAttribute(), new ArrayList<>(Arrays.asList(Enums.NAME_ATTRIBUTE)));
		model.addEntitySet(newEntitySet);

		assert entitySet.getAttributes().contains(attribute.getAttribute());
		assert model.getEntitySets().size() == 7;

		Transformation transformation = new Transformation(EnumTransformation.EXTRACT_ATTR_TO_OWN_ENTITY_SET);
		transformation.addArgument(entitySet, EnumTransformationRole.SOURCE_ENTITY_SET);
		transformation.addArgument(attribute, EnumTransformationRole.ATTRIBUTE);
		transformation.addArgument(newEntitySet, EnumTransformationRole.DEST_ENTITY_SET);

		Transformator.execute(mapping, transformation);
		Transformator.revert(mapping, transformation);
		Transformator.execute(mapping, transformation);
		Transformator.revert(mapping, transformation);

		assertTrue(ERModelUtils.modelsAreEqual(model, modelClone));
	}

	@Test
	public void testMoveAttributeToIncidentAssociationRevert() {
		ERModel model = TestUtils.getERModels().get(0);
		ERModel modelClone = ERModelUtils.getClone(model);
		Mapping mapping = new Mapping(null, model);

		EntitySet entitySet = model.getEntitySets().get(1);
		assert "Izby".equals(entitySet.getName());

		EntitySet neighbour = model.getEntitySets().get(0);
		assert "Budovy".equals(neighbour.getName());

		Association association = (Association) entitySet.getNeighbours().get(neighbour).get(0);
		TransformableAttribute attribute = new TransformableAttribute("Cislo");

		assert entitySet.getAttributes().contains(attribute.getAttribute());
		assert !association.getAttributes().contains(attribute.getAttribute());

		Transformation transformation = new Transformation(EnumTransformation.MOVE_ATTR_TO_INCIDENT_ASSOCIATION);
		transformation.addArgument(entitySet, EnumTransformationRole.ENTITY_SET);
		transformation.addArgument(attribute, EnumTransformationRole.ATTRIBUTE);
		transformation.addArgument(association, EnumTransformationRole.ASSOCIATION);

		Transformator.execute(mapping, transformation);
		Transformator.revert(mapping, transformation);
		Transformator.execute(mapping, transformation);
		Transformator.revert(mapping, transformation);

		assertTrue(ERModelUtils.modelsAreEqual(model, modelClone));
	}

	@Test
	public void testRebindMNTo1NN1Revert() {
		ERModel model = TestUtils.getERModels().get(1);
		ERModel modelClone = ERModelUtils.getClone(model);
		Mapping mapping = new Mapping(null, model);

		Association association = (Association) model.getRelationships().get(0);

		assert association.isBinary();
		assert model.getEntitySets().size() == 6;

		Transformation transformation = new Transformation(EnumTransformation.REBIND_MN_TO_1NN1);
		transformation.addArgument(association, EnumTransformationRole.ASSOCIATION);

		Transformator.execute(mapping, transformation);
		Transformator.revert(mapping, transformation);
		Transformator.execute(mapping, transformation);
		Transformator.revert(mapping, transformation);

		assertTrue(ERModelUtils.modelsAreEqual(model, modelClone));
	}

	@Test
	public void testGeneralizationTo11AssociationRevert() {
		ERModel model = TestUtils.getERModels().get(2);
		ERModel modelClone = ERModelUtils.getClone(model);
		Mapping mapping = new Mapping(null, model);

		Generalization generalization = (Generalization) model.getRelationships().get(6);

		Transformation transformation = new Transformation(EnumTransformation.GENERALIZATION_TO_11_ASSOCIATION);
		transformation.addArgument(generalization, EnumTransformationRole.GENERALIZATION);

		Transformator.execute(mapping, transformation);
		Transformator.revert(mapping, transformation);
		Transformator.execute(mapping, transformation);
		Transformator.revert(mapping, transformation);

		assertTrue(ERModelUtils.modelsAreEqual(model, modelClone));
	}

	@Test
	public void testContract11AssociationRevert() {
		ERModel model = TestUtils.getERModels().get(0);
		ERModel modelClone = ERModelUtils.getClone(model);
		Mapping mapping = new Mapping(null, model);

		Association association = (Association) model.getRelationships().get(5);

		assert association.isBinary();
		assert model.getEntitySets().size() == 6;

		Transformation transformation = new Transformation(EnumTransformation.CONTRACT_11_ASSOCIATION);
		transformation.addArgument(association, EnumTransformationRole.ASSOCIATION);

		Transformator.execute(mapping, transformation);
		Transformator.revert(mapping, transformation);
		Transformator.execute(mapping, transformation);
		Transformator.revert(mapping, transformation);

		assertTrue(ERModelUtils.modelsAreEqual(model, modelClone));
	}

	@Test
	public void testContract11AssociationRevertOnExemplarModel() {
		ERModel exemplarModel = TestUtils.getERModels().get(0);
		ERModel exemplarModelClone = ERModelUtils.getClone(exemplarModel);
		ERModel studentModel = TestUtils.getERModels().get(1);
		ERModel studentModelClone = ERModelUtils.getClone(studentModel);
		Mapping mapping = new Mapping(exemplarModel, studentModel);

		Association association = (Association) exemplarModel.getRelationships().get(5);
		TransformableFlag flag = new TransformableFlag();

		assert association.isBinary();
		assert exemplarModel.getEntitySets().size() == 6;

		Transformation transformation = new Transformation(EnumTransformation.CONTRACT_11_ASSOCIATION);
		transformation.addArgument(association, EnumTransformationRole.ASSOCIATION);
		transformation.addArgument(flag, EnumTransformationRole.EXEMPLAR_MODEL_FLAG);

		Transformator.execute(mapping, transformation);
		Transformator.revert(mapping, transformation);
		Transformator.execute(mapping, transformation);
		Transformator.revert(mapping, transformation);

		assertTrue(ERModelUtils.modelsAreEqual(exemplarModel, exemplarModelClone));
		assertTrue(ERModelUtils.modelsAreEqual(studentModel, studentModelClone));
	}

	@Test
	public void testRebindNaryAssociationRevert() {
		ERModel model = TestUtils.getERModels().get(1);
		ERModel modelClone = ERModelUtils.getClone(model);
		Mapping mapping = new Mapping(null, model);

		Association association = (Association) model.getRelationships().get(4);

		assert !association.isBinary();

		Transformation transformation = new Transformation(EnumTransformation.REBIND_NARY_ASSOCIATION);
		transformation.addArgument(association, EnumTransformationRole.ASSOCIATION);

		Transformator.execute(mapping, transformation);
		Transformator.revert(mapping, transformation);
		Transformator.execute(mapping, transformation);
		Transformator.revert(mapping, transformation);

		assertTrue(ERModelUtils.modelsAreEqual(model, modelClone));
	}
}
