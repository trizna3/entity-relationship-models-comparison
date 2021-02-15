package sk.trizna.erm_comparison.tests;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;

import sk.trizna.erm_comparison.common.enums.EnumConstants;
import sk.trizna.erm_comparison.common.enums.EnumTransformation;
import sk.trizna.erm_comparison.common.enums.EnumTransformationRole;
import sk.trizna.erm_comparison.common.utils.ERModelUtils;
import sk.trizna.erm_comparison.comparing.mapping.Mapping;
import sk.trizna.erm_comparison.entity_relationship_model.Association;
import sk.trizna.erm_comparison.entity_relationship_model.Attribute;
import sk.trizna.erm_comparison.entity_relationship_model.ERModel;
import sk.trizna.erm_comparison.entity_relationship_model.EntitySet;
import sk.trizna.erm_comparison.entity_relationship_model.Generalization;
import sk.trizna.erm_comparison.entity_relationship_model.TransformableFlag;
import sk.trizna.erm_comparison.transformations.Transformator;

public class TransformatorRevertTest {

	@Test
	public void testExtractAttributeToOwnEntitySetNewRevert() {
		ERModel model = TestUtils.getERModels().get(0);
		ERModel modelClone = ERModelUtils.getClone(model);
		Mapping mapping = new Mapping(null, model);

		EntitySet entitySet = model.getEntitySets().get(1);
		assert "Izby".equals(entitySet.getNameText());

		Attribute attribute = entitySet.getAttribute("Cislo");

		assert entitySet.getAttributes().contains(attribute);
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
		assert "Izby".equals(entitySet.getNameText());

		Attribute attribute = entitySet.getAttribute("Cislo");

		EntitySet newEntitySet = new EntitySet(attribute.getText(), new ArrayList<>(Arrays.asList(EnumConstants.NAME_ATTRIBUTE)));
		model.addEntitySet(newEntitySet);

		assert entitySet.getAttributes().contains(attribute);
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
		assert "Izby".equals(entitySet.getNameText());

		EntitySet neighbour = model.getEntitySets().get(0);
		assert "Budovy".equals(neighbour.getNameText());

		Association association = (Association) entitySet.getNeighbours().get(neighbour).get(0);
		Attribute attribute = entitySet.getAttribute("Cislo");

		assert entitySet.getAttributes().contains(attribute);
		assert !association.getAttributes().contains(attribute);

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
