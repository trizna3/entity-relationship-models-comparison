package tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.Test;

import common.enums.EnumTransformation;
import common.enums.EnumTransformationRole;
import entityRelationshipModel.Attribute;
import entityRelationshipModel.EntitySet;
import transformations.Transformation;

public class TransformationTest {

	@Test
	public void testTransformationNotEqualsByCode() {
		Transformation t1 = new Transformation(EnumTransformation.BIND_TO_NARY_ASSOCIATION);
		Transformation t2 = new Transformation(EnumTransformation._11_ASSOCIATION_TO_GENERALIZATION);
		
		assertFalse(t1.equals(t2));
		assertFalse(t2.equals(t1));
	}
	
	@Test
	public void testTransformationEqualsByCode() {
		Transformation t1 = new Transformation(EnumTransformation.BIND_TO_NARY_ASSOCIATION);
		Transformation t2 = new Transformation(EnumTransformation.BIND_TO_NARY_ASSOCIATION);
		
		assertTrue(t1.equals(t2));
		assertTrue(t2.equals(t1));
	}
	
	@Test
	public void testTransformationNotEqualsByArguments_ArgsCountDiffer() {
		Transformation t1 = new Transformation(EnumTransformation.BIND_TO_NARY_ASSOCIATION);
		Transformation t2 = new Transformation(EnumTransformation.BIND_TO_NARY_ASSOCIATION);
		
		EntitySet entitySet = new EntitySet("Test", Arrays.asList("a","b"));
		t1.addArgument(entitySet, EnumTransformationRole.ENTITY_SET);
		
		assertFalse(t1.equals(t2));
		assertFalse(t2.equals(t1));
	}
	
	@Test
	public void testTransformationNotEqualsByArguments_ArgsRolesDiffer() {
		Transformation t1 = new Transformation(EnumTransformation.BIND_TO_NARY_ASSOCIATION);
		Transformation t2 = new Transformation(EnumTransformation.BIND_TO_NARY_ASSOCIATION);
		
		EntitySet entitySet = new EntitySet("Test", Arrays.asList("a","b"));
		t1.addArgument(entitySet, EnumTransformationRole.ENTITY_SET);
		t2.addArgument(entitySet, EnumTransformationRole.ENTITY_SET_TARGET);
		
		assertFalse(t1.equals(t2));
		assertFalse(t2.equals(t1));
	}
	
	@Test
	public void testTransformationNotEqualsByArguments_ArgsDeepDiff() {
		Transformation t1 = new Transformation(EnumTransformation.BIND_TO_NARY_ASSOCIATION);
		Transformation t2 = new Transformation(EnumTransformation.BIND_TO_NARY_ASSOCIATION);
		
		EntitySet entitySet = new EntitySet("Test", Arrays.asList("a","b"));
		t1.addArgument(entitySet, EnumTransformationRole.ENTITY_SET);
		
		EntitySet entitySetCopy = new EntitySet(entitySet);
		t2.addArgument(entitySetCopy, EnumTransformationRole.ENTITY_SET);
		
		entitySetCopy.getAttributes().add(new Attribute("attribute"));
		
		assertFalse(t1.equals(t2));
		assertFalse(t2.equals(t1));
	}
	
	@Test
	public void testTransformationEqualsByArguments() {
		Transformation t1 = new Transformation(EnumTransformation.BIND_TO_NARY_ASSOCIATION);
		Transformation t2 = new Transformation(EnumTransformation.BIND_TO_NARY_ASSOCIATION);
		
		EntitySet entitySet = new EntitySet("Test", Arrays.asList("a","b"));
		t1.addArgument(entitySet, EnumTransformationRole.ENTITY_SET);
		t2.addArgument(entitySet, EnumTransformationRole.ENTITY_SET);
		
		assertTrue(t1.equals(t2));
		assertTrue(t2.equals(t1));
	}
}
