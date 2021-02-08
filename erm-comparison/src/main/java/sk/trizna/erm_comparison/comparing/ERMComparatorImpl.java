package sk.trizna.erm_comparison.comparing;

import java.util.HashMap;
import java.util.Map;

import sk.trizna.erm_comparison.common.Utils;
import sk.trizna.erm_comparison.comparing.mapping.MappingExtended;
import sk.trizna.erm_comparison.comparing.translator.ERModelDiffTranslator;
import sk.trizna.erm_comparison.entity_relationship_model.ERModel;
import sk.trizna.erm_comparison.mappingSearch.mapping_finder.MappingFinder;

/**
 * @author - Adam Trizna
 */

/**
 * Entity relationship models comparator.
 */
public class ERMComparatorImpl implements ERMComparator {

	private MappingFinder mappingFinder;
	private ERModelDiffTranslator translator;
	
	private Map<ERMComparisonInput,ERModelDiff> diffCache;
	
	class ERMComparisonInput {
		private ERModel exemplarModel;
		private ERModel studentModel;
		
		public ERMComparisonInput(ERModel exemplarModel, ERModel studentModel) { 
			this.exemplarModel = exemplarModel;
			this.studentModel = studentModel;
		}

		public ERModel getExemplarModel() {
			return exemplarModel;
		}

		public ERModel getStudentModel() {
			return studentModel;
		}

		@Override
		public boolean equals(Object obj) {
			if (!(obj instanceof ERMComparisonInput)) {
				return false;
			}
			ERMComparisonInput otherInput = (ERMComparisonInput) obj;
			if (this.getExemplarModel() == null || 
				this.getStudentModel() == null ||
				otherInput.getExemplarModel() == null ||
				otherInput.getStudentModel() == null) {
				return false;
			}
			return this.getExemplarModel().equals(otherInput.getExemplarModel()) && this.getStudentModel().equals(otherInput.getStudentModel());
		}

		@Override
		public int hashCode() {
			int hash = 0;
			
			hash += getExemplarModel() != null ? 6701 * getExemplarModel().hashCode() : 0;
			hash += getStudentModel() != null ? 6703 * getStudentModel().hashCode() : 0;
			
			return hash;
		}
	}
	
	/**
     * {@inheritDoc}
     */
    @Override
    public ERModelDiff getModelsDiff(ERModel exemplarModel, ERModel studentModel) {
    	Utils.validateNotNull(exemplarModel);
    	Utils.validateNotNull(studentModel);
    	
    	ERModelDiff diff = getDiffFromCache(exemplarModel, studentModel);
    	if (diff == null) {
			diff = computeModelsDiff(exemplarModel, studentModel);
			saveDiffToCache(exemplarModel, studentModel, diff);
    	}
		return diff;
    }
    
    @Override
	public ERModelDiffReport getModelsDiffReport(ERModel exemplarModel, ERModel studentModel) {
    	Utils.validateNotNull(exemplarModel);
    	Utils.validateNotNull(studentModel);
    	
		return getTranslator().createDiffReport(getModelsDiff(exemplarModel, studentModel));
	}
    
	private ERModelDiff computeModelsDiff(ERModel exemplarModel, ERModel studentModel) {
    	MappingExtended foundMapping = getMappingFinder().findBestMapping(exemplarModel, studentModel);
    	return getTranslator().computeDiff(foundMapping);
	}
    
    private MappingFinder getMappingFinder() {
		if (mappingFinder == null) {
			mappingFinder = new MappingFinder();
		}
		return mappingFinder;
	}

	public ERModelDiffTranslator getTranslator() {
		if (translator == null) {
			translator = new ERModelDiffTranslator();
		}
		return translator;
	}

	private Map<ERMComparisonInput,ERModelDiff> getDiffCache() {
		if (diffCache == null) {
			diffCache = new HashMap<ERMComparatorImpl.ERMComparisonInput, ERModelDiff>();
		}
		return diffCache;
	}
	
	private void saveDiffToCache(ERModel exemplarModel, ERModel studentModel, ERModelDiff diff) {
		getDiffCache().put(new ERMComparisonInput(exemplarModel, studentModel), diff);
	}
	
	private ERModelDiff getDiffFromCache(ERModel exemplarModel, ERModel studentModel) {
		return getDiffCache().get(new ERMComparisonInput(exemplarModel, studentModel));
	}
}
