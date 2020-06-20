package evaluation;

import java.util.HashMap;
import java.util.Map;

import comparing.Mapping;
import entityRelationshipModel.ERModel;
import transformations.types.Transformation;
import transformations.types.Transformation_AddAssociation;
import transformations.types.Transformation_AddAttribute;
import transformations.types.Transformation_AddEntitySet;
import transformations.types.Transformation_AddGeneralization;
import transformations.types.Transformation_RemoveAssociation;
import transformations.types.Transformation_RemoveAttribute;
import transformations.types.Transformation_RemoveEntitySet;
import transformations.types.Transformation_RemoveGeneralization;

/**
 * @author - Adam Trizna
 */
public class TransformationEvaluator implements IModelEvaluator {

	double WEIGHT;

	public TransformationEvaluator(double WEIGHT) {
		this.WEIGHT = WEIGHT;
	}

	private static final Map<String, Double> transformationPenalties = new HashMap<>();
	static {
		transformationPenalties.put(Transformation.CODE_ADD_ASSOCIATION, 1d);
		transformationPenalties.put(Transformation.CODE_ADD_GENERALIZATION, 1d);
		transformationPenalties.put(Transformation.CODE_ADD_ENTITY_SET, 1d);
		transformationPenalties.put(Transformation.CODE_ADD_ATTRIBUTE, 1d);
		transformationPenalties.put(Transformation.CODE_REMOVE_ASSOCIATION, 1d);
		transformationPenalties.put(Transformation.CODE_REMOVE_GENERALIZATION, 1d);
		transformationPenalties.put(Transformation.CODE_REMOVE_ENTITY_SET, 1d);
		transformationPenalties.put(Transformation.CODE_REMOVE_ATTRIBUTE, 1d);
	}

	public Map<String, Double> getTransformationPenalties() {
		return transformationPenalties;
	}

	/**
	 * @param exemplarModel
	 * @param studentsModel
	 * @param mapping
	 * @return Penalty value for used transformations
	 */
	@Override
	public double evaluate(ERModel exemplarModel, ERModel studentsModel, Mapping mapping) {

		throw new UnsupportedOperationException();
	}

	/**
	 * Computes penalty for used transformation, based on transformation type.
	 * 
	 * @param transformation
	 * @return penalty value
	 */
	private double penalizeTransformation(Transformation transformation) {

		if (transformation instanceof Transformation_AddAssociation) {
			return transformationPenalties.get(Transformation.CODE_ADD_ASSOCIATION);
		} else if (transformation instanceof Transformation_AddGeneralization) {
			return transformationPenalties.get(Transformation.CODE_ADD_GENERALIZATION);
		} else if (transformation instanceof Transformation_AddEntitySet) {
			return transformationPenalties.get(Transformation.CODE_ADD_ENTITY_SET);
		} else if (transformation instanceof Transformation_AddAttribute) {
			return transformationPenalties.get(Transformation.CODE_ADD_ATTRIBUTE);
		} else if (transformation instanceof Transformation_RemoveAssociation) {
			return transformationPenalties.get(Transformation.CODE_REMOVE_ASSOCIATION);
		} else if (transformation instanceof Transformation_RemoveGeneralization) {
			return transformationPenalties.get(Transformation.CODE_REMOVE_GENERALIZATION);
		} else if (transformation instanceof Transformation_RemoveEntitySet) {
			return transformationPenalties.get(Transformation.CODE_REMOVE_ENTITY_SET);
		} else if (transformation instanceof Transformation_RemoveAttribute) {
			return transformationPenalties.get(Transformation.CODE_REMOVE_ATTRIBUTE);
		} else {
			throw new IllegalArgumentException("unknown transformation");
		}
	}
}
