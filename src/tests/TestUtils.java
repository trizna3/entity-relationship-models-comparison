package tests;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import common.enums.Enum;
import entityRelationshipModel.Association;
import entityRelationshipModel.AssociationSide;
import entityRelationshipModel.ERModel;
import entityRelationshipModel.EntitySet;
import entityRelationshipModel.Generalization;
import entityRelationshipModel.Relationship;

/**
 * @author - Adam Trizna
 */

public class TestUtils {

	public static List<ERModel> getERModels() {
		return new ArrayList<ERModel>(Arrays.asList(
				makeERModel1(),
				makeERModel2(),
				makeERModel3(),
				makeERModel4()
				));
	}
	
	private static ERModel makeERModel1(){
		ERModel model = new ERModel();
		
		List<EntitySet> entitySets = new ArrayList<>();
		entitySets.add(new EntitySet("Budovy", new ArrayList<>(Arrays.asList("Ulica","Cislo","Mesto","PSC"))));		// 0
		entitySets.add(new EntitySet("Izby", new ArrayList<>(Arrays.asList("Cislo","C.Poschodia","Kapacita","Poplatok"))));		// 1
		entitySets.add(new EntitySet("Zmluvy", new ArrayList<>(Arrays.asList("Od","Do"))));		// 2
		entitySets.add(new EntitySet("Platby", new ArrayList<>(Arrays.asList("Vyska","Datum"))));		// 3
		entitySets.add(new EntitySet("Studenti", new ArrayList<>(Arrays.asList("Meno","Priezvisko","C.OP"))));	// 4
		entitySets.add(new EntitySet("Konta", new ArrayList<>(Arrays.asList("Kredit"))));		// 5
		
		List<Relationship> relationships = new ArrayList<>();
		relationships.add(new Association(new AssociationSide[] {new AssociationSide(entitySets.get(0),Enum.CARDINALITY_ONE),new AssociationSide(entitySets.get(1),Enum.CARDINALITY_MANY)}, null));
		relationships.add(new Association(new AssociationSide[] {new AssociationSide(entitySets.get(1),Enum.CARDINALITY_ONE),new AssociationSide(entitySets.get(2),Enum.CARDINALITY_MANY)}, null));
		relationships.add(new Association(new AssociationSide[] {new AssociationSide(entitySets.get(2),Enum.CARDINALITY_ONE),new AssociationSide(entitySets.get(3),Enum.CARDINALITY_MANY)}, null));
		relationships.add(new Association(new AssociationSide[] {new AssociationSide(entitySets.get(2),Enum.CARDINALITY_MANY),new AssociationSide(entitySets.get(4),Enum.CARDINALITY_ONE)}, null));
		relationships.add(new Association(new AssociationSide[] {new AssociationSide(entitySets.get(3),Enum.CARDINALITY_MANY),new AssociationSide(entitySets.get(5),Enum.CARDINALITY_ONE)}, null));
		relationships.add(new Association(new AssociationSide[] {new AssociationSide(entitySets.get(4),Enum.CARDINALITY_ONE),new AssociationSide(entitySets.get(5),Enum.CARDINALITY_ONE)}, null));
		
		model.addAllRelationships(relationships);
		model.addAllEntitySets(entitySets);
		return model;
	}
	private static ERModel makeERModel2(){
		ERModel model = new ERModel();
		
		List<EntitySet> entitySets = new ArrayList<>();
		entitySets.add(new EntitySet("Osoby", new ArrayList<>(Arrays.asList("Meno","Priezvisko"))));		// 0
		entitySets.add(new EntitySet("Schodze", new ArrayList<>(Arrays.asList("Od", "Do"))));		// 1
		entitySets.add(new EntitySet("Body", new ArrayList<>(Arrays.asList("Kedy", "Poradove cislo"))));		// 2
		entitySets.add(new EntitySet("Dokumenty", new ArrayList<>(Arrays.asList("Nazov","Text"))));		// 3
		entitySets.add(new EntitySet("Typy bodov", new ArrayList<>(Arrays.asList("Nazov"))));	// 4
		entitySets.add(new EntitySet("Moznosti hlasovania", new ArrayList<>(Arrays.asList("Nazov"))));		// 5
		
		List<Relationship> relationships = new ArrayList<>();
		relationships.add(new Association(new AssociationSide[] {new AssociationSide(entitySets.get(0),Enum.CARDINALITY_MANY),new AssociationSide(entitySets.get(1),Enum.CARDINALITY_MANY)}, null));
		relationships.add(new Association(new AssociationSide[] {new AssociationSide(entitySets.get(1),Enum.CARDINALITY_ONE),new AssociationSide(entitySets.get(2),Enum.CARDINALITY_MANY)}, null));
		relationships.add(new Association(new AssociationSide[] {new AssociationSide(entitySets.get(2),Enum.CARDINALITY_MANY),new AssociationSide(entitySets.get(3),Enum.CARDINALITY_MANY)}, null));
		relationships.add(new Association(new AssociationSide[] {new AssociationSide(entitySets.get(2),Enum.CARDINALITY_MANY),new AssociationSide(entitySets.get(4),Enum.CARDINALITY_ONE)}, null));
		relationships.add(new Association(new AssociationSide[] {new AssociationSide(entitySets.get(0),Enum.CARDINALITY_MANY),new AssociationSide(entitySets.get(2),Enum.CARDINALITY_MANY),new AssociationSide(entitySets.get(5),Enum.CARDINALITY_ONE)}, null));
		
		model.addAllRelationships(relationships);
		model.addAllEntitySets(entitySets);
		return model;
	}
	private static ERModel makeERModel3(){
		ERModel model = new ERModel();
		
		List<EntitySet> entitySets = new ArrayList<>();
		entitySets.add(new EntitySet("Budovy", new ArrayList<>(Arrays.asList("Ulica","Cislo","Mesto","PSC"))));		// 0
		entitySets.add(new EntitySet("Izby", new ArrayList<>(Arrays.asList("Cislo","C.Poschodia","Kapacita","Poplatok"))));		// 1
		entitySets.add(new EntitySet("Zmluvy", new ArrayList<>(Arrays.asList("Od","Do"))));		// 2
		entitySets.add(new EntitySet("Platby", new ArrayList<>(Arrays.asList("Vyska","Datum"))));		// 3
		entitySets.add(new EntitySet("Studenti", new ArrayList<>(Arrays.asList())));	// 4
		entitySets.add(new EntitySet("Konta", new ArrayList<>(Arrays.asList("Kredit"))));		// 5
		entitySets.add(new EntitySet("Ludia", new ArrayList<>(Arrays.asList("Meno","Priezvisko","C.OP"))));	// 6
		
		List<Relationship> relationships = new ArrayList<>();
		relationships.add(new Association(new AssociationSide[] {new AssociationSide(entitySets.get(0),Enum.CARDINALITY_ONE),new AssociationSide(entitySets.get(1),Enum.CARDINALITY_MANY)}, null));
		relationships.add(new Association(new AssociationSide[] {new AssociationSide(entitySets.get(1),Enum.CARDINALITY_ONE),new AssociationSide(entitySets.get(2),Enum.CARDINALITY_MANY)}, null));
		relationships.add(new Association(new AssociationSide[] {new AssociationSide(entitySets.get(2),Enum.CARDINALITY_ONE),new AssociationSide(entitySets.get(3),Enum.CARDINALITY_MANY)}, null));
		relationships.add(new Association(new AssociationSide[] {new AssociationSide(entitySets.get(2),Enum.CARDINALITY_MANY),new AssociationSide(entitySets.get(4),Enum.CARDINALITY_ONE)}, null));
		relationships.add(new Association(new AssociationSide[] {new AssociationSide(entitySets.get(3),Enum.CARDINALITY_MANY),new AssociationSide(entitySets.get(5),Enum.CARDINALITY_ONE)}, null));
		relationships.add(new Association(new AssociationSide[] {new AssociationSide(entitySets.get(4),Enum.CARDINALITY_ONE),new AssociationSide(entitySets.get(5),Enum.CARDINALITY_ONE)}, null));
		relationships.add(new Generalization(entitySets.get(6), entitySets.get(4)));
		
		model.addAllRelationships(relationships);
		model.addAllEntitySets(entitySets);
		return model;
	}
	private static ERModel makeERModel4(){
		return null;
	}
}
