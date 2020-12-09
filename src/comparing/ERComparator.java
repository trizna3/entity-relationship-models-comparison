package comparing;

import java.util.Map;

import common.Utils;
import entityRelationshipModel.ERModel;
import entityRelationshipModel.EntitySet;
import mappingSearch.mappingFinder.MappingFinder;

/**
 * @author - Adam Trizna
 */

/**
 * Entity relationship models comparator.
 */
public class ERComparator implements IComparator {

	private MappingFinder mappingFinder;
	private ERModelComparisonTranslator translator;
	private ERModelComparisonReport comparisonReport;
	
	/**
     * {@inheritDoc}
     */
    @Override
    public ERModelComparisonReport getCompareReport(ERModel exemplarModel, ERModel studentModel) {
    	
    	Utils.validateNotNull(exemplarModel);
    	Utils.validateNotNull(studentModel);
    	
    	if (comparisonReport == null) {
    		computeCompareReport(exemplarModel, studentModel);
    	}
    	
    	return comparisonReport;
    }
    
    @Override
	public void computeCompareReport(ERModel exemplarModel, ERModel studentModel) {
    	
    	Utils.validateNotNull(exemplarModel);
    	Utils.validateNotNull(studentModel);
    	
    	Map<EntitySet, EntitySet> foundMap = getMappingFinder().getBestMapping(exemplarModel, studentModel);
    	comparisonReport = getTranslator().createReport(exemplarModel, studentModel, foundMap);
	}
    
    private MappingFinder getMappingFinder() {
		if (mappingFinder == null) {
			mappingFinder = new MappingFinder();
		}
		return mappingFinder;
	}

	public ERModelComparisonTranslator getTranslator() {
		if (translator == null) {
			translator = new ERModelComparisonTranslator();
		}
		return translator;
	}
}
