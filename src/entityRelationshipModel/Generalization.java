package entityRelationshipModel;

import common.CollectionUtils;

import java.util.ArrayList;
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

    public Generalization(List<GeneralizationSide> sides) {
        this.sides = sides;
    }

    public Generalization(EntitySet superEntitySet, EntitySet subEntitySet) {
        sides = new ArrayList<>();
        sides.add(new GeneralizationSide(superEntitySet,GeneralizationRole.SUPER));
        sides.add(new GeneralizationSide(subEntitySet,GeneralizationRole.SUB));
    }

    public List<GeneralizationSide> getSides() {
        return sides;
    }

    public EntitySet getSuperEntitySet() {
        return CollectionUtils.getFirst(sides, GeneralizationSide.class, generalizationSide -> GeneralizationRole.SUPER.equals(generalizationSide.getRole())).getEntitySet();
    }

    public EntitySet getSubEntitySet() {
        return CollectionUtils.getFirst(sides, GeneralizationSide.class, generalizationSide -> GeneralizationRole.SUB.equals(generalizationSide.getRole())).getEntitySet();
    }
}
