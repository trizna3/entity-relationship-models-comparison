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

//		result.addAll(getPossibleExtractAttributeToOwnEntitySetTransformations(mapping));
//		result.addAll(getPossibleMoveAttributeToIncidentEntitySetTransformations(mapping));
//		result.addAll(getPossibleMoveAttributeToIncidentAssociationTransformations(mapping));
//		result.addAll(getPossibleRebindMNTo1NN1Transformations(mapping));
		result.addAll(getPossibleRebind1NN1ToMNTransformations(mapping));
		result.addAll(getPossibleGeneralizationToAssociationTransformations(mapping));
		result.addAll(getPossibleContract11AssociationTransformations(mapping));
//		result.addAll(getPossibleRebindNaryAssociationTransformations(mapping));

		return result;
	}

	private static List<Transformation> getPossibleExtractAttributeToOwnEntitySetTransformations(Mapping mapping) {
		List<Transformation> result = new ArrayList<>();

		TransformationAnalystUtils.getPossibleExtractAttributeToOwnEntitySetTransformations(result, mapping.getStudentModel());

		return result;
	}

	private static List<Transformation> getPossibleMoveAttributeToIncidentEntitySetTransformations(Mapping mapping) {
		List<Transformation> result = new ArrayList<>();

		TransformationAnalystUtils.getPossibleMoveAttributeToIncidentEntitySetTransformations(result, mapping.getStudentModel());

		return result;
	}

	private static List<Transformation> getPossibleMoveAttributeToIncidentAssociationTransformations(Mapping mapping) {
		List<Transformation> result = new ArrayList<>();

		TransformationAnalystUtils.getPossibleMoveAttributeToIncidentAssociationTransformations(result, mapping.getStudentModel());

		return result;
	}

	private static List<Transformation> getPossibleRebindMNTo1NN1Transformations(Mapping mapping) {
		List<Transformation> result = new ArrayList<>();

		TransformationAnalystUtils.getPossibleRebindMNTo1NN1Transformations(result, mapping.getStudentModel());

		return result;
	}

	private static List<Transformation> getPossibleRebind1NN1ToMNTransformations(Mapping mapping) {
		List<Transformation> result = new ArrayList<>();

		TransformationAnalystUtils.getPossibleRebind1NN1ToMNTransformations(result, mapping.getStudentModel());

		return result;
	}

	private static List<Transformation> getPossibleGeneralizationToAssociationTransformations(Mapping mapping) {
		List<Transformation> result = new ArrayList<>();

		TransformationAnalystUtils.getPossibleGeneralizationToAssociationTransformations(result, mapping.getStudentModel());

		return result;
	}

	private static List<Transformation> getPossibleContract11AssociationTransformations(Mapping mapping) {
		List<Transformation> result = new ArrayList<>();

		TransformationAnalystUtils.getPossibleContract11AssociationTransformations(result, mapping.getExemplarModel());
		TransformationAnalystUtils.getPossibleContract11AssociationTransformations(result, mapping.getStudentModel());

		return result;
	}

	private static List<Transformation> getPossibleRebindNaryAssociationTransformations(Mapping mapping) {
		List<Transformation> result = new ArrayList<>();

		TransformationAnalystUtils.getPossibleRebindNaryAssociationTransformations(result, mapping.getExemplarModel());

		return result;
	}
}
