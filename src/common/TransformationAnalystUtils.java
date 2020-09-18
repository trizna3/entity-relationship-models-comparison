package common;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import common.enums.EnumTransformation;
import common.enums.EnumTransformationRole;
import common.enums.Enums;
import entityRelationshipModel.Association;
import entityRelationshipModel.ERModel;
import entityRelationshipModel.Relationship;
import entityRelationshipModel.TransformableFlag;
import transformations.Transformation;

public class TransformationAnalystUtils {

	public static void getPossibleContract11AssociationTransformations(List<Transformation> target, ERModel model, boolean isExemplar) {
		for (Relationship relationship : model.getRelationships()) {
			if (relationship instanceof Association == false) {
				continue;
			}
			Association association = (Association) relationship;
			if (!association.isBinary()) {
				continue;
			}
			if (!Enums.CARDINALITY_ONE.equals(association.getFirstSide().getRole()) || association.getFirstSide().getEntitySet().getMappedTo() != null) {
				continue;
			}
			if (!Enums.CARDINALITY_ONE.equals(association.getSecondSide().getRole()) || association.getSecondSide().getEntitySet().getMappedTo() != null) {
				continue;
			}

			association.setTransformationRole(EnumTransformationRole.ASSOCIATION);
			if (isExemplar) {
				TransformableFlag flag = new TransformableFlag(EnumTransformationRole.EXEMPLAR_MODEL_FLAG);
				target.add(new Transformation(EnumTransformation.CONTRACT_11_ASSOCIATION, new HashSet<>(Arrays.asList(association, flag))));
			} else {
				target.add(new Transformation(EnumTransformation.CONTRACT_11_ASSOCIATION, new HashSet<>(Arrays.asList(association))));
			}
		}
	}
}
