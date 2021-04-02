package sk.trizna.erm_comparison.common;

import java.io.PrintWriter;

import sk.trizna.erm_comparison.common.enums.EnumTransformation;
import sk.trizna.erm_comparison.common.enums.EnumTransformationRole;
import sk.trizna.erm_comparison.common.utils.PrintUtils;
import sk.trizna.erm_comparison.common.utils.RelationshipUtils;
import sk.trizna.erm_comparison.common.utils.TransformationUtils;
import sk.trizna.erm_comparison.common.utils.Utils;
import sk.trizna.erm_comparison.comparing.mapping.Mapping;
import sk.trizna.erm_comparison.entity_relationship_model.Association;
import sk.trizna.erm_comparison.entity_relationship_model.AssociationSide;
import sk.trizna.erm_comparison.entity_relationship_model.Attribute;
import sk.trizna.erm_comparison.entity_relationship_model.EntitySet;
import sk.trizna.erm_comparison.entity_relationship_model.Generalization;
import sk.trizna.erm_comparison.transformations.Transformable;
import sk.trizna.erm_comparison.transformations.Transformation;

public class Logger {
	
	
	private static final String EXEMPLAR_FLAG = "E";
	private static final String STUDENT_FLAG = "S";
	protected static final String MAPPING_DELIMITER = "===========================================================";

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
	
	private void logMappingPair(EntitySet exemplarEntitySet, EntitySet studentEntitySet) {
		Utils.validateNotNull(exemplarEntitySet);
		Utils.validateNotNull(studentEntitySet);
		
		getWriter().println(exemplarEntitySet.getNameText() + PrintUtils.DELIMITER_DASH + studentEntitySet.getNameText());
	}
	
	private void logTransformation(Transformation transformation) {
		String message = null;
		if (EnumTransformation.REBIND_MN_TO_1NN1.equals(transformation.getCode())) {
			message = logRebindMNTo1NN1(transformation);
		} else
		if (EnumTransformation.REBIND_1NN1_TO_MN.equals(transformation.getCode())) {
			message = logRebind1NN1ToMN(transformation);
		} else
		if (EnumTransformation.MOVE_ATTR_TO_INCIDENT_ENTITY_SET.equals(transformation.getCode())) {
			message = logMoveAttributeToIncidentEntitySet(transformation);
		} else
		if (EnumTransformation.MOVE_ATTR_TO_INCIDENT_ASSOCIATION.equals(transformation.getCode())) {
			message = logMoveAttributeToIncidentAssociation(transformation);
		} else
		if (EnumTransformation.GENERALIZATION_TO_11_ASSOCIATION.equals(transformation.getCode())) {
			message = logGeneralizationTo11Associaton(transformation);
		} else
		if (EnumTransformation.EXTRACT_ATTR_TO_OWN_ENTITY_SET.equals(transformation.getCode())) {
			message = logExtractAttrToOwnEntitySet(transformation);
		} else
		if (EnumTransformation.CONTRACT_11_ASSOCIATION.equals(transformation.getCode())) {
			message = logContract11Association(transformation);
		} else
		if (EnumTransformation.REBIND_NARY_ASSOCIATION.equals(transformation.getCode())) {
			message = logRebindNaryAssociation(transformation);
		} else
		if (EnumTransformation.DECOMPOSE_ATTRIBUTE.equals(transformation.getCode())) {
			message = logDecomposeAttribute(transformation);
		} else
		if (EnumTransformation.MERGE_ENTITY_SETS.equals(transformation.getCode())) {
			message = logMergeEntitySets(transformation);
		} else
		if (EnumTransformation.CHANGE_CARDINALITY.equals(transformation.getCode())) {
			message = logChangeCardinality(transformation);
		}
	
		if (message != null) {
			getWriter().println(message);
		}
	}

	private String logRebindMNTo1NN1(Transformation transformation) {
//		REBIND_MN_TO_1NN1 - ASSOCIATION, ENTITY_SET, ASSOCIATION_1,ASSOCIATION_2 - ASSOCIATION, ASSOCIATION_1,ASSOCIATION_2,ENTITY_SET
		Association association = (Association) TransformationUtils.getTransformableByRole(transformation, EnumTransformationRole.ASSOCIATION);
		
		StringBuilder message = getTransformationLogHeader(transformation);
		message.append(association.getFirstSide().getEntitySet().getNameText() + PrintUtils.DELIMITER_DASH + association.getSecondSide().getEntitySet().getNameText());
		return message.toString();
	}
	private String logRebind1NN1ToMN(Transformation transformation) {
//		REBIND_1NN1_TO_MN - ASSOCIATION, ASSOCIATION_1,ASSOCIATION_2,ENTITY_SET - ASSOCIATION, ENTITY_SET, ASSOCIATION_1,ASSOCIATION_2
		EntitySet entitySet = (EntitySet) TransformationUtils.getTransformableByRole(transformation, EnumTransformationRole.ENTITY_SET);
		
		StringBuilder message = getTransformationLogHeader(transformation);
		message.append(entitySet.getNameText());
		return message.toString();
	}
	private String logMoveAttributeToIncidentEntitySet(Transformation transformation) {
//		MOVE_ATTR_TO_INCIDENT_ENTITY_SET - ATTRIBUTE,ASSOCIATION,ENTITY_SET - ATTRIBUTE,ASSOCIATION,ENTITY_SET
		EntitySet entitySet = (EntitySet) TransformationUtils.getTransformableByRole(transformation, EnumTransformationRole.ENTITY_SET);
		Attribute attribute = (Attribute) TransformationUtils.getTransformableByRole(transformation, EnumTransformationRole.ATTRIBUTE);
		
		StringBuilder message = getTransformationLogHeader(transformation);
		message.append(attribute.getText());
		message.append(PrintUtils.DELIMITER_DASH);
		message.append(entitySet.getNameText());
		return message.toString();
	}
	private String logMoveAttributeToIncidentAssociation(Transformation transformation) {
//		MOVE_ATTR_TO_INCIDENT_ASSOCIATION - ATTRIBUTE,ENTITY_SET,ASSOCIATION - ATTRIBUTE,ASSOCIATION,ENTITY_SET
		Association association = (Association) TransformationUtils.getTransformableByRole(transformation, EnumTransformationRole.ASSOCIATION);
		Attribute attribute = (Attribute) TransformationUtils.getTransformableByRole(transformation, EnumTransformationRole.ATTRIBUTE);
		
		StringBuilder message = getTransformationLogHeader(transformation);
		message.append(attribute.getText());
		message.append(PrintUtils.DELIMITER_DASH);
		message.append(association.getNameText());
		return message.toString();
	}
	private String logExtractAttrToOwnEntitySet(Transformation transformation) {
//		EXTRACT_ATTR_TO_OWN_ENTITY_SET - ATTRIBUTE,SOURCE_ENTITY_SET,DEST_ENTITY_SET - ATTRIBUTE,DEST_ENTITY_SET,SOURCE_ENTITY_SET
		EntitySet entitySet = (EntitySet) TransformationUtils.getTransformableByRole(transformation, EnumTransformationRole.DEST_ENTITY_SET); // should be source_entity_set by now
		Attribute attribute = (Attribute) TransformationUtils.getTransformableByRole(transformation, EnumTransformationRole.ATTRIBUTE);
		
		StringBuilder message = getTransformationLogHeader(transformation);
		message.append(attribute.getText());
		message.append(PrintUtils.DELIMITER_DASH);
		message.append(entitySet.getNameText());
		return message.toString();
	}
	private String logGeneralizationTo11Associaton(Transformation transformation) {
//		GENERALIZATION_TO_11_ASSOCIATION - GENERALIZATION - ASSOCIATION, GENERALIZATION
		Generalization generalization = (Generalization) TransformationUtils.getTransformableByRole(transformation, EnumTransformationRole.GENERALIZATION);
		
		StringBuilder message = getTransformationLogHeader(transformation);
		message.append(generalization.getSubEntitySet().getNameText());
		message.append(PrintUtils.DELIMITER_DASH);
		message.append(generalization.getSuperEntitySet().getNameText());
		return message.toString();
	}
	private String logContract11Association(Transformation transformation) {
//		CONTRACT_11_ASSOCIATION - ASSOCIATION, EXEMPLAR_MODEL_FLAG - ENTITY_SET, ASSOCIATION, TRANSFORMABLE_LIST, EXEMPLAR_MODEL_FLAG
		Association association = (Association) TransformationUtils.getTransformableByRole(transformation, EnumTransformationRole.ASSOCIATION);
		
		StringBuilder message = getTransformationLogHeader(transformation);
		message.append(association.getFirstSide().getEntitySet().getNameText());
		message.append(PrintUtils.DELIMITER_DASH);
		message.append(association.getSecondSide().getEntitySet().getNameText());
		return message.toString();
	}
	private String logRebindNaryAssociation(Transformation transformation) {
//		REBIND_NARY_ASSOCIATION - ASSOCIATION, ENTITY_SET - ENTITY_SET, ASSOCIATION
		Association association = (Association) TransformationUtils.getTransformableByRole(transformation, EnumTransformationRole.ASSOCIATION);
		
		StringBuilder message = getTransformationLogHeader(transformation);
		
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
	
	private String logDecomposeAttribute(Transformation transformation) {
//		DECOMPOSE_ATTRIBUTE - ENTITY_SET, ATTRIBUTE, TRANSFORMABLE_LIST - ENTITY_SET, ATTRIBUTE, TRANSFORMABLE_LIST
		EntitySet entitySet = (EntitySet) TransformationUtils.getTransformableByRole(transformation, EnumTransformationRole.ENTITY_SET);
		Attribute attribute = (Attribute) TransformationUtils.getTransformableByRole(transformation, EnumTransformationRole.ATTRIBUTE);
		
		StringBuilder message = getTransformationLogHeader(transformation);
		message.append(attribute.getText());
		message.append(PrintUtils.DELIMITER_DASH);
		message.append(entitySet.getNameText());
		
		return message.toString();
	}
	
	private String logMergeEntitySets(Transformation transformation) {
//		MERGE_ENTITY_SETS = ENTITY_SET1, ENTITY_SET2 - ENTITY_SET, ?ENTITY_SET2, ?TRANSFORMABLE_LIST
		EntitySet entitySet = (EntitySet) TransformationUtils.getTransformableByRole(transformation, EnumTransformationRole.ENTITY_SET);
		if (entitySet == null) {
			entitySet = (EntitySet) TransformationUtils.getTransformableByRole(transformation, EnumTransformationRole.ENTITY_SET1);
		}
		EntitySet entitySet2 = (EntitySet) TransformationUtils.getTransformableByRole(transformation, EnumTransformationRole.ENTITY_SET2);
		
		StringBuilder message = getTransformationLogHeader(transformation);
		message.append(entitySet.getNameText());
		message.append(PrintUtils.DELIMITER_DASH);
		message.append(entitySet2.getNameText());
		
		return message.toString();
	}
	
	private String logChangeCardinality(Transformation transformation) {
//		CHANGE_CARDINALITY = ENTITY_SET, ASSOCIATION - ENTITY_SET, ASSOCIATION
		EntitySet entitySet = (EntitySet) TransformationUtils.getTransformableByRole(transformation, EnumTransformationRole.ENTITY_SET);
		Association association = (Association) TransformationUtils.getTransformableByRole(transformation, EnumTransformationRole.ASSOCIATION);
		
		StringBuilder message = getTransformationLogHeader(transformation);
		message.append(entitySet.getNameText());
		message.append(PrintUtils.DELIMITER_DASH);
		if (association.isBinary()) {
			message.append(RelationshipUtils.getOtherEntitySet(association, entitySet).getNameText());
		} else {
			message.append(association.getNameText());
		}
		
		
		return message.toString();
	}
	
	
	/*
	 * TODOs:
	 * 	FLIP_CARDINALITY:E/S-es1-es2 (first es has it's cardinality flipped)
	 */
	
	protected PrintWriter getWriter() {
		if (writer == null) {
			try {
				writer = new PrintWriter(filepath, "UTF-8");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return writer;
	}
	
	private StringBuilder getTransformationLogHeader(Transformation transformation) {
		Utils.validateNotNull(transformation);
		
		StringBuilder message = new StringBuilder(transformation.getCode().toString());
		message.append(PrintUtils.DELIMITER_COLON);
		message.append(getExemplarStudentFlag(transformation));
		message.append(PrintUtils.DELIMITER_DASH);
		
		return message;
	}
	
	private String getExemplarStudentFlag(Transformation transformation) {
		Utils.validateNotNull(transformation);
		
		Transformable flag = TransformationUtils.getTransformableByRole(transformation, EnumTransformationRole.EXEMPLAR_MODEL_FLAG);
		return flag != null ? EXEMPLAR_FLAG : STUDENT_FLAG;
	}
}
