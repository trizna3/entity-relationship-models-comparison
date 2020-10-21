package transformations;

import java.util.ArrayList;
import java.util.List;

import common.TransformationAnalystUtils;
import common.objectPools.TransformationPool;
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

//		getPossibleExtractAttributeToOwnEntitySetTransformations(mapping,result);
		getPossibleMoveAttributeToIncidentEntitySetTransformations(mapping,result);
		getPossibleMoveAttributeToIncidentAssociationTransformations(mapping,result);
		getPossibleRebindMNTo1NN1Transformations(mapping,result);
		getPossibleRebind1NN1ToMNTransformations(mapping,result);
		getPossibleGeneralizationToAssociationTransformations(mapping,result);
		getPossibleContract11AssociationTransformations(mapping,result);
		getPossibleRebindNaryAssociationTransformations(mapping,result);

		return result;
	}
	
	public static void freeTransformations(List<Transformation> transformations) {
		transformations.forEach(transformation -> TransformationPool.getInstance().freeTransformation(transformation));		
	}

	private static void getPossibleExtractAttributeToOwnEntitySetTransformations(Mapping mapping, List<Transformation> result) {
		TransformationAnalystUtils.getPossibleExtractAttributeToOwnEntitySetTransformations(result, mapping.getStudentModel());
	}

	private static void getPossibleMoveAttributeToIncidentEntitySetTransformations(Mapping mapping, List<Transformation> result) {
		TransformationAnalystUtils.getPossibleMoveAttributeToIncidentEntitySetTransformations(result, mapping.getStudentModel());
	}

	private static void getPossibleMoveAttributeToIncidentAssociationTransformations(Mapping mapping, List<Transformation> result) {
		TransformationAnalystUtils.getPossibleMoveAttributeToIncidentAssociationTransformations(result, mapping.getStudentModel());
	}

	private static void getPossibleRebindMNTo1NN1Transformations(Mapping mapping, List<Transformation> result) {
		TransformationAnalystUtils.getPossibleRebindMNTo1NN1Transformations(result, mapping.getStudentModel());
	}

	private static void getPossibleRebind1NN1ToMNTransformations(Mapping mapping, List<Transformation> result) {
		TransformationAnalystUtils.getPossibleRebind1NN1ToMNTransformations(result, mapping.getStudentModel());
	}

	private static void getPossibleGeneralizationToAssociationTransformations(Mapping mapping, List<Transformation> result) {
		TransformationAnalystUtils.getPossibleGeneralizationToAssociationTransformations(result, mapping.getStudentModel());
	}

	private static void getPossibleContract11AssociationTransformations(Mapping mapping, List<Transformation> result) {
		TransformationAnalystUtils.getPossibleContract11AssociationTransformations(result, mapping.getExemplarModel());
		TransformationAnalystUtils.getPossibleContract11AssociationTransformations(result, mapping.getStudentModel());
	}

	private static void getPossibleRebindNaryAssociationTransformations(Mapping mapping, List<Transformation> result) {
		TransformationAnalystUtils.getPossibleRebindNaryAssociationTransformations(result, mapping.getStudentModel());
		TransformationAnalystUtils.getPossibleRebindNaryAssociationTransformations(result, mapping.getExemplarModel());
	}
}
