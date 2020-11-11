package comparing;

import common.Utils;
import entityRelationshipModel.Attribute;
import entityRelationshipModel.Attributed;

public class AttributedComparator {
	
	private static AttributedComparator INSTANCE = new AttributedComparator();
	
	
	
	public static AttributedComparator getInstance() {
		return INSTANCE;
	}

	public double compareSymmetric(Attributed attributed1, Attributed attributed2) {
		Utils.validateNotNull(attributed1);
		Utils.validateNotNull(attributed2);
		
		double max = 0;
		double value = 0;
		
		for (Attribute attribute : attributed1.getAttributes()) {
			max += 1;
			// !! working version
			if (attributed2.getAttributes().contains(attribute)) {
				value += 1;
			}
		}
		for (Attribute attribute : attributed2.getAttributes()) {
			max += 1;
			// !! working version
			if (attributed1.getAttributes().contains(attribute)) {
				value += 1;
			}
		}
		
		return value == max ? 1 : value/max;
	}
	
	public double compareAsymmetric(Attributed subAttributed, Attributed superAttributed) {
		Utils.validateNotNull(subAttributed);
		Utils.validateNotNull(superAttributed);
		
		double max = 0;
		double value = 0;
		
		for (Attribute attribute : subAttributed.getAttributes()) {
			max += 1;
			// !! working version
			if (superAttributed.getAttributes().contains(attribute)) {
				value += 1;
			}
		}
		
		return value == max ? 1 : value/max;
	}	
}
