package transformations;

import java.util.ArrayList;
import java.util.List;

import common.TransformationAnalystUtils;
import comparing.Mapping;

/**
 * @author - Adam Trizna
 */

/**
 * Object analyzing entity sets mapping between two entity relationship models.
 * Checks what transformations could be made in order to unify exemplar and
 * student's models structure.
 */
public class TransformationAnalyst {

	/**
	 * Returns list of possible transformations.
	 * 
	 * @param mapping
	 * @return
	 */
	public static List<Transformation> getPossibleTransformations(Mapping mapping) {
		List<Transformation> result = new ArrayList<>();

		result.addAll(getPossibleExtractAttributeToOwnEntitySetTransformations(mapping));
		result.addAll(getPossibleMoveAttributeToIncidentEntitySetTransformations(mapping));
		result.addAll(getPossibleMoveAttributeToIncidentAssociationTransformations(mapping));
		result.addAll(getPossibleRebindMNTo1NN1Transformations(mapping));
		result.addAll(getPossibleRebind1NN1ToMNTransformations(mapping));
		result.addAll(getPossibleGeneralizationToAssociationTransformations(mapping));
		result.addAll(getPossibleContract11AssociationTransformations(mapping));
		result.addAll(getPossibleRebindNaryAssociationTransformations(mapping));

		return result;
	}

	private static List<Transformation> getPossibleExtractAttributeToOwnEntitySetTransformations(Mapping mapping) {
//		EXTRACT_ATTR_TO_OWN_ENTITY_SET - ATTRIBUTE,SOURCE_ENTITY_SET,DEST_ENTITY_SET - ATTRIBUTE,DEST_ENTITY_SET,SOURCE_ENTITY_SET

		// unsupported yet
		return new ArrayList<>();
	}

	private static List<Transformation> getPossibleMoveAttributeToIncidentEntitySetTransformations(Mapping mapping) {
//		MOVE_ATTR_TO_INCIDENT_ENTITY_SET - ATTRIBUTE,ASSOCIATION,ENTITY_SET - ATTRIBUTE,ASSOCIATION,ENTITY_SET

		// unsupported yet
		return new ArrayList<>();
	}

	private static List<Transformation> getPossibleMoveAttributeToIncidentAssociationTransformations(Mapping mapping) {
//		MOVE_ATTR_TO_INCIDENT_ASSOCIATION - ATTRIBUTE,ENTITY_SET,ASSOCIATION - ATTRIBUTE,ASSOCIATION,ENTITY_SET

		// unsupported yet
		return new ArrayList<>();
	}

	private static List<Transformation> getPossibleRebindMNTo1NN1Transformations(Mapping mapping) {
//		REBIND_MN_TO_1NN1 - ASSOCIATION - ASSOCIATION_1,ASSOCIATION_2,ENTITY_SET

		// unsupported yet
		return new ArrayList<>();
	}

	private static List<Transformation> getPossibleRebind1NN1ToMNTransformations(Mapping mapping) {
//		REBIND_1NN1_TO_MN - ASSOCIATION_1,ASSOCIATION_2,ENTITY_SET - ASSOCIATION

		// unsupported yet
		return new ArrayList<>();
	}

	private static List<Transformation> getPossibleGeneralizationToAssociationTransformations(Mapping mapping) {
//		GENERALIZATION_TO_11_ASSOCIATION - GENERALIZATION - ASSOCIATION, GENERALIZATION

		// unsupported yet
		return new ArrayList<>();
	}

	private static List<Transformation> getPossibleContract11AssociationTransformations(Mapping mapping) {
		List<Transformation> result = new ArrayList<>();

		TransformationAnalystUtils.getPossibleContract11AssociationTransformations(result, mapping.getExemplarModel());
		TransformationAnalystUtils.getPossibleContract11AssociationTransformations(result, mapping.getStudentModel());

		return result;
	}

	private static List<Transformation> getPossibleRebindNaryAssociationTransformations(Mapping mapping) {
//		REBIND_NARY_ASSOCIATION - ASSOCIATION - ENTITY_SET, ASSOCIATION

		// unsupported yet
		return new ArrayList<>();
	}
}
