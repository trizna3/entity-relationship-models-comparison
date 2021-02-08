package sk.trizna.erm_comparison.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import sk.trizna.erm_comparison.comparing.mapping.Mapping;
import sk.trizna.erm_comparison.entity_relationship_model.Association;
import sk.trizna.erm_comparison.entity_relationship_model.Attribute;
import sk.trizna.erm_comparison.entity_relationship_model.ERModel;
import sk.trizna.erm_comparison.entity_relationship_model.ERText;
import sk.trizna.erm_comparison.entity_relationship_model.EntitySet;
import sk.trizna.erm_comparison.entity_relationship_model.Relationship;

public class MappingUtils extends Utils {

	private final static String EMPTY_CODE = "EMPTY";
	/**
	 * Empty entity set. Used in mapping search.
	 */
	public final static EntitySet EMPTY_ENTITY_SET;
	static {
		EMPTY_ENTITY_SET = new EntitySet(EMPTY_CODE);
		EMPTY_ENTITY_SET.setEmpty(true);
	}

	/**
	 * Returns a list of all model's entitySets with no mapping image set.
	 * 
	 * @param model
	 * @return
	 */
	public static List<EntitySet> getNotMappedEntitySets(ERModel model) {
		validateNotNull(model);

		List<EntitySet> result = new ArrayList<>();

		for (EntitySet entitySet : model.getEntitySets()) {
			if (entitySet.getMappedTo() == null) {
				result.add(entitySet);
			}
		}

		return result;
	}
	
	/**
	 * Returns attributes of all (not mapped) entitySets - for moveAttributeToIncidentEntitySet transformation analysis
	 * @param model
	 * @return
	 */
	public static Set<Attribute> getNotMappedEntitySetAttributes(ERModel model) {
		validateNotNull(model);
		Set<Attribute> result = new HashSet<>();
		for (EntitySet entitySet : model.getEntitySets()) {
			if (entitySet.getMappedTo() == null) {
				result.addAll(entitySet.getAttributes());
			}
		}
		return result;		
	}
	
	/**
	 * Returns attributes of all (not mapped) associations - for moveAttributeToIncidentAssociation transformation analysis
	 * @param model
	 * @return
	 */
	public static Set<Attribute> getNotMappedAssociationAttributes(ERModel model) {
		validateNotNull(model);
		Set<Attribute> result = new HashSet<>();
		for (Relationship relationship : model.getRelationships()) {
			if (relationship instanceof Association && !RelationshipUtils.isMapped(relationship)) {
				result.addAll( ((Association)relationship).getAttributes());				
			}
		}		
		return result;		
	}
	
	/**
	 * Returns union of name and attributes
	 * @param model
	 * @return
	 */
	public static Set<ERText> getNotMappedEntitySetNames(ERModel model) {
		validateNotNull(model);
		Set<ERText> result = new HashSet<>();
		
		for (EntitySet entitySet : model.getEntitySets()) {
			if (entitySet.getMappedTo() == null && entitySet.getName() != null && entitySet.getName().getName() != null) {
				result.add(entitySet.getName());
			}
		}

		return result;		
	}

	/**
	 * Creates map of entitySets <EntitySet, it's image>. Resulting entitySets are
	 * copies of the input entitySets.
	 * 
	 * @param mapping
	 * @return
	 */
	public static Map<EntitySet, EntitySet> createEntitySetMap(Mapping mapping) {
		validateNotNull(mapping);

		Map<EntitySet, EntitySet> map = new HashMap<EntitySet, EntitySet>();
		for (EntitySet entitySet : mapping.getExemplarModel().getEntitySets()) {
			if (entitySet.getMappedTo() == null) {
				map.put(new EntitySet(entitySet), new EntitySet("---"));
			} else {
				map.put(new EntitySet(entitySet), new EntitySet(entitySet.getMappedTo()));
			}
		}
		return map;
	}
}
