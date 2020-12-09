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
     */
    public ERModelComparisonReport getCompareReport(ERModel exemplarModel, ERModel studentModel);
    
    /**
     * Recomputes models comparison report.
     * 
     * @param exemplarModel
     * @param studentModel
     */
    public void computeCompareReport(ERModel exemplarModel, ERModel studentModel);
}
