package entityRelationshipModel;

import java.util.List;

/**
 * Relationship type: Generalization.
 * Describes a binary relationship of "super" entity set and "sub" entity set. Denotes the "sub" ES being a specific kind of the "super" ES ("super" is a generalization of "sub"). Equivalent to an Association with cardinalites 1:1.
 * @see GeneralizationSide
 */
public class Generalization extends Relationship {

    /**
     * List of generalization sides.
     */
    private List<GeneralizationSide> sides;

    public List<GeneralizationSide> getSides() {
        return sides;
    }
}
