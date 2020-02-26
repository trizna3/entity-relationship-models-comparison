package comparing;

/**
 * @author - Adam Trizna
 */

import transformations.Transformation;

import java.util.Stack;

/**
 * An object wrapping multiple transformations in a sequence.
 * Is a stack extension for easy transformation rollbacking.
 */
public class TransformationPack extends Stack<Transformation> {
}
