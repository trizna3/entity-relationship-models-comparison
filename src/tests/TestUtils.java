package tests;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import common.Utils;
import common.enums.Enums;
import entityRelationshipModel.Association;
import entityRelationshipModel.AssociationSide;
import entityRelationshipModel.ERModel;
import entityRelationshipModel.EntitySet;
import entityRelationshipModel.Generalization;
import entityRelationshipModel.Relationship;
import transformations.Transformation;

/**
 * @author - Adam Trizna
 */

public class TestUtils {

	public static List<ERModel> getERModels() {
		return new ArrayList<ERModel>(Arrays.asList(makeERModel1(), makeERModel2(), makeERModel3(), makeERModel4(), makeERModel5(), makeERModel6(), makeERModel7(), makeERModel8(), makeERModel9()));
	}

	private static ERModel makeERModel1() {
		ERModel model = new ERModel();

		List<EntitySet> entitySets = new ArrayList<>();
		entitySets.add(new EntitySet("Budovy", new ArrayList<>(Arrays.asList("Ulica", "Cislo", "Mesto", "PSC")))); // 0
		entitySets.add(new EntitySet("Izby", new ArrayList<>(Arrays.asList("Cislo", "C.Poschodia", "Kapacita", "Poplatok")))); // 1
		entitySets.add(new EntitySet("Zmluvy", new ArrayList<>(Arrays.asList("Od", "Do")))); // 2
		entitySets.add(new EntitySet("Platby", new ArrayList<>(Arrays.asList("Vyska", "Datum")))); // 3
		entitySets.add(new EntitySet("Studenti", new ArrayList<>(Arrays.asList("Meno", "Priezvisko", "C.OP")))); // 4
		entitySets.add(new EntitySet("Konta", new ArrayList<>(Arrays.asList("Kredit")))); // 5

		List<Relationship> relationships = new ArrayList<>();
		relationships.add(new Association(new AssociationSide[] { new AssociationSide(entitySets.get(0), Enums.CARDINALITY_ONE), new AssociationSide(entitySets.get(1), Enums.CARDINALITY_MANY) }, null));
		relationships.add(new Association(new AssociationSide[] { new AssociationSide(entitySets.get(1), Enums.CARDINALITY_ONE), new AssociationSide(entitySets.get(2), Enums.CARDINALITY_MANY) }, null));
		relationships.add(new Association(new AssociationSide[] { new AssociationSide(entitySets.get(2), Enums.CARDINALITY_ONE), new AssociationSide(entitySets.get(3), Enums.CARDINALITY_MANY) }, null));
		relationships.add(new Association(new AssociationSide[] { new AssociationSide(entitySets.get(2), Enums.CARDINALITY_MANY), new AssociationSide(entitySets.get(4), Enums.CARDINALITY_ONE) }, null));
		relationships.add(new Association(new AssociationSide[] { new AssociationSide(entitySets.get(3), Enums.CARDINALITY_MANY), new AssociationSide(entitySets.get(5), Enums.CARDINALITY_ONE) }, null));
		relationships.add(new Association(new AssociationSide[] { new AssociationSide(entitySets.get(4), Enums.CARDINALITY_ONE), new AssociationSide(entitySets.get(5), Enums.CARDINALITY_ONE) }, null));

		model.addAllRelationships(relationships);
		model.addAllEntitySets(entitySets);
		return model;
	}

	/**
	 * NRSR vzor
	 * 
	 * @return
	 */
	private static ERModel makeERModel2() {
		ERModel model = new ERModel();

		List<EntitySet> entitySets = new ArrayList<>();
		entitySets.add(new EntitySet("Osoby", new ArrayList<>(Arrays.asList("Meno", "Priezvisko")))); // 0
		entitySets.add(new EntitySet("Schodze", new ArrayList<>(Arrays.asList("Od", "Do")))); // 1
		entitySets.add(new EntitySet("Body", new ArrayList<>(Arrays.asList("Kedy", "Poradove cislo")))); // 2
		entitySets.add(new EntitySet("Dokumenty", new ArrayList<>(Arrays.asList("Nazov", "Text")))); // 3
		entitySets.add(new EntitySet("Typy bodov", new ArrayList<>(Arrays.asList("Nazov")))); // 4
		entitySets.add(new EntitySet("Moznosti hlasovania", new ArrayList<>(Arrays.asList("Nazov")))); // 5

		List<Relationship> relationships = new ArrayList<>();
		relationships.add(new Association(new AssociationSide[] { new AssociationSide(entitySets.get(0), Enums.CARDINALITY_MANY), new AssociationSide(entitySets.get(1), Enums.CARDINALITY_MANY) }, null));
		relationships.add(new Association(new AssociationSide[] { new AssociationSide(entitySets.get(1), Enums.CARDINALITY_ONE), new AssociationSide(entitySets.get(2), Enums.CARDINALITY_MANY) }, null));
		relationships.add(new Association(new AssociationSide[] { new AssociationSide(entitySets.get(2), Enums.CARDINALITY_MANY), new AssociationSide(entitySets.get(3), Enums.CARDINALITY_MANY) }, null));
		relationships.add(new Association(new AssociationSide[] { new AssociationSide(entitySets.get(2), Enums.CARDINALITY_MANY), new AssociationSide(entitySets.get(4), Enums.CARDINALITY_ONE) }, null));
		relationships.add(new Association(new AssociationSide[] { new AssociationSide(entitySets.get(0), Enums.CARDINALITY_MANY), new AssociationSide(entitySets.get(2), Enums.CARDINALITY_MANY), new AssociationSide(entitySets.get(5), Enums.CARDINALITY_ONE) }, null));

		model.addAllRelationships(relationships);
		model.addAllEntitySets(entitySets);
		return model;
	}

	private static ERModel makeERModel3() {
		ERModel model = new ERModel();

		List<EntitySet> entitySets = new ArrayList<>();
		entitySets.add(new EntitySet("Budovy", new ArrayList<>(Arrays.asList("Ulica", "Cislo", "Mesto", "PSC")))); // 0
		entitySets.add(new EntitySet("Izby", new ArrayList<>(Arrays.asList("Cislo", "C.Poschodia", "Kapacita", "Poplatok")))); // 1
		entitySets.add(new EntitySet("Zmluvy", new ArrayList<>(Arrays.asList("Od", "Do")))); // 2
		entitySets.add(new EntitySet("Platby", new ArrayList<>(Arrays.asList("Vyska", "Datum")))); // 3
		entitySets.add(new EntitySet("Studenti", new ArrayList<>(Arrays.asList()))); // 4
		entitySets.add(new EntitySet("Konta", new ArrayList<>(Arrays.asList("Kredit")))); // 5
		entitySets.add(new EntitySet("Ludia", new ArrayList<>(Arrays.asList("Meno", "Priezvisko", "C.OP")))); // 6

		List<Relationship> relationships = new ArrayList<>();
		relationships.add(new Association(new AssociationSide[] { new AssociationSide(entitySets.get(0), Enums.CARDINALITY_ONE), new AssociationSide(entitySets.get(1), Enums.CARDINALITY_MANY) }, null));
		relationships.add(new Association(new AssociationSide[] { new AssociationSide(entitySets.get(1), Enums.CARDINALITY_ONE), new AssociationSide(entitySets.get(2), Enums.CARDINALITY_MANY) }, null));
		relationships.add(new Association(new AssociationSide[] { new AssociationSide(entitySets.get(2), Enums.CARDINALITY_ONE), new AssociationSide(entitySets.get(3), Enums.CARDINALITY_MANY) }, null));
		relationships.add(new Association(new AssociationSide[] { new AssociationSide(entitySets.get(2), Enums.CARDINALITY_MANY), new AssociationSide(entitySets.get(4), Enums.CARDINALITY_ONE) }, null));
		relationships.add(new Association(new AssociationSide[] { new AssociationSide(entitySets.get(3), Enums.CARDINALITY_MANY), new AssociationSide(entitySets.get(5), Enums.CARDINALITY_ONE) }, null));
		relationships.add(new Association(new AssociationSide[] { new AssociationSide(entitySets.get(4), Enums.CARDINALITY_ONE), new AssociationSide(entitySets.get(5), Enums.CARDINALITY_ONE) }, null));
		relationships.add(new Generalization(entitySets.get(6), entitySets.get(4)));

		model.addAllRelationships(relationships);
		model.addAllEntitySets(entitySets);
		return model;
	}

	private static ERModel makeERModel4() {
		ERModel model = new ERModel();

		List<EntitySet> entitySets = new ArrayList<>();
		entitySets.add(new EntitySet("Budovy", new ArrayList<>(Arrays.asList("Nazov", "Adresa")))); // 0
		entitySets.add(new EntitySet("Izby", new ArrayList<>(Arrays.asList("Poschodie", "Cislo dveri", "Kapacita", "Poplatok")))); // 1
		entitySets.add(new EntitySet("Zmluvy", new ArrayList<>(Arrays.asList("Zaciatok", "Koniec")))); // 2
		entitySets.add(new EntitySet("Platby", new ArrayList<>(Arrays.asList("Suma", "Cas")))); // 3
		entitySets.add(new EntitySet("Studenti", new ArrayList<>(Arrays.asList("Meno", "Kredit na konte", "vek")))); // 4

		List<Relationship> relationships = new ArrayList<>();
		relationships.add(new Association(new AssociationSide[] { new AssociationSide(entitySets.get(0), Enums.CARDINALITY_ONE), new AssociationSide(entitySets.get(1), Enums.CARDINALITY_MANY) }, null));
		relationships.add(new Association(new AssociationSide[] { new AssociationSide(entitySets.get(1), Enums.CARDINALITY_ONE), new AssociationSide(entitySets.get(2), Enums.CARDINALITY_MANY) }, null));
		relationships.add(new Association(new AssociationSide[] { new AssociationSide(entitySets.get(2), Enums.CARDINALITY_ONE), new AssociationSide(entitySets.get(3), Enums.CARDINALITY_ONE) }, null));
		relationships.add(new Association(new AssociationSide[] { new AssociationSide(entitySets.get(3), Enums.CARDINALITY_MANY), new AssociationSide(entitySets.get(4), Enums.CARDINALITY_ONE) }, null));

		model.addAllRelationships(relationships);
		model.addAllEntitySets(entitySets);
		return model;
	}

	/**
	 * Zamer aby mal 1NN1 vazbu.
	 * 
	 * @return
	 */
	private static ERModel makeERModel5() {
		ERModel model = new ERModel();

		List<EntitySet> entitySets = new ArrayList<>();
		entitySets.add(new EntitySet("Budovy", new ArrayList<>(Arrays.asList("Nazov", "Adresa")))); // 0
		entitySets.add(new EntitySet("Izby", new ArrayList<>(Arrays.asList("Poschodie", "Cislo dveri", "Kapacita", "Poplatok")))); // 1
		entitySets.add(new EntitySet("Zmluvy", new ArrayList<>(Arrays.asList("Zaciatok", "Koniec")))); // 2
		entitySets.add(new EntitySet("Platby", new ArrayList<>(Arrays.asList("Suma", "Cas")))); // 3
		entitySets.add(new EntitySet("Studenti", new ArrayList<>(Arrays.asList("Meno", "Kredit na konte", "vek")))); // 4

		List<Relationship> relationships = new ArrayList<>();
		relationships.add(new Association(new AssociationSide[] { new AssociationSide(entitySets.get(0), Enums.CARDINALITY_ONE), new AssociationSide(entitySets.get(1), Enums.CARDINALITY_MANY) }, null));
		relationships.add(new Association(new AssociationSide[] { new AssociationSide(entitySets.get(1), Enums.CARDINALITY_MANY), new AssociationSide(entitySets.get(2), Enums.CARDINALITY_ONE) }, null));
		relationships.add(new Association(new AssociationSide[] { new AssociationSide(entitySets.get(2), Enums.CARDINALITY_ONE), new AssociationSide(entitySets.get(3), Enums.CARDINALITY_ONE) }, null));
		relationships.add(new Association(new AssociationSide[] { new AssociationSide(entitySets.get(3), Enums.CARDINALITY_MANY), new AssociationSide(entitySets.get(4), Enums.CARDINALITY_ONE) }, null));

		model.addAllRelationships(relationships);
		model.addAllEntitySets(entitySets);
		return model;
	}

	private static ERModel makeERModel6() {
		ERModel model = new ERModel();

		List<EntitySet> entitySets = new ArrayList<>();
		entitySets.add(new EntitySet("Osoby", new ArrayList<>(Arrays.asList("Meno", "Priezvisko")))); // 0
		entitySets.add(new EntitySet("Schodze", new ArrayList<>(Arrays.asList("Od", "Do")))); // 1
		entitySets.add(new EntitySet("Body", new ArrayList<>(Arrays.asList("Kedy", "Poradove cislo")))); // 2
		entitySets.add(new EntitySet("Dokumenty", new ArrayList<>(Arrays.asList("Nazov", "Text")))); // 3
		entitySets.add(new EntitySet("Typy bodov", new ArrayList<>(Arrays.asList("Nazov")))); // 4
		entitySets.add(new EntitySet("Moznosti hlasovania", new ArrayList<>(Arrays.asList("Nazov")))); // 5
		entitySets.add(new EntitySet("Osoby na schodzi", new ArrayList<>(Arrays.asList()))); // 6
		entitySets.add(new EntitySet("Body v dokumente", new ArrayList<>(Arrays.asList()))); // 7

		List<Relationship> relationships = new ArrayList<>();
		relationships.add(new Association(new AssociationSide[] { new AssociationSide(entitySets.get(1), Enums.CARDINALITY_ONE), new AssociationSide(entitySets.get(2), Enums.CARDINALITY_MANY) }, null));
		relationships.add(new Association(new AssociationSide[] { new AssociationSide(entitySets.get(2), Enums.CARDINALITY_MANY), new AssociationSide(entitySets.get(4), Enums.CARDINALITY_ONE) }, null));
		relationships.add(new Association(new AssociationSide[] { new AssociationSide(entitySets.get(0), Enums.CARDINALITY_MANY), new AssociationSide(entitySets.get(2), Enums.CARDINALITY_MANY), new AssociationSide(entitySets.get(5), Enums.CARDINALITY_ONE) }, null));
		relationships.add(new Association(new AssociationSide[] { new AssociationSide(entitySets.get(0), Enums.CARDINALITY_ONE), new AssociationSide(entitySets.get(6), Enums.CARDINALITY_MANY) }, null));
		relationships.add(new Association(new AssociationSide[] { new AssociationSide(entitySets.get(1), Enums.CARDINALITY_ONE), new AssociationSide(entitySets.get(6), Enums.CARDINALITY_MANY) }, null));
		relationships.add(new Association(new AssociationSide[] { new AssociationSide(entitySets.get(2), Enums.CARDINALITY_ONE), new AssociationSide(entitySets.get(7), Enums.CARDINALITY_MANY) }, null));
		relationships.add(new Association(new AssociationSide[] { new AssociationSide(entitySets.get(3), Enums.CARDINALITY_ONE), new AssociationSide(entitySets.get(7), Enums.CARDINALITY_MANY) }, null));

		model.addAllRelationships(relationships);
		model.addAllEntitySets(entitySets);
		return model;
	}

	private static ERModel makeERModel7() {
		ERModel model = new ERModel();

		List<EntitySet> entitySets = new ArrayList<>();
		entitySets.add(new EntitySet("Budovy", new ArrayList<>(Arrays.asList("Ulica", "Cislo", "Mesto", "PSC")))); // 0
		entitySets.add(new EntitySet("Izby", new ArrayList<>(Arrays.asList("Cislo", "C.Poschodia", "Kapacita", "Poplatok")))); // 1
		entitySets.add(new EntitySet("Zmluvy", new ArrayList<>(Arrays.asList("Od", "Do")))); // 2
		entitySets.add(new EntitySet("Platby", new ArrayList<>(Arrays.asList("Vyska", "Datum")))); // 3
		entitySets.add(new EntitySet("Studenti", new ArrayList<>(Arrays.asList("Meno", "Priezvisko", "C.OP")))); // 4
		entitySets.add(new EntitySet("Konta", new ArrayList<>(Arrays.asList("Kredit")))); // 5

		List<Relationship> relationships = new ArrayList<>();
		relationships.add(new Association(new AssociationSide[] { new AssociationSide(entitySets.get(0), Enums.CARDINALITY_ONE), new AssociationSide(entitySets.get(1), Enums.CARDINALITY_MANY) }, null));
		((Association) relationships.get(0)).addAttribute("Test attribute 1");
		((Association) relationships.get(0)).addAttribute("Test attribute 2");
		relationships.add(new Association(new AssociationSide[] { new AssociationSide(entitySets.get(1), Enums.CARDINALITY_ONE), new AssociationSide(entitySets.get(2), Enums.CARDINALITY_MANY) }, null));
		relationships.add(new Association(new AssociationSide[] { new AssociationSide(entitySets.get(2), Enums.CARDINALITY_ONE), new AssociationSide(entitySets.get(3), Enums.CARDINALITY_MANY) }, null));
		((Association) relationships.get(2)).addAttribute("Test attribute 3");
		relationships.add(new Association(new AssociationSide[] { new AssociationSide(entitySets.get(2), Enums.CARDINALITY_MANY), new AssociationSide(entitySets.get(4), Enums.CARDINALITY_ONE) }, null));
		relationships.add(new Association(new AssociationSide[] { new AssociationSide(entitySets.get(3), Enums.CARDINALITY_MANY), new AssociationSide(entitySets.get(5), Enums.CARDINALITY_ONE) }, null));
		((Association) relationships.get(4)).addAttribute("Test attribute 4");
		((Association) relationships.get(4)).addAttribute("Test attribute 5");
		((Association) relationships.get(4)).addAttribute("Test attribute 6");
		relationships.add(new Association(new AssociationSide[] { new AssociationSide(entitySets.get(4), Enums.CARDINALITY_ONE), new AssociationSide(entitySets.get(5), Enums.CARDINALITY_ONE) }, null));

		model.addAllRelationships(relationships);
		model.addAllEntitySets(entitySets);
		return model;
	}

	/**
	 * Chemicky dennik vzor
	 * 
	 * @return
	 */
	private static ERModel makeERModel8() {
		ERModel model = new ERModel();

		List<EntitySet> entitySets = new ArrayList<>();
		entitySets.add(new EntitySet("Chemicke latky", new ArrayList<>(Arrays.asList("Nazov", "Vzorec")))); // 0
		entitySets.add(new EntitySet("Chemicke reakcie", new ArrayList<>(Arrays.asList("Rovnica")))); // 1

		List<Relationship> relationships = new ArrayList<>();
		relationships.add(new Association(new AssociationSide[] { new AssociationSide(entitySets.get(0), Enums.CARDINALITY_ONE), new AssociationSide(entitySets.get(1), Enums.CARDINALITY_MANY) }, null));
		((Association) relationships.get(0)).setName("Katalyzator");
		((Association) relationships.get(0)).addAttribute("Hmotnost");
		relationships.add(new Association(new AssociationSide[] { new AssociationSide(entitySets.get(0), Enums.CARDINALITY_ONE), new AssociationSide(entitySets.get(1), Enums.CARDINALITY_MANY) }, null));
		((Association) relationships.get(1)).setName("Reaktant 1");
		((Association) relationships.get(1)).addAttribute("Hmotnost");
		relationships.add(new Association(new AssociationSide[] { new AssociationSide(entitySets.get(0), Enums.CARDINALITY_ONE), new AssociationSide(entitySets.get(1), Enums.CARDINALITY_MANY) }, null));
		((Association) relationships.get(2)).setName("Reaktant 2");
		((Association) relationships.get(2)).addAttribute("Hmotnost");
		relationships.add(new Association(new AssociationSide[] { new AssociationSide(entitySets.get(0), Enums.CARDINALITY_ONE), new AssociationSide(entitySets.get(1), Enums.CARDINALITY_MANY) }, null));
		((Association) relationships.get(3)).setName("Produkt");

		model.addAllRelationships(relationships);
		model.addAllEntitySets(entitySets);
		return model;
	}

	/**
	 * Chemicky dennik student 1
	 * 
	 * @return
	 */
	private static ERModel makeERModel9() {
		ERModel model = new ERModel();

		List<EntitySet> entitySets = new ArrayList<>();
		entitySets.add(new EntitySet("Chemicke latky", new ArrayList<>(Arrays.asList("Nazov", "Vzorec")))); // 0
		entitySets.add(new EntitySet("Chemicke reakcie", new ArrayList<>(Arrays.asList("Rovnica", "Hmotnost reaktantu A", "Hmotnost reaktantu B", "Hmotnost reaktantu C")))); // 1

		List<Relationship> relationships = new ArrayList<>();
		relationships.add(new Association(new AssociationSide[] { new AssociationSide(entitySets.get(0), Enums.CARDINALITY_ONE), new AssociationSide(entitySets.get(1), Enums.CARDINALITY_MANY) }, null));
		relationships.add(new Association(new AssociationSide[] { new AssociationSide(entitySets.get(0), Enums.CARDINALITY_ONE), new AssociationSide(entitySets.get(1), Enums.CARDINALITY_MANY) }, null));
		relationships.add(new Association(new AssociationSide[] { new AssociationSide(entitySets.get(0), Enums.CARDINALITY_ONE), new AssociationSide(entitySets.get(1), Enums.CARDINALITY_MANY) }, null));
		relationships.add(new Association(new AssociationSide[] { new AssociationSide(entitySets.get(0), Enums.CARDINALITY_ONE), new AssociationSide(entitySets.get(1), Enums.CARDINALITY_MANY) }, null));

		model.addAllRelationships(relationships);
		model.addAllEntitySets(entitySets);
		return model;
	}

	public static boolean containsTransformation(List<Transformation> transformations, String code) {
		Utils.validateNotNull(transformations);
		Utils.validateNotNull(code);

		for (Transformation transformation : transformations) {
			if (code.equals(transformation.getCode())) {
				return true;
			}
		}
		return false;
	}
}
