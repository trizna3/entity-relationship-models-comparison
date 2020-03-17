package transformations.types;

import entityRelationshipModel.EntityRelationshipModel;
import transformations.ParametersNotSetException;

import java.util.HashMap;
import java.util.Map;

/**
 * @author - Adam Trizna
 */

/**
 * An entity relationship model transformation. Describes any kind of such transformation, which modifies the model's structure while keeping it's amount of ability to store data.
 */
public abstract class Transformation {

    public static final String CODE_ADD_ASSOCIATION = "CODE_ADD_ASSOCIATION";
    public static final String CODE_ADD_GENERALIZATION = "CODE_ADD_GENERALIZATION";
    public static final String CODE_ADD_ENTITY_SET = "CODE_ADD_ENTITY_SET";
    public static final String CODE_ADD_ATTRIBUTE = "CODE_ADD_ATTRIBUTE";
    public static final String CODE_REMOVE_ASSOCIATION = "CODE_REMOVE_ASSOCIATION";
    public static final String CODE_REMOVE_GENERALIZATION = "CODE_REMOVE_GENERALIZATION";
    public static final String CODE_REMOVE_ENTITY_SET = "CODE_REMOVE_ENTITY_SET";
    public static final String CODE_REMOVE_ATTRIBUTE = "CODE_REMOVE_ATTRIBUTE";


    protected Map<String,Object> parameterMap = new HashMap<>();
    protected String[] parameterNames;

    /**
     * Executes it's purpose.
     * @param model
     */
    public void doTransformation(EntityRelationshipModel model) {
        if (model == null) {
            throw new IllegalArgumentException("Model cannot be null");
        }
        checkParameters();
        execute(model);
    }

    /**
     * Undoes it's effect.
     * @param model
     */
    public void undoTransformation(EntityRelationshipModel model) {
        if (model == null) {
            throw new IllegalArgumentException("Model cannot be null");
        }
        checkParameters();
        setToOriginalState(model);
    }

    /**
     * @see Transformation#doTransformation(EntityRelationshipModel)
     * @param model
     */
    protected abstract void execute(EntityRelationshipModel model);

    /**
     * @see Transformation#undoTransformation(EntityRelationshipModel)
     * @param model
     */
    protected abstract void setToOriginalState(EntityRelationshipModel model);

    /**
     * Sets parameter value to parameter map.
     * Checks if parameter already has any value. Parameter reassignment is not allowed.
     * @param parameterName
     * @param parameterValue
     */
    public void setParameter(String parameterName,Object parameterValue) {
        if (parameterMap.get(parameterName) != null) {
            throw new IllegalStateException("Cannot reassign " + parameterName + "!" );
        }
        if (parameterValue == null) {
            throw new IllegalArgumentException("Parameter value is null!");
        }
        parameterMap.put(parameterName,parameterValue);
    }

    /**
     * Checks if all parameters are set. Parameters list is specified for each specific transformation.
     */
    protected void checkParameters() {
        for (int i = 0; i < parameterNames.length; i++) {
            if (parameterMap.get(parameterNames[i]) == null) {
                throw new ParametersNotSetException();
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append(getClass().toString());
        result.append("-> (");
        for (String param : parameterNames) {
            result.append(param+": "+parameterMap.get(param)+", ");
        }
        result.append(")");
        return result.toString();
    }
}