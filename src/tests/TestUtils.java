package tests;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import common.Utils;
import common.enums.EnumRelationshipSideRole;
import common.enums.EnumTransformation;
import entityRelationshipModel.Association;
import entityRelationshipModel.AssociationSide;
import entityRelationshipModel.Attribute;
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
		return new ArrayList<ERModel>(Arrays.asList(makeERModel_Internaty_Vzor(), makeERModel2(), makeERModel3(), makeERModel4(), makeERModel5(), makeERModel6(), makeERModel7(), makeERModel_Dennik_vzor(), makeERModel_Dennik_S1()));
	}

	public static ERModel makeERModel_Internaty_Vzor() {
		ERModel model = new ERModel();

		List<EntitySet> entitySets = new ArrayList<>();
		entitySets.add(new EntitySet("Budovy", new ArrayList<>(Arrays.asList("Ulica", "Cislo", "Mesto", "PSC")))); // 0
		entitySets.add(new EntitySet("Izby", new ArrayList<>(Arrays.asList("Cislo", "C.Poschodia", "Kapacita", "Poplatok")))); // 1
		entitySets.add(new EntitySet("Zmluvy", new ArrayList<>(Arrays.asList("Od", "Do")))); // 2
		entitySets.add(new EntitySet("Platby", new ArrayList<>(Arrays.asList("Vyska", "Datum")))); // 3
		entitySets.add(new EntitySet("Studenti", new ArrayList<>(Arrays.asList("Meno", "Priezvisko", "C.OP")))); // 4
		entitySets.add(new EntitySet("Konta", new ArrayList<>(Arrays.asList("Kredit")))); // 5

		List<Relationship> relationships = new ArrayList<>();
		relationships.add(new Association(Arrays.asList(new AssociationSide(entitySets.get(0), EnumRelationshipSideRole.CARDINALITY_ONE), new AssociationSide(entitySets.get(1), EnumRelationshipSideRole.CARDINALITY_MANY)), null));
		relationships.add(new Association(Arrays.asList(new AssociationSide(entitySets.get(1), EnumRelationshipSideRole.CARDINALITY_ONE), new AssociationSide(entitySets.get(2), EnumRelationshipSideRole.CARDINALITY_MANY)), null));
		relationships.add(new Association(Arrays.asList(new AssociationSide(entitySets.get(2), EnumRelationshipSideRole.CARDINALITY_ONE), new AssociationSide(entitySets.get(3), EnumRelationshipSideRole.CARDINALITY_MANY)), null));
		relationships.add(new Association(Arrays.asList(new AssociationSide(entitySets.get(2), EnumRelationshipSideRole.CARDINALITY_MANY), new AssociationSide(entitySets.get(4), EnumRelationshipSideRole.CARDINALITY_ONE)), null));
		relationships.add(new Association(Arrays.asList(new AssociationSide(entitySets.get(3), EnumRelationshipSideRole.CARDINALITY_MANY), new AssociationSide(entitySets.get(5), EnumRelationshipSideRole.CARDINALITY_ONE)), null));
		relationships.add(new Association(Arrays.asList(new AssociationSide(entitySets.get(4), EnumRelationshipSideRole.CARDINALITY_ONE), new AssociationSide(entitySets.get(5), EnumRelationshipSideRole.CARDINALITY_ONE)), null));

		model.addAllRelationships(relationships);
		model.addAllEntitySets(entitySets);
		return model;
	}
	
	public static ERModel makeERModel8() {
		ERModel model = new ERModel();

		List<EntitySet> entitySets = new ArrayList<>();
		entitySets.add(new EntitySet("Budovy", new ArrayList<>(Arrays.asList("Ulica", "Cislo", "Mesto", "PSC")))); // 0
		entitySets.add(new EntitySet("Izby", new ArrayList<>(Arrays.asList("C.Poschodia", "Kapacita", "Poplatok")))); // 1
		entitySets.add(new EntitySet("Zmluvy", new ArrayList<>(Arrays.asList("Od", "Do")))); // 2
		entitySets.add(new EntitySet("Platby", new ArrayList<>(Arrays.asList("Vyska", "Datum")))); // 3
		entitySets.add(new EntitySet("Studenti", new ArrayList<>(Arrays.asList("Meno", "Priezvisko", "C.OP")))); // 4
		entitySets.add(new EntitySet("Konta", new ArrayList<>(Arrays.asList("Kredit")))); // 5
		entitySets.add(new EntitySet("Kredit", null)); // 6
		
		List<Relationship> relationships = new ArrayList<>();	
		relationships.add(new Association(Arrays.asList(new AssociationSide(entitySets.get(0), EnumRelationshipSideRole.CARDINALITY_ONE), new AssociationSide(entitySets.get(1), EnumRelationshipSideRole.CARDINALITY_MANY)), Arrays.asList("Cislo")));
		relationships.add(new Association(Arrays.asList(new AssociationSide(entitySets.get(0), EnumRelationshipSideRole.CARDINALITY_MANY), new AssociationSide(entitySets.get(2), EnumRelationshipSideRole.CARDINALITY_ONE)), null));
		relationships.add(new Association(Arrays.asList(new AssociationSide(entitySets.get(1), EnumRelationshipSideRole.CARDINALITY_ONE), new AssociationSide(entitySets.get(2), EnumRelationshipSideRole.CARDINALITY_MANY)), null));
		relationships.add(new Association(Arrays.asList(new AssociationSide(entitySets.get(2), EnumRelationshipSideRole.CARDINALITY_ONE), new AssociationSide(entitySets.get(3), EnumRelationshipSideRole.CARDINALITY_MANY)), null));
		relationships.add(new Association(Arrays.asList(new AssociationSide(entitySets.get(2), EnumRelationshipSideRole.CARDINALITY_MANY), new AssociationSide(entitySets.get(4), EnumRelationshipSideRole.CARDINALITY_ONE)), null));
		relationships.add(new Association(Arrays.asList(new AssociationSide(entitySets.get(3), EnumRelationshipSideRole.CARDINALITY_MANY), new AssociationSide(entitySets.get(5), EnumRelationshipSideRole.CARDINALITY_ONE)), null));
		relationships.add(new Association(Arrays.asList(new AssociationSide(entitySets.get(4), EnumRelationshipSideRole.CARDINALITY_ONE), new AssociationSide(entitySets.get(5), EnumRelationshipSideRole.CARDINALITY_ONE)), null));

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
		relationships.add(new Association(Arrays.asList(new AssociationSide(entitySets.get(0), EnumRelationshipSideRole.CARDINALITY_MANY), new AssociationSide(entitySets.get(1), EnumRelationshipSideRole.CARDINALITY_MANY)), null));
		relationships.add(new Association(Arrays.asList(new AssociationSide(entitySets.get(1), EnumRelationshipSideRole.CARDINALITY_ONE), new AssociationSide(entitySets.get(2), EnumRelationshipSideRole.CARDINALITY_MANY)), null));
		relationships.add(new Association(Arrays.asList(new AssociationSide(entitySets.get(2), EnumRelationshipSideRole.CARDINALITY_MANY), new AssociationSide(entitySets.get(3), EnumRelationshipSideRole.CARDINALITY_MANY)), null));
		relationships.add(new Association(Arrays.asList(new AssociationSide(entitySets.get(2), EnumRelationshipSideRole.CARDINALITY_MANY), new AssociationSide(entitySets.get(4), EnumRelationshipSideRole.CARDINALITY_ONE)), null));
		relationships.add(new Association(Arrays.asList(new AssociationSide(entitySets.get(0), EnumRelationshipSideRole.CARDINALITY_MANY), new AssociationSide(entitySets.get(2), EnumRelationshipSideRole.CARDINALITY_MANY), new AssociationSide(entitySets.get(5), EnumRelationshipSideRole.CARDINALITY_ONE)), null));

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
		relationships.add(new Association(Arrays.asList(new AssociationSide(entitySets.get(0), EnumRelationshipSideRole.CARDINALITY_ONE), new AssociationSide(entitySets.get(1), EnumRelationshipSideRole.CARDINALITY_MANY)), null));
		relationships.add(new Association(Arrays.asList(new AssociationSide(entitySets.get(1), EnumRelationshipSideRole.CARDINALITY_ONE), new AssociationSide(entitySets.get(2), EnumRelationshipSideRole.CARDINALITY_MANY)), null));
		relationships.add(new Association(Arrays.asList(new AssociationSide(entitySets.get(2), EnumRelationshipSideRole.CARDINALITY_ONE), new AssociationSide(entitySets.get(3), EnumRelationshipSideRole.CARDINALITY_MANY)), null));
		relationships.add(new Association(Arrays.asList(new AssociationSide(entitySets.get(2), EnumRelationshipSideRole.CARDINALITY_MANY), new AssociationSide(entitySets.get(4), EnumRelationshipSideRole.CARDINALITY_ONE)), null));
		relationships.add(new Association(Arrays.asList(new AssociationSide(entitySets.get(3), EnumRelationshipSideRole.CARDINALITY_MANY), new AssociationSide(entitySets.get(5), EnumRelationshipSideRole.CARDINALITY_ONE)), Arrays.asList("cislo")));
		relationships.add(new Association(Arrays.asList(new AssociationSide(entitySets.get(4), EnumRelationshipSideRole.CARDINALITY_ONE), new AssociationSide(entitySets.get(5), EnumRelationshipSideRole.CARDINALITY_ONE)), null));
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
		relationships.add(new Association(Arrays.asList(new AssociationSide(entitySets.get(0), EnumRelationshipSideRole.CARDINALITY_ONE), new AssociationSide(entitySets.get(1), EnumRelationshipSideRole.CARDINALITY_MANY)), null));
		relationships.add(new Association(Arrays.asList(new AssociationSide(entitySets.get(1), EnumRelationshipSideRole.CARDINALITY_ONE), new AssociationSide(entitySets.get(2), EnumRelationshipSideRole.CARDINALITY_MANY)), null));
		relationships.add(new Association(Arrays.asList(new AssociationSide(entitySets.get(2), EnumRelationshipSideRole.CARDINALITY_ONE), new AssociationSide(entitySets.get(3), EnumRelationshipSideRole.CARDINALITY_ONE)), null));
		relationships.add(new Association(Arrays.asList(new AssociationSide(entitySets.get(3), EnumRelationshipSideRole.CARDINALITY_MANY), new AssociationSide(entitySets.get(4), EnumRelationshipSideRole.CARDINALITY_ONE)), null));

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
		relationships.add(new Association(Arrays.asList(new AssociationSide(entitySets.get(0), EnumRelationshipSideRole.CARDINALITY_ONE), new AssociationSide(entitySets.get(1), EnumRelationshipSideRole.CARDINALITY_MANY)), null));
		relationships.add(new Association(Arrays.asList(new AssociationSide(entitySets.get(1), EnumRelationshipSideRole.CARDINALITY_MANY), new AssociationSide(entitySets.get(2), EnumRelationshipSideRole.CARDINALITY_ONE)), null));
		relationships.add(new Association(Arrays.asList(new AssociationSide(entitySets.get(2), EnumRelationshipSideRole.CARDINALITY_ONE), new AssociationSide(entitySets.get(3), EnumRelationshipSideRole.CARDINALITY_ONE)), null));
		relationships.add(new Association(Arrays.asList(new AssociationSide(entitySets.get(3), EnumRelationshipSideRole.CARDINALITY_MANY), new AssociationSide(entitySets.get(4), EnumRelationshipSideRole.CARDINALITY_ONE)), null));

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
		relationships.add(new Association(Arrays.asList(new AssociationSide(entitySets.get(1), EnumRelationshipSideRole.CARDINALITY_ONE), new AssociationSide(entitySets.get(2), EnumRelationshipSideRole.CARDINALITY_MANY)), null));
		relationships.add(new Association(Arrays.asList(new AssociationSide(entitySets.get(2), EnumRelationshipSideRole.CARDINALITY_MANY), new AssociationSide(entitySets.get(4), EnumRelationshipSideRole.CARDINALITY_ONE)), null));
		relationships.add(new Association(Arrays.asList(new AssociationSide(entitySets.get(0), EnumRelationshipSideRole.CARDINALITY_MANY), new AssociationSide(entitySets.get(2), EnumRelationshipSideRole.CARDINALITY_MANY), new AssociationSide(entitySets.get(5), EnumRelationshipSideRole.CARDINALITY_ONE)), null));
		relationships.add(new Association(Arrays.asList(new AssociationSide(entitySets.get(0), EnumRelationshipSideRole.CARDINALITY_ONE), new AssociationSide(entitySets.get(6), EnumRelationshipSideRole.CARDINALITY_MANY)), null));
		relationships.add(new Association(Arrays.asList(new AssociationSide(entitySets.get(1), EnumRelationshipSideRole.CARDINALITY_ONE), new AssociationSide(entitySets.get(6), EnumRelationshipSideRole.CARDINALITY_MANY)), null));
		relationships.add(new Association(Arrays.asList(new AssociationSide(entitySets.get(2), EnumRelationshipSideRole.CARDINALITY_ONE), new AssociationSide(entitySets.get(7), EnumRelationshipSideRole.CARDINALITY_MANY)), null));
		relationships.add(new Association(Arrays.asList(new AssociationSide(entitySets.get(3), EnumRelationshipSideRole.CARDINALITY_ONE), new AssociationSide(entitySets.get(7), EnumRelationshipSideRole.CARDINALITY_MANY)), null));

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
		relationships.add(new Association(Arrays.asList(new AssociationSide(entitySets.get(0), EnumRelationshipSideRole.CARDINALITY_ONE), new AssociationSide(entitySets.get(1), EnumRelationshipSideRole.CARDINALITY_MANY)), null));
		((Association) relationships.get(0)).addAttribute(new Attribute("Test attribute 1"));
		((Association) relationships.get(0)).addAttribute(new Attribute("Test attribute 2"));
		relationships.add(new Association(Arrays.asList(new AssociationSide(entitySets.get(1), EnumRelationshipSideRole.CARDINALITY_ONE), new AssociationSide(entitySets.get(2), EnumRelationshipSideRole.CARDINALITY_MANY)), null));
		relationships.add(new Association(Arrays.asList(new AssociationSide(entitySets.get(2), EnumRelationshipSideRole.CARDINALITY_ONE), new AssociationSide(entitySets.get(3), EnumRelationshipSideRole.CARDINALITY_MANY)), null));
		((Association) relationships.get(2)).addAttribute(new Attribute("Test attribute 3"));
		relationships.add(new Association(Arrays.asList(new AssociationSide(entitySets.get(2), EnumRelationshipSideRole.CARDINALITY_MANY), new AssociationSide(entitySets.get(4), EnumRelationshipSideRole.CARDINALITY_ONE)), null));
		relationships.add(new Association(Arrays.asList(new AssociationSide(entitySets.get(3), EnumRelationshipSideRole.CARDINALITY_MANY), new AssociationSide(entitySets.get(5), EnumRelationshipSideRole.CARDINALITY_ONE)), null));
		((Association) relationships.get(4)).addAttribute(new Attribute("Test attribute 4"));
		((Association) relationships.get(4)).addAttribute(new Attribute("Test attribute 5"));
		((Association) relationships.get(4)).addAttribute(new Attribute("Test attribute 6"));
		relationships.add(new Association(Arrays.asList(new AssociationSide(entitySets.get(4), EnumRelationshipSideRole.CARDINALITY_ONE), new AssociationSide(entitySets.get(5), EnumRelationshipSideRole.CARDINALITY_ONE)), null));

		model.addAllRelationships(relationships);
		model.addAllEntitySets(entitySets);
		return model;
	}

	/**
	 * Chemicky dennik vzor
	 * 
	 * @return
	 */
	public static ERModel makeERModel_Dennik_vzor() {
		ERModel model = new ERModel();

		List<EntitySet> entitySets = new ArrayList<>();
		entitySets.add(new EntitySet("Chemicke latky", new ArrayList<>(Arrays.asList("Nazov", "Vzorec")))); // 0
		entitySets.add(new EntitySet("Chemicke reakcie", new ArrayList<>(Arrays.asList("Rovnica")))); // 1

		List<Relationship> relationships = new ArrayList<>();
		relationships.add(new Association(Arrays.asList(new AssociationSide(entitySets.get(0), EnumRelationshipSideRole.CARDINALITY_ONE), new AssociationSide(entitySets.get(1), EnumRelationshipSideRole.CARDINALITY_MANY)), null));
		((Association) relationships.get(0)).setNameText("Katalyzator");
		((Association) relationships.get(0)).addAttribute(new Attribute("Hmotnost katalyzatora"));
		relationships.add(new Association(Arrays.asList(new AssociationSide(entitySets.get(0), EnumRelationshipSideRole.CARDINALITY_ONE), new AssociationSide(entitySets.get(1), EnumRelationshipSideRole.CARDINALITY_MANY)), null));
		((Association) relationships.get(1)).setNameText("Reaktant 1");
		((Association) relationships.get(1)).addAttribute(new Attribute("Hmotnost reaktantu A"));
		relationships.add(new Association(Arrays.asList(new AssociationSide(entitySets.get(0), EnumRelationshipSideRole.CARDINALITY_ONE), new AssociationSide(entitySets.get(1), EnumRelationshipSideRole.CARDINALITY_MANY)), null));
		((Association) relationships.get(2)).setNameText("Reaktant 2");
		((Association) relationships.get(2)).addAttribute(new Attribute("Hmotnost reaktantu B"));
		relationships.add(new Association(Arrays.asList(new AssociationSide(entitySets.get(0), EnumRelationshipSideRole.CARDINALITY_ONE), new AssociationSide(entitySets.get(1), EnumRelationshipSideRole.CARDINALITY_MANY)), null));
		((Association) relationships.get(3)).setNameText("Produkt");

		model.addAllRelationships(relationships);
		model.addAllEntitySets(entitySets);
		return model;
	}

	/**
	 * Chemicky dennik student 1
	 * 
	 * @return
	 */
	public static ERModel makeERModel_Dennik_S1() {
		ERModel model = new ERModel();

		List<EntitySet> entitySets = new ArrayList<>();
		entitySets.add(new EntitySet("Chemicke latky", new ArrayList<>(Arrays.asList("Nazov", "Vzorec")))); // 0
		entitySets.add(new EntitySet("Chemicke reakcie", new ArrayList<>(Arrays.asList("Rovnica", "Hmotnost reaktantu A", "Hmotnost reaktantu B", "Hmotnost katalyzatora")))); // 1

		List<Relationship> relationships = new ArrayList<>();
		relationships.add(new Association(Arrays.asList(new AssociationSide(entitySets.get(0), EnumRelationshipSideRole.CARDINALITY_ONE), new AssociationSide(entitySets.get(1), EnumRelationshipSideRole.CARDINALITY_MANY)), null));
		relationships.add(new Association(Arrays.asList(new AssociationSide(entitySets.get(0), EnumRelationshipSideRole.CARDINALITY_ONE), new AssociationSide(entitySets.get(1), EnumRelationshipSideRole.CARDINALITY_MANY)), null));
		relationships.add(new Association(Arrays.asList(new AssociationSide(entitySets.get(0), EnumRelationshipSideRole.CARDINALITY_ONE), new AssociationSide(entitySets.get(1), EnumRelationshipSideRole.CARDINALITY_MANY)), null));
		relationships.add(new Association(Arrays.asList(new AssociationSide(entitySets.get(0), EnumRelationshipSideRole.CARDINALITY_ONE), new AssociationSide(entitySets.get(1), EnumRelationshipSideRole.CARDINALITY_MANY)), null));

		model.addAllRelationships(relationships);
		model.addAllEntitySets(entitySets);
		return model;
	}

	/**
	 * Chemicky dennik student 2
	 * 
	 * @return
	 */
	public static ERModel makeERModel_Dennik_S2() {
		ERModel model = new ERModel();

		List<EntitySet> entitySets = new ArrayList<>();
		entitySets.add(new EntitySet("Chemicke latky", new ArrayList<>(Arrays.asList("Nazov", "Vzorec")))); // 0
		entitySets.add(new EntitySet("Chemicke reakcie", new ArrayList<>(Arrays.asList("Hmotnost reaktantu A", "Hmotnost reaktantu B", "Hmotnost katalyzatora")))); // 1
		entitySets.add(new EntitySet("Produkt", new ArrayList<>(Arrays.asList("Rovnica", "Hmotnost chemickych latok")))); // 2

		List<Relationship> relationships = new ArrayList<>();
		relationships.add(new Association(Arrays.asList(new AssociationSide(entitySets.get(0), EnumRelationshipSideRole.CARDINALITY_MANY), new AssociationSide(entitySets.get(1), EnumRelationshipSideRole.CARDINALITY_ONE)), null));
		relationships.add(new Association(Arrays.asList(new AssociationSide(entitySets.get(0), EnumRelationshipSideRole.CARDINALITY_ONE), new AssociationSide(entitySets.get(1), EnumRelationshipSideRole.CARDINALITY_MANY)), null));
		relationships.add(new Generalization(entitySets.get(1), entitySets.get(2)));

		model.addAllRelationships(relationships);
		model.addAllEntitySets(entitySets);
		return model;
	}

	/**
	 * Eshop vzor
	 * 
	 * @return
	 */
	public static ERModel makeERModel_Eshop_Vzor() {
		ERModel model = new ERModel();

		List<EntitySet> entitySets = new ArrayList<>();
		entitySets.add(new EntitySet("Typy produktov", new ArrayList<>(Arrays.asList("Nazov"))));// 0
		entitySets.add(new EntitySet("Produkty", new ArrayList<>(Arrays.asList("Vaha", "Cena")))); // 1
		entitySets.add(new EntitySet("Nazvy specialnych atributov", new ArrayList<>(Arrays.asList("Atribut")))); // 2

		List<Relationship> relationships = new ArrayList<>();
		relationships.add(new Association(Arrays.asList(new AssociationSide(entitySets.get(0), EnumRelationshipSideRole.CARDINALITY_ONE), new AssociationSide(entitySets.get(1), EnumRelationshipSideRole.CARDINALITY_MANY)), null));
		relationships.add(new Association(Arrays.asList(new AssociationSide(entitySets.get(0), EnumRelationshipSideRole.CARDINALITY_MANY), new AssociationSide(entitySets.get(2), EnumRelationshipSideRole.CARDINALITY_MANY)), null));
		relationships.add(new Association("Hodnoty specialnych atributov", Arrays.asList(new AssociationSide(entitySets.get(1), EnumRelationshipSideRole.CARDINALITY_MANY), new AssociationSide(entitySets.get(2), EnumRelationshipSideRole.CARDINALITY_MANY)), new ArrayList<String>(Arrays.asList("Hodnota"))));

		model.addAllRelationships(relationships);
		model.addAllEntitySets(entitySets);
		return model;
	}

	/**
	 * Eshop student 1
	 * 
	 * @return
	 */
	public static ERModel makeERModel_Eshop_S1() {
		ERModel model = new ERModel();

		List<EntitySet> entitySets = new ArrayList<>();
		entitySets.add(new EntitySet("Produkty", new ArrayList<>(Arrays.asList("Nazov", "Vaha", "Cena")))); // 0
		entitySets.add(new EntitySet("Typy produktov")); // 1
		entitySets.add(new EntitySet("Atributy produktu")); // 2

		List<Relationship> relationships = new ArrayList<>();
		relationships.add(new Association(Arrays.asList(new AssociationSide(entitySets.get(0), EnumRelationshipSideRole.CARDINALITY_MANY), new AssociationSide(entitySets.get(1), EnumRelationshipSideRole.CARDINALITY_ONE)), null));
		relationships.add(new Association(Arrays.asList(new AssociationSide(entitySets.get(1), EnumRelationshipSideRole.CARDINALITY_ONE), new AssociationSide(entitySets.get(2), EnumRelationshipSideRole.CARDINALITY_MANY)), null));

		model.addAllRelationships(relationships);
		model.addAllEntitySets(entitySets);
		return model;
	}

	/**
	 * Eshop student 2
	 * 
	 * @return
	 */
	public static ERModel makeERModel_Eshop_S2() {
		ERModel model = new ERModel();

		List<EntitySet> entitySets = new ArrayList<>();
		entitySets.add(new EntitySet("Produkty", new ArrayList<>(Arrays.asList("Typ nazov", "Vaha", "Cena")))); // 0
		entitySets.add(new EntitySet("Typy produktov", new ArrayList<>(Arrays.asList("Chladnicka", "Pracka")))); // 1
		entitySets.add(new EntitySet("Atributy", new ArrayList<>(Arrays.asList("Objem")))); // 2

		List<Relationship> relationships = new ArrayList<>();
		relationships.add(new Association(Arrays.asList(new AssociationSide(entitySets.get(0), EnumRelationshipSideRole.CARDINALITY_ONE), new AssociationSide(entitySets.get(1), EnumRelationshipSideRole.CARDINALITY_ONE), new AssociationSide(entitySets.get(2), EnumRelationshipSideRole.CARDINALITY_ONE)), null));

		model.addAllRelationships(relationships);
		model.addAllEntitySets(entitySets);
		return model;
	}

	/**
	 * Vztahy vzor
	 * 
	 * @return
	 */
	public static ERModel makeERModel_Vztahy_vzor() {
		ERModel model = new ERModel();

		List<EntitySet> entitySets = new ArrayList<>();
		entitySets.add(new EntitySet("Osoby", new ArrayList<>(Arrays.asList("Meno", "Priezvisko", "Rok narodenia")))); // 0
		entitySets.add(new EntitySet("Vztahy", new ArrayList<>(Arrays.asList("Odkedy")))); // 1
		entitySets.add(new EntitySet("Typ vztahu", new ArrayList<>(Arrays.asList("Nazov")))); // 2

		List<Relationship> relationships = new ArrayList<>();
		relationships.add(new Association("osoba 1", Arrays.asList(new AssociationSide(entitySets.get(0), EnumRelationshipSideRole.CARDINALITY_ONE), new AssociationSide(entitySets.get(1), EnumRelationshipSideRole.CARDINALITY_MANY)), null));
		relationships.add(new Association("osoba 2", Arrays.asList(new AssociationSide(entitySets.get(0), EnumRelationshipSideRole.CARDINALITY_ONE), new AssociationSide(entitySets.get(1), EnumRelationshipSideRole.CARDINALITY_MANY)), null));

		relationships.add(new Association(Arrays.asList(new AssociationSide(entitySets.get(1), EnumRelationshipSideRole.CARDINALITY_MANY), new AssociationSide(entitySets.get(2), EnumRelationshipSideRole.CARDINALITY_ONE)), null));

		model.addAllRelationships(relationships);
		model.addAllEntitySets(entitySets);
		return model;
	}

	/**
	 * Vztahy student 1
	 * 
	 * @return
	 */
	public static ERModel makeERModel_Vztahy_S1() {
		ERModel model = new ERModel();

		List<EntitySet> entitySets = new ArrayList<>();
		entitySets.add(new EntitySet("Osoby", new ArrayList<>(Arrays.asList("Meno", "Priezvisko", "Rok narodenia")))); // 0
		entitySets.add(new EntitySet("Vztahy", new ArrayList<>(Arrays.asList("Nazov", "Osoba 1", "Osoba 2", "Odkedy")))); // 1
		entitySets.add(new EntitySet("Typ vztahu", new ArrayList<>(Arrays.asList("Nazov")))); // 2

		List<Relationship> relationships = new ArrayList<>();
		relationships.add(new Association(Arrays.asList(new AssociationSide(entitySets.get(0), EnumRelationshipSideRole.CARDINALITY_ONE), new AssociationSide(entitySets.get(1), EnumRelationshipSideRole.CARDINALITY_MANY)), null));
		relationships.add(new Association(Arrays.asList(new AssociationSide(entitySets.get(0), EnumRelationshipSideRole.CARDINALITY_ONE), new AssociationSide(entitySets.get(1), EnumRelationshipSideRole.CARDINALITY_MANY)), null));

		relationships.add(new Association(Arrays.asList(new AssociationSide(entitySets.get(1), EnumRelationshipSideRole.CARDINALITY_MANY), new AssociationSide(entitySets.get(2), EnumRelationshipSideRole.CARDINALITY_ONE)), null));

		model.addAllRelationships(relationships);
		model.addAllEntitySets(entitySets);
		return model;
	}

	/**
	 * Vztahy student 2
	 * 
	 * @return
	 */
	public static ERModel makeERModel_Vztahy_S2() {
		ERModel model = new ERModel();

		List<EntitySet> entitySets = new ArrayList<>();
		entitySets.add(new EntitySet("Osoby", new ArrayList<>(Arrays.asList("Meno", "Priezvisko", "Rok narodenia")))); // 0
		entitySets.add(new EntitySet("Osoby", new ArrayList<>(Arrays.asList("Meno 2", "Priezvisko 2", "Rok narodenia 2")))); // 1
		entitySets.add(new EntitySet("Vztahy", new ArrayList<>(Arrays.asList("Nazov", "Odkedy")))); // 2

		List<Relationship> relationships = new ArrayList<>();
		relationships.add(new Association(Arrays.asList(new AssociationSide(entitySets.get(0), EnumRelationshipSideRole.CARDINALITY_ONE), new AssociationSide(entitySets.get(1), EnumRelationshipSideRole.CARDINALITY_ONE), new AssociationSide(entitySets.get(2), EnumRelationshipSideRole.CARDINALITY_ONE)), null));

		model.addAllRelationships(relationships);
		model.addAllEntitySets(entitySets);
		return model;
	}

	/**
	 * Internaty student 1
	 * 
	 * @return
	 */
	public static ERModel makeERModel_Internaty_S1() {
		ERModel model = new ERModel();

		List<EntitySet> entitySets = new ArrayList<>();
		entitySets.add(new EntitySet("Budovy", new ArrayList<>(Arrays.asList("Nazov", "Adresa")))); // 0
		entitySets.add(new EntitySet("Izby", new ArrayList<>(Arrays.asList("Cislo", "C.Poschodia", "Kapacita", "Poplatok")))); // 1
		entitySets.add(new EntitySet("Zmluvy", new ArrayList<>(Arrays.asList("Od", "Do")))); // 2
		entitySets.add(new EntitySet("Platby", new ArrayList<>(Arrays.asList("Vyska", "Datum")))); // 3
		entitySets.add(new EntitySet("Studenti", new ArrayList<>(Arrays.asList("Kredit", "Meno", "Vek")))); // 4

		List<Relationship> relationships = new ArrayList<>();
		relationships.add(new Association(Arrays.asList(new AssociationSide(entitySets.get(0), EnumRelationshipSideRole.CARDINALITY_ONE), new AssociationSide(entitySets.get(1), EnumRelationshipSideRole.CARDINALITY_MANY)), null));
		relationships.add(new Association(Arrays.asList(new AssociationSide(entitySets.get(1), EnumRelationshipSideRole.CARDINALITY_ONE), new AssociationSide(entitySets.get(2), EnumRelationshipSideRole.CARDINALITY_MANY)), null));
		relationships.add(new Association(Arrays.asList(new AssociationSide(entitySets.get(2), EnumRelationshipSideRole.CARDINALITY_ONE), new AssociationSide(entitySets.get(3), EnumRelationshipSideRole.CARDINALITY_ONE)), null));
		relationships.add(new Association(Arrays.asList(new AssociationSide(entitySets.get(3), EnumRelationshipSideRole.CARDINALITY_MANY), new AssociationSide(entitySets.get(4), EnumRelationshipSideRole.CARDINALITY_ONE)), null));

		model.addAllRelationships(relationships);
		model.addAllEntitySets(entitySets);
		return model;
	}

	/**
	 * Internaty student 2
	 * 
	 * @return
	 */
	public static ERModel makeERModel_Internaty_S2() {
		ERModel model = new ERModel();

		List<EntitySet> entitySets = new ArrayList<>();
		entitySets.add(new EntitySet("Internaty", new ArrayList<>(Arrays.asList("Nazov", "Adresa")))); // 5
		entitySets.add(new EntitySet("Budovy", new ArrayList<>(Arrays.asList("Nazov", "Adresa")))); // 0
		entitySets.add(new EntitySet("Izby", new ArrayList<>(Arrays.asList("Cislo", "C.Poschodia", "Kapacita", "Poplatok")))); // 1
		entitySets.add(new EntitySet("Zmluvy", new ArrayList<>(Arrays.asList("Od", "Do")))); // 2
		entitySets.add(new EntitySet("Platby", new ArrayList<>(Arrays.asList("Vyska", "Datum")))); // 3
		entitySets.add(new EntitySet("Ubytovani", new ArrayList<>(Arrays.asList("Kredit")))); // 4

		List<Relationship> relationships = new ArrayList<>();
		relationships.add(new Association(Arrays.asList(new AssociationSide(entitySets.get(5), EnumRelationshipSideRole.CARDINALITY_ONE), new AssociationSide(entitySets.get(0), EnumRelationshipSideRole.CARDINALITY_MANY)), null));
		relationships.add(new Association(Arrays.asList(new AssociationSide(entitySets.get(0), EnumRelationshipSideRole.CARDINALITY_ONE), new AssociationSide(entitySets.get(1), EnumRelationshipSideRole.CARDINALITY_MANY)), null));
		relationships.add(new Association(Arrays.asList(new AssociationSide(entitySets.get(1), EnumRelationshipSideRole.CARDINALITY_ONE), new AssociationSide(entitySets.get(3), EnumRelationshipSideRole.CARDINALITY_ONE)), null));
		relationships.add(new Association(Arrays.asList(new AssociationSide(entitySets.get(1), EnumRelationshipSideRole.CARDINALITY_ONE), new AssociationSide(entitySets.get(4), EnumRelationshipSideRole.CARDINALITY_ONE)), null));
		relationships.add(new Association(Arrays.asList(new AssociationSide(entitySets.get(4), EnumRelationshipSideRole.CARDINALITY_ONE), new AssociationSide(entitySets.get(2), EnumRelationshipSideRole.CARDINALITY_ONE)), null));

		model.addAllRelationships(relationships);
		model.addAllEntitySets(entitySets);
		return model;
	}

	/**
	 * MHD Vzor
	 * 
	 * @return
	 */
	public static ERModel makeERModel_MHD_Vzor() {
		ERModel model = new ERModel();

		List<EntitySet> entitySets = new ArrayList<>();
		entitySets.add(new EntitySet("Zastavky", new ArrayList<>(Arrays.asList("Nazov")))); // 0
		entitySets.add(new EntitySet("Linky", new ArrayList<>(Arrays.asList("Cislo")))); // 1
		entitySets.add(new EntitySet("Vozidla", new ArrayList<>(Arrays.asList("Kod")))); // 2
		entitySets.add(new EntitySet("Typy vozidla", new ArrayList<>(Arrays.asList("Nazov")))); // 3

		List<Relationship> relationships = new ArrayList<>();
		relationships.add(new Association("Zahrna", Arrays.asList(new AssociationSide(entitySets.get(0), EnumRelationshipSideRole.CARDINALITY_MANY), new AssociationSide(entitySets.get(1), EnumRelationshipSideRole.CARDINALITY_MANY)), new ArrayList<>(Arrays.asList("Cas po zastavku"))));
		relationships.add(new Association("Jazdi na linke", Arrays.asList(new AssociationSide(entitySets.get(1), EnumRelationshipSideRole.CARDINALITY_MANY), new AssociationSide(entitySets.get(2), EnumRelationshipSideRole.CARDINALITY_MANY)), new ArrayList<>(Arrays.asList("Cas odchodu z prvej zastavky"))));
		relationships.add(new Association(Arrays.asList(new AssociationSide(entitySets.get(1), EnumRelationshipSideRole.CARDINALITY_MANY), new AssociationSide(entitySets.get(3), EnumRelationshipSideRole.CARDINALITY_ONE)), null));
		relationships.add(new Association(Arrays.asList(new AssociationSide(entitySets.get(2), EnumRelationshipSideRole.CARDINALITY_MANY), new AssociationSide(entitySets.get(3), EnumRelationshipSideRole.CARDINALITY_ONE)), null));

		model.addAllRelationships(relationships);
		model.addAllEntitySets(entitySets);
		return model;
	}

	/**
	 * MHD student 1
	 * 
	 * @return
	 */
	public static ERModel makeERModel_MHD_S1() {
		ERModel model = new ERModel();

		List<EntitySet> entitySets = new ArrayList<>();
		entitySets.add(new EntitySet("Zastavky", new ArrayList<>(Arrays.asList("Nazov")))); // 0
		entitySets.add(new EntitySet("Linky", new ArrayList<>(Arrays.asList("Cislo", "Typ vozidla")))); // 1
		entitySets.add(new EntitySet("Vozidla", new ArrayList<>(Arrays.asList("Kod", "Typ vozidla")))); // 2
		entitySets.add(new EntitySet("Casy", new ArrayList<>(Arrays.asList("Cas odchodu")))); // 3

		List<Relationship> relationships = new ArrayList<>();
		relationships.add(new Association("L_L", Arrays.asList(new AssociationSide(entitySets.get(0), EnumRelationshipSideRole.CARDINALITY_MANY), new AssociationSide(entitySets.get(1), EnumRelationshipSideRole.CARDINALITY_MANY)), new ArrayList<>(Arrays.asList("Cas po zastavku"))));
		relationships.add(new Association(Arrays.asList(new AssociationSide(entitySets.get(1), EnumRelationshipSideRole.CARDINALITY_MANY), new AssociationSide(entitySets.get(2), EnumRelationshipSideRole.CARDINALITY_ONE)), null));
		relationships.add(new Association(Arrays.asList(new AssociationSide(entitySets.get(1), EnumRelationshipSideRole.CARDINALITY_MANY), new AssociationSide(entitySets.get(3), EnumRelationshipSideRole.CARDINALITY_ONE)), null));
		relationships.add(new Association(Arrays.asList(new AssociationSide(entitySets.get(2), EnumRelationshipSideRole.CARDINALITY_ONE), new AssociationSide(entitySets.get(3), EnumRelationshipSideRole.CARDINALITY_MANY)), null));

		model.addAllRelationships(relationships);
		model.addAllEntitySets(entitySets);
		return model;
	}

	/**
	 * MHD student 2
	 * 
	 * @return
	 */
	public static ERModel makeERModel_MHD_S2() {
		ERModel model = new ERModel();

		List<EntitySet> entitySets = new ArrayList<>();
		entitySets.add(new EntitySet("Zastavky", new ArrayList<>(Arrays.asList("Nazov")))); // 0
		entitySets.add(new EntitySet("Linky", new ArrayList<>(Arrays.asList("Cislo", "Trasa")))); // 1
		entitySets.add(new EntitySet("Vozidla", new ArrayList<>(Arrays.asList("Kod", "Cas")))); // 2
		entitySets.add(new EntitySet("Trvanie cesty", new ArrayList<>(Arrays.asList("Zaciatok", "Koniec")))); // 3

		List<Relationship> relationships = new ArrayList<>();
		relationships.add(new Association(Arrays.asList(new AssociationSide(entitySets.get(0), EnumRelationshipSideRole.CARDINALITY_MANY), new AssociationSide(entitySets.get(1), EnumRelationshipSideRole.CARDINALITY_ONE)), null));
		relationships.add(new Association(Arrays.asList(new AssociationSide(entitySets.get(1), EnumRelationshipSideRole.CARDINALITY_MANY), new AssociationSide(entitySets.get(3), EnumRelationshipSideRole.CARDINALITY_ONE)), null));

		relationships.add(new Association("autobus", Arrays.asList(new AssociationSide(entitySets.get(1), EnumRelationshipSideRole.CARDINALITY_ONE), new AssociationSide(entitySets.get(2), EnumRelationshipSideRole.CARDINALITY_MANY)), null));
		relationships.add(new Association("trolejbus", Arrays.asList(new AssociationSide(entitySets.get(1), EnumRelationshipSideRole.CARDINALITY_ONE), new AssociationSide(entitySets.get(2), EnumRelationshipSideRole.CARDINALITY_MANY)), null));
		relationships.add(new Association("elektricka", Arrays.asList(new AssociationSide(entitySets.get(1), EnumRelationshipSideRole.CARDINALITY_ONE), new AssociationSide(entitySets.get(2), EnumRelationshipSideRole.CARDINALITY_MANY)), null));

		model.addAllRelationships(relationships);
		model.addAllEntitySets(entitySets);
		return model;
	}
	
	/**
	 * NRSR student Vzor
	 * 
	 * @return
	 */
	public static ERModel makeERModel_NRSR_Vzor() {
		ERModel model = new ERModel();

		List<EntitySet> entitySets = new ArrayList<>();
		entitySets.add(new EntitySet("Osoby", new ArrayList<>(Arrays.asList("Meno","Priezvisko")))); // 0
		entitySets.add(new EntitySet("Schodze", new ArrayList<>(Arrays.asList("Od", "Do")))); // 1
		entitySets.add(new EntitySet("Body", new ArrayList<>(Arrays.asList("Kedy", "Poradove cislo")))); // 2
		entitySets.add(new EntitySet("Dokumenty", new ArrayList<>(Arrays.asList("Nazov", "Text")))); // 3
		entitySets.add(new EntitySet("Typy bodov", new ArrayList<>(Arrays.asList("Nazov")))); // 4
		entitySets.add(new EntitySet("Moznosti hlasovania", new ArrayList<>(Arrays.asList("Nazov")))); // 5

		List<Relationship> relationships = new ArrayList<>();
		relationships.add(new Association(Arrays.asList(new AssociationSide(entitySets.get(0), EnumRelationshipSideRole.CARDINALITY_MANY), new AssociationSide(entitySets.get(1), EnumRelationshipSideRole.CARDINALITY_MANY)), null));
		relationships.add(new Association(Arrays.asList(new AssociationSide(entitySets.get(1), EnumRelationshipSideRole.CARDINALITY_ONE), new AssociationSide(entitySets.get(2), EnumRelationshipSideRole.CARDINALITY_MANY)), null));
		relationships.add(new Association(Arrays.asList(new AssociationSide(entitySets.get(2), EnumRelationshipSideRole.CARDINALITY_MANY), new AssociationSide(entitySets.get(3), EnumRelationshipSideRole.CARDINALITY_MANY)), null));
		relationships.add(new Association(Arrays.asList(new AssociationSide(entitySets.get(2), EnumRelationshipSideRole.CARDINALITY_MANY), new AssociationSide(entitySets.get(4), EnumRelationshipSideRole.CARDINALITY_ONE)), null));
		relationships.add(new Association(Arrays.asList(new AssociationSide(entitySets.get(0), EnumRelationshipSideRole.CARDINALITY_MANY), new AssociationSide(entitySets.get(2), EnumRelationshipSideRole.CARDINALITY_MANY), new AssociationSide(entitySets.get(5), EnumRelationshipSideRole.CARDINALITY_ONE)), null));

		model.addAllRelationships(relationships);
		model.addAllEntitySets(entitySets);
		return model;
	}
	
	/**
	 * NRSR student 1
	 * 
	 * @return
	 */
	public static ERModel makeERModel_NRSR_S1() {
		ERModel model = new ERModel();

		List<EntitySet> entitySets = new ArrayList<>();
		entitySets.add(new EntitySet("Osoby", new ArrayList<>(Arrays.asList("Meno","Priezvisko")))); // 0
		entitySets.add(new EntitySet("Schodze", new ArrayList<>(Arrays.asList("Name", "Trvanie")))); // 1
		entitySets.add(new EntitySet("Body", new ArrayList<>(Arrays.asList()))); // 2
		entitySets.add(new EntitySet("Dokumenty", new ArrayList<>(Arrays.asList("Nazov")))); // 3
		entitySets.add(new EntitySet("Typy bodov", new ArrayList<>(Arrays.asList("Minimalny pocet hlasov")))); // 4
		entitySets.add(new EntitySet("Historia hlasov", new ArrayList<>(Arrays.asList("Kedy","Ako")))); // 5
		entitySets.add(new EntitySet("Typy hlasovania", new ArrayList<>(Arrays.asList("Za","Proti","Zdrzal sa","Nehlasoval")))); // 6
		entitySets.add(new EntitySet("NRSR", new ArrayList<>(Arrays.asList()))); // 7

		List<Relationship> relationships = new ArrayList<>();
		relationships.add(new Association(Arrays.asList(new AssociationSide(entitySets.get(7), EnumRelationshipSideRole.CARDINALITY_ONE), new AssociationSide(entitySets.get(0), EnumRelationshipSideRole.CARDINALITY_MANY)), null));
		relationships.add(new Association(Arrays.asList(new AssociationSide(entitySets.get(0), EnumRelationshipSideRole.CARDINALITY_MANY), new AssociationSide(entitySets.get(1), EnumRelationshipSideRole.CARDINALITY_MANY)), null));
		relationships.add(new Association(Arrays.asList(new AssociationSide(entitySets.get(0), EnumRelationshipSideRole.CARDINALITY_ONE), new AssociationSide(entitySets.get(5), EnumRelationshipSideRole.CARDINALITY_ONE)), null));
		relationships.add(new Association(Arrays.asList(new AssociationSide(entitySets.get(0), EnumRelationshipSideRole.CARDINALITY_MANY), new AssociationSide(entitySets.get(6), EnumRelationshipSideRole.CARDINALITY_ONE)), null));
		relationships.add(new Association(Arrays.asList(new AssociationSide(entitySets.get(0), EnumRelationshipSideRole.CARDINALITY_MANY), new AssociationSide(entitySets.get(1), EnumRelationshipSideRole.CARDINALITY_MANY), new AssociationSide(entitySets.get(2), EnumRelationshipSideRole.CARDINALITY_ONE)), null));
		relationships.add(new Association(Arrays.asList(new AssociationSide(entitySets.get(1), EnumRelationshipSideRole.CARDINALITY_ONE), new AssociationSide(entitySets.get(2), EnumRelationshipSideRole.CARDINALITY_MANY)), null));
		relationships.add(new Association(Arrays.asList(new AssociationSide(entitySets.get(2), EnumRelationshipSideRole.CARDINALITY_MANY), new AssociationSide(entitySets.get(3), EnumRelationshipSideRole.CARDINALITY_ONE)), null));
		relationships.add(new Generalization(entitySets.get(2), entitySets.get(4)));
		
		model.addAllRelationships(relationships);
		model.addAllEntitySets(entitySets);
		return model;
	}
	
	/**
	 * NRSR student 2
	 * 
	 * @return
	 */
	public static ERModel makeERModel_NRSR_S2() {
		ERModel model = new ERModel();

		List<EntitySet> entitySets = new ArrayList<>();
		entitySets.add(new EntitySet("Osoby", new ArrayList<>(Arrays.asList("Meno","Priezvisko")))); // 0
		entitySets.add(new EntitySet("Schodze", new ArrayList<>(Arrays.asList("Doba trvania")))); // 1
		entitySets.add(new EntitySet("Body", new ArrayList<>(Arrays.asList("Nazov","Minimalny pocet poslancov")))); // 2
		entitySets.add(new EntitySet("Dokumenty", new ArrayList<>(Arrays.asList("Nazov")))); // 3
		entitySets.add(new EntitySet("Ako hlasovali", new ArrayList<>(Arrays.asList("Hlas")))); // 4
		entitySets.add(new EntitySet("Moznosti hlasovania", new ArrayList<>(Arrays.asList("Nazov")))); // 5
		entitySets.add(new EntitySet("Historia", new ArrayList<>(Arrays.asList("Meno","Priezvisko","Hlasoval")))); // 6

		List<Relationship> relationships = new ArrayList<>();
		relationships.add(new Association(Arrays.asList(new AssociationSide(entitySets.get(0), EnumRelationshipSideRole.CARDINALITY_MANY), new AssociationSide(entitySets.get(1), EnumRelationshipSideRole.CARDINALITY_MANY)), null));
		relationships.add(new Association(Arrays.asList(new AssociationSide(entitySets.get(1), EnumRelationshipSideRole.CARDINALITY_ONE), new AssociationSide(entitySets.get(2), EnumRelationshipSideRole.CARDINALITY_MANY)), null));
		relationships.add(new Association(Arrays.asList(new AssociationSide(entitySets.get(1), EnumRelationshipSideRole.CARDINALITY_MANY), new AssociationSide(entitySets.get(6), EnumRelationshipSideRole.CARDINALITY_MANY)), null));
		relationships.add(new Association(Arrays.asList(new AssociationSide(entitySets.get(2), EnumRelationshipSideRole.CARDINALITY_MANY), new AssociationSide(entitySets.get(3), EnumRelationshipSideRole.CARDINALITY_MANY)), null));
		relationships.add(new Association(Arrays.asList(new AssociationSide(entitySets.get(2), EnumRelationshipSideRole.CARDINALITY_ONE), new AssociationSide(entitySets.get(4), EnumRelationshipSideRole.CARDINALITY_MANY)), null));
		relationships.add(new Association(Arrays.asList(new AssociationSide(entitySets.get(0), EnumRelationshipSideRole.CARDINALITY_ONE), new AssociationSide(entitySets.get(4), EnumRelationshipSideRole.CARDINALITY_MANY)), null));
		relationships.add(new Association(Arrays.asList(new AssociationSide(entitySets.get(5), EnumRelationshipSideRole.CARDINALITY_MANY), new AssociationSide(entitySets.get(4), EnumRelationshipSideRole.CARDINALITY_MANY)), null));
		relationships.add(new Association(Arrays.asList(new AssociationSide(entitySets.get(5), EnumRelationshipSideRole.CARDINALITY_MANY), new AssociationSide(entitySets.get(6), EnumRelationshipSideRole.CARDINALITY_MANY)), null));
		relationships.add(new Association(Arrays.asList(new AssociationSide(entitySets.get(4), EnumRelationshipSideRole.CARDINALITY_ONE), new AssociationSide(entitySets.get(6), EnumRelationshipSideRole.CARDINALITY_ONE)), null));
		
		model.addAllRelationships(relationships);
		model.addAllEntitySets(entitySets);
		return model;
	}
	
	/**
	 * BattleOfTitans Vzor
	 * 
	 * @return
	 */
	public static ERModel makeERModel_BattleOfTitans_Vzor() {
		ERModel model = new ERModel();

		List<EntitySet> entitySets = new ArrayList<>();
		entitySets.add(new EntitySet("Hry", new ArrayList<>(Arrays.asList("Tah")))); // 0
		entitySets.add(new EntitySet("Hracie plochy", new ArrayList<>(Arrays.asList("Pocet riadkov","Pocet stlpcov")))); // 1
		entitySets.add(new EntitySet("Policko", new ArrayList<>(Arrays.asList("Riadok","Stlpec")))); // 2
		entitySets.add(new EntitySet("Pocasia", new ArrayList<>(Arrays.asList("Nazov")))); // 3
		entitySets.add(new EntitySet("Typy policok", new ArrayList<>(Arrays.asList("Nazov")))); // 4
		entitySets.add(new EntitySet("Typy pohonov", new ArrayList<>(Arrays.asList("Nazov")))); // 5
		entitySets.add(new EntitySet("Jednotky", new ArrayList<>(Arrays.asList("Meno","Zivot","Sila utoku","Sila obrany","Aktualne akcne body","Akcne body")))); // 6

		List<Relationship> relationships = new ArrayList<>();
		relationships.add(new Association(Arrays.asList(new AssociationSide(entitySets.get(0), EnumRelationshipSideRole.CARDINALITY_ONE), new AssociationSide(entitySets.get(1), EnumRelationshipSideRole.CARDINALITY_ONE)), null));
		relationships.add(new Association(Arrays.asList(new AssociationSide(entitySets.get(0), EnumRelationshipSideRole.CARDINALITY_MANY), new AssociationSide(entitySets.get(3), EnumRelationshipSideRole.CARDINALITY_ONE)), null));
		relationships.add(new Association(Arrays.asList(new AssociationSide(entitySets.get(1), EnumRelationshipSideRole.CARDINALITY_ONE), new AssociationSide(entitySets.get(2), EnumRelationshipSideRole.CARDINALITY_MANY)), null));
		relationships.add(new Association(Arrays.asList(new AssociationSide(entitySets.get(2), EnumRelationshipSideRole.CARDINALITY_MANY), new AssociationSide(entitySets.get(4), EnumRelationshipSideRole.CARDINALITY_ONE)), null));
		relationships.add(new Association(Arrays.asList(new AssociationSide(entitySets.get(5), EnumRelationshipSideRole.CARDINALITY_ONE), new AssociationSide(entitySets.get(6), EnumRelationshipSideRole.CARDINALITY_MANY)), null));
		relationships.add(new Association(Arrays.asList(new AssociationSide(entitySets.get(3), EnumRelationshipSideRole.CARDINALITY_MANY), new AssociationSide(entitySets.get(4), EnumRelationshipSideRole.CARDINALITY_MANY), new AssociationSide(entitySets.get(5), EnumRelationshipSideRole.CARDINALITY_MANY)), null));
		
		model.addAllRelationships(relationships);
		model.addAllEntitySets(entitySets);
		return model;
	}
	
	/**
	 * BattleOfTitans student 1
	 * 
	 * @return
	 */
	public static ERModel makeERModel_BattleOfTitans_S1() {
		ERModel model = new ERModel();

		List<EntitySet> entitySets = new ArrayList<>();
		entitySets.add(new EntitySet("Hry", new ArrayList<>(Arrays.asList()))); // 0
		entitySets.add(new EntitySet("Hracie plochy", new ArrayList<>(Arrays.asList("Pocet riadkov","Pocet stlpcov")))); // 1
		entitySets.add(new EntitySet("Typy pocasia", new ArrayList<>(Arrays.asList()))); // 2
		entitySets.add(new EntitySet("Typy policok", new ArrayList<>(Arrays.asList()))); // 3
		entitySets.add(new EntitySet("Typy pohonov", new ArrayList<>(Arrays.asList()))); // 4
		entitySets.add(new EntitySet("Jednotky", new ArrayList<>(Arrays.asList("Meno","Sila utoku","Sila obrany")))); // 5

		List<Relationship> relationships = new ArrayList<>();
		relationships.add(new Association("Aktualny stav",Arrays.asList(new AssociationSide(entitySets.get(0), EnumRelationshipSideRole.CARDINALITY_MANY), new AssociationSide(entitySets.get(1), EnumRelationshipSideRole.CARDINALITY_ONE)), new ArrayList<>(Arrays.asList("Tah"))));
		relationships.add(new Association("Pocasie",Arrays.asList(new AssociationSide(entitySets.get(1), EnumRelationshipSideRole.CARDINALITY_MANY), new AssociationSide(entitySets.get(2), EnumRelationshipSideRole.CARDINALITY_MANY)), new ArrayList<>(Arrays.asList("Stlpec","Riadok"))));
		relationships.add(new Association("Umiestnenie",Arrays.asList(new AssociationSide(entitySets.get(1), EnumRelationshipSideRole.CARDINALITY_MANY), new AssociationSide(entitySets.get(3), EnumRelationshipSideRole.CARDINALITY_MANY)), new ArrayList<>(Arrays.asList("Stlpec","Riadok"))));
		relationships.add(new Association("Stav",Arrays.asList(new AssociationSide(entitySets.get(1), EnumRelationshipSideRole.CARDINALITY_MANY), new AssociationSide(entitySets.get(5), EnumRelationshipSideRole.CARDINALITY_MANY)), new ArrayList<>(Arrays.asList("Stlpec","Riadok","Zivot","Akcne Body"))));
		relationships.add(new Association(Arrays.asList(new AssociationSide(entitySets.get(4), EnumRelationshipSideRole.CARDINALITY_ONE), new AssociationSide(entitySets.get(5), EnumRelationshipSideRole.CARDINALITY_MANY)), null));
		relationships.add(new Association("Posun",Arrays.asList(new AssociationSide(entitySets.get(2), EnumRelationshipSideRole.CARDINALITY_MANY), new AssociationSide(entitySets.get(3), EnumRelationshipSideRole.CARDINALITY_MANY), new AssociationSide(entitySets.get(4), EnumRelationshipSideRole.CARDINALITY_MANY)), new ArrayList<>(Arrays.asList("Akcne Body"))));
		
		model.addAllRelationships(relationships);
		model.addAllEntitySets(entitySets);
		return model;
	}
	
	/**
	 * BattleOfTitans student 2
	 * 
	 * @return
	 */
	public static ERModel makeERModel_BattleOfTitans_S2() {
		ERModel model = new ERModel();

		List<EntitySet> entitySets = new ArrayList<>();
		entitySets.add(new EntitySet("Hry", new ArrayList<>(Arrays.asList("ID")))); // 0
		entitySets.add(new EntitySet("Hracie plochy", new ArrayList<>(Arrays.asList("Pocet riadkov","Pocet stlpcov")))); // 1
		entitySets.add(new EntitySet("Pocasia", new ArrayList<>(Arrays.asList("Pocasie 1","Pocasie 2","Pocasie 3")))); // 2
		entitySets.add(new EntitySet("Policka", new ArrayList<>(Arrays.asList("Typ","Jednotka")))); // 3
		entitySets.add(new EntitySet("Typy pohonov", new ArrayList<>(Arrays.asList()))); // 4
		entitySets.add(new EntitySet("Jednotky", new ArrayList<>(Arrays.asList("Meno","Sila utoku","Sila obrany")))); // 5
		entitySets.add(new EntitySet("Pocet tahov", new ArrayList<>(Arrays.asList()))); // 6

		List<Relationship> relationships = new ArrayList<>();
		relationships.add(new Association(Arrays.asList(new AssociationSide(entitySets.get(0), EnumRelationshipSideRole.CARDINALITY_MANY), new AssociationSide(entitySets.get(1), EnumRelationshipSideRole.CARDINALITY_ONE)), null));
		relationships.add(new Association(Arrays.asList(new AssociationSide(entitySets.get(1), EnumRelationshipSideRole.CARDINALITY_MANY), new AssociationSide(entitySets.get(2), EnumRelationshipSideRole.CARDINALITY_MANY)), null));
		relationships.add(new Association(Arrays.asList(new AssociationSide(entitySets.get(1), EnumRelationshipSideRole.CARDINALITY_ONE), new AssociationSide(entitySets.get(3), EnumRelationshipSideRole.CARDINALITY_MANY)), null));
		relationships.add(new Association(Arrays.asList(new AssociationSide(entitySets.get(3), EnumRelationshipSideRole.CARDINALITY_ONE), new AssociationSide(entitySets.get(5), EnumRelationshipSideRole.CARDINALITY_ONE)), null));
		relationships.add(new Association(Arrays.asList(new AssociationSide(entitySets.get(4), EnumRelationshipSideRole.CARDINALITY_ONE), new AssociationSide(entitySets.get(5), EnumRelationshipSideRole.CARDINALITY_MANY)), null));
		relationships.add(new Generalization(entitySets.get(2), entitySets.get(6)));
				
		model.addAllRelationships(relationships);
		model.addAllEntitySets(entitySets);
		return model;
	}
	
	/**
	 * Poistovna vzor
	 * 
	 * @return
	 */
	public static ERModel makeERModel_Poistovna_Vzor() {
		ERModel model = new ERModel();

		List<EntitySet> entitySets = new ArrayList<>();
		entitySets.add(new EntitySet("Konta", new ArrayList<>(Arrays.asList("Meno","Heslo")))); // 0
		entitySets.add(new EntitySet("Zmluvy", new ArrayList<>(Arrays.asList("Od","Do","Mesacny poplatok")))); // 1
		entitySets.add(new EntitySet("Zmluvy na poistenie nehnutelnosti", new ArrayList<>(Arrays.asList("Suma zodpovednost","Suma pohroma","Rozloha obytnej casti","Rozloha pivnice")))); // 2
		entitySets.add(new EntitySet("Zmluvy na zivotne poistenie", new ArrayList<>(Arrays.asList("Suma uraz","Suma smrt","Suma PN")))); // 3
		entitySets.add(new EntitySet("Typy nehnutelnosti", new ArrayList<>(Arrays.asList("Nazov")))); // 4
		entitySets.add(new EntitySet("Osoby", new ArrayList<>(Arrays.asList("Meno","Priezvisko")))); // 5
		entitySets.add(new EntitySet("Adresy", new ArrayList<>(Arrays.asList("Ulica","Cislo","Mesto","PSC","Krajina")))); // 6

		List<Relationship> relationships = new ArrayList<>();
		relationships.add(new Association(Arrays.asList(new AssociationSide(entitySets.get(0), EnumRelationshipSideRole.CARDINALITY_ONE), new AssociationSide(entitySets.get(1), EnumRelationshipSideRole.CARDINALITY_MANY)), null));
		relationships.add(new Generalization(entitySets.get(1), entitySets.get(2)));
		relationships.add(new Generalization(entitySets.get(1), entitySets.get(3)));
		relationships.add(new Association(Arrays.asList(new AssociationSide(entitySets.get(3), EnumRelationshipSideRole.CARDINALITY_ONE), new AssociationSide(entitySets.get(5), EnumRelationshipSideRole.CARDINALITY_ONE)), null));
//		relationships.add(new Association(Arrays.asList(new AssociationSide(entitySets.get(3), EnumRelationshipSideRole.CARDINALITY_ONE), new AssociationSide(entitySets.get(5), EnumRelationshipSideRole.CARDINALITY_ONE)), null));
		relationships.add(new Association(Arrays.asList(new AssociationSide(entitySets.get(6), EnumRelationshipSideRole.CARDINALITY_ONE), new AssociationSide(entitySets.get(5), EnumRelationshipSideRole.CARDINALITY_ONE)), null));
		relationships.add(new Association(Arrays.asList(new AssociationSide(entitySets.get(6), EnumRelationshipSideRole.CARDINALITY_ONE), new AssociationSide(entitySets.get(2), EnumRelationshipSideRole.CARDINALITY_ONE)), null));
		relationships.add(new Association(Arrays.asList(new AssociationSide(entitySets.get(2), EnumRelationshipSideRole.CARDINALITY_MANY), new AssociationSide(entitySets.get(4), EnumRelationshipSideRole.CARDINALITY_ONE)), null));
				
		model.addAllRelationships(relationships);
		model.addAllEntitySets(entitySets);
		return model;
	}
	
	/**
	 * Poistovna student 1
	 * 
	 * @return
	 */
	public static ERModel makeERModel_Poistovna_S1() {
		ERModel model = new ERModel();

		List<EntitySet> entitySets = new ArrayList<>();
		entitySets.add(new EntitySet("Zakaznik", new ArrayList<>(Arrays.asList("Meno","Adresa","Pohlavie","Cislo OP")))); // 0
		entitySets.add(new EntitySet("Poistenie", new ArrayList<>(Arrays.asList("Od","Do","Mesacny poplatok")))); // 1
		entitySets.add(new EntitySet("Zmluvy na poistenie nehnutelnosti", new ArrayList<>(Arrays.asList("Suma zodpovednost","Suma pohroma","Rozloha obytnej casti","Rozloha pivnice")))); // 2
		entitySets.add(new EntitySet("Zmluvy na zivotne poistenie", new ArrayList<>(Arrays.asList("Suma uraz","Suma smrt","Suma PN")))); // 3
		entitySets.add(new EntitySet("Nehnutelnosti", new ArrayList<>(Arrays.asList("Adresa","Typ","Rozloha obytnej casti","Rozloha pivnice")))); // 4
		entitySets.add(new EntitySet("Osoby", new ArrayList<>(Arrays.asList("Meno","Priezvisko","Pohlavie","Narodnost","Adresa")))); // 5
		entitySets.add(new EntitySet("Konto", new ArrayList<>(Arrays.asList("Meno","Heslo")))); // 6

		List<Relationship> relationships = new ArrayList<>();
		relationships.add(new Association(Arrays.asList(new AssociationSide(entitySets.get(0), EnumRelationshipSideRole.CARDINALITY_ONE), new AssociationSide(entitySets.get(3), EnumRelationshipSideRole.CARDINALITY_MANY)), null));
		relationships.add(new Generalization(entitySets.get(1), entitySets.get(2)));
		relationships.add(new Generalization(entitySets.get(1), entitySets.get(3)));
		relationships.add(new Association(Arrays.asList(new AssociationSide(entitySets.get(1), EnumRelationshipSideRole.CARDINALITY_MANY), new AssociationSide(entitySets.get(6), EnumRelationshipSideRole.CARDINALITY_ONE)), null));
		relationships.add(new Association(Arrays.asList(new AssociationSide(entitySets.get(5), EnumRelationshipSideRole.CARDINALITY_ONE), new AssociationSide(entitySets.get(3), EnumRelationshipSideRole.CARDINALITY_MANY)), null));
		relationships.add(new Association(Arrays.asList(new AssociationSide(entitySets.get(0), EnumRelationshipSideRole.CARDINALITY_ONE), new AssociationSide(entitySets.get(2), EnumRelationshipSideRole.CARDINALITY_MANY)), null));
		
		model.addAllRelationships(relationships);
		model.addAllEntitySets(entitySets);
		return model;
	}
	
	/**
	 * Poistovna student 2
	 * 
	 * @return
	 */
	public static ERModel makeERModel_Poistovna_S2() {
		ERModel model = new ERModel();

		List<EntitySet> entitySets = new ArrayList<>();
		entitySets.add(new EntitySet("Konta", new ArrayList<>(Arrays.asList("Meno","Heslo")))); // 0
		entitySets.add(new EntitySet("Zmluva", new ArrayList<>(Arrays.asList()))); // 1
		entitySets.add(new EntitySet("Typy poistenia", new ArrayList<>(Arrays.asList("Nazov")))); // 2
		entitySets.add(new EntitySet("Objekt poistenia", new ArrayList<>(Arrays.asList("Nazov","Vyska")))); // 3
		
		List<Relationship> relationships = new ArrayList<>();
		relationships.add(new Association("Uzatvara",Arrays.asList(new AssociationSide(entitySets.get(0), EnumRelationshipSideRole.CARDINALITY_ONE), new AssociationSide(entitySets.get(1), EnumRelationshipSideRole.CARDINALITY_MANY)), new ArrayList<>(Arrays.asList("Odkedy","Dokedy"))));
		relationships.add(new Association(Arrays.asList(new AssociationSide(entitySets.get(1), EnumRelationshipSideRole.CARDINALITY_MANY), new AssociationSide(entitySets.get(2), EnumRelationshipSideRole.CARDINALITY_ONE)), null));
		relationships.add(new Association(Arrays.asList(new AssociationSide(entitySets.get(1), EnumRelationshipSideRole.CARDINALITY_MANY), new AssociationSide(entitySets.get(3), EnumRelationshipSideRole.CARDINALITY_ONE)), null));
		
		model.addAllRelationships(relationships);
		model.addAllEntitySets(entitySets);
		return model;
	}
	
	/**
	 * UFO Vzor
	 * 
	 * @return
	 */
	public static ERModel makeERModel_UFO_Vzor() {
		ERModel model = new ERModel();

		List<EntitySet> entitySets = new ArrayList<>();
		entitySets.add(new EntitySet("Modely lietadiel", new ArrayList<>(Arrays.asList("Kod")))); // 0
		entitySets.add(new EntitySet("Lietadla", new ArrayList<>(Arrays.asList("Seriove cislo","Meno")))); // 1
		entitySets.add(new EntitySet("Stihacie modely", new ArrayList<>(Arrays.asList()))); // 2
		entitySets.add(new EntitySet("Dopravne modely", new ArrayList<>(Arrays.asList("Kapacita osob","Kapacita hmotnost","Kapacita objem")))); // 3
		entitySets.add(new EntitySet("Typy rakiet", new ArrayList<>(Arrays.asList("Nazov")))); // 4
		entitySets.add(new EntitySet("Vojaci", new ArrayList<>(Arrays.asList("Meno","Aktualny zivot","Maximalny zivot","Rychlost","Sila","Obratnost","Inteligencia")))); // 5
		entitySets.add(new EntitySet("Role vojakov", new ArrayList<>(Arrays.asList("Modifikator maximalneho zivota","Modifikator rychlosti","Modfikator sily","Modifikator obratnosti","Modifikator inteligencie","Nazov prace")))); // 6
		
		List<Relationship> relationships = new ArrayList<>();
		relationships.add(new Generalization(entitySets.get(0), entitySets.get(2)));
		relationships.add(new Generalization(entitySets.get(0), entitySets.get(3)));
		relationships.add(new Association(Arrays.asList(new AssociationSide(entitySets.get(0), EnumRelationshipSideRole.CARDINALITY_ONE), new AssociationSide(entitySets.get(1), EnumRelationshipSideRole.CARDINALITY_MANY)), null));
		relationships.add(new Association("Unesie",Arrays.asList(new AssociationSide(entitySets.get(2), EnumRelationshipSideRole.CARDINALITY_ONE), new AssociationSide(entitySets.get(4), EnumRelationshipSideRole.CARDINALITY_MANY)), new ArrayList<>(Arrays.asList("Mnozstvo"))));
		relationships.add(new Association("Nesie",Arrays.asList(new AssociationSide(entitySets.get(1), EnumRelationshipSideRole.CARDINALITY_ONE), new AssociationSide(entitySets.get(4), EnumRelationshipSideRole.CARDINALITY_MANY)), new ArrayList<>(Arrays.asList("Pocet"))));
		relationships.add(new Association("Sluzi na",Arrays.asList(new AssociationSide(entitySets.get(1), EnumRelationshipSideRole.CARDINALITY_ONE), new AssociationSide(entitySets.get(5), EnumRelationshipSideRole.CARDINALITY_MANY), new AssociationSide(entitySets.get(6), EnumRelationshipSideRole.CARDINALITY_ONE)), null));
		
		model.addAllRelationships(relationships);
		model.addAllEntitySets(entitySets);
		return model;
	}
	
	/**
	 * UFO student 1
	 * 
	 * @return
	 */
	public static ERModel makeERModel_UFO_S1() {
		ERModel model = new ERModel();

		List<EntitySet> entitySets = new ArrayList<>();
		entitySets.add(new EntitySet("Obranny system", new ArrayList<>(Arrays.asList("Lietadlo","Vojak")))); // 0
		entitySets.add(new EntitySet("Lietadla", new ArrayList<>(Arrays.asList("Seriove cislo","Meno","Model","Druh")))); // 1
		entitySets.add(new EntitySet("Stihacie modely", new ArrayList<>(Arrays.asList("Pocet rakiet","Typ rakiet")))); // 2
		entitySets.add(new EntitySet("Dopravne modely", new ArrayList<>(Arrays.asList("Kapacita osob","Kapacita hmotnost","Kapacita objem")))); // 3
		entitySets.add(new EntitySet("Raketa", new ArrayList<>(Arrays.asList("Hmotnost")))); // 4
		entitySets.add(new EntitySet("Vojaci", new ArrayList<>(Arrays.asList("Meno","Aktualny zivot","Maximalny zivot","Rychlost","Sila","Obratnost","Inteligencia","Rola")))); // 5
		entitySets.add(new EntitySet("Druh lietadla", new ArrayList<>(Arrays.asList("Stihacie","Dopravne")))); // 6
		
		List<Relationship> relationships = new ArrayList<>();
		relationships.add(new Association(Arrays.asList(new AssociationSide(entitySets.get(0), EnumRelationshipSideRole.CARDINALITY_ONE), new AssociationSide(entitySets.get(5), EnumRelationshipSideRole.CARDINALITY_MANY)), null));
		relationships.add(new Association(Arrays.asList(new AssociationSide(entitySets.get(0), EnumRelationshipSideRole.CARDINALITY_ONE), new AssociationSide(entitySets.get(1), EnumRelationshipSideRole.CARDINALITY_MANY)), null));
		relationships.add(new Association(Arrays.asList(new AssociationSide(entitySets.get(1), EnumRelationshipSideRole.CARDINALITY_MANY), new AssociationSide(entitySets.get(6), EnumRelationshipSideRole.CARDINALITY_ONE)), null));
		relationships.add(new Association(Arrays.asList(new AssociationSide(entitySets.get(6), EnumRelationshipSideRole.CARDINALITY_MANY), new AssociationSide(entitySets.get(2), EnumRelationshipSideRole.CARDINALITY_ONE)), null));
		relationships.add(new Association(Arrays.asList(new AssociationSide(entitySets.get(6), EnumRelationshipSideRole.CARDINALITY_MANY), new AssociationSide(entitySets.get(3), EnumRelationshipSideRole.CARDINALITY_ONE)), null));
		relationships.add(new Association(Arrays.asList(new AssociationSide(entitySets.get(2), EnumRelationshipSideRole.CARDINALITY_MANY), new AssociationSide(entitySets.get(4), EnumRelationshipSideRole.CARDINALITY_MANY)), null));
		
		model.addAllRelationships(relationships);
		model.addAllEntitySets(entitySets);
		return model;
	}
	
	/**
	 * UFO student 2
	 * 
	 * @return
	 */
	public static ERModel makeERModel_UFO_S2() {
		ERModel model = new ERModel();

		List<EntitySet> entitySets = new ArrayList<>();
		entitySets.add(new EntitySet("Modely lietadiel", new ArrayList<>(Arrays.asList("Nazov")))); // 0
		entitySets.add(new EntitySet("Lietadla", new ArrayList<>(Arrays.asList("Seriove cislo","Meno")))); // 1
		entitySets.add(new EntitySet("Stihacie modely", new ArrayList<>(Arrays.asList()))); // 2
		entitySets.add(new EntitySet("Dopravne modely", new ArrayList<>(Arrays.asList("Kapacita osob","Kapacita hmotnost","Kapacita objem")))); // 3
		entitySets.add(new EntitySet("Naklad", new ArrayList<>(Arrays.asList("Typy rakiet","Pocet")))); // 4
		entitySets.add(new EntitySet("Vojaci", new ArrayList<>(Arrays.asList("Meno","Aktualny zivot","Maximalny zivot","Rychlost","Sila","Obratnost","Inteligencia")))); // 5
		entitySets.add(new EntitySet("Role vojakov", new ArrayList<>(Arrays.asList("Modifikator maximalneho zivota","Modifikator rychlosti","Modfikator sily","Modifikator obratnosti","Modifikator inteligencie","Nazov prace")))); // 6
		
		List<Relationship> relationships = new ArrayList<>();
		relationships.add(new Generalization(entitySets.get(0), entitySets.get(2)));
		relationships.add(new Generalization(entitySets.get(0), entitySets.get(3)));
		relationships.add(new Association(Arrays.asList(new AssociationSide(entitySets.get(0), EnumRelationshipSideRole.CARDINALITY_ONE), new AssociationSide(entitySets.get(1), EnumRelationshipSideRole.CARDINALITY_MANY)), null));
		relationships.add(new Association("Nesie",Arrays.asList(new AssociationSide(entitySets.get(1), EnumRelationshipSideRole.CARDINALITY_ONE), new AssociationSide(entitySets.get(4), EnumRelationshipSideRole.CARDINALITY_MANY)), null));
		relationships.add(new Association("Unesie",Arrays.asList(new AssociationSide(entitySets.get(2), EnumRelationshipSideRole.CARDINALITY_ONE), new AssociationSide(entitySets.get(4), EnumRelationshipSideRole.CARDINALITY_MANY)), null));
		relationships.add(new Association("Sluzi na",Arrays.asList(new AssociationSide(entitySets.get(1), EnumRelationshipSideRole.CARDINALITY_ONE), new AssociationSide(entitySets.get(5), EnumRelationshipSideRole.CARDINALITY_ONE), new AssociationSide(entitySets.get(6), EnumRelationshipSideRole.CARDINALITY_ONE)), null));
		
		model.addAllRelationships(relationships);
		model.addAllEntitySets(entitySets);
		return model;
	}

	public static boolean containsTransformation(List<Transformation> transformations, EnumTransformation code) {
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

