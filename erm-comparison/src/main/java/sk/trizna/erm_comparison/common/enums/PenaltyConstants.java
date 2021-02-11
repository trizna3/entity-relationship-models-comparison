package sk.trizna.erm_comparison.common.enums;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class PenaltyConstants {
	
	public static final double TRANSFORMATION_PENALTY_FREE = 0;
	public static final double TRANSFORMATION_PENALTY_LIGHT = 0.5;
	public static final double TRANSFORMATION_PENALTY_MEDIUM = 3;
	public static final double TRANSFORMATION_PENALTY_HEAVY = 5;
	
	public static final Set<EnumTransformation> FREE_TRANSFORMATIONS = new HashSet<>(
			Arrays.asList(
					EnumTransformation.REBIND_MN_TO_1NN1,
					EnumTransformation.REBIND_1NN1_TO_MN,
					EnumTransformation.EXTRACT_ATTR_TO_OWN_ENTITY_SET,
					EnumTransformation.CONTRACT_11_ASSOCIATION,
					EnumTransformation.DECOMPOSE_ATTRIBUTE
					));
	public static final Set<EnumTransformation> LIGHT_TRANSFORMATIONS = new HashSet<>(
			Arrays.asList(
					EnumTransformation.MOVE_ATTR_TO_INCIDENT_ENTITY_SET,
					EnumTransformation.MOVE_ATTR_TO_INCIDENT_ASSOCIATION,
					EnumTransformation.GENERALIZATION_TO_11_ASSOCIATION,
					EnumTransformation._11_ASSOCIATION_TO_GENERALIZATION,
					EnumTransformation.REBIND_NARY_ASSOCIATION,
					EnumTransformation.CREATE_ATTRIBUTE,
					EnumTransformation.REMOVE_ATTRIBUTE,
					EnumTransformation.CHANGE_CARDINALITY,
					EnumTransformation.RENAME_ATTRIBUTE
					));
	public static final Set<EnumTransformation> MEDIUM_TRANSFORMATIONS = new HashSet<>(
			Arrays.asList(
					EnumTransformation.CREATE_ASSOCIATION,
					EnumTransformation.CREATE_GENERALIZATION,
					EnumTransformation.REMOVE_ASSOCIATION,
					EnumTransformation.REMOVE_GENERALIZATION
					));
	public static final Set<EnumTransformation> HEAVY_TRANSFORMATIONS = new HashSet<>(
			Arrays.asList(
					EnumTransformation.CREATE_ENTITY_SET,
					EnumTransformation.REMOVE_ENTITY_SET,
					EnumTransformation.RENAME_ENTITY_SET
					));
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
					EnumTransformation.MOVE_ATTR_TO_INCIDENT_ENTITY_SET,
					EnumTransformation.MOVE_ATTR_TO_INCIDENT_ASSOCIATION,
					EnumTransformation.GENERALIZATION_TO_11_ASSOCIATION,
					EnumTransformation.CONTRACT_11_ASSOCIATION,
					EnumTransformation.UNCONTRACT_11_ASSOCIATION,
					EnumTransformation.REBIND_NARY_ASSOCIATION,
					EnumTransformation.BIND_TO_NARY_ASSOCIATION
					));
	
}
