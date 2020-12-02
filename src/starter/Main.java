package starter;

import comparing.Mapping;
import entityRelationshipModel.ERModel;
import mappingSearch.mappingEvaluator.Evaluator;
import mappingSearch.mappingEvaluator.IEvaluator;
import tests.TestUtils;
/**
 * @author - Adam Trizna
 */

public class Main {

	public static void main(String[] args) {
		
		debugTest();
	}
	
	private static void debugTest() {
		System.out.println("Test - mapping finder: Internaty 2");
		ERModel exemplarModel = TestUtils.makeERModel_Internaty_Vzor();
		ERModel studentModel = TestUtils.makeERModel_Internaty_S2();
		IEvaluator evaluator = new Evaluator();

		Mapping mappingJeho = new Mapping(exemplarModel, studentModel);		
		mappingJeho.map(exemplarModel.getEntitySets().get(0), studentModel.getEntitySets().get(0));
		mappingJeho.map(exemplarModel.getEntitySets().get(1), studentModel.getEntitySets().get(1));
		mappingJeho.map(exemplarModel.getEntitySets().get(2), studentModel.getEntitySets().get(2));
		mappingJeho.map(exemplarModel.getEntitySets().get(3), studentModel.getEntitySets().get(3));
		mappingJeho.map(exemplarModel.getEntitySets().get(5), studentModel.getEntitySets().get(4));
		evaluator.evaluate(mappingJeho);	// 63.5
		mappingJeho.unmapAll();
		
		Mapping mappingMoj = new Mapping(exemplarModel, studentModel);
		mappingMoj.map(exemplarModel.getEntitySets().get(0), studentModel.getEntitySets().get(0));
		mappingMoj.map(exemplarModel.getEntitySets().get(1), studentModel.getEntitySets().get(1));
		mappingMoj.map(exemplarModel.getEntitySets().get(2), studentModel.getEntitySets().get(2));
		mappingMoj.map(exemplarModel.getEntitySets().get(3), studentModel.getEntitySets().get(3));
		mappingMoj.map(exemplarModel.getEntitySets().get(4), studentModel.getEntitySets().get(4));		
		evaluator.evaluate(mappingMoj);		// 62
		mappingMoj.unmapAll();
		
		Mapping mappingJehoOpt = new Mapping(exemplarModel, studentModel);		
		mappingJehoOpt.map(exemplarModel.getEntitySets().get(0), studentModel.getEntitySets().get(0));
		mappingJehoOpt.map(exemplarModel.getEntitySets().get(1), studentModel.getEntitySets().get(1));
		mappingJehoOpt.map(exemplarModel.getEntitySets().get(2), studentModel.getEntitySets().get(2));
		mappingJehoOpt.map(exemplarModel.getEntitySets().get(3), studentModel.getEntitySets().get(3));
		mappingJehoOpt.map(exemplarModel.getEntitySets().get(4), studentModel.getEntitySets().get(5));
		mappingJehoOpt.map(exemplarModel.getEntitySets().get(5), studentModel.getEntitySets().get(4));
		evaluator.evaluate(mappingJehoOpt);
		mappingJeho.unmapAll();
	}
}