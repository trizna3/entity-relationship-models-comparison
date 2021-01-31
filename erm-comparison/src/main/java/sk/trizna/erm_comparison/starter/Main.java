package sk.trizna.erm_comparison.starter;

import java.io.IOException;

import sk.trizna.erm_comparison.entity_relationship_model.ERModel;
import sk.trizna.erm_comparison.mappingSearch.mapping_finder.MappingFinder;
import sk.trizna.erm_comparison.parser.Parser;
import sk.trizna.erm_comparison.parser.SyntaxException;
/**
 * @author - Adam Trizna
 */

public class Main {

	public static void main(String[] args) {
		
		try {
			
			ERModel model1 = Parser.fromString(Parser.fileToString("..//erm-comparison-data//input_model_scripts//exemplar_solutions//internaty_vzor.txt"));
			ERModel model2 = Parser.fromString(Parser.fileToString("..//erm-comparison-data//input_model_scripts//students_solutions//internaty_s1.txt"));
			
			new MappingFinder().getBestMapping(model1, model2);
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SyntaxException e) {
			e.printStackTrace();
		}
	}
}