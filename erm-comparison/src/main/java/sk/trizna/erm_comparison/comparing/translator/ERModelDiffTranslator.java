package sk.trizna.erm_comparison.comparing.translator;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import sk.trizna.erm_comparison.common.utils.Utils;
import sk.trizna.erm_comparison.comparing.ERModelDiff;
import sk.trizna.erm_comparison.comparing.ERModelDiffReport;
import sk.trizna.erm_comparison.comparing.mapping.MappingExtended;
import sk.trizna.erm_comparison.comparing.mapping.RelationshipMappingComputer;
import sk.trizna.erm_comparison.entity_relationship_model.ERModel;
import sk.trizna.erm_comparison.entity_relationship_model.EntitySet;
import sk.trizna.erm_comparison.entity_relationship_model.Relationship;
import sk.trizna.erm_comparison.transformations.Transformation;

public class ERModelDiffTranslator {
	
	private EntitySetTranslator entitySetTranslator = new EntitySetTranslator();
	private RelationshipTranslator relationshipTranslator = new RelationshipTranslator();
	private TransformationTranslator transformaionTranslator = new TransformationTranslator();
	private AttributedTranslator attributedTranslator = new AttributedTranslator();
	private RelationshipMappingComputer relationshipMappingComputer = new RelationshipMappingComputer(); 

	/**
	 * Translates {@link MappingExtended} to {@link ERModelDiff}
	 * @param mappingExtended
	 * @return
	 */
	public ERModelDiff computeDiff(MappingExtended mappingExtended) {
		Utils.validateNotNull(mappingExtended);
		
		ERModel exemplarModel = mappingExtended.getExemplarModel();
		ERModel studentModel =  mappingExtended.getStudentModel();
		Map<EntitySet,EntitySet> entitySetMap = mappingExtended.getMappingEvaluation().getEntitySetMap();
		Map<Relationship,Relationship> relationshipMap = getRelationshipMappingComputer().computeRelationshipMapping(mappingExtended);
		List<EntitySet> notMappedExemplarEntitySets = exemplarModel.getEntitySets().stream().filter(es -> es.getMappedTo() == null).collect(Collectors.toList());
		List<EntitySet> notMappedStudentEntitySets = studentModel.getEntitySets().stream().filter(es -> es.getMappedTo() == null).collect(Collectors.toList());;
		List<Transformation> transformationsMade = mappingExtended.getTransformations();
		
		return new ERModelDiff(
				exemplarModel, 
				studentModel, 
				entitySetMap,
				relationshipMap,
				notMappedExemplarEntitySets, 
				notMappedStudentEntitySets, 
				transformationsMade);
	}
	
	/**
	 * Translates {@link ERModelDiff} to {@link ERModelDiffReport}
	 * @param diff
	 * @return
	 */
	public ERModelDiffReport createDiffReport(ERModelDiff diff) {
		Utils.validateNotNull(diff);
		
		ERModelDiffReport report = new ERModelDiffReport();
		
		getEntitySetTranslator().translate(report, diff);
		getRelationshipTranslator().translate(report, diff);
		getTransformaionTranslator().translate(report, diff);
		getAttributedTranslator().translate(report, diff);
		
		return report;
	}

	private EntitySetTranslator getEntitySetTranslator() {
		return entitySetTranslator;
	}

	private RelationshipTranslator getRelationshipTranslator() {
		return relationshipTranslator;
	}

	private TransformationTranslator getTransformaionTranslator() {
		return transformaionTranslator;
	}

	private RelationshipMappingComputer getRelationshipMappingComputer() {
		return relationshipMappingComputer;
	}

	private AttributedTranslator getAttributedTranslator() {
		return attributedTranslator;
	}
}
