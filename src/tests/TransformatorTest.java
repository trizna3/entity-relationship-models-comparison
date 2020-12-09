package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import common.ERModelUtils;
import common.RelationshipUtils;
import common.TransformationUtils;
import common.enums.EnumConstants;
import common.enums.EnumRelationshipSideRole;
import common.enums.EnumTransformation;
import common.enums.EnumTransformationRole;
import comparing.Mapping;
import entityRelationshipModel.Association;
import entityRelationshipModel.Attribute;
import entityRelationshipModel.ERModel;
import entityRelationshipModel.EntitySet;
import entityRelationshipModel.Generalization;
import entityRelationshipModel.Relationship;
import entityRelationshipModel.TransformableFlag;
import transformations.Transformation;
import transformations.Transformator;

public class TransformatorTest {

	@Test
	public void testExtractAttributeToOwnEntitySetNew() {
		ERModel model = TestUtils.getERModels().get(0);
		Mapping mapping = new Mapping(null, model);

		EntitySet entitySet = model.getEntitySets().get(1);
		assert "Izby".equals(entitySet.getNameText());

		Attribute attribute = new Attribute("Cislo");

		assert entitySet.getAttributes().contains(attribute);
		assert model.getEntitySets().size() == 6;

		Transformation transformation = new Transformation(EnumTransformation.EXTRACT_ATTR_TO_OWN_ENTITY_SET);
		transformation.addArgument(entitySet, EnumTransformationRole.SOURCE_ENTITY_SET);
		transformation.addArgument(attribute, EnumTransformationRole.ATTRIBUTE);

		Transformator.execute(mapping, transformation);

		assertNotNull(TransformationUtils.getTransformableByRole(transformation, EnumTransformationRole.ATTRIBUTE));
		assertNotNull(TransformationUtils.getTransformableByRole(transformation, EnumTransformationRole.DEST_ENTITY_SET));
		assertNotNull(TransformationUtils.getTransformableByRole(transformation, EnumTransformationRole.SOURCE_ENTITY_SET));

		assertFalse(entitySet.getAttributes().contains(attribute));
		assertTrue(model.getEntitySets().size() == 7);
		EntitySet newEntitySet = ERModelUtils.getEntitySetByName(model, attribute.getAttribute());
		assertNotNull(newEntitySet);
		assertTrue(newEntitySet.getAttributes().contains(new Attribute(EnumConstants.NAME_ATTRIBUTE)));
		Relationship relationship = entitySet.getNeighbours().get(newEntitySet).get(0);
		assertNotNull(relationship);
		assertEquals(RelationshipUtils.getRole(relationship, entitySet), EnumRelationshipSideRole.CARDINALITY_MANY);
		assertEquals(RelationshipUtils.getRole(relationship, newEntitySet), EnumRelationshipSideRole.CARDINALITY_ONE);
	}

	@Test
	public void testExtractAttributeToOwnEntitySetExisting() {
		ERModel model = TestUtils.getERModels().get(0);
		Mapping mapping = new Mapping(null, model);

		EntitySet entitySet = model.getEntitySets().get(1);
		assert "Izby".equals(entitySet.getNameText());

		Attribute attribute = new Attribute("Cislo");

		EntitySet newEntitySet = new EntitySet(attribute.getAttribute(), new ArrayList<>(Arrays.asList(EnumConstants.NAME_ATTRIBUTE)));
		model.addEntitySet(newEntitySet);

		assert entitySet.getAttributes().contains(attribute);
		assert model.getEntitySets().size() == 7;

		Transformation transformation = new Transformation(EnumTransformation.EXTRACT_ATTR_TO_OWN_ENTITY_SET);
		transformation.addArgument(entitySet, EnumTransformationRole.SOURCE_ENTITY_SET);
		transformation.addArgument(attribute, EnumTransformationRole.ATTRIBUTE);
		transformation.addArgument(newEntitySet, EnumTransformationRole.DEST_ENTITY_SET);

		Transformator.execute(mapping, transformation);

		assertNotNull(TransformationUtils.getTransformableByRole(transformation, EnumTransformationRole.ATTRIBUTE));
		assertNotNull(TransformationUtils.getTransformableByRole(transformation, EnumTransformationRole.DEST_ENTITY_SET));
		assertNotNull(TransformationUtils.getTransformableByRole(transformation, EnumTransformationRole.SOURCE_ENTITY_SET));

		assertFalse(entitySet.getAttributes().contains(attribute));
		assertTrue(model.getEntitySets().size() == 7);
		Relationship relationship = entitySet.getNeighbours().get(newEntitySet).get(0);
		assertNotNull(relationship);
		assertEquals(RelationshipUtils.getRole(relationship, entitySet), EnumRelationshipSideRole.CARDINALITY_MANY);
		assertEquals(RelationshipUtils.getRole(relationship, newEntitySet), EnumRelationshipSideRole.CARDINALITY_ONE);
	}

	@Test
	public void testMoveAttributeToIncidentAssociation() {
		ERModel model = TestUtils.getERModels().get(0);
		Mapping mapping = new Mapping(null, model);

		EntitySet entitySet = model.getEntitySets().get(1);
		assert "Izby".equals(entitySet.getNameText());

		EntitySet neighbour = model.getEntitySets().get(0);
		assert "Budovy".equals(neighbour.getNameText());

		Association association = (Association) entitySet.getNeighbours().get(neighbour).get(0);

		Attribute attribute = new Attribute("Cislo");

		assert entitySet.getAttributes().contains(attribute);
		assert !association.getAttributes().contains(attribute);

		Transformation transformation = new Transformation(EnumTransformation.MOVE_ATTR_TO_INCIDENT_ASSOCIATION);
		transformation.addArgument(entitySet, EnumTransformationRole.ENTITY_SET);
		transformation.addArgument(attribute, EnumTransformationRole.ATTRIBUTE);
		transformation.addArgument(association, EnumTransformationRole.ASSOCIATION);

		Transformator.execute(mapping, transformation);

		assertNotNull(TransformationUtils.getTransformableByRole(transformation, EnumTransformationRole.ATTRIBUTE));
		assertNotNull(TransformationUtils.getTransformableByRole(transformation, EnumTransformationRole.ASSOCIATION));
		assertNotNull(TransformationUtils.getTransformableByRole(transformation, EnumTransformationRole.ENTITY_SET));

		assertFalse(entitySet.getAttributes().contains(attribute));
		assertTrue(association.getAttributes().contains(attribute));
	}

	@Test
	public void testMoveAttributeToIncidentEntitySet() {
		ERModel model = TestUtils.makeERModel_Eshop_Vzor();
		Mapping mapping = new Mapping(null, model);

		EntitySet entitySet = model.getEntitySets().get(1);
		assert "Produkty".equals(entitySet.getNameText());

		EntitySet neighbour = model.getEntitySets().get(2);
		assert "Nazvy specialnych atributov".equals(neighbour.getNameText());

		Association association = (Association) entitySet.getNeighbours().get(neighbour).get(0);

		Attribute attribute = new Attribute("Hodnota");

		assert !entitySet.getAttributes().contains(attribute);
		assert association.getAttributes().contains(attribute);

		Transformation transformation = new Transformation(EnumTransformation.MOVE_ATTR_TO_INCIDENT_ENTITY_SET);
		transformation.addArgument(entitySet, EnumTransformationRole.ENTITY_SET);
		transformation.addArgument(attribute, EnumTransformationRole.ATTRIBUTE);
		transformation.addArgument(association, EnumTransformationRole.ASSOCIATION);

		Transformator.execute(mapping, transformation);

		assertNotNull(TransformationUtils.getTransformableByRole(transformation, EnumTransformationRole.ATTRIBUTE));
		assertNotNull(TransformationUtils.getTransformableByRole(transformation, EnumTransformationRole.ASSOCIATION));
		assertNotNull(TransformationUtils.getTransformableByRole(transformation, EnumTransformationRole.ENTITY_SET));

		assertTrue(entitySet.getAttributes().contains(attribute));
		assertFalse(association.getAttributes().contains(attribute));
	}

	@Test
	public void testRebindMNTo1NN1() {
		ERModel model = TestUtils.getERModels().get(1);
		Mapping mapping = new Mapping(null, model);

		Association association = (Association) model.getRelationships().get(0);

		assert association.isBinary();
		EntitySet entitySet1 = association.getFirstSide().getEntitySet();
		EntitySet entitySet2 = association.getSecondSide().getEntitySet();

		assert model.getEntitySets().size() == 6;

		Transformation transformation = new Transformation(EnumTransformation.REBIND_MN_TO_1NN1);
		transformation.addArgument(association, EnumTransformationRole.ASSOCIATION);

		Transformator.execute(mapping, transformation);

		assertNotNull(TransformationUtils.getTransformableByRole(transformation, EnumTransformationRole.ASSOCIATION));
		assertNotNull(TransformationUtils.getTransformableByRole(transformation, EnumTransformationRole.ASSOCIATION_1));
		assertNotNull(TransformationUtils.getTransformableByRole(transformation, EnumTransformationRole.ASSOCIATION_2));
		assertNotNull(TransformationUtils.getTransformableByRole(transformation, EnumTransformationRole.ENTITY_SET));

		Association association1 = (Association) TransformationUtils.getTransformableByRole(transformation, EnumTransformationRole.ASSOCIATION_1);
		Association association2 = (Association) TransformationUtils.getTransformableByRole(transformation, EnumTransformationRole.ASSOCIATION_2);
		EntitySet entitySet = (EntitySet) TransformationUtils.getTransformableByRole(transformation, EnumTransformationRole.ENTITY_SET);

		assertTrue(model.getEntitySets().size() == 7);
		assertFalse(model.contains(association));
		assertTrue(model.contains(entitySet));
		assertTrue(model.contains(association1));
		assertTrue(model.contains(association2));
		assertTrue(association1.isBinary());
		assertTrue(association2.isBinary());
		assertTrue(RelationshipUtils.getRole(association1, entitySet1) != null && EnumRelationshipSideRole.CARDINALITY_ONE.equals(RelationshipUtils.getRole(association1, entitySet1)));
		assertTrue(EnumRelationshipSideRole.CARDINALITY_MANY.equals(RelationshipUtils.getRole(association1, entitySet)));
		assertTrue(EnumRelationshipSideRole.CARDINALITY_MANY.equals(RelationshipUtils.getRole(association2, entitySet)));
		assertTrue(RelationshipUtils.getRole(association2, entitySet2) != null && EnumRelationshipSideRole.CARDINALITY_ONE.equals(RelationshipUtils.getRole(association2, entitySet2)));
	}

	@Test
	public void testGeneralizationTo11Association() {
		ERModel model = TestUtils.getERModels().get(2);
		Mapping mapping = new Mapping(null, model);

		Generalization generalization = (Generalization) model.getRelationships().get(6);

		Transformation transformation = new Transformation(EnumTransformation.GENERALIZATION_TO_11_ASSOCIATION);
		transformation.addArgument(generalization, EnumTransformationRole.GENERALIZATION);

		Transformator.execute(mapping, transformation);

		assertNotNull(TransformationUtils.getTransformableByRole(transformation, EnumTransformationRole.ASSOCIATION));
		assertNotNull(TransformationUtils.getTransformableByRole(transformation, EnumTransformationRole.GENERALIZATION));

		Association association = (Association) TransformationUtils.getTransformableByRole(transformation, EnumTransformationRole.ASSOCIATION);

		assertNotNull(association);
		assertTrue(association.isBinary());
		assertFalse(model.contains(generalization));
		assertTrue(model.contains(association));
		assertEquals(generalization.getFirstSide().getEntitySet(), association.getFirstSide().getEntitySet());
		assertEquals(generalization.getSecondSide().getEntitySet(), association.getSecondSide().getEntitySet());
	}

	@Test
	public void testContract11Association() {
		ERModel model = TestUtils.getERModels().get(0);
		Mapping mapping = new Mapping(null, model);

		Association association = (Association) model.getRelationships().get(5);

		assert association.isBinary();
		EntitySet entitySet1 = association.getFirstSide().getEntitySet();
		EntitySet entitySet2 = association.getSecondSide().getEntitySet();
		List<Relationship> entitySet1Relationships = new ArrayList<>(entitySet1.getIncidentRelationships());
		List<Relationship> entitySet2Relationships = new ArrayList<>(entitySet2.getIncidentRelationships());
		

		String entitySet1Name = entitySet1.getNameText();
		String entitySet2Name = entitySet2.getNameText();

		assert model.getEntitySets().size() == 6;

		Transformation transformation = new Transformation(EnumTransformation.CONTRACT_11_ASSOCIATION);
		transformation.addArgument(association, EnumTransformationRole.ASSOCIATION);

		Transformator.execute(mapping, transformation);

		assertNotNull(TransformationUtils.getTransformableByRole(transformation, EnumTransformationRole.ENTITY_SET));
		assertNotNull(TransformationUtils.getTransformableByRole(transformation, EnumTransformationRole.ASSOCIATION));
		assertNotNull(TransformationUtils.getTransformableByRole(transformation, EnumTransformationRole.TRANSFORMABLE_LIST));

		assertTrue(model.getEntitySets().size() == 5);
		assertFalse(model.contains(association));
		EntitySet entitySet = ERModelUtils.getEntitySetByName(model, entitySet1Name + EnumConstants.DELIMITER_SEMICOLON + entitySet2Name);
		assertNotNull(entitySet);
		for (Attribute attribute : entitySet1.getAttributes()) {
			assertTrue(entitySet.getAttributes().contains(attribute));
		}
		for (Attribute attribute : entitySet2.getAttributes()) {
			assertTrue(entitySet.getAttributes().contains(attribute));
		}
		
		// relationships check
		assertTrue(entitySet2.getIncidentRelationships().isEmpty());
		for (Relationship relationship : entitySet2Relationships) {
			if (relationship == association) continue;
			assertTrue(RelationshipUtils.contains(relationship, entitySet1));
			assertFalse(RelationshipUtils.contains(relationship, entitySet2));
		}
		for (Relationship relationship : entitySet1Relationships) {
			if (relationship == association) continue;
			assertTrue(RelationshipUtils.contains(relationship, entitySet1));
			assertFalse(RelationshipUtils.contains(relationship, entitySet2));
		}
	}

	@Test
	public void testContract11AssociationOnExemplarModel() {
		ERModel exemplarModel = TestUtils.getERModels().get(0);
		ERModel studentModel = TestUtils.getERModels().get(1);
		ERModel studentModelClone = ERModelUtils.getClone(studentModel);
		Mapping mapping = new Mapping(exemplarModel, studentModel);

		Association association = (Association) exemplarModel.getRelationships().get(5);
		TransformableFlag flag = new TransformableFlag();

		assert association.isBinary();
		EntitySet entitySet1 = association.getFirstSide().getEntitySet();
		EntitySet entitySet2 = association.getSecondSide().getEntitySet();

		String entitySet1Name = entitySet1.getNameText();
		String entitySet2Name = entitySet2.getNameText();

		assert exemplarModel.getEntitySets().size() == 6;

		Transformation transformation = new Transformation(EnumTransformation.CONTRACT_11_ASSOCIATION);
		transformation.addArgument(association, EnumTransformationRole.ASSOCIATION);
		transformation.addArgument(flag, EnumTransformationRole.EXEMPLAR_MODEL_FLAG);

		Transformator.execute(mapping, transformation);

		assertNotNull(TransformationUtils.getTransformableByRole(transformation, EnumTransformationRole.ENTITY_SET));
		assertNotNull(TransformationUtils.getTransformableByRole(transformation, EnumTransformationRole.ASSOCIATION));
		assertNotNull(TransformationUtils.getTransformableByRole(transformation, EnumTransformationRole.TRANSFORMABLE_LIST));
		assertNotNull(TransformationUtils.getTransformableByRole(transformation, EnumTransformationRole.EXEMPLAR_MODEL_FLAG));

		assertTrue(exemplarModel.getEntitySets().size() == 5);
		assertFalse(exemplarModel.contains(association));
		EntitySet entitySet = ERModelUtils.getEntitySetByName(exemplarModel, entitySet1Name + EnumConstants.DELIMITER_SEMICOLON + entitySet2Name);
		assertNotNull(entitySet);
		for (Attribute attribute : entitySet1.getAttributes()) {
			assertTrue(entitySet.getAttributes().contains(attribute));
		}
		for (Attribute attribute : entitySet2.getAttributes()) {
			assertTrue(entitySet.getAttributes().contains(attribute));
		}
		assertTrue(ERModelUtils.modelsAreEqual(studentModel, studentModelClone));
	}

	@Test
	public void testRebindNaryAssociation() {
		ERModel model = TestUtils.getERModels().get(1);
		Mapping mapping = new Mapping(null, model);

		Association association = (Association) model.getRelationships().get(4);

		assert !association.isBinary();

		Transformation transformation = new Transformation(EnumTransformation.REBIND_NARY_ASSOCIATION);
		transformation.addArgument(association, EnumTransformationRole.ASSOCIATION);

		Transformator.execute(mapping, transformation);

		assertNotNull(TransformationUtils.getTransformableByRole(transformation, EnumTransformationRole.ENTITY_SET));
		assertNotNull(TransformationUtils.getTransformableByRole(transformation, EnumTransformationRole.ASSOCIATION));

		EntitySet entitySet = (EntitySet) TransformationUtils.getTransformableByRole(transformation, EnumTransformationRole.ENTITY_SET);

		assertFalse(model.contains(association));
		assertTrue(model.getRelationships().size() == 7);
		assertTrue(model.getEntitySets().size() == 7);
		assertNotNull(entitySet);
		assertTrue(model.contains(entitySet));
		for (Relationship relationship : entitySet.getIncidentRelationships()) {
			assertTrue(relationship instanceof Association);
			Association assoc = (Association) relationship;
			assertTrue(assoc.contains(entitySet));
			assertTrue(assoc.isBinary());
			assertTrue(EnumRelationshipSideRole.CARDINALITY_MANY.equals(RelationshipUtils.getRole(assoc, entitySet)));
			assertTrue(EnumRelationshipSideRole.CARDINALITY_ONE.equals(RelationshipUtils.getOtherSide(assoc, entitySet).getRole()));
		}
	}
}
