package entityRelationshipModel;

import java.util.List;

public class Generalization extends Relationship {

    private List<GeneralizationSide> sides;

    public List<GeneralizationSide> getSides() {
        return sides;
    }
}
