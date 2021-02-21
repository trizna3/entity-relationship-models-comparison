package sk.trizna.erm_comparison.common.enums;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class TranslationConstants {
	
	/** ARGUMENTS **/
	public static final String ARG_EXEMPLAR = "[exemplar]";
	
	public static final String ARG_STUDENT = "[student]";
	
	public static final String ARG_ATTRIBUTED = "[attributed]";
	
	public static final String ARG_ATTRIBUTE = "[attribute]";
	
	public static final String ARG_ENTITY_SET = "[entitySet]";

	public static final String ARG_ASSOCIATION = "[association]";
	
	public static final String ARG_SUPER_ENTITY_SET = "[superEntitySet]";
	
	public static final String ARG_SUB_ENTITY_SET = "[subEntitySet]";
	
	public static final String ARG_SOURCE_ENTITY_SET = "[sourceEntitySet]";
	
	public static final String ARG_DEST_ENTITY_SET = "[destEntitySet]";
	
	public static final String ARG_DEST_ASSOCIATION = "[destAssociation]";
	
	public static final String ARG_ENTITY_SET_1 = "[entitySet1]";
	
	public static final String ARG_ENTITY_SET_2 = "[entitySet2]";
	
	public static final Set<EnumTransformation> TRANSLATABLE_TRANSFORMATIONS = new HashSet<>(
			Arrays.asList(
					EnumTransformation.CREATE_ENTITY_SET,
					EnumTransformation.CREATE_ASSOCIATION,
					EnumTransformation.CREATE_GENERALIZATION,
					EnumTransformation.CREATE_ATTRIBUTE,
					EnumTransformation.REMOVE_ENTITY_SET,
					EnumTransformation.REMOVE_ASSOCIATION,
					EnumTransformation.REMOVE_GENERALIZATION,
					EnumTransformation.REMOVE_ATTRIBUTE,
					EnumTransformation.CHANGE_CARDINALITY,
					EnumTransformation.RENAME_ENTITY_SET,
					EnumTransformation.RENAME_ATTRIBUTE,
					EnumTransformation.EXTRACT_ATTR_TO_OWN_ENTITY_SET,
					EnumTransformation.MOVE_ATTR_TO_INCIDENT_ENTITY_SET,
					EnumTransformation.MOVE_ATTR_TO_INCIDENT_ASSOCIATION,
					EnumTransformation.GENERALIZATION_TO_11_ASSOCIATION,
					EnumTransformation.REBIND_NARY_ASSOCIATION,
					EnumTransformation.MERGE_ENTITY_SETS
					));
}
