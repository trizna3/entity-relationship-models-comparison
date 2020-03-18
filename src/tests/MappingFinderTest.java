package tests;

import comparing.Mapping;
import mappingSearch.mappingFinder.ComplexMappingFinder;
import mappingSearch.mappingFinder.IMappingFinder;
import mappingSearch.mappingFinder.BasicMappingFinder;
import entityRelationshipModel.EntityRelationshipModel;
import org.junit.jupiter.api.Test;

/**
 * @author - Adam Trizna
 */
class MappingFinderTest {

    @Test
    void getBestMapping() {
        EntityRelationshipModel exemplarModel = TestUtils.getModel1();
        EntityRelationshipModel studentModel = TestUtils.getModel2();

        IMappingFinder finder = new BasicMappingFinder();
        IMappingFinder finder2 = new ComplexMappingFinder();

        Mapping bestMapping = finder.getBestMapping(exemplarModel,studentModel);
        bestMapping.print();

        Mapping bestMapping2 = finder2.getBestMapping(exemplarModel,studentModel);
    }
}