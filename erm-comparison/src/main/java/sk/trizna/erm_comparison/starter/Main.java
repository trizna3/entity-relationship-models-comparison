package sk.trizna.erm_comparison.starter;

import java.io.IOException;

import sk.trizna.erm_comparison.common.key_config.TransformationRoleManager;
import sk.trizna.erm_comparison.comparing.ERMComparator;
import sk.trizna.erm_comparison.comparing.ERMComparatorImpl;
import sk.trizna.erm_comparison.comparing.ERModelDiffReport;
import sk.trizna.erm_comparison.entity_relationship_model.ERModel;
import sk.trizna.erm_comparison.parser.Parser;
import sk.trizna.erm_comparison.parser.SyntaxException;
/**
 * @author - Adam Trizna
 */

public class Main {

	public static void main(String[] args) {
		
		try {
			
			TransformationRoleManager foo = TransformationRoleManager.getInstance();
			foo.getResource("EXTRACT_ATTR_TO_OWN_ENTITY_SET");
			ERMComparator comparator = new ERMComparatorImpl();
			
			
			ERModel model1 = Parser.fromString(Parser.fileToString("..//erm-comparison-data//input_model_scripts//exemplar_solutions//internaty_vzor.txt"));
			ERModel model2 = Parser.fromString(Parser.fileToString("..//erm-comparison-data//input_model_scripts//students_solutions//internaty_s1.txt"));
			
			ERModelDiffReport report = comparator.getModelsDiffReport(model1, model2);
			
			System.out.println(report.getReportText());
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SyntaxException e) {
			e.printStackTrace();
		}
	}
}