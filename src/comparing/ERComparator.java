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
	private ERModelDiff comparisonDiff;
	
	/**
     * {@inheritDoc}
     */
    @Override
    public ERModelDiff getCompareDiff(ERModel exemplarModel, ERModel studentModel) {
    	
    	Utils.validateNotNull(exemplarModel);
    	Utils.validateNotNull(studentModel);
    	
    	if (comparisonDiff == null) {
    		computeCompareDiff(exemplarModel, studentModel);
    	}
    	
    	return comparisonDiff;
    }
    
    @Override
	public void computeCompareDiff(ERModel exemplarModel, ERModel studentModel) {
    	
    	Utils.validateNotNull(exemplarModel);
    	Utils.validateNotNull(studentModel);
    	
    	Map<EntitySet, EntitySet> foundMap = getMappingFinder().getBestMapping(exemplarModel, studentModel);
    	comparisonDiff = getTranslator().computeDiff(exemplarModel, studentModel, foundMap);
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
