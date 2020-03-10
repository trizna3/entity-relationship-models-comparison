package transformations;

/**
 * @author - Adam Trizna
 */

import transformations.types.*;

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
            case Transformation.CODE_ADD_ATTRIBUTE:
                return new Transformation_AddAttribute();
            case Transformation.CODE_REMOVE_ASSOCIATION:
                return new Transformation_RemoveAssociation();
            case Transformation.CODE_REMOVE_GENERALIZATION:
                return new Transformation_RemoveGeneralization();
            case Transformation.CODE_REMOVE_ENTITY_SET:
                return new Transformation_RemoveEntitySet();
            case Transformation.CODE_REMOVE_ATTRIBUTE:
                return new Transformation_RemoveAttribute();
            default:
                throw new IllegalArgumentException("Unknown transformation code");
        }
    }
}