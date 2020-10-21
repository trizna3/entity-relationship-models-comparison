package starter;

import java.util.Arrays;

import common.ERModelUtils;
import common.enums.EnumRelationshipSideRole;
import comparing.Mapping;
import entityRelationshipModel.Association;
import entityRelationshipModel.AssociationSide;
import entityRelationshipModel.ERModel;
import entityRelationshipModel.EntitySet;
import transformations.Transformation;
import transformations.Transformator;

/**
 * @author - Adam Trizna
 */

public class Main {

	public static void main(String[] args) {
		
		EntitySet esEmployees = new EntitySet("Employees", null);
		EntitySet esJobs = new EntitySet("Jobs", null);
		EntitySet esEmpJobs = new EntitySet("EmpJobs", null);

		ERModel model1 = new ERModel();
		model1.addEntitySet(esEmployees);
		model1.addEntitySet(esJobs);
		model1.addEntitySet(esEmpJobs);
		model1.addRelationship(new Association(Arrays.asList(new AssociationSide(esEmployees, EnumRelationshipSideRole.CARDINALITY_ONE), new AssociationSide(esEmpJobs, EnumRelationshipSideRole.CARDINALITY_MANY)), null));
		model1.addRelationship(new Association(Arrays.asList(new AssociationSide(esJobs, EnumRelationshipSideRole.CARDINALITY_ONE), new AssociationSide(esEmpJobs, EnumRelationshipSideRole.CARDINALITY_MANY)), null));

		EntitySet esPeople = new EntitySet("People", null);
		EntitySet esPositions = new EntitySet("Positions", null);

		ERModel model2 = new ERModel();
		model2.addEntitySet(esPeople);
		model2.addEntitySet(esPositions);
		model2.addRelationship(new Association(Arrays.asList(new AssociationSide(esPeople, EnumRelationshipSideRole.CARDINALITY_ONE), new AssociationSide(esPositions, EnumRelationshipSideRole.CARDINALITY_MANY)), null));

		Mapping mapping = new Mapping(model1, model2);
		esEmployees.setMappedTo(esPeople);
		esPeople.setMappedTo(esEmployees);
		esJobs.setMappedTo(esPositions);
		esPositions.setMappedTo(esJobs);

		for (Transformation transformation : mapping.getTransformations()) {
			Transformator.execute(mapping, transformation);
		}
		System.out.println(model1);
		System.out.println("---");
		System.out.println(model2);

		System.out.println(ERModelUtils.modelsAreEqual(model1, model2));

	}
}