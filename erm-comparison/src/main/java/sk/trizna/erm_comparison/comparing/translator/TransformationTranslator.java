package sk.trizna.erm_comparison.comparing.translator;

import sk.trizna.erm_comparison.common.PrintUtils;
import sk.trizna.erm_comparison.common.TransformationUtils;
import sk.trizna.erm_comparison.common.TranslationUtils;
import sk.trizna.erm_comparison.common.Utils;
import sk.trizna.erm_comparison.common.enums.EnumTransformation;
import sk.trizna.erm_comparison.common.enums.EnumTransformationRole;
import sk.trizna.erm_comparison.comparing.ERModelDiff;
import sk.trizna.erm_comparison.comparing.ERModelDiffReport;
import sk.trizna.erm_comparison.entity_relationship_model.Association;
import sk.trizna.erm_comparison.entity_relationship_model.Attribute;
import sk.trizna.erm_comparison.entity_relationship_model.EntitySet;
import sk.trizna.erm_comparison.entity_relationship_model.Generalization;
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
			String translation = translateTransformation(transformation);
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
	private static String translateTransformation(Transformation transformation) {
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
		else if (EnumTransformation.REBIND_MN_TO_1NN1.equals(transformation.getCode())) {
			Association association = (Association) TransformationUtils.getTransformableByRole(transformation, EnumTransformationRole.ASSOCIATION);
			return TranslationUtils.translateRebindMNTo1NN1(PrintUtils.getNameByIncidentEntitySets(association));
		} 
		else if (EnumTransformation.REBIND_1NN1_TO_MN.equals(transformation.getCode())) {
			Association association = (Association) TransformationUtils.getTransformableByRole(transformation, EnumTransformationRole.ASSOCIATION);
			return TranslationUtils.translateRebind1NN1ToMN(PrintUtils.getNameByIncidentEntitySets(association));
		} 
		else if (EnumTransformation.GENERALIZATION_TO_11_ASSOCIATION.equals(transformation.getCode())) {
			Generalization generalization = (Generalization) TransformationUtils.getTransformableByRole(transformation, EnumTransformationRole.GENERALIZATION);
			return TranslationUtils.translateGeneralizationTo11Association(generalization.getSuperEntitySet().getNameText(), generalization.getSubEntitySet().getNameText());
		} 
		else if (EnumTransformation.CONTRACT_11_ASSOCIATION.equals(transformation.getCode())) {
			Association association = (Association) TransformationUtils.getTransformableByRole(transformation, EnumTransformationRole.ASSOCIATION);
			return TranslationUtils.translateContract11Association(PrintUtils.getNameByIncidentEntitySets(association));
		} 
		else if (EnumTransformation.REBIND_NARY_ASSOCIATION.equals(transformation.getCode())) {
			Association association = (Association) TransformationUtils.getTransformableByRole(transformation, EnumTransformationRole.ASSOCIATION);
			return TranslationUtils.translateRebindNaryAssociation(PrintUtils.getNameByIncidentEntitySets(association));
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
		}
		else {
			return null;
		}
	}
}
