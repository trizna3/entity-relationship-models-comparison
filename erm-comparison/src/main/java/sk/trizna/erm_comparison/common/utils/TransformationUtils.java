
package sk.trizna.erm_comparison.common.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import sk.trizna.erm_comparison.common.enums.EnumRelationshipSideRole;
import sk.trizna.erm_comparison.common.enums.EnumTransformation;
import sk.trizna.erm_comparison.common.enums.EnumTransformationRole;
import sk.trizna.erm_comparison.entity_relationship_model.Association;
import sk.trizna.erm_comparison.entity_relationship_model.AssociationSide;
import sk.trizna.erm_comparison.entity_relationship_model.RelationshipSide;
import sk.trizna.erm_comparison.entity_relationship_model.TransformableFlag;
import sk.trizna.erm_comparison.transformations.Transformable;
import sk.trizna.erm_comparison.transformations.Transformation;
import sk.trizna.erm_comparison.transformations.Transformator;

public class TransformationUtils extends Utils {

	public static Transformable getTransformableByRole(Transformation transformation, EnumTransformationRole role) {
		validateNotNull(transformation);
		validateNotNull(role);
		
		for (Transformable transformable : transformation.getArguments()) {
			if (role.equals(transformation.getArgumentMap().get(transformable))) {
				return transformable;
			}
		}
		return null;
	}
	
	public static boolean containsRole(Transformation transformation, EnumTransformationRole role) {
		validateNotNull(transformation);
		validateNotNull(role);
		
		return transformation.getArgumentMap().containsValue(role);
	}

	/**
	 * Flips the cardinality on given associationSide (between ONE - MANY).
	 * 
	 * @param associationSide
	 */
	public static void flipCardinality(AssociationSide associationSide) {
		validateNotNull(associationSide);

		if (EnumRelationshipSideRole.ONE.equals(associationSide.getRole())) {
			associationSide.setRole(EnumRelationshipSideRole.MANY);
		}
		if (EnumRelationshipSideRole.MANY.equals(associationSide.getRole())) {
			associationSide.setRole(EnumRelationshipSideRole.ONE);
		}
	}

	/**
	 * To avoid cycling, a transformation made will leave a flag on the transformed
	 * object. It's reverting transformation will either drop the flag or write it's
	 * own. Flag will serve as indicator in the transformation analysis.
	 * 
	 * @param code = code of currently performed transformation
	 */
	public static void overwriteTransformationFlag(EnumTransformation code, Transformable transformable) {
		EnumTransformation revertingCode = Transformator.getRevertingTransformation(code);

		if (transformable.containsTransformationFlag(revertingCode)) {
			// it's the transformation reverting scenario - drop the flag
			transformable.removeTransformationFlag(revertingCode);
		} else {
			// it's the new transformation execution scenario - write own flag
			transformable.addTransformationFlag(code);
		}

	}
	
	public static List<Transformation> copyTransformationList(List<Transformation> transformationList) {
		validateNotNull(transformationList);
		
		List<Transformation> copy = new ArrayList<>();
		transformationList.forEach(tr -> copy.add(new Transformation(tr)));
		
		return copy;
	}
	
	public static boolean areIndependent(Transformation t1, Transformation t2) {
		validateNotNull(t1);
		validateNotNull(t2);
		
		return !intersect(t1.getArgumentMap().keySet(),t2.getArgumentMap().keySet());
	}
	
	/**
	 * Given N-ary (N>2) association, returns a suitable name for it's substituing entity set.
	 * @param association
	 * @return
	 */
	public static String getNameForNaryRebindEntitySet(Association association) {
		validateNotNull(association);
		
		if (!StringUtils.isBlank(association.getNameText())) {
			// use association's name
			return association.getNameText();
		} else {
			// compose name
			StringBuilder compositeName = new StringBuilder();
			
			for (RelationshipSide side : association.getSides()) {
				if (compositeName.length() > 0) {
					compositeName.append(PrintUtils.DELIMITER_SEMICOLON);
				}
				compositeName.append(side.getEntitySet().getNameText());
			}
			
			return compositeName.toString();
		}
	}
	
	private static boolean intersect(Collection<Transformable> set1, Collection<Transformable> set2) {
		Iterator<Transformable> it1 = set1.iterator();
		
		while (it1.hasNext()) {
			Transformable t = it1.next();
			if (t instanceof TransformableFlag) {
				continue;
			}
			if (set2.contains(t)) {
				return true;
			}
		}
		return false;
	}
}
