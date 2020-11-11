package comparing;

import entityRelationshipModel.ERModel;

/**
 * @author - Adam Trizna
 */

/**
 * Entity relationship models comparator.
 */
public class ERComparator implements IComparator {

	/**
	 * weight of name similarity in Named ER elements comparison
	 */
	public static final int NAME_WEIGHT = 2;
	/**
	 * weight of attributes similarity in Attributed ER elements comparison
	 */
	public static final int ATTRIBUTE_WEIGHT = 2;
	/**
	 * weight of incident entity sets similarity in relationships comparison
	 */
	public static final int ENTITY_SET_WEIGHT = 1;
	
    /**
     * {@inheritDoc}
     */
    @Override
    public void compare(ERModel exemplarModel, ERModel studentsModel) {
        throw new UnsupportedOperationException("todo");
    }
}
