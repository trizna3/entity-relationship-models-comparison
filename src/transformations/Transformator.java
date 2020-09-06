package transformations;

import java.util.List;

import comparing.Mapping;

public class Transformator {

	/**
	 * Executes transformation by given transformation code and arguments. Returns
	 * the arguments in transformed form.
	 */
	public static List<Transformable> execute(Mapping mapping, String transformationCode, List<Transformable> arguments) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Reverts transformation given by transformation code and arguments. Returns
	 * the arguments in transformation-reverted form.
	 */
	public static List<Transformable> revert(Mapping mapping, String transformationCode, List<Transformable> arguments) {
		throw new UnsupportedOperationException();
	}
}
