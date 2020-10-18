package common;

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

		StringBuilder result = new StringBuilder();

		result.append(direction + PrintUtils.print(transformation));

		log(result.toString());
	}
}
