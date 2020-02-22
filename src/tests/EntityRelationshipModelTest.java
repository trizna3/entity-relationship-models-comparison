package tests;

import comparing.Mapping;
import entityRelationshipModel.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class EntityRelationshipModelTest {

    @org.junit.jupiter.api.Test
    void getEntitySets() {
        EntityRelationshipModel model1 = getModel1();
        EntityRelationshipModel model2 = getModel2();

        assertEquals(model1.getEntitySets().size(),getEntitySets1().size());
        assertEquals(model1.getRelationships().size(),getRelationships1().size());

        assertEquals(model2.getEntitySets().size(),getEntitySets2().size());
        assertEquals(model2.getRelationships().size(),getRelationships2().size());
    }

    @org.junit.jupiter.api.Test
    void getRelationships() {
    }

    @org.junit.jupiter.api.Test
    void addEntitySet() {
    }

    @org.junit.jupiter.api.Test
    void addRelationship() {
    }

    private EntityRelationshipModel getModel1() {
        return makeModel(getEntitySets1(),getRelationships1());
    }

    private EntityRelationshipModel getModel2() {
        return makeModel(getEntitySets2(),getRelationships2());
    }

    private EntityRelationshipModel makeModel(Map<String,EntitySet> entitySets, Map<String,String> relationships){
        EntityRelationshipModel model = new EntityRelationshipModel();

        for (EntitySet entitySet : entitySets.values()) {
            model.addEntitySet(entitySet);
        }
        for (String name : relationships.keySet()) {
            AssociationSide as1 = new AssociationSide(entitySets.get(name),TestUtils.getRandomCardinality());
            AssociationSide as2 = new AssociationSide(entitySets.get(relationships.get(name)),TestUtils.getRandomCardinality());
            model.addRelationship(new Association(Arrays.asList(as1,as2),new ArrayList<>()));
        }

        return model;
    }

    private Map<String,EntitySet> getEntitySets1() {
        return makeEntitySets(TestUtils.getEntitySetNames1());
    }

    private Map<String,EntitySet> getEntitySets2() {
        return makeEntitySets(TestUtils.getEntitySetNames2());
    }

    private Map<String,String> getRelationships1() {
        return TestUtils.getRelationships1();
    }

    private Map<String,String> getRelationships2() {
        return TestUtils.getRelationships2();
    }

    private Map<String,EntitySet> makeEntitySets(List<String> names) {
        Map<String,EntitySet> entitySets = new HashMap<>();
        for (String name : names) {
            entitySets.put(name, new EntitySet(name));
        }
        return entitySets;
    }

    private Mapping getMapping() {
        Mapping myMapping = new Mapping();
        Map<String,String> mapping = TestUtils.getMapping();
        for (String key : mapping.keySet()) {
            myMapping.map(getEntitySets1().get(key),getEntitySets2().get(mapping.get(key)));
        }
        return myMapping;
    }
}