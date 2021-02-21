package sk.trizna.erm_comparison.comparing.translator;

import sk.trizna.erm_comparison.common.enums.EnumTransformation;
import sk.trizna.erm_comparison.common.enums.EnumTransformationRole;
import sk.trizna.erm_comparison.common.enums.TranslationConstants;
import sk.trizna.erm_comparison.common.utils.PrintUtils;
import sk.trizna.erm_comparison.common.utils.TransformationUtils;
import sk.trizna.erm_comparison.common.utils.TranslationUtils;
import sk.trizna.erm_comparison.common.utils.Utils;
import sk.trizna.erm_comparison.comparing.ERModelDiff;
import sk.trizna.erm_comparison.comparing.ERModelDiffReport;
import sk.trizna.erm_comparison.entity_relationship_model.Association;
import sk.trizna.erm_comparison.entity_relationship_model.Attribute;
import sk.trizna.erm_comparison.entity_relationship_model.EntitySet;
import sk.trizna.erm_comparison.entity_relationship_model.Generalization;
import sk.trizna.erm_comparison.entity_relationship_model.TransformableFlag;
import sk.trizna.erm_comparison.transformations.Transformation;

public class TransformationTranslator {
	
	/**
	 * Fills the transformation parts of {@link ERModelDiffReport}.
	 * 
	 * @param report
	 * @param diff
	 */
	public void translate(ERModelDiffReport report, ERModelDiff diff) {
		Utils.validateNotNull(report);
		Utils.validateNotNull(diff);
		
		for (Transformation transformation : diff.getTransformationsMade()) {
			String translation = translateTransformation(transformation, diff);
			if (translation != null) {
				report.getTransformationNotes().add(translation);
			}
		}
	}
	
	/**
	 * Translates transformation to text student-readable message, containing info describing student's solution error.
	 * Ignores equivalent transformations.
	 * 
	 * @param transformation
	 * @return
	 */
	private static String translateTransformation(Transformation transformation, ERModelDiff diff) {
		if (!TranslationConstants.TRANSLATABLE_TRANSFORMATIONS.contains(transformation.getCode())) {
			return null;
		}
		if (EnumTransformation.EXTRACT_ATTR_TO_OWN_ENTITY_SET.equals(transformation.getCode())) {
			Attribute attribute = (Attribute) TransformationUtils.getTransformableByRole(transformation, EnumTransformationRole.ATTRIBUTE);
			EntitySet sourceEntitySet = (EntitySet) TransformationUtils.getTransformableByRole(transformation, EnumTransformationRole.DEST_ENTITY_SET); // should be source by now
			return TranslationUtils.translateExtractAttrToOwnEntitySet(attribute.getText(), sourceEntitySet.getNameText());
		} 
		else if (EnumTransformation.MOVE_ATTR_TO_INCIDENT_ENTITY_SET.equals(transformation.getCode())) {
			Attribute attribute = (Attribute) TransformationUtils.getTransformableByRole(transformation, EnumTransformationRole.ATTRIBUTE);
			EntitySet destEntitySet = (EntitySet) TransformationUtils.getTransformableByRole(transformation, EnumTransformationRole.ENTITY_SET);
			return TranslationUtils.translateMoveAttrToIncidentEntitySet(attribute.getText(), destEntitySet.getNameText());
		} 
		else if (EnumTransformation.MOVE_ATTR_TO_INCIDENT_ASSOCIATION.equals(transformation.getCode())) {
			Attribute attribute = (Attribute) TransformationUtils.getTransformableByRole(transformation, EnumTransformationRole.ATTRIBUTE);
			Association association = (Association) TransformationUtils.getTransformableByRole(transformation, EnumTransformationRole.ASSOCIATION);
			return TranslationUtils.translateMoveAttrToIncidentAssociation(attribute.getText(), PrintUtils.getNameByIncidentEntitySets(association));
		} 
		else if (EnumTransformation.GENERALIZATION_TO_11_ASSOCIATION.equals(transformation.getCode())) {
			Generalization generalization = (Generalization) TransformationUtils.getTransformableByRole(transformation, EnumTransformationRole.GENERALIZATION);
			return TranslationUtils.translateGeneralizationTo11Association(generalization.getSuperEntitySet().getNameText(), generalization.getSubEntitySet().getNameText());
		} 
		else if (EnumTransformation.REBIND_NARY_ASSOCIATION.equals(transformation.getCode())) {
			Association association = (Association) TransformationUtils.getTransformableByRole(transformation, EnumTransformationRole.ASSOCIATION);
			EntitySet entitySet = (EntitySet) TransformationUtils.getTransformableByRole(transformation, EnumTransformationRole.ENTITY_SET);
			TransformableFlag flag = (TransformableFlag) TransformationUtils.getTransformableByRole(transformation, EnumTransformationRole.EXEMPLAR_MODEL_FLAG);
			if (flag != null) {
				EntitySet image = (EntitySet) Utils.firstNonNull(diff.getEntitySetMap().get(entitySet),entitySet.getMappedTo());
				if (image == null) {
					return null;
				}
				return TranslationUtils.translateRebindNaryAssociationExemplar(image.getNameText());
			} else {
				return TranslationUtils.translateRebindNaryAssociationStudent(PrintUtils.getNameByIncidentEntitySets(association));				
			}
		} 
		else if (EnumTransformation.MERGE_ENTITY_SETS.equals(transformation.getCode())) {
			EntitySet entitySet1 = (EntitySet) TransformationUtils.getTransformableByRole(transformation, EnumTransformationRole.ENTITY_SET);
			EntitySet entitySet2 = (EntitySet) TransformationUtils.getTransformableByRole(transformation, EnumTransformationRole.ENTITY_SET2);
			return TranslationUtils.translateMergeEntitySets(entitySet1.getNameText(),entitySet2.getNameText());
		}
		else if (EnumTransformation.CHANGE_CARDINALITY.equals(transformation.getCode())) {
			EntitySet entitySet = (EntitySet) TransformationUtils.getTransformableByRole(transformation, EnumTransformationRole.ENTITY_SET);
			Association association = (Association) TransformationUtils.getTransformableByRole(transformation, EnumTransformationRole.ASSOCIATION);
			return TranslationUtils.translateChangeCardinality(entitySet.getNameText(),PrintUtils.getNameByIncidentEntitySets(association));
		} else {
			throw new IllegalArgumentException("Untranslatable transformation sent to translation!");
		}
	}
}
