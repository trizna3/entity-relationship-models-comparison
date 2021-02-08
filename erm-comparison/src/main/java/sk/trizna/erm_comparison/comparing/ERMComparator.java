package sk.trizna.erm_comparison.comparing;

import sk.trizna.erm_comparison.entity_relationship_model.ERModel;

/**
 * @author - Adam Trizna
 */

/**
 * Interface of entity relationship comparators.
 */
public interface ERMComparator {

    /**
     * Gets two ER models. Returns models difference in a sense of their capability of holding data.

     * @param exemplarModel
     * @param studentsModel
     */
    public abstract ERModelDiff getModelsDiff(ERModel exemplarModel, ERModel studentModel);
    
    
    public abstract ERModelDiffReport getModelsDiffReport(ERModel exemplarModel, ERModel studentModel);
}
