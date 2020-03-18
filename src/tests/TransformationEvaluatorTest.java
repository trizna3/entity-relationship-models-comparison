package tests;

import comparing.Mapping;
import entityRelationshipModel.EntityRelationshipModel;
import evaluation.TransformationEvaluator;
import mappingSearch.mappingEvaluator.IMappingTransformationEvaluator;
import mappingSearch.mappingEvaluator.MappingTransformationEvaluator;
import org.junit.jupiter.api.Test;
import transformations.types.Transformation;

import java.util.List;

/**
 * @author - Adam Trizna
 */
class TransformationEvaluatorTest {

    IMappingTransformationEvaluator mapping2TransformationEvaluator;
    TransformationEvaluator transformationEvaluator;

    private IMappingTransformationEvaluator getMapping2TransformationEvaluator() {
        if (mapping2TransformationEvaluator == null) {
            mapping2TransformationEvaluator = new MappingTransformationEvaluator();
        }
        return mapping2TransformationEvaluator;
    }

    private TransformationEvaluator getTransformationEvaluator() {
        if (transformationEvaluator == null) {
            transformationEvaluator = new TransformationEvaluator(1);
        }
        return transformationEvaluator;
    }

    @Test
    void evaluate() {
        EntityRelationshipModel exemplarModel = TestUtils.getModel3();
        EntityRelationshipModel studentsModel = TestUtils.getModel4();
        Mapping mapping = TestUtils.getMapping2();

        List<Transformation> transformationList = getMapping2TransformationEvaluator().getTransformationList(exemplarModel,studentsModel,mapping);
        mapping.setTransformations(transformationList);
        double penalty = getTransformationEvaluator().evaluate(exemplarModel,studentsModel,mapping);

        System.out.println("penalty = " + penalty);
    }
}