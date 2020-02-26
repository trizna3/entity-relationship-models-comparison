package transformations;

import entityRelationshipModel.EntityRelationshipModel;

/**
 * @author - Adam Trizna
 */

/**
 * An entity relationship model transformation. Describes any kind of such transformation, which modifies the model's structure while keeping it's amount of ability to store data.
 */
public abstract class Transformation {

    public static final String CODE_NN_TO_1NN1 = "NN_TO_1NN1";
    public static final String CODE_1NN1_TO_NN  = "1NN1_TO_NN";

    /**
     * Executes it's purpose.
     * @param model
     */
    public abstract void execute(EntityRelationshipModel model);

    /**
     * Undoes it's effect.
     * @param model
     */
    public abstract void undo(EntityRelationshipModel model);
}