package sk.trizna.erm_comparison.common;

import sk.trizna.erm_comparison.common.enums.EnumRelationshipSideRole;

public class ParseUtils extends Utils {

	public static EnumRelationshipSideRole parseCardinality(String rawCardinality) {
		if (rawCardinality == null) {
			return null;
		}
		
		switch(rawCardinality) {
			case "ONE":
				return EnumRelationshipSideRole.CARDINALITY_ONE;
			case "MANY":
				return EnumRelationshipSideRole.CARDINALITY_MANY;
			default:
				throw new IllegalArgumentException("Unknown cardinality type");
		} 
	}
}
