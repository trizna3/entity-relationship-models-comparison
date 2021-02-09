package sk.trizna.erm_comparison.common;

import sk.trizna.erm_comparison.common.enums.EnumTransformation;
import sk.trizna.erm_comparison.common.enums.TranslationConstants;
import sk.trizna.erm_comparison.common.key_config.TranslationManager;

public class TranslationUtils {

	private static final TranslationManager TRANSLATION_MANAGER = TranslationManager.getInstance(); 
	
	public static String getTemplate(String code) {
		return TRANSLATION_MANAGER.getResource(code);
	}
	
	public static String translateRenameEntitySet(String exemplarName, String studentName) {
		Translation translation = new Translation(EnumTransformation.RENAME_ENTITY_SET);
		translation.setArgument(TranslationConstants.ARG_EXEMPLAR, exemplarName);
		translation.setArgument(TranslationConstants.ARG_STUDENT, studentName);
		return translation.toString();
	}
	
	public static String translateCreateEntitySet(String exemplarName) {
		Translation translation = new Translation(EnumTransformation.CREATE_ENTITY_SET);
		translation.setArgument(TranslationConstants.ARG_ENTITY_SET, exemplarName);
		return translation.toString();
	}
	
	public static String translateRemoveEntitySet(String studentName) {
		Translation translation = new Translation(EnumTransformation.REMOVE_ENTITY_SET);
		translation.setArgument(TranslationConstants.ARG_ENTITY_SET, studentName);
		return translation.toString();
	}
	
	public static String translateCreateAttribute(String attributed, String attribute) {
		Translation translation = new Translation(EnumTransformation.CREATE_ATTRIBUTE);
		translation.setArgument(TranslationConstants.ARG_ATTRIBUTED, attributed);
		translation.setArgument(TranslationConstants.ARG_ATTRIBUTE, attribute);
		return translation.toString();
	}
	
	public static String translateRemoveAttribute(String attributed, String attribute) {
		Translation translation = new Translation(EnumTransformation.REMOVE_ATTRIBUTE);
		translation.setArgument(TranslationConstants.ARG_ATTRIBUTED, attributed);
		translation.setArgument(TranslationConstants.ARG_ATTRIBUTE, attribute);
		return translation.toString();
	}
	
	public static String translateCreateAssociation(String associationName) {
		Translation translation = new Translation(EnumTransformation.CREATE_ASSOCIATION);
		translation.setArgument(TranslationConstants.ARG_ASSOCIATION, associationName);
		return translation.toString();
	}
	
	public static String translateRemoveAssociation(String associationName) {
		Translation translation = new Translation(EnumTransformation.REMOVE_ASSOCIATION);
		translation.setArgument(TranslationConstants.ARG_ASSOCIATION, associationName);
		return translation.toString();
	}
	
	public static String translateCreateGeneralization(String superEntitySet, String subEntitySet) {
		Translation translation = new Translation(EnumTransformation.CREATE_GENERALIZATION);
		translation.setArgument(TranslationConstants.ARG_SUPER_ENTITY_SET, superEntitySet);
		translation.setArgument(TranslationConstants.ARG_SUB_ENTITY_SET, subEntitySet);
		return translation.toString();
	}
	
	public static String translateRemoveGeneralization(String superEntitySet, String subEntitySet) {
		Translation translation = new Translation(EnumTransformation.REMOVE_GENERALIZATION);
		translation.setArgument(TranslationConstants.ARG_SUPER_ENTITY_SET, superEntitySet);
		translation.setArgument(TranslationConstants.ARG_SUB_ENTITY_SET, subEntitySet);
		return translation.toString();
	}
	
	public static String translateExtractAttrToOwnEntitySet(String attribute, String sourceEntitySet) {
		Translation translation = new Translation(EnumTransformation.EXTRACT_ATTR_TO_OWN_ENTITY_SET);
		translation.setArgument(TranslationConstants.ARG_ATTRIBUTE, attribute);
		translation.setArgument(TranslationConstants.ARG_SOURCE_ENTITY_SET, sourceEntitySet);
		return translation.toString();
	}
	
	public static String translateMoveAttrToIncidentEntitySet(String attribute, String destEntitySet) {
		Translation translation = new Translation(EnumTransformation.MOVE_ATTR_TO_INCIDENT_ENTITY_SET);
		translation.setArgument(TranslationConstants.ARG_ATTRIBUTE, attribute);
		translation.setArgument(TranslationConstants.ARG_DEST_ENTITY_SET, destEntitySet);
		return translation.toString();
	}
	
	public static String translateMoveAttrToIncidentAssociation(String attribute, String destAssociation) {
		Translation translation = new Translation(EnumTransformation.MOVE_ATTR_TO_INCIDENT_ASSOCIATION);
		translation.setArgument(TranslationConstants.ARG_ATTRIBUTE, attribute);
		translation.setArgument(TranslationConstants.ARG_DEST_ASSOCIATION, destAssociation);
		return translation.toString();
	}
	
	public static String translateRebindMNTo1NN1(String associationName) {
		Translation translation = new Translation(EnumTransformation.REBIND_MN_TO_1NN1);
		translation.setArgument(TranslationConstants.ARG_ASSOCIATION, associationName);
		return translation.toString();
	}
	
	public static String translateRebind1NN1ToMN(String associationName) {
		Translation translation = new Translation(EnumTransformation.REBIND_1NN1_TO_MN);
		translation.setArgument(TranslationConstants.ARG_ASSOCIATION, associationName);
		return translation.toString();
	}
	
	public static String translateGeneralizationTo11Association(String superEntitySet, String subEntitySet) {
		Translation translation = new Translation(EnumTransformation.GENERALIZATION_TO_11_ASSOCIATION);
		translation.setArgument(TranslationConstants.ARG_SUPER_ENTITY_SET, superEntitySet);
		translation.setArgument(TranslationConstants.ARG_SUB_ENTITY_SET, subEntitySet);
		return translation.toString();
	}
	
	public static String translateContract11Association(String associationName) {
		Translation translation = new Translation(EnumTransformation.CONTRACT_11_ASSOCIATION);
		translation.setArgument(TranslationConstants.ARG_ASSOCIATION, associationName);
		return translation.toString();
	}
	
	public static String translateRebindNaryAssociation(String associationName) {
		Translation translation = new Translation(EnumTransformation.REBIND_NARY_ASSOCIATION);
		translation.setArgument(TranslationConstants.ARG_ASSOCIATION, associationName);
		return translation.toString();
	}
}
