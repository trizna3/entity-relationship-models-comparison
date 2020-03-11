package mappingSearch.mappingEvaluator;

import comparing.Mapping;
import entityRelationshipModel.EntityRelationshipModel;

/**
 * @author - Adam Trizna
 */

/**
 * Object for mapping evaluation.
 * This kind of evaluator computes mapping penalty and keeps record of mapping with minimal penalty.
 */
public interface IBestMappingEvaluator {

    /**
     * Evaluates given mapping.
     * @param exemplarModel
     * @param studentModel
     * @param mapping
     */
    void evaluate(EntityRelationshipModel exemplarModel, EntityRelationshipModel studentModel, Mapping mapping);

    /**
     * @return Mapping with minimal penalty evaluated so far.
     */
    Mapping getBestMapping();

    /**
     * @return Penalty value of the best mapping.
     */
    double getBestMappingPenalty();
}
