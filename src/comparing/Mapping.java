package comparing;

import entityRelationshipModel.EntitySet;

import java.util.HashMap;
import java.util.Map;

/**
 * Object representing mapping of entity sets between two entity relationship model.
 */
public class Mapping {

    /**
     * Mapping.
     * If es1 and es2 are paired, both (es1,es2) and (es2,es1) will be in the data
     */
    private Map<EntitySet,EntitySet> data;

    private Map<EntitySet, EntitySet> getData() {
        if (data == null) {
            data = new HashMap<>();
        }
        return data;
    }

    /**
     * Add mapping pair. (both entries in the data map)
     * @param entitySet1
     * @param entitySet2
     */
    public void map(EntitySet entitySet1, EntitySet entitySet2) {
        if (entitySet1 == null || entitySet2 == null) {
            throw new IllegalArgumentException("mapped entity set cannot be null");
        }
        getData().put(entitySet1,entitySet2);
        getData().put(entitySet2,entitySet1);
    }

    /**
     * Remove mapping pair. (both key and value)
     * @param entitySet
     */
    public void unmap(EntitySet entitySet) {
        if (entitySet == null) {
            throw new IllegalArgumentException("illegal unmapping request - entity set is null");
        }
        if (getData().containsKey(entitySet)) {
            for (EntitySet key : getData().keySet()) {
                if (entitySet.equals(key) || entitySet.equals(getData().get(key))) {
                    getData().remove(key);
                }
            }
        }
    }

    /**
     * @param entitySet
     * @return paired entity set of given entity set
     */
    public EntitySet getImage(EntitySet entitySet) {
        if (entitySet == null) {
            throw new IllegalArgumentException("illegal mapping image request - entity set is null");
        }
        return getData().get(entitySet);
    }
}
