package mappingSearch.mappingEvaluator;

/**
 * @author - Adam Trizna
 */

import comparing.Mapping;
import entityRelationshipModel.ERModel;
import transformations.types.Transformation;

import java.util.List;

/**
 * Object for mapping evaluation.
 * This kind of evaluator computes a list of transformations from given mapping and entity-relationship models.
 */
public interface IMappingTransformationEvaluator {

    /**
     * @return list of transformations made to the students model, so it's structurally equal to the exemplar model in the given entity set mapping.
     */
    List<Transformation> getTransformationList(ERModel exemplarModel, ERModel studentsModel, Mapping mapping);
}
