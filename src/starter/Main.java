package starter;

import common.ERModelUtils;
import common.enums.Enum;
import comparing.Mapping;
import entityRelationshipModel.Association;
import entityRelationshipModel.AssociationSide;
import entityRelationshipModel.ERModel;
import entityRelationshipModel.EntitySet;
import mappingSearch.mappingEvaluator.MappingEvaluator;
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
		model1.addRelationship(new Association(new AssociationSide[] { new AssociationSide(esEmployees, Enum.CARDINALITY_ONE), new AssociationSide(esEmpJobs, Enum.CARDINALITY_MANY) }, null));
		model1.addRelationship(new Association(new AssociationSide[] { new AssociationSide(esJobs, Enum.CARDINALITY_ONE), new AssociationSide(esEmpJobs, Enum.CARDINALITY_MANY) }, null));

		EntitySet esPeople = new EntitySet("People", null);
		EntitySet esPositions = new EntitySet("Positions", null);

		ERModel model2 = new ERModel();
		model2.addEntitySet(esPeople);
		model2.addEntitySet(esPositions);
		model2.addRelationship(new Association(new AssociationSide[] { new AssociationSide(esPeople, Enum.CARDINALITY_ONE), new AssociationSide(esPositions, Enum.CARDINALITY_MANY) }, null));

		Mapping mapping = new Mapping(model1, model2);
		esEmployees.setMappedTo(esPeople);
		esPeople.setMappedTo(esEmployees);
		esJobs.setMappedTo(esPositions);
		esPositions.setMappedTo(esJobs);

		new MappingEvaluator().expandTransformationList(mapping);

//		System.out.println(model2);

		for (Transformation transformation : mapping.getTransformations()) {
			Transformator.execute(mapping, transformation);
		}
		System.out.println(model1);
		System.out.println("---");
		System.out.println(model2);

		System.out.println(ERModelUtils.modelsAreEqual(model1, model2));

//		List<EntitySet> entitySets = new ArrayList<>(Arrays.asList(es1, es2));
//
//		EntitySet es = CollectionUtils.getFirst(entitySets, EntitySet.class, entitySet -> "es1".equals(entitySet.getName()));
//
//		System.out.println(es.getName());
	}
}