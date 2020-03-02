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
            case Transformation.CODE_ADD_ASSOCIATION:
                return new Transformation_AddAssociation();
            case Transformation.CODE_ADD_GENERALIZATION:
                return new Transformation_AddGeneralization();
            case Transformation.CODE_ADD_ENTITY_SET:
                return new Transformation_AddEntitySet();
            default:
                throw new IllegalArgumentException("Unknown transformation code");
        }
    }
}