package tests;

import comparing.Mapping;
import entityRelationshipModel.EntityRelationshipModel;
import evaluation.IEvaluator;
import evaluation.MainEvaluator;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author - Adam Trizna
 */

class IEvaluatorTest {

    @org.junit.jupiter.api.Test
    void evaluate() {
        EntityRelationshipModel model1 = TestUtils.getModel1();
        EntityRelationshipModel model2 = TestUtils.getModel2();
        Mapping mapping = TestUtils.getMapping();

        IEvaluator evaluator = new MainEvaluator();

        evaluator.evaluate(model1,model2,mapping);
    }
}