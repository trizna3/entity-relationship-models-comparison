package common;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import common.enums.EnumTransformation;
import common.enums.EnumTransformationRole;
import common.enums.Enums;
import entityRelationshipModel.Association;
import entityRelationshipModel.ERModel;
import entityRelationshipModel.EntitySet;
import entityRelationshipModel.Generalization;
import entityRelationshipModel.Relationship;
import entityRelationshipModel.TransformableFlag;
import transformations.Transformation;

public class TransformationAnalystUtils {

	public static void getPossibleContract11AssociationTransformations(List<Transformation> target, ERModel model) {
		Utils.validateNotNull(target);
		Utils.validateNotNull(model);

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
			if (model.isExemplar()) {
				TransformableFlag flag = new TransformableFlag(EnumTransformationRole.EXEMPLAR_MODEL_FLAG);
				target.add(new Transformation(EnumTransformation.CONTRACT_11_ASSOCIATION, new HashSet<>(Arrays.asList(association, flag))));
			} else {
				target.add(new Transformation(EnumTransformation.CONTRACT_11_ASSOCIATION, new HashSet<>(Arrays.asList(association))));
			}
		}
	}

	public static void getPossibleRebindMNTo1NN1Transformations(List<Transformation> target, ERModel model) {
		Utils.validateNotNull(target);
		Utils.validateNotNull(model);

		for (Relationship relationship : model.getRelationships()) {
			if (relationship instanceof Association == false) {
				continue;
			}
			Association association = (Association) relationship;
			if (!association.isBinary()) {
				continue;
			}
			if (!Enums.CARDINALITY_MANY.equals(association.getFirstSide().getRole()) || association.getFirstSide().getEntitySet().getMappedTo() != null) {
				continue;
			}
			if (!Enums.CARDINALITY_MANY.equals(association.getSecondSide().getRole()) || association.getSecondSide().getEntitySet().getMappedTo() != null) {
				continue;
			}

			association.setTransformationRole(EnumTransformationRole.ASSOCIATION);
			target.add(new Transformation(EnumTransformation.REBIND_MN_TO_1NN1, new HashSet<>(Arrays.asList(association))));
		}
	}

	public static void getPossibleRebind1NN1ToMNTransformations(List<Transformation> target, ERModel model) {
		Utils.validateNotNull(target);
		Utils.validateNotNull(model);

		nextES: for (EntitySet entitySet : model.getEntitySets()) {
			if (entitySet.getNeighbours().size() != 2) {
				continue;
			}
			List<Relationship> incidentRelationships = entitySet.getIncidentRelationships();
			if (incidentRelationships.size() != 2) {
				continue;
			}
			Association association1 = null;
			Association association2 = null;
			for (Relationship relationship : incidentRelationships) {
				if (relationship instanceof Association == false) {
					continue nextES;
				}
				if (!Enums.CARDINALITY_MANY.equals(RelationshipUtils.getRole(relationship, entitySet))) {
					continue nextES;
				}
				if (!Enums.CARDINALITY_ONE.equals(RelationshipUtils.getOtherSide(relationship, entitySet).getRole())) {
					continue nextES;
				}

				if (association1 == null) {
					association1 = (Association) relationship;
					association1.setTransformationRole(EnumTransformationRole.ASSOCIATION_1);
				} else {
					association2 = (Association) relationship;
					association2.setTransformationRole(EnumTransformationRole.ASSOCIATION_2);
				}
			}
			entitySet.setTransformationRole(EnumTransformationRole.ENTITY_SET);
			target.add(new Transformation(EnumTransformation.REBIND_1NN1_TO_MN, new HashSet<>(Arrays.asList(entitySet, association1, association2))));
		}
	}

	public static void getPossibleGeneralizationToAssociationTransformations(List<Transformation> target, ERModel model) {
		Utils.validateNotNull(target);
		Utils.validateNotNull(model);

		for (Relationship relationship : model.getRelationships()) {
			if (relationship instanceof Generalization) {
				relationship.setTransformationRole(EnumTransformationRole.GENERALIZATION);
				target.add(new Transformation(EnumTransformation.GENERALIZATION_TO_11_ASSOCIATION, new HashSet<>(Arrays.asList((Generalization) relationship))));
			}
		}
	}

	public static void getPossibleExtractAttributeToOwnEntitySetTransformations(List<Transformation> target, ERModel model) {
//		EXTRACT_ATTR_TO_OWN_ENTITY_SET - ATTRIBUTE,SOURCE_ENTITY_SET,DEST_ENTITY_SET - ATTRIBUTE,DEST_ENTITY_SET,SOURCE_ENTITY_SET
		Utils.validateNotNull(target);
		Utils.validateNotNull(model);

	}

	public static void getPossibleMoveAttributeToIncidentEntitySetTransformations(List<Transformation> target, ERModel model) {
//		MOVE_ATTR_TO_INCIDENT_ENTITY_SET - ATTRIBUTE,ASSOCIATION,ENTITY_SET - ATTRIBUTE,ASSOCIATION,ENTITY_SET
		Utils.validateNotNull(target);
		Utils.validateNotNull(model);

	}

	public static void getPossibleMoveAttributeToIncidentAssociationTransformations(List<Transformation> target, ERModel model) {
//		MOVE_ATTR_TO_INCIDENT_ASSOCIATION - ATTRIBUTE,ENTITY_SET,ASSOCIATION - ATTRIBUTE,ASSOCIATION,ENTITY_SET
		Utils.validateNotNull(target);
		Utils.validateNotNull(model);

	}

	public static void getPossibleRebindNaryAssociationTransformations(List<Transformation> target, ERModel model) {
		Utils.validateNotNull(target);
		Utils.validateNotNull(model);

		for (Relationship relationship : model.getRelationships()) {
			if (relationship instanceof Association && relationship.getSides().length > 2) {
				relationship.setTransformationRole(EnumTransformationRole.ASSOCIATION);
				target.add(new Transformation(EnumTransformation.REBIND_NARY_ASSOCIATION, new HashSet<>(Arrays.asList((Association) relationship))));
			}
		}

	}
}
