package transformations;

/**
 * @author - Adam Trizna
 */
public class ParametersNotSetException extends IllegalStateException{

    public ParametersNotSetException() {
        super("All parameters are not set. Cannot execute transformation.");
    }
}
