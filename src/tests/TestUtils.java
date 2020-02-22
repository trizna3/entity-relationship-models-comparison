package tests;

import entityRelationshipModel.Cardinality;

import java.util.*;

public class TestUtils {

    private final static List<String> ENTITY_SET_NAMES_1 = new ArrayList<>(Arrays.asList("employees","jobs","job_history","departments"));
    private final static List<String> ENTITY_SET_NAMES_2 = new ArrayList<>(Arrays.asList("people","positions","position_history","areas"));

    private final static Map<String,String> RELATIONSHIPS_1 = new HashMap<>();
    static {
        RELATIONSHIPS_1.put("employees","employees");
        RELATIONSHIPS_1.put("employees","jobs");
        RELATIONSHIPS_1.put("employees","job_history");
        RELATIONSHIPS_1.put("employees","departments");
        RELATIONSHIPS_1.put("job","job_history");
    }
    private final static Map<String,String> RELATIONSHIPS_2 = new HashMap<>();
    static {
        RELATIONSHIPS_2.put("people","people");
        RELATIONSHIPS_2.put("people","positions");
        RELATIONSHIPS_2.put("people","areas");
        RELATIONSHIPS_2.put("positions","position_history");
    }

    private final static Map<String,String> MAPPING = new HashMap<>();
    static {
        MAPPING.put("employees","people");
        MAPPING.put("jobs","positions");
        MAPPING.put("job_history","position_history");
        MAPPING.put("departments","areas");
    }

    public static List<String> getEntitySetNames1() {
        return ENTITY_SET_NAMES_1;
    }

    public static List<String> getEntitySetNames2() {
        return ENTITY_SET_NAMES_2;
    }

    public static Map<String, String> getRelationships1() {
        return RELATIONSHIPS_1;
    }

    public static Map<String, String> getRelationships2() {
        return RELATIONSHIPS_2;
    }

    public static Cardinality getRandomCardinality() {
        Random r = new Random();
        if (r.nextBoolean()) {
            return Cardinality.ONE;
        }
        return Cardinality.MANY;
    }

    public static Map<String, String> getMapping() {
        return MAPPING;
    }
}
