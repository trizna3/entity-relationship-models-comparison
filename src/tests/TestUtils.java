package tests;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import common.Enums;
import comparing.Mapping;
import entityRelationshipModel.Association;
import entityRelationshipModel.AssociationSide;
import entityRelationshipModel.ERModel;
import entityRelationshipModel.EntitySet;

/**
 * @author - Adam Trizna
 */

public class TestUtils {

	private final static List<String> ENTITY_SET_NAMES_1 = new ArrayList<>(Arrays.asList("employees", "jobs", "job_history", "departments"));
	private final static List<String> ENTITY_SET_NAMES_2 = new ArrayList<>(Arrays.asList("people", "position_history", "areas"));
	private final static List<String> ENTITY_SET_NAMES_3 = new ArrayList<>(Arrays.asList("AA1", "BB1", "C1"));
	private final static List<String> ENTITY_SET_NAMES_4 = new ArrayList<>(Arrays.asList("AA2", "AB2", "BB2", "D2"));

	private final static Map<String, String> ATTRIBUTES_3 = new HashMap<>();
	static {
		ATTRIBUTES_3.put("C1", "c");
	}
	private final static Map<String, String> ATTRIBUTES_4 = new HashMap<>();
	static {
		ATTRIBUTES_4.put("AA2", "c");
	}

	private final static Set<String[]> RELATIONSHIPS_1 = new HashSet<>();
	static {
		RELATIONSHIPS_1.add(new String[] { "employees", "employees", "1", "*" });
		RELATIONSHIPS_1.add(new String[] { "employees", "jobs", "1", "*" });
		RELATIONSHIPS_1.add(new String[] { "employees", "departments", "1", "*" });
		RELATIONSHIPS_1.add(new String[] { "job_history", "jobs", "1", "*" });
	}
	private final static Set<String[]> RELATIONSHIPS_2 = new HashSet<>();
	static {
		RELATIONSHIPS_2.add(new String[] { "people", "people", "1", "*" });
		RELATIONSHIPS_2.add(new String[] { "people", "areas", "1", "*" });
		RELATIONSHIPS_2.add(new String[] { "people", "position_history", "*", "*" });
	}

	private final static Set<String[]> RELATIONSHIPS_3 = new HashSet<>();
	static {
		RELATIONSHIPS_3.add(new String[] { "AA1", "BB1", "*", "*" });
		RELATIONSHIPS_3.add(new String[] { "AA1", "C1", "1", "1" });
	}

	private final static Set<String[]> RELATIONSHIPS_4 = new HashSet<>();
	static {
		RELATIONSHIPS_4.add(new String[] { "AA2", "AB2", "1", "*" });
		RELATIONSHIPS_4.add(new String[] { "BB2", "AB2", "1", "*" });
		RELATIONSHIPS_4.add(new String[] { "BB2", "D2", "1", "1" });
	}

	private final static Map<String, String> MAPPING = new HashMap<>();
	static {
		MAPPING.put("employees", "people");
		MAPPING.put("job_history", "position_history");
		MAPPING.put("departments", "areas");
	}

	private final static Map<String, String> MAPPING_2 = new HashMap<>();
	static {
		MAPPING_2.put("AA1", "AA2");
		MAPPING_2.put("BB1", "BB2");
	}

	public static List<String> getEntitySetNames1() {
		return ENTITY_SET_NAMES_1;
	}

	private static Map<String, EntitySet> entitySets1;
	private static Map<String, EntitySet> entitySets2;
	private static Map<String, EntitySet> entitySets3;
	private static Map<String, EntitySet> entitySets4;

	public static List<String> getEntitySetNames2() {
		return ENTITY_SET_NAMES_2;
	}

	public static Set<String[]> getRelationships1() {
		return RELATIONSHIPS_1;
	}

	public static Set<String[]> getRelationships2() {
		return RELATIONSHIPS_2;
	}

	public static String getRandomCardinality() {
		Random r = new Random();
		if (r.nextBoolean()) {
			return Enums.CARDINALITY_ONE;
		}
		return Enums.CARDINALITY_MANY;
	}

	public static ERModel getModel1() {
		return makeModel(getEntitySets1(), getRelationships1());
	}

	public static ERModel getModel2() {
		return makeModel(getEntitySets2(), getRelationships2());
	}

	public static ERModel makeModel(Map<String, EntitySet> entitySets, Set<String[]> relationships) {
		ERModel model = new ERModel();

		for (EntitySet entitySet : entitySets.values()) {
			model.addEntitySet(entitySet);
		}
		for (String[] rel : relationships) {
			AssociationSide as1 = new AssociationSide(entitySets.get(rel[0]), convertToCardinality(rel[2]));
			AssociationSide as2 = new AssociationSide(entitySets.get(rel[1]), convertToCardinality(rel[3]));
			model.addRelationship(new Association(new AssociationSide[] { as1, as2 }, new String[] {}));
		}

		return model;
	}

	private static String convertToCardinality(String symbol) {
		if ("1".equals(symbol)) {
			return Enums.CARDINALITY_ONE;
		}
		if ("*".equals(symbol)) {
			return Enums.CARDINALITY_MANY;
		}
		return null;
	}

	public static Map<String, EntitySet> getEntitySets1() {
		if (entitySets1 == null) {
			entitySets1 = makeEntitySets(getEntitySetNames1());
		}
		return entitySets1;
	}

	public static Map<String, EntitySet> getEntitySets2() {
		if (entitySets2 == null) {
			entitySets2 = makeEntitySets(getEntitySetNames2());
		}
		return entitySets2;
	}

	public static Map<String, EntitySet> getEntitySets3() {
		if (entitySets3 == null) {
			entitySets3 = makeEntitySets(getEntitySetNames3());
			for (String esName : entitySets3.keySet()) {
//				if (ATTRIBUTES_3.get(esName) != null) {
//					entitySets3.get(esName).getAttributes().add(ATTRIBUTES_3.get(esName));
//				}
			}
		}
		return entitySets3;
	}

	public static Map<String, EntitySet> getEntitySets4() {
		if (entitySets4 == null) {
			entitySets4 = makeEntitySets(getEntitySetNames4());
			for (String esName : entitySets4.keySet()) {
//				if (ATTRIBUTES_4.get(esName) != null) {
//					entitySets4.get(esName).getAttributes().add(ATTRIBUTES_4.get(esName));
//				}
			}
		}
		return entitySets4;
	}

	public static Map<String, EntitySet> makeEntitySets(List<String> names) {
		Map<String, EntitySet> entitySets = new HashMap<>();
		for (String name : names) {
			entitySets.put(name, new EntitySet(name));
		}
		return entitySets;
	}

	public static Mapping getMapping1() {
		Mapping myMapping = new Mapping();
		for (String key : MAPPING.keySet()) {
			myMapping.map(getEntitySets1().get(key), getEntitySets2().get(MAPPING.get(key)));
		}
		for (EntitySet es : getEntitySets1().values()) {
			if (myMapping.getImage(es) == null) {
				EntitySet ee = new EntitySet("empty for " + es.getName());
				ee.setEmpty(true);
				myMapping.map(es, ee);
			}
		}
		for (EntitySet es : getEntitySets2().values()) {
			if (myMapping.getImage(es) == null) {
				EntitySet ee = new EntitySet("empty for " + es.getName());
				ee.setEmpty(true);
				myMapping.map(es, ee);
			}
		}
		return myMapping;
	}

	public static Mapping getMapping2() {
		Mapping myMapping = new Mapping();
		for (String key : MAPPING_2.keySet()) {
			myMapping.map(getEntitySets3().get(key), getEntitySets4().get(MAPPING_2.get(key)));
		}
		for (EntitySet es : getEntitySets3().values()) {
			if (myMapping.getImage(es) == null) {
				EntitySet ee = new EntitySet("empty for " + es.getName());
				ee.setEmpty(true);
				myMapping.map(es, ee);
			}
		}
		for (EntitySet es : getEntitySets4().values()) {
			if (myMapping.getImage(es) == null) {
				EntitySet ee = new EntitySet("empty for " + es.getName());
				ee.setEmpty(true);
				myMapping.map(es, ee);
			}
		}
		return myMapping;
	}

	public static List<String> getEntitySetNames3() {
		return ENTITY_SET_NAMES_3;
	}

	public static List<String> getEntitySetNames4() {
		return ENTITY_SET_NAMES_4;
	}

	public static Set<String[]> getRelationships3() {
		return RELATIONSHIPS_3;
	}

	public static Set<String[]> getRelationships4() {
		return RELATIONSHIPS_4;
	}

	public static ERModel getModel3() {
		return makeModel(getEntitySets3(), getRelationships3());
	}

	public static ERModel getModel4() {
		return makeModel(getEntitySets4(), getRelationships4());
	}
}
