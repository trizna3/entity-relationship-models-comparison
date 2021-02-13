package sk.trizna.erm_comparison.common.utils;

import sk.trizna.erm_comparison.common.enums.EnumRelationshipSideRole;

public class ParseUtils extends Utils {

	public static EnumRelationshipSideRole parseCardinality(String rawCardinality) {
		if (rawCardinality == null) {
			return null;
		}
		
		switch(rawCardinality) {
			case "ONE":
				return EnumRelationshipSideRole.ONE;
			case "MANY":
				return EnumRelationshipSideRole.MANY;
			default:
				throw new IllegalArgumentException("Unknown cardinality type");
		} 
	}
}
