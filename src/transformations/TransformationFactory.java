package transformations;

/**
 * @author - Adam Trizna
 */

import transformations.types.Transformation;
import transformations.types.Transformation_AddAssociation;
import transformations.types.Transformation_AddEntitySet;
import transformations.types.Transformation_AddGeneralization;

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