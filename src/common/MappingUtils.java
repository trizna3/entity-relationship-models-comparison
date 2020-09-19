package common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import comparing.Mapping;
import entityRelationshipModel.ERModel;
import entityRelationshipModel.EntitySet;

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
			map.put(new EntitySet(entitySet), new EntitySet(entitySet.getMappedTo()));
		}
		return map;
	}
}
