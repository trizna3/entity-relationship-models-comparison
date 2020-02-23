package tests;

import comparing.Mapping;
import comparing.mappingSearch.IMappingFinder;
import comparing.mappingSearch.MappingFinder;
import entityRelationshipModel.EntityRelationshipModel;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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