import common.CollectionUtils;
import common.Logger;
import entityRelationshipModel.EntitySet;

import java.util.*;

public class Main {

    public static void main(String[] args) {
        EntitySet es1 = new EntitySet("es1",null);
        EntitySet es2 = new EntitySet("es2",null);

        List<EntitySet> entitySets = new ArrayList<>(Arrays.asList(es1,es2));

        EntitySet es = CollectionUtils.getFirst(entitySets, EntitySet.class, entitySet -> "es1".equals(entitySet.getName()));

        System.out.println(es.getName());
    }
}
