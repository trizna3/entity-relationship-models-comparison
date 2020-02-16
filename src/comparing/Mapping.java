package comparing;

import entityRelationshipModel.EntitySet;

import java.util.HashMap;
import java.util.Map;

public class Mapping {

    private Map<EntitySet,EntitySet> data;

    private Map<EntitySet, EntitySet> getData() {
        if (data == null) {
            data = new HashMap<>();
        }
        return data;
    }

    public void map(EntitySet entitySet1, EntitySet entitySet2) {
        if (entitySet1 == null || entitySet2 == null) {
            throw new IllegalArgumentException("mapped entity set cannot be null");
        }
        getData().put(entitySet1,entitySet2);
        getData().put(entitySet2,entitySet1);
    }

    public void unmap(EntitySet entitySet) {
        // unmapni jeho ako kluc aj ako value
    }

    public EntitySet getImage(EntitySet entitySet) {
        if (entitySet == null) {
            throw new IllegalArgumentException("illegal mapping image request - entity set is null");
        }
        return getData().get(entitySet);
    }
}
