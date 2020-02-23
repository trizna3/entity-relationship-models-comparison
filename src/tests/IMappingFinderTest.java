package tests;

import comparing.Mapping;
import mappingSearch.IMappingFinder;
import mappingSearch.MappingFinder;
import entityRelationshipModel.EntityRelationshipModel;
import org.junit.jupiter.api.Test;

/**
 * @author - Adam Trizna
 */
class IMappingFinderTest {

    @Test
    void getBestMapping() {
        EntityRelationshipModel exemplarModel = TestUtils.getModel1();
        EntityRelationshipModel studentModel = TestUtils.getModel2();
        IMappingFinder finder = new MappingFinder();

        Mapping bestMapping = finder.getBestMapping(exemplarModel,studentModel);

        bestMapping.print();
    }
}