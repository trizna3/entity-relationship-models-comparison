package starter;

import java.io.IOException;

import comparing.Mapping;
import entityRelationshipModel.ERModel;
import mappingSearch.mappingEvaluator.Evaluator;
import mappingSearch.mappingEvaluator.IEvaluator;
import mappingSearch.mappingFinder.MappingFinder;
import parser.Parser;
import parser.SyntaxException;
import tests.TestUtils;
/**
 * @author - Adam Trizna
 */

public class Main {

	public static void main(String[] args) {
		
		try {
			
			ERModel model1 = Parser.fromString(Parser.fileToString("input_model_scripts//exemplar_solutions//internaty_vzor.txt"));
			ERModel model2 = Parser.fromString(Parser.fileToString("input_model_scripts//students_solutions//internaty_s1.txt"));
			
			new MappingFinder().getBestMapping(model1, model2);
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SyntaxException e) {
			e.printStackTrace();
		}
		
		debugTest();
	}
	
	private static void debugTest() {
		
		ERModel exemplarModel = TestUtils.makeERModel_Poistovna_Vzor();
		ERModel studentModel = TestUtils.makeERModel_Poistovna_S1();
		IEvaluator evaluator = new Evaluator();

		Mapping mappingJeho = new Mapping(exemplarModel, studentModel);
		mappingJeho.map(exemplarModel.getEntitySets().get(1), studentModel.getEntitySets().get(1));
		mappingJeho.map(exemplarModel.getEntitySets().get(2), studentModel.getEntitySets().get(2));
		mappingJeho.map(exemplarModel.getEntitySets().get(3), studentModel.getEntitySets().get(3));
		mappingJeho.map(exemplarModel.getEntitySets().get(0), studentModel.getEntitySets().get(6));
		evaluator.evaluate(mappingJeho);
		mappingJeho.unmapAll();
		
		Mapping mappingMoj = new Mapping(exemplarModel, studentModel);		
		mappingJeho.map(exemplarModel.getEntitySets().get(1), studentModel.getEntitySets().get(1));
		mappingJeho.map(exemplarModel.getEntitySets().get(2), studentModel.getEntitySets().get(2));
		mappingJeho.map(exemplarModel.getEntitySets().get(3), studentModel.getEntitySets().get(3));
		mappingJeho.map(exemplarModel.getEntitySets().get(0), studentModel.getEntitySets().get(6));
		mappingJeho.map(exemplarModel.getEntitySets().get(5), studentModel.getEntitySets().get(5));
		evaluator.evaluate(mappingMoj);
		mappingMoj.unmapAll();
	}
	
	
}