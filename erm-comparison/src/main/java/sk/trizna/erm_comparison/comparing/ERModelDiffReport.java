package sk.trizna.erm_comparison.comparing;

import java.util.ArrayList;
import java.util.List;

/**
 * {@link ERModelDiff} object, interpreted to student-readable report.
 * 
 * @author Adam Trizna
 *
 */
public class ERModelDiffReport {
	
	public enum ERModelDiffReportInfoLevel {
		FULL,
		RESTRICTED
	}
	
	/** EntitySets from exemplar ERModel, for which no counterpart was found **/
	private List<String> missingEntitySetsNotes;
	
	/** EntitySets from student's ERModel, for which no counterpart was found **/
	private List<String> additionalEntitySetsNotes;
	
	/** Notes, describing need to rename studen't entitySets **/
	private List<String> entitySetRenameNotes;
	
	/** Map.key = student's (mapped) entitySet name; Map.value = attributes, which were found in entitySet's exemplar counterpart but not found in student's entitySet **/
	private List<String> missingAttributesNotes;
	
	/** Map.key = student's (mapped) entitySet name; Map.value = attributes, which were found in student's entitySet but not in it's exemplar counterpart **/
	private List<String> additionalAttributesNotes;
	
	/** Notes, describing missing relationships, eg. such exemplar relationships, for which incident entitySets counterparts are not in any relationship **/
	private List<String> missingRelationshipNotes;
	
	/** Notes, describing additional relationships, eg. such student relationships, for which incident entitySets counterparts are not in any relationship **/
	private List<String> additionalRelationshipNotes;
	
	/** Notes, generated from non-equivalent transformations, made to models **/
	private List<String> transformationNotes;

	private ERModelDiffReportInfoLevel infoLevel = ERModelDiffReportInfoLevel.FULL;
	
	public ERModelDiffReport() {
		this.missingEntitySetsNotes = new ArrayList<String>();
		this.additionalEntitySetsNotes = new ArrayList<String>();
		this.missingAttributesNotes = new ArrayList<String>();
		this.additionalAttributesNotes = new ArrayList<String>();
		this.transformationNotes = new ArrayList<String>();
		this.missingRelationshipNotes = new ArrayList<String>();
		this.additionalRelationshipNotes = new ArrayList<String>();
	}
	
	/**
	 * Returns ERModelDiff report in text summarization.
	 * 
	 * Returned text is dependent of {@link #getInfoLevel()} 
	 * 
	 * @return
	 */
	public String getReportText() {
		throw new UnsupportedOperationException("Not implemented yet");
	}

	public List<String> getMissingEntitySetsNotes() {
		if (missingEntitySetsNotes == null) {
			missingEntitySetsNotes = new ArrayList<String>(0);
		}
		return missingEntitySetsNotes;
	}

	public List<String> getAdditionalEntitySetsNotes() {
		if (additionalEntitySetsNotes == null) {
			additionalEntitySetsNotes = new ArrayList<String>(0);
		}
		return additionalEntitySetsNotes;
	}

	public List<String> getMissingAttributesNotes() {
		if (missingAttributesNotes == null) {
			missingAttributesNotes = new ArrayList<String>();
		}
		return missingAttributesNotes;
	}

	public List<String> getAdditionalAttributesNotes() {
		if (additionalAttributesNotes == null) {
			additionalAttributesNotes = new ArrayList<String>();
		}
		return additionalAttributesNotes;
	}
	
	public List<String> getTransformationNotes() {
		if (transformationNotes == null) {
			transformationNotes = new ArrayList<String>(0);
		}
		return transformationNotes;
	}

	public ERModelDiffReportInfoLevel getInfoLevel() {
		return infoLevel;
	}

	public void setInfoLevel(ERModelDiffReportInfoLevel infoLevel) {
		this.infoLevel = infoLevel;
	}

	public List<String> getMissingRelationshipNotes() {
		if (missingRelationshipNotes == null) {
			missingRelationshipNotes = new ArrayList<String>();
		}
		return missingRelationshipNotes;
	}

	public List<String> getAdditionalRelationshipNotes() {
		if (additionalRelationshipNotes == null) {
			additionalRelationshipNotes = new ArrayList<String>();
		}
		return additionalRelationshipNotes;
	}

	public List<String> getEntitySetNamesNotes() {
		if (entitySetRenameNotes == null) {
			entitySetRenameNotes = new ArrayList<String>();
		}
		return entitySetRenameNotes;
	}
}
