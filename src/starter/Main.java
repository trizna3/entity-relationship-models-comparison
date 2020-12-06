package starter;

import comparing.Mapping;
import entityRelationshipModel.ERModel;
import languageProcessing.StanfordLemmatizer;
import mappingSearch.mappingEvaluator.Evaluator;
import mappingSearch.mappingEvaluator.IEvaluator;
import tests.TestUtils;
/**
 * @author - Adam Trizna
 */

public class Main {

	public static void main(String[] args) {
		
		String sentence = "Today I went to the store and bought a cammel.";
		
		for (String word : sentence.split(" ")) {
			System.out.println(StanfordLemmatizer.getInstance().lemmatizeWord(word));
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