package entityRelationshipModel;

/**
 * @author - Adam Trizna
 */

public interface RelationshipSide {

    EntitySet getEntitySet();

    RelationshipSideRole getRole();
}
