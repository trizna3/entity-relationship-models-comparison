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
		translation.setArgument(TranslationConstants.ARG_EXEMPLAR, exemplarName);
		return translation.toString();
	}
	
	public static String translateRemoveEntitySet(String studentName) {
		Translation translation = new Translation(EnumTransformation.REMOVE_ENTITY_SET);
		translation.setArgument(TranslationConstants.ARG_STUDENT, studentName);
		return translation.toString();
	}
	
	public static String translateCreateAttribute(String attributed, String attribute) {
		Translation translation = new Translation(EnumTransformation.CREATE_ATTRIBUTE);
		translation.setArgument(TranslationConstants.ARG_ATTRIBUTED, attributed);
		translation.setArgument(TranslationConstants.ARG_ATTRIBUTE, attribute);
		return translation.toString();
	}
	public static String translateRemoveAttribute(String attributed, String attribute) {
		Translation translation = new Translation(EnumTransformation.REMOVE_ENTITY_SET);
		translation.setArgument(TranslationConstants.ARG_ATTRIBUTED, attributed);
		translation.setArgument(TranslationConstants.ARG_ATTRIBUTE, attribute);
		return translation.toString();
	}
}
