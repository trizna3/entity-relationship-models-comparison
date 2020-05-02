package tests;

import comparing.Mapping;
import entityRelationshipModel.EntityRelationshipModel;
import mappingSearch.mappingEvaluator.IMappingTransformationEvaluator;
import mappingSearch.mappingEvaluator.MappingTransformationEvaluator;
import org.junit.Test;
import transformations.types.Transformation;

import java.util.List;

/**
 * @author - Adam Trizna
 */
class IMappingTransformationEvaluatorTest {

    private IMappingTransformationEvaluator getEvaluator() {
        return new MappingTransformationEvaluator();
    }

    @Test
    void getTransformationList() {

        EntityRelationshipModel exemplarModel = TestUtils.getModel3();
        EntityRelationshipModel studentsModel = TestUtils.getModel4();
        Mapping mapping = TestUtils.getMapping2();

        List<Transformation> transformationList = getEvaluator().getTransformationList(exemplarModel,studentsModel,mapping);

        System.out.println("ALL TRANSFORMATIONS:");
        for (Transformation transformation : transformationList) {
            System.out.println(transformation);
        }
        System.out.println("\n\n");
        for (Transformation transformation : transformationList) {
            System.out.println("executing: " + transformation);
            transformation.doTransformation(studentsModel);
        }
    }
}