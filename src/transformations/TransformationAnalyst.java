package transformations;

import java.util.List;
import java.util.Map;

import comparing.Mapping;

/**
 * @author - Adam Trizna
 */

/**
 * Object analyzing entity sets mapping between two entity relationship models.
 * Checks what transformations could be made in order to unify exemplar and
 * student's models structure.
 */
public class TransformationAnalyst {

	/**
	 * Returns list of possible transformations.
	 * 
	 * @param mapping
	 * @return
	 */
	public static Map<String, List<Transformable>> getPossibleTransformations(Mapping mapping) {
		throw new UnsupportedOperationException();
	}
}
