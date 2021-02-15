
package sk.trizna.erm_comparison.common.utils;

import java.util.ArrayList;
import java.util.List;

import sk.trizna.erm_comparison.common.enums.EnumRelationshipSideRole;
import sk.trizna.erm_comparison.common.enums.EnumTransformation;
import sk.trizna.erm_comparison.common.enums.EnumTransformationRole;
import sk.trizna.erm_comparison.entity_relationship_model.Association;
import sk.trizna.erm_comparison.entity_relationship_model.AssociationSide;
import sk.trizna.erm_comparison.entity_relationship_model.Attribute;
import sk.trizna.erm_comparison.entity_relationship_model.ERText;
import sk.trizna.erm_comparison.entity_relationship_model.EntitySet;
import sk.trizna.erm_comparison.entity_relationship_model.Relationship;
import sk.trizna.erm_comparison.entity_relationship_model.RelationshipSide;
import sk.trizna.erm_comparison.entity_relationship_model.TransformableFlag;
import sk.trizna.erm_comparison.entity_relationship_model.TransformableList;
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
		
		for (Transformable arg1 : t1.getArgumentMap().keySet()) {
			for (Transformable arg2 : t2.getArgumentMap().keySet()) {
				if (areEqual(arg1, arg2)) {
					return false;
				}
			} 
		}
		return true;
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
	
	public static boolean areEqual(Transformation t1, Transformation t2) {
		Utils.validateNotNull(t1);
		Utils.validateNotNull(t2);
		
		if (t1.getCode() != t2.getCode()) {
			return false;
		}
		if (t1.getArgumentMap().size() != t2.getArgumentMap().size()) {
			return false;
		}
		for (EnumTransformationRole role : t1.getArgumentMap().values()) {
			if (!areEqual(getTransformableByRole(t1, role),getTransformableByRole(t2, role))) {
				return false;
			}
			
		}
		return true;
	}
	
	private static boolean areEqual(Transformable t1, Transformable t2) {
		if (t1 == null && t2 == null) {
			return true;
		}
		if (t1 == null || t2 == null) {
			return false;
		}
		if (!t1.getClass().equals(t2.getClass())) {
			return false;
		}
		if (t1 instanceof Attribute) {
			return ERModelUtils.areEqual((ERText)t1, (ERText)t2);
		}
		if (t1 instanceof EntitySet) {
			return ERModelUtils.areEqual((EntitySet)t1, (EntitySet)t2);
		}
		if (t1 instanceof Relationship) {
			return ERModelUtils.areEqual((Relationship)t1, (Relationship)t2);
		}
		if (t1 instanceof RelationshipSide) {
			return ERModelUtils.areEqual((RelationshipSide)t1, (RelationshipSide)t2);
		}
		if (t1 instanceof TransformableFlag) {
			return true;
		}
		if (t1 instanceof TransformableList) {
			return areEqual((TransformableList)t1, (TransformableList)t2);
		}
		return false;
	}
	
	private static boolean areEqual(TransformableList list1, TransformableList list2) {
		if (list1.size() != list2.size()) {
			return false;
		}
		
		transformable : for (Transformable t1 : list1) {
			for (Transformable t2 : list2) {
				if (areEqual(t1, t2)) {
					continue transformable;
				}
			}
			return false;
		}
		
		return true;
	}
}
