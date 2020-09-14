package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

import org.junit.Test;

import common.ERModelUtils;
import common.RelationshipUtils;
import common.enums.Enum;
import common.enums.EnumTransformation;
import common.enums.EnumTransformationRole;
import comparing.Mapping;
import entityRelationshipModel.Association;
import entityRelationshipModel.ERModel;
import entityRelationshipModel.EntitySet;
import entityRelationshipModel.Relationship;
import entityRelationshipModel.TransformableAttribute;
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
		
		Transformator.execute(mapping, new Transformation(EnumTransformation.EXTRACT_ATTR_TO_OWN_ENTITY_SET, new HashSet<>(Arrays.asList(entitySet,attribute))));
		
		assertFalse(entitySet.getAttributes().contains(attribute.getAttribute()));
		assertEquals(model.getEntitySets().size(),7);
		EntitySet newEntitySet = ERModelUtils.getEntitySetByName(model, attribute.getAttribute());
		assertNotNull(newEntitySet);
		assertTrue(newEntitySet.getAttributes().contains(Enum.NAME_ATTRIBUTE));
		Relationship relationship = entitySet.getNeighbours().get(newEntitySet).get(0);
		assertNotNull(relationship);
		assertEquals(RelationshipUtils.getRole(relationship, entitySet),Enum.CARDINALITY_MANY);
		assertEquals(RelationshipUtils.getRole(relationship, newEntitySet),Enum.CARDINALITY_ONE);
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
		
		EntitySet newEntitySet = new EntitySet(attribute.getAttribute(),new ArrayList<>(Arrays.asList(Enum.NAME_ATTRIBUTE)));
		newEntitySet.setTransformationRole(EnumTransformationRole.DEST_ENTITY_SET);
		model.addEntitySet(newEntitySet);
		
		assert entitySet.getAttributes().contains(attribute.getAttribute());
		
		Transformator.execute(mapping, new Transformation(EnumTransformation.EXTRACT_ATTR_TO_OWN_ENTITY_SET, new HashSet<>(Arrays.asList(entitySet,attribute,newEntitySet))));
		
		assertFalse(entitySet.getAttributes().contains(attribute.getAttribute()));
		assertEquals(model.getEntitySets().size(),7);
		Relationship relationship = entitySet.getNeighbours().get(newEntitySet).get(0);
		assertNotNull(relationship);
		assertEquals(RelationshipUtils.getRole(relationship, entitySet),Enum.CARDINALITY_MANY);
		assertEquals(RelationshipUtils.getRole(relationship, newEntitySet),Enum.CARDINALITY_ONE);
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
		
		Transformator.execute(mapping, new Transformation(EnumTransformation.MOVE_ATTR_TO_INCIDENT_ASSOCIATION, new HashSet<>(Arrays.asList(entitySet,attribute,association))));
		
		assertFalse(entitySet.getAttributes().contains(attribute.getAttribute()));
		assertTrue(association.getAttributes().contains(attribute.getAttribute()));
	}
	
	@Test
	public void testRebindMNTo1NN1() {
//		REBIND_MN_TO_1NN1 - ASSOCIATION - ASSOCIATION_1,ASSOCIATION_2,ENTITY_SET
		fail("Not yet implemented");
	}
	
	@Test
	public void testGeneralizationTo11Association() {
//		GENERALIZATION_TO_11_ASSOCIATION - GENERALIZATION - ASSOCIATION, GENERALIZATION
		fail("Not yet implemented");
	}
	
	@Test
	public void testContract11Association() {
//		CONTRACT_11_ASSOCIATION - ASSOCIATION - ENTITY_SET, ASSOCIATION
		fail("Not yet implemented");
	}
	
	@Test
	public void testRebindNaryAssociation() {
//		REBIND_NARY_ASSOCIATION - ASSOCIATION - ENTITY_SET, ASSOCIATION
		fail("Not yet implemented");
	}
}
