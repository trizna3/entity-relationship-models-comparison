package tests;

import entityRelationshipModel.*;

import static org.junit.Assert.*;
import org.junit.Test;

/**
 * @author - Adam Trizna
 */

class EntityRelationshipModelTest {

	@Test
    void getEntitySets() {
        EntityRelationshipModel model1 = TestUtils.getModel1();
        EntityRelationshipModel model2 = TestUtils.getModel2();

        assertEquals(model1.getEntitySets().size(),TestUtils.getEntitySets1().size());
        assertEquals(model2.getEntitySets().size(),TestUtils.getEntitySets2().size());
    }

	@Test
    void getRelationships() {
        EntityRelationshipModel model1 = TestUtils.getModel1();
        EntityRelationshipModel model2 = TestUtils.getModel2();

        assertEquals(model1.getRelationships().size(),TestUtils.getRelationships1().size());
        assertEquals(model2.getRelationships().size(),TestUtils.getRelationships2().size());
    }


}