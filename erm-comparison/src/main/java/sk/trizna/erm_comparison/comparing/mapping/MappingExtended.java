package sk.trizna.erm_comparison.comparing.mapping;

import sk.trizna.erm_comparison.entity_relationship_model.ERModel;

public class MappingExtended extends Mapping {
	
	private MappingEvaluation mappingEvaluation;

	public MappingExtended(ERModel exemplarModel, ERModel studentModel, MappingEvaluation mappingEvaluation) {
		super(exemplarModel, studentModel);
		setMappingEvaluation(mappingEvaluation);
		
	}

	private void setMappingEvaluation(MappingEvaluation mappingEvaluation) {
		this.mappingEvaluation = mappingEvaluation;
	}

	public MappingEvaluation getMappingEvaluation() {
		return mappingEvaluation;
	}
}
