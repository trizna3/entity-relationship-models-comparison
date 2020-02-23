package transformations;

/**
 * @author - Adam Trizna
 */

/**
 * Object factory, creating instances of transformations by given transformation code.
 */
public class TransformationFactory {

    public static Transformation getTransformation(String code) {
        switch (code) {
            case Transformation.CODE_1NN1_TO_NN:
                return new Transformation_1NN1_TO_NN();
            case Transformation.CODE_NN_TO_1NN1:
                return new Transformation_NN_TO_1NN1();
            default:
                throw new IllegalArgumentException("Unknown transformation code");
        }
    }
}