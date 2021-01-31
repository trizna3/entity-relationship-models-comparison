package sk.trizna.erm_comparison.comparing;

import sk.trizna.erm_comparison.common.Utils;
import sk.trizna.erm_comparison.entity_relationship_model.ERModel;
import sk.trizna.erm_comparison.mappingSearch.mapping_finder.MappingFinder;

/**
 * @author - Adam Trizna
 */

/**
 * Entity relationship models comparator.
 */
public class ERComparator implements IComparator {

	private MappingFinder mappingFinder;
	private ERModelComparisonTranslator translator;
	
	/**
     * {@inheritDoc}
     */
    @Override
    public ERModelDiff getCompareDiff(ERModel exemplarModel, ERModel studentModel) {
    	
    	Utils.validateNotNull(exemplarModel);
    	Utils.validateNotNull(studentModel);
    	
		return computeCompareDiff(exemplarModel, studentModel);
    }
    
	private ERModelDiff computeCompareDiff(ERModel exemplarModel, ERModel studentModel) {
    	
    	Utils.validateNotNull(exemplarModel);
    	Utils.validateNotNull(studentModel);
    	
    	Mapping foundMapping = getMappingFinder().getBestMapping(exemplarModel, studentModel);
    	return getTranslator().computeDiff(exemplarModel, studentModel, foundMapping);
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
