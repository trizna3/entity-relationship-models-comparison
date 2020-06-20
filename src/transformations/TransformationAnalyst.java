package transformations;

import java.util.List;
import java.util.Map;

import comparing.Mapping;

/**
 * @author - Adam Trizna
 */

/**
 * Object analysing entity sets mapping between two entity relationship models.
 * Checks what transformations could be made in order to unify exemplar and
 * student's models structure.
 */
public class TransformationAnalyst {

	/**
	 * idea: ak by sa ukazalo, ze ti nestaci rozlisovat argumenty podla classy,
	 * namiesto List<Object> pouzi nejaky interface List<Transformable>, ktory budu
	 * implementovat vsetky zakladne objekty modelu a ktory bude definovat rolu toho
	 * objektu pre danu transformaciu.
	 */
	public static Map<String, List<Object>> getPossibleTransformations(Mapping mapping) {
		throw new UnsupportedOperationException();
	}
}
