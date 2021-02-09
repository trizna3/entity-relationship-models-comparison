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
		switch (getInfoLevel()) {
			case FULL:
				return getDetailedReport();
			case RESTRICTED:
				return getRestrictedReport();
		}
		return null;
	}
	
	private String getReportHeader() {
		StringBuilder header = new StringBuilder();
		
		header.append("**************************************************\n");
		header.append("** Entity-Relationship-Models comparison report **\n");
		header.append("**************************************************\n");
		
		return header.toString();
	}
	
	private String getRestrictedReport() {
		StringBuilder sb = new StringBuilder();

		sb.append(getReportHeader());
		sb.append("Missing entity sets count = " + getMissingEntitySetsNotes().size() + "\n");
		sb.append("Unnecessary entity sets count = " + getAdditionalEntitySetsNotes().size() + "\n");
		sb.append("Entity sets renamed count = " + getEntitySetRenameNotes().size() + "\n");
		sb.append("Missing relationships count = " + getMissingRelationshipNotes().size() + "\n");
		sb.append("Unnecessary relationships count = " + getAdditionalRelationshipNotes().size() + "\n");
		sb.append("Missing attributes count = " + getMissingAttributesNotes().size() + "\n");
		sb.append("Unnecessary attributes count = " + getAdditionalAttributesNotes().size() + "\n");
		sb.append("More complex model modifications count = " + getTransformationNotes().size() + "\n");
		
		return sb.toString();
	}
	
	private String getDetailedReport() {
		StringBuilder sb = new StringBuilder();
		
		sb.append(getReportHeader());
		if (!getMissingEntitySetsNotes().isEmpty()) {
			sb.append("* missing entity sets report notes:\n");
			getMissingEntitySetsNotes().forEach(note -> {
				sb.append("- " + note + "\n");
			});
		}
		if (!getAdditionalEntitySetsNotes().isEmpty()) {
			sb.append("* unnecessary entity sets report notes:\n");
			getAdditionalEntitySetsNotes().forEach(note -> {
				sb.append("- " + note + "\n");
			});
		}
		if (!getEntitySetRenameNotes().isEmpty()) {
			sb.append("* entity sets renaming report notes:\n");
			getEntitySetRenameNotes().forEach(note -> {
				sb.append("- " + note + "\n");
			});
		}
		if (!getMissingRelationshipNotes().isEmpty()) {
			sb.append("* missing relationships report notes:\n");
			getMissingRelationshipNotes().forEach(note -> {
				sb.append("- " + note + "\n");
			});
		}
		if (!getAdditionalRelationshipNotes().isEmpty()) {
			sb.append("* unnecessary relationships report notes:\n");
			getAdditionalRelationshipNotes().forEach(note -> {
				sb.append("- " + note + "\n");
			});
		}
		if (!getMissingAttributesNotes().isEmpty()) {
			sb.append("* missing attributes report notes:\n");
			getMissingAttributesNotes().forEach(note -> {
				sb.append("- " + note + "\n");
			});
		}
		if (!getAdditionalAttributesNotes().isEmpty()) {
			sb.append("* unnecessary attributes report notes:\n");
			getAdditionalAttributesNotes().forEach(note -> {
				sb.append("- " + note + "\n");
			});
		}
		if (!getTransformationNotes().isEmpty()) {
			sb.append("* complex model modification report notes:\n");
			getTransformationNotes().forEach(note -> {
				sb.append("- " + note + "\n");
			});
		}
		
		return sb.toString();
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

	public List<String> getEntitySetRenameNotes() {
		if (entitySetRenameNotes == null) {
			entitySetRenameNotes = new ArrayList<String>();
		}
		return entitySetRenameNotes;
	}
}
