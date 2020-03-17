package tests;

import comparing.Mapping;
import entityRelationshipModel.EntityRelationshipModel;
import entityRelationshipModel.EntitySet;
import mappingSearch.mappingEvaluator.IMappingTransformationEvaluator;
import mappingSearch.mappingEvaluator.MappingTransformationEvaluator;
import org.junit.jupiter.api.Test;
import transformations.types.Transformation;
import transformations.types.Transformation_AddAttribute;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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
        System.out.println();
        System.out.println();
        for (Transformation transformation : transformationList) {
            System.out.println("executing: " + transformation);
            transformation.doTransformation(studentsModel);
        }

        System.out.println();

        System.out.println("student entity sets:");
        System.out.println(studentsModel.getEntitySets());
        System.out.println("student relationships:");
        System.out.println(studentsModel.getRelationships());

//        Transformation tr = new Transformation_AddAttribute();
//        tr.setParameter(Transformation_AddAttribute.ATTRIBUTE_NAME,"newAttribute");
//        tr.setParameter(Transformation_AddAttribute.ENTITY_SET, new EntitySet("EntitySet"));

//        System.out.println(tr);
    }
}