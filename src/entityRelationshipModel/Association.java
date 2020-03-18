package entityRelationshipModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author - Adam Trizna
 */

/**
 * Type of relationship: Association.
 * Joins 2 or more entity sets, each with specific cardinality.
 * @see AssociationSide
 */
public class Association extends Relationship{

    /**
     * List of association sides.
     */
    private List<AssociationSide> sides;
    /**
     * Attributes list.
     */
    private List<String> attributes;

    public Association() {
        this.sides = new ArrayList<>();
        this.attributes = new ArrayList<>();
    }

    public Association(List<AssociationSide> sides, List<String> attributes) {
        this.sides = sides;
        this.attributes = attributes;
    }

    public List<AssociationSide> getSides() {
        return sides;
    }

    public List<String> getAttributes() {
        return attributes;
    }

    public boolean isBinary() {
        return sides.size() == 2;
    }

    public AssociationSide getFirstSide(){
        if (!isBinary()) {
            throw new IllegalStateException("Association is not binary!");
        }
        return getSides().get(0);
    }

    public AssociationSide getSecondSide(){
        if (!isBinary()) {
            throw new IllegalStateException("Association is not binary!");
        }
        return getSides().get(1);
    }

    public Cardinality getCardinality(EntitySet entitySet) {
        Optional<Cardinality> cardinality = sides.stream().filter(side -> entitySet.equals(side.getEntitySet())).map(AssociationSide::getCardinality).findFirst();

        if (!cardinality.isPresent()) {
            throw new IllegalArgumentException("This Association doesn't contain given entity set!");
        }
        return cardinality.get();
    }
}
