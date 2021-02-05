package sk.trizna.erm_comparison.common.enums;

public enum EnumTransformation {

	/*** TRANSFORMATION CODES ***/

	EMPTY,
	CREATE_ENTITY_SET,
	CREATE_ASSOCIATION,
	CREATE_GENERALIZATION,
	CREATE_ATTRIBUTE,
	REMOVE_ENTITY_SET,
	REMOVE_ASSOCIATION,
	REMOVE_GENERALIZATION,
	REMOVE_ATTRIBUTE,
	CHANGE_CARDINALITY,
	RENAME_ENTITY_SET,
	RENAME_ATTRIBUTE,
	EXTRACT_ATTR_TO_OWN_ENTITY_SET,
	MERGE_ATTR_FROM_OWN_ENTITY_SET,
	MOVE_ATTR_TO_INCIDENT_ENTITY_SET,
	MOVE_ATTR_TO_INCIDENT_ASSOCIATION,
	REBIND_MN_TO_1NN1,
	REBIND_1NN1_TO_MN,
	GENERALIZATION_TO_11_ASSOCIATION,
	_11_ASSOCIATION_TO_GENERALIZATION,
	CONTRACT_11_ASSOCIATION,
	UNCONTRACT_11_ASSOCIATION,
	REBIND_NARY_ASSOCIATION,
	BIND_TO_NARY_ASSOCIATION
}