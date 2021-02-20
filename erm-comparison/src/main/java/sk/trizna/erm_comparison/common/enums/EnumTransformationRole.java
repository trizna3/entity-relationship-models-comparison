package sk.trizna.erm_comparison.common.enums;

public enum EnumTransformationRole {

	/*** TRANSFORMATION ROLES CODES ***/
	ATTRIBUTE,
	SOURCE_ENTITY_SET,
	DEST_ENTITY_SET,
	ASSOCIATION,
	ENTITY_SET,
	ASSOCIATION_1,
	ASSOCIATION_2,
	GENERALIZATION,
	ASSOCIATION_SIDE,
	ENTITY_SET_TARGET,
	ATTRIBUTE_TARGET,
	TRANSFORMABLE_LIST,
	TRANSFORMABLE_MAP,
	ENTITY_SET1,
	ENTITY_SET2,
	/**
	 * flag if the transformation shall be done on the exemplar model (default is
	 * the student's model).
	 */
	EXEMPLAR_MODEL_FLAG
}
