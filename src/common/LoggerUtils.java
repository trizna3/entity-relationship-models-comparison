package common;

import entityRelationshipModel.EntitySet;
import transformations.Transformable;
import transformations.Transformation;

public class LoggerUtils {

	public final static String DIRECTION_DOWN = "->";
	public final static String DIRECTION_UP = "<-";
	
	public static void log(String text) {
		System.out.println(text);
	}
	
	public static void logTransformation(Transformation transformation, String direction) {
		Utils.validateNotNull(transformation);
		Utils.validateNotNull(direction);
		
		StringBuffer result = new StringBuffer();
		
		result.append(direction + " " + transformation.getCode() + ": (");
		boolean first = true;
		for (Transformable argument : transformation.getArguments()) {
			if (first) {
				first = false;
			} else {
				result.append(", ");
			}
			result.append(transformation.getArgumentMap().get(argument));
			result.append(":");
			result.append(PrintUtils.getReprName(argument));
		}
		result.append(")");
		
		log(result.toString());
	}
}
