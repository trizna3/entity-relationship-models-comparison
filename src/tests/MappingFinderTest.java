package tests;

import comparing.Mapping;
import mappingSearch.mappingFinder.ComplexMappingFinder;
import mappingSearch.mappingFinder.IMappingFinder;
import mappingSearch.mappingFinder.BasicMappingFinder;
import entityRelationshipModel.ERModel;
import org.junit.Test;

/**
 * @author - Adam Trizna
 */
class MappingFinderTest {

    @Test
    void getBestMapping() {
        ERModel exemplarModel = TestUtils.getModel1();
        ERModel studentModel = TestUtils.getModel2();

        IMappingFinder finder = new BasicMappingFinder();
        IMappingFinder finder2 = new ComplexMappingFinder();

        Mapping bestMapping = finder.getBestMapping(exemplarModel,studentModel);
        bestMapping.print();

        Mapping bestMapping2 = finder2.getBestMapping(exemplarModel,studentModel);
    }
}