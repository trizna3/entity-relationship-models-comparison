package tests;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import common.Utils;
import common.enums.EnumTransformation;
import common.enums.Enums;
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
		relationships.add(new Association(Arrays.asList(new AssociationSide(entitySets.get(0), Enums.CARDINALITY_ONE), new AssociationSide(entitySets.get(1), Enums.CARDINALITY_MANY)), null));
		relationships.add(new Association(Arrays.asList(new AssociationSide(entitySets.get(1), Enums.CARDINALITY_ONE), new AssociationSide(entitySets.get(2), Enums.CARDINALITY_MANY)), null));
		relationships.add(new Association(Arrays.asList(new AssociationSide(entitySets.get(2), Enums.CARDINALITY_ONE), new AssociationSide(entitySets.get(3), Enums.CARDINALITY_MANY)), null));
		relationships.add(new Association(Arrays.asList(new AssociationSide(entitySets.get(2), Enums.CARDINALITY_MANY), new AssociationSide(entitySets.get(4), Enums.CARDINALITY_ONE)), null));
		relationships.add(new Association(Arrays.asList(new AssociationSide(entitySets.get(3), Enums.CARDINALITY_MANY), new AssociationSide(entitySets.get(5), Enums.CARDINALITY_ONE)), null));
		relationships.add(new Association(Arrays.asList(new AssociationSide(entitySets.get(4), Enums.CARDINALITY_ONE), new AssociationSide(entitySets.get(5), Enums.CARDINALITY_ONE)), null));

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
		relationships.add(new Association(Arrays.asList(new AssociationSide(entitySets.get(0), Enums.CARDINALITY_MANY), new AssociationSide(entitySets.get(1), Enums.CARDINALITY_MANY)), null));
		relationships.add(new Association(Arrays.asList(new AssociationSide(entitySets.get(1), Enums.CARDINALITY_ONE), new AssociationSide(entitySets.get(2), Enums.CARDINALITY_MANY)), null));
		relationships.add(new Association(Arrays.asList(new AssociationSide(entitySets.get(2), Enums.CARDINALITY_MANY), new AssociationSide(entitySets.get(3), Enums.CARDINALITY_MANY)), null));
		relationships.add(new Association(Arrays.asList(new AssociationSide(entitySets.get(2), Enums.CARDINALITY_MANY), new AssociationSide(entitySets.get(4), Enums.CARDINALITY_ONE)), null));
		relationships.add(new Association(Arrays.asList(new AssociationSide(entitySets.get(0), Enums.CARDINALITY_MANY), new AssociationSide(entitySets.get(2), Enums.CARDINALITY_MANY), new AssociationSide(entitySets.get(5), Enums.CARDINALITY_ONE)), null));

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
		relationships.add(new Association(Arrays.asList(new AssociationSide(entitySets.get(0), Enums.CARDINALITY_ONE), new AssociationSide(entitySets.get(1), Enums.CARDINALITY_MANY)), null));
		relationships.add(new Association(Arrays.asList(new AssociationSide(entitySets.get(1), Enums.CARDINALITY_ONE), new AssociationSide(entitySets.get(2), Enums.CARDINALITY_MANY)), null));
		relationships.add(new Association(Arrays.asList(new AssociationSide(entitySets.get(2), Enums.CARDINALITY_ONE), new AssociationSide(entitySets.get(3), Enums.CARDINALITY_MANY)), null));
		relationships.add(new Association(Arrays.asList(new AssociationSide(entitySets.get(2), Enums.CARDINALITY_MANY), new AssociationSide(entitySets.get(4), Enums.CARDINALITY_ONE)), null));
		relationships.add(new Association(Arrays.asList(new AssociationSide(entitySets.get(3), Enums.CARDINALITY_MANY), new AssociationSide(entitySets.get(5), Enums.CARDINALITY_ONE)), null));
		relationships.add(new Association(Arrays.asList(new AssociationSide(entitySets.get(4), Enums.CARDINALITY_ONE), new AssociationSide(entitySets.get(5), Enums.CARDINALITY_ONE)), null));
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
		relationships.add(new Association(Arrays.asList(new AssociationSide(entitySets.get(0), Enums.CARDINALITY_ONE), new AssociationSide(entitySets.get(1), Enums.CARDINALITY_MANY)), null));
		relationships.add(new Association(Arrays.asList(new AssociationSide(entitySets.get(1), Enums.CARDINALITY_ONE), new AssociationSide(entitySets.get(2), Enums.CARDINALITY_MANY)), null));
		relationships.add(new Association(Arrays.asList(new AssociationSide(entitySets.get(2), Enums.CARDINALITY_ONE), new AssociationSide(entitySets.get(3), Enums.CARDINALITY_ONE)), null));
		relationships.add(new Association(Arrays.asList(new AssociationSide(entitySets.get(3), Enums.CARDINALITY_MANY), new AssociationSide(entitySets.get(4), Enums.CARDINALITY_ONE)), null));

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
		relationships.add(new Association(Arrays.asList(new AssociationSide(entitySets.get(0), Enums.CARDINALITY_ONE), new AssociationSide(entitySets.get(1), Enums.CARDINALITY_MANY)), null));
		relationships.add(new Association(Arrays.asList(new AssociationSide(entitySets.get(1), Enums.CARDINALITY_MANY), new AssociationSide(entitySets.get(2), Enums.CARDINALITY_ONE)), null));
		relationships.add(new Association(Arrays.asList(new AssociationSide(entitySets.get(2), Enums.CARDINALITY_ONE), new AssociationSide(entitySets.get(3), Enums.CARDINALITY_ONE)), null));
		relationships.add(new Association(Arrays.asList(new AssociationSide(entitySets.get(3), Enums.CARDINALITY_MANY), new AssociationSide(entitySets.get(4), Enums.CARDINALITY_ONE)), null));

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
		relationships.add(new Association(Arrays.asList(new AssociationSide(entitySets.get(1), Enums.CARDINALITY_ONE), new AssociationSide(entitySets.get(2), Enums.CARDINALITY_MANY)), null));
		relationships.add(new Association(Arrays.asList(new AssociationSide(entitySets.get(2), Enums.CARDINALITY_MANY), new AssociationSide(entitySets.get(4), Enums.CARDINALITY_ONE)), null));
		relationships.add(new Association(Arrays.asList(new AssociationSide(entitySets.get(0), Enums.CARDINALITY_MANY), new AssociationSide(entitySets.get(2), Enums.CARDINALITY_MANY), new AssociationSide(entitySets.get(5), Enums.CARDINALITY_ONE)), null));
		relationships.add(new Association(Arrays.asList(new AssociationSide(entitySets.get(0), Enums.CARDINALITY_ONE), new AssociationSide(entitySets.get(6), Enums.CARDINALITY_MANY)), null));
		relationships.add(new Association(Arrays.asList(new AssociationSide(entitySets.get(1), Enums.CARDINALITY_ONE), new AssociationSide(entitySets.get(6), Enums.CARDINALITY_MANY)), null));
		relationships.add(new Association(Arrays.asList(new AssociationSide(entitySets.get(2), Enums.CARDINALITY_ONE), new AssociationSide(entitySets.get(7), Enums.CARDINALITY_MANY)), null));
		relationships.add(new Association(Arrays.asList(new AssociationSide(entitySets.get(3), Enums.CARDINALITY_ONE), new AssociationSide(entitySets.get(7), Enums.CARDINALITY_MANY)), null));

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
		relationships.add(new Association(Arrays.asList(new AssociationSide(entitySets.get(0), Enums.CARDINALITY_ONE), new AssociationSide(entitySets.get(1), Enums.CARDINALITY_MANY)), null));
		((Association) relationships.get(0)).addAttribute(new Attribute("Test attribute 1"));
		((Association) relationships.get(0)).addAttribute(new Attribute("Test attribute 2"));
		relationships.add(new Association(Arrays.asList(new AssociationSide(entitySets.get(1), Enums.CARDINALITY_ONE), new AssociationSide(entitySets.get(2), Enums.CARDINALITY_MANY)), null));
		relationships.add(new Association(Arrays.asList(new AssociationSide(entitySets.get(2), Enums.CARDINALITY_ONE), new AssociationSide(entitySets.get(3), Enums.CARDINALITY_MANY)), null));
		((Association) relationships.get(2)).addAttribute(new Attribute("Test attribute 3"));
		relationships.add(new Association(Arrays.asList(new AssociationSide(entitySets.get(2), Enums.CARDINALITY_MANY), new AssociationSide(entitySets.get(4), Enums.CARDINALITY_ONE)), null));
		relationships.add(new Association(Arrays.asList(new AssociationSide(entitySets.get(3), Enums.CARDINALITY_MANY), new AssociationSide(entitySets.get(5), Enums.CARDINALITY_ONE)), null));
		((Association) relationships.get(4)).addAttribute(new Attribute("Test attribute 4"));
		((Association) relationships.get(4)).addAttribute(new Attribute("Test attribute 5"));
		((Association) relationships.get(4)).addAttribute(new Attribute("Test attribute 6"));
		relationships.add(new Association(Arrays.asList(new AssociationSide(entitySets.get(4), Enums.CARDINALITY_ONE), new AssociationSide(entitySets.get(5), Enums.CARDINALITY_ONE)), null));

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
		relationships.add(new Association(Arrays.asList(new AssociationSide(entitySets.get(0), Enums.CARDINALITY_ONE), new AssociationSide(entitySets.get(1), Enums.CARDINALITY_MANY)), null));
		((Association) relationships.get(0)).setName("Katalyzator");
		((Association) relationships.get(0)).addAttribute(new Attribute("Hmotnost katalyzatora"));
		relationships.add(new Association(Arrays.asList(new AssociationSide(entitySets.get(0), Enums.CARDINALITY_ONE), new AssociationSide(entitySets.get(1), Enums.CARDINALITY_MANY)), null));
		((Association) relationships.get(1)).setName("Reaktant 1");
		((Association) relationships.get(1)).addAttribute(new Attribute("Hmotnost reaktantu A"));
		relationships.add(new Association(Arrays.asList(new AssociationSide(entitySets.get(0), Enums.CARDINALITY_ONE), new AssociationSide(entitySets.get(1), Enums.CARDINALITY_MANY)), null));
		((Association) relationships.get(2)).setName("Reaktant 2");
		((Association) relationships.get(2)).addAttribute(new Attribute("Hmotnost reaktantu B"));
		relationships.add(new Association(Arrays.asList(new AssociationSide(entitySets.get(0), Enums.CARDINALITY_ONE), new AssociationSide(entitySets.get(1), Enums.CARDINALITY_MANY)), null));
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
	public static ERModel makeERModel_Dennik_S1() {
		ERModel model = new ERModel();

		List<EntitySet> entitySets = new ArrayList<>();
		entitySets.add(new EntitySet("Chemicke latky", new ArrayList<>(Arrays.asList("Nazov", "Vzorec")))); // 0
		entitySets.add(new EntitySet("Chemicke reakcie", new ArrayList<>(Arrays.asList("Rovnica", "Hmotnost reaktantu A", "Hmotnost reaktantu B", "Hmotnost katalyzatora")))); // 1

		List<Relationship> relationships = new ArrayList<>();
		relationships.add(new Association(Arrays.asList(new AssociationSide(entitySets.get(0), Enums.CARDINALITY_ONE), new AssociationSide(entitySets.get(1), Enums.CARDINALITY_MANY)), null));
		relationships.add(new Association(Arrays.asList(new AssociationSide(entitySets.get(0), Enums.CARDINALITY_ONE), new AssociationSide(entitySets.get(1), Enums.CARDINALITY_MANY)), null));
		relationships.add(new Association(Arrays.asList(new AssociationSide(entitySets.get(0), Enums.CARDINALITY_ONE), new AssociationSide(entitySets.get(1), Enums.CARDINALITY_MANY)), null));
		relationships.add(new Association(Arrays.asList(new AssociationSide(entitySets.get(0), Enums.CARDINALITY_ONE), new AssociationSide(entitySets.get(1), Enums.CARDINALITY_MANY)), null));

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
		relationships.add(new Association(Arrays.asList(new AssociationSide(entitySets.get(0), Enums.CARDINALITY_MANY), new AssociationSide(entitySets.get(1), Enums.CARDINALITY_ONE)), null));
		relationships.add(new Association(Arrays.asList(new AssociationSide(entitySets.get(0), Enums.CARDINALITY_ONE), new AssociationSide(entitySets.get(1), Enums.CARDINALITY_MANY)), null));
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
		relationships.add(new Association(Arrays.asList(new AssociationSide(entitySets.get(0), Enums.CARDINALITY_ONE), new AssociationSide(entitySets.get(1), Enums.CARDINALITY_MANY)), null));
		relationships.add(new Association(Arrays.asList(new AssociationSide(entitySets.get(0), Enums.CARDINALITY_MANY), new AssociationSide(entitySets.get(2), Enums.CARDINALITY_MANY)), null));
		relationships.add(new Association("Hodnoty specialnych atributov", Arrays.asList(new AssociationSide(entitySets.get(1), Enums.CARDINALITY_MANY), new AssociationSide(entitySets.get(2), Enums.CARDINALITY_MANY)), new ArrayList<String>(Arrays.asList("Hodnota"))));

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
		relationships.add(new Association(Arrays.asList(new AssociationSide(entitySets.get(0), Enums.CARDINALITY_MANY), new AssociationSide(entitySets.get(1), Enums.CARDINALITY_ONE)), null));
		relationships.add(new Association(Arrays.asList(new AssociationSide(entitySets.get(1), Enums.CARDINALITY_ONE), new AssociationSide(entitySets.get(2), Enums.CARDINALITY_MANY)), null));

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
		relationships.add(new Association(Arrays.asList(new AssociationSide(entitySets.get(0), Enums.CARDINALITY_ONE), new AssociationSide(entitySets.get(1), Enums.CARDINALITY_ONE), new AssociationSide(entitySets.get(2), Enums.CARDINALITY_ONE)), null));

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
		relationships.add(new Association("osoba 1", Arrays.asList(new AssociationSide(entitySets.get(0), Enums.CARDINALITY_ONE), new AssociationSide(entitySets.get(1), Enums.CARDINALITY_MANY)), null));
		relationships.add(new Association("osoba 2", Arrays.asList(new AssociationSide(entitySets.get(0), Enums.CARDINALITY_ONE), new AssociationSide(entitySets.get(1), Enums.CARDINALITY_MANY)), null));

		relationships.add(new Association(Arrays.asList(new AssociationSide(entitySets.get(1), Enums.CARDINALITY_MANY), new AssociationSide(entitySets.get(2), Enums.CARDINALITY_ONE)), null));

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
		relationships.add(new Association(Arrays.asList(new AssociationSide(entitySets.get(0), Enums.CARDINALITY_ONE), new AssociationSide(entitySets.get(1), Enums.CARDINALITY_MANY)), null));
		relationships.add(new Association(Arrays.asList(new AssociationSide(entitySets.get(0), Enums.CARDINALITY_ONE), new AssociationSide(entitySets.get(1), Enums.CARDINALITY_MANY)), null));

		relationships.add(new Association(Arrays.asList(new AssociationSide(entitySets.get(1), Enums.CARDINALITY_MANY), new AssociationSide(entitySets.get(2), Enums.CARDINALITY_ONE)), null));

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
		entitySets.add(new EntitySet("Osoby", new ArrayList<>(Arrays.asList("Meno", "Priezvisko", "Rok narodenia")))); // 1
		entitySets.add(new EntitySet("Vztahy", new ArrayList<>(Arrays.asList("Nazov", "Odkedy")))); // 2

		List<Relationship> relationships = new ArrayList<>();
		relationships.add(new Association(Arrays.asList(new AssociationSide(entitySets.get(0), Enums.CARDINALITY_ONE), new AssociationSide(entitySets.get(1), Enums.CARDINALITY_ONE), new AssociationSide(entitySets.get(2), Enums.CARDINALITY_ONE)), null));

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
		relationships.add(new Association(Arrays.asList(new AssociationSide(entitySets.get(0), Enums.CARDINALITY_ONE), new AssociationSide(entitySets.get(1), Enums.CARDINALITY_MANY)), null));
		relationships.add(new Association(Arrays.asList(new AssociationSide(entitySets.get(1), Enums.CARDINALITY_ONE), new AssociationSide(entitySets.get(2), Enums.CARDINALITY_MANY)), null));
		relationships.add(new Association(Arrays.asList(new AssociationSide(entitySets.get(2), Enums.CARDINALITY_ONE), new AssociationSide(entitySets.get(3), Enums.CARDINALITY_ONE)), null));
		relationships.add(new Association(Arrays.asList(new AssociationSide(entitySets.get(3), Enums.CARDINALITY_MANY), new AssociationSide(entitySets.get(4), Enums.CARDINALITY_ONE)), null));

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
		relationships.add(new Association(Arrays.asList(new AssociationSide(entitySets.get(5), Enums.CARDINALITY_ONE), new AssociationSide(entitySets.get(0), Enums.CARDINALITY_MANY)), null));
		relationships.add(new Association(Arrays.asList(new AssociationSide(entitySets.get(0), Enums.CARDINALITY_ONE), new AssociationSide(entitySets.get(1), Enums.CARDINALITY_MANY)), null));
		relationships.add(new Association(Arrays.asList(new AssociationSide(entitySets.get(1), Enums.CARDINALITY_ONE), new AssociationSide(entitySets.get(3), Enums.CARDINALITY_ONE)), null));
		relationships.add(new Association(Arrays.asList(new AssociationSide(entitySets.get(1), Enums.CARDINALITY_ONE), new AssociationSide(entitySets.get(4), Enums.CARDINALITY_ONE)), null));
		relationships.add(new Association(Arrays.asList(new AssociationSide(entitySets.get(4), Enums.CARDINALITY_ONE), new AssociationSide(entitySets.get(2), Enums.CARDINALITY_ONE)), null));

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
		relationships.add(new Association("Zahrna", Arrays.asList(new AssociationSide(entitySets.get(0), Enums.CARDINALITY_MANY), new AssociationSide(entitySets.get(1), Enums.CARDINALITY_MANY)), new ArrayList<>(Arrays.asList("Cas po zastavku"))));
		relationships.add(new Association("Jazdi na linke", Arrays.asList(new AssociationSide(entitySets.get(1), Enums.CARDINALITY_MANY), new AssociationSide(entitySets.get(2), Enums.CARDINALITY_MANY)), new ArrayList<>(Arrays.asList("Cas odchodu z prvej zastavky"))));
		relationships.add(new Association(Arrays.asList(new AssociationSide(entitySets.get(1), Enums.CARDINALITY_MANY), new AssociationSide(entitySets.get(3), Enums.CARDINALITY_ONE)), null));
		relationships.add(new Association(Arrays.asList(new AssociationSide(entitySets.get(2), Enums.CARDINALITY_MANY), new AssociationSide(entitySets.get(3), Enums.CARDINALITY_ONE)), null));

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
		relationships.add(new Association("L_L", Arrays.asList(new AssociationSide(entitySets.get(0), Enums.CARDINALITY_MANY), new AssociationSide(entitySets.get(1), Enums.CARDINALITY_MANY)), new ArrayList<>(Arrays.asList("Cas po zastavku"))));
		relationships.add(new Association(Arrays.asList(new AssociationSide(entitySets.get(1), Enums.CARDINALITY_MANY), new AssociationSide(entitySets.get(2), Enums.CARDINALITY_ONE)), null));
		relationships.add(new Association(Arrays.asList(new AssociationSide(entitySets.get(1), Enums.CARDINALITY_MANY), new AssociationSide(entitySets.get(3), Enums.CARDINALITY_ONE)), null));
		relationships.add(new Association(Arrays.asList(new AssociationSide(entitySets.get(2), Enums.CARDINALITY_ONE), new AssociationSide(entitySets.get(3), Enums.CARDINALITY_MANY)), null));

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
		relationships.add(new Association(Arrays.asList(new AssociationSide(entitySets.get(0), Enums.CARDINALITY_MANY), new AssociationSide(entitySets.get(1), Enums.CARDINALITY_ONE)), null));
		relationships.add(new Association(Arrays.asList(new AssociationSide(entitySets.get(1), Enums.CARDINALITY_MANY), new AssociationSide(entitySets.get(3), Enums.CARDINALITY_ONE)), null));

		relationships.add(new Association("autobus", Arrays.asList(new AssociationSide(entitySets.get(1), Enums.CARDINALITY_ONE), new AssociationSide(entitySets.get(2), Enums.CARDINALITY_MANY)), null));
		relationships.add(new Association("trolejbus", Arrays.asList(new AssociationSide(entitySets.get(1), Enums.CARDINALITY_ONE), new AssociationSide(entitySets.get(2), Enums.CARDINALITY_MANY)), null));
		relationships.add(new Association("elektricka", Arrays.asList(new AssociationSide(entitySets.get(1), Enums.CARDINALITY_ONE), new AssociationSide(entitySets.get(2), Enums.CARDINALITY_MANY)), null));

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
