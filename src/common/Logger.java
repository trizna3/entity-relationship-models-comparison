package common;

import java.io.PrintWriter;

import common.enums.EnumTransformation;
import common.enums.EnumTransformationRole;
import comparing.Mapping;
import entityRelationshipModel.Association;
import entityRelationshipModel.AssociationSide;
import entityRelationshipModel.Attribute;
import entityRelationshipModel.EntitySet;
import entityRelationshipModel.Generalization;
import tests.ValidationEvaluator;
import transformations.Transformation;

public class Logger {

	private String filepath;
	private PrintWriter writer;
	
	// --
	
	public Logger(String filepath) {
		this.filepath = filepath;
	}
	
	public void close() {
		getWriter().close();
	}
	
	/**
	 * Writes mapping to file.
	 * 
	 * @param mapping = result of mapping search algorithm.
	 */
	public void logMapping(Mapping mapping) {
		Utils.validateNotNull(mapping);
		
		// mapping pairs
		for (EntitySet entitySet : mapping.getExemplarModel().getEntitySets()) {
			assert entitySet.getMappedTo() != null;
			logMappingPair(entitySet, entitySet.getMappedTo());
		}
		
		// transformations
		for (Transformation transformation : mapping.getTransformations()) {
			logTransformation(transformation);
		}
	}
	
	/**
	 * Writes validation evaluation result
	 * 
	 * @param golden = target mapping
	 * @param result = difference of golden mapping - found mapping
	 */
	public void logValidationEvaluationResult(ValidationEvaluator.MappingParsed golden, ValidationEvaluator.EvaluationResult result) {
		Utils.validateNotNull(golden);
		Utils.validateNotNull(result);
		
		ValidationEvaluator.MappingParsed missing = result.getMissing();
		ValidationEvaluator.MappingParsed overflow = result.getOverflow();
		
		/* MAPPING PAIRS */
		int goldenPairs = golden.getMap().keySet().size();
		int missingPairs = missing.getMap().keySet().size();
		int overPairs = overflow.getMap().keySet().size();
		
		getWriter().println("Mapping " + (goldenPairs-missingPairs) + "/" + goldenPairs + " pairs, " + overPairs + " additional pairs.");
		getWriter().println("Missing pairs: " + (missing.getMap().keySet().isEmpty() ? PrintUtils.DELIMITER_DASH : ""));
		for (String entitySet : missing.getMap().keySet()) {
			getWriter().println(" " + entitySet + PrintUtils.DELIMITER_DASH + missing.getMap().get(entitySet));
		}
		getWriter().println("Additional pairs: " + (overflow.getMap().keySet().isEmpty() ? PrintUtils.DELIMITER_DASH : ""));
		for (String entitySet : overflow.getMap().keySet()) {
			getWriter().println(" " + entitySet + PrintUtils.DELIMITER_DASH + overflow.getMap().get(entitySet));
		}
		
		/* MAPPING TRANSFORMATIONS */
		int goldenTrans = golden.getTransformations().keySet().size();
		int missingTrans = missing.getTransformations().keySet().size();
		int overTrans = overflow.getTransformations().keySet().size();
		
		getWriter().println("\nMapping  " + (goldenTrans-missingTrans) + "/" + goldenTrans + " transformations, " + overTrans + " additional transformations.");
		getWriter().println("Missing transformations: " + (missing.getTransformations().keySet().isEmpty() ? PrintUtils.DELIMITER_DASH : ""));
		for (String entitySet : missing.getTransformations().keySet()) {
			getWriter().println(" " + entitySet + PrintUtils.DELIMITER_COLON + String.join(PrintUtils.DELIMITER_DASH,missing.getTransformations().get(entitySet)));
		}
		getWriter().println("Additional transformations: " + (overflow.getTransformations().keySet().isEmpty() ? PrintUtils.DELIMITER_DASH : ""));
		for (String entitySet : overflow.getTransformations().keySet()) {
			getWriter().println(" " + entitySet + PrintUtils.DELIMITER_COLON + String.join(PrintUtils.DELIMITER_DASH,overflow.getTransformations().get(entitySet)));
		}
	}
	
	private void logMappingPair(EntitySet exemplarEntitySet, EntitySet studentEntitySet) {
		Utils.validateNotNull(exemplarEntitySet);
		Utils.validateNotNull(studentEntitySet);
		
		getWriter().println(exemplarEntitySet.getNameText() + PrintUtils.DELIMITER_DASH + studentEntitySet.getNameText());
	}
	
	private void logTransformation(Transformation transformation) {
		String message = null;
		if (EnumTransformation.REBIND_MN_TO_1NN1.equals(transformation.getCode())) {
			message = logRebindMNTo1NN1(transformation);
		}
		if (EnumTransformation.REBIND_1NN1_TO_MN.equals(transformation.getCode())) {
			message = logRebind1NN1ToMN(transformation);
		}
		if (EnumTransformation.MOVE_ATTR_TO_INCIDENT_ENTITY_SET.equals(transformation.getCode())) {
			message = logMoveAttributeToIncidentEntitySet(transformation);
		}
		if (EnumTransformation.MOVE_ATTR_TO_INCIDENT_ASSOCIATION.equals(transformation.getCode())) {
			message = logMoveAttributeToIncidentAssociation(transformation);
		}
		if (EnumTransformation.GENERALIZATION_TO_11_ASSOCIATION.equals(transformation.getCode())) {
			message = logGeneralizationTo11Associaton(transformation);
		}
		if (EnumTransformation.EXTRACT_ATTR_TO_OWN_ENTITY_SET.equals(transformation.getCode())) {
			message = logExtractAttrToOwnEntitySet(transformation);
		}
		if (EnumTransformation.CONTRACT_11_ASSOCIATION.equals(transformation.getCode())) {
			message = logContract11Association(transformation);
		}
		if (EnumTransformation.REBIND_NARY_ASSOCIATION.equals(transformation.getCode())) {
			message = logRebindNaryAssociation(transformation);
		}
		
		if (message != null) {
			getWriter().println(message);
		}
	}

	private String logRebindMNTo1NN1(Transformation transformation) {
//		REBIND_MN_TO_1NN1 - ASSOCIATION, ENTITY_SET, ASSOCIATION_1,ASSOCIATION_2 - ASSOCIATION, ASSOCIATION_1,ASSOCIATION_2,ENTITY_SET
		Association association = (Association) TransformationUtils.getTransformableByRole(transformation, EnumTransformationRole.ASSOCIATION);
		
		StringBuilder message = new StringBuilder(transformation.getCode().toString());
		message.append(PrintUtils.DELIMITER_COLON);
		message.append(association.getFirstSide().getEntitySet().getNameText() + PrintUtils.DELIMITER_DASH + association.getSecondSide().getEntitySet().getNameText());
		return message.toString();
	}
	private String logRebind1NN1ToMN(Transformation transformation) {
//		REBIND_1NN1_TO_MN - ASSOCIATION, ASSOCIATION_1,ASSOCIATION_2,ENTITY_SET - ASSOCIATION, ENTITY_SET, ASSOCIATION_1,ASSOCIATION_2
		EntitySet entitySet = (EntitySet) TransformationUtils.getTransformableByRole(transformation, EnumTransformationRole.ENTITY_SET);
		
		StringBuilder message = new StringBuilder(transformation.getCode().toString());
		message.append(PrintUtils.DELIMITER_COLON);
		message.append(entitySet.getNameText());
		return message.toString();
	}
	private String logMoveAttributeToIncidentEntitySet(Transformation transformation) {
//		MOVE_ATTR_TO_INCIDENT_ENTITY_SET - ATTRIBUTE,ASSOCIATION,ENTITY_SET - ATTRIBUTE,ASSOCIATION,ENTITY_SET
		EntitySet entitySet = (EntitySet) TransformationUtils.getTransformableByRole(transformation, EnumTransformationRole.ENTITY_SET);
		Attribute attribute = (Attribute) TransformationUtils.getTransformableByRole(transformation, EnumTransformationRole.ATTRIBUTE);
		
		StringBuilder message = new StringBuilder(transformation.getCode().toString());
		message.append(PrintUtils.DELIMITER_COLON);
		message.append(attribute.getText());
		message.append(PrintUtils.DELIMITER_DASH);
		message.append(entitySet.getNameText());
		return message.toString();
	}
	private String logMoveAttributeToIncidentAssociation(Transformation transformation) {
//		MOVE_ATTR_TO_INCIDENT_ASSOCIATION - ATTRIBUTE,ENTITY_SET,ASSOCIATION - ATTRIBUTE,ASSOCIATION,ENTITY_SET
		Association association = (Association) TransformationUtils.getTransformableByRole(transformation, EnumTransformationRole.ASSOCIATION);
		Attribute attribute = (Attribute) TransformationUtils.getTransformableByRole(transformation, EnumTransformationRole.ATTRIBUTE);
		
		StringBuilder message = new StringBuilder(transformation.getCode().toString());
		message.append(PrintUtils.DELIMITER_COLON);
		message.append(attribute.getText());
		message.append(PrintUtils.DELIMITER_DASH);
		message.append(association.getNameText());
		return message.toString();
	}
	private String logExtractAttrToOwnEntitySet(Transformation transformation) {
//		EXTRACT_ATTR_TO_OWN_ENTITY_SET - ATTRIBUTE,SOURCE_ENTITY_SET,DEST_ENTITY_SET - ATTRIBUTE,DEST_ENTITY_SET,SOURCE_ENTITY_SET
		EntitySet entitySet = (EntitySet) TransformationUtils.getTransformableByRole(transformation, EnumTransformationRole.DEST_ENTITY_SET); // should be source_entity_set by now
		Attribute attribute = (Attribute) TransformationUtils.getTransformableByRole(transformation, EnumTransformationRole.ATTRIBUTE);
		
		StringBuilder message = new StringBuilder(transformation.getCode().toString());
		message.append(PrintUtils.DELIMITER_COLON);
		message.append(attribute.getText());
		message.append(PrintUtils.DELIMITER_DASH);
		message.append(entitySet.getNameText());
		return message.toString();
	}
	private String logGeneralizationTo11Associaton(Transformation transformation) {
//		GENERALIZATION_TO_11_ASSOCIATION - GENERALIZATION - ASSOCIATION, GENERALIZATION
		Generalization generalization = (Generalization) TransformationUtils.getTransformableByRole(transformation, EnumTransformationRole.GENERALIZATION);
		
		StringBuilder message = new StringBuilder(transformation.getCode().toString());
		message.append(PrintUtils.DELIMITER_COLON);
		message.append(generalization.getSubEntitySet().getNameText());
		message.append(PrintUtils.DELIMITER_DASH);
		message.append(generalization.getSuperEntitySet().getNameText());
		return message.toString();
	}
	private String logContract11Association(Transformation transformation) {
//		CONTRACT_11_ASSOCIATION - ASSOCIATION, EXEMPLAR_MODEL_FLAG - ENTITY_SET, ASSOCIATION, TRANSFORMABLE_LIST, EXEMPLAR_MODEL_FLAG
		Association association = (Association) TransformationUtils.getTransformableByRole(transformation, EnumTransformationRole.ASSOCIATION);
		
		StringBuilder message = new StringBuilder(transformation.getCode().toString());
		message.append(PrintUtils.DELIMITER_COLON);
		message.append(association.getFirstSide().getEntitySet().getNameText());
		message.append(PrintUtils.DELIMITER_DASH);
		message.append(association.getSecondSide().getEntitySet().getNameText());
		return message.toString();
	}
	private String logRebindNaryAssociation(Transformation transformation) {
//		REBIND_NARY_ASSOCIATION - ASSOCIATION, ENTITY_SET - ENTITY_SET, ASSOCIATION
		Association association = (Association) TransformationUtils.getTransformableByRole(transformation, EnumTransformationRole.ASSOCIATION);
		
		StringBuilder message = new StringBuilder(transformation.getCode().toString());
		message.append(PrintUtils.DELIMITER_COLON);
		
		boolean first = true;
		for (AssociationSide side : association.getSides()) {
			EntitySet es = side.getEntitySet();
			
			if (!first) {
				message.append(PrintUtils.DELIMITER_DASH);
			} else {
				first = false;
			}
			message.append(es.getNameText());
		}

		return message.toString();
	}
	
	private PrintWriter getWriter() {
		if (writer == null) {
			try {
				writer = new PrintWriter(filepath, "UTF-8");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return writer;
	}
}