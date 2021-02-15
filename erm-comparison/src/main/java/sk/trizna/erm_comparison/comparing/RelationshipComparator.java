package sk.trizna.erm_comparison.comparing;

import java.util.Comparator;

import sk.trizna.erm_comparison.common.utils.ERModelUtils;
import sk.trizna.erm_comparison.entity_relationship_model.Relationship;

public class RelationshipComparator implements Comparator<Object> {

	private static final RelationshipComparator INSTANCE = new RelationshipComparator();

	public static RelationshipComparator getInstance() {
		return INSTANCE;
	} 
	
	@Override
	public int compare(Object o1, Object o2) {
		if (!(o1 instanceof Relationship)) {
			return 1;
		}
		if (!(o2 instanceof Relationship)) {
			return 1;
		}
		return ERModelUtils.areEqual((Relationship) o1, (Relationship) o2) ? 0 : 1;
	}	

	

}
