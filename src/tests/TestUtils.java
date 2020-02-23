package tests;

import comparing.Mapping;
import entityRelationshipModel.*;

import java.util.*;

/**
 * @author - Adam Trizna
 */

public class TestUtils {

    private final static List<String> ENTITY_SET_NAMES_1 = new ArrayList<>(Arrays.asList("employees","jobs","job_history","departments"));
    private final static List<String> ENTITY_SET_NAMES_2 = new ArrayList<>(Arrays.asList("people","position_history","areas"));

    private final static Set<String[]> RELATIONSHIPS_1 = new HashSet<>();
        static {
            RELATIONSHIPS_1.add(new String[]{"employees","employees","1","*"});
            RELATIONSHIPS_1.add(new String[]{"employees","jobs","1","*"});
            RELATIONSHIPS_1.add(new String[]{"employees","departments","1","*"});
            RELATIONSHIPS_1.add(new String[]{"job_history","jobs","1","*"});
    }
    private final static Set<String[]> RELATIONSHIPS_2 = new HashSet<>();
    static {
        RELATIONSHIPS_2.add(new String[]{"people","people","1","*"});
        RELATIONSHIPS_2.add(new String[]{"people","areas","1","*"});
        RELATIONSHIPS_2.add(new String[]{"people","position_history","*","*"});
    }

    private final static Map<String,String> MAPPING = new HashMap<>();
    static {
        MAPPING.put("employees","people");
        MAPPING.put("job_history","position_history");
        MAPPING.put("departments","areas");
    }

    public static List<String> getEntitySetNames1() {
        return ENTITY_SET_NAMES_1;
    }

    private static Map<String,EntitySet> entitySets1;
    private static Map<String,EntitySet> entitySets2;

    public static List<String> getEntitySetNames2() {
        return ENTITY_SET_NAMES_2;
    }

    public static Set<String[]> getRelationships1() {
        return RELATIONSHIPS_1;
    }

    public static Set<String[]> getRelationships2() {
        return RELATIONSHIPS_2;
    }

    public static Cardinality getRandomCardinality() {
        Random r = new Random();
        if (r.nextBoolean()) {
            return Cardinality.ONE;
        }
        return Cardinality.MANY;
    }

    public static EntityRelationshipModel getModel1() {
        return makeModel(getEntitySets1(),getRelationships1());
    }

    public static EntityRelationshipModel getModel2() {
        return makeModel(getEntitySets2(),getRelationships2());
    }

    public static EntityRelationshipModel makeModel(Map<String, EntitySet> entitySets, Set<String[]> relationships){
        EntityRelationshipModel model = new EntityRelationshipModel();

        for (EntitySet entitySet : entitySets.values()) {
            model.addEntitySet(entitySet);
        }
        for (String[] rel : relationships) {
            AssociationSide as1 = new AssociationSide(entitySets.get(rel[0]),convertToCardinality(rel[2]));
            AssociationSide as2 = new AssociationSide(entitySets.get(rel[1]),convertToCardinality(rel[3]));
            model.addRelationship(new Association(Arrays.asList(as1,as2),new ArrayList<>()));
        }

        return model;
    }

    private static Cardinality convertToCardinality(String symbol){
        if ("1".equals(symbol)) {
            return Cardinality.ONE;
        }
        if ("*".equals(symbol)) {
            return Cardinality.MANY;
        }
        return null;
    }

    public static Map<String,EntitySet> getEntitySets1() {
        if (entitySets1 == null) {
            entitySets1 = makeEntitySets(getEntitySetNames1());
        }
        return entitySets1;
    }

    public static Map<String,EntitySet> getEntitySets2() {
        if (entitySets2 == null) {
            entitySets2 = makeEntitySets(getEntitySetNames2());
        }
        return entitySets2;
    }

    public static Map<String,EntitySet> makeEntitySets(List<String> names) {
        Map<String,EntitySet> entitySets = new HashMap<>();
        for (String name : names) {
            entitySets.put(name, new EntitySet(name));
        }
        return entitySets;
    }

    public static Mapping getMapping() {
        Mapping myMapping = new Mapping();
        for (String key : MAPPING.keySet()) {
            myMapping.map(getEntitySets1().get(key),getEntitySets2().get(MAPPING.get(key)));
        }
        return myMapping;
    }
}
