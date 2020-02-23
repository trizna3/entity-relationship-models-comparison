package comparing;

import entityRelationshipModel.IEntityRelationshipModel;

/**
 * @author - Adam Trizna
 */

/**
 * Interface of entity relationship comparators.
 */
public interface IComparator {

    /**
     * Gets two ER models. Returns models difference in a sense of their capability of holding data.
     * @param exemplarModel
     * @param studentsModel
     * TODO: adjust output type
     */
    public void compare(IEntityRelationshipModel exemplarModel, IEntityRelationshipModel studentsModel);
}
