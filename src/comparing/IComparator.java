package comparing;

import entityRelationshipModel.ERModel;

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
    public void compare(ERModel exemplarModel, ERModel studentsModel);
}
