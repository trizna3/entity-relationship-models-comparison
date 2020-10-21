package mappingSearch.mappingFinder;

import java.util.Comparator;

import entityRelationshipModel.EntitySet;

/**
 * Compares entitySet double, based on it's priority attribute. 
 * 
 * @author Adam Trizna
 *
 */
public class EntitySetComparator implements Comparator<EntitySet> {
	
	private static final EntitySetComparator INSTANCE = new EntitySetComparator();  

	@Override
	public int compare(EntitySet o1, EntitySet o2) {
		if (o1.getPriority() == null || o2.getPriority() == null) {
			return 1;
		}
		return (int) (o1.getPriority().doubleValue() - o2.getPriority().doubleValue());
	}

	public static EntitySetComparator getInstance() {
		return INSTANCE;
	}

}
