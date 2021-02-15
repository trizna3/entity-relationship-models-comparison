package sk.trizna.erm_comparison.comparing;

import java.util.Comparator;

import sk.trizna.erm_comparison.common.utils.StringUtils;
import sk.trizna.erm_comparison.entity_relationship_model.ERText;

public class ERTextComparator implements Comparator<Object> {

	private static final ERTextComparator INSTANCE = new ERTextComparator();

	public static ERTextComparator getInstance() {
		return INSTANCE;
	} 
	
	@Override
	public int compare(Object o1, Object o2) {
		if (!(o1 instanceof ERText)) {
			return 1;
		}
		if (!(o2 instanceof ERText)) {
			return 1;
		}
		return StringUtils.areEqual(((ERText)o1).getText(), ((ERText)o2).getText()) ? 0 : 1;
	}	
}
