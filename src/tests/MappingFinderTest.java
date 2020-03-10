package tests;

import comparing.Mapping;
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

        Mapping bestMapping = finder.getBestMapping(exemplarModel,studentModel);

        bestMapping.print();
    }
}