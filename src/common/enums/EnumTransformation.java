package common.enums;

public class EnumTransformation {

	/*** TRANSFORMATION CODES ***/

	public static final String CREATE_ENTITY_SET = "CREATE_ENTITY_SET";

	public static final String CREATE_ASSOCIATION = "CREATE_ASSOCIATION";

	public static final String CREATE_GENERALIZATION = "CREATE_GENERALIZATION";

	public static final String CREATE_ATTRIBUTE = "CREATE_ATTRIBUTE";

	public static final String REMOVE_ENTITY_SET = "REMOVE_ENTITY_SET";

	public static final String REMOVE_ASSOCIATION = "REMOVE_ASSOCIATION";

	public static final String REMOVE_GENERALIZATION = "REMOVE_GENERALIZATION";

	public static final String REMOVE_ATTRIBUTE = "REMOVE_ATTRIBUTE";

	public static final String CHANGE_CARDINALITY = "CHANGE_CARDINALITY";

	public static final String RENAME_ENTITY_SET = "RENAME_ENTITY_SET";

	public static final String RENAME_ATTRIBUTE = "RENAME_ATTRIBUTE";

	public static final String EXTRACT_ATTR_TO_OWN_ENTITY_SET = "EXTRACT_ATTR_TO_OWN_ENTITY_SET";
	
	public static final String MERGE_ATTR_FROM_OWN_ENTITY_SET = "MERGE_ATTR_FROM_OWN_ENTITY_SET";

	public static final String MOVE_ATTR_TO_INCIDENT_ENTITY_SET = "MOVE_ATTR_TO_INCIDENT_ENTITY_SET";

	public static final String MOVE_ATTR_TO_INCIDENT_ASSOCIATION = "MOVE_ATTR_TO_INCIDENT_ASSOCIATION";

	public static final String REBIND_MN_TO_1NN1 = "REBIND_MN_TO_1NN1";

	public static final String REBIND_1NN1_TO_MN = "REBIND_1NN1_TO_MN";

	public static final String GENERALIZATION_TO_11_ASSOCIATION = "GENERALIZATION_TO_11_ASSOCIATION";
	
	public static final String _11_ASSOCIATION_TO_GENERALIZATION = "_11_ASSOCIATION_TO_GENERALIZATION";

	public static final String CONTRACT_11_ASSOCIATION = "CONTRACT_11_ASSOCIATION";
	
	public static final String UNCONTRACT_11_ASSOCIATION = "UNCONTRACT_11_ASSOCIATION";

	public static final String REBIND_NARY_ASSOCIATION = "REBIND_NARY_ASSOCIATION";
	
	public static final String BIND_TO_NARY_ASSOCIATION = "BIND_TO_NARY_ASSOCIATION";
}
