package sk.trizna.erm_comparison.common.enums;

public class EnumConstants {

	/*** CONFIGURATION ***/
	public static final String APP_CONFIG_NAME = "resource//cfg//app_config.properties";
	
	public static final String DICT_CONFIG_NAME = "resource//cfg//dict_config.properties";
	
	public static final String TRANSLATION_NAME = "resource//cfg//translation.properties";
	
	public static final String ATTRIBUTE_COMPOSITION_NAME = "resource//cfg//attribute_composition.properties";
	
	public static final String TRANSFORMATION_ROLE_NAME = "resource//cfg//transformation_role.properties";
	
	public static final String CONFIG_PRINT_RESULT = "print_result";
	
	public static final String CONFIG_PRINT_TRANSFORMATION_PROGRESS = "print_transformation_progress";
	
	public static final String CONFIG_TRACK_PROGRESS = "track_progress";
	
	public static final String CONFIG_EARLY_STOP = "early_stop";
	
	public static final String CONFIG_EARLY_STOP_BOUND = "early_stop_bound";
	
	public static final String CONFIG_LANGUAGE_PROCESSOR = "language_processor";
	
	/*** LANGUAGE PROCESSOR IMPLEMENTATIONS ***/
	public static final String LP_DICTIONARY_IMPL = "dictionary";
	
	public static final String LP_WORD2VEC_DICT_IMPL = "word2vec_dict";
	
	/*** ATTRIBUTES ***/

	public static final String NAME_ATTRIBUTE = "Name";	
}
