package entityRelationshipModel;

import java.util.List;

public class Association extends Relationship{

    private List<AssociationSide> sides;
    private List<String> attributes;

    public List<AssociationSide> getSides() {
        return sides;
    }

    public List<String> getAttributes() {
        return attributes;
    }
}
