package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

import org.junit.Test;

import common.ERModelUtils;
import common.RelationshipUtils;
import common.TransformationUtils;
import common.enums.Enum;
import common.enums.EnumTransformation;
import common.enums.EnumTransformationRole;
import comparing.Mapping;
import entityRelationshipModel.Association;
import entityRelationshipModel.ERModel;
import entityRelationshipModel.EntitySet;
import entityRelationshipModel.Generalization;
import entityRelationshipModel.Relationship;
import entityRelationshipModel.TransformableAttribute;
import entityRelationshipModel.TransformableFlag;
import transformations.Transformation;
import transformations.Transformator;

public class TransformatorTest {

	@Test
	public void testExtractAttributeToOwnEntitySetNew() {
		ERModel model = TestUtils.getERModels().get(0);
		Mapping mapping = new Mapping(null, model);

		EntitySet entitySet = model.getEntitySets().get(1);
		entitySet.setTransformationRole(EnumTransformationRole.SOURCE_ENTITY_SET);
		assert "Izby".equals(entitySet.getName());

		TransformableAttribute attribute = new TransformableAttribute("Cislo");
		attribute.setTransformationRole(EnumTransformationRole.ATTRIBUTE);

		assert entitySet.getAttributes().contains(attribute.getAttribute());
		assert model.getEntitySets().size() == 6;

		Transformator.execute(mapping, new Transformation(EnumTransformation.EXTRACT_ATTR_TO_OWN_ENTITY_SET, new HashSet<>(Arrays.asList(entitySet, attribute))));

		assertFalse(entitySet.getAttributes().contains(attribute.getAttribute()));
		assertTrue(model.getEntitySets().size() == 7);
		EntitySet newEntitySet = ERModelUtils.getEntitySetByName(model, attribute.getAttribute());
		assertNotNull(newEntitySet);
		assertTrue(newEntitySet.getAttributes().contains(Enum.NAME_ATTRIBUTE));
		Relationship relationship = entitySet.getNeighbours().get(newEntitySet).get(0);
		assertNotNull(relationship);
		assertEquals(RelationshipUtils.getRole(relationship, entitySet), Enum.CARDINALITY_MANY);
		assertEquals(RelationshipUtils.getRole(relationship, newEntitySet), Enum.CARDINALITY_ONE);
	}

	@Test
	public void testExtractAttributeToOwnEntitySetExisting() {
		ERModel model = TestUtils.getERModels().get(0);
		Mapping mapping = new Mapping(null, model);

		EntitySet entitySet = model.getEntitySets().get(1);
		entitySet.setTransformationRole(EnumTransformationRole.SOURCE_ENTITY_SET);
		assert "Izby".equals(entitySet.getName());

		TransformableAttribute attribute = new TransformableAttribute("Cislo");
		attribute.setTransformationRole(EnumTransformationRole.ATTRIBUTE);

		EntitySet newEntitySet = new EntitySet(attribute.getAttribute(), new ArrayList<>(Arrays.asList(Enum.NAME_ATTRIBUTE)));
		newEntitySet.setTransformationRole(EnumTransformationRole.DEST_ENTITY_SET);
		model.addEntitySet(newEntitySet);

		assert entitySet.getAttributes().contains(attribute.getAttribute());
		assert model.getEntitySets().size() == 7;

		Transformator.execute(mapping, new Transformation(EnumTransformation.EXTRACT_ATTR_TO_OWN_ENTITY_SET, new HashSet<>(Arrays.asList(entitySet, attribute, newEntitySet))));

		assertFalse(entitySet.getAttributes().contains(attribute.getAttribute()));
		assertTrue(model.getEntitySets().size() == 7);
		Relationship relationship = entitySet.getNeighbours().get(newEntitySet).get(0);
		assertNotNull(relationship);
		assertEquals(RelationshipUtils.getRole(relationship, entitySet), Enum.CARDINALITY_MANY);
		assertEquals(RelationshipUtils.getRole(relationship, newEntitySet), Enum.CARDINALITY_ONE);
	}

	@Test
	public void testMoveAttributeToIncidentAssociation() {
		ERModel model = TestUtils.getERModels().get(0);
		Mapping mapping = new Mapping(null, model);

		EntitySet entitySet = model.getEntitySets().get(1);
		entitySet.setTransformationRole(EnumTransformationRole.ENTITY_SET);
		assert "Izby".equals(entitySet.getName());

		EntitySet neighbour = model.getEntitySets().get(0);
		assert "Budovy".equals(neighbour.getName());

		Association association = (Association) entitySet.getNeighbours().get(neighbour).get(0);
		association.setTransformationRole(EnumTransformationRole.ASSOCIATION);

		TransformableAttribute attribute = new TransformableAttribute("Cislo");
		attribute.setTransformationRole(EnumTransformationRole.ATTRIBUTE);

		assert entitySet.getAttributes().contains(attribute.getAttribute());
		assert !association.getAttributes().contains(attribute.getAttribute());

		Transformator.execute(mapping, new Transformation(EnumTransformation.MOVE_ATTR_TO_INCIDENT_ASSOCIATION, new HashSet<>(Arrays.asList(entitySet, attribute, association))));

		assertFalse(entitySet.getAttributes().contains(attribute.getAttribute()));
		assertTrue(association.getAttributes().contains(attribute.getAttribute()));
	}

	@Test
	public void testRebindMNTo1NN1() {
		ERModel model = TestUtils.getERModels().get(1);
		Mapping mapping = new Mapping(null, model);

		Association association = (Association) model.getRelationships().get(0);
		association.setTransformationRole(EnumTransformationRole.ASSOCIATION);

		assert association.isBinary();
		EntitySet entitySet1 = association.getFirstSide().getEntitySet();
		EntitySet entitySet2 = association.getSecondSide().getEntitySet();

		assert model.getEntitySets().size() == 6;

		Transformation transformation = Transformator.execute(mapping, new Transformation(EnumTransformation.REBIND_MN_TO_1NN1, new HashSet<>(Arrays.asList(association))));

		Association association1 = (Association) TransformationUtils.getTransformableByRole(transformation.getArguments(), EnumTransformationRole.ASSOCIATION_1);
		Association association2 = (Association) TransformationUtils.getTransformableByRole(transformation.getArguments(), EnumTransformationRole.ASSOCIATION_2);
		EntitySet entitySet = (EntitySet) TransformationUtils.getTransformableByRole(transformation.getArguments(), EnumTransformationRole.ENTITY_SET);

		assertTrue(model.getEntitySets().size() == 7);
		assertFalse(model.contains(association));
		assertTrue(model.contains(entitySet));
		assertTrue(model.contains(association1));
		assertTrue(model.contains(association2));
		assertTrue(association1.isBinary());
		assertTrue(association2.isBinary());
		assertTrue(RelationshipUtils.getRole(association1, entitySet1) != null && Enum.CARDINALITY_ONE.equals(RelationshipUtils.getRole(association1, entitySet1)));
		assertTrue(Enum.CARDINALITY_MANY.equals(RelationshipUtils.getRole(association1, entitySet)));
		assertTrue(Enum.CARDINALITY_MANY.equals(RelationshipUtils.getRole(association2, entitySet)));
		assertTrue(RelationshipUtils.getRole(association2, entitySet2) != null && Enum.CARDINALITY_ONE.equals(RelationshipUtils.getRole(association2, entitySet2)));
	}

	@Test
	public void testGeneralizationTo11Association() {
		ERModel model = TestUtils.getERModels().get(2);
		Mapping mapping = new Mapping(null, model);

		Generalization generalization = (Generalization) model.getRelationships().get(6);
		generalization.setTransformationRole(EnumTransformationRole.GENERALIZATION);

		Transformation transformation = Transformator.execute(mapping, new Transformation(EnumTransformation.GENERALIZATION_TO_11_ASSOCIATION, new HashSet<>(Arrays.asList(generalization))));

		Association association = (Association) TransformationUtils.getTransformableByRole(transformation.getArguments(), EnumTransformationRole.ASSOCIATION);

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
		association.setTransformationRole(EnumTransformationRole.ASSOCIATION);

		assert association.isBinary();
		EntitySet entitySet1 = association.getFirstSide().getEntitySet();
		EntitySet entitySet2 = association.getSecondSide().getEntitySet();

		String entitySet1Name = entitySet1.getName();
		String entitySet2Name = entitySet2.getName();

		assert model.getEntitySets().size() == 6;

		Transformator.execute(mapping, new Transformation(EnumTransformation.CONTRACT_11_ASSOCIATION, new HashSet<>(Arrays.asList(association))));

		assertTrue(model.getEntitySets().size() == 5);
		assertFalse(model.contains(association));
		EntitySet entitySet = ERModelUtils.getEntitySetByName(model, entitySet1Name + Enum.ENTITY_SETS_DELIMITER + entitySet2Name);
		assertNotNull(entitySet);
		for (String attribute : entitySet1.getAttributes()) {
			assertTrue(entitySet.getAttributes().contains(attribute));
		}
		for (String attribute : entitySet2.getAttributes()) {
			assertTrue(entitySet.getAttributes().contains(attribute));
		}
	}

	@Test
	public void testContract11AssociationOnExemplarModel() {
		ERModel exemplarModel = TestUtils.getERModels().get(0);
		ERModel studentModel = TestUtils.getERModels().get(1);
		ERModel studentModelClone = ERModelUtils.getClone(studentModel);
		Mapping mapping = new Mapping(exemplarModel, studentModel);

		Association association = (Association) exemplarModel.getRelationships().get(5);
		association.setTransformationRole(EnumTransformationRole.ASSOCIATION);

		TransformableFlag flag = new TransformableFlag();
		flag.setTransformationRole(EnumTransformationRole.EXEMPLAR_MODEL_FLAG);

		assert association.isBinary();
		EntitySet entitySet1 = association.getFirstSide().getEntitySet();
		EntitySet entitySet2 = association.getSecondSide().getEntitySet();

		String entitySet1Name = entitySet1.getName();
		String entitySet2Name = entitySet2.getName();

		assert exemplarModel.getEntitySets().size() == 6;

		Transformator.execute(mapping, new Transformation(EnumTransformation.CONTRACT_11_ASSOCIATION, new HashSet<>(Arrays.asList(association, flag))));

		assertTrue(exemplarModel.getEntitySets().size() == 5);
		assertFalse(exemplarModel.contains(association));
		EntitySet entitySet = ERModelUtils.getEntitySetByName(exemplarModel, entitySet1Name + Enum.ENTITY_SETS_DELIMITER + entitySet2Name);
		assertNotNull(entitySet);
		for (String attribute : entitySet1.getAttributes()) {
			assertTrue(entitySet.getAttributes().contains(attribute));
		}
		for (String attribute : entitySet2.getAttributes()) {
			assertTrue(entitySet.getAttributes().contains(attribute));
		}
		assertTrue(ERModelUtils.modelsAreEqual(studentModel, studentModelClone));
	}

	@Test
	public void testRebindNaryAssociation() {
		ERModel model = TestUtils.getERModels().get(1);
		Mapping mapping = new Mapping(null, model);

		Association association = (Association) model.getRelationships().get(4);
		association.setTransformationRole(EnumTransformationRole.ASSOCIATION);

		assert !association.isBinary();

		Transformation transformation = Transformator.execute(mapping, new Transformation(EnumTransformation.REBIND_NARY_ASSOCIATION, new HashSet<>(Arrays.asList(association))));

		EntitySet entitySet = (EntitySet) TransformationUtils.getTransformableByRole(transformation.getArguments(), EnumTransformationRole.ENTITY_SET);

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
			assertTrue(Enum.CARDINALITY_MANY.equals(RelationshipUtils.getRole(assoc, entitySet)));
			assertTrue(Enum.CARDINALITY_ONE.equals(RelationshipUtils.getOtherSide(assoc, entitySet).getRole()));
		}
	}
}
